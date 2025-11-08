import java.util.ArrayList;

public class cart {
    ArrayList<menuItems> items = new ArrayList<>();
    restaurant restaurant;

    cart(){
        restaurant = null;
    }
    public void addItem(menuItems item){
        if(restaurant == null){
            System.err.println("Restraunt not set");
        }
        items.add(item);
    }
    public int getTotalCost(){
        int sum=0;
        for(menuItems m : items){
            sum = sum+m.price;
        }
        return sum;
    }
    public boolean isEmpty(){
        return (restaurant == null || items.isEmpty());
    }
    public void clear (){
        items.clear();
        restaurant = null;
    }
    public void setRestaurant(restaurant r){
        restaurant = r;
    }

    public restaurant getRestaurant() {
        return restaurant;
    }
    public ArrayList<menuItems> getItems(){
        return items;
    }
}
