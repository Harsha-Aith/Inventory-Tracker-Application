package ucf.assignments;

import javafx.beans.InvalidationListener;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableSet;
import javafx.collections.SetChangeListener;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;

import java.time.LocalDate;
import java.util.*;
import java.util.function.Predicate;

public class InventoryManager
{

    @FXML
    public String name;
    @FXML
    public String serialNum;
    @FXML
    public String price;

   // create an observable list for the items and a filteredlist to store the search data
   public ObservableList <Item> inventoryItems = FXCollections.<Item>observableArrayList();
    FilteredList<Item> filteredData = new FilteredList<>(inventoryItems, b -> true);

    @FXML
    public ObservableList<Item> addItem(Item item)
    {
        // add the item to the list and return the list
        inventoryItems.add(item);
        return inventoryItems;
    }

    @FXML
    public ObservableList<Item> deleteItem(Item item)
    {
        // remove the item from the list and return the list
        inventoryItems.remove(item);
        return inventoryItems;
    }

    @FXML
    public void clearAllItems()
    {
        // clear all items in the list
        inventoryItems.clear();
    }

    public ObservableList<Item> getList()
    {
        return inventoryItems;
    }


    public boolean checkDuplicates(String serial)
    {
        // create a for loop to go through the list
        // if the serial number being entered in the textfield is the same as any of the serial numbers
        // return true
        // else return false
        for(int i = 0; i < inventoryItems.size(); i++)
        {
            System.out.println("Size: " + inventoryItems.size());
            if(serial.equals(inventoryItems.get(i).getSerialNum())) {
                System.out.println("ITS THE SAME " + inventoryItems.get(i).getSerialNum());
                return true;
            }
        }
        return false;
    }

    public boolean searchFindsOrder(Item item, String searchText)
    {
       // if the text in the search bar is null ot empty
        // return true
       //if the characters of the name searched in the bar has the same characters as what is in the item
        // return true
        // if the characters of the name searched in the bar has the same characters as what is in the item
         // return true
        // if the characters of the name searched in the bar has the same characters as what is in the item
         // return true
        // if all of those are not true, return false
            if (searchText == null || searchText.isEmpty())
                return true;
            if (item.getName().toLowerCase().indexOf(searchText.toLowerCase()) != -1)
                return true;
            else if (item.getSerialNum().toLowerCase().indexOf(searchText.toLowerCase()) != -1)
                return true;
            if (String.valueOf(item.getPrice()).toLowerCase().indexOf(searchText.toLowerCase()) != -1)
                return true;
            return false;
    }



    public Predicate<Item> createPredicate(String searchText)
    {
        // create a lamda expression to check the search box to check the filtered list
        // return a call to the search order function that searches for corresponding text in the each item
        return item -> {
            if (searchText == null || searchText.isEmpty()) return true;
            return searchFindsOrder(item, searchText);
        };
    }


}
