package step.learning.oop;

import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

public class Shotgun extends Weapon implements Classified{

    @Required
    private int cartridge;

    public String getActionType() {
        return actionType;
    }

    public void setActionType(String actionType) {
        this.actionType = actionType;
    }

    @Required
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

    private static final List<Object[]> requiredFields = new ArrayList<Object[]>();

    public static boolean isParseableFromJson(JsonObject jsonObject) {
//        if(requiredFields.isEmpty()){
//            requiredFields.add(Stream.concat(
//                            Arrays.stream( Shotgun.class.getDeclaredFields() ),
//                            Arrays.stream(Shotgun.class.getSuperclass().getDeclaredFields() ) )
//                    .filter(field -> field.isAnnotationPresent(Required.class)).toArray());
//        }
//        return requiredFields.stream().allMatch(field -> jsonObject.has(field.getClass().getName()));
        return
                Stream.concat(
                                Arrays.stream(Shotgun.class.getDeclaredFields()),
                                Arrays.stream(Shotgun.class.getSuperclass().getDeclaredFields()))
                        .filter(field -> field.isAnnotationPresent(Required.class))
                        .allMatch(field -> jsonObject.has(field.getName()));
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
