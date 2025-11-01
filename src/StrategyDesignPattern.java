import java.util.SortedMap;

interface Talkable{
    void talk();
}
interface Walkable{
    void walk();
}
interface Flyable{
    void fly();
}

class NormalTalk implements Talkable{
    @Override
    public void talk() {
        System.out.println("Normal talk robot");
    }
}
class NoTalk implements Talkable{
    @Override
    public void talk() {
        System.out.println("This Robot cannot talk");
    }
}
class NormalWalk implements Walkable{
    @Override
    public void walk() {
        System.out.println("Normal walk robot");
    }
}
class NoWalk implements  Walkable{
    @Override
    public void walk() {
        System.out.println("This is no walk robot");
    }
}

class NormalFly implements Flyable {
    @Override
    public void fly() {
        System.out.println("Normal fly robot");
    }
}
class NoFly implements Flyable{
    @Override
    public void fly() {
        System.out.println("This is no fly robot");
    }
}

abstract class Robot {
    protected Walkable walkBehaviour;
    protected Flyable flyBehaviour;
    protected Talkable talkBehaviour;

    public Robot (Walkable w , Flyable f , Talkable t ){
        this.walkBehaviour = w;
        this.flyBehaviour = f;
        this.talkBehaviour = t;
    }
    public void walk(){
        walkBehaviour.walk();
    }
    public void fly(){
        flyBehaviour.fly();
    }
    public void talk(){
        talkBehaviour.talk();
    }
    public abstract void projection();
}
class CompanionRobot extends Robot{
    public CompanionRobot(Walkable w , Talkable t , Flyable f){
        super(w,f,t);
    }
    public void projection(){
        System.out.println("Displaying Companion robot features...");
    }
}
class WorkerRobot extends Robot{
    public WorkerRobot(Walkable w , Talkable t , Flyable f){
        super(w , f, t );
    }
    public void projection(){
        System.out.println("Displaying Worker Robot Features");
    }
}

public class StrategyDesignPattern {

    public static void main(String[] args) {
        Robot r1 = new CompanionRobot(new NormalWalk() ,new NormalTalk() , new NoFly());
        r1.walk();
        r1.talk();
        r1.fly();
        r1.projection();
        System.out.println("--------------------------");
        Robot r2 = new WorkerRobot(new NormalWalk() , new NoTalk() , new NormalFly());
        r2.walk();
        r2.talk();
        r2.fly();
        r2.projection();
    }

}
