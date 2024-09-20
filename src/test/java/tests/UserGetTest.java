package tests;

import io.qameta.allure.Description;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import lib.Assertions;
import lib.BaseTestCase;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import lib.ApiCoreRequests;
import java.util.HashMap;
import java.util.Map;

public class UserGetTest extends BaseTestCase {

    ApiCoreRequests apiCoreRequests = new ApiCoreRequests();

    @Test
    @Description("This test get data another user")
    @DisplayName("Test positive get data")
    public void testGetUserDataWithAnotherId(){
        Map<String, String> authData = new HashMap<>();
        authData.put("email", "vinkotov@example.com");
        authData.put("password", "1234");

        Response responseGetAuth = apiCoreRequests.userLogin("https://playground.learnqa.ru/api_dev/user/login", authData);

        String header = this.getHeader(responseGetAuth, "x-csrf-token");
        String cookie = this.getCookie(responseGetAuth, "auth_sid");

        Response responseUserData = apiCoreRequests.getUserData("https://playground.learnqa.ru/api_dev/user/3",header, cookie);

        Assertions.assertJsonHasField(responseUserData, "username");
        Assertions.assertJsonNotHasField(responseUserData,"firstName");
        Assertions.assertJsonNotHasField(responseUserData,"lastName");
        Assertions.assertJsonNotHasField(responseUserData,"email");

    }
}