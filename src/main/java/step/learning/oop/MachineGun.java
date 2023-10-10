package step.learning.oop;

import com.google.gson.JsonObject;
import com.sun.corba.se.spi.ior.ObjectKey;
import sun.nio.cs.ext.IBM037;

import javax.crypto.Mac;
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

    private static Object[] requiredFields;
    @JsonParseCheck
    public static boolean isParseableFromJson(JsonObject jsonObject) {
        requiredFields = Stream.concat(
                        Arrays.stream( MachineGun.class.getDeclaredFields() ),
                        Arrays.stream(MachineGun.class.getSuperclass().getDeclaredFields() ) )
                .filter(field -> field.isAnnotationPresent(Required.class))
                .map(field -> field.getName())
                .toArray();
        return
                Arrays.stream(requiredFields)
                        .allMatch(field -> jsonObject.has(field.toString() ) );

    }

    @JsonFactory
    public static MachineGun fromJson(JsonObject jsonObject) throws IllegalArgumentException {
        for(Object field : requiredFields){
            if(!jsonObject.has(field.toString())) {
                throw new IllegalArgumentException("Missing required field: " + field);
            }
        }
        return new MachineGun(
                jsonObject.get(requiredFields[1].toString()).getAsString(),
                jsonObject.get(requiredFields[0].toString()).getAsInt()
        );
    }
}
