package step.learning.oop;

import java.util.ArrayList;
import java.util.List;

public class Armory
{
    private List<Weapon> weapons;
    public Armory(){
        weapons = new ArrayList<>();
    }
    public void addWeapon(Weapon weapon){
        weapons.add(weapon);
    }
    public void removeWeapon(Weapon weapon){
        weapons.remove(weapon);
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
