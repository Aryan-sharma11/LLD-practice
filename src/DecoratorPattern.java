import org.w3c.dom.ls.LSOutput;

interface Character {
    String getAbilities();
}
class Mario implements Character{
    @Override
    public String getAbilities() {
        return "Mario";
    }
}
abstract class Decorator implements Character{
    Character ch = null;
    Decorator(Character c){
        ch = c;
    }
}

class GrowupAbilities extends Decorator{
    GrowupAbilities(Character c){
        super(c);
    }

    @Override
    public String getAbilities() {
        return ch.getAbilities()+"with Growup";
    }
}
class HeightupAbilities extends Decorator{
    HeightupAbilities(Character c){
        super(c);
    }

    @Override
    public String getAbilities() {
        return ch.getAbilities()+"with Heightup";
    }
}
class FireupAbilities extends Decorator{
    FireupAbilities(Character c){
        super(c);
    }

    @Override
    public String getAbilities() {
        return ch.getAbilities()+"with Fireup";
    }
}

public class DecoratorPattern {
    public static void main(String[] args) {
        Character mario = new Mario();
        System.out.println(mario.getAbilities());
        mario = new HeightupAbilities(mario);
        System.out.println(mario.getAbilities());
        mario = new GrowupAbilities(mario);
        System.out.println(mario.getAbilities());

    }

}
