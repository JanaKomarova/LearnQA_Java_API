package lib;
import io.qameta.allure.Step;
import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.http.Header;
import io.restassured.response.Response;

import java.util.Map;

import static io.restassured.RestAssured.given;

public class ApiCoreRequests {

    @Step("Create user")
    public Response createUserRequest(String url, Map<String, String> userData){
        return   given()
                .filter(new AllureRestAssured())
                .body(userData)
                .post(url)
                .andReturn();
    }

    @Step("User login")
    public Response userLogin(String url, Map<String, String> authData){
        return given()
                .filter(new AllureRestAssured())
                .body(authData)
                .post(url)
                .andReturn();
    }

    @Step("Get user data")
    public Response getUserData(String url, String header, String cookie){
        return given()
                .filter(new AllureRestAssured())
                .header(new Header("x-csrf-token", header))
                .cookie("auth_sid", cookie)
                .get(url)
                .andReturn();
    }

    @Step("Edit user data")
    public Response editUserDataWithoutLogin(String url, Map<String, String> editData ){
        return   given()
                .filter(new AllureRestAssured())
                .body(editData)
                .put(url)
                .andReturn();
    }

    @Step("Edit user data")
    public Response editUserData(String url, Map<String, String> editData, String header, String cookie ){
        return   given()
                .filter(new AllureRestAssured())
                .header(new Header("x-csrf-token", header))
                .cookie("auth_sid", cookie)
                .body(editData)
                .put(url)
                .andReturn();
    }

    @Step("Delete user")
    public Response deleteUser(String url, String header, String cookie ){
        return   given()
                .filter(new AllureRestAssured())
                .header(new Header("x-csrf-token", header))
                .cookie("auth_sid", cookie)
                .delete(url)
                .andReturn();
    }

}
