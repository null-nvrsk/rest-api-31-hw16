package tests;

import models.users.single.CreateUserResponseModel;
import models.users.single.ReadUserResponseModel;
import models.users.single.UpdateUserResponseModel;
import models.users.single.UserRequestModel;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.qameta.allure.Allure.step;
import static io.restassured.RestAssured.given;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static specs.SingleUserSpec.*;

@DisplayName("Проверка ручек из категории Users")
public class ReqresUsersTests extends TestBase {

    @Test
    @DisplayName("Проверка одного пользователя (#2)")
    public void singleUserTest() {
        ReadUserResponseModel singleUserResponse =
                step("Получить данные по пользователю #2", () ->
                        given(userRequestSpec)
                                .when()
                                .get("/users/2")
                                .then()
                                .spec(userResponse200Spec)
                                .extract().as(ReadUserResponseModel.class));

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
        ReadUserResponseModel singleUserResponse =
                step("Получить данные по несуществующему пользователю #23", () ->
                        given(userRequestSpec)
                                .when()
                                .get("/users/23")
                                .then()
                                .spec(userResponse404Spec)
                                .extract().as(ReadUserResponseModel.class));

        step("Проверка, что нет данных пользователя", () -> {
            assertThat(singleUserResponse.getData()).isNull();
            assertThat(singleUserResponse.getSupport()).isNull();
        });
    }

    @Test
    @DisplayName("Создание нового пользователя")
    public void createUserTest() {
        UserRequestModel createUserBody = new UserRequestModel();
        createUserBody.setName("morpheus");
        createUserBody.setJob("leader");

        CreateUserResponseModel createUserResponse =
                step("Получить данные по несуществующему пользователю #23", () ->
                        given(userRequestSpec)
                            .body(createUserBody)
                        .when()
                            .post("/users")
                        .then()
                            .spec(userResponse201Spec)
                            .extract().as(CreateUserResponseModel.class));

        step("Проверка данных нового пользователя", () -> {
            assertThat(createUserResponse.getName()).isEqualTo("morpheus");
            assertThat(createUserResponse.getJob()).isEqualTo("leader");
            assertThat(createUserResponse.getId()).asInt().isPositive();
        });
    }

    @Test
    @DisplayName("Обновление параметров пользователя (метод PUT)")
    public void updateSingleUserTest() {
        UserRequestModel updateUserBody = new UserRequestModel();
        updateUserBody.setName("morpheus");
        updateUserBody.setJob("zion resident");

        UpdateUserResponseModel updateUserResponse =
                step("Обновляем параметров пользователя (метод PUT)", () ->
                        given(userRequestSpec)
                            .body(updateUserBody)
                        .when()
                            .put("/users/2")
                        .then()
                            .spec(userResponse200Spec)
                            .extract().as(UpdateUserResponseModel.class));

        step("Проверка новых данных пользователя", () -> {
            assertThat(updateUserResponse.getName()).isEqualTo("morpheus");
            assertThat(updateUserResponse.getJob()).isEqualTo("zion resident");
        });
    }

    @Test
    @DisplayName("Обновление всех параметров пользователя (метод PATCH)")
    public void updateFieldsSingleUserTest() {
        UserRequestModel updateUserBody = new UserRequestModel();
        updateUserBody.setName("morpheus");
        updateUserBody.setJob("zion resident");

        UpdateUserResponseModel updateUserResponse =
                step("Обновляем параметров пользователя (метод PUT)", () ->
                        // @formatter:off
                        given(userRequestSpec)
                            .body(updateUserBody)
                        .when()
                            .put("/users/2")
                        .then()
                            .spec(userResponse200Spec)
                            .extract().as(UpdateUserResponseModel.class));
                        // @formatter:on

        step("Проверка новых данных пользователя", () -> {
            assertThat(updateUserResponse.getName()).isEqualTo("morpheus");
            assertThat(updateUserResponse.getJob()).isEqualTo("zion resident");
        });
    }

    @Test
    @DisplayName("Удаление пользователя (#2)")
    public void deleteUserTest() {
        // @formatter:off
        given(userRequestSpec)
        .when()
            .delete("/users/2")
        .then()
            .spec(userResponse204Spec);
        // @formatter:on
    }
}
