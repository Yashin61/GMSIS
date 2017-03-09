// Author: yhk30--y.hosseinkhorrami@se15.qmul.ac.uk--150479358--Yashin Hossein Khorrami

package vehicles;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Warranty
{
    private IntegerProperty warrantyID;
    private StringProperty name;
    private StringProperty address;
    private StringProperty expiryDate;
    
    public Warranty(int warrantyIDX, String nameX, String addressX, String expiryDateX)
    {
        warrantyID=new SimpleIntegerProperty(warrantyIDX);
        name=new SimpleStringProperty(nameX);
        address=new SimpleStringProperty(addressX);
        expiryDate=new SimpleStringProperty(expiryDateX);
    }
    
    public IntegerProperty getWarrantyID1() {
        return warrantyID;
    }
    public int getWarrantyID()
    {
        return warrantyID.get();
    }
    public void setWarrantyID(Integer i) {
        warrantyID.set(i);
    }
    
    public StringProperty getName1() {
        return name;
    }
    public String getName()
    {
        return name.get();
    }
    public void setName(String i) {
        name.set(i);
    }
    
    public StringProperty getAddress1() {
        return address;
    }
    public String getAddress()
    {
        return address.get();
    }
    public void setAddress(String i) {
        address.set(i);
    }
    
    public StringProperty getExpiryDate1() {
        return expiryDate;
    }
    public String getExpiryDate()
    {
        return expiryDate.get();
    }
    public void setExpiryDate(String i) {
        expiryDate.set(i);
    }
}