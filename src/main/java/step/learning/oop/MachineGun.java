package step.learning.oop;

public class MachineGun extends Weapon implements Automatic, Classified{
    MachineGun(String name, double fireRate) {
        super.setName(name);
        this.setFireRate(fireRate);
    }
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
}
