package tests;
import io.qameta.allure.Description;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import lib.Assertions;
import lib.BaseTestCase;
import lib.DataGenerator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import lib.ApiCoreRequests;
import java.util.HashMap;
import java.util.Map;

public class UserRegisterTest extends BaseTestCase {
    private final ApiCoreRequests apiCoreRequests = new ApiCoreRequests();

    @Test
    @Description("This test does not create a user with mail without @")
    @DisplayName("Test negativ incorrect Email")
    public void testCreateUserIncorrectEmail(){
        String incorrectEmail = "testexample.com";

        Map<String, String> userData = new HashMap<>();
        userData.put("email",incorrectEmail);
        userData = DataGenerator.getRegistrationData(userData);

        Response responseCreateAuthWithIncorrectEmail = apiCoreRequests.createUserRequest("https://playground.learnqa.ru/api/user", userData);

        Assertions.assertResponseTextEquals(responseCreateAuthWithIncorrectEmail, "Invalid email format");
        Assertions.assertResponseCodeEquals(responseCreateAuthWithIncorrectEmail, 400);
    }

    @Description("This test does not create a user without parameter")
    @DisplayName("Test negativ without parameter")
    @ParameterizedTest
    @ValueSource (strings = {"email", "password", "username", "firstName", "lastName"})

    public void testCreateUserMissedParam (String condition) {

        Map<String, String> userData = new HashMap<>();
        if (condition.equals("email") ) {
            userData.put("email", null);
        }
        else if (condition.equals("password") ) {
            userData.put("password", null);
        }

        else if (condition.equals("username") ) {
            userData.put("username", null);
        }

        else if (condition.equals("firstName") ) {
            userData.put("firstName", null);
        }

        else if (condition.equals("lastName") ) {
            userData.put("lastName", null);
        }


        userData = DataGenerator.getRegistrationData(userData);

        Response responseCreateUserWithoutOneField = apiCoreRequests.createUserRequest("https://playground.learnqa.ru/api/user", userData);

        Assertions.assertResponseTextEquals(responseCreateUserWithoutOneField, "The following required params are missed: "+condition);
        Assertions.assertResponseCodeEquals(responseCreateUserWithoutOneField, 400);

    }

    @Test
    @Description("This test does not create with user name 1 characters")
    @DisplayName("Test negativ with short user name")
    public void testCreateUserWithShortName(){
        String shortUserName = "m";

        Map<String, String> userData = new HashMap<>();
        userData.put("username",shortUserName);
        userData = DataGenerator.getRegistrationData(userData);

        Response responseCreateAuthWithIncorrectEmail = apiCoreRequests.createUserRequest("https://playground.learnqa.ru/api/user", userData);


        Assertions.assertResponseTextEquals(responseCreateAuthWithIncorrectEmail, "The value of 'username' field is too short");
        Assertions.assertResponseCodeEquals(responseCreateAuthWithIncorrectEmail, 400);
    }

    @Test
    @Description("This test does not create with user name more 250 characters")
    @DisplayName("Test negativ with long user name")

    public void testCreateUserWithLongName(){
        String longUserName = "VjLNFrXnlRXgXdFIJkDbSrMJxRvOegOEXleXwmDrhgSurlGRUkJuyOZioVUQyzJsJxKBYYBOafOhhOtDfGzIFDtWoIuWQlaGapcyTTCyPgnWtdIMlfEFikmQfutdoqAerhvmurhPDrDQtufsoUAzNPMrqpAzMSIDXRQRxBvkLRYiqlnCuktqeWDXYbOtzEQJePJsOviVfwtqZabwdfuNAKTpdGpHBeAyGLyUInobbGWSvJhlMvdWLTSSrD\n" +
                "HXUFXGUwTMCgiLTintRNakNfDukJRGoDDXMMUJggRQagyWStaWiulVbBFeWvUfVZWGjMggQlAQOXtrUIfvoLQNRUUtCsymtuvMteXlrVdfSbeIiFwDAbJmlCBiYPmicJZGlGkbFgCGelzUVcmUGmdxgdSWPNavaLGBkGZLDAQOmnmhnYfRJJfmsHEWVmxiHdhPBzkBTFGTDOBRZxPBIUTlfxolbrnIlJofZPwdfGxSTughvAylEVWdqlcL\n" +
                "fCGGscGxXunbZdwUHciDdxLziMGNAEoFEOCAYZgAqQkSGfCJBkFWKLeUApXzHsfBmlUNkEZFONFRHaPBeOagXoLdSQJMCTTXLvafAjiGxKGUPJXiYdwFTfPXfdbKSyNENuCzoXEDWuJEHkVtkuhAzeGTbRMAtuqcoouzGwFIwKEdJreoLnLXlQbgIxJYegQRVhLpVpQMuOfhxuislaBftymiMwSCUQcUClPmQRePsAYcgkzVeNLIvXKnMk\n" +
                "vNvRHxKFTmghJGNnOxZNGQGnDkgxfbDHRmTGompAjocpcfpxxQZZbAAiMGrRcYaMDXppTFqyudfYDIJtEFNqkUmYagCsIKHkjuwHfIOozEjHIMVQdVXrzsXfRNQXAHmbbZZdrfNAhrjDlAAoCQmaJTCVavqXICCGXTLBjeruTNcmpjCYIpXNAqPEecwUYDACTNyBdUPIooUMbjwdiQAqIkhJIjcNkuEtyyizmLMvCaKDpkqNllPgXicmkG\n" +
                "XPIDCzUuDmYRtBHjwGKnKvcfgtcFVkZaQKgUtfzhepTfFbKSsbcYVsltLVsyQPnwhYEiMYFjIBcEuIvMbNNzwxGHUiQhrTiucHyMcgOqJrrLcjvIAKZYLtbgqOwEnqIRmJuIxZSSPECvChuUaomcqtUcuAEYeQWasKpAGdJSanWJzXkeRBUfZvgNnNtTFSPTsXWwYdwPvdyIcWgPjXVDkNcsmZLiMHFKAZMcgiiPBwxljzTePbeSJaDCed";

        Map<String, String> userData = new HashMap<>();
        userData.put("username",longUserName);
        userData = DataGenerator.getRegistrationData(userData);

        Response responseCreateAuthWithIncorrectEmail = apiCoreRequests.createUserRequest("https://playground.learnqa.ru/api/user", userData);

        Assertions.assertResponseTextEquals(responseCreateAuthWithIncorrectEmail, "The value of 'username' field is too long");
        Assertions.assertResponseCodeEquals(responseCreateAuthWithIncorrectEmail, 400);
    }
}
