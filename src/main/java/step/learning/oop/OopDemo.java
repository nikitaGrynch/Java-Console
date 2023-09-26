package step.learning.oop;

public class OopDemo {
    public void run() {
        Armory armory = new Armory();
        armory.addWeapon(new Gun("Colt Defender", 8, 3));
        armory.addWeapon(new Rifle("35M rifle", (float)7.92));
        armory.addWeapon(new MachineGun("Breda 30", (float)6.5));
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
    }
}
