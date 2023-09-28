package step.learning.oop;

import com.google.gson.JsonObject;

public class Gun extends Weapon implements Classified, Used{
    private int cartridge;

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

    public static boolean isParseableFromJson(JsonObject jsonObject){
        String[] requiredFields = {"name", "cartridge"};
        for (String field : requiredFields){
            if(!jsonObject.has(field)){
                return false;
            }
        }
        return true;
    }

    public static Gun fromJson(JsonObject jsonObject) throws IllegalArgumentException {
        String[] requiredFields = { "name", "cartridge", "yearsInUse" };
        for(String field : requiredFields){
            if(!jsonObject.has(field)) {
                throw new IllegalArgumentException("Missing required field: " + field);
            }
        }
        return new Gun(
                jsonObject.get(requiredFields[0]).getAsString(),
                jsonObject.get(requiredFields[1]).getAsInt(),
                jsonObject.get(requiredFields[2]).getAsInt()
                );
    }
}
