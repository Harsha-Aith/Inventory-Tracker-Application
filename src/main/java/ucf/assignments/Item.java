package ucf.assignments;

import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;

public class Item
{
    @FXML
    public SimpleStringProperty name;
    @FXML
    public SimpleStringProperty serialNum;
    public SimpleStringProperty price;

    public Item(String name, String serialNum, String price)
    {
        this.name = new SimpleStringProperty(name);
        this.serialNum = new SimpleStringProperty(serialNum);
        this.price = new SimpleStringProperty(price);
    }


    public void setPrice(String price1)
    {
        price.set(price1);
    }

    public void setSerialNum(String serialNum1) {
        serialNum.set(serialNum1);
    }
    public void setName(String name1)
    {
        name.set(name1);
    }
    public String getName()
    {
        return name.get();
    }
    public String getSerialNum()
    {
        return serialNum.get();
    }

    public String getPrice()
    {
        return price.get();
    }


    }
