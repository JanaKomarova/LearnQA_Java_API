import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class HeaderTest {

    @Test
    public void testHeader(){
        Response responseGetHeaders = RestAssured
                .get("https://playground.learnqa.ru/api/homework_header")
                .andReturn();

        String expectedHeader = responseGetHeaders.getHeader("x-secret-homework-header");
        assertEquals("Some secret value", expectedHeader, "Unexpected header");
    }
}
