package step.learning.oop;

import com.google.gson.JsonObject;

@Serializable
public class MachineGun extends Weapon implements Automatic, Classified{
    MachineGun(String name, double fireRate) {
        super.setName(name);
        this.setFireRate(fireRate);
    }
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

    @JsonParseCheck
    public static boolean isParseableFromJson(JsonObject jsonObject){
        String[] requiredFields = {"name", "fireRate"};
        for (String field : requiredFields){
            if(!jsonObject.has(field)){
                return false;
            }
        }
        return true;
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
