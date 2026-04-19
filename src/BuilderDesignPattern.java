// example of builder design pattern

abstract class Car {

    Engine engine; 

    public abstract void drive();

}
class SUV extends Car{

    SUV(Engine e){
        this.engine  = e;
    }
    @Override
    public void drive(){
        System.out.println("Starting SUV...");
        engine.start();
    }

}
class SEDAN extends Car{

    SEDAN(Engine e){
        this.engine  = e;
    }
    @Override
    public void drive(){
        System.out.println("Starting Sedan...");
        engine.start();
    }

}
class Hatchback extends Car{

    Hatchback(Engine e){
        this.engine  = e;
    }
    @Override
    public void drive(){
        System.out.println("Starting Hatchback...");
        engine.start();
    }

}

abstract class Engine{

    public abstract void start();

}

class Petrol extends Engine{

    @Override
    public void start(){
        System.out.println("Petrol Engine Started vroooom");
    }
}
class Diesel extends Engine{
    @Override
    public void start (){
        System.out.println("Diesel Engine Started vroooooom");
    }
}
class Electric extends Engine{
    @Override
    public void start(){
        System.out.println("Electric engine started shhhhhhh");
    }
}


public class BuilderDesignPattern {
    public static void main(String[] args) {
        Car c1 = new SUV(new Electric());
        Car c2 = new SEDAN(new Diesel());
        Car c3 = new Hatchback(new Petrol());

        c1.drive();
        c2.drive();
        c3.drive();

    }

}
