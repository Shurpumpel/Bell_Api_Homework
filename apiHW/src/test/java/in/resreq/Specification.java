package in.resreq;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

public class Specification {
    public static RequestSpecification requestSpec() {
        RequestSpecification requestSpecification = new RequestSpecBuilder()
                .setBaseUri("https://reqres.in")
                .setContentType("application/json")
                .build();
        return requestSpecification;
    }

    public static ResponseSpecification responseSpec() {
        ResponseSpecification responseSpecification = new ResponseSpecBuilder()
                .expectStatusCode(200)
                .build();
        return responseSpecification;
    }

    public static void installSpec(RequestSpecification requestSpec) {
        RestAssured.requestSpecification = requestSpec;
    }

    public static void installSpec(ResponseSpecification responseSpec) {
        RestAssured.responseSpecification = responseSpec;
    }

    public static void installSpec(RequestSpecification requestSpec, ResponseSpecification responseSpec) {
        RestAssured.requestSpecification = requestSpec;
        RestAssured.responseSpecification = responseSpec;
    }

    public static void specNull() {
        RestAssured.requestSpecification = null;
        RestAssured.responseSpecification = null;
    }

    public static void responseSpecNull(){
        RestAssured.responseSpecification = null;
    }
}
