package ucf.assignments;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class ManageFilesTest {

    @Test
    void getFileExtension()
    {
        // create a manage files object
        // create a string with the expected extension name
        // create a string calling the file extension method
        // assert equals the expected to actual extension
        ManageFiles manageFiles = new ManageFiles();
        String exp = "txt";
        String actual = manageFiles.getFileExtension(new File("src/test/java/ucf/assignments/NAT.txt"));
        assertEquals(exp, actual);

    }

    @Test
    void exportToTSV()
    {
            // create a manage files object
            // create new item, expected
            // create a string for the actual value
            // use assert equals to compare the two values
            ManageFiles manageFiles = new ManageFiles();
            Item t =  new Item("Eat Grass", "asdfghjkl9", "12");
            String text = manageFiles.exportToTSV(t);
            String exp = "Eat Grass\tasdfghjkl9\t12\n";
            assertEquals(exp, text);

    }

    @Test
    void exportToHTML()
    {
        // create a manage files object
        // create a String variable that is the contents of the file
        // create a new String variable and initialize it by calling the exportToHTML() function from the ManageFiles class
        // use assertEquals() to compare if the file contents are the same
        ManageFiles manageFiles = new ManageFiles();
        String html = "";
        Item item =  new Item("Eat Grass", "asdfghjklo", "");
        html += "<tr>\n" +
                "\t<td>Eat Grass</td><td>asdfghjklo</td>\n" +
                "<td></th>\n";
        String text = manageFiles.exportToHTML(item);
        assertEquals(html, text);


    }

    @Test
    void exportToJSON()
    {
        // create a manage files object
        // create a String variable that is the contents of the file
        // create a new String variable and initialize it by calling the exportTojson() function from the ManageFiles class
        // use assertEquals() to compare if the file contents are the same
        ManageFiles manageFiles = new ManageFiles();
        String json = "";
        Item item =  new Item("VAD", "ZXC456VB78", "800");
        json += "{\n\t\t \"itemName\":\"" + "VAD" + "\"," + "\n\t\t \"itemSerialNum\":\"" + "ZXC456VB78" + "\","
        + "\n\t\t \"itemPrice\":\"" + "800" + "\"\n}";;
        String text = manageFiles.exportToJSON(item);
        assertEquals(json, text);

    }

    @Test
    void importTSV() throws IOException {
        // create a new manager
        // fill up manager with items
        // create a list of items and a manage files
        // create a new file, call the import tsv function
        // call the import tsv function to the second manager
        // compare the contents of both managers
        InventoryManager manager = new InventoryManager();
        manager.addItem(new Item("sad", "ASDF4567J8", "100"));
        ObservableList<Item> manager2 = FXCollections.observableArrayList();
        ManageFiles files = new ManageFiles();
        manager2 = files.importTSV(new File("src/test/java/ucf/assignments/NAT.txt"));
        for (int i = 0; i < manager.getList().size(); i++) {

            assertEquals(manager.getList().get(i).getName(), manager2.get(i).getName());
        }
    }

    @Test
    void importHTML() throws IOException {
        // create a new manager
        // fill up manager with items
        // create a list of items and a manage files
        // create a new file, call the import html function
        // call the import html function to the second manager
        // compare the contents of both managers
        InventoryManager manager = new InventoryManager();
        manager.addItem(new Item("chilsom", "1234567884", "1.23"));
        manager.addItem(new Item("jazz", "1234567898", "1345"));
        ObservableList<Item> manager2 = FXCollections.observableArrayList();
        ManageFiles files = new ManageFiles();
        manager2 = files.importHTML(new File("src/test/java/ucf/assignments/jazz.html"));
        for (int i = 0; i < manager.getList().size(); i++) {

            assertEquals(manager.getList().get(i).getName(), manager2.get(i).getName());
        }

    }

    @Test
    void importJSON() throws FileNotFoundException {
        // create a new manager
        // fill up manager with items
        // create a list of items and a manage files
        // create a new file, call the import html function
        // call the import html function to the second manager
        // compare the contents of both managers
        InventoryManager manager = new InventoryManager();
        Item item =  new Item("VAD", "ZXC456VB78", "800");
        ObservableList<Item> manager2 = FXCollections.observableArrayList();
        ManageFiles files = new ManageFiles();
        manager2 = files.importJSON(new File("src/test/java/ucf/assignments/BADDING.json"));
        for (int i = 0; i < manager.getList().size(); i++) {

            assertEquals(manager.getList().get(i).getName(), manager2.get(i).getName());
        }

    }
}