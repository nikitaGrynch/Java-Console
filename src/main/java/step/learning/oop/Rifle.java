package step.learning.oop;

import com.google.gson.JsonObject;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

@Serializable
public class Rifle extends Weapon implements Rifled{
    Rifle(String name, float caliber) {
        super.setName(name);
        this.setCaliber(caliber);
    }
    @Required
    private float caliber;

    public double getCaliber() {
        return caliber;
    }

    public void setCaliber(float caliber) {
        this.caliber = caliber;
    }

    @Override
    public String getCard() {
        return String.format("Rifle: '%s' (caliber: %.2f)", super.getName(), getCaliber());
    }

    private static Object[] requiredFields;
    @JsonParseCheck
    public static boolean isParseableFromJson(JsonObject jsonObject) {
        requiredFields = Stream.concat(
                        Arrays.stream( Rifle.class.getDeclaredFields() ),
                        Arrays.stream(Rifle.class.getSuperclass().getDeclaredFields() ) )
                .filter(field -> field.isAnnotationPresent(Required.class))
                .map(field -> field.getName())
                .toArray();
        return
                Arrays.stream(requiredFields)
                        .allMatch(field -> jsonObject.has(field.toString() ) );
    }

    @JsonFactory
    public static Rifle fromJson(JsonObject jsonObject) throws IllegalArgumentException {
        for(Object field : requiredFields){
            if(!jsonObject.has(field.toString())) {
                throw new IllegalArgumentException("Missing required field: " + field);
            }
        }
        return new Rifle(
                jsonObject.get(requiredFields[1].toString()).getAsString(),
                jsonObject.get(requiredFields[0].toString()).getAsFloat()
        );
    }

}
