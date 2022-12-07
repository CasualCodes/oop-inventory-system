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
public class MainTab_ItemInfo {
    
    private String itmID;
    private String itmName;
    private String isVat;
    private double itmPrice;
    private double itmOrigPrice;
    private int itmQty;
    private double itmSubTotal;
    private double totalAmount;
    
    
    
    private double cashAmount;
    private double changeAmount;

    public MainTab_ItemInfo() {
    }
    
    public String getItmID(){
        return itmID;
    }
    
    public String getItmName(){
        return itmName;
    }
    
     public double getOrigPrice(){
        return itmOrigPrice;
    }
    
    public double getItmPrice(){
        return itmPrice;
    }
    
    public int getItmQty(){
        return itmQty;
    }
    
    public String getVat(){
        return isVat;
    }
    
     public double getItmSubTotal(){
        return itmSubTotal = itmPrice * itmQty;
    }
     
    public double getCashAmount(){
        return cashAmount;
    }
    
    public double getChangeAmount(double total){
        return changeAmount = total - cashAmount;
    } 
           
    public void setItmID(String id){
        this.itmID = id;
    }
    
    public void setItmName(String name){
        this.itmName = name;
    }
    
    public void setItmPrice(double price){
        this.itmPrice = price;
    }
    
    public void setItmQty(int qty){
        this.itmQty = qty;
    }
    
    public void setOrigPrice(double origPri){
        itmOrigPrice = origPri;
    }
    
    public void setVatIndicator(String vat){
        isVat = vat;
    }
    
     public void setCashAmount(double cash){
        cashAmount = cash;
    }
     
    
    
    
}
