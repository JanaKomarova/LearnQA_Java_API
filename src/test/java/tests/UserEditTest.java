package tests;

import io.qameta.allure.*;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import lib.Assertions;
import lib.BaseTestCase;
import lib.DataGenerator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import lib.ApiCoreRequests;
import java.util.HashMap;
import java.util.Map;

public class UserEditTest extends BaseTestCase {

    ApiCoreRequests apiCoreRequests = new ApiCoreRequests();

    @Test
    @TmsLinks({@TmsLink(value = "TEST-135"), @TmsLink(value = "TEST-158")})
    @Description("This test edit user data without login")
    @DisplayName("Test negativ edit user data w/o login")
    public void testEditUserNotAuth(){

        //CREATE USER
        Map<String, String> userData = DataGenerator.getRegistrationData();

        Response responseCreateAuth = apiCoreRequests.createUserRequest("https://playground.learnqa.ru/api_dev/user",userData);

        String userId = responseCreateAuth.jsonPath().getString("id");

        //EDIT NOT AUTH
        String newName = "Changed Name";
        Map<String, String> editData = new HashMap<>();
        editData.put("firstName", newName);

        Response responseEditUser = apiCoreRequests.editUserDataWithoutLogin("https://playground.learnqa.ru/api_dev/user/"+userId, editData );

        Assertions.assertJsonByName(responseEditUser, "error", "Auth token not supplied");
        Assertions.assertResponseCodeEquals(responseEditUser, 400);

    }

    @Test
    @Flaky
    @Description("This test edit another user")
    @DisplayName("Test negativ edit another user")
    public void testEditUserAnotherLoginUser(){
        //CREATE USER 1
        Map<String, String> userLoginData = DataGenerator.getRegistrationData();

        Response responseCreateUser1 = apiCoreRequests.createUserRequest("https://playground.learnqa.ru/api_dev/user",userLoginData);

        //LOGIN USER 1
        Map<String, String> authData = new HashMap<>();
        authData.put("email", userLoginData.get("email"));
        authData.put("password", userLoginData.get("password"));

        Response responseGetAuth = apiCoreRequests.userLogin("https://playground.learnqa.ru/api_dev/user/login",authData);

        //CREATE USER 2
        Map<String, String> userEditData = DataGenerator.getRegistrationData();

        Response responseCreateUser2 = apiCoreRequests.createUserRequest("https://playground.learnqa.ru/api_dev/user",userEditData);

        String userId = responseCreateUser2.jsonPath().getString("id");

        //EDIT USER 1 DATA BY USER 2
        String newName = "Changed Name";
        Map<String, String> editData = new HashMap<>();
        editData.put("firstName", newName);

        Response responseEditUser = apiCoreRequests.editUserData("https://playground.learnqa.ru/api_dev/user/"+userId, editData,
                this.getHeader(responseGetAuth , "x-csrf-token"),
                this.getCookie(responseGetAuth, "auth_sid"));

        Assertions.assertJsonByName(responseEditUser, "error", "This user can only edit their own data.");
        Assertions.assertResponseCodeEquals(responseEditUser, 400);
    }

    @Test
    @Issue(value = "TEST-4627")
    @Description("This test edit user with email without @")
    @DisplayName("Test negativ incorrect email")
    public void testEditUserIncorrectEmail() {

        //CREATE USER
        Map<String, String> userData = DataGenerator.getRegistrationData();

        Response responseCreateAuth = apiCoreRequests.createUserRequest("https://playground.learnqa.ru/api_dev/user",userData);

        String userId = responseCreateAuth.jsonPath().getString("id");

        //LOGIN
        Map<String, String> authData = new HashMap<>();
        authData.put("email", userData.get("email"));
        authData.put("password", userData.get("password"));

        Response responseGetAuth = apiCoreRequests.userLogin("https://playground.learnqa.ru/api_dev/user/login",authData);

        //EDIT
        String incorrectEmail = "testexample.com";
        Map<String, String> editData = new HashMap<>();
        editData.put("email", incorrectEmail);

        Response responseEditUser = apiCoreRequests.editUserData("https://playground.learnqa.ru/api_dev/user/"+userId, editData,
                this.getHeader(responseGetAuth , "x-csrf-token"),
                this.getCookie(responseGetAuth, "auth_sid"));

        Assertions.assertJsonByName(responseEditUser, "error", "Invalid email format");
        Assertions.assertResponseCodeEquals(responseEditUser, 400);
    }

    @Test
    @Link(name = "Ссылка", url = "http://yandex.ru")
    @Description("This test edit user with firstName 1 characters")
    @DisplayName("Test negativ short firstName ")
    public void testEditUserIncorrectName(){
        //CREATE USER
        Map<String, String> userData = DataGenerator.getRegistrationData();

        Response responseCreateAuth = apiCoreRequests.createUserRequest("https://playground.learnqa.ru/api_dev/user",userData);

        String userId = responseCreateAuth.jsonPath().getString("id");

        //LOGIN
        Map<String, String> authData = new HashMap<>();
        authData.put("email", userData.get("email"));
        authData.put("password", userData.get("password"));

        Response responseGetAuth = apiCoreRequests.userLogin("https://playground.learnqa.ru/api_dev/user/login",authData);

        //EDIT
        String incorrectName = "m";
        Map<String, String> editData = new HashMap<>();
        editData.put("firstName ", incorrectName);

        Response responseEditUser = apiCoreRequests.editUserData("https://playground.learnqa.ru/api_dev/user/"+userId, editData,
                this.getHeader(responseGetAuth , "x-csrf-token"),
                this.getCookie(responseGetAuth, "auth_sid"));

        Assertions.assertJsonByName(responseEditUser, "error", "No data to update");
        Assertions.assertResponseCodeEquals(responseEditUser, 400);

    }
}
