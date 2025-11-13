// remote example

import java.util.ArrayList;

interface Command{
    void execute();
    void undo();
}
class FanCommand implements Command{
    Fan f = new Fan();
    @Override
    public void execute() {
        f.On();
    }
    public void undo(){
        f.Off();
    }
}
class LightCommand implements Command{
    Light l = new Light();
    @Override
    public void execute() {
        l.On();
    }
    public void undo(){
        l.Off();
    }
}
class Fan {
    void On (){
        System.out.println("Fan is on");
    }
    void Off(){
        System.out.println("Fan is off");
    }
}
class Light {
    void On (){
        System.out.println("Light is on");
    }
    void Off(){
        System.out.println("Light is off");
    }
}
interface IRemote{
    Command[] buttons = new Command[3];
    boolean [] pressed = new boolean[3];
    void setCommand(int index , Command c);
    void pressButton(int index);
}
class Remote implements IRemote{
    Remote(){
        for(int i =0 ; i< 3 ;i++){
            buttons[i] = null;
            pressed[i] = false ;
        }
    }
    @Override
    public void setCommand(int index , Command c) {
        buttons[index] = c;
    }

    @Override
    public void pressButton(int index) {
        // check if button has a command
        if(buttons[index] == null){
            System.out.println("No command set for this button" + index);
        }else {
            if (pressed[index]) {
                buttons[index].undo();
                pressed[index] = false;
            } else {
                buttons[index].execute();
                pressed[index] = true;
            }
        }
    }
}
public class CommandDesignPattern {
    public static void main(String[] args) {
        IRemote r = new Remote();
        System.out.println("Pressing Fan button ------ ");
        r.setCommand(0 , new FanCommand());
        r.pressButton(0);
        r.pressButton(0);
        System.out.println("Pressing light button ------- ");
        r.setCommand(1 , new LightCommand());
        r.pressButton(1);
        r.pressButton(1);
        System.out.println("Pressing 3rd button -------");
        r.pressButton(2);

    }
}
