package ucf.assignments;

import com.google.gson.Gson;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;
import com.google.gson.stream.JsonReader;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;

// import org.json.simple.*;

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
        String text = "";
        manager.getList().clear();
        text = t.getName() + "\t" + t.getSerialNum() + "\t" + t.getPrice() + "\n";
        list.add(t);
        return text;
    }

    @FXML
    public String exportToHTML(Item t)
    {
        String html;
        html = "table, th, td {\n" +
                "  border: 1px solid black;\n" +
                "}" +
                "<table>\n" + "<tr>\n";
        html += "<th>\n" + "<td>" + t.getName() + "</td>\n" + "<td>" + t.getSerialNum() + "</td>\n" + "<td>" + t.getPrice() + "</th>\n";
        html += "</tr>\n" + "</table>\n";
        return html;
    }

    public String exportToJSON(Item item)
    {
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

    public ObservableList<Item> importJSON(File filename) throws FileNotFoundException {
        ObservableList<Item> item = null;
        BufferedReader br = null;
        Gson gson = new Gson();

        JSONParser parser = new JSONParser();
        JSONObject items = null;

        boolean line;
        try {
            br = new BufferedReader(new FileReader(filename));
            ItemManager manager = gson.fromJson(br, ItemManager.class);
            System.out.println(manager);
            //Object obj = parser.parse(br);
            /*
            while(line = br.readLine() == null)
            for(int i = 0; i < items.length(); i++)
            {
                items = (JSONObject) .get(i);
                String name = (String) items.get("itemName");
                String serialNum = (String) items.get("itemSerialNum");
                String price = (String) items.get("itemPrice");
                System.out.println("name: " + name + "SerialNum: " + serialNum + "Price: " + price);

            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        } finally {
            if(br != null)
            {
                try{
                    br.close();
                }
                catch (IOException e)
                {
                    e.printStackTrace();
                }
            }
        }

         */
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
        } catch (JsonIOException e) {
            e.printStackTrace();
        }
        return (ObservableList<Item>) items;


    }
}



