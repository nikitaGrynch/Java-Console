package step.learning.oop;

public class Gun extends Weapon implements Classified, Used{
    private int cartridge;

    private int yearsInUse;

    public Gun(String name, int cartridge, int yearsInUse) {
        super.setName(name);
        setCartridge(cartridge);
        setYearsInUse(yearsInUse);
    }

    public int getCartridge() {
        return cartridge;
    }

    public void setCartridge(int cartridge){
        this.cartridge = cartridge;
    }

    public int getYearsInUse(){
        return yearsInUse;
    }

    public void setYearsInUse(int yearsInUse){
        this.yearsInUse = yearsInUse;
    }

    @Override
    public String getCard(){
        return String.format(
                "Gun: '%s' (cartridge: %d)",
                super.getName(),
                this.getCartridge()
        );
    }

    @Override
    public String getLevel() {
        return "For civil";
    }

    @Override
    public String getYears() {
        return getYearsInUse() + " years in use";
    }
}
