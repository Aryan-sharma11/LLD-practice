public class menuItems {
    String itemCode;
    String itemName;
    int price ;
    menuItems(String code , String name , int price ){
        this.itemCode = code ;
        this.itemName = name;
        this.price = price;
    }
    public String getCode(){
        return itemCode;
    }
    public String getName(){
        return itemName;
    }
    public int getPrice(){
        return price;
    }
    public void setCode(String code){
        itemCode = code;
    }
    public void setItemName(String name){
        itemName = name;
    }
    public void setPrice(int p){
        price = p;
    }
}
