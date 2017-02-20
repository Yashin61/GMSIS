// Author: yhk30--y.hosseinkhorrami@se15.qmul.ac.uk--150479358--Yashin Hossein Khorrami

package vehicles;

public class Warranty
{
    private int warrantyID;
    private String name;
    private String address;
    private String expiryDate;
    
    public Warranty(int warrantyIDX, String nameX, String addressX, String expiryDateX)
    {
        warrantyID=warrantyIDX;
        name=nameX;
        address=addressX;
        expiryDate=expiryDateX;
    }
    
    public int getWarrantyID()
    {
        return warrantyID;
    }
    public String getName()
    {
        return name;
    }
    public String getAddress()
    {
        return address;
    }
    public String getExpiryDate()
    {
        return expiryDate;
    }
}