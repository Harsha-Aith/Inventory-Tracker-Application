package ucf.assignments;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.function.Predicate;

import static org.junit.jupiter.api.Assertions.*;

class InventoryManagerTest
{
    InventoryManager manager = new InventoryManager();

    @Test
    void testAddItem()
    {
        // create a String variable to store a name intended to compare the actual with
        // call the addItem() method and store the description of the item in
        // use assertEquals to compare the two names
        Item t = new Item("XBOX One", "AXB124AXY3", "399.99");
        ObservableList<Item> actual = manager.addItem(t);
        ObservableList<Item> expected = FXCollections.observableArrayList(t);
        assertEquals(expected, actual);
    }

    @Test
    void testDeleteItem()
    {
        // create a String variable to store a name intended to compare the actual with
        // call the deleteItem() method and store the description of the item in
        // use assertEquals to compare the two names
        Item t = new Item("XBOX One", "AXB124AXY3", "399.99");
        ObservableList<Item> actual = manager.deleteItem(t);
        ObservableList<Item> expected = FXCollections.observableArrayList(t);
        expected.remove(t);
        assertEquals(expected, actual);
    }

    @Test
    void testClearAllItems()
    {
        ObservableList<Item> actual = FXCollections.observableArrayList(new Item("XBOX One", "AXB124AXY3", "399.99"));
        manager.clearAllItems();
    }

    @Test
    void testCheckDuplicates()
    {
        // create an observable list of items that are equal to the list in the InventoryManager class
        // create a boolean variable equal to the checkDuplicates method to check a duplicate of one of the serial numbers
        // assert equals true to boolean variable
        manager.inventoryItems = FXCollections.observableArrayList(new Item("XBOX One", "AXB124AXY3", "399.99"),
                new Item("PS4", "AXBCVB3456", "564.99"));
        boolean actual = manager.checkDuplicates("AXB124AXY3");
        assertEquals(true, actual);

    }

    @Test
    void testSearchFindsOrder()
    {
        // create a new item
        // create a boolean variable to call the searchFindsOrder function to find possible search text in the item
        // assert equals the expected value to the searchFindsOrder function
        Item item = new Item("XBOX One", "AXB124AXY3", "399.99");
        boolean actual = manager.searchFindsOrder(item, "XBOX One");
        assertEquals(true, actual);

    }


    void testSortItems()
    {
        // tableview sorts items by column
    }

}