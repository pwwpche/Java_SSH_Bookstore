package BookStore.Dao.Wrapper;

import BookStore.Entity.OrderbookEntity;
import BookStore.Entity.OrdersEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by pwwpche on 2014/6/12.
 *
 */
public class CountInfoWrapper {
    private String itemName;
    private String itemString;

    private List<CountInfoItem> items;

    public CountInfoWrapper(){
        itemName = "";
        itemString = "";
        items = new ArrayList<CountInfoItem>();
    }

    public void setItemName(String str){
        itemName = str;
    }
    public String getItemName(){
        return itemName;
    }

    public String getItemString(){
        return itemString;
    }

    public void setItemString(String str){
        itemString = str;
    }

    public void  setItems(List<CountInfoItem> stringList){
        items = stringList;
    }
    public List<CountInfoItem>getItems(){
        return items;
    }


    public void insertPair(String name, Double price){
        CountInfoItem item = new CountInfoItem();
        item.setName(name);
        item.setTotalPrice(price);
        items.add(item);
    }
}

;