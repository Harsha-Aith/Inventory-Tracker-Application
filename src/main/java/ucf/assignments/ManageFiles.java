package ucf.assignments;

import com.google.gson.Gson;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;
import com.google.gson.stream.JsonReader;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import org.json.simple.*;

import org.json.simple.parser.JSONParser;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.*;
import java.lang.reflect.Type;
import java.net.URL;
import java.util.Iterator;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import java.io.InputStream;

import javax.print.Doc;

public class ManageFiles
{
    InventoryManager manager = new InventoryManager();
    public ObservableList<Item> list = FXCollections.observableArrayList();



    public String getFileExtension(File file) {
        String extension = "";

        try
        {
            if (file != null && file.exists()) {
                String name = file.getName();
                extension = name.substring(name.lastIndexOf(".")+1);
            }
        } catch (Exception e)
        {
            extension = "";
        }

        return extension;

    }

    @FXML
    public String exportToTSV(Item t)
    {
        // Create a string
        // Concatenate the text with the item contents with tabs
        // add the item to the list
        // return the string
        String text = "";
        manager.getList().clear();
        text = t.getName() + "\t" + t.getSerialNum() + "\t" + t.getPrice() + "\n";
        list.add(t);
        return text;
    }

    @FXML
    public String exportToHTML(Item t)
    {
        // Create a string
        // Concatenate the text with the item contents with with table contents
        // return the string
        String html = "";
        html +="<tr>\n\t<td>" + t.getName() + "</td>" + "<td>" + t.getSerialNum() + "</td>\n" + "<td>" + t.getPrice() + "</th>\n";
        return html;
    }

    public String exportToJSON(Item item)
    {
        // Create a string
        // Concatenate the text with the item contents with with table contents
        // return the string
            String json = "";
            json += "{\n\t\t \"itemName\":\"" + item.getName() + "\",";
            json += "\n\t\t \"itemSerialNum\":\"" + item.getSerialNum() + "\",";
            json += "\n\t\t \"itemPrice\":\"" + item.getPrice() + "\"\n}";
        return json;
    }

    public ObservableList<Item> importTSV(File filename) throws IOException
    {
        // Create a new ObservableList of Tasks
        // create a try catch
        // create a new bufferedReader of the filename to read the file
        // create a new string to read the line
        // while there are more lines to read
        // read the line and split the data by comma
        // add the data to the list by creating a new Task of elements of the array
        //catch
        // if the path is not found, throw an exception
        // use the method printStackTrace() to print an error message
        // return the list
        //ObservableList<Task> tasks = FXCollections.observableArrayList();
        manager.getList().clear();
        try (BufferedReader br = new BufferedReader(new FileReader(filename)))
        {
            String line;
            while ((line = br.readLine()) != null)
            {
                // process the line
                System.out.println(line);
                String data[] = line.split("\t");
                Item t = new Item(data[0], data[1], data[2]);
                manager.addItem(t);
            }
        }
        catch (Throwable e)
        {
            e.printStackTrace();
        }

        return manager.getList();
    }
    public ObservableList<Item> importHTML(File filename) throws IOException
    {
        // create a new observable list of items
        // create a document to parse the file to json
        // create an element to select the table
        // iterate through the table using an iterator
        // skip the table headers
        // while the row still has contents
            // iterate through the row
            // fill each contents of the table with variables
            // put them in an item and add them to the observable list
        // return the list
        ObservableList<Item> items = FXCollections.observableArrayList();
        String file = String.valueOf(filename);
        Document htmlDoc = Jsoup.parse(filename, "utf-8");
        System.out.println("Doc: " + htmlDoc);
        Element table = htmlDoc.select("table").first();
        String name, serialNum, price;
        Iterator<Element> row = table.select("tr").iterator();

        row.next();

        while(row.hasNext())
        {
            Iterator<Element> ite = ((Element)row.next()).select("td").iterator();
            name = ite.next().text();
            System.out.print("Name: " + name + " ");
            serialNum = ite.next().text();
            System.out.print("Serial Number: " + serialNum + " ");
            price = ite.next().text();
            System.out.println("Value: " + price);
            items.add(new Item(name, serialNum, price));
        }

        return items;
    }


    public ObservableList<Item> importJSON(File filename) throws FileNotFoundException
    {
        // create a list to hold the items
        // create a json parser
        // create a try catch
        // parse the file and convert it into a json object
        // create a json array of the items
        // initialize the list
        // create a for loop to go through the items
        // create an object to hold each item
        // create strings to extract the data from each key
        // add the item to the list
        // catch the exception
        // return the list
        ObservableList<Item> item = null;
        JSONParser parser = new JSONParser();
        try {

            Object obj = parser.parse(new FileReader(filename));
            JSONObject jsonObject = (JSONObject) obj;

            // A JSON array. JSONObject supports java.util.List interface.
            JSONArray itemList = (JSONArray) jsonObject.get("Item");

            System.out.println("List: " + itemList);
            item = FXCollections.observableArrayList();

            for (int i = 0; i < itemList.size(); i++)
            {
                JSONObject itemHolder = (JSONObject) itemList.get(i);
                String name = (String)itemHolder.get("itemName");
                System.out.println("Name: " + name);
                String serialNum =(String)itemHolder.get("itemSerialNum");
                System.out.println("Serial: " + serialNum);
                String price = (String)itemHolder.get("itemPrice");
                System.out.println("Price: " + price);
                Item t = new Item( name,  serialNum,  price);
                item.add(t);
            }

            } catch(Exception e){
                e.printStackTrace();
            }
            return item;
    }
}
