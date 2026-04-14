package DiscountCoupon;

import java.util.*;
import java.util.concurrent.locks.*;

class Product {
    String name;
    String category;
    double price;
    Product(String name , String category , double price){
        this.name = name; 
        this.category = category;
        this.price = price;
    }
}
class CartItem {
    Product product;
    int quantity;
    CartItem(Product p , int quantity){
        this.product = p;
        this.quantity = quantity;
    }
    public double itemTotal(){
        return product.price*quantity;
    }
}
class  Cart {
   private ArrayList<CartItem> items;
   private boolean loyaltyMember;
   private double originalTotal;
   private double currentTotal;
   private String paymentBank;
   
   Cart(){
    items = new ArrayList<>();
        this.loyaltyMember = false;
        this.paymentBank   = "";
   }
   void addProduct(Product p , int quantity){
        CartItem item = new CartItem(p , quantity);
        items.add(item);
        this.originalTotal +=item.itemTotal();
        this.currentTotal += item.itemTotal();
   } 
   double getOriginalTotal(){
        return originalTotal;
   }
   double getCurrentTotal(){
        return currentTotal;
   }
   void applyDiscount(double amt){
        currentTotal -= amt;
        if(currentTotal <0){
            currentTotal = 0;
        }
   }
   void setLoyaltyMember(boolean member){
        this.loyaltyMember = member;
   }
   void setPaymentBank(String paymentBank){
        this.paymentBank = paymentBank;
   }
   boolean isLoyalMember(){
        return loyaltyMember;
   }
   String getPaymentBank(){
        return paymentBank;
   }

   public ArrayList<CartItem> getItems(){
        return items;
   }

}

enum StrategyType{
    FLAT,
    PERCENT,
    PERCENT_WITH_CAP,
}
interface DiscountStrategy{
    double calculate(double amount);
}

class FlatDiscount implements DiscountStrategy{
    double amount;
    
    FlatDiscount(double amount){
        this.amount = amount;
    }
    @Override
    public double calculate(double baseAmount){

        return Math.min(amount , baseAmount);
    }
}
class PercentDiscount implements DiscountStrategy{
    double percentage;
    
    PercentDiscount(double pct){
        this.percentage = pct;
    }

    @Override
    public double calculate(double amount){
        return (amount/100)*percentage;
    }
}
class PercentDiscountWithCap implements DiscountStrategy{
    double percent;
    double cap;

    PercentDiscountWithCap(double pct , double cap){
        this.percent = pct;
        this.cap = cap;
    }

    @Override 
    public double calculate (double amount){
        double pctDisc =  (amount/100)*percent ;
        return Math.min(pctDisc , cap);
    }
}


class DiscountStrategyManager{
    
    protected static final DiscountStrategyManager ds = new DiscountStrategyManager();
    private DiscountStrategyManager(){}
    
    public static DiscountStrategyManager getStrategyManager(){
        return ds;
    }
    public DiscountStrategy getStrategyType(StrategyType st , double amount , double percent){

        switch(st){
            case FLAT:
                return new FlatDiscount(amount);
            case PERCENT:
                return new PercentDiscount(percent);
            case PERCENT_WITH_CAP:
                return new PercentDiscountWithCap(percent , amount);
            default:
                System.out.println("Invalid Strategy");
                return null;
        }
    }
}

abstract class Coupon{
    Coupon next; // chain of responsibility
    boolean combine;

    Coupon(){
        next = null;
    }
    
    void setNext(Coupon next){
        this.next = next;
    }
    Coupon getNext(){
        return next;
    }
    void setCombine(boolean combine){
        this.combine = combine;
    }


    void applyDiscount(Cart cart){
        if(isApplicable(cart)){
            double discount = getDiscount(cart);
            cart.applyDiscount(discount);
            System.out.println(name()+"Discount Applied "+ discount);

            if(!isCombinable()){
                return;
            }
            if(next!=null ){
                next.applyDiscount(cart);
            }
        }
    }
    protected abstract boolean isApplicable(Cart c);
    protected abstract String name();

    protected boolean isCombinable(){
        return combine;
    }
    public double getDiscount(Cart c){
        // Template design Pattern
        // check is Applicable 
        // call apply discount 
        // return amount;
        return 0.0;
    }
}

class SeasonalDiscount extends Coupon{
    private double percent;
    String category;
    DiscountStrategy ds;

    SeasonalDiscount(double percent , String category , boolean combine){
        this.percent = percent;
        this.category = category;
        super.combine = combine;
        this.ds = DiscountStrategyManager.getStrategyManager().getStrategyType(StrategyType.PERCENT, 0, percent);
    }

    @Override 
    protected boolean isApplicable(Cart c){
        for(CartItem itm : c.getItems()){
            if(itm.product.category.equals(category)){
                return true;
            }
        }
        return false;
    }

    @Override 
    protected String name(){
        return "Seasonal Offer " + (int)percent + "% off " + category;
    }

    @Override 
    public double getDiscount(Cart c){
        double subtotal = 0.0;
        for (CartItem item : c.getItems()) {
            if (item.product.category.equals(category)) {
                subtotal += item.itemTotal();
            }
        }
        return ds.calculate(subtotal);
    }
}
class LoyalityDiscount extends Coupon{
    private double percent;
    DiscountStrategy ds;

    LoyalityDiscount(double percent , boolean combine){
        this.percent = percent;
        this.combine = combine;
        this.ds = DiscountStrategyManager.getStrategyManager().getStrategyType(StrategyType.PERCENT, 0.0, percent);
    }

    @Override 
    protected boolean isApplicable(Cart c){
        return c.isLoyalMember();
    }

    @Override 
    public double getDiscount(Cart c){
        return ds.calculate(c.getCurrentTotal());
    }

    @Override 
    protected String name(){
        return "Loyal Member Discount " + (int)percent + "% off ";
    }

}
class BulkPurchaseDiscount extends Coupon{
    private double threshold;
    private double flatOff;
    
    DiscountStrategy ds;

    BulkPurchaseDiscount(double threshold , double flatoff , boolean combine){
        this.threshold = threshold;
        this.flatOff = flatoff;
        this.combine = combine;
        this.ds = DiscountStrategyManager.getStrategyManager().getStrategyType(StrategyType.FLAT, flatoff, 0.0);
    }

    @Override 
    protected boolean isApplicable(Cart c){
        if(c.getOriginalTotal() >= threshold){
            return true;
        }
        return false;
    }
    @Override 
    public double getDiscount(Cart c){
        return ds.calculate(c.getCurrentTotal());
    }

    @Override 
    protected String name(){
        return "Bulk order Discount " + (int)flatOff + "off ";
    }
}
class BankCouponDiscount extends Coupon{
    private double percent;
    String bank;
    private double minSpend;
    private double offCap;
    DiscountStrategy ds;

    BankCouponDiscount(String bank , double pct , double minSpend , double offCap , boolean combine){
        this.bank = bank;
        this.percent = pct;
        this.minSpend = minSpend;
        this.offCap = offCap;
        this.combine = combine;
        this.ds = DiscountStrategyManager.getStrategyManager().getStrategyType(StrategyType.PERCENT_WITH_CAP, offCap, percent);

    }


    @Override 
    protected boolean isApplicable(Cart c){
        return c.getCurrentTotal()>=minSpend;
    }

    @Override 
    public double getDiscount(Cart c){
        return ds.calculate(c.getCurrentTotal());
    }

    @Override 
    protected String name(){
        return bank + "Bank Discount " + (int)percent + " off upto "+ (int)offCap;
    }
}

class CouponManager{
    private static CouponManager cm;
    Coupon head;
    public static final Lock lock = new ReentrantLock();

    private CouponManager(){
            head = null;

    }

    public static synchronized CouponManager getCouponManager(){
        if (cm == null) {
            cm = new CouponManager();
        }
        return cm;

    }
    public void registerCoupon(Coupon c){
        lock.lock();        
        try {
            if (head == null) {
                head = c;
            } else {
                Coupon cur = head;
                while (cur.getNext() != null) {
                    cur = cur.getNext();
                }
                cur.setNext(c);
            }
        } finally {
            lock.unlock();
        }


    }
    public double applyAllCoupon(Cart cart){
        lock.lock();
        try {
            if (head != null) {
                head.applyDiscount(cart);
            }
            return cart.getCurrentTotal();
        } finally {
            lock.unlock();
        }
    }

    public List<String> getApplicableCoupon(Cart cart){
        lock.lock();
            try {
                List<String> res = new ArrayList<>();
                Coupon cur = head;
                while (cur != null) {
                    if (cur.isApplicable(cart)) {
                        res.add(cur.name());
                    }
                    cur = cur.getNext();
                }
                return res;
            } finally {
                lock.unlock();
            }   
    }
}


public class DiscountCoupon {
   public static void main(String[] args) {
        CouponManager mgr = CouponManager.getCouponManager();
        mgr.registerCoupon(new SeasonalDiscount(10, "Clothing", true));
        mgr.registerCoupon(new LoyalityDiscount(5 , true));    
        mgr.registerCoupon(new BulkPurchaseDiscount(1000, 100, true));
        mgr.registerCoupon(new BankCouponDiscount("ABC", 15, 2000, 500,false));

        Product p1 = new Product("Winter Jacket", "Clothing", 1000);
        Product p2 = new Product("Smartphone", "Electronics", 20000);
        Product p3 = new Product("Jeans", "Clothing", 1000);
        Product p4 = new Product("Headphones", "Electronics", 2000);

        Cart cart = new Cart();
        cart.addProduct(p1, 1);
        cart.addProduct(p2, 1);
        cart.addProduct(p3, 2);
        cart.addProduct(p4, 1);
        cart.setLoyaltyMember(true);
        cart.setPaymentBank("ABC");

        System.out.println("Original Cart Total: " + cart.getOriginalTotal() + " Rs");

        List<String> applicable = mgr.getApplicableCoupon(cart);
        System.out.println("Applicable Coupons:");
        for (String name : applicable) {
            System.out.println(" - " + name);
        }

        double finalTotal = mgr.applyAllCoupon(cart);
        System.out.println("Final Cart Total after discounts: " + finalTotal + " Rs");
    }
}
