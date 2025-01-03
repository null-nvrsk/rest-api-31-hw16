package tests;

import io.restassured.RestAssured;
import models.users.create.CreateUserRequestModel;
import models.users.create.CreateUserResponseModel;
import models.users.read_single.SingleUserResponseModel;
import models.users.update.UpdateUserRequestModel;
import models.users.update.UpdateUserResponseModel;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.qameta.allure.Allure.step;
import static io.restassured.RestAssured.given;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static specs.singleUserSpec.*;

@DisplayName("Проверка ручек из категории Users")
public class ReqresUsersTests {

    @BeforeAll
    public static void setup() {
        RestAssured.baseURI = "https://reqres.in";
        RestAssured.basePath = "/api";
    }

    @Test
    @DisplayName("Проверка одного пользователя (#2)")
    public void singleUserTest() {
        SingleUserResponseModel singleUserResponse =
            step("Получить данные по пользователю #2", () ->
                given(singleUserRequestSpec)
                .when()
                    .get("/users/2")
                .then()
                    .spec(singleUserResponseSpec)
                    .extract().as(SingleUserResponseModel.class));

        step("Проверка данных пользователя", () -> {
            assertThat(singleUserResponse.getData().getId()).isEqualTo(2);
            assertThat(singleUserResponse.getData().getEmail()).isEqualTo("janet.weaver@reqres.in");
            assertThat(singleUserResponse.getData().getFirstName()).isEqualTo("Janet");
            assertThat(singleUserResponse.getData().getLastName()).isEqualTo("Weaver");
            assertThat(singleUserResponse.getData().getAvatar()).isEqualTo("https://reqres.in/img/faces/2-image.jpg");
        });
    }

    @Test
    @DisplayName("Проверка несуществующего пользователя")
    public void invalidSingleUserTest() {
        SingleUserResponseModel singleUserResponse =
            step("Получить данные по несуществующему пользователю #23", () ->
                given(singleUserRequestSpec)
                .when()
                    .get("/users/23")
                .then()
                    .spec(invalidUserResponseSpec)
                    .extract().as(SingleUserResponseModel.class));

        step("Проверка, что нет данных пользователя", () -> {
            assertThat(singleUserResponse.getData()).isNull();
            assertThat(singleUserResponse.getSupport()).isNull();
        });
    }

    @Test
    @DisplayName("Создание нового пользователя")
    public void createUserTest() {
        CreateUserRequestModel createUserBody = new CreateUserRequestModel();
        createUserBody.setName("morpheus");
        createUserBody.setJob("leader");

        CreateUserResponseModel сreateUserResponse =
            step("Получить данные по несуществующему пользователю #23", () ->
                given(singleUserRequestSpec)
                    .body(createUserBody)
                .when()
                    .post("/users")
                .then()
                    .spec(createUserResponseSpec)
                    .extract().as(CreateUserResponseModel.class));

        step("Проверка данных нового пользователя", () -> {
            assertThat(сreateUserResponse.getName()).isEqualTo("morpheus");
            assertThat(сreateUserResponse.getJob()).isEqualTo("leader");
            assertThat(сreateUserResponse.getId()).asInt().isPositive();
        });
    }

    @Test
    @DisplayName("Обновление параметров пользователя (метод PUT)")
    public void updateSingleUserTest() {
        UpdateUserRequestModel updateUserBody = new UpdateUserRequestModel();
        updateUserBody.setName("morpheus");
        updateUserBody.setJob("zion resident");

        UpdateUserResponseModel updateUserResponse =
            step("Обновляем параметров пользователя (метод PUT)", () ->
                given(singleUserRequestSpec)
                    .body(updateUserBody)
                .when()
                    .put("/users/2")
                .then()
                    .spec(updateUserResponseSpec)
                    .extract().as(UpdateUserResponseModel.class));

        step("Проверка новых данных пользователя", () -> {
            assertThat(updateUserResponse.getName()).isEqualTo("morpheus");
            assertThat(updateUserResponse.getJob()).isEqualTo("zion resident");
        });
    }

    @Test
    @DisplayName("Обновление всех параметров пользователя (метод PATCH)")
    public void updateFieldsSingleUserTest() {
        UpdateUserRequestModel updateUserBody = new UpdateUserRequestModel();
        updateUserBody.setName("morpheus");
        updateUserBody.setJob("zion resident");

        UpdateUserResponseModel updateUserResponse =
            step("Обновляем параметров пользователя (метод PUT)", () ->
                given(singleUserRequestSpec)
                    .body(updateUserBody)
                .when()
                    .put("/users/2")
                .then()
                    .spec(updateUserResponseSpec)
                    .extract().as(UpdateUserResponseModel.class));

        step("Проверка новых данных пользователя", () -> {
            assertThat(updateUserResponse.getName()).isEqualTo("morpheus");
            assertThat(updateUserResponse.getJob()).isEqualTo("zion resident");
        });
    }

    @Test
    @DisplayName("Удаление пользователя (#2)")
    public void deleteUserTest() {
        given(singleUserRequestSpec)
        .when()
            .delete("/users/2")
        .then()
            .spec(deleteUserResponseSpec);
    }
}
