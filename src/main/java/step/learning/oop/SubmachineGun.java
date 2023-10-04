package step.learning.oop;

import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

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

    private static final List<Object[]> requiredFields = new ArrayList<Object[]>();

    public static boolean isParseableFromJson(JsonObject jsonObject) {
//        if(requiredFields.isEmpty()){
//            requiredFields.add(Stream.concat(
//                            Arrays.stream( SubmachineGun.class.getDeclaredFields() ),
//                            Arrays.stream(SubmachineGun.class.getSuperclass().getDeclaredFields() ) )
//                    .filter(field -> field.isAnnotationPresent(Required.class)).toArray());
//        }
//        return requiredFields.stream().allMatch(field -> jsonObject.has(field.getClass().getName()));
        return
                Stream.concat(
                                Arrays.stream(SubmachineGun.class.getDeclaredFields()),
                                Arrays.stream(SubmachineGun.class.getSuperclass().getDeclaredFields()))
                        .filter(field -> field.isAnnotationPresent(Required.class))
                        .allMatch(field -> jsonObject.has(field.getName()));
    }

    public static SubmachineGun fromJson(JsonObject jsonObject) throws IllegalArgumentException {
        String[] requiredFields = { "name", "caliber", "type"};
        for(String field : requiredFields){
            if(!jsonObject.has(field)) {
                throw new IllegalArgumentException("Missing required field: " + field);
            }
        }
        return new SubmachineGun(
                jsonObject.get(requiredFields[0]).getAsString(),
                jsonObject.get(requiredFields[1]).getAsFloat(),
                jsonObject.get(requiredFields[2]).getAsString()
        );
    }

    @Override
    public String getCard() {
        return String.format("Submachine gun: '%s' (caliber: %.2f, type: %s)", super.getName(), getCaliber(), getType());
    }
}
