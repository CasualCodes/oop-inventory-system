/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Project_Package;

/**
 *
 * @author janwi
 */
public class InventoryTab_ItemInfo {
    
    private String itemName;
    private String itemID;
    private int itemStock;
    private double itemOrigPrice;
    private double itemSalePrice;

    public InventoryTab_ItemInfo() {
    }
    
    public String getName(){
        return itemName;
    }
    
    public String getItemID(){
        return itemID;
    }
    
    public int getItemStock(){
        return itemStock;
    }
    
    public double getOrigPrice(){
        return itemOrigPrice;
    }
    
    public double getSalePrice(){
        return itemSalePrice;
    }
    
    public void setItemName(String itmName){
        itemName = itmName;
    }
    
    public void setItemID(String itmID){
        itemID = itmID;
    }
    
    public void setItemOrigPrice(double itmOrigPrice){
        itemOrigPrice = itmOrigPrice;
    }
    
    public void setItemSalePrice(double itmSalePrice){
        itemSalePrice = itmSalePrice;
    }
    
    public void setItemStock(int itmStock){
        itemStock = itmStock;
    }
    
    public void setNewStock(int currStock, int newStock){
        itemStock = currStock + newStock;
    }
    
    public String getVatIndicator(boolean isVatable){
        String v = "";
        
        if(isVatable){
            v = "V";
        }else if(!isVatable){
            v = "NV";
        }
        return v;
    }
}
