import java.util.ArrayList;

public class restManager {
    ArrayList<restaurant> restaurantList;
    restManager(){
        restaurantList = new ArrayList<>();
    }

    void addRestaurant(String restaurantId, String name , String location){
        restaurant r1 = new restaurant(restaurantId,name);
        restaurantList.add(r1);
    }

}
