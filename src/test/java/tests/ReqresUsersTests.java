package tests;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import models.users.single_user.SingleUserResponseModel;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.qameta.allure.Allure.step;
import static io.restassured.RestAssured.given;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.hamcrest.Matchers.is;
import static specs.singleUserSpec.singleUserRequestSpec;
import static specs.singleUserSpec.singleUserResponseSpec;

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
        given()
                .log().uri()
                .contentType(ContentType.JSON)
                .when()
                .get("/users/23")
                .then()
                .log().status()
                .log().body()
                .statusCode(404);
    }

    @Test
    @DisplayName("Создание нового пользователя")
    public void createUserTest() {
        String data = "{ \"name\": \"morpheus\", \"job\": \"leader\" }";

        given()
                .log().uri()
                .contentType(ContentType.JSON)
                .body(data)
                .when()
                .post("/users")
                .then()
                .log().status()
                .log().body()
                .statusCode(201)
                .body("name", is("morpheus"))
                .body("job", is("leader"));
    }

    @Test
    @DisplayName("Обновление параметров пользователя (метод PUT)")
    public void updateSingleUserTest() {
        String data = "{ \"name\": \"morpheus\", \"job\": \"zion resident\" }";

        given()
                .log().uri()
                .contentType(ContentType.JSON)
                .body(data)
                .when()
                .put("/users/2")
                .then()
                .log().status()
                .log().body()
                .statusCode(200)
                .body("name", is("morpheus"))
                .body("job", is("zion resident"));
    }

    @Test
    @DisplayName("Обновление всех параметров пользователя (метод PATCH)")
    public void updateFieldsSingleUserTest() {
        String data = "{ \"name\": \"morpheus\", \"job\": \"zion resident\" }";

        given()
                .log().uri()
                .contentType(ContentType.JSON)
                .body(data)
                .when()
                .patch("/users/2")
                .then()
                .log().status()
                .log().body()
                .statusCode(200)
                .body("name", is("morpheus"))
                .body("job", is("zion resident"));
    }

    @Test
    @DisplayName("Обновление параметра имени пользователя")
    public void updateFieldNameSingleUserTest() {
        String data = "{ \"name\": \"neo\" }";

        given()
                .log().uri()
                .contentType(ContentType.JSON)
                .body(data)
                .when()
                .patch("/users/2")
                .then()
                .log().status()
                .log().body()
                .statusCode(200)
                .body("name", is("neo"));
    }

    @Test
    @DisplayName("Обновление параметра занятия пользователя")
    public void updateFieldJobSingleUserTest() {
        String data = "{ \"job\": \"plumber\" }";

        given()
                .log().uri()
                .contentType(ContentType.JSON)
                .body(data)
                .when()
                .patch("/users/2")
                .then()
                .log().status()
                .log().body()
                .statusCode(200)
                .body("job", is("plumber"));
    }
}
