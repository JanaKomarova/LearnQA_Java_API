import io.restassured.path.json.JsonPath;
import org.junit.jupiter.api.Test;
import io.restassured.RestAssured;

public class ParseJsonTest {
    @Test
    public void GetSecondMessageTest(){

        JsonPath response = RestAssured
                .get("https://playground.learnqa.ru/api/get_json_homework")
                .jsonPath();

        String secondMessage = response.get("messages.message[1]");
        System.out.println(secondMessage);

    }

}
