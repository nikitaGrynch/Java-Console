package step.learning.oop;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Objects;

public class OopDemo {

    public void run3(){
        String resourceName = "colt.json";
        try(InputStreamReader reader =
                    new InputStreamReader(
                            Objects.requireNonNull(
                                    this.getClass()
                                            .getClassLoader()
                                            .getResourceAsStream(resourceName)))) {
            JsonObject jsonObject = JsonParser.parseReader(reader).getAsJsonObject();
            Weapon weapon = null;
            if(jsonObject.has("cartridge")){
                weapon = new Gun(
                        jsonObject.get("name").getAsString(),
                        jsonObject.get("cartridge").getAsInt(),
                        jsonObject.get("yearsInUse").getAsInt()
                );
            }
            else if(jsonObject.has("fireRate")){
                weapon = new MachineGun(
                        jsonObject.get("name").getAsString(),
                        jsonObject.get("fireRate").getAsDouble()
                );
            }
            else if(jsonObject.has("caliber")){
                weapon = new Rifle(
                        jsonObject.get("name").getAsString(),
                        jsonObject.get("caliber").getAsFloat()
                );
            }
            else{
                System.out.println("Weapon type unrecognized");
            }
            if(weapon != null){
                System.out.println(weapon.getCard());
            }
        }
        catch (IOException ex){
            System.out.println("IO error: " + ex.getMessage());
        }
        catch(NullPointerException ignored){
            System.out.printf("Resource '%s' not found%n", resourceName);
        }
    }

    public void run2() {
        String jsonString = "{\"name\":\"Colt Defender\", \"cartridge\": 8, \"yearsInUse\": 3}";
        Gson gson = new Gson();
        Gun gun = new Gson().fromJson(jsonString, Gun.class);
        System.out.println(gun.getCard());
        System.out.println(gson.toJson(gun));
        Gson gson2 = new GsonBuilder()
                .setPrettyPrinting() // spaces and breaks;
                .serializeNulls()
                .setDateFormat("yyyy-MM-dd")
                .create();
        System.out.println(gson2.toJson(gun));
    }
    public void run1() {
        Armory armory = new Armory();
        armory.addWeapon(new Gun("Colt Defender", 8, 3));
        armory.addWeapon(new Rifle("35M rifle", (float)7.92));
        armory.addWeapon(new MachineGun("Breda 30", (float)6.5));
        armory.addWeapon( new MachineGun( "M249 SAW", 8.5 ) );
        armory.addWeapon( new Rifle( "Mauser 98k",   7.92f ) );
        armory.addWeapon(new Gun("Glock 19", 17, 1));
        armory.addWeapon(new MachineGun("ДШК", 600));
        armory.addWeapon(new SubmachineGun("Beretta M12", (float)9, "SMG"));
        armory.addWeapon(new SubmachineGun("FN P90", (float)5.7, "PDW"));
        armory.addWeapon(new SubmachineGun("Lmg-Pist 41/44", (float)7.65, "MP"));
        armory.addWeapon(new Shotgun("Benelli Nova", 12, "pump"));
        armory.addWeapon(new Shotgun("Browning Citori", 12, "double-barreled"));
        armory.addWeapon(new Shotgun("Benelli Supernova", 12, "pump"));
        armory.printAll();
        System.out.println("------------------------------");
        armory.printAutomatic();
        System.out.println("------------------------------");
        armory.printNonAutomatic();
        System.out.println("------------------------------");
        armory.printClassified();
        System.out.println("------------------------------");
        armory.printRifled();
        System.out.println("------------------------------");
        armory.printNonRifled();
        System.out.println("------------------------------");
        armory.printUsed();
        armory.save();
    }

    public void run(){
        Armory armory = new Armory();
        armory.load();
        armory.printAll();
    }
}
