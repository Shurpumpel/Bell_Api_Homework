package in.resreq;

import dto.*;
import io.restassured.http.Cookies;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.jupiter.api.Assertions;
import org.testng.Assert;
import org.testng.annotations.Test;
import steps.Steps;

import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.hasKey;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.core.IsEqual.equalTo;

public class ApiTests {

    @Test
    public void firstTest(){
        given()
                .when()
                .get("https://reqres.in/api/users?page=2")
                .then()
                .log().all()
                .statusCode(200);
    }

    @Test
    public void secondTest(){

        given()
                .when()
                .get("https://reqres.in/api/users?page=2")
                .then()
                .log().all()
                .body("page",equalTo(2))
                .body("data[0].first_name",equalTo("Michael"))
                .body("total",notNullValue())
                .statusCode(200);
    }

    @Test
    public void thirdTest(){

        Response response = given()
                .when()
                .get("https://reqres.in/api/users?page=2")
                .then()
                .log().all()
                .body("page",equalTo(2))
                .body("data[0].first_name",equalTo("Michael"))
                .body("total",notNullValue())
                .statusCode(200)
                .extract().response();
        JsonPath jsonPath = response.jsonPath();
        Assert.assertEquals(jsonPath.getInt("page"),2,"Page is not 2");
    }

    @Test
    public void fourTest(){

        Response response = given()
                .when()
                .get("https://reqres.in/api/users?page=2")
                .then()
                .log().all()
                .body("page",equalTo(2))
                .body("data[0].first_name",equalTo("Michael"))
                .body("total",notNullValue())
                .statusCode(200)
                .extract().response();
        JsonPath jsonPath = response.jsonPath();
        Assert.assertEquals(jsonPath.getInt("page"),2,"Page is not 2");
    }

    @Test
    public void fiveTest(){
        ListUsers listUserInfo = given()
                .when()
                .get("https://reqres.in/api/users?page=2")
                .then()
                .log().body()
                .statusCode(200)
                .extract().as(ListUsers.class);
   //     Assert.assertTrue(listUserInfo.getData().stream().anyMatch(x-> x.getId() == 12));
    }

    @Test
    public void sixTest(){
        HashMap<String,String> reqLogin = new HashMap<>();
        reqLogin.put("email","eve.holt@reqres.in");
        reqLogin.put("password","cityslicka");
        given()
                .contentType("application/json")
                .body(reqLogin)
                .when()
                .post("https://reqres.in/api/login")
                .then()
                .log().body()
                .statusCode(200);
    }

    @Test
    public void sevenTest(){
        UserLogin userLogin = new UserLogin("eve.holt@reqres.in","cityslicka");
        given()
                .contentType("application/json")
                .body(userLogin)
                .when()
                .post("https://reqres.in/api/login")
                .then()
                .log().body()
                .statusCode(200);
    }

    @Test
    public void eightTest(){
        UserLogin userLogin = new UserLogin("eve.holt@reqres.in","cityslicka");
        Specification.installSpec(Specification.requestSpec(),Specification.responseSpec());
        given()
                .body(userLogin)
                .when()
                .post("/api/login")
                .then()
                .log().body()
                .statusCode(200);
    }

    @Test
    public void nineTest(){
        UserLogin userLogin = new UserLogin("eve.holt@reqres.in","cityslicka");
        Specification.installSpec(Specification.requestSpec(),Specification.responseSpec());
        Cookies cookies= given()
                .body(userLogin)
                .when()
                .post("/api/login")
                .then()
                .log().body()
                .statusCode(200)
                .extract()
                .response()
                .getDetailedCookies();

        given()
                .body(userLogin)
                .cookies(cookies)
                .when()
                .post("/api/login")
                .then()
                .log().body()
                .statusCode(200);
    }

    @Test
    public void tenTest(){
        given()
                .keyStore("src/Belyy.jks","123456")
                .when()
                .get("https://www.google.ru")
                .then()
                .log().all()
                .statusCode(200);
    }

    @Test
    public void hwFirstTest(){
        Specification.installSpec(Specification.requestSpec(), Specification.responseSpec());
        ListColors listColorsInfo = given()
                .when()
                .get("/api/unknown")
                .then()
                .extract().as(ListColors.class);
        List<String> colors = listColorsInfo.getData().stream()
                .map(ColorInfo::getColor)
                .collect(Collectors.toList());
        Steps.checkColorsFormats(colors);

        Assertions.assertEquals(listColorsInfo.getData().size(), listColorsInfo.getPerPage(),
                "Количество элементов data не соответствуе значению per_page");

        Assertions.assertTrue(listColorsInfo.getData().stream()
                .anyMatch(x->x.getYear().equals(2001)));

    }

    @Test
    public void hwSecondTest(){
        Specification.installSpec(Specification.requestSpec(),Specification.responseSpec());
        UserLogin userLogin = new UserLogin("eve.holt@reqres.in", "pistol");
        UserRegisterResponse userRegisterResponse = given()
                .body(userLogin)
                .when()
                .post("/api/register")
                .then()
                .extract().as(UserRegisterResponse.class);

        Response response = given()
                .when()
                .get("api/users/"+userRegisterResponse.getId())
                .then()
                .extract().response();
        JsonPath jsonPath = response.jsonPath();
        UserInfo user = jsonPath.getObject("data", UserInfo.class);
        Assertions.assertNotNull(user);

        Specification.responseSpecNull();
        given()
                .when()
                .delete("/api/users/"+userRegisterResponse.getId())
                .then()
                .statusCode(204);
    }

    @Test
    public void hwThirdTest(){
        UserLogin userLogin = new UserLogin("eve.holt@reqres.in");
        Specification.installSpec(Specification.requestSpec());
        given()
                .body(userLogin)
                .when()
                .post("/api/register")
                .then()
                .statusCode(400)
                .body("$", hasKey("error"));
    }

}
