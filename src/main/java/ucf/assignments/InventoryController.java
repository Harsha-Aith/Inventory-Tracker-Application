package ucf.assignments;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import javafx.util.converter.DoubleStringConverter;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

public class InventoryController implements Initializable
{
    @FXML
    public TableView<Item> inventoryTable = new TableView<>();

    @FXML
    private TableColumn<Item, String> nameCol = new TableColumn("Name");
    @FXML
    private TableColumn<Item, String> serialNumCol = new TableColumn("Serial Number");
    @FXML
    private TableColumn<Item, Double> priceCol = new TableColumn("Value");

    // create add button, delete button, edit button, clear button, import and export list


    @FXML
    private Button addItemButton;
    @FXML
    private Button deleteItemButton;
    @FXML
    private Button clearButton;
   /* @FXML
    private String name;
    @FXML
    private String serialNum;
    @FXML
    private Double price;

    */

    @FXML
    private TextField name;
    @FXML
    private TextField serialNum;
    @FXML
    private TextField price;

    private String nameVal;
    private String serialNumVal;
    private Double priceVal;

    Stage p = new Stage();

    InventoryManager itemManager = new InventoryManager();

    ObservableList <Item> inventoryItems2 = FXCollections.<Item>observableArrayList(new Item("Jacob", "Smith", 90));

    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
        nameCol.setCellFactory(TextFieldTableCell.forTableColumn());
        nameCol.setCellValueFactory((new PropertyValueFactory<>("name")));

        serialNumCol.setCellFactory(TextFieldTableCell.forTableColumn());
        serialNumCol.setCellValueFactory((new PropertyValueFactory<>("serialNum")));

        DoubleStringConverter d = new DoubleStringConverter();
        TextFieldTableCell cell = new TextFieldTableCell();
        priceCol.setCellFactory(TextFieldTableCell.forTableColumn(new StringConverter<Double>() {
            @Override
            public String toString(Double object) {
                return String.valueOf(object);
            }

            @Override
            public Double fromString(String string) {
                return null;
            }
        }));
        priceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
        inventoryTable.setEditable(true);
        inventoryTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

    }

    @FXML
    public void addItemClicked(ActionEvent event) throws IOException
    {
        if(event.getSource() == addItemButton)
        {

            Item item = new Item(name.getText(), serialNum.getText(), Double.parseDouble(price.getText()));
            nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
            serialNumCol.setCellValueFactory(new PropertyValueFactory<>("serialNum"));
            priceCol.setCellValueFactory(new PropertyValueFactory<Item, Double>("price"));
            //p = (Stage)anchorPane.getScene().getWindow();
            Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("Inventory.fxml"));
            Scene scene = new Scene(root);
            p.setScene(scene);
            Alert alert = new Alert(Alert.AlertType.ERROR, "");
            alert.initModality(Modality.APPLICATION_MODAL);
            alert.initOwner(p);
            if(item.getName().length() < 2 || item.getName().length() > 256)
            {
                alert.getDialogPane().setContentText("Invalid Name!! Must be between 1 and 256 Characters and have no commas!!");
                alert.getDialogPane().setHeaderText("Invalid Item");
                alert.showAndWait();
            }
            else if(itemManager.checkDuplicates(serialNum.getText()) == true || item.getSerialNum().length() != 10 || !item.getSerialNum().matches("[a-zA-Z0-9]*"))
            {

                System.out.println("Serial#: " + serialNum);
                if(item.getSerialNum().length() != 10)
                {
                    System.out.println("Length not 10");

                }
                else if(!item.getSerialNum().matches("[a-zA-Z0-9]*"))
                {
                    System.out.println("Letters and Numbers!!");
                }
                else if(itemManager.checkDuplicates(serialNum.getText()))
                    System.out.println("DUPLICATES!!!!");

                alert.getDialogPane().setContentText("Invalid Serial #!! Must be between 10 characters, only contain letters and/or numbers, and be Unique!!");
                alert.getDialogPane().setHeaderText("Invalid Item");
                alert.showAndWait();
            }
            else {
                inventoryTable.setItems(itemManager.addItem(item));
                System.out.println("ADD BUTTON CLICKED");
            }
            }
        }

    @FXML
    public void deleteItemClicked(ActionEvent event)
    {
        if(event.getSource() == deleteItemButton)
        {
            Item t = new Item(name.getText(), serialNum.getText(), Double.parseDouble(price.getText()));
            inventoryTable.setItems(itemManager.deleteItem(t));
            inventoryTable.getItems().removeAll(inventoryTable.getSelectionModel().getSelectedItem());
        }
    }

    @FXML
    public void clearAllItemsClicked(ActionEvent event)
    {

    }

    @FXML
    public void editName(TableColumn.CellEditEvent cellEditEvent)
    {

    }

    @FXML
    public void editSerialNum(TableColumn.CellEditEvent cellEditEvent)
    {

    }

    @FXML
    public void editPrice(ActionEvent event)
    {

    }

}
