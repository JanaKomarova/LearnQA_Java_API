import org.junit.jupiter.api.Test;
import io.restassured.RestAssured;
import io.restassured.response.Response;
public class ManyRedirectsTest {
    @Test
    public void testRestAssured(){

        String url = "https://playground.learnqa.ru/api/long_redirect";
        while (url != null) {

            Response response = RestAssured
                    .given()
                    .redirects()
                    .follow(false)
                    .get(url)
                    .andReturn();

            url = response.getHeader("Location");
            System.out.println(url);
        }
    }
}
