package step.learning.oop;

import com.google.gson.JsonObject;

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

    private static final List<Object[]> requiredFields = new ArrayList<Object[]>();


    @JsonParseCheck
    public static boolean isParseableFromJson(JsonObject jsonObject) {
//        if(requiredFields.isEmpty()){
//            requiredFields.add(Stream.concat(
//                            Arrays.stream( Rifle.class.getDeclaredFields() ),
//                            Arrays.stream(Rifle.class.getSuperclass().getDeclaredFields() ) )
//                    .filter(field -> field.isAnnotationPresent(Required.class)).toArray());
//        }
//        return requiredFields.stream().allMatch(field -> jsonObject.has(field.getClass().getName()));
        return
                Stream.concat(
                                Arrays.stream(Rifle.class.getDeclaredFields()),
                                Arrays.stream(Rifle.class.getSuperclass().getDeclaredFields()))
                        .filter(field -> field.isAnnotationPresent(Required.class))
                        .allMatch(field -> jsonObject.has(field.getName()));
    }

    @JsonFactory
    public static Rifle fromJson(JsonObject jsonObject) throws IllegalArgumentException {
        String[] requiredFields = { "name", "caliber"};
        for(String field : requiredFields){
            if(!jsonObject.has(field)) {
                throw new IllegalArgumentException("Missing required field: " + field);
            }
        }
        return new Rifle(
                jsonObject.get(requiredFields[0]).getAsString(),
                jsonObject.get(requiredFields[1]).getAsFloat()
        );
    }

}
