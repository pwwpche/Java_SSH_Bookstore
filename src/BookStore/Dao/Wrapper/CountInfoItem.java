package BookStore.Dao.Wrapper;

public class CountInfoItem{
    private String itemName;
    private double totalPrice;

    public void setName(String str){
        this.itemName = str;
    }

    public String getItemName(){
        return itemName;
    }

    public void setTotalPrice(double price){
        this.totalPrice = price;
    }

    public double getTotalPrice(){
        return this.totalPrice;
    }
}
