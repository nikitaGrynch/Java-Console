package step.learning.oop;

import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

@Serializable
public class SubmachineGun extends Weapon implements Automatic{
    public float getCaliber() {
        return caliber;
    }

    public void setCaliber(float caliber) {
        this.caliber = caliber;
    }

    @Required
    private float caliber;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Required
    private String type;

    public SubmachineGun(String name, float caliber, String type){
        super.setName(name);
        this.setCaliber(caliber);
        this.setType(type);
    }

    private static Object[] requiredFields;
    @JsonParseCheck
    public static boolean isParseableFromJson(JsonObject jsonObject) {
        requiredFields = Stream.concat(
                        Arrays.stream( SubmachineGun.class.getDeclaredFields() ),
                        Arrays.stream(SubmachineGun.class.getSuperclass().getDeclaredFields() ) )
                .filter(field -> field.isAnnotationPresent(Required.class))
                .map(field -> field.getName())
                .toArray();
        return
                Arrays.stream(requiredFields)
                        .allMatch(field -> jsonObject.has(field.toString() ) );
    }

    @JsonFactory
    public static SubmachineGun fromJson(JsonObject jsonObject) throws IllegalArgumentException {
        for(Object field : requiredFields){
            if(!jsonObject.has(field.toString())) {
                throw new IllegalArgumentException("Missing required field: " + field);
            }
        }
        return new SubmachineGun(
                jsonObject.get(requiredFields[2].toString()).getAsString(),
                jsonObject.get(requiredFields[0].toString()).getAsFloat(),
                jsonObject.get(requiredFields[1].toString()).getAsString()
        );
    }

    @Override
    public String getCard() {
        return String.format("Submachine gun: '%s' (caliber: %.2f, type: %s)", super.getName(), getCaliber(), getType());
    }
}
