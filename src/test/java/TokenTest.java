import io.restassured.path.json.JsonPath;
import org.junit.jupiter.api.Test;
import io.restassured.RestAssured;
import java.util.HashMap;
import java.util.Map;
public class TokenTest {
    @Test
    public void testRestAssured() throws InterruptedException {

        //1. Создаём задачу
        JsonPath response = RestAssured
                .get("https://playground.learnqa.ru/ajax/api/longtime_job")
                .jsonPath();

        String token = response.get("token");
        Integer seconds = response.get("seconds");

        Map<String, String> queryParam = new HashMap<>();
        queryParam.put("token", token);

        //2. Проверяем, что задача ещё не готова
        JsonPath response1 = RestAssured
                .given()
                .queryParams(queryParam)
                .get("https://playground.learnqa.ru/ajax/api/longtime_job")
                .jsonPath();

        String status = response1.get("status");
        System.out.println("Задача в статусе: "+status);

        //3. Ждём нужное количество секунд
        System.out.println("Ждём "+seconds+" секунд");
        Thread.sleep(seconds*1000);

        //4. Повторно отправляем запрос, чтобы получить результат
        JsonPath response2 = RestAssured
                .given()
                .queryParams(queryParam)
                .get("https://playground.learnqa.ru/ajax/api/longtime_job")
                .jsonPath();

        String result = response2.get("result");
        status = response2.get("status");
        System.out.println("Задача в статусе: "+status);
        System.out.println("Результат выполнения задачи: "+result);
    }
}
