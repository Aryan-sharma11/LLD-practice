import java.util.ArrayList;

public class restaurant {
    static int nextRestId;
    int restaurantId;
    String name ="";
    String location ="";
    ArrayList<menuItems> items ;
    public restaurant(String name , String location){
        this.name = name;
        this.location = location;
        this.restaurantId = ++nextRestId;
    }
    public String getName(){
        return name;
    }
    public String getLocation(){
        return location;
    }
    public void setName(String n ){
        name = n;
    }
    public void setLocation(String n){
        location = n;
    }
    public void addMenuItem(menuItems item){
        items.add(item);
    }
    public ArrayList<menuItems> getMenu(){
        return items;
    }
}

