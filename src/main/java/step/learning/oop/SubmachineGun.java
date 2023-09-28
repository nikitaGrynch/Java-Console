package step.learning.oop;

import com.google.gson.JsonObject;

public class SubmachineGun extends Weapon implements Automatic{
    public float getCaliber() {
        return caliber;
    }

    public void setCaliber(float caliber) {
        this.caliber = caliber;
    }

    private float caliber;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
    private String type;

    public SubmachineGun(String name, float caliber, String type){
        super.setName(name);
        this.setCaliber(caliber);
        this.setType(type);
    }

    public static boolean isParseableFromJson(JsonObject jsonObject){
        String[] requiredFields = {"name", "caliber", "type"};
        for (String field : requiredFields){
            if(!jsonObject.has(field)){
                return false;
            }
        }
        return true;
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
