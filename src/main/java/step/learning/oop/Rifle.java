package step.learning.oop;

import com.google.gson.JsonObject;

@Serializable
public class Rifle extends Weapon implements Rifled{
    Rifle(String name, float caliber) {
        super.setName(name);
        this.setCaliber(caliber);
    }
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


    @JsonParseCheck
    public static boolean isParseableFromJson(JsonObject jsonObject){
        String[] requiredFields = {"name", "caliber"};
        for (String field : requiredFields){
            if(!jsonObject.has(field)){
                return false;
            }
        }
        return true;
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
