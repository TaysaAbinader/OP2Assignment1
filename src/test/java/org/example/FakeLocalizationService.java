package org.example;
import java.util.HashMap;
import java.util.Map;

public class FakeLocalizationService extends LocalizationService {
    @Override
    public Map<String, String> getLanguageStrings(String langCode) {
        Map<String, String> map = new HashMap<>();
        // Provide enough data to satisfy the controller's if (!strings.isEmpty()) check
        map.put("prompt2", "PriceLabel");
        map.put("prompt3", "QtyLabel");
        map.put("result", "ResultLabel");
        map.put("calculate_button", "Calculate");
        return map;
    }
}
