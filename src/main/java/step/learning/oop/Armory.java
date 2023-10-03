package step.learning.oop;

import com.google.gson.*;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

public class Armory
{
    private List<Weapon> getSerializableWeapons(){
        List<Weapon> result = new LinkedList<>();
        for(Weapon weapon: weapons){
            if(weapon.getClass().isAnnotationPresent(Serializable.class)){
                result.add(weapon);
            }
        }
        return result;
    }
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
            writer.write(gson.toJson(this.getSerializableWeapons()));
        }
        catch (IOException ex){
            throw new RuntimeException(ex);
        }
    }

    private List<Class<?>> findSerializableClasses(){
        List<Class<?>> weaponClasses = new ArrayList<>();
        String armoryName = Armory.class.getName();
        String packageName = armoryName.substring(0, armoryName.lastIndexOf('.') + 1);
        String packagePath = packageName.replace('.', '/');
        URL resourceUrl = Armory.class.getClassLoader().getResource(packagePath);
        if(resourceUrl == null){
            throw new RuntimeException(String.format("Package '%s' got no resource", packagePath));
        }
        String resourcePath = resourceUrl.getPath();
        try {resourcePath = URLDecoder.decode(resourcePath, "UTF-8"); }
        catch (UnsupportedEncodingException ignored) {}
        File resourceDir = new File(resourcePath);
        File[] files = resourceDir.listFiles();
        if(files == null){
            throw new RuntimeException(String.format("Directory '%s' got no file list", resourceDir));
        }
        for(File file : files){
            if(file.isDirectory()){
                continue;
            }
            else if(file.isFile()) {
                if (file.getName().endsWith(".class")) {
                    String className = packageName +
                            file.getName().substring(0, file.getName().lastIndexOf('.'));
                    try {
                        Class<?> classType = Class.forName(className);
                        if (classType.isAnnotationPresent(Serializable.class)
                                && Weapon.class.isAssignableFrom(classType)) {
                            weaponClasses.add(classType);
                        }
                    } catch (ClassNotFoundException ignored) {
                        System.err.printf("Class '%s' not found", className);
                    }
                }
            }
        }
        return weaponClasses;
    }

    public void load() throws RuntimeException{
        String resourceName = "armory.json";
        List<Class<?>> weaponClasses = this.findSerializableClasses();

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
                    Method isParseableFromJson = null; //  = weaponClass.getDeclaredMethod("isParseableFromJson", JsonObject.class);
                    Method fromJson = null;  // = weaponClass.getDeclaredMethod("fromJson", JsonObject.class);
                    for (Method method : weaponClass.getDeclaredMethods()){
                        if(method.isAnnotationPresent(JsonParseCheck.class)){
                            if(isParseableFromJson != null){
                                throw new RuntimeException(String.format("Multiple methods with @%s annotation", JsonParseCheck.class.getName()));
                            }
                            isParseableFromJson = method;
                        }
                        if(method.isAnnotationPresent(JsonFactory.class)){
                            if(fromJson != null){
                                throw new RuntimeException(String.format("Multiple methods with @%s annotation", JsonFactory.class.getName()));
                            }
                            fromJson = method;
                        }

                    }
                    if(isParseableFromJson == null || fromJson == null){
                        continue;
                    }
                    isParseableFromJson.setAccessible(true);
                    boolean res = (boolean) isParseableFromJson.invoke(null, jsonObject);
                    if(res) {
                        fromJson.setAccessible(true);
                        weapon = (Weapon) fromJson.invoke(null, jsonObject);
                        break;
                    }
                }
                if(weapon != null){
                    this.weapons.add(weapon);
                }
                else{
                    System.err.println("Weapon type unrecognized - skipped");
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
        catch( IllegalAccessException | InvocationTargetException ex) {
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
