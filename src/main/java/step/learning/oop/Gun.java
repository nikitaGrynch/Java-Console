package step.learning.oop;

import com.google.gson.JsonObject;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

@Serializable
public class Gun extends Weapon implements Classified, Used{
    @Required
    private int cartridge;

    @Required
    private int yearsInUse;

    public Gun(String name, int cartridge, int yearsInUse) {
        super.setName(name);
        setCartridge(cartridge);
        setYearsInUse(yearsInUse);
    }

    public int getCartridge() {
        return cartridge;
    }

    public void setCartridge(int cartridge){
        this.cartridge = cartridge;
    }

    public int getYearsInUse(){
        return yearsInUse;
    }

    public void setYearsInUse(int yearsInUse){
        this.yearsInUse = yearsInUse;
    }

    @Override
    public String getCard(){
        return String.format(
                "Gun: '%s' (cartridge: %d)",
                super.getName(),
                this.getCartridge()
        );
    }

    @Override
    public String getLevel() {
        return "For civil";
    }

    @Override
    public String getYears() {
        return getYearsInUse() + " years in use";
    }

    private static Object[] requiredFields;

    @JsonParseCheck
    public static boolean isParseableFromJson(JsonObject jsonObject){
        requiredFields = Stream.concat(
                        Arrays.stream( Gun.class.getDeclaredFields() ),
                        Arrays.stream(Gun.class.getSuperclass().getDeclaredFields() ) )
                .filter(field -> field.isAnnotationPresent(Required.class))
                .map((field -> field.getName()))
                .toArray();
        return
                Arrays.stream(requiredFields)
                        .allMatch(field -> jsonObject.has(field.toString() ) );
    }

    @JsonFactory
    public static Gun fromJson(JsonObject jsonObject) throws IllegalArgumentException {
        for(Object field : requiredFields){
            if(!jsonObject.has(field.toString())) {
                throw new IllegalArgumentException("Missing required field: " + field);
            }
        }
        return new Gun(
                jsonObject.get(requiredFields[2].toString()).getAsString(),
                jsonObject.get(requiredFields[0].toString()).getAsInt(),
                jsonObject.get(requiredFields[1].toString()).getAsInt()
                );
    }
}
