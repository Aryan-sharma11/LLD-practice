import java.lang.reflect.Proxy;

enum GatewayType {
    PAYTM,
    RAZORPAY,
}
class PaymentRequest {
    String sender;
    String receiver;
    double amount;
    String currency;
    
    PaymentRequest(String Sender, String Receiver, double Amount, String Currency) {
        this.sender = Sender;
        this.receiver = Receiver;
        this.amount = Amount;
        this.currency = Currency;
    }
}
interface BankingSystem{
   boolean processPayment(PaymentRequest request);
}
class RazorPayBankingSystem implements BankingSystem{
    @Override
    public boolean processPayment(PaymentRequest request) {
        // Implement RazorPay specific payment processing logic here
        System.out.println("Processing payment through RazorPay for " + request.amount + " " + request.currency);
        
        return Math.random()<0.7; // Simulate successful payment
    }
}
class PaytmBankingSystem implements BankingSystem{
    @Override
    public boolean processPayment(PaymentRequest request) {
        // Implement Paytm specific payment processing logic here
        System.out.println("Processing payment through Paytm for " + request.amount + " " + request.currency);
        return Math.random()<0.9; // Simulate successful payment
    }
}

abstract class PaymentGateway{
    protected BankingSystem bs;
    public PaymentGateway(){
        this.bs = null;
    }
    boolean processPayment(PaymentRequest request){
        if(bs == null){
            System.out.println("No banking system configured.");
            return false;
        }
        if(!validatePayment(request)){
            System.out.println("Payment validation failed.");
            return false;
        }        if(!initiatePayment(request)){
            System.out.println("Payment initiation failed.");
            return false;
        }
        if(!confirmPayment(request)){
            System.out.println("Payment confirmation failed.");
            return false;
        }
        return true;
    }
    protected abstract boolean validatePayment(PaymentRequest request);
    protected abstract boolean initiatePayment(PaymentRequest request);
    protected abstract boolean confirmPayment(PaymentRequest request);
}

class ProxyPaymentGateway extends PaymentGateway{
    protected PaymentGateway realPg;
    protected int maxRetries;
    
    public ProxyPaymentGateway(PaymentGateway realPg, int maxRetries) {
        this.realPg = realPg;
        this.maxRetries = maxRetries;
    }
    @Override
    boolean processPayment(PaymentRequest request) {
        boolean result = false;
        for(int attempt = 0 ; attempt < maxRetries ; attempt++){
            if (attempt > 0){
                System.out.println("[Proxy] Retrying payment (attempt " + (attempt+1)+ ") for " + request.sender + ".");
            }
            result = realPg.processPayment(request);
            if (result){
                System.out.println("Payment successful");
               break;
            }
        }
        return result;
    }

    @Override
    protected boolean initiatePayment(PaymentRequest req){
        return realPg.initiatePayment(req);
    }
    @Override
    protected boolean validatePayment(PaymentRequest req){
        return realPg.validatePayment(req);
    }
    @Override
    protected boolean confirmPayment(PaymentRequest req){
        return realPg.confirmPayment(req);
    }
}
class PaytmPaymentGateway extends PaymentGateway{
    public PaytmPaymentGateway(){
        this.bs = new PaytmBankingSystem();
    }
    @Override
    protected boolean validatePayment(PaymentRequest request) {
        // Implement Paytm specific payment validation logic here
        System.out.println("Validating payment for " + request.sender + " through Paytm.");
        return true; // Simulate successful validation
    }

    @Override
    protected boolean initiatePayment(PaymentRequest request) {
        // Implement Paytm specific payment initiation logic here
        System.out.println("Initiating payment for " + request.sender + " through Paytm.");
        return bs.processPayment(request); // Simulate payment processing
    }

    @Override
    protected boolean confirmPayment(PaymentRequest request) {
        // Implement Paytm specific payment confirmation logic here
        System.out.println("Confirming payment for " + request.sender + " through Paytm.");   
     return true; // Simulate successful confirmation
    }

}

class RazorPayPaymentGateway extends PaymentGateway{
    public RazorPayPaymentGateway(){
        this.bs = new RazorPayBankingSystem();
    }
    @Override
    protected boolean validatePayment(PaymentRequest request) {
        // Implement Paytm specific payment validation logic here
        System.out.println("Validating payment for " + request.sender + " through Razorpay.");
        return true; // Simulate successful validation
    }

    @Override
    protected boolean initiatePayment(PaymentRequest request) {
        // Implement Paytm specific payment initiation logic here
        System.out.println("Initiating payment for " + request.sender + " through RazorPay.");
        return bs.processPayment(request); // Simulate payment processing
    }

    @Override
    protected boolean confirmPayment(PaymentRequest request) {
        // Implement Paytm specific payment confirmation logic here
        System.out.println("Confirming payment for " + request.sender + " through RazorPay.");   
     return true; // Simulate successful confirmation
    }

}

class PaymentService {
   PaymentGateway pg;
   private static final PaymentService ps = new PaymentService() ;
   private PaymentService(){
    this.pg = null;
   }

   public static PaymentService getService(){
        return ps;
   }
   public void setGateway(PaymentGateway pg){
    this.pg = pg;
   }

    public boolean processPayment(PaymentRequest req){
        // TODO
        if(pg == null){
            System.out.println("Payment gateway not instantiated");
            return false;
        } else {
            return pg.processPayment(req);
        }
    }
}
class GatewayFactory {

    public static final GatewayFactory gf = new GatewayFactory();
    private GatewayFactory(){}
    public PaymentGateway getFactory(GatewayType gt){
        switch(gt){
            case PAYTM:
                return new ProxyPaymentGateway(new PaytmPaymentGateway(), 3);
            case RAZORPAY:
                return new ProxyPaymentGateway(new RazorPayPaymentGateway(), 10) ;
            default:
                System.out.println("Invalid gateway type");
                return null;
        }
    }

}
class PaymentController {
    PaymentService ps;
    private static final PaymentController pc = new PaymentController();

    private PaymentController(){
        this.ps = null;
    }

    public static PaymentController getPaymentHandler(){
        return pc;
    }
    public boolean handlePayment(GatewayType gt , PaymentRequest req){

        PaymentGateway pg = GatewayFactory.gf.getFactory(gt);
        PaymentService.getService().setGateway(pg);
        return PaymentService.getService().processPayment(req);
    }
    
}
public class PaymentGatewayApplication {
    public static void main(String[] args) {
        PaymentController pc = PaymentController.getPaymentHandler();
        PaymentRequest req1 = new PaymentRequest("Alice", "Bob", 100.0, "USD");
        boolean result1 = pc.handlePayment(GatewayType.PAYTM, req1);
        System.out.println("Payment result: " + result1);

        PaymentRequest req2 = new PaymentRequest("Charlie", "Dave", 200.0, "USD");
        boolean result2 = pc.handlePayment(GatewayType.RAZORPAY, req2);
        System.out.println("Payment result: " + result2);
    }
}



