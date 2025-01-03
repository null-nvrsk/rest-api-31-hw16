package specs;

import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

import static helpers.CustomAllureListener.withCustomTemplates;
import static io.restassured.RestAssured.with;
import static io.restassured.filter.log.LogDetail.BODY;
import static io.restassured.filter.log.LogDetail.STATUS;
import static io.restassured.http.ContentType.JSON;
import static org.hamcrest.Matchers.notNullValue;

public class singleUserSpec {

    public static RequestSpecification singleUserRequestSpec = with()
            .filter(withCustomTemplates())
            .log().uri()
            .log().headers()
            .contentType(JSON);

    public static ResponseSpecification singleUserResponseSpec = new ResponseSpecBuilder()
            .log(STATUS)
            .log(BODY)
            .expectStatusCode(200)
            .expectBody("data.id", notNullValue())
            .build();

    public static ResponseSpecification invalidUserResponseSpec = new ResponseSpecBuilder()
            .log(STATUS)
            .log(BODY)
            .expectStatusCode(404)
            .build();

    public static ResponseSpecification createUserResponseSpec = new ResponseSpecBuilder()
            .log(STATUS)
            .log(BODY)
            .expectStatusCode(201)
            .expectBody("name", notNullValue())
            .expectBody("job", notNullValue())
            .expectBody("id", notNullValue())
            .expectBody("createdAt", notNullValue())
            .build();

    public static ResponseSpecification updateUserResponseSpec = new ResponseSpecBuilder()
            .log(STATUS)
            .log(BODY)
            .expectStatusCode(200)
            .expectBody("name", notNullValue())
            .expectBody("job", notNullValue())
            .expectBody("updatedAt", notNullValue())
            .build();

    public static ResponseSpecification deleteUserResponseSpec = new ResponseSpecBuilder()
            .log(STATUS)
            .log(BODY)
            .expectStatusCode(204)
            .build();
}

