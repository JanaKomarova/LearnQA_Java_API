import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class TextStringTest {

    @Test
    public void testTextString(){
        String textString = "text line more 15 characters";
        assertTrue(textString.length()>15, "Text less than 15 characters");
    }
}
