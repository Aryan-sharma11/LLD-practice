
interface Burger {
    void prepare();
}
class PremiumBurger implements Burger{
    @Override
    public void prepare() {
        System.out.println("Preparing premium burger");
    }
}
class StandardBurger implements Burger{
    @Override
    public void prepare() {
        System.out.println("Preparing standard burger");
    }
}
class BasicBurger implements Burger{
    @Override
    public void prepare() {
        System.out.println("Preparing basic burger");
    }
}
class WheatPremiumBurger implements Burger{
    @Override
    public void prepare() {
        System.out.println("Preparing wheat premium burger");
    }
}
class WheatStandardBurger implements Burger{
    @Override
    public void prepare() {
        System.out.println("Preparing wheat standard burger");
    }
}
class WheatBasicBurger implements Burger{
    @Override
    public void prepare() {
        System.out.println("Preparing wheat basic burger");
    }
}
abstract class BurgerFactory{
    public abstract Burger createBurger(String type);

}
class BurgerKing extends BurgerFactory{

    public Burger createBurger(String type){
        return switch (type) {
            case "premium" -> new WheatPremiumBurger();
            case "basic" -> new WheatBasicBurger();
            case "standard" -> new WheatStandardBurger();
            default -> null;
        };
    }
}
class BurgerSingh extends BurgerFactory{

    public Burger createBurger(String type){
        return switch (type) {
            case "premium" -> new PremiumBurger();
            case "basic" -> new BasicBurger();
            case "standard" -> new StandardBurger();
            default -> null;
        };
    }
}
public class SimpleFactoryDesignPattern {

    public static void main(String[] args) {
       BurgerFactory f = new BurgerSingh();
       Burger b = f.createBurger("premium");
       if(b!=null){
           b.prepare();
       }
        BurgerFactory f1 = new BurgerKing();
        Burger b1 = f1.createBurger("premium");
        if(b1!=null){
            b1.prepare();
        }
    }

}
