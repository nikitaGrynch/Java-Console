package step.learning.hws;

import java.util.HashMap;
import java.util.Map;

public class MyDictionary {
    Map<String, String> dictionary;

    public MyDictionary(){
        dictionary = new HashMap<>();
        dictionary.put("sea", "море");
        dictionary.put("cat", "кошка");
        dictionary.put("apple", "яблоко");
        dictionary.put("juice", "сок");
        dictionary.put("water", "вода");
        dictionary.put("variable", "переменная");
        dictionary.put("window", "окно");
        dictionary.put("help", "помощь");
        dictionary.put("dictionary", "словарь");
        dictionary.put("week", "неделя");
    }

    public String GetTranslate(String word){
        return dictionary.get(word);
    }
}
