package step.learning.oop;

import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

@Serializable
public class MachineGun extends Weapon implements Automatic, Classified{
    MachineGun(String name, double fireRate) {
        super.setName(name);
        this.setFireRate(fireRate);
    }
    @Required
    private double fireRate;
    public double getFireRate() {
        return fireRate;
    }

    public void setFireRate(double fireRate) {
        this.fireRate = fireRate;
    }
    @Override
    public String getCard() {
        return String.format("Machine Gun '%s' (fire rate: %.1f)", super.getName(), getFireRate());
    }

    @Override
    public String getLevel() {
        return "For military";
    }

    private static final List<Object[]> requiredFields = new ArrayList<Object[]>();

    @JsonParseCheck
    public static boolean isParseableFromJson(JsonObject jsonObject) {
//        if (requiredFields.isEmpty()) {
//            requiredFields.add(Stream.concat(
//                            Arrays.stream(MachineGun.class.getDeclaredFields()),
//                            Arrays.stream(MachineGun.class.getSuperclass().getDeclaredFields()))
//                    .filter(field -> field.isAnnotationPresent(Required.class)).toArray());
//        }
//        return requiredFields.stream().allMatch(field -> jsonObject.has(field.getClass().getName()));
        return
                Stream.concat(
                                Arrays.stream(MachineGun.class.getDeclaredFields()),
                                Arrays.stream(MachineGun.class.getSuperclass().getDeclaredFields()))
                        .filter(field -> field.isAnnotationPresent(Required.class))
                        .allMatch(field -> jsonObject.has(field.getName()));

    }

    @JsonFactory
    public static MachineGun fromJson(JsonObject jsonObject) throws IllegalArgumentException {
        String[] requiredFields = { "name", "fireRate"};
        for(String field : requiredFields){
            if(!jsonObject.has(field)) {
                throw new IllegalArgumentException("Missing required field: " + field);
            }
        }
        return new MachineGun(
                jsonObject.get(requiredFields[0]).getAsString(),
                jsonObject.get(requiredFields[1]).getAsInt()
        );
    }
}
