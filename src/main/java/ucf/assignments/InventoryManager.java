package ucf.assignments;

import javafx.beans.InvalidationListener;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableSet;
import javafx.collections.SetChangeListener;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;

import java.time.LocalDate;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class InventoryManager
{

    @FXML
    private String name;
    @FXML
    private String serialNum;
    @FXML
    private double price;

    //@FXML
    //private Set<Item> inventoryItems = FXCollections.observableSet();
     ObservableList <Item> inventoryItems = FXCollections.<Item>observableArrayList();

    @FXML
    public ObservableList<Item> addItem(Item item)
    {
        inventoryItems.add(item);
        return inventoryItems;
    }

    @FXML
    public ObservableList<Item> deleteItem(Item item)
    {
        inventoryItems.remove(item);
        return inventoryItems;
    }

    @FXML
    public void clearAllItems()
    {
        inventoryItems.clear();
    }


    public boolean checkDuplicates(String serial)
    {
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

}
