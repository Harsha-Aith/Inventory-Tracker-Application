package ucf.assignments;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class InventoryManagerTest
{
    InventoryManager manager = new InventoryManager();

    @Test
    void addItem()
    {
        // create a String variable to store a name intended to compare the actual with
        // call the addItem() method and store the description of the item in
        // use assertEquals to compare the two names
        Item t = new Item("XBOX One", "AXB124AXY3", 399.99);
        ObservableList<Item> actual = manager.addItem(t);
        ObservableList<Item> expected = FXCollections.observableArrayList(t);
        assertEquals(expected, actual);
    }

    @Test
    void deleteItem()
    {
        // create a String variable to store a name intended to compare the actual with
        // call the deleteItem() method and store the description of the item in
        // use assertEquals to compare the two names
        Item t = new Item("XBOX One", "AXB124AXY3", 399.99);
        ObservableList<Item> actual = manager.deleteItem(t);
        ObservableList<Item> expected = FXCollections.observableArrayList(t);
        expected.remove(t);
        assertEquals(expected, actual);
    }

    @Test
    void clearAllItems() {
    }

    @Test
    void checkDuplicates() {
    }
}