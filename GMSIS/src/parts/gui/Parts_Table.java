/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package parts.gui;

/**
 *
 * @author rohim
 */
public class Parts_Table {
    int id;
    String name;
    String model;
    String make;
    String description;
    int qty;
    String cost;
    public  Parts_Table(int i, String n, String mo, String ma, String d, int q, String c){
 id =i;
    name=n;
    model=mo;
    make=ma;
    description=d;
   qty=q;
    cost=c;
    }
    
    public void setID(int i){
        id=i;
    }
      public void setName(String i){
        name=i;
    }
       public void setModel(String i){
        model=i;
    }
        public void setMake(String i){
        make=i;
    }
    
         public void setDescription(String i){
        description=i;
    }
          public void setQTY(int i){
        qty=i;
    }
           public void setCost(String i){
    cost=i;
        }
            public int getID(){
        return id;
    }
      public String getName(){
        return name;
    }
       public String getModel(){
        return model;
    }
        public String getMake(){
        return make;
    }
    
         public String getDescription(){
        return description;
    }
          public int getQTY(){
        return qty;
    }
           public String getCost(){
    return cost;
        }
}
