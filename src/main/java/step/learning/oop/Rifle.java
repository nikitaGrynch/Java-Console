package step.learning.oop;

public class Rifle extends Weapon implements Rifled{
    Rifle(String name, float caliber) {
        super.setName(name);
        this.setCaliber(caliber);
    }
    private float caliber;

    public double getCaliber() {
        return caliber;
    }

    public void setCaliber(float caliber) {
        this.caliber = caliber;
    }

    @Override
    public String getCard() {
        return String.format("Rifle: '%s' (caliber: %.2f)", super.getName(), getCaliber());
    }

}
