package steps;

import org.junit.jupiter.api.Assertions;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Steps {

    public static void checkColorsFormats(List<String> colors){
        boolean isAllColorsGood = true;
        for(String color : colors){
            isAllColorsGood = isColorFormatGood(color);
            if(!isAllColorsGood)
                break;
        }
        Assertions.assertTrue(isAllColorsGood, "Формат цвета неверный");
    }

    public static boolean isColorFormatGood(String color){
        Pattern patternColor = Pattern.compile("#[0-9A-F]{6}");
        Matcher matcher = patternColor.matcher(color);
        return matcher.find();
    }

}
