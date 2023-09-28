package step.learning.oop;

import com.google.gson.*;

import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Armory
{
    public List<Weapon> weapons;
    public Armory(){
        weapons = new ArrayList<>();
    }
    public void addWeapon(Weapon weapon){
        weapons.add(weapon);
    }
    public void removeWeapon(Weapon weapon){
        weapons.remove(weapon);
    }

    public void save(){
        String path = URLDecoder.decode(
                this.getClass()
                        .getClassLoader()
                        .getResource("./")
                        .getPath() );
        try(
            FileWriter writer = new FileWriter(path + "armory.json") ) {
            Gson gson = new GsonBuilder()
                    .setPrettyPrinting() // spaces and breaks;
                    .serializeNulls()
                    .setDateFormat("yyyy-MM-dd")
                    .create();
            writer.write(gson.toJson(this.weapons));
        }
        catch (IOException ex){
            throw new RuntimeException(ex);
        }
    }
    public void load() throws RuntimeException{
        String resourceName = "armory.json";
        Class<?>[] weaponClasses = { Shotgun.class, Gun.class, MachineGun.class, Rifle.class, SubmachineGun.class };
        try(InputStreamReader reader =
                    new InputStreamReader(
                            Objects.requireNonNull(
                                    this.getClass()
                                            .getClassLoader()
                                            .getResourceAsStream(resourceName)))) {
            JsonArray jsonArray = JsonParser.parseReader(reader).getAsJsonArray();
            for(JsonElement jsonElement : jsonArray){
                System.out.println(jsonElement);

                JsonObject jsonObject = jsonElement.getAsJsonObject();
                Weapon weapon = null;
                for(Class<?> weaponClass : weaponClasses){
                    Method isParseableFromJson = weaponClass
                            .getDeclaredMethod("isParseableFromJson", JsonObject.class);
                    isParseableFromJson.setAccessible(true);
                    boolean res = (boolean) isParseableFromJson.invoke(null, jsonObject);
                    if(res) {
                        Method fromJson = weaponClass.getDeclaredMethod("fromJson", JsonObject.class);
                        fromJson.setAccessible(true);
                        weapon = (Weapon) fromJson.invoke(null, jsonObject);
                        break;
                    }
                }
                /*
                if(jsonObject.has("cartridge")){
                    if(Gun.isParseableFromJson(jsonObject)) {
                        weapon = Gun.fromJson(jsonObject);
                    }
                }

                else if(jsonObject.has("fireRate")){
                    if(MachineGun.isParseableFromJson(jsonObject)) {
                        weapon = MachineGun.fromJson(jsonObject);
                    }
                }
                else if(jsonObject.has("caliber")){
                    if(Rifle.isParseableFromJson(jsonObject)) {
                        weapon = Rifle.fromJson(jsonObject);
                    }
                }
                else{
                    System.out.println("Weapon type unrecognized - skipped");
                }
                */

                if(weapon != null){
                    this.weapons.add(weapon);
                }
            }

        }
        catch (IOException ex ){
            throw new RuntimeException("IO error: " + ex.getMessage());
        }
        catch( NullPointerException ignored ) {
            throw new RuntimeException(String.format("Resource '%s' not found%n", resourceName));
        }
        catch ( IllegalArgumentException ex ){
            throw new RuntimeException("JSON parse error: " + ex.getMessage());
        }
        catch(NoSuchMethodException | IllegalAccessException | InvocationTargetException ex) {
            throw new RuntimeException("Reflection error: " + ex.getMessage());
        }
    }

    public void printAll(){
        System.out.println("All");
        System.out.println("---");
        for(Weapon weapon : weapons){
            System.out.println(weapon.getCard());
        }
    }

    public void printAutomatic() {
        System.out.println("Automatic");
        System.out.println("---------");
        for(Weapon weapon : weapons){
            if(isAutomatic(weapon)) {
                System.out.println(weapon.getCard());
            }
        }
    }

    public void printNonAutomatic(){
        System.out.println("Non Automatic");
        System.out.println("-------------");
        for(Weapon weapon : weapons){
            if(!isAutomatic(weapon)) {
                System.out.println(weapon.getCard());
            }
        }
    }

    public boolean isAutomatic (Weapon weapon){
        return (weapon instanceof Automatic);
    }

    public void printClassified(){
        System.out.println("Classified");
        System.out.println("----------");
        for(Weapon weapon : weapons){
            if(isClassified(weapon)) {
                Classified weaponAsClassified = (Classified) weapon;
                System.out.printf(
                        "(%s) %s%n",
                        weaponAsClassified.getLevel(),
                        weapon.getCard());
            }
        }
    }

    public boolean isClassified(Weapon weapon){
        return (weapon instanceof Classified);
    }

    public boolean isRifled(Weapon weapon){
        return (weapon instanceof Rifled);
    }

    public void printRifled() {
        System.out.println("Rifled");
        System.out.println("------");
        for(Weapon weapon : weapons){
            if(isRifled(weapon)) {
                System.out.println(weapon.getCard());
            }
        }
    }
    public void printNonRifled() {
        System.out.println("Non-Rifled");
        System.out.println("----------");
        for(Weapon weapon : weapons){
            if(!isRifled(weapon)) {
                System.out.println(weapon.getCard());
            }
        }
    }

    public boolean isUsed(Weapon weapon){
        return (weapon instanceof Used);
    }

    public void printUsed() {
        System.out.println("Used");
        System.out.println("----");
        for(Weapon weapon : weapons){
            if(isUsed(weapon)) {
                Used weaponAsUsed = (Used)weapon;
                System.out.printf("(%s) %s%n",weaponAsUsed.getYears(), weapon.getCard());
            }
        }
    }
}
