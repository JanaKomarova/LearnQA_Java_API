package tests;

import io.qameta.allure.*;
import io.restassured.response.Response;
import lib.Assertions;
import lib.BaseTestCase;
import lib.DataGenerator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import lib.ApiCoreRequests;
import java.util.HashMap;
import java.util.Map;

public class UserDeleteTest extends BaseTestCase {
    ApiCoreRequests apiCoreRequests = new ApiCoreRequests();
    @Test
    @Description("This test delete user with id = 2")
    @DisplayName("Test negativ delete user id 2")
    public void testDeleteUserWithIdTwo(){

        Map<String,String> authData = new HashMap<>();
        authData.put("email", "vinkotov@example.com");
        authData.put("password", "1234");

        Response responseGetAuth = apiCoreRequests.userLogin("https://playground.learnqa.ru/api_dev/user/login", authData);

        Response responseDeleteUser = apiCoreRequests.deleteUser("https://playground.learnqa.ru/api_dev/user/2",
                this.getHeader(responseGetAuth, "x-csrf-token"),
                this.getCookie(responseGetAuth, "auth_sid"));

        Assertions.assertJsonByName(responseDeleteUser, "error", "Please, do not delete test users with ID 1, 2, 3, 4 or 5.");
        Assertions.assertResponseCodeEquals(responseDeleteUser, 400);
    }

    @Test
    @Owner(value = "Комарова Яна Вячеславовна")
    @Description("This test delete user")
    @DisplayName("Test positive delete user")
    public void testDeleteCreatedUser(){
        //CREATE USER
        Map<String, String> userData = DataGenerator.getRegistrationData();

        Response responseCreateAuth = apiCoreRequests.createUserRequest("https://playground.learnqa.ru/api_dev/user",userData);

        String userId = responseCreateAuth.jsonPath().getString("id");

        //LOGIN
        Map<String, String> authData = new HashMap<>();
        authData.put("email", userData.get("email"));
        authData.put("password", userData.get("password"));

        Response responseGetAuth = apiCoreRequests.userLogin("https://playground.learnqa.ru/api_dev/user/login",authData);

        //DELETE
        Response responseDeleteUser = apiCoreRequests.deleteUser("https://playground.learnqa.ru/api_dev/user/"+userId,
                this.getHeader(responseGetAuth, "x-csrf-token"),
                this.getCookie(responseGetAuth, "auth_sid"));

        Assertions.assertJsonByName(responseDeleteUser, "success", "!");
        Assertions.assertResponseCodeEquals(responseDeleteUser, 200);

        //GET DATA DELETE USER
        Response responseUserData = apiCoreRequests.getUserData("https://playground.learnqa.ru/api_dev/user/"+userId,
                this.getHeader(responseGetAuth, "x-csrf-token"),
                this.getCookie(responseGetAuth, "auth_sid"));

        Assertions.assertResponseTextEquals(responseUserData, "User not found");
        Assertions.assertResponseCodeEquals(responseDeleteUser, 200);

    }

    @Test
    @Severity(value = SeverityLevel.BLOCKER)
    @Description("This test delete  another user")
    @DisplayName("Test negativ delete another user")
    public void testDeleteUserAnotherLoginUser (){
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
        System.out.println(responseCreateUser2.asString());
        String userId = responseCreateUser2.jsonPath().getString("id");

        //DELETE USER 1 DATA BY USER 2
        String newName = "Changed Name";
        Map<String, String> editData = new HashMap<>();
        editData.put("firstName", newName);

        Response responseEditUser = apiCoreRequests.deleteUser("https://playground.learnqa.ru/api_dev/user/"+userId,
                this.getHeader(responseGetAuth , "x-csrf-token"),
                this.getCookie(responseGetAuth, "auth_sid"));

        Assertions.assertJsonByName(responseEditUser, "error", "This user can only delete their own account.");
        Assertions.assertResponseCodeEquals(responseEditUser, 400);
    }
}
