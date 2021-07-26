package ucf.assignments;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import javafx.util.converter.DoubleStringConverter;

import java.awt.*;
import java.io.*;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Objects;
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
    private TableColumn<Item, String> priceCol = new TableColumn("Value");

    // create add button, delete button, edit button, clear button, import and export list


    @FXML
    private Button addItemButton;
    @FXML
    private Button deleteItemButton;
    @FXML
    private Button clearButton;
    @FXML
    private MenuItem importList;
    @FXML
    private MenuItem exportList;

    // create textfields for the 3 text boxes to input data
    @FXML
    private TextField name;
    @FXML
    private TextField serialNum;
    @FXML
    private TextField price;
    @FXML
    private TextField searchValue;



    Stage p = new Stage();

    InventoryManager itemManager = new InventoryManager();


    FileChooser chooser = new FileChooser();

    ManageFiles files = new ManageFiles();

    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
        // intialize the cell and cellvalue factories for the nameCol, serialNumCol, and priceCol
        nameCol.setCellFactory(TextFieldTableCell.forTableColumn());
        nameCol.setCellValueFactory((new PropertyValueFactory<>("name")));

        serialNumCol.setCellFactory(TextFieldTableCell.forTableColumn());
        serialNumCol.setCellValueFactory((new PropertyValueFactory<>("serialNum")));

        DoubleStringConverter d = new DoubleStringConverter();
        TextFieldTableCell cell = new TextFieldTableCell();
        priceCol.setCellFactory(TextFieldTableCell.forTableColumn());
        /*{
            @Override
            public String toString(Double object) {
                return String.valueOf(object);
            }

            @Override
            public Double fromString(String string) {
                return d.fromString(string);
            }
        }));

         */
        priceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
        inventoryTable.setEditable(true);
        inventoryTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);


    }





    @FXML
    public void addItemClicked(ActionEvent event) throws IOException
    {

        // if the add button is clicked
            // create a new item with the data from each field
            // set the cell value factory for each field

         if(event.getSource() == addItemButton)
        {

            Item item = new Item(name.getText(), serialNum.getText(), price.getText());
            nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
            serialNumCol.setCellValueFactory(new PropertyValueFactory<>("serialNum"));
            priceCol.setCellValueFactory(new PropertyValueFactory<Item, String>("price"));
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


                }

                /*else if(!item.getPrice().matches(" ^^\\$(([1-9]\\d{0,2}(,\\d{3})*)|(([1-9]\\d*)?\\d))(\\.\\d\\d)?$"))
                {
                    alert.getDialogPane().setContentText("Invalid Price!! Must be between 10 characters, only contain letters and/or numbers, and be Unique!!");
                    alert.getDialogPane().setHeaderText("Invalid Item");
                    alert.showAndWait();
                }

                 */

            else {
                inventoryTable.setItems(itemManager.addItem(item));
                System.out.println("ADD BUTTON CLICKED");
            }
            }
        }

    @FXML
    public void deleteItemClicked(ActionEvent event)
    {
        // if the delete button is clicked
        // get the item that is selected
        // remove all the items that are selected
        if(event.getSource() == deleteItemButton)
        {
            Item t = new Item(name.getText(), serialNum.getText(), price.getText());
            inventoryTable.setItems(itemManager.deleteItem(t));
            inventoryTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
            inventoryTable.getItems().removeAll(inventoryTable.getSelectionModel().getSelectedItem());
        }
    }


    @FXML
    public void clearAllItemsClicked(ActionEvent event)
    {
        // if the clear button is clicked
        // call the clear all items method in the item manager class
        if(event.getSource() == clearButton)
        {
            itemManager.clearAllItems();
        }
    }

    @FXML
    public void editName()
    {
        // set the cell value factory of the column
        // create an on edit commit action with an event handler
        // create a new task that gets the row value
        // if the name is invalid (length < 1 or length > 256 and does not contain a tab)
        // display an error saying that it is invalid and make them reenter another name
        //if it is valid, display the new value changed by the user
        // catch the IO exception with a print stack trace
        Item t = inventoryTable.getSelectionModel().getSelectedItem();
        nameCol.setEditable(true);
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        System.out.println("yayayayaya!!!!!!!!!!!!!!!!!!!!!");
        nameCol.setOnEditCommit(event -> {
            Alert alert = new Alert(Alert.AlertType.ERROR, "");
            alert.initModality(Modality.APPLICATION_MODAL);
            alert.initOwner(p);


            ((Item) event.getTableView().getItems().get(event.getTablePosition().getRow())).setName((String) event.getNewValue());
            String newVal = (String) event.getNewValue();
            if (newVal.length() < 1 || newVal.length() > 256 ||newVal.contains("\t") ) {
                System.out.println("New Value: " + newVal);
                alert.getDialogPane().setContentText("Invalid Name!! Must be between 1 and 256 Characters and have no commas!!");
                alert.getDialogPane().setHeaderText("Invalid Item");
                alert.showAndWait();

            } else {
                System.out.println("New Value Correct: " + newVal);
                ((Item) event.getTableView().getItems().get(event.getTablePosition().getRow())).setName((String) event.getNewValue());
            }

        });

    }

    @FXML
    public void editSerialNum()
    {
        // create an on edit commit action with an event handler
        // create a new task that gets the row value
        // load the fxml file
        // create a try catch for the file
        // create a scene and an alert to display in error if an invalid field is entered
        // if the serial number is invalid
        // display an error saying that it is invalid and make them enter another serial number
        //if it is valid, display the new value changed by the user
        // catch the IO exception with a print stack trace

        Item t = inventoryTable.getSelectionModel().getSelectedItem();
        serialNumCol.setEditable(true);
        serialNumCol.setCellValueFactory(new PropertyValueFactory<>("serialNum"));
        System.out.println("yayayayaya!!!!!!!!!!!!!!!!!!!!!");
        serialNumCol.setOnEditCommit(event -> {
            try {

                Alert alert = new Alert(Alert.AlertType.ERROR, "");
                alert.initModality(Modality.APPLICATION_MODAL);
                alert.initOwner(p);


                //((Item) event.getTableView().getItems().get(event.getTablePosition().getRow())).setSerialNum((String) event.getNewValue());
                String newVal = (String) event.getNewValue();
                if (newVal.length() != 10 || itemManager.checkDuplicates(serialNum.getText()) || !newVal.matches("\"^(?=.*[A-Z])(?=.*[0-9])[A-Z0-9]+$\""))
                {
                    System.out.println("New Value: " + newVal);
                    alert.getDialogPane().setContentText("Invalid Serial #!! Must be between 10 characters, only contain letters and/or numbers, and be Unique!!");
                    alert.getDialogPane().setHeaderText("Invalid Item");
                    alert.showAndWait();
                    newVal = event.getOldValue();

                }
                else
                {
                    System.out.println("New Value Correct: " + newVal);
                    ((Item) event.getTableView().getItems().get(event.getTablePosition().getRow())).setSerialNum(event.getNewValue());
                }
            } catch (Exception e) {
                e.printStackTrace();
            }


        });

    }

    @FXML
    public void editPrice()
    {
        // create an on edit commit action with an event handler
        // create a new task that gets the row value
        // load the fxml file
        // create a try catch for the file
        // create a scene and an alert to display in error if an invalid field is entered
        // display the new value changed by the user
        // catch the IO exception with a print stack trace
        Item t = inventoryTable.getSelectionModel().getSelectedItem();
        priceCol.setEditable(true);
        priceCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        System.out.println("yayayayaya!!!!!!!!!!!!!!!!!!!!!");
        priceCol.setOnEditCommit(event -> {
            Alert alert = new Alert(Alert.AlertType.ERROR, "");
            alert.initModality(Modality.APPLICATION_MODAL);
            alert.initOwner(p);


            //((Item) event.getTableView().getItems().get(event.getTablePosition().getRow())).setPrice((String) event.getNewValue());
            String newVal = (String) event.getNewValue();
            if (!newVal.matches("[0-9]+") && !newVal.contains(".")) {
                System.out.println("New Value: " + newVal);
                alert.getDialogPane().setContentText("Invalid Price!!! Must be only Digits");
                alert.getDialogPane().setHeaderText("Invalid Item");
                alert.showAndWait();

            } else {
                System.out.println("New Value: " + newVal);
                ((Item) event.getTableView().getItems().get(event.getTablePosition().getRow())).setPrice((String) event.getNewValue());
            }
    });
    }

    public void searchClicked(ActionEvent event)
    {
        // add a listener to the text entered in the search bar
        // call the set predicate function from the filtered data in the ItemManafer class
        // set it to the createPredicate function with the new search value in the text
        // set the table to show the filtered data
        searchValue.textProperty().addListener((observable, oldValue, newValue) ->
                itemManager.filteredData.setPredicate(itemManager.createPredicate(newValue))
        );
        inventoryTable.setItems(itemManager.filteredData);
    }

    public void importListClicked(ActionEvent event) throws IOException
    {
        // initialize the file chooser to open a new window showing a new stage
        // if the file is not null
        // if(file != null)
        // set the items and columns to read the data in the table
        // call the importList function from the ManageFiles class to import the list
        // create a new scene
        chooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("txt", "*.txt"));
        chooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("json", "*.json"));
        chooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("html", "*.html"));

        Scene scene = new Scene(new Group());
        Stage stage = new Stage();
        stage.setTitle(chooser.getTitle());
        stage.setWidth(300);
        stage.setHeight(500);
        importList.setOnAction(new EventHandler() {
            @Override
            public void handle(Event event) {
                try {
                    File selectedFile = chooser.showOpenDialog(stage);
                    System.out.println("File Name: " + selectedFile);
                    String fileExtension = files.getFileExtension(selectedFile);
                    if (selectedFile != null) {
                        inventoryTable.setEditable(true);
                        if (fileExtension.equals("txt"))
                        {
                            System.out.println("File Extension: " + fileExtension);
                            System.out.println("Size: " + itemManager.getList().size());
                            inventoryTable.setItems(files.importTSV(selectedFile));
                            inventoryTable.getColumns().setAll(nameCol, serialNumCol, priceCol);
                            for (int i = 0; i < itemManager.getList().size(); i++)
                                System.out.println(itemManager.getList().get(i).getName());
                        }
                        else if (fileExtension.equals("html"))
                        {
                            inventoryTable.setItems(files.importHTML(selectedFile));
                            inventoryTable.getColumns().setAll(nameCol, serialNumCol, priceCol);
                            for (int i = 0; i < itemManager.getList().size(); i++)
                                System.out.println(itemManager.getList().get(i).getName());

                        }
                        else if (fileExtension.equals("json"))
                        {
                            Gson gson = new Gson();
                            BufferedReader br = null;
                            br = new BufferedReader(new FileReader(selectedFile));
                            ItemManager manager = gson.fromJson(br, ItemManager.class);
                            System.out.println(manager);
                            inventoryTable.setItems(files.importJSON(selectedFile));
                            stage.setScene(scene);
                            stage.show();

                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            });
    }


    public void exportListClicked(ActionEvent event) throws IOException
    {
        chooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("txt", "*.txt"));
        chooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("json", "*.json"));
        chooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("html", "*.html"));
        Scene scene = new Scene(new Group());
        Stage stage = new Stage();
        stage.setTitle(chooser.getTitle());
        stage.setWidth(300);
        stage.setHeight(500);
        exportList.setOnAction(new EventHandler() {
            @Override
            public void handle(Event event) {
                try {
                    File selectedFile = chooser.showSaveDialog(stage);
                    System.out.println("File Name: " + selectedFile);
                    if (selectedFile != null)
                    {

                        File fileName = selectedFile.getAbsoluteFile();
                        FileWriter writer = new FileWriter(fileName);
                        String fileExtension = files.getFileExtension(fileName);
                        System.out.println("File Extension: " + fileExtension);
                        if (fileExtension.equals("txt"))
                        {
                            System.out.println("File Extension: " + fileExtension);
                            for (Item t : inventoryTable.getItems())
                            {
                                writer.append(files.exportToTSV(t));
                            }
                        }
                        else if (fileExtension.equals("html"))
                        {
                            System.out.println("File Extension: " + fileExtension);
                            try
                            {
                                //BufferedWriter bw = new BufferedWriter(writer);
                                writer.append("<head>\n" +
                                        "<style>\n" +
                                        "table {\n" +
                                        "  font-family: arial, sans-serif;\n" +
                                        "  border-collapse: collapse;\n" +
                                        "  width: 100%;\n" +
                                        "}\n" +
                                        "\n" +
                                        "td, th {\n" +
                                        "  border: 1px solid #dddddd;\n" +
                                        "  text-align: left;\n" +
                                        "  padding: 8px;\n" +
                                        "}\n" +
                                        "\n" +
                                        "tr:nth-child(even) {\n" +
                                        "  background-color: #dddddd;\n" +
                                        "}" +
                                        "table, th, td {\n" +
                                        "  border: 1px solid black;\n" +
                                        "}\n" +
                                        "</style>\n" +
                                        "</head>\n" +
                                        "<body>\n" +
                                        "\n" +
                                        "<table>\n" + "<tr>\n");
                                writer.append( "  \t<th>Name</th>\n" +
                                        "   \t<th>Serial Number</th>\n" +
                                        "    \t<th>Value</th>   \n");
                                for (Item t : inventoryTable.getItems())
                                {
                                    writer.append(files.exportToHTML(t));
                                }


                            } catch (IOException e)
                            {
                                e.printStackTrace();
                            }
                        }
                        else if(fileExtension.equals("json"))
                        {
                            String json = "";
                            json += "{\n\t\"Item\":[";
                            int index = 0;
                            for (Item item: inventoryTable.getItems())
                            {
                                json += files.exportToJSON(item);
                                System.out.println("Size: " + inventoryTable.getItems().size());
                                System.out.println("Index: " + index);
                                System.out.println(json);
                                if(inventoryTable.getItems().size()-1 > index)
                                {
                                    json += ",\n";
                                }
                                index++;
                            }
                            json += "]\n}\n";
                            writer.append(json);

                        }
                        writer.flush();
                        writer.close();
                        stage.setScene(scene);
                        stage.show();

                    }
                } catch (IOException e)
                {
                    e.printStackTrace();
                }

            }
        });
    }

}
