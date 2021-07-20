package ucf.assignments;

import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;

public class Item
{
    @FXML
    SimpleStringProperty name;
    @FXML
    SimpleStringProperty serialNum;
    double price;

    public Item(String name, String serialNum, double price)
    {
        this.name = new SimpleStringProperty(name);
        this.serialNum = new SimpleStringProperty(serialNum);
        this.price = price;
    }


    public void setPrice(Double price)
    {
        this.price = price;
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

    public double getPrice()
    {
        return price;
    }


    }
