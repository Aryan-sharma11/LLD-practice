
abstract class Handler {
    protected Handler nextHandler;

    public void setNextHandler(Handler nextHandler) {
        this.nextHandler = nextHandler;
    }

    public abstract void handleRequest(int amount);
}

class ThousandsHandler extends Handler {
    private int numberOfBills;

    public ThousandsHandler(int numberOfBills) {
        this.numberOfBills = numberOfBills;
    }

    @Override
    public void handleRequest(int amount) {

        if (amount >= 1000) {
            int count = amount / 1000;
            if(count > numberOfBills && numberOfBills > 0){
                count = numberOfBills;
            }
            numberOfBills -= count;
            System.out.println("Dispensing " + count + " thousand-dollar bills");
            amount = amount - (count * 1000);
        }
        if (nextHandler != null) {
            nextHandler.handleRequest(amount);
        } else {
            if(amount > 0){
                System.out.println("Remaining amount " + amount + " cannot be dispensed in available denominations.");
            }
        }
    }
}
class FiveHundredsHandler extends Handler{
    private int numberOfBills;

    public FiveHundredsHandler(int numberOfBills) {
        this.numberOfBills = numberOfBills;
    }
    @Override
    public void handleRequest(int amount){
        if(amount >= 500 && numberOfBills > 0){
            int count = amount / 500;
            if (count > numberOfBills){
                count = numberOfBills;
            }
            numberOfBills -= count;
            System.out.println("Dispensing " + count + " five-hundred-dollar bills");
            amount = amount - (count * 500);
        }
        if(nextHandler != null){
            nextHandler.handleRequest(amount);
        }else {
            if(amount > 0){
                System.out.println("Remaining amount " + amount + " cannot be dispensed in available denominations.");
            }
        }
    }
}
class HundredsHandler extends Handler{
    private int numberOfBills;
    public HundredsHandler(int numberOfBills) {
        this.numberOfBills = numberOfBills;
    }
    @Override
    public void handleRequest(int amount){
        if(amount >= 100 && numberOfBills > 0){
            int count = amount / 100;
            if(count > numberOfBills){
                count = numberOfBills;
            }
            numberOfBills -= count;
            System.out.println("Dispensing " + count + " hundred-dollar bills");
            amount = amount - (count * 100);
        }
        if(nextHandler != null){
            nextHandler.handleRequest(amount);
        } else {
            if(amount > 0){
                System.out.println("Remaining amount " + amount + " cannot be dispensed in available denominations.");
            }
        }
    }
}




public class ChainOfResponisbilityDesignPattern {
    public static void main(String[] args) {
        Handler thousandsHandler = new ThousandsHandler(5);
        Handler fiveHundredsHandler = new FiveHundredsHandler(10);
        Handler hundredsHandler = new HundredsHandler(4);

        thousandsHandler.setNextHandler(fiveHundredsHandler);
        fiveHundredsHandler.setNextHandler(hundredsHandler);

        int amountToDispense = 12000;
        System.out.println("Requesting to dispense amount: " + amountToDispense);
        thousandsHandler.handleRequest(amountToDispense);
    }



}
