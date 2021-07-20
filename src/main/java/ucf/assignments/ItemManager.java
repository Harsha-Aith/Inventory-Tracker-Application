package ucf.assignments;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.time.LocalDate;

public class ItemManager
{
    @FXML
    public TableView<Item> itemTable = new TableView<>();

    @FXML
    private TableColumn<Item, String> nameCol = new TableColumn("Name");
    @FXML
    private TableColumn<Item, LocalDate> serialNum = new TableColumn("Serial #");
    @FXML
    private TableColumn<Item, String> priceCol = new TableColumn("Value($)");

    // create add button, delete button, edit button, clear button, import and export list


    @FXML
    private Button addItemButton;
    @FXML
    private Button deleteItemButton;
    @FXML
    private Button clearButton;

}
