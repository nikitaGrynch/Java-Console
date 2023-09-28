package step.learning.oop;

import com.google.gson.JsonObject;

public class Shotgun extends Weapon implements Classified{
    private int cartridge;

    public String getActionType() {
        return actionType;
    }

    public void setActionType(String actionType) {
        this.actionType = actionType;
    }

    private String actionType;
    public int getCartridge() {
        return cartridge;
    }

    public void setCartridge(int cartridge) {
        this.cartridge = cartridge;
    }



    public Shotgun(String name, int cartridge, String actionType){
        super.setName(name);
        this.setCartridge(cartridge);
        this.setActionType(actionType);
    }
    public static boolean isParseableFromJson(JsonObject jsonObject){
        String[] requiredFields = {"name", "cartridge", "actionType"};
        for (String field : requiredFields){
            if(!jsonObject.has(field)){
                return false;
            }
        }
        return true;
    }
    public static Shotgun fromJson(JsonObject jsonObject) throws IllegalArgumentException {
        String[] requiredFields = { "name", "cartridge", "actionType" };
        for(String field : requiredFields){
            if(!jsonObject.has(field)) {
                throw new IllegalArgumentException("Missing required field: " + field);
            }
        }
        return new Shotgun(
                jsonObject.get(requiredFields[0]).getAsString(),
                jsonObject.get(requiredFields[1]).getAsInt(),
                jsonObject.get(requiredFields[2]).getAsString()
        );
    }
    @Override
    public String getCard() {
        return String.format(
                "Shortgun: '%s' (cartridge: %d, action type: %s)",
                super.getName(),
                this.getCartridge(),
                this.getActionType()
        );
    }

    @Override
    public String getLevel() {
        return "For hunters";
    }
}
