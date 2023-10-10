package step.learning.oop;

import com.google.gson.JsonObject;
import com.sun.corba.se.spi.ior.ObjectKey;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

@Serializable
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

    private static Object[] requiredFields;

    @JsonParseCheck
    public static boolean isParseableFromJson(JsonObject jsonObject) {
        requiredFields = Stream.concat(
                        Arrays.stream( Shotgun.class.getDeclaredFields() ),
                        Arrays.stream(Shotgun.class.getSuperclass().getDeclaredFields() ) )
                .filter(field -> field.isAnnotationPresent(Required.class))
                .map(field -> field.getName())
                .toArray();
        return
                Arrays.stream(requiredFields)
                        .allMatch(field -> jsonObject.has(field.toString() ) );
    }

    @JsonFactory
    public static Shotgun fromJson(JsonObject jsonObject) throws IllegalArgumentException {
        for(Object field : requiredFields){
            if(!jsonObject.has(field.toString())) {
                throw new IllegalArgumentException("Missing required field: " + field);
            }
        }
        return new Shotgun(
                jsonObject.get(requiredFields[2].toString()).getAsString(),
                jsonObject.get(requiredFields[0].toString()).getAsInt(),
                jsonObject.get(requiredFields[1].toString()).getAsString()
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
