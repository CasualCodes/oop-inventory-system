/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Project_Package;

import java.awt.CardLayout;
import java.awt.Color;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Scanner;
import javax.swing.RowFilter;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.time.LocalDate;
import java.util.Currency;
import javax.swing.JOptionPane;
import javax.swing.ListSelectionModel;

/**
 *
 * @author jan wilhelm sy, jovic francis rayco, jabez villamarzo, russel pispis, gerald pispis
 */
public class Main_Frame extends javax.swing.JFrame {
    
    CardLayout mainCard;
    CardLayout cardProgramTabs;
    CardLayout cardMainTab;
    CardLayout cardSalesTab;
    
    DefaultTableModel mainCart;
    DefaultTableModel inventory;
    DefaultTableModel inventoryUser;
    DefaultTableModel salesHistory;
    DefaultTableModel salesReceipt;
    
    MainTab_ItemInfo mainTabItm;
    InventoryTab_ItemInfo inventoryTabItm;
    
    boolean isInventoryEditable = false;
    
    DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
    DefaultTableCellRenderer rightRenderer = new DefaultTableCellRenderer();
    DefaultTableCellRenderer leftRenderer = new DefaultTableCellRenderer();
    
    // --------------------------- Global Variables - ArrayLists [INVENTORY] ---------------------------
    ArrayList<String> IDInventory = new ArrayList<>();
    ArrayList<String> nameInventory = new ArrayList<>();
    ArrayList<Integer> stockInventory = new ArrayList<>();
    ArrayList<Double> originalPriceInventory = new ArrayList<>();
    ArrayList<Double> salePriceInventory = new ArrayList<>();
    ArrayList<String> isVatableInventory = new ArrayList<>();
    // --------------------------- Global Variables - ArrayLists [INVENTORY] ---------------------------
    
    // --------------------------- Global Variables - ArrayLists [HISTORY] ---------------------------
    ArrayList<String> dateHistory = new ArrayList<>();
    ArrayList<String> IDHistory = new ArrayList<>();
    ArrayList<String> nameHistory = new ArrayList<>();
    ArrayList<Integer> quantityHistory = new ArrayList<>();
    ArrayList<Double> originalPriceHistory = new ArrayList<>();
    ArrayList<Double> salePriceHistory = new ArrayList<>();
    ArrayList<Double> subTotalHistory = new ArrayList<>();
    ArrayList<String> isVatableHistory = new ArrayList<>();
    ArrayList<Double> profitHistory = new ArrayList<>();
    // --------------------------- Global Variables - ArrayLists [HISTORY] ---------------------------
    
    // --------------------------- Global Variables - ArrayLists [CART ITEMS] ---------------------------
    ArrayList<String> dateCartItem = new ArrayList<>();
    ArrayList<String> IDCartItem = new ArrayList<>();
    ArrayList<String> nameCartItem = new ArrayList<>();
    ArrayList<Integer> quantityCartItem = new ArrayList<>();
    ArrayList<Double> originalPriceCartItem = new ArrayList<>();
    ArrayList<Double> salePriceCartItem = new ArrayList<>();
    ArrayList<Double> subTotalCartItem = new ArrayList<>();
    ArrayList<String> isVatableCartItem = new ArrayList<>();
    // --------------------------- Global Variables - ArrayLists [CART ITEMS] ---------------------------
    
    // --------------------------- Global Variables - ArrayLists [REFERENCE] ---------------------------
    ArrayList<String> referenceNumbers = new ArrayList<>();
    ArrayList<String> referenceNumbersDate = new ArrayList<>();
    // --------------------------- Global Variables - ArrayLists [REFERENCE] ---------------------------
    
    // ------------- Global Variables - ArrayLists <-> TxtFile <-> ArrayLists --------------
    String filePath = "";
    String theResult;

    String txtContent = "";
    String insertTxtContent = "";
    String newText = "";
    String useContent;
    // ------------- Global Variables - ArrayLists <-> TxtFile <-> ArrayLists --------------
    
    // WRITING - ArrayList -> TxtFile
    public static String fileToString(String filePath) throws Exception {
        String theInput = null;
        Scanner goScanner = new Scanner(new File(filePath));
        StringBuffer goBuffer = new StringBuffer();

        while (goScanner.hasNextLine()) {
            theInput = goScanner.nextLine();
            goBuffer.append(theInput);
        }

        return goBuffer.toString();
    }
    
    // READING - TxtFile (Invetory.txt) -> Inventory (ARRAYLIST)
    public void readFileOnceSystemIsOpened() throws Exception {
        
        File getFile = new File("Inventory.txt");
        Scanner scanFile = new Scanner(getFile);
        
        while(scanFile.hasNextLine()) {
            
            String[] textPerLine = scanFile.nextLine().split(",");
            
            IDInventory.add(textPerLine[0]);
            nameInventory.add(textPerLine[1]);
            stockInventory.add(Integer.parseInt(textPerLine[2]));
            originalPriceInventory.add(Double.parseDouble(textPerLine[3]));
            salePriceInventory.add(Double.parseDouble(textPerLine[4]));
            isVatableInventory.add(textPerLine[5]);
            
        }
        
    }
    
    // READING - TxtFile (History.txt) -> History (ARRAYLIST)
    // Code here for reading.
    
    public void loadTxtFileToArrayListToSalesHistory() throws Exception {
        
        File getFile = new File("History.txt");
        Scanner scanFile = new Scanner(getFile);
        
        while(scanFile.hasNextLine()) {
            
            String[] textPerLine = scanFile.nextLine().split(",");
            
            dateHistory.add(textPerLine[0]);
            IDHistory.add(textPerLine[1]);
            nameHistory.add(textPerLine[2]);
            quantityHistory.add(Integer.parseInt(textPerLine[3]));
            originalPriceHistory.add(Double.parseDouble(textPerLine[4]));
            salePriceHistory.add(Double.parseDouble(textPerLine[5]));
            subTotalHistory.add(Double.parseDouble(textPerLine[6]));
            isVatableHistory.add(textPerLine[7]);
            profitHistory.add(Double.parseDouble(textPerLine[8]));
            
        }
        
    }
    
    // READING - TxtFile (References.txt) -> REFERENCES (ARRAYLIST)
    // Code here for reading.
    
    public void loadTxtFileToArrayListToReceiptsHistory() throws Exception {
        
        File getFile = new File("References.txt");
        Scanner scanFile = new Scanner(getFile);
        
        while(scanFile.hasNextLine()) {
            
            String[] textPerLine = scanFile.nextLine().split(",");
            
            referenceNumbersDate.add(textPerLine[0]);
            referenceNumbers.add(textPerLine[1]);
            
        }
        
    }
    
    boolean isManager = false;
    
    public Main_Frame() {
        initComponents();
        
        mainTabItm = new MainTab_ItemInfo();
        inventoryTabItm = new InventoryTab_ItemInfo();    
        
        mainCard = (CardLayout)(MainCardLayout.getLayout());
        cardProgramTabs = (CardLayout)(Program_Tabs.getLayout());
        cardMainTab = (CardLayout)(Tab_main.getLayout());
        cardSalesTab = (CardLayout)(SalesCard.getLayout());
        
        mainCart = (DefaultTableModel)(Main_cartViewer_Table.getModel());
        inventory = (DefaultTableModel)(Inventory_stockViewer_Table.getModel());
        inventoryUser = (DefaultTableModel)(Inventory_stockViewerUser_Table.getModel());
        salesHistory = (DefaultTableModel)(Sales_revenueProfitViewer_Table.getModel());
        salesReceipt = (DefaultTableModel)(Sales_receiptList_Table.getModel());
        
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
        rightRenderer.setHorizontalAlignment(SwingConstants.RIGHT);
        leftRenderer.setHorizontalAlignment(SwingConstants.LEFT);
        
        // Start of the program - Defaults
        Main_Add_Btn.setEnabled(false);
        
        
        // ------------------------ READ FILE (Inventory.txt) -> ArrayList -> System ------------------------
        try {
            readFileOnceSystemIsOpened();
        } catch(Exception e) {}
            inventorySystemRefresherOnceSystemIsOpened();
        try {
            loadTxtFileToArrayListToSalesHistory();
        } catch(Exception e) {}
            salesHistorySystemRefresherOnceSystemIsOpened();
        try {
            loadTxtFileToArrayListToReceiptsHistory();
        } catch(Exception e) {}
            receiptHistorySystemRefresherOnceSystemIsOpened();
            
        // ------------------------ READ FILE (Inventory.txt) -> ArrayList -> System ------------------------
        
        defaultInventoryTabState();
        
    }
    
    public void inventorySystemRefresherOnceSystemIsOpened() {
        
        for(int a = 0; a < IDInventory.size(); a++) {
                    
            inventory.addRow(new Object[]{
                                            IDInventory.get(a), 
                                            nameInventory.get(a), 
                                            stockInventory.get(a),
                                            formatCurrency(roundNumbers(originalPriceInventory.get(a))), 
                                            formatCurrency(roundNumbers(salePriceInventory.get(a))),
                                            isVatableInventory.get(a)
                                         });
            
            inventoryUser.addRow(new Object[]{
                                            IDInventory.get(a), 
                                            nameInventory.get(a), 
                                            stockInventory.get(a),
                                            formatCurrency(roundNumbers(originalPriceInventory.get(a))), 
                                            formatCurrency(roundNumbers(salePriceInventory.get(a))),
                                            isVatableInventory.get(a)
                                         });
            
            
        }
        
        
                    
        
    }
    
    public void salesHistorySystemRefresherOnceSystemIsOpened() {
        
        for(int a = 0; a < IDHistory.size(); a++) {
               
            salesHistory.addRow(new Object[]{
                                            dateHistory.get(a),
                                            IDHistory.get(a),
                                            nameHistory.get(a),
                                            quantityHistory.get(a),
                                            formatCurrency(roundNumbers(salePriceHistory.get(a))),
                                            formatCurrency(roundNumbers(subTotalHistory.get(a))),
                                            formatCurrency(roundNumbers(profitHistory.get(a)))
                                         });
                    
        }
        
    }
    
    public void receiptHistorySystemRefresherOnceSystemIsOpened() {
        
        for(int a = 0; a < referenceNumbers.size(); a++) {
            
            salesReceipt.addRow(new Object[]{
                                            referenceNumbersDate.get(a),
                                            referenceNumbers.get(a)
                                         });
                    
        }
        
    }
    
    // Data -> ArrayList -> TxtFile - Inventory System Table
    void InsertArrayListToTxtFileToSystem () {
        
        inventory.setRowCount(0);
        inventoryUser.setRowCount(0);
        try {
            
            filePath = "Inventory.txt";
            theResult = fileToString(filePath);
            PrintWriter goAddPrint = new PrintWriter(new File(filePath));
            
            for(int a = 0; a < IDInventory.size(); a++) {
                    
                    newText += 
                    IDInventory.get(a)              + "," + 
                    nameInventory.get(a)            + "," + 
                    stockInventory.get(a)           + "," + 
                    originalPriceInventory.get(a)   + "," + 
                    salePriceInventory.get(a)       + "," + 
                    isVatableInventory.get(a)       + "\r\n";
                    
                }
                
                theResult = theResult.replaceAll(theResult, newText);

                goAddPrint.append("");
                goAddPrint.append(theResult);
                goAddPrint.flush();

                newText = "";
                
                for(int a = 0; a < IDInventory.size(); a++) {
                    
                    inventory.addRow(new Object[]{
                                                    IDInventory.get(a), 
                                                    nameInventory.get(a), 
                                                    stockInventory.get(a),
                                                    formatCurrency(roundNumbers(originalPriceInventory.get(a))), 
                                                    formatCurrency(roundNumbers(salePriceInventory.get(a))),
                                                    isVatableInventory.get(a)
                                                 });
                    
                    inventoryUser.addRow(new Object[]{
                                            IDInventory.get(a), 
                                            nameInventory.get(a), 
                                            stockInventory.get(a),
                                            formatCurrency(roundNumbers(originalPriceInventory.get(a))), 
                                            formatCurrency(roundNumbers(salePriceInventory.get(a))),
                                            isVatableInventory.get(a)
                                         });
                    
                }

            defaultInventoryTabState();
            
        } catch(Exception catchException) {
            
            System.out.println("Exception Catched!");
            System.out.println(catchException);
            
        }
        
    }
    
    // Data -> ArrayList -> TxtFile - History Sales System Table
    void InsertHistoryArrayListToTxtFileToSystem () {
        
        salesHistory.setRowCount(0);
        try {
            
            filePath = "History.txt";
            theResult = fileToString(filePath);
            PrintWriter goAddPrint = new PrintWriter(new File(filePath));
            
            for(int a = 0; a < IDHistory.size(); a++) {
                
                    newText += 
                    
                    dateHistory.get(a)             + "," + 
                    IDHistory.get(a)               + "," + 
                    nameHistory.get(a)             + "," + 
                    quantityHistory.get(a)         + "," + 
                    originalPriceHistory.get(a)    + "," + 
                    salePriceHistory.get(a)        + "," + 
                    subTotalHistory.get(a)         + "," + 
                    isVatableHistory.get(a)        + "," + 
                    profitHistory.get(a)           + "\r\n";
                    
                    
                }
                
                theResult = theResult.replaceAll(theResult, newText);

                goAddPrint.append("");
                goAddPrint.append(theResult);
                goAddPrint.flush();

                newText = "";
                
                for(int a = 0; a < dateHistory.size(); a++) {
                    
                    salesHistory.addRow(new Object[]{
                                                        dateHistory.get(a),
                                                        IDHistory.get(a),
                                                        nameHistory.get(a),
                                                        quantityHistory.get(a),
                                                        formatCurrency(roundNumbers(salePriceHistory.get(a))),
                                                        formatCurrency(roundNumbers(subTotalHistory.get(a))),
                                                        formatCurrency(roundNumbers(profitHistory.get(a)))
                                                    });
                    
                }
            
        } catch(Exception catchException) {
            
            System.out.println("Exception Catched!");
            System.out.println(catchException);
            
        }
        
    }
    
    // Data -> ArrayList -> TxtFile - Reference Sales System Table
    void InsertReferenceArrayListToTxtFileToSystem () {
        
        salesReceipt.setRowCount(0);
        try {
            
            filePath = "References.txt";
            theResult = fileToString(filePath);
            PrintWriter goAddPrint = new PrintWriter(new File(filePath));
            
            for(int a = 0; a < referenceNumbers.size(); a++) {
                
                    newText += 
                    
                    referenceNumbersDate.get(a)         + "," +  
                    referenceNumbers.get(a)             + "\r\n";
                    
                    
                }
                
                theResult = theResult.replaceAll(theResult, newText);

                goAddPrint.append("");
                goAddPrint.append(theResult);
                goAddPrint.flush();

                newText = "";
                
                for(int a = 0; a < referenceNumbers.size(); a++) {
                    
                    salesReceipt.addRow(new Object[]{
                                                        referenceNumbersDate.get(a),
                                                        referenceNumbers.get(a)
                                                    });
                    
                }
            
        } catch(Exception catchException) {
            
            System.out.println("Exception Catched!");
            System.out.println(catchException);
            
        }
        
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        MainCardLayout = new javax.swing.JPanel();
        LoginCard = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        Login_account_DropDown = new javax.swing.JComboBox<>();
        Login_Password_TxtField = new javax.swing.JPasswordField();
        Login_login_Btn = new javax.swing.JButton();
        Login_wrongPassword_Indicator = new javax.swing.JLabel();
        FunctionsCard = new javax.swing.JPanel();
        SidePanel = new javax.swing.JPanel();
        SideButtons = new javax.swing.JPanel();
        SidePanel_main_Btn = new javax.swing.JButton();
        SidePanel_inventory_Btn = new javax.swing.JButton();
        SidePanel_sales_Btn = new javax.swing.JButton();
        SidePanel_logout_Btn = new javax.swing.JButton();
        Program_Tabs = new javax.swing.JPanel();
        Tab_main = new javax.swing.JPanel();
        Main_purchaseInformation = new javax.swing.JPanel();
        Main_inputPanel = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        Main_itmName_TxtField = new javax.swing.JTextField();
        Main_itmID_TxtField = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        Main_itmPrice_TxtField = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        Main_itmQuantity_TxtField = new javax.swing.JTextField();
        Main_Add_Btn = new javax.swing.JButton();
        Main_insufficientStock_Indicator = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        Main_availableStock_Indicator = new javax.swing.JLabel();
        Main_cartPanel = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        Main_cartViewer_Table = new javax.swing.JTable();
        jLabel10 = new javax.swing.JLabel();
        Main_cartTotal_txt = new javax.swing.JLabel();
        Main_remove_Btn = new javax.swing.JButton();
        Main_confirm_Btn = new javax.swing.JButton();
        Main_cancel_Btn = new javax.swing.JButton();
        Main_paymentInformation = new javax.swing.JPanel();
        Main_paymentPanel = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        Main_paymentTotal_Txt = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        Main_cashAmount_TxtField = new javax.swing.JTextField();
        Main_pay_Btn = new javax.swing.JButton();
        Main_cancelPayment_Btn = new javax.swing.JButton();
        Main_InsufficientAmount_Indicator = new javax.swing.JLabel();
        Main_receiptInformation = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel12 = new javax.swing.JLabel();
        main_printReceipt_btn = new javax.swing.JButton();
        Main_receiptBack_Btn = new javax.swing.JButton();
        Main_receiptNumRef_Txt = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        Main_receipt_TxtPane = new javax.swing.JTextArea();
        Tab_inventory = new javax.swing.JPanel();
        jLabel14 = new javax.swing.JLabel();
        Inventory_itmOrigPrice_TxtField = new javax.swing.JTextField();
        Inventory_itmID_TxtField = new javax.swing.JTextField();
        jLabel30 = new javax.swing.JLabel();
        Inventory_itmName_TxtField = new javax.swing.JTextField();
        jLabel33 = new javax.swing.JLabel();
        jLabel35 = new javax.swing.JLabel();
        jLabel34 = new javax.swing.JLabel();
        Inventory_itmSalePrice_TxtField = new javax.swing.JTextField();
        jLabel36 = new javax.swing.JLabel();
        jLabel37 = new javax.swing.JLabel();
        Inventory_itmStock_TxtField = new javax.swing.JTextField();
        Inventory_itmStockAdder_TxtField = new javax.swing.JTextField();
        Inventory_addStock_Btn = new javax.swing.JButton();
        Inventory_Vat_Indicator = new javax.swing.JCheckBox();
        Inventory_insert_Btn = new javax.swing.JButton();
        Inventory_edit_Btn = new javax.swing.JButton();
        Inventory_remove_Btn = new javax.swing.JButton();
        Inventory_clear_Btn = new javax.swing.JButton();
        jScrollPane6 = new javax.swing.JScrollPane();
        Inventory_stockViewer_Table = new javax.swing.JTable();
        jLabel38 = new javax.swing.JLabel();
        Inventory_search_TxtField = new javax.swing.JTextField();
        Inventory_IDExists_Indicator = new javax.swing.JLabel();
        Inventory_Editing_Indicator = new javax.swing.JLabel();
        Tab_inventoryUser = new javax.swing.JPanel();
        jScrollPane7 = new javax.swing.JScrollPane();
        Inventory_stockViewerUser_Table = new javax.swing.JTable();
        jLabel15 = new javax.swing.JLabel();
        Inventory_searchUser_TxtField = new javax.swing.JTextField();
        jLabel39 = new javax.swing.JLabel();
        Tab_sales = new javax.swing.JPanel();
        RevenueSalesPanel = new javax.swing.JPanel();
        totalRevenue = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        ProfitPanel = new javax.swing.JPanel();
        jLabel19 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        totalProfit = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        ReceiptsPanel = new javax.swing.JPanel();
        jLabel23 = new javax.swing.JLabel();
        jLabel24 = new javax.swing.JLabel();
        totalReceipt = new javax.swing.JLabel();
        SalesCard = new javax.swing.JPanel();
        RevenueAndProfitInformation = new javax.swing.JPanel();
        jLabel29 = new javax.swing.JLabel();
        sales_iteamSearch_TxtField = new javax.swing.JTextField();
        jScrollPane3 = new javax.swing.JScrollPane();
        Sales_revenueProfitViewer_Table = new javax.swing.JTable();
        jLabel27 = new javax.swing.JLabel();
        Sales_totalRevenue_Txt = new javax.swing.JLabel();
        jLabel31 = new javax.swing.JLabel();
        Sales_totalProfit_Txt = new javax.swing.JLabel();
        ReceiptInformation = new javax.swing.JPanel();
        jLabel25 = new javax.swing.JLabel();
        Sales_receiptSearch_TxtField = new javax.swing.JTextField();
        jScrollPane4 = new javax.swing.JScrollPane();
        Sales_receiptList_Table = new javax.swing.JTable();
        jScrollPane5 = new javax.swing.JScrollPane();
        Sales_ReceiptViewer_TxtArea = new javax.swing.JTextPane();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setMinimumSize(new java.awt.Dimension(1250, 670));
        setResizable(false);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        MainCardLayout.setLayout(new java.awt.CardLayout());

        LoginCard.setBackground(new java.awt.Color(255, 255, 255));

        jLabel8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Elements/SplashLogin/background (7).png"))); // NOI18N

        Login_account_DropDown.setFont(new java.awt.Font("sansserif", 1, 12)); // NOI18N
        Login_account_DropDown.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "ADMIN", "USER" }));
        Login_account_DropDown.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                Login_account_DropDownItemStateChanged(evt);
            }
        });

        Login_Password_TxtField.setFont(new java.awt.Font("sansserif", 0, 18)); // NOI18N

        Login_login_Btn.setBackground(new java.awt.Color(0, 0, 0));
        Login_login_Btn.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        Login_login_Btn.setForeground(new java.awt.Color(255, 255, 255));
        Login_login_Btn.setText("LOGIN");
        Login_login_Btn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Login_login_BtnActionPerformed(evt);
            }
        });

        Login_wrongPassword_Indicator.setFont(new java.awt.Font("sansserif", 1, 12)); // NOI18N
        Login_wrongPassword_Indicator.setForeground(new java.awt.Color(228, 154, 154));
        Login_wrongPassword_Indicator.setText("INCORRECT PASSWORD");

        javax.swing.GroupLayout LoginCardLayout = new javax.swing.GroupLayout(LoginCard);
        LoginCard.setLayout(LoginCardLayout);
        LoginCardLayout.setHorizontalGroup(
            LoginCardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(LoginCardLayout.createSequentialGroup()
                .addGroup(LoginCardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(LoginCardLayout.createSequentialGroup()
                        .addGap(525, 525, 525)
                        .addComponent(jLabel8))
                    .addGroup(LoginCardLayout.createSequentialGroup()
                        .addGap(466, 466, 466)
                        .addComponent(Login_Password_TxtField, javax.swing.GroupLayout.PREFERRED_SIZE, 318, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(LoginCardLayout.createSequentialGroup()
                        .addGap(522, 522, 522)
                        .addComponent(Login_login_Btn, javax.swing.GroupLayout.PREFERRED_SIZE, 202, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, LoginCardLayout.createSequentialGroup()
                .addGap(0, 556, Short.MAX_VALUE)
                .addComponent(Login_wrongPassword_Indicator)
                .addGap(556, 556, 556))
            .addGroup(LoginCardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(LoginCardLayout.createSequentialGroup()
                    .addGap(465, 465, 465)
                    .addComponent(Login_account_DropDown, javax.swing.GroupLayout.PREFERRED_SIZE, 318, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(467, Short.MAX_VALUE)))
        );
        LoginCardLayout.setVerticalGroup(
            LoginCardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(LoginCardLayout.createSequentialGroup()
                .addGap(101, 101, 101)
                .addComponent(jLabel8)
                .addGap(62, 62, 62)
                .addComponent(Login_Password_TxtField, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(Login_login_Btn, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(Login_wrongPassword_Indicator)
                .addContainerGap(180, Short.MAX_VALUE))
            .addGroup(LoginCardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(LoginCardLayout.createSequentialGroup()
                    .addGap(319, 319, 319)
                    .addComponent(Login_account_DropDown, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(319, Short.MAX_VALUE)))
        );

        Login_wrongPassword_Indicator.setVisible(false);

        MainCardLayout.add(LoginCard, "LoginCard");

        FunctionsCard.setBackground(new java.awt.Color(204, 204, 255));

        SidePanel.setBackground(new java.awt.Color(41, 50, 65));

        SideButtons.setBackground(new java.awt.Color(41, 50, 65));

        SidePanel_main_Btn.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
        SidePanel_main_Btn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Elements/SidePanel_Buttons/Selected.png"))); // NOI18N
        SidePanel_main_Btn.setText("MAIN");
        SidePanel_main_Btn.setBorderPainted(false);
        SidePanel_main_Btn.setContentAreaFilled(false);
        SidePanel_main_Btn.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        SidePanel_main_Btn.setPreferredSize(new java.awt.Dimension(180, 40));
        SidePanel_main_Btn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SidePanel_main_BtnActionPerformed(evt);
            }
        });
        SideButtons.add(SidePanel_main_Btn);

        SidePanel_inventory_Btn.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
        SidePanel_inventory_Btn.setForeground(new java.awt.Color(255, 255, 255));
        SidePanel_inventory_Btn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Elements/SidePanel_Buttons/Default.png"))); // NOI18N
        SidePanel_inventory_Btn.setText("INVENTORY");
        SidePanel_inventory_Btn.setBorderPainted(false);
        SidePanel_inventory_Btn.setContentAreaFilled(false);
        SidePanel_inventory_Btn.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        SidePanel_inventory_Btn.setPreferredSize(new java.awt.Dimension(180, 40));
        SidePanel_inventory_Btn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SidePanel_inventory_BtnActionPerformed(evt);
            }
        });
        SideButtons.add(SidePanel_inventory_Btn);

        SidePanel_sales_Btn.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
        SidePanel_sales_Btn.setForeground(new java.awt.Color(255, 255, 255));
        SidePanel_sales_Btn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Elements/SidePanel_Buttons/Default.png"))); // NOI18N
        SidePanel_sales_Btn.setText("SALES");
        SidePanel_sales_Btn.setBorderPainted(false);
        SidePanel_sales_Btn.setContentAreaFilled(false);
        SidePanel_sales_Btn.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        SidePanel_sales_Btn.setPreferredSize(new java.awt.Dimension(180, 40));
        SidePanel_sales_Btn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SidePanel_sales_BtnActionPerformed(evt);
            }
        });
        SideButtons.add(SidePanel_sales_Btn);

        SidePanel_logout_Btn.setText("LOGOUT");
        SidePanel_logout_Btn.setBorderPainted(false);
        SidePanel_logout_Btn.setFocusPainted(false);
        SidePanel_logout_Btn.setFocusable(false);
        SidePanel_logout_Btn.setRequestFocusEnabled(false);
        SidePanel_logout_Btn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SidePanel_logout_BtnActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout SidePanelLayout = new javax.swing.GroupLayout(SidePanel);
        SidePanel.setLayout(SidePanelLayout);
        SidePanelLayout.setHorizontalGroup(
            SidePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(SideButtons, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 180, Short.MAX_VALUE)
            .addGroup(SidePanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(SidePanel_logout_Btn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        SidePanelLayout.setVerticalGroup(
            SidePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(SidePanelLayout.createSequentialGroup()
                .addGap(58, 58, 58)
                .addComponent(SideButtons, javax.swing.GroupLayout.PREFERRED_SIZE, 211, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 335, Short.MAX_VALUE)
                .addComponent(SidePanel_logout_Btn)
                .addGap(43, 43, 43))
        );

        Program_Tabs.setBackground(new java.awt.Color(255, 255, 255));
        Program_Tabs.setPreferredSize(new java.awt.Dimension(180, 670));
        Program_Tabs.setLayout(new java.awt.CardLayout());

        Tab_main.setBackground(new java.awt.Color(204, 204, 255));
        Tab_main.setPreferredSize(new java.awt.Dimension(12500, 670));
        Tab_main.setLayout(new java.awt.CardLayout());

        Main_purchaseInformation.setBackground(new java.awt.Color(255, 255, 255));

        Main_inputPanel.setBackground(new java.awt.Color(246, 247, 251));
        Main_inputPanel.setPreferredSize(new java.awt.Dimension(580, 600));

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel1.setText("ITEM INFORMATION");

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel2.setText("ITEM ID");

        Main_itmName_TxtField.setDisabledTextColor(new java.awt.Color(0, 0, 0));

        Main_itmID_TxtField.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Main_itmID_TxtFieldKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                Main_itmID_TxtFieldKeyReleased(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel3.setText("ITEM NAME");

        Main_itmPrice_TxtField.setDisabledTextColor(new java.awt.Color(0, 0, 0));

        jLabel4.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel4.setText("PRICE");

        jLabel5.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel5.setText("QUANTITY");

        Main_itmQuantity_TxtField.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Main_itmQuantity_TxtFieldKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                Main_itmQuantity_TxtFieldKeyReleased(evt);
            }
        });

        Main_Add_Btn.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        Main_Add_Btn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Elements/Buttons/ADD Default.png"))); // NOI18N
        Main_Add_Btn.setText("ADD ITEM");
        Main_Add_Btn.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        Main_Add_Btn.setPressedIcon(new javax.swing.ImageIcon(getClass().getResource("/Elements/Buttons/ADD Hover _ Pressed.png"))); // NOI18N
        Main_Add_Btn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Main_Add_BtnActionPerformed(evt);
            }
        });

        Main_insufficientStock_Indicator.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        Main_insufficientStock_Indicator.setForeground(new java.awt.Color(228, 154, 154));
        Main_insufficientStock_Indicator.setText("INSUFFICIENT STOCK");

        jLabel7.setText("Available Stocks:");

        Main_availableStock_Indicator.setText("0");

        javax.swing.GroupLayout Main_inputPanelLayout = new javax.swing.GroupLayout(Main_inputPanel);
        Main_inputPanel.setLayout(Main_inputPanelLayout);
        Main_inputPanelLayout.setHorizontalGroup(
            Main_inputPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(Main_inputPanelLayout.createSequentialGroup()
                .addGap(28, 28, 28)
                .addGroup(Main_inputPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(Main_inputPanelLayout.createSequentialGroup()
                        .addComponent(Main_insufficientStock_Indicator)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(Main_inputPanelLayout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addContainerGap(321, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, Main_inputPanelLayout.createSequentialGroup()
                        .addGroup(Main_inputPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(Main_inputPanelLayout.createSequentialGroup()
                                .addGroup(Main_inputPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(Main_inputPanelLayout.createSequentialGroup()
                                        .addComponent(jLabel5)
                                        .addGap(67, 67, 67))
                                    .addComponent(Main_itmQuantity_TxtField, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(Main_availableStock_Indicator, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(Main_Add_Btn, javax.swing.GroupLayout.PREFERRED_SIZE, 162, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(Main_inputPanelLayout.createSequentialGroup()
                                .addGroup(Main_inputPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(Main_itmID_TxtField, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel2))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(Main_inputPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(Main_itmName_TxtField, javax.swing.GroupLayout.PREFERRED_SIZE, 233, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel3))
                                .addGap(27, 27, 27)
                                .addGroup(Main_inputPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel4)
                                    .addComponent(Main_itmPrice_TxtField, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addGap(28, 28, 28))))
        );
        Main_inputPanelLayout.setVerticalGroup(
            Main_inputPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(Main_inputPanelLayout.createSequentialGroup()
                .addGap(40, 40, 40)
                .addComponent(jLabel1)
                .addGap(90, 90, 90)
                .addGroup(Main_inputPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jLabel3)
                    .addComponent(jLabel4))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(Main_inputPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(Main_itmID_TxtField, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(Main_itmName_TxtField, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(Main_itmPrice_TxtField, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(47, 47, 47)
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(Main_inputPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(Main_itmQuantity_TxtField, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(Main_Add_Btn, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7)
                    .addComponent(Main_availableStock_Indicator))
                .addGap(46, 46, 46)
                .addComponent(Main_insufficientStock_Indicator)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        Main_itmName_TxtField.setEnabled(false);
        Main_itmPrice_TxtField.setEnabled(false);
        Main_insufficientStock_Indicator.setVisible(false);

        Main_cartPanel.setBackground(new java.awt.Color(246, 247, 251));

        Main_cartViewer_Table.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "id", "name", "qty", "orig", "sale", "sub", "vat"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.Integer.class, java.lang.Double.class, java.lang.Double.class, java.lang.Double.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        Main_cartViewer_Table.setOpaque(false);
        Main_cartViewer_Table.getTableHeader().setResizingAllowed(false);
        Main_cartViewer_Table.getTableHeader().setReorderingAllowed(false);
        jScrollPane1.setViewportView(Main_cartViewer_Table);
        if (Main_cartViewer_Table.getColumnModel().getColumnCount() > 0) {
            Main_cartViewer_Table.getColumnModel().getColumn(0).setResizable(false);
            Main_cartViewer_Table.getColumnModel().getColumn(1).setResizable(false);
            Main_cartViewer_Table.getColumnModel().getColumn(2).setResizable(false);
            Main_cartViewer_Table.getColumnModel().getColumn(3).setResizable(false);
            Main_cartViewer_Table.getColumnModel().getColumn(4).setResizable(false);
            Main_cartViewer_Table.getColumnModel().getColumn(5).setResizable(false);
            Main_cartViewer_Table.getColumnModel().getColumn(6).setResizable(false);
        }
        Main_cartViewer_Table.getTableHeader().setUI(null);
        hideColumn(0);
        hideColumn(3);
        hideColumn(4);
        hideColumn(6);

        Main_cartViewer_Table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        jLabel10.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel10.setText("Total:");

        Main_cartTotal_txt.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        Main_cartTotal_txt.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        Main_cartTotal_txt.setText("0.0");

        Main_remove_Btn.setText("REMOVE");
        Main_remove_Btn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Main_remove_BtnActionPerformed(evt);
            }
        });

        Main_confirm_Btn.setBackground(new java.awt.Color(51, 51, 51));
        Main_confirm_Btn.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        Main_confirm_Btn.setForeground(new java.awt.Color(255, 255, 255));
        Main_confirm_Btn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Elements/Buttons/Confirm Default.png"))); // NOI18N
        Main_confirm_Btn.setText("CONFIRM");
        Main_confirm_Btn.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        Main_confirm_Btn.setPressedIcon(new javax.swing.ImageIcon(getClass().getResource("/Elements/Buttons/ADD Hover _ Pressed.png"))); // NOI18N
        Main_confirm_Btn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Main_confirm_BtnActionPerformed(evt);
            }
        });

        Main_cancel_Btn.setBackground(new java.awt.Color(51, 51, 51));
        Main_cancel_Btn.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        Main_cancel_Btn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Elements/Buttons/Cancel Default.png"))); // NOI18N
        Main_cancel_Btn.setText("CANCEL");
        Main_cancel_Btn.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        Main_cancel_Btn.setPressedIcon(new javax.swing.ImageIcon(getClass().getResource("/Elements/Buttons/Cancel Pressed.png"))); // NOI18N
        Main_cancel_Btn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Main_cancel_BtnActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout Main_cartPanelLayout = new javax.swing.GroupLayout(Main_cartPanel);
        Main_cartPanel.setLayout(Main_cartPanelLayout);
        Main_cartPanelLayout.setHorizontalGroup(
            Main_cartPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(Main_cartPanelLayout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(Main_cartPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(Main_cartPanelLayout.createSequentialGroup()
                        .addComponent(Main_confirm_Btn, javax.swing.GroupLayout.PREFERRED_SIZE, 179, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(Main_cancel_Btn, javax.swing.GroupLayout.PREFERRED_SIZE, 1, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, Main_cartPanelLayout.createSequentialGroup()
                        .addComponent(Main_remove_Btn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(55, 55, 55)
                        .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(Main_cartTotal_txt, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, Main_cartPanelLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 364, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(20, 20, 20))
        );
        Main_cartPanelLayout.setVerticalGroup(
            Main_cartPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(Main_cartPanelLayout.createSequentialGroup()
                .addGap(40, 40, 40)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 387, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(27, 27, 27)
                .addGroup(Main_cartPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(Main_cartTotal_txt)
                    .addComponent(jLabel10)
                    .addComponent(Main_remove_Btn))
                .addGap(28, 28, 28)
                .addGroup(Main_cartPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(Main_confirm_Btn, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(Main_cancel_Btn, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(56, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout Main_purchaseInformationLayout = new javax.swing.GroupLayout(Main_purchaseInformation);
        Main_purchaseInformation.setLayout(Main_purchaseInformationLayout);
        Main_purchaseInformationLayout.setHorizontalGroup(
            Main_purchaseInformationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(Main_purchaseInformationLayout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addComponent(Main_inputPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 600, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(Main_cartPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(24, Short.MAX_VALUE))
        );
        Main_purchaseInformationLayout.setVerticalGroup(
            Main_purchaseInformationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(Main_purchaseInformationLayout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addGroup(Main_purchaseInformationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(Main_cartPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(Main_inputPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(50, Short.MAX_VALUE))
        );

        Tab_main.add(Main_purchaseInformation, "Main_purchaseInfo_Card");

        Main_paymentInformation.setBackground(new java.awt.Color(204, 204, 204));

        Main_paymentPanel.setBackground(new java.awt.Color(255, 255, 255));

        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 36)); // NOI18N
        jLabel6.setText("PAYMENT");

        Main_paymentTotal_Txt.setFont(new java.awt.Font("Tahoma", 0, 20)); // NOI18N
        Main_paymentTotal_Txt.setText("00.00");

        jLabel9.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel9.setText("ENTER CASH AMOUNT:");

        jLabel11.setFont(new java.awt.Font("Tahoma", 0, 20)); // NOI18N
        jLabel11.setText("Total Amount to Pay: ");

        Main_cashAmount_TxtField.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Main_cashAmount_TxtFieldKeyPressed(evt);
            }
        });

        Main_pay_Btn.setBackground(new java.awt.Color(51, 51, 51));
        Main_pay_Btn.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        Main_pay_Btn.setForeground(new java.awt.Color(255, 255, 255));
        Main_pay_Btn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Elements/Buttons/Confirm Default.png"))); // NOI18N
        Main_pay_Btn.setText("PAY");
        Main_pay_Btn.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        Main_pay_Btn.setPressedIcon(new javax.swing.ImageIcon(getClass().getResource("/Elements/Buttons/Confirm Pressed.png"))); // NOI18N
        Main_pay_Btn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Main_pay_BtnActionPerformed(evt);
            }
        });

        Main_cancelPayment_Btn.setBackground(new java.awt.Color(51, 51, 51));
        Main_cancelPayment_Btn.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        Main_cancelPayment_Btn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Elements/Buttons/Cancel Default.png"))); // NOI18N
        Main_cancelPayment_Btn.setText("CANCEL");
        Main_cancelPayment_Btn.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        Main_cancelPayment_Btn.setPressedIcon(new javax.swing.ImageIcon(getClass().getResource("/Elements/Buttons/Cancel Pressed.png"))); // NOI18N
        Main_cancelPayment_Btn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Main_cancelPayment_BtnActionPerformed(evt);
            }
        });

        Main_InsufficientAmount_Indicator.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        Main_InsufficientAmount_Indicator.setForeground(new java.awt.Color(228, 154, 154));
        Main_InsufficientAmount_Indicator.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        Main_InsufficientAmount_Indicator.setText("INSUFFICIENT AMOUNT");
        Main_InsufficientAmount_Indicator.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        Main_InsufficientAmount_Indicator.setVisible(false);

        javax.swing.GroupLayout Main_paymentPanelLayout = new javax.swing.GroupLayout(Main_paymentPanel);
        Main_paymentPanel.setLayout(Main_paymentPanelLayout);
        Main_paymentPanelLayout.setHorizontalGroup(
            Main_paymentPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(Main_paymentPanelLayout.createSequentialGroup()
                .addGap(40, 40, 40)
                .addGroup(Main_paymentPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(Main_paymentPanelLayout.createSequentialGroup()
                        .addGroup(Main_paymentPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 243, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel6))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, Main_paymentPanelLayout.createSequentialGroup()
                        .addGroup(Main_paymentPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(Main_cashAmount_TxtField, javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(Main_paymentPanelLayout.createSequentialGroup()
                                .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 216, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(Main_paymentTotal_Txt, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addComponent(Main_InsufficientAmount_Indicator, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 329, Short.MAX_VALUE)
                            .addGroup(Main_paymentPanelLayout.createSequentialGroup()
                                .addComponent(Main_cancelPayment_Btn, javax.swing.GroupLayout.PREFERRED_SIZE, 141, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(Main_pay_Btn, javax.swing.GroupLayout.PREFERRED_SIZE, 1, Short.MAX_VALUE)))
                        .addGap(40, 40, 40))))
        );
        Main_paymentPanelLayout.setVerticalGroup(
            Main_paymentPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(Main_paymentPanelLayout.createSequentialGroup()
                .addGap(35, 35, 35)
                .addComponent(jLabel6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(Main_paymentPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(Main_paymentTotal_Txt, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(Main_cashAmount_TxtField, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(31, 31, 31)
                .addGroup(Main_paymentPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(Main_pay_Btn, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(Main_cancelPayment_Btn, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(Main_InsufficientAmount_Indicator)
                .addContainerGap(35, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout Main_paymentInformationLayout = new javax.swing.GroupLayout(Main_paymentInformation);
        Main_paymentInformation.setLayout(Main_paymentInformationLayout);
        Main_paymentInformationLayout.setHorizontalGroup(
            Main_paymentInformationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, Main_paymentInformationLayout.createSequentialGroup()
                .addContainerGap(330, Short.MAX_VALUE)
                .addComponent(Main_paymentPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(331, 331, 331))
        );
        Main_paymentInformationLayout.setVerticalGroup(
            Main_paymentInformationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(Main_paymentInformationLayout.createSequentialGroup()
                .addGap(120, 120, 120)
                .addComponent(Main_paymentPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(135, Short.MAX_VALUE))
        );

        Tab_main.add(Main_paymentInformation, "Main_paymentInfo_Card");

        Main_receiptInformation.setBackground(new java.awt.Color(204, 204, 204));

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));

        jLabel12.setFont(new java.awt.Font("Tahoma", 1, 36)); // NOI18N
        jLabel12.setText("RECEIPT");

        main_printReceipt_btn.setBackground(new java.awt.Color(51, 51, 51));
        main_printReceipt_btn.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        main_printReceipt_btn.setForeground(new java.awt.Color(255, 255, 255));
        main_printReceipt_btn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Elements/Buttons/Confirm Default.png"))); // NOI18N
        main_printReceipt_btn.setText("PRINT");
        main_printReceipt_btn.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        main_printReceipt_btn.setPressedIcon(new javax.swing.ImageIcon(getClass().getResource("/Elements/Buttons/Confirm Pressed.png"))); // NOI18N
        main_printReceipt_btn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                main_printReceipt_btnActionPerformed(evt);
            }
        });

        Main_receiptBack_Btn.setBackground(new java.awt.Color(51, 51, 51));
        Main_receiptBack_Btn.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        Main_receiptBack_Btn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Elements/Buttons/Cancel Default.png"))); // NOI18N
        Main_receiptBack_Btn.setText("BACK");
        Main_receiptBack_Btn.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        Main_receiptBack_Btn.setPressedIcon(new javax.swing.ImageIcon(getClass().getResource("/Elements/Buttons/Cancel Pressed.png"))); // NOI18N
        Main_receiptBack_Btn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Main_receiptBack_BtnActionPerformed(evt);
            }
        });

        Main_receiptNumRef_Txt.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        Main_receiptNumRef_Txt.setText("00000000000121");

        jLabel13.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel13.setText("REFERENCE #: ");

        jScrollPane2.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        Main_receipt_TxtPane.setColumns(20);
        Main_receipt_TxtPane.setFont(new java.awt.Font("Monospaced", 0, 12)); // NOI18N
        Main_receipt_TxtPane.setLineWrap(true);
        Main_receipt_TxtPane.setRows(5);
        Main_receipt_TxtPane.setWrapStyleWord(true);
        jScrollPane2.setViewportView(Main_receipt_TxtPane);
        Main_receipt_TxtPane.setEditable(false);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(28, 28, 28)
                        .addComponent(Main_receiptBack_Btn, javax.swing.GroupLayout.PREFERRED_SIZE, 1, Short.MAX_VALUE)
                        .addGap(18, 18, 18)
                        .addComponent(main_printReceipt_btn, javax.swing.GroupLayout.PREFERRED_SIZE, 179, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addContainerGap(27, Short.MAX_VALUE)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 360, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(25, 25, 25))
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(28, 28, 28)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel12)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(Main_receiptNumRef_Txt, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel12)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(Main_receiptNumRef_Txt, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 395, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(Main_receiptBack_Btn, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(main_printReceipt_btn, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(22, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout Main_receiptInformationLayout = new javax.swing.GroupLayout(Main_receiptInformation);
        Main_receiptInformation.setLayout(Main_receiptInformationLayout);
        Main_receiptInformationLayout.setHorizontalGroup(
            Main_receiptInformationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, Main_receiptInformationLayout.createSequentialGroup()
                .addContainerGap(335, Short.MAX_VALUE)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(323, 323, 323))
        );
        Main_receiptInformationLayout.setVerticalGroup(
            Main_receiptInformationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(Main_receiptInformationLayout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(75, Short.MAX_VALUE))
        );

        Tab_main.add(Main_receiptInformation, "Main_receiptInfo_Card");

        Program_Tabs.add(Tab_main, "mainTab_Card");

        Tab_inventory.setBackground(new java.awt.Color(255, 255, 255));

        jLabel14.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel14.setText("ITEM ID");

        Inventory_itmOrigPrice_TxtField.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Inventory_itmOrigPrice_TxtFieldKeyPressed(evt);
            }
        });

        Inventory_itmID_TxtField.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Inventory_itmID_TxtFieldKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                Inventory_itmID_TxtFieldKeyReleased(evt);
            }
        });

        jLabel30.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel30.setText("ITEM NAME");

        jLabel33.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel33.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel33.setText("ORIGINAL PRICE");

        jLabel35.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel35.setText("PHP");

        jLabel34.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel34.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel34.setText("SALE PRICE");

        Inventory_itmSalePrice_TxtField.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Inventory_itmSalePrice_TxtFieldKeyPressed(evt);
            }
        });

        jLabel36.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel36.setText("PHP");

        jLabel37.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel37.setText("STOCK");

        Inventory_itmStock_TxtField.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Inventory_itmStock_TxtFieldKeyPressed(evt);
            }
        });

        Inventory_itmStockAdder_TxtField.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Inventory_itmStockAdder_TxtFieldKeyPressed(evt);
            }
        });

        Inventory_addStock_Btn.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        Inventory_addStock_Btn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Elements/Buttons/ADD Default.png"))); // NOI18N
        Inventory_addStock_Btn.setText("ADD");
        Inventory_addStock_Btn.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        Inventory_addStock_Btn.setPressedIcon(new javax.swing.ImageIcon(getClass().getResource("/Elements/Buttons/ADD Hover _ Pressed.png"))); // NOI18N
        Inventory_addStock_Btn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Inventory_addStock_BtnActionPerformed(evt);
            }
        });

        Inventory_Vat_Indicator.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        Inventory_Vat_Indicator.setText("Is Item VATABLE?");
        Inventory_Vat_Indicator.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Inventory_Vat_IndicatorActionPerformed(evt);
            }
        });

        Inventory_insert_Btn.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        Inventory_insert_Btn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Elements/Buttons/ADD Default.png"))); // NOI18N
        Inventory_insert_Btn.setText("INSERT");
        Inventory_insert_Btn.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        Inventory_insert_Btn.setPressedIcon(new javax.swing.ImageIcon(getClass().getResource("/Elements/Buttons/ADD Hover _ Pressed.png"))); // NOI18N
        Inventory_insert_Btn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Inventory_insert_BtnActionPerformed(evt);
            }
        });

        Inventory_edit_Btn.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        Inventory_edit_Btn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Elements/Buttons/Edit Default.png"))); // NOI18N
        Inventory_edit_Btn.setText("EDIT");
        Inventory_edit_Btn.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        Inventory_edit_Btn.setPressedIcon(new javax.swing.ImageIcon(getClass().getResource("/Elements/Buttons/Edit Pressed.png"))); // NOI18N
        Inventory_edit_Btn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Inventory_edit_BtnActionPerformed(evt);
            }
        });

        Inventory_remove_Btn.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        Inventory_remove_Btn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Elements/Buttons/Cancel Default.png"))); // NOI18N
        Inventory_remove_Btn.setText("REMOVE");
        Inventory_remove_Btn.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        Inventory_remove_Btn.setPressedIcon(new javax.swing.ImageIcon(getClass().getResource("/Elements/Buttons/Cancel Pressed.png"))); // NOI18N
        Inventory_remove_Btn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Inventory_remove_BtnActionPerformed(evt);
            }
        });

        Inventory_clear_Btn.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        Inventory_clear_Btn.setForeground(new java.awt.Color(255, 255, 255));
        Inventory_clear_Btn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Elements/Buttons/Confirm Default.png"))); // NOI18N
        Inventory_clear_Btn.setText("CLEAR");
        Inventory_clear_Btn.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        Inventory_clear_Btn.setPressedIcon(new javax.swing.ImageIcon(getClass().getResource("/Elements/Buttons/Confirm Pressed.png"))); // NOI18N
        Inventory_clear_Btn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Inventory_clear_BtnActionPerformed(evt);
            }
        });

        Inventory_stockViewer_Table.setBorder(new javax.swing.border.MatteBorder(null));
        Inventory_stockViewer_Table.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "NAME", "STOCK", "ORIG. PRICE", "SALE PRICE", "Vatable?"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.Integer.class, java.lang.Double.class, java.lang.Double.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        Inventory_stockViewer_Table.getTableHeader().setReorderingAllowed(false);
        Inventory_stockViewer_Table.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                Inventory_stockViewer_TableMouseClicked(evt);
            }
        });
        jScrollPane6.setViewportView(Inventory_stockViewer_Table);
        if (Inventory_stockViewer_Table.getColumnModel().getColumnCount() > 0) {
            Inventory_stockViewer_Table.getColumnModel().getColumn(0).setPreferredWidth(50);
            Inventory_stockViewer_Table.getColumnModel().getColumn(1).setPreferredWidth(180);
            Inventory_stockViewer_Table.getColumnModel().getColumn(2).setPreferredWidth(60);
            Inventory_stockViewer_Table.getColumnModel().getColumn(3).setPreferredWidth(80);
            Inventory_stockViewer_Table.getColumnModel().getColumn(4).setPreferredWidth(80);
            Inventory_stockViewer_Table.getColumnModel().getColumn(5).setPreferredWidth(10);
        }
        Inventory_stockViewer_Table.getColumnModel().getColumn(0).setHeaderRenderer(centerRenderer);
        Inventory_stockViewer_Table.getColumnModel().getColumn(1).setHeaderRenderer(leftRenderer);
        Inventory_stockViewer_Table.getColumnModel().getColumn(2).setHeaderRenderer(centerRenderer);
        Inventory_stockViewer_Table.getColumnModel().getColumn(3).setHeaderRenderer(rightRenderer);
        Inventory_stockViewer_Table.getColumnModel().getColumn(4).setHeaderRenderer(rightRenderer);
        Inventory_stockViewer_Table.getColumnModel().getColumn(5).setHeaderRenderer(centerRenderer);

        Inventory_stockViewer_Table.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);
        Inventory_stockViewer_Table.getColumnModel().getColumn(1).setCellRenderer(leftRenderer);
        Inventory_stockViewer_Table.getColumnModel().getColumn(2).setCellRenderer(centerRenderer);
        Inventory_stockViewer_Table.getColumnModel().getColumn(3).setCellRenderer(rightRenderer);
        Inventory_stockViewer_Table.getColumnModel().getColumn(4).setCellRenderer(rightRenderer);
        Inventory_stockViewer_Table.getColumnModel().getColumn(5).setCellRenderer(centerRenderer);
        Inventory_stockViewer_Table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION); // Added code for single selection

        jLabel38.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        jLabel38.setText("Search");

        Inventory_search_TxtField.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                Inventory_search_TxtFieldKeyReleased(evt);
            }
        });

        Inventory_IDExists_Indicator.setFont(new java.awt.Font("Tahoma", 1, 15)); // NOI18N
        Inventory_IDExists_Indicator.setForeground(new java.awt.Color(228, 154, 154));
        Inventory_IDExists_Indicator.setText("ID ALREADY EXISTS");

        Inventory_Editing_Indicator.setFont(new java.awt.Font("Tahoma", 1, 36)); // NOI18N
        Inventory_Editing_Indicator.setForeground(new java.awt.Color(255, 221, 94));
        Inventory_Editing_Indicator.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        Inventory_Editing_Indicator.setText("EDITING MODE");

        javax.swing.GroupLayout Tab_inventoryLayout = new javax.swing.GroupLayout(Tab_inventory);
        Tab_inventory.setLayout(Tab_inventoryLayout);
        Tab_inventoryLayout.setHorizontalGroup(
            Tab_inventoryLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(Tab_inventoryLayout.createSequentialGroup()
                .addGroup(Tab_inventoryLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(Tab_inventoryLayout.createSequentialGroup()
                        .addGap(21, 21, 21)
                        .addGroup(Tab_inventoryLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(Tab_inventoryLayout.createSequentialGroup()
                                .addGroup(Tab_inventoryLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel14)
                                    .addComponent(Inventory_itmID_TxtField, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(38, 38, 38)
                                .addGroup(Tab_inventoryLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(Inventory_itmName_TxtField, javax.swing.GroupLayout.DEFAULT_SIZE, 195, Short.MAX_VALUE)
                                    .addComponent(jLabel30))
                                .addGap(21, 21, 21))
                            .addGroup(Tab_inventoryLayout.createSequentialGroup()
                                .addComponent(Inventory_IDExists_Indicator)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                    .addGroup(Tab_inventoryLayout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(Tab_inventoryLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(Tab_inventoryLayout.createSequentialGroup()
                                .addComponent(jLabel37)
                                .addGap(18, 18, 18)
                                .addGroup(Tab_inventoryLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(Tab_inventoryLayout.createSequentialGroup()
                                        .addComponent(Inventory_itmStock_TxtField, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(Inventory_itmStockAdder_TxtField, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(Inventory_addStock_Btn, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(Inventory_Vat_Indicator)))
                            .addGroup(Tab_inventoryLayout.createSequentialGroup()
                                .addComponent(jLabel33, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(Inventory_itmOrigPrice_TxtField, javax.swing.GroupLayout.PREFERRED_SIZE, 156, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jLabel35))
                            .addGroup(Tab_inventoryLayout.createSequentialGroup()
                                .addComponent(jLabel34, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(Inventory_itmSalePrice_TxtField, javax.swing.GroupLayout.PREFERRED_SIZE, 156, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(10, 10, 10)
                                .addComponent(jLabel36)))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(Tab_inventoryLayout.createSequentialGroup()
                        .addGroup(Tab_inventoryLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, Tab_inventoryLayout.createSequentialGroup()
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel38)
                                .addGap(18, 18, 18)
                                .addComponent(Inventory_search_TxtField, javax.swing.GroupLayout.PREFERRED_SIZE, 131, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(Tab_inventoryLayout.createSequentialGroup()
                                .addGap(35, 35, 35)
                                .addGroup(Tab_inventoryLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addGroup(Tab_inventoryLayout.createSequentialGroup()
                                        .addComponent(Inventory_edit_Btn, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(Inventory_clear_Btn, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(Tab_inventoryLayout.createSequentialGroup()
                                        .addComponent(Inventory_insert_Btn, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(Inventory_remove_Btn, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(Inventory_Editing_Indicator, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGap(0, 0, Short.MAX_VALUE)))
                        .addGap(18, 18, 18)))
                .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 702, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20))
        );
        Tab_inventoryLayout.setVerticalGroup(
            Tab_inventoryLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(Tab_inventoryLayout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addGroup(Tab_inventoryLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 592, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(Tab_inventoryLayout.createSequentialGroup()
                        .addGroup(Tab_inventoryLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(Inventory_search_TxtField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel38))
                        .addGap(56, 56, 56)
                        .addComponent(Inventory_IDExists_Indicator)
                        .addGap(18, 18, 18)
                        .addGroup(Tab_inventoryLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(Inventory_itmID_TxtField, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(Tab_inventoryLayout.createSequentialGroup()
                                .addComponent(jLabel14)
                                .addGap(36, 36, 36))
                            .addGroup(Tab_inventoryLayout.createSequentialGroup()
                                .addComponent(jLabel30)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(Inventory_itmName_TxtField, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(Tab_inventoryLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel33)
                            .addComponent(Inventory_itmOrigPrice_TxtField, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel35))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(Tab_inventoryLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel34)
                            .addComponent(Inventory_itmSalePrice_TxtField, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel36))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(Tab_inventoryLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel37)
                            .addComponent(Inventory_itmStock_TxtField, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(Inventory_itmStockAdder_TxtField, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(Inventory_addStock_Btn, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addComponent(Inventory_Vat_Indicator)
                        .addGap(49, 49, 49)
                        .addGroup(Tab_inventoryLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(Inventory_insert_Btn, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(Inventory_remove_Btn, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(Tab_inventoryLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(Inventory_edit_Btn, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(Inventory_clear_Btn, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(43, 43, 43)
                        .addComponent(Inventory_Editing_Indicator)))
                .addContainerGap(53, Short.MAX_VALUE))
        );

        Inventory_IDExists_Indicator.setVisible(false);
        Inventory_Editing_Indicator.setVisible(false);

        Program_Tabs.add(Tab_inventory, "inventoryTab_Card");

        Tab_inventoryUser.setBackground(new java.awt.Color(255, 255, 255));

        Inventory_stockViewerUser_Table.setBorder(new javax.swing.border.MatteBorder(null));
        Inventory_stockViewerUser_Table.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "NAME", "STOCK", "ORIG. PRICE", "SALE PRICE", "Vatable?"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.Integer.class, java.lang.Double.class, java.lang.Double.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        Inventory_stockViewerUser_Table.getTableHeader().setReorderingAllowed(false);
        jScrollPane7.setViewportView(Inventory_stockViewerUser_Table);
        if (Inventory_stockViewerUser_Table.getColumnModel().getColumnCount() > 0) {
            Inventory_stockViewerUser_Table.getColumnModel().getColumn(0).setPreferredWidth(50);
            Inventory_stockViewerUser_Table.getColumnModel().getColumn(1).setPreferredWidth(180);
            Inventory_stockViewerUser_Table.getColumnModel().getColumn(2).setPreferredWidth(60);
            Inventory_stockViewerUser_Table.getColumnModel().getColumn(3).setPreferredWidth(80);
            Inventory_stockViewerUser_Table.getColumnModel().getColumn(4).setPreferredWidth(80);
            Inventory_stockViewerUser_Table.getColumnModel().getColumn(5).setPreferredWidth(10);
        }
        Inventory_stockViewerUser_Table.getColumnModel().getColumn(3).setMinWidth(0);
        Inventory_stockViewerUser_Table.getColumnModel().getColumn(3).setMaxWidth(0);
        Inventory_stockViewerUser_Table.getColumnModel().getColumn(3).setWidth(0);

        Inventory_stockViewerUser_Table.setEnabled(false);

        Inventory_stockViewerUser_Table.getColumnModel().getColumn(0).setHeaderRenderer(centerRenderer);
        Inventory_stockViewerUser_Table.getColumnModel().getColumn(1).setHeaderRenderer(leftRenderer);
        Inventory_stockViewerUser_Table.getColumnModel().getColumn(2).setHeaderRenderer(centerRenderer);
        Inventory_stockViewerUser_Table.getColumnModel().getColumn(3).setHeaderRenderer(rightRenderer);
        Inventory_stockViewerUser_Table.getColumnModel().getColumn(4).setHeaderRenderer(rightRenderer);
        Inventory_stockViewerUser_Table.getColumnModel().getColumn(5).setHeaderRenderer(centerRenderer);

        Inventory_stockViewerUser_Table.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);
        Inventory_stockViewerUser_Table.getColumnModel().getColumn(1).setCellRenderer(leftRenderer);
        Inventory_stockViewerUser_Table.getColumnModel().getColumn(2).setCellRenderer(centerRenderer);
        Inventory_stockViewerUser_Table.getColumnModel().getColumn(3).setCellRenderer(rightRenderer);
        Inventory_stockViewerUser_Table.getColumnModel().getColumn(4).setCellRenderer(rightRenderer);
        Inventory_stockViewerUser_Table.getColumnModel().getColumn(5).setCellRenderer(centerRenderer);

        jLabel15.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel15.setText("AVAILABLE ITEMS");

        Inventory_searchUser_TxtField.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                Inventory_searchUser_TxtFieldKeyReleased(evt);
            }
        });

        jLabel39.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        jLabel39.setText("Search");

        javax.swing.GroupLayout Tab_inventoryUserLayout = new javax.swing.GroupLayout(Tab_inventoryUser);
        Tab_inventoryUser.setLayout(Tab_inventoryUserLayout);
        Tab_inventoryUserLayout.setHorizontalGroup(
            Tab_inventoryUserLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(Tab_inventoryUserLayout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addGroup(Tab_inventoryUserLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(Tab_inventoryUserLayout.createSequentialGroup()
                        .addComponent(jLabel39)
                        .addGap(18, 18, 18)
                        .addComponent(Inventory_searchUser_TxtField, javax.swing.GroupLayout.PREFERRED_SIZE, 131, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane7, javax.swing.GroupLayout.PREFERRED_SIZE, 1021, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel15))
                .addContainerGap(26, Short.MAX_VALUE))
        );
        Tab_inventoryUserLayout.setVerticalGroup(
            Tab_inventoryUserLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, Tab_inventoryUserLayout.createSequentialGroup()
                .addContainerGap(40, Short.MAX_VALUE)
                .addComponent(jLabel15)
                .addGap(31, 31, 31)
                .addGroup(Tab_inventoryUserLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(Inventory_searchUser_TxtField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel39))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane7, javax.swing.GroupLayout.PREFERRED_SIZE, 506, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(26, 26, 26))
        );

        Program_Tabs.add(Tab_inventoryUser, "inventoryTabUser_Card");

        Tab_sales.setBackground(new java.awt.Color(255, 255, 255));

        RevenueSalesPanel.setBackground(new java.awt.Color(172, 223, 253));
        RevenueSalesPanel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                RevenueSalesPanelMouseClicked(evt);
            }
        });
        RevenueSalesPanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        totalRevenue.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        totalRevenue.setText("50,000.00");
        RevenueSalesPanel.add(totalRevenue, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 80, 210, -1));

        jLabel18.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel18.setText("Annual Revenue");
        RevenueSalesPanel.add(jLabel18, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 50, -1, -1));

        jLabel16.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel16.setText("PHP");
        RevenueSalesPanel.add(jLabel16, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 80, -1, -1));

        jLabel17.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Elements/Icons/Revenue.png"))); // NOI18N
        RevenueSalesPanel.add(jLabel17, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 20, 20, 20));

        ProfitPanel.setBackground(new java.awt.Color(195, 172, 253));
        ProfitPanel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                ProfitPanelMouseClicked(evt);
            }
        });
        ProfitPanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel19.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Elements/Icons/Revenue.png"))); // NOI18N
        ProfitPanel.add(jLabel19, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 20, 20, 20));

        jLabel20.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel20.setText("Annual Profit");
        ProfitPanel.add(jLabel20, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 50, -1, -1));

        totalProfit.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        totalProfit.setText("10,000.00");
        ProfitPanel.add(totalProfit, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 80, 210, -1));

        jLabel22.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel22.setText("PHP");
        ProfitPanel.add(jLabel22, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 80, -1, -1));

        ReceiptsPanel.setBackground(new java.awt.Color(253, 172, 235));
        ReceiptsPanel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                ReceiptsPanelMouseClicked(evt);
            }
        });
        ReceiptsPanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel23.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Elements/Icons/Revenue.png"))); // NOI18N
        ReceiptsPanel.add(jLabel23, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 20, 20, 20));

        jLabel24.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel24.setText("Receipts");
        ReceiptsPanel.add(jLabel24, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 50, -1, -1));

        totalReceipt.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        totalReceipt.setText("1");
        ReceiptsPanel.add(totalReceipt, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 80, 270, -1));

        SalesCard.setLayout(new java.awt.CardLayout());

        RevenueAndProfitInformation.setBackground(new java.awt.Color(255, 255, 255));

        jLabel29.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        jLabel29.setText("Search");

        sales_iteamSearch_TxtField.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                sales_iteamSearch_TxtFieldKeyReleased(evt);
            }
        });

        Sales_revenueProfitViewer_Table.setBorder(new javax.swing.border.MatteBorder(null));
        Sales_revenueProfitViewer_Table.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Date", "ID", "Name", "Quantity", "Price", "Total Price", "Profit +"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.Integer.class, java.lang.String.class, java.lang.Integer.class, java.lang.Double.class, java.lang.Double.class, java.lang.Double.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        Sales_revenueProfitViewer_Table.setOpaque(false);
        Sales_revenueProfitViewer_Table.getTableHeader().setResizingAllowed(false);
        Sales_revenueProfitViewer_Table.getTableHeader().setReorderingAllowed(false);
        jScrollPane3.setViewportView(Sales_revenueProfitViewer_Table);
        Sales_revenueProfitViewer_Table.getColumnModel().getColumn(0).setHeaderRenderer(centerRenderer);
        Sales_revenueProfitViewer_Table.getColumnModel().getColumn(1).setHeaderRenderer(centerRenderer);
        Sales_revenueProfitViewer_Table.getColumnModel().getColumn(2).setHeaderRenderer(centerRenderer);
        Sales_revenueProfitViewer_Table.getColumnModel().getColumn(3).setHeaderRenderer(centerRenderer);
        Sales_revenueProfitViewer_Table.getColumnModel().getColumn(4).setHeaderRenderer(rightRenderer);
        Sales_revenueProfitViewer_Table.getColumnModel().getColumn(5).setHeaderRenderer(rightRenderer);
        Sales_revenueProfitViewer_Table.getColumnModel().getColumn(6).setHeaderRenderer(rightRenderer);

        Sales_revenueProfitViewer_Table.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);
        Sales_revenueProfitViewer_Table.getColumnModel().getColumn(1).setCellRenderer(centerRenderer);
        Sales_revenueProfitViewer_Table.getColumnModel().getColumn(2).setCellRenderer(leftRenderer);
        Sales_revenueProfitViewer_Table.getColumnModel().getColumn(3).setCellRenderer(centerRenderer);
        Sales_revenueProfitViewer_Table.getColumnModel().getColumn(4).setCellRenderer(rightRenderer);
        Sales_revenueProfitViewer_Table.getColumnModel().getColumn(5).setCellRenderer(rightRenderer);
        Sales_revenueProfitViewer_Table.getColumnModel().getColumn(6).setCellRenderer(rightRenderer);

        Sales_revenueProfitViewer_Table.setEnabled(false);

        jLabel27.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel27.setText("REVENUE:");

        Sales_totalRevenue_Txt.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        Sales_totalRevenue_Txt.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        Sales_totalRevenue_Txt.setText("50,000.00");

        jLabel31.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel31.setText("PROFIT:");

        Sales_totalProfit_Txt.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        Sales_totalProfit_Txt.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        Sales_totalProfit_Txt.setText("10,000.00");

        javax.swing.GroupLayout RevenueAndProfitInformationLayout = new javax.swing.GroupLayout(RevenueAndProfitInformation);
        RevenueAndProfitInformation.setLayout(RevenueAndProfitInformationLayout);
        RevenueAndProfitInformationLayout.setHorizontalGroup(
            RevenueAndProfitInformationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(RevenueAndProfitInformationLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(RevenueAndProfitInformationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(RevenueAndProfitInformationLayout.createSequentialGroup()
                        .addComponent(jLabel29)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(sales_iteamSearch_TxtField, javax.swing.GroupLayout.PREFERRED_SIZE, 131, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 973, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, RevenueAndProfitInformationLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jLabel27)
                        .addGap(18, 18, 18)
                        .addComponent(Sales_totalRevenue_Txt, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(35, 35, 35)
                        .addComponent(jLabel31, javax.swing.GroupLayout.PREFERRED_SIZE, 74, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(Sales_totalProfit_Txt, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        RevenueAndProfitInformationLayout.setVerticalGroup(
            RevenueAndProfitInformationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(RevenueAndProfitInformationLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(RevenueAndProfitInformationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(sales_iteamSearch_TxtField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel29))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 346, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(RevenueAndProfitInformationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel27)
                    .addComponent(Sales_totalRevenue_Txt)
                    .addComponent(jLabel31)
                    .addComponent(Sales_totalProfit_Txt))
                .addContainerGap(19, Short.MAX_VALUE))
        );

        SalesCard.add(RevenueAndProfitInformation, "revenueProfit_Card");

        ReceiptInformation.setBackground(new java.awt.Color(255, 255, 255));

        jLabel25.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        jLabel25.setText("Search");

        Sales_receiptSearch_TxtField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Sales_receiptSearch_TxtFieldActionPerformed(evt);
            }
        });
        Sales_receiptSearch_TxtField.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                Sales_receiptSearch_TxtFieldKeyReleased(evt);
            }
        });

        Sales_receiptList_Table.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        Sales_receiptList_Table.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Date", "Reference #"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        Sales_receiptList_Table.setOpaque(false);
        Sales_receiptList_Table.getTableHeader().setResizingAllowed(false);
        Sales_receiptList_Table.getTableHeader().setReorderingAllowed(false);
        Sales_receiptList_Table.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                Sales_receiptList_TableMouseClicked(evt);
            }
        });
        Sales_receiptList_Table.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Sales_receiptList_TableKeyPressed(evt);
            }
        });
        jScrollPane4.setViewportView(Sales_receiptList_Table);
        Sales_receiptList_Table.getColumnModel().getColumn(0).setHeaderRenderer(centerRenderer);
        Sales_receiptList_Table.getColumnModel().getColumn(1).setHeaderRenderer(centerRenderer);

        Sales_receiptList_Table.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);
        Sales_receiptList_Table.getColumnModel().getColumn(1).setCellRenderer(centerRenderer);

        Sales_receiptList_Table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        Sales_ReceiptViewer_TxtArea.setFont(new java.awt.Font("Monospaced", 0, 12)); // NOI18N
        jScrollPane5.setViewportView(Sales_ReceiptViewer_TxtArea);
        Sales_ReceiptViewer_TxtArea.setEditable(false);

        javax.swing.GroupLayout ReceiptInformationLayout = new javax.swing.GroupLayout(ReceiptInformation);
        ReceiptInformation.setLayout(ReceiptInformationLayout);
        ReceiptInformationLayout.setHorizontalGroup(
            ReceiptInformationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ReceiptInformationLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(ReceiptInformationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(ReceiptInformationLayout.createSequentialGroup()
                        .addComponent(jLabel25)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(Sales_receiptSearch_TxtField, javax.swing.GroupLayout.PREFERRED_SIZE, 131, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(ReceiptInformationLayout.createSequentialGroup()
                        .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jScrollPane5, javax.swing.GroupLayout.DEFAULT_SIZE, 511, Short.MAX_VALUE)))
                .addContainerGap())
        );
        ReceiptInformationLayout.setVerticalGroup(
            ReceiptInformationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ReceiptInformationLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(ReceiptInformationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(Sales_receiptSearch_TxtField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel25))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(ReceiptInformationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane5, javax.swing.GroupLayout.DEFAULT_SIZE, 389, Short.MAX_VALUE)
                    .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addContainerGap())
        );

        SalesCard.add(ReceiptInformation, "receiptInfo_Card");

        javax.swing.GroupLayout Tab_salesLayout = new javax.swing.GroupLayout(Tab_sales);
        Tab_sales.setLayout(Tab_salesLayout);
        Tab_salesLayout.setHorizontalGroup(
            Tab_salesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(Tab_salesLayout.createSequentialGroup()
                .addGap(38, 38, 38)
                .addGroup(Tab_salesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(SalesCard, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(Tab_salesLayout.createSequentialGroup()
                        .addComponent(RevenueSalesPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 319, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(ProfitPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 319, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(ReceiptsPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 319, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(39, Short.MAX_VALUE))
        );
        Tab_salesLayout.setVerticalGroup(
            Tab_salesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(Tab_salesLayout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(Tab_salesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(ProfitPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 148, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(RevenueSalesPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 148, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(ReceiptsPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 148, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(SalesCard, javax.swing.GroupLayout.PREFERRED_SIZE, 442, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(47, Short.MAX_VALUE))
        );

        Program_Tabs.add(Tab_sales, "salesTab_Card");

        javax.swing.GroupLayout FunctionsCardLayout = new javax.swing.GroupLayout(FunctionsCard);
        FunctionsCard.setLayout(FunctionsCardLayout);
        FunctionsCardLayout.setHorizontalGroup(
            FunctionsCardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1250, Short.MAX_VALUE)
            .addGroup(FunctionsCardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(FunctionsCardLayout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(SidePanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, 0)
                    .addComponent(Program_Tabs, javax.swing.GroupLayout.PREFERRED_SIZE, 1070, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );
        FunctionsCardLayout.setVerticalGroup(
            FunctionsCardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 670, Short.MAX_VALUE)
            .addGroup(FunctionsCardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(FunctionsCardLayout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addGroup(FunctionsCardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(SidePanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(Program_Tabs, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGap(0, 0, Short.MAX_VALUE)))
        );

        MainCardLayout.add(FunctionsCard, "FunctionsCard");

        getContentPane().add(MainCardLayout, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1250, 670));

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents


    // - - - - - - - - - - - - SIDE PANEL CODES - - - - - - - - - - - - //
    
    
    private void SidePanel_main_BtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SidePanel_main_BtnActionPerformed
        SidePanel_main_Btn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Elements/SidePanel_Buttons/Selected.png")));
        SidePanel_main_Btn.setForeground(Color.black);
        
        SidePanel_inventory_Btn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Elements/SidePanel_Buttons/Default.png")));
        SidePanel_inventory_Btn.setForeground(Color.white);
        
        SidePanel_sales_Btn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Elements/SidePanel_Buttons/Default.png")));
        SidePanel_sales_Btn.setForeground(Color.white);
        
        cardProgramTabs.show(Program_Tabs, "mainTab_Card");
        
    }//GEN-LAST:event_SidePanel_main_BtnActionPerformed

    private void SidePanel_inventory_BtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SidePanel_inventory_BtnActionPerformed
        SidePanel_main_Btn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Elements/SidePanel_Buttons/Default.png")));
        SidePanel_main_Btn.setForeground(Color.white);
        
        SidePanel_inventory_Btn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Elements/SidePanel_Buttons/Selected.png")));
        SidePanel_inventory_Btn.setForeground(Color.black);
        
        SidePanel_sales_Btn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Elements/SidePanel_Buttons/Default.png")));
        SidePanel_sales_Btn.setForeground(Color.white);
        
        if(isManager){
            cardProgramTabs.show(Program_Tabs, "inventoryTab_Card");
        }else{
           cardProgramTabs.show(Program_Tabs, "inventoryTabUser_Card"); 
        }
        
    }//GEN-LAST:event_SidePanel_inventory_BtnActionPerformed

    private void SidePanel_sales_BtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SidePanel_sales_BtnActionPerformed
        SidePanel_main_Btn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Elements/SidePanel_Buttons/Default.png")));
        SidePanel_main_Btn.setForeground(Color.white);
        
        SidePanel_inventory_Btn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Elements/SidePanel_Buttons/Default.png")));
        SidePanel_inventory_Btn.setForeground(Color.white);
        
        SidePanel_sales_Btn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Elements/SidePanel_Buttons/Selected.png")));
        SidePanel_sales_Btn.setForeground(Color.black);
        
        cardProgramTabs.show(Program_Tabs, "salesTab_Card");
        
        loadSalesData();
    }//GEN-LAST:event_SidePanel_sales_BtnActionPerformed

  
    // - - - - - - - - - - - - MAIN TABS CODES - - - - - - - - - - - - //
    
    
    private void Main_itmID_TxtFieldKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Main_itmID_TxtFieldKeyReleased
    
        if(IDInventory.contains(Main_itmID_TxtField.getText())) {
            
            int itemIDIndex = IDInventory.indexOf(Main_itmID_TxtField.getText());
            
            Main_itmName_TxtField.setText(nameInventory.get(itemIDIndex));
            Main_itmPrice_TxtField.setText(formatCurrency(roundNumbers(salePriceInventory.get(itemIDIndex))));
            Main_availableStock_Indicator.setText("" + stockInventory.get(itemIDIndex));
            
        } else {
            
            Main_itmName_TxtField.setText("");
            Main_itmPrice_TxtField.setText("");
            Main_availableStock_Indicator.setText("0");
            
        }
        
    }//GEN-LAST:event_Main_itmID_TxtFieldKeyReleased

    private void Main_itmQuantity_TxtFieldKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Main_itmQuantity_TxtFieldKeyPressed
        
        //ALLOWS ONLY NUMBER TO BE INPUTTED
        
        String value = Main_itmQuantity_TxtField.getText();
        
        int l = value.length();
        
        if (evt.getKeyChar() >= '0' && evt.getKeyChar() <= '9' || evt.getKeyChar() == 8) {
            Main_itmQuantity_TxtField.setEditable(true);
        } else {
            Main_itmQuantity_TxtField.setEditable(false);
        }
    }//GEN-LAST:event_Main_itmQuantity_TxtFieldKeyPressed

    private void Main_itmQuantity_TxtFieldKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Main_itmQuantity_TxtFieldKeyReleased
        
        //CHECK IF THERE IS SUFFICIENT STOCK OF THE ITEM
        
        // To make sure first that the item is available. - ITEMSTOCK & ITEMNAME
        if(!Main_itmQuantity_TxtField.getText().isEmpty() && !Main_itmName_TxtField.getText().isEmpty()){
            
            int stockToBuyInput = Integer.parseInt(Main_itmQuantity_TxtField.getText());
            int currentStockOfItem = Integer.parseInt(Main_availableStock_Indicator.getText());
        
            if(currentStockOfItem < stockToBuyInput){
                Main_insufficientStock_Indicator.setVisible(true);
                Main_Add_Btn.setEnabled(false);
            }else{
                Main_insufficientStock_Indicator.setVisible(false);
                Main_Add_Btn.setEnabled(true);
            }
            
        }   
        
        // If Main_itmQuantity_TxtField is empty, Main_Add_Button will be disabled.
        if(Main_itmQuantity_TxtField.getText().trim().length() == 0 || 
           Main_itmQuantity_TxtField.getText().trim().equals("0")) {
            Main_Add_Btn.setEnabled(false);
        }
        
    }//GEN-LAST:event_Main_itmQuantity_TxtFieldKeyReleased

    private void Main_Add_BtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Main_Add_BtnActionPerformed

        try{
           if(!Main_itmID_TxtField.getText().isEmpty()         &&
           !Main_itmName_TxtField.getText().isEmpty()       && 
           !Main_itmPrice_TxtField.getText().isEmpty()      && 
           !Main_itmQuantity_TxtField.getText().isEmpty()   &&
           !Main_itmQuantity_TxtField.getText().equals("0") ){
            
                LocalDate localDate = LocalDate.now();
            
                // GETTING INDEX - To Use for Accessing Data From ArrayList [INVENTORY].
                // Used For      - Inventory [Database]
                int indexForInventoryArrayList = IDInventory.indexOf(Main_itmID_TxtField.getText());

                // COMPUTATIONS
                double  originalPriceTotal  = originalPriceInventory.get(indexForInventoryArrayList);
                int     quantityTotal       = Integer.parseInt(Main_itmQuantity_TxtField.getText());
                double  subTotal            = salePriceInventory.get(indexForInventoryArrayList) * quantityTotal;

                // SETTING VALUES - Insert To ArrayList [CART ITEM].
                dateCartItem.add(localDate.toString());
                IDCartItem.add(IDInventory.get(indexForInventoryArrayList));
                nameCartItem.add(nameInventory.get(indexForInventoryArrayList));
                quantityCartItem.add(quantityTotal);
                originalPriceCartItem.add(originalPriceTotal);
                salePriceCartItem.add(salePriceInventory.get(indexForInventoryArrayList));
                subTotalCartItem.add(subTotal);
                isVatableCartItem.add(isVatableInventory.get(indexForInventoryArrayList));
                
                // FORMULA FOR PROFIT
                
                // GETTING INDEX - To Use for Accessing Data From ArrayList [CART ITEM].
                // Used For      - Cart Item [Database]
                int indexForCartItemArrayList = mainCart.getRowCount();

                mainCart.addRow(new Object[]{
                                                IDCartItem.get(indexForCartItemArrayList), 
                                                nameCartItem.get(indexForCartItemArrayList), 
                                                quantityCartItem.get(indexForCartItemArrayList), 
                                                originalPriceCartItem.get(indexForCartItemArrayList), 
                                                salePriceCartItem.get(indexForCartItemArrayList), 
                                                subTotalCartItem.get(indexForCartItemArrayList), 
                                                isVatableCartItem.get(indexForCartItemArrayList)
                                            });

                clearMainTab();
                Main_Add_Btn.setEnabled(false); // Disable ulit yung "ADD BUTTON" after adding.
                
                // CHECK - Kung nakapasok sa History ArrayList lahat.
                
                    for(int a = 0; a < IDCartItem.size(); a++) {
                    System.out.println(         IDCartItem.get(a) + " " +  
                                                nameCartItem.get(a) + " " +  
                                                quantityCartItem.get(a) + " " +   
                                                originalPriceCartItem.get(a) + " " +  
                                                salePriceCartItem.get(a) + " " +  
                                                subTotalCartItem.get(a) + " " +  
                                                isVatableCartItem.get(a));
                }
                System.out.println();
                
                // COMPUTATION - Subtract yung quantity na inadd ni user sa Database.
                
                stockInventory.set(indexForInventoryArrayList, stockInventory.get(indexForInventoryArrayList) - quantityTotal);
                Main_availableStock_Indicator.setText("" + stockInventory.get(indexForInventoryArrayList));
                
                // Refresh Inventory System
                InsertArrayListToTxtFileToSystem();
                
                // COMPUTATION - Subtract yung quantity na inadd ni user sa Database.
                
                double cartTotalResult = 0;
                for(int a = 0; a < IDCartItem.size(); a++) {
                    cartTotalResult += subTotalCartItem.get(a);
                }
                
                Main_cartTotal_txt.setText(formatCurrency(roundNumbers(cartTotalResult)));
                
                Main_availableStock_Indicator.setText("0");
                
                // Insert to CART.txt
                if(!isManager) {
                    insertToCartTxtForRecovery();
                }
                
            }   
        }catch(Exception e){
            
        }
         
    }//GEN-LAST:event_Main_Add_BtnActionPerformed

    public void insertToCartTxtForRecovery() {
        
        String toInsertToCARTTXT = "";
        
        for(int a = 0; a < IDCartItem.size(); a++) {
            
            toInsertToCARTTXT += ( dateCartItem.get(a) + ","
                                 + IDCartItem.get(a) + ","
                                 + nameCartItem.get(a) + ","
                                 + quantityCartItem.get(a) + ","
                                 + originalPriceCartItem.get(a) + ","
                                 + salePriceCartItem.get(a) + ","
                                 + subTotalCartItem.get(a) + ","
                                 + isVatableCartItem.get(a) + "\r\n" );
            
        }
        
        try {
            
            FileWriter writeCART = new FileWriter("Cart.txt");
            writeCART.write(toInsertToCARTTXT);
            writeCART.close();
            
            
        } catch(Exception catchException) {
            
            System.out.println("ERROR - Inserting to CART.txt (ADD BUTTON)");
            
        }
        
    }
    
    private void Main_remove_BtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Main_remove_BtnActionPerformed
        
        //DELETE SELECTED ROW 
        
        try{
           int delRow = Main_cartViewer_Table.getSelectedRow();
        
        if(delRow >= 0){
            
            String IDNumberToBeDeleted = IDCartItem.get(delRow);                        // Kinuha ko ID Name/# ng nasa row na ma dedelete.
            int indexForInventoryArrayList = IDInventory.indexOf(IDNumberToBeDeleted);  // Hinanap ko siya sa Inventory Database (ArrayList).
            
            // Ibalik yung quantity na nabawas and idagdag ulit sa inventory.
            stockInventory.set(
                                    indexForInventoryArrayList, 
                                    stockInventory.get(indexForInventoryArrayList) + quantityCartItem.get(delRow)
                              );
            
            // Refresh Inventory System
            InsertArrayListToTxtFileToSystem();
            
            // Refresh Quantity Stock - Found At Main User Area
            Main_availableStock_Indicator.setText("" + stockInventory.get(indexForInventoryArrayList));
            
            dateCartItem.remove(delRow);
            IDCartItem.remove(delRow);
            nameCartItem.remove(delRow);
            quantityCartItem.remove(delRow);
            originalPriceCartItem.remove(delRow);
            salePriceCartItem.remove(delRow);
            subTotalCartItem.remove(delRow);
            isVatableCartItem.remove(delRow);
            
            mainCart.removeRow(delRow);
            
            double cartTotalResult = 0;
            for(int a = 0; a < IDCartItem.size(); a++) {
                cartTotalResult += subTotalCartItem.get(a);
            }
                
            Main_cartTotal_txt.setText(formatCurrency(roundNumbers(cartTotalResult)));
            Main_availableStock_Indicator.setText("0");
            
        } 
        }catch(Exception e){
            
        }
        
        if(!isManager) {
            insertToCartTxtForRecovery();
        }

    }//GEN-LAST:event_Main_remove_BtnActionPerformed

    private void Main_cancel_BtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Main_cancel_BtnActionPerformed
        
        try{
            Main_availableStock_Indicator.setText("0");
        
        // Put back stack/quantity to database.
        
        for(int a = 0; a < mainCart.getRowCount(); a++) {
            
            String IDContent = mainCart.getValueAt(a, 0).toString();
            int quantityContent = Integer.parseInt(mainCart.getValueAt(a, 2).toString());
            
            int indexOfIDinIDInventory = IDInventory.indexOf(IDContent);
            stockInventory.set(indexOfIDinIDInventory, stockInventory.get(indexOfIDinIDInventory) + quantityContent);
            
        }
        
        InsertArrayListToTxtFileToSystem();
        resetMainTab();
        
        dateCartItem.removeAll(dateCartItem);
        IDCartItem.removeAll(IDCartItem);
        nameCartItem.removeAll(nameCartItem);
        quantityCartItem.removeAll(quantityCartItem);
        originalPriceCartItem.removeAll(originalPriceCartItem);
        salePriceCartItem.removeAll(salePriceCartItem);
        subTotalCartItem.removeAll(subTotalCartItem);
        isVatableCartItem.removeAll(isVatableCartItem);
        
        mainCart.setRowCount(0);
        
        }catch(Exception e){
            
        }
        
        if(!isManager) {
            // DELETE CONTENTS of Cart.txt
            try {

                FileWriter writeCART = new FileWriter("Cart.txt");
                writeCART.write("");
                writeCART.close();


            } catch(Exception catchException) {

                System.out.println("ERROR - Inserting to CART.txt (CANCEL BUTTON)");

            }
        }
        
    }//GEN-LAST:event_Main_cancel_BtnActionPerformed

    private void Main_confirm_BtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Main_confirm_BtnActionPerformed
        
        //GO TO PAYMENT PAGE
        if(!isManager) {
            insertToCartTxtForRecovery();
        }
        
        if(Main_cartViewer_Table.getRowCount() > 0){
            cardMainTab.show(Tab_main, "Main_paymentInfo_Card");
            
            Main_paymentTotal_Txt.setText(formatCurrency(roundNumbers((cartTotal()))));
        }   
    }//GEN-LAST:event_Main_confirm_BtnActionPerformed

    private void Main_cashAmount_TxtFieldKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Main_cashAmount_TxtFieldKeyPressed
        
        //ALLOWS ONLY NUMBER TO BE INPUTTED
        
        String value = Main_cashAmount_TxtField.getText();
        
        int l = value.length();
        
        if (evt.getKeyChar() >= '0' && evt.getKeyChar() <= '9' || evt.getKeyChar() == 8) {
            Main_cashAmount_TxtField.setEditable(true);
        } else {
            Main_cashAmount_TxtField.setEditable(false);
        }
    }//GEN-LAST:event_Main_cashAmount_TxtFieldKeyPressed

    private void Main_pay_BtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Main_pay_BtnActionPerformed
        
        double cash = Double.parseDouble(Main_cashAmount_TxtField.getText());
        double amountToPay = Double.parseDouble(Main_paymentTotal_Txt.getText().replaceAll(",", ""));
        double changeAmount;
        
        if(cash < amountToPay){
            
            Main_InsufficientAmount_Indicator.setVisible(true); // Insufficient Amount
            
        }else{  // Correct Amount
            
            // DELETE CONTENT OF Cart.txt
            if(!isManager) {
                
                // DELETE CONTENT OF Cart.txt
                try {

                    FileWriter writeCART = new FileWriter("Cart.txt");
                    writeCART.write("");
                    writeCART.close();


                } catch(Exception catchException) {

                    System.out.println("ERROR - Inserting to CART.txt (PAYMENT SUCCEED)");

                }
                
            }
            
            // CREATION OF Reference Number
            LocalDate localDate = LocalDate.now();
            String referenceGenerated = "";
            
            do {
                
                int num1 = (int) (Math.random() * 9);
                int num2 = (int) (Math.random() * 9);
                int num3 = (int) (Math.random() * 9);
                int num4 = (int) (Math.random() * 9);
                int num5 = (int) (Math.random() * 9);
                int num6 = (int) (Math.random() * 9);
                int num7 = (int) (Math.random() * 9);
                int num8 = (int) (Math.random() * 9);
                int num9 = (int) (Math.random() * 9);
                
                referenceGenerated = "" + num1 + "" + num2 + "" + num3 + "" + num4 + 
                                     "" + num5 + "" + num6 + "" + num7 + "" + num8 + "" + num9;
                
            } while(referenceNumbers.contains(referenceGenerated));
            
            referenceNumbers.add(referenceGenerated);
            referenceNumbersDate.add(localDate.toString());
            
            for(int a = 0; a < dateCartItem.size(); a++) {
                
                dateHistory.add(dateCartItem.get(a));
                IDHistory.add(IDCartItem.get(a));
                nameHistory.add(nameCartItem.get(a));
                quantityHistory.add(quantityCartItem.get(a));
                originalPriceHistory.add(originalPriceCartItem.get(a));
                salePriceHistory.add(salePriceCartItem.get(a));
                subTotalHistory.add(subTotalCartItem.get(a));
                isVatableHistory.add(isVatableCartItem.get(a));
                
            }
            
            // PROFIT FORMULA
            double totalProfit = 0;
            
            for(int a = 0; a < IDCartItem.size(); a++) {
                if(isVatableCartItem.get(a).equals("V")) {
                    
                    totalProfit += ((subTotalCartItem.get(a) / 1.12) - 
                                    (originalPriceCartItem.get(a) * quantityCartItem.get(a)));
                    
                } else {
                    
                    totalProfit += (subTotalCartItem.get(a) - 
                                   (originalPriceCartItem.get(a) * quantityCartItem.get(a)));
                    
                }
                
                profitHistory.add(totalProfit);
                totalProfit = 0;    
            }
            
            double totalVatableSalesResult = 0;
            double totalVatExcemptSalesResult = 0;
            double totalVATSalesResult = 0;
            
            for(int a = 0; a < IDCartItem.size(); a++) {
                if(isVatableCartItem.get(a).equals("V")) {
                    totalVatableSalesResult += (subTotalCartItem.get(a) - (subTotalCartItem.get(a) * .12));
                }
            }
            
            for(int a = 0; a < IDCartItem.size(); a++) {
                if(isVatableCartItem.get(a).equals("NV")) {
                    totalVatExcemptSalesResult += (subTotalCartItem.get(a));
                }
            }
            
            for(int a = 0; a < IDCartItem.size(); a++) {
                if(isVatableCartItem.get(a).equals("V")) {
                    totalVATSalesResult += (subTotalCartItem.get(a) * .12);
                }
            }
            
            // Refreshing - SALES TAB
            InsertHistoryArrayListToTxtFileToSystem();
            
            // Refreshing - RECEIPTS TAB
            InsertReferenceArrayListToTxtFileToSystem();
            
            // P123
            dateCartItem.removeAll(dateCartItem);
            IDCartItem.removeAll(IDCartItem);
            nameCartItem.removeAll(nameCartItem);
            quantityCartItem.removeAll(quantityCartItem);
            originalPriceCartItem.removeAll(originalPriceCartItem);
            salePriceCartItem.removeAll(salePriceCartItem);
            subTotalCartItem.removeAll(subTotalCartItem);
            isVatableCartItem.removeAll(isVatableCartItem);
            
            
            // DITO YUNG CODE PAG SUFFICIENT PERA NI USER.
            
            changeAmount = cash - amountToPay;
            //GO TO RECEIPT PAGE
            
            //SAMPLE RECEIPT PRINTING
            Main_InsufficientAmount_Indicator.setVisible(false);
            cardMainTab.show(Tab_main, "Main_receiptInfo_Card");
            
            // RECEIPT FORMAT CODE
            String date = localDate.toString(); // insert date variable here
            Main_receiptNumRef_Txt.setText(referenceGenerated);
            String receiptHeader = "RECEIPT#" + referenceGenerated + "\n" +
                                   "Pentagon Enterprise\n" +
                                   date + "\n" +
                                   "-----------------------------------------\n" +
                                   "Pentagon Enterprise\n" +
                                   "VAT REG TIN 209-209-185-0050 \n" +
                                   "CITI ROSTAM PHILIPPINES 4506 \n" +
                                   "873 BLDG. MASTER STREET \n" +
                                   "PENTAGON, ROSTAM (NEW ALBAY NIEVO) \n" +
                                   "-----------------------------------------\n\n";
            String receiptInfo = String.format("%s", receiptHeader);
            Main_receipt_TxtPane.setText(receiptInfo);
            
            int idLength = getMaxNameLength(0);
            int nameLength = getMaxNameLength(1);
            int qtyLength = getMaxNameLength(2);
            int priceLength = getMaxNameLength(5);
            
            for(int i = 0; i < Main_cartViewer_Table.getRowCount(); i++){
                String receiptContents = String.format("%-" + idLength + "s  ", mainCart.getValueAt(i, 0).toString()) +
                                         String.format("%-" + nameLength + "s  ", mainCart.getValueAt(i, 1).toString()) +
                                         String.format("%-" + qtyLength + "s  ", mainCart.getValueAt(i, 2).toString()) +
                                         String.format("%-" + priceLength + "s  ", formatCurrency(roundNumbers(Double.parseDouble(mainCart.getValueAt(i, 5).toString())))) +
                                         String.format("%2s  \n\n", mainCart.getValueAt(i, 6).toString());
                Main_receipt_TxtPane.append(receiptContents);
            }
            
            String receiptFooter = "-----------------------------------------\n" +
                                   "VATABLE : " + formatCurrency(roundNumbers(totalVatableSalesResult)) + "\n" +
                                   "VAT EXEMPT : " + formatCurrency(roundNumbers(totalVatExcemptSalesResult)) + "\n" +
                                   "VAT : " + formatCurrency(roundNumbers(totalVATSalesResult)) + "\n\n" +
                                   "TOTAL  : " + formatCurrency(roundNumbers(cartTotal())) + "\n" +
                                   "CASH   : " + formatCurrency(roundNumbers(cash)) + "\n" +
                                   "CHANGE : " + formatCurrency(roundNumbers(changeAmount)) + "\n\n" +
                                   "Thank You For Shopping At Pentagon Enterprise";
            Main_receipt_TxtPane.append(String.format("%s", receiptFooter));
            
            // FILE CREATION
            String referenceFileName = referenceGenerated; // <- ASSUMING referenceGenerated is UNIQUE
            String newFileContents = String.format("%s", Main_receipt_TxtPane.getText());
            
            // Create a new file at project folder
            File outFile = new File("receiptFolder\\"+referenceFileName+".txt"); // <- Address of new file. THE NEW FOLDER IS ASSUMED TO ALREADY EXIST
            try
            {
                outFile.createNewFile();                        // <- create new file
                PrintWriter writer = new PrintWriter(outFile);  // <- Open Writer
                writer.write(newFileContents);                  // <- Place Contents
                writer.close();                                 // <- Close Writer to SAVE placed contents.
            }
            catch (Exception e) // <- file already exists/receipt file with same reference number
            {
                System.out.println("Reference Number Already Exists, cannot create receipt"); // Placeholder
            }
            
            Main_cashAmount_TxtField.setText("");
        }
    }//GEN-LAST:event_Main_pay_BtnActionPerformed

    private void Main_cancelPayment_BtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Main_cancelPayment_BtnActionPerformed
        
        //GO BACK TO MAIN PAGE
        
        Main_cashAmount_TxtField.setText("");
        cardMainTab.show(Tab_main, "Main_purchaseInfo_Card");
    }//GEN-LAST:event_Main_cancelPayment_BtnActionPerformed

    private void main_printReceipt_btnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_main_printReceipt_btnActionPerformed
        
        //GO BACK TO MAIN PAGE
        
        try {
            boolean finished = Main_receipt_TxtPane.print();
            
            if(finished){
                cardMainTab.show(Tab_main, "Main_purchaseInfo_Card");
                resetMainTab();
                
            }
            
        } catch(Exception catchException) {
            System.out.println("Error, Cannot Print Receipt!");
        }
    }//GEN-LAST:event_main_printReceipt_btnActionPerformed

    
    private void Main_receiptBack_BtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Main_receiptBack_BtnActionPerformed
        
        //GO BACK TO MAIN PAGE
        
        cardMainTab.show(Tab_main, "Main_purchaseInfo_Card");
        resetMainTab();
    }//GEN-LAST:event_Main_receiptBack_BtnActionPerformed

    
     // - - - - - - - - - - - - INVENTORY TABS CODES - - - - - - - - - - - - //
    
    
    private void Inventory_stockViewer_TableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Inventory_stockViewer_TableMouseClicked

        //DISPLATY SELECTED ROW
        
        if(isInventoryEditable){
            Inventory_stockViewer_Table.setEnabled(false);
        }else{
           int index = Inventory_stockViewer_Table.getSelectedRow();
            System.out.println(index);
        
            Inventory_itmID_TxtField.setText(Inventory_stockViewer_Table.getValueAt(index, 0).toString());
            Inventory_itmName_TxtField.setText(Inventory_stockViewer_Table.getValueAt(index, 1).toString());
            Inventory_itmStock_TxtField.setText(Inventory_stockViewer_Table.getValueAt(index, 2).toString());
            Inventory_itmOrigPrice_TxtField.setText(Inventory_stockViewer_Table.getValueAt(index, 3).toString());

            double indexOfSales = Double.parseDouble(Inventory_stockViewer_Table.getValueAt(index, 4).toString().replaceAll(",", ""));
            double tolerance = 0.01;
        
            if(Inventory_stockViewer_Table.getValueAt(index, 5).toString().equals("V")){
                double newResult = indexOfSales / 1.12;

                if (Math.abs(Math.round(newResult) - newResult) < tolerance) {
                    newResult = Math.round(newResult);
                }
                Inventory_itmSalePrice_TxtField.setText("" + formatCurrency(roundNumbers(newResult)));

                Inventory_Vat_Indicator.setSelected(true);
            }else{
                Inventory_itmSalePrice_TxtField.setText(Inventory_stockViewer_Table.getValueAt(index, 4).toString());
                Inventory_Vat_Indicator.setSelected(false);
            }

            inventoryItemSelected(); 
        }
    }//GEN-LAST:event_Inventory_stockViewer_TableMouseClicked

    private void Inventory_addStock_BtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Inventory_addStock_BtnActionPerformed
        
        //ONLY ADD STOCK
        
        try{
            if(!Inventory_itmStockAdder_TxtField.getText().isEmpty()) {
            
                int index = Inventory_stockViewer_Table.getSelectedRow();
                int currStock = Integer.parseInt(Inventory_itmStock_TxtField.getText());
                int addStock = Integer.parseInt(Inventory_itmStockAdder_TxtField.getText());

                int newStock = currStock + addStock;

                if(newStock < 0){
                    newStock = 0;
                }

                JOptionPane.showMessageDialog(null, "Stock has been succesfully added!");
                inventory.setValueAt(newStock, index, 2);
                Inventory_itmStockAdder_TxtField.setText("");

                // -------------------- Change Item Stock - Inventory.txt --------------------
                int itemIDArrayIndex = IDInventory.indexOf(Inventory_itmID_TxtField.getText());
                stockInventory.set(itemIDArrayIndex, newStock);
                // -------------------- Change Item Stock - Inventory.txt --------------------

                // -------------------- Data -> ArrayList -> TxtFile - Inventory System Table --------------------
                InsertArrayListToTxtFileToSystem();
                // -------------------- Data -> ArrayList -> TxtFile - Inventory System Table --------------------

                defaultInventoryTabState(); 
            
            }
        }catch(Exception e){
            
        } 
    }//GEN-LAST:event_Inventory_addStock_BtnActionPerformed

    private void Inventory_itmStockAdder_TxtFieldKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Inventory_itmStockAdder_TxtFieldKeyPressed
        
        //ALLOWS ONLY NUMBER TO BE INPUTTED
        
         if (evt.getKeyChar() >= '0' && evt.getKeyChar() <= '9' || evt.getKeyChar() == 8 ) {
            Inventory_itmStockAdder_TxtField.setEditable(true);
        } else {
            Inventory_itmStockAdder_TxtField.setEditable(false);
        }
         
        if(evt.getKeyChar() == '-' && Inventory_itmStockAdder_TxtField.getText().isEmpty()){
            Inventory_itmStockAdder_TxtField.setText("-");
        
        }else if(evt.getKeyChar() == '-' && !Inventory_itmStockAdder_TxtField.getText().isEmpty() && !Inventory_itmStockAdder_TxtField.getText().contains("-")){
            Inventory_itmStockAdder_TxtField.setText("-".concat(Inventory_itmStockAdder_TxtField.getText()));
        }
 
    }//GEN-LAST:event_Inventory_itmStockAdder_TxtFieldKeyPressed

    private void Inventory_itmOrigPrice_TxtFieldKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Inventory_itmOrigPrice_TxtFieldKeyPressed
        
        //ALLOWS ONLY NUMBER TO BE INPUTTED
        
        if(Inventory_itmOrigPrice_TxtField.getText().contains(".")){                            //DON'T ALLOW USER TO ENTER MORE THAN 1 '.'
            if (evt.getKeyChar() >= '0' && evt.getKeyChar() <= '9' || evt.getKeyChar() == 8) {
                Inventory_itmOrigPrice_TxtField.setEditable(true);
            } else {
                Inventory_itmOrigPrice_TxtField.setEditable(false);
            }
            
        }else{
            if (evt.getKeyChar() >= '0' && evt.getKeyChar() <= '9' || evt.getKeyChar() == 8 || evt.getKeyChar() == '.') {
                Inventory_itmOrigPrice_TxtField.setEditable(true);
            } else {
                Inventory_itmOrigPrice_TxtField.setEditable(false);
            }
        }
       
    }//GEN-LAST:event_Inventory_itmOrigPrice_TxtFieldKeyPressed

    private void Inventory_itmSalePrice_TxtFieldKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Inventory_itmSalePrice_TxtFieldKeyPressed
        
        if(Inventory_itmSalePrice_TxtField.getText().contains(".")){                            //DON'T ALLOW USER TO ENTER MORE THAN 1 '.'
            if (evt.getKeyChar() >= '0' && evt.getKeyChar() <= '9' || evt.getKeyChar() == 8) {
                Inventory_itmSalePrice_TxtField.setEditable(true);
            } else {
                Inventory_itmSalePrice_TxtField.setEditable(false);
            }
            
        }else{
            if (evt.getKeyChar() >= '0' && evt.getKeyChar() <= '9' || evt.getKeyChar() == 8 || evt.getKeyChar() == '.') {
                Inventory_itmSalePrice_TxtField.setEditable(true);
            } else {
                Inventory_itmSalePrice_TxtField.setEditable(false);
            }
        }
        
    }//GEN-LAST:event_Inventory_itmSalePrice_TxtFieldKeyPressed

    private void Inventory_itmStock_TxtFieldKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Inventory_itmStock_TxtFieldKeyPressed
        
        //ALLOWS ONLY NUMBER TO BE INPUTTED

        if (evt.getKeyChar() >= '0' && evt.getKeyChar() <= '9' || evt.getKeyChar() == 8) {
            Inventory_itmStock_TxtField.setEditable(true);
        } else {
            Inventory_itmStock_TxtField.setEditable(false);
        }
    }//GEN-LAST:event_Inventory_itmStock_TxtFieldKeyPressed

    private void Inventory_insert_BtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Inventory_insert_BtnActionPerformed
    
        try{
            if(!Inventory_itmID_TxtField.getText().isEmpty()        && 
           !Inventory_itmName_TxtField.getText().isEmpty()      && 
           !Inventory_itmStock_TxtField.getText().isEmpty()     && 
           !Inventory_itmOrigPrice_TxtField.getText().isEmpty() && 
           !Inventory_itmSalePrice_TxtField.getText().isEmpty() ){
            
                String temp = itmId(Inventory_itmID_TxtField.getText());

               if(!IDInventory.contains(temp)) {
                   
                    double salesPrice = Double.parseDouble(Inventory_itmSalePrice_TxtField.getText());

                    IDInventory.add(temp);
                    nameInventory.add(Inventory_itmName_TxtField.getText().toUpperCase());
                    stockInventory.add(Integer.parseInt(Inventory_itmStock_TxtField.getText()));
                    originalPriceInventory.add(Double.parseDouble(Inventory_itmOrigPrice_TxtField.getText()));

                    if(Inventory_Vat_Indicator.isSelected()) {

                        salesPrice = salesPrice + (salesPrice * .12);

                        salePriceInventory.add(salesPrice);
                        isVatableInventory.add("V");
                    } else {

                        salePriceInventory.add(Double.parseDouble(Inventory_itmSalePrice_TxtField.getText()));
                        isVatableInventory.add("NV");
                    }

                    InsertArrayListToTxtFileToSystem();
                    defaultInventoryTabState();

                    JOptionPane.showMessageDialog(null, "Item has been succesfully inserted!");   
               }
            }
        }catch(Exception e){
            
        }
    }//GEN-LAST:event_Inventory_insert_BtnActionPerformed
   
    private void Inventory_remove_BtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Inventory_remove_BtnActionPerformed
        
        int delRow = Inventory_stockViewer_Table.getSelectedRow();
        
        if(delRow >= 0){
            
            inventory.removeRow(delRow);
            
            // ----------------------- Remove Data -> ArrayList -> TxtFile -> Inventory Table -----------------------
            int itemIDIndexArray = IDInventory.indexOf(Inventory_itmID_TxtField.getText());
            
            IDInventory.remove(itemIDIndexArray);
            nameInventory.remove(itemIDIndexArray);
            stockInventory.remove(itemIDIndexArray);
            originalPriceInventory.remove(itemIDIndexArray);
            salePriceInventory.remove(itemIDIndexArray);
            isVatableInventory.remove(itemIDIndexArray);
            // ----------------------- Remove Data -> ArrayList -> TxtFile -> Inventory Table -----------------------

            InsertArrayListToTxtFileToSystem();
            defaultInventoryTabState();
            
            JOptionPane.showMessageDialog(null, "Item has been succesfully removed!");
                
        }  
    }//GEN-LAST:event_Inventory_remove_BtnActionPerformed

    int itemIDIndexArray;
    private void Inventory_edit_BtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Inventory_edit_BtnActionPerformed

        try{
            if(!isInventoryEditable){
            
            isInventoryEditable = true;
            Inventory_Editing_Indicator.setVisible(true);
             
            inventoryEditState();
            
            // ------------------------- SET DATA TO BE EDITED ON THE ELSE IF BLOCK -------------------------
            itemIDIndexArray = IDInventory.indexOf(Inventory_itmID_TxtField.getText());
            // ------------------------- SET DATA TO BE EDITED ON THE ELSE IF BLOCK -------------------------
        
            
            }else if(isInventoryEditable){

                if(!Inventory_itmID_TxtField.getText().isEmpty()        && 
                   !Inventory_itmName_TxtField.getText().isEmpty()      && 
                   !Inventory_itmOrigPrice_TxtField.getText().isEmpty() &&
                   !Inventory_itmSalePrice_TxtField.getText().isEmpty() && 
                   !Inventory_itmStock_TxtField.getText().isEmpty()     ){

                    // ------------------------- Edit Data -> ArrayList -> TxtFile -> Inventory Table -------------------------

                         String tempID = itmId(Inventory_itmID_TxtField.getText());

                    if(IDInventory.contains(tempID) && !IDInventory.get(itemIDIndexArray).equals(tempID)){
                        Inventory_IDExists_Indicator.setVisible(true);
                    }else{
                        Inventory_IDExists_Indicator.setVisible(false); 

                        Inventory_Editing_Indicator.setVisible(false);

                        IDInventory.set(itemIDIndexArray, tempID);
                        nameInventory.set(itemIDIndexArray, Inventory_itmName_TxtField.getText().toUpperCase());
                        stockInventory.set(itemIDIndexArray, Integer.parseInt(Inventory_itmStock_TxtField.getText()));
                        originalPriceInventory.set(itemIDIndexArray, Double.parseDouble(Inventory_itmOrigPrice_TxtField.getText().replaceAll(",", "")));
                        salePriceInventory.set(itemIDIndexArray, Double.parseDouble(Inventory_itmSalePrice_TxtField.getText().replaceAll(",", "")));

                        if(Inventory_Vat_Indicator.isSelected()) {  // From Non-Vatable to Vatable (NV -> V)

                            double salesContent = Double.parseDouble(Inventory_itmSalePrice_TxtField.getText().replaceAll(",", ""));
                            double newResult = salesContent + (salesContent * .12);

                            salePriceInventory.set(itemIDIndexArray, newResult);

                            isVatableInventory.set(itemIDIndexArray, "V");
                        } else {
                            isVatableInventory.set(itemIDIndexArray, "NV");
                        }

                        // ------------------------- Edit Data -> ArrayList -> TxtFile -> Inventory Table -------------------------

                        InsertArrayListToTxtFileToSystem();

                        defaultInventoryTabState();

                        JOptionPane.showMessageDialog(null, "Item has been succesfully edited!");
                        isInventoryEditable = false; 
                    }
                }   
            }
        }catch(Exception e){
            
        }
    }//GEN-LAST:event_Inventory_edit_BtnActionPerformed

    private void Inventory_clear_BtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Inventory_clear_BtnActionPerformed
        defaultInventoryTabState();
        Inventory_itmStockAdder_TxtField.setText("");
        Inventory_IDExists_Indicator.setVisible(false);
        
        isInventoryEditable = false;
        Inventory_Editing_Indicator.setVisible(false);
    }//GEN-LAST:event_Inventory_clear_BtnActionPerformed

    private void Inventory_search_TxtFieldKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Inventory_search_TxtFieldKeyReleased
        
        //SEARCH FOR ITEMS
        
        TableRowSorter<DefaultTableModel> filter = new TableRowSorter<DefaultTableModel>(inventory);
        Inventory_stockViewer_Table.setRowSorter(filter);
         
        filter.setRowFilter(RowFilter.regexFilter("^" + Inventory_search_TxtField.getText().toUpperCase(), 0,1));
        
    }//GEN-LAST:event_Inventory_search_TxtFieldKeyReleased

    
    // - - - - - - - - - - - - SALES TABS CODES - - - - - - - - - - - - //
    
    
    private void RevenueSalesPanelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_RevenueSalesPanelMouseClicked
        cardSalesTab.show(SalesCard, "revenueProfit_Card");
    }//GEN-LAST:event_RevenueSalesPanelMouseClicked

    private void ProfitPanelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_ProfitPanelMouseClicked
        cardSalesTab.show(SalesCard, "revenueProfit_Card");
    }//GEN-LAST:event_ProfitPanelMouseClicked

    private void ReceiptsPanelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_ReceiptsPanelMouseClicked
        cardSalesTab.show(SalesCard, "receiptInfo_Card");
    }//GEN-LAST:event_ReceiptsPanelMouseClicked

    private void sales_iteamSearch_TxtFieldKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_sales_iteamSearch_TxtFieldKeyReleased
        
        //SEARCH FOR ITEMS
        
        TableRowSorter<DefaultTableModel> filter = new TableRowSorter<DefaultTableModel>(salesHistory);
        Sales_revenueProfitViewer_Table.setRowSorter(filter);
        
        double revenueForItem = 0;
        double profitForItem = 0;
        
        for(int i = 0; i < Sales_revenueProfitViewer_Table.getRowCount(); i++){
            if(IDHistory.get(i).startsWith(sales_iteamSearch_TxtField.getText()) || nameHistory.get(i).toUpperCase().startsWith(sales_iteamSearch_TxtField.getText().toUpperCase())){
                revenueForItem += subTotalHistory.get(i);
                profitForItem += profitHistory.get(i);
            }
        }
         
        Sales_totalRevenue_Txt.setText(formatCurrency(revenueForItem));
        Sales_totalProfit_Txt.setText(formatCurrency(profitForItem));
        
        filter.setRowFilter(RowFilter.regexFilter("^" + sales_iteamSearch_TxtField.getText().toUpperCase(), 1,2));
        
        
        
    }//GEN-LAST:event_sales_iteamSearch_TxtFieldKeyReleased

    private void Sales_receiptSearch_TxtFieldKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Sales_receiptSearch_TxtFieldKeyReleased
        
        //SEARCH FOR ITEMS
        
        TableRowSorter<DefaultTableModel> filter = new TableRowSorter<DefaultTableModel>(salesReceipt);
        Sales_receiptList_Table.setRowSorter(filter);
        
         
        filter.setRowFilter(RowFilter.regexFilter("^" + Sales_receiptSearch_TxtField.getText(), 1));
        
    }//GEN-LAST:event_Sales_receiptSearch_TxtFieldKeyReleased

    private void Inventory_Vat_IndicatorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Inventory_Vat_IndicatorActionPerformed
        if(Inventory_Vat_Indicator.isSelected()) {
            System.out.println("VATABLE");
        } else {
            System.out.println("NOT VATABLE");
        }
    }//GEN-LAST:event_Inventory_Vat_IndicatorActionPerformed

    private void Sales_receiptList_TableKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Sales_receiptList_TableKeyPressed
        
    }//GEN-LAST:event_Sales_receiptList_TableKeyPressed

    private void Sales_receiptSearch_TxtFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Sales_receiptSearch_TxtFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_Sales_receiptSearch_TxtFieldActionPerformed

    private void Sales_receiptList_TableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Sales_receiptList_TableMouseClicked
        int selectedReferenceNumber = Sales_receiptList_Table.getSelectedRow();
        String referenceNumberSelected = Sales_receiptList_Table.getValueAt(selectedReferenceNumber, 1).toString();
        
        try {
            
            File getFile = new File("receiptFolder\\" + referenceNumberSelected + ".txt");
            Scanner scanFile = new Scanner(getFile);
            
            String toBePrintedText = "";
            while(scanFile.hasNextLine()) {
                
                toBePrintedText += scanFile.nextLine() + "\n";
                
            }
            
            Sales_ReceiptViewer_TxtArea.setText(toBePrintedText);
            
        } catch(Exception catchException) {
            
            System.out.println("ERROR CATCHED - Sales_receiptList_TableMouseClicked");
            
        }
    }//GEN-LAST:event_Sales_receiptList_TableMouseClicked

    private void Main_itmID_TxtFieldKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Main_itmID_TxtFieldKeyPressed
        //ALLOWS ONLY NUMBER TO BE INPUTTED
        
        if (evt.getKeyChar() >= '0' && evt.getKeyChar() <= '9' || evt.getKeyChar() == 8) {
            Main_itmID_TxtField.setEditable(true);
        } else {
            Main_itmID_TxtField.setEditable(false);
        }
    }//GEN-LAST:event_Main_itmID_TxtFieldKeyPressed

    private void Inventory_itmID_TxtFieldKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Inventory_itmID_TxtFieldKeyPressed
       
        if(Inventory_itmID_TxtField.getText().length() < 4){                                    //ALLOW ONLY 4 INPUTS IN THE ITEM TEXT FIELD
            if (evt.getKeyChar() >= '0' && evt.getKeyChar() <= '9' || evt.getKeyChar() == 8) {  //ALLOWS ONLY NUMBER TO BE INPUTTED
                Inventory_itmID_TxtField.setEditable(true);
            } else {
                Inventory_itmID_TxtField.setEditable(false);
            }
        }else{
            if(evt.getKeyChar() == 8){
                Inventory_itmID_TxtField.setEditable(true);
            }else{
                Inventory_itmID_TxtField.setEditable(false);
            }
        }
  
    }//GEN-LAST:event_Inventory_itmID_TxtFieldKeyPressed

    private void Inventory_itmID_TxtFieldKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Inventory_itmID_TxtFieldKeyReleased

        String tempID = itmId(Inventory_itmID_TxtField.getText());
            
        if(IDInventory.contains(tempID) && !IDInventory.get(itemIDIndexArray).equals(tempID) && isInventoryEditable){
            Inventory_IDExists_Indicator.setVisible(true);
            
        }else if(IDInventory.contains(tempID) && !isInventoryEditable){
            
            Inventory_IDExists_Indicator.setVisible(true);
        }else{
            
            Inventory_IDExists_Indicator.setVisible(false); 
         }
    }//GEN-LAST:event_Inventory_itmID_TxtFieldKeyReleased

    private void Login_login_BtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Login_login_BtnActionPerformed
        String password = "admin";
        char[] passcode = Login_Password_TxtField.getPassword();
        
        String loginCode = String.valueOf(passcode);
        
        if("ADMIN".equals(Login_account_DropDown.getSelectedItem().toString())){
           
           Login_Password_TxtField.setEnabled(true);
           
           if(loginCode.equals(password))    {
               
                isManager = true;
                mainCard.show(MainCardLayout, "FunctionsCard");
                
                // MAIN TAB 
                SidePanel_inventory_Btn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Elements/SidePanel_Buttons/Selected.png")));
                SidePanel_inventory_Btn.setForeground(Color.black);
                SidePanel_inventory_Btn.setText("INVENTORY");

                SidePanel_sales_Btn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Elements/SidePanel_Buttons/Default.png")));
                SidePanel_sales_Btn.setForeground(Color.white);
                
                cardProgramTabs.show(Program_Tabs, "inventoryTab_Card");
                
               
                SidePanel_main_Btn.setVisible(false);
                SidePanel_inventory_Btn.setVisible(true);
                SidePanel_sales_Btn.setVisible(true);
                
                Login_wrongPassword_Indicator.setVisible(false);
     
            }else{
                Login_wrongPassword_Indicator.setVisible(true);
            }
        }else{
            
           Login_Password_TxtField.setEnabled(false); 
           
           isManager = false;
           mainCard.show(MainCardLayout, "FunctionsCard");
           
            // MAIN TAB 
           SidePanel_main_Btn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Elements/SidePanel_Buttons/Selected.png")));
           SidePanel_main_Btn.setForeground(Color.black);

           SidePanel_inventory_Btn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Elements/SidePanel_Buttons/Default.png")));
           SidePanel_inventory_Btn.setForeground(Color.white);
           SidePanel_inventory_Btn.setText("VIEW ITEMS");

           SidePanel_sales_Btn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Elements/SidePanel_Buttons/Default.png")));
           SidePanel_sales_Btn.setForeground(Color.white);
           cardProgramTabs.show(Program_Tabs, "mainTab_Card");
           
           Login_wrongPassword_Indicator.setVisible(false);
           
           SidePanel_main_Btn.setVisible(true);
           SidePanel_inventory_Btn.setVisible(true);
           SidePanel_sales_Btn.setVisible(false);
           
           // CART - Action
           checkIfCartContainsONAPPOPEN();
       
        }
    }//GEN-LAST:event_Login_login_BtnActionPerformed

    public void checkIfCartContainsONAPPOPEN() {
        
        try {
            
            File fileCART = new File("Cart.txt");
            Scanner scanCART = new Scanner(fileCART);
            String cartContent = "";
            
            while(scanCART.hasNextLine()) {
                cartContent += scanCART.nextLine();
            }
            
            if(!cartContent.isEmpty()) {
                
                loadCARTItems();
                
            }
            
        } catch(Exception cartException) {
            
            System.out.println("ERROR - checkIfCartContainsONAPPOPEN() method.");
            System.out.println("ERROR - Checking if cart contains after logging in.");
            
        }
        
    }
    
    public void loadCARTItems() {
        
        try {
            
            File getCartFile = new File("Cart.txt");
            Scanner scanCartFile = new Scanner(getCartFile);
            
                dateCartItem.removeAll(dateCartItem);
                IDCartItem.removeAll(IDCartItem);
                nameCartItem.removeAll(nameCartItem);
                quantityCartItem.removeAll(quantityCartItem);
                originalPriceCartItem.removeAll(originalPriceCartItem);
                salePriceCartItem.removeAll(salePriceCartItem);
                subTotalCartItem.removeAll(subTotalCartItem);
                isVatableCartItem.removeAll(isVatableCartItem);
            
            while(scanCartFile.hasNextLine()) {
                
                String[] textPerLineOfCartTxt = scanCartFile.nextLine().split(",");
                
                dateCartItem.add(textPerLineOfCartTxt[0]);
                IDCartItem.add(textPerLineOfCartTxt[1]);
                nameCartItem.add(textPerLineOfCartTxt[2]);
                quantityCartItem.add(Integer.parseInt(textPerLineOfCartTxt[3]));
                originalPriceCartItem.add(Double.parseDouble(textPerLineOfCartTxt[4]));
                salePriceCartItem.add(Double.parseDouble(textPerLineOfCartTxt[5]));
                subTotalCartItem.add(Double.parseDouble(textPerLineOfCartTxt[6]));
                isVatableCartItem.add(textPerLineOfCartTxt[7]);
                
            }
            
            mainCart.setRowCount(0);
            
            for(int a = 0; a < dateCartItem.size(); a++) {
                mainCart.addRow(new Object[]{
                                                IDCartItem.get(a), 
                                                nameCartItem.get(a), 
                                                quantityCartItem.get(a), 
                                                originalPriceCartItem.get(a), 
                                                salePriceCartItem.get(a), 
                                                subTotalCartItem.get(a), 
                                                isVatableCartItem.get(a)
                                            });
            }
            
        } catch(Exception catchException) {
            
            System.out.println("ERROR - Inserting to Cart ArrayLists Database. (loadCARTItems)");
            
        }
        
    }
    
    private void Login_account_DropDownItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_Login_account_DropDownItemStateChanged
        
        if("ADMIN".equals(Login_account_DropDown.getSelectedItem().toString())){
            Login_Password_TxtField.setEnabled(true);
            Login_wrongPassword_Indicator.setVisible(false);
        
        }else if ("USER".equals(Login_account_DropDown.getSelectedItem().toString())){
            Login_Password_TxtField.setEnabled(false); 
            Login_Password_TxtField.setText(""); 
            Login_wrongPassword_Indicator.setVisible(false);
        }
    }//GEN-LAST:event_Login_account_DropDownItemStateChanged

    private void SidePanel_logout_BtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SidePanel_logout_BtnActionPerformed
        
        Login_Password_TxtField.setText("");
        defaultInventoryTabState();
        resetMainTab();
        clearSalesTab();
        
        mainCart.setRowCount(0);
        
        mainCard.show(MainCardLayout, "LoginCard");
    }//GEN-LAST:event_SidePanel_logout_BtnActionPerformed

    private void Inventory_searchUser_TxtFieldKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Inventory_searchUser_TxtFieldKeyReleased
        TableRowSorter<DefaultTableModel> filter = new TableRowSorter<DefaultTableModel>(inventoryUser);
        Inventory_stockViewerUser_Table.setRowSorter(filter);
         
        filter.setRowFilter(RowFilter.regexFilter("^" + Inventory_searchUser_TxtField.getText().toUpperCase(), 0,1));
    }//GEN-LAST:event_Inventory_searchUser_TxtFieldKeyReleased

    // - - - - - - - - - - - - OTHERS - - - - - - - - - - - - //
    
    //ADD 0 TO THE ID'S IF NECESSARY
    public String itmId(String temp){
         
        int length = temp.length();
        
        if(length < 4){
            for(int i = length; i < 4; i++){
                temp = temp.concat("0");
            }
        }
        return temp;
    }
    
    public void hideColumn(int index){
        Main_cartViewer_Table.getColumnModel().getColumn(index).setMinWidth(0);
        Main_cartViewer_Table.getColumnModel().getColumn(index).setMaxWidth(0);
        Main_cartViewer_Table.getColumnModel().getColumn(index).setWidth(0);
    }
    
    public void clearMainTab(){
        Main_itmID_TxtField.setText("");
        Main_itmName_TxtField.setText("");
        Main_itmPrice_TxtField.setText("");
        Main_itmQuantity_TxtField.setText("");
    }
    
    public void resetMainTab(){
        clearMainTab();
        mainCart.setRowCount(0);
        Main_cartTotal_txt.setText("0.00");
    }
    
    public void inventoryItemSelected(){
        Inventory_itmID_TxtField.setEnabled(false);
        Inventory_itmName_TxtField.setEnabled(false);
        Inventory_itmOrigPrice_TxtField.setEnabled(false);
        Inventory_itmSalePrice_TxtField.setEnabled(false);
        Inventory_itmStock_TxtField.setEnabled(false);
        Inventory_itmStockAdder_TxtField.setEnabled(true);
        Inventory_Vat_Indicator.setEnabled(false);
        
        Inventory_insert_Btn.setEnabled(false);
        Inventory_remove_Btn.setEnabled(true);
        Inventory_clear_Btn.setEnabled(true);
        Inventory_edit_Btn.setEnabled(true);
         Inventory_addStock_Btn.setEnabled(true);
    }
    
    public void inventoryEditState(){
        Inventory_itmID_TxtField.setEnabled(true);
        Inventory_itmName_TxtField.setEnabled(true);
        Inventory_itmOrigPrice_TxtField.setEnabled(true);
        Inventory_itmSalePrice_TxtField.setEnabled(true);
        Inventory_itmStock_TxtField.setEnabled(true);
        Inventory_itmStockAdder_TxtField.setEnabled(false);
        Inventory_Vat_Indicator.setEnabled(true);
        
        Inventory_clear_Btn.setText("BACK");

        Inventory_stockViewer_Table.setRowSelectionAllowed(false);
        Inventory_stockViewer_Table.setEnabled(false);
        
        Inventory_insert_Btn.setEnabled(false);
        Inventory_remove_Btn.setEnabled(false);
        Inventory_edit_Btn.setEnabled(true);
        Inventory_addStock_Btn.setEnabled(false);
    }
    
    public void defaultInventoryTabState(){
        Inventory_itmID_TxtField.setEnabled(true);
        Inventory_itmName_TxtField.setEnabled(true);
        Inventory_itmOrigPrice_TxtField.setEnabled(true);
        Inventory_itmSalePrice_TxtField.setEnabled(true);
        Inventory_itmStock_TxtField.setEnabled(true);
        Inventory_itmStockAdder_TxtField.setEnabled(false);
        Inventory_Vat_Indicator.setEnabled(true);
        Inventory_Vat_Indicator.setSelected(false);
        
        Inventory_clear_Btn.setText("CLEAR");
        Inventory_stockViewer_Table.setRowSelectionAllowed(true);
        Inventory_stockViewer_Table.setEnabled(true);
        
        
        Inventory_insert_Btn.setEnabled(true);
        Inventory_remove_Btn.setEnabled(false);
        Inventory_clear_Btn.setEnabled(true);
        Inventory_edit_Btn.setEnabled(false);
        Inventory_addStock_Btn.setEnabled(false);
        
        clearInventoryTab();
    }
    
    public void clearInventoryTab(){
        Inventory_itmID_TxtField.setText("");
        Inventory_itmName_TxtField.setText("");
        Inventory_itmOrigPrice_TxtField.setText("");
        Inventory_itmSalePrice_TxtField.setText("");
        Inventory_itmStock_TxtField.setText("");
        
        Inventory_stockViewer_Table.getSelectionModel().clearSelection();
    }
    
    public void clearSalesTab(){
        Sales_receiptList_Table.getSelectionModel().clearSelection();
        Sales_ReceiptViewer_TxtArea.setText("");
    }
      
    public double cartTotal(){
        double total = 0;
        
        for(int i = 0; i < Main_cartViewer_Table.getRowCount(); i++){
            total += Double.parseDouble(mainCart.getValueAt(i, 5).toString());
        }
        return total;
    }
    
    public void loadSalesData(){
        // Refresh Every Data   
        
        // -------------------------------- TOTAL REVENUE, PROFIT ---------------------------------
        double totalRevenueResult = 0;
        double totalProfitResult = 0;
        
        for(int a = 0; a < subTotalHistory.size(); a++) {
            totalRevenueResult += subTotalHistory.get(a);
        }
        for(int a = 0; a < profitHistory.size(); a++) {
            totalProfitResult += profitHistory.get(a);
        }
        // ----------------------------------- TOTAL RECEIPT(s) ------------------------------------ 
        int totalReceiptResult = 0;
        
        for(int a = 0; a < referenceNumbers.size(); a++) {
            totalReceiptResult += 1;
        }
        // ----------------------------------- TOTAL RECEIPT(s) ------------------------------------ 
        
        totalRevenue.setText(formatCurrency(roundNumbers(totalRevenueResult)));
        totalProfit.setText(formatCurrency(roundNumbers(totalProfitResult)));
        totalReceipt.setText("" + totalReceiptResult);
        Sales_totalRevenue_Txt.setText(formatCurrency(roundNumbers(totalRevenueResult)));
        Sales_totalProfit_Txt.setText(formatCurrency(roundNumbers(totalProfitResult)));
        
    }

    public int getMaxNameLength(int index) 
    {
        int i = 0;
        int max = mainCart.getValueAt(i, index).toString().length();
        if (Main_cartViewer_Table.getRowCount() == i)
        {
            // return max;
        }
        else
        {
            for (i = 1; i < Main_cartViewer_Table.getRowCount(); i++)
            {
                max = max < mainCart.getValueAt(i, index).toString().length() ?  mainCart.getValueAt(i, index).toString().length() : max;
            }
            // return max;
        }
        return max;
    }
    
    // FORMATTING AREA
    public static String formatCurrency(double number) {
	NumberFormat formatter = NumberFormat.getInstance();
	formatter.setCurrency(Currency.getInstance("PHP"));
	formatter.setMaximumFractionDigits(2);
	formatter.setMinimumFractionDigits(2);
		
	return formatter.format(number);
    }
    
    public static double roundNumbers(double number) {
	NumberFormat formatter = NumberFormat.getInstance();
	formatter.setMaximumFractionDigits(2);
	formatter.setMinimumFractionDigits(2);
		
	String formattedNumber = formatter.format(number);
	String temps[] = formattedNumber.split(",");
		
	formattedNumber = "";
	for (int i = 0; i < temps.length; i++) {
            formattedNumber += temps[i];
	}
		
	return Double.parseDouble(formattedNumber);
    }
    
    
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Main_Frame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Main_Frame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Main_Frame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Main_Frame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
       
        Main_Frame loading = new Main_Frame();
        loading.setVisible(true);
        /* Create and display the form */
        
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Main_Frame().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel FunctionsCard;
    private javax.swing.JLabel Inventory_Editing_Indicator;
    private javax.swing.JLabel Inventory_IDExists_Indicator;
    private javax.swing.JCheckBox Inventory_Vat_Indicator;
    private javax.swing.JButton Inventory_addStock_Btn;
    private javax.swing.JButton Inventory_clear_Btn;
    private javax.swing.JButton Inventory_edit_Btn;
    private javax.swing.JButton Inventory_insert_Btn;
    private javax.swing.JTextField Inventory_itmID_TxtField;
    private javax.swing.JTextField Inventory_itmName_TxtField;
    private javax.swing.JTextField Inventory_itmOrigPrice_TxtField;
    private javax.swing.JTextField Inventory_itmSalePrice_TxtField;
    private javax.swing.JTextField Inventory_itmStockAdder_TxtField;
    private javax.swing.JTextField Inventory_itmStock_TxtField;
    private javax.swing.JButton Inventory_remove_Btn;
    private javax.swing.JTextField Inventory_searchUser_TxtField;
    private javax.swing.JTextField Inventory_search_TxtField;
    private javax.swing.JTable Inventory_stockViewerUser_Table;
    private javax.swing.JTable Inventory_stockViewer_Table;
    private javax.swing.JPanel LoginCard;
    private javax.swing.JPasswordField Login_Password_TxtField;
    private javax.swing.JComboBox<String> Login_account_DropDown;
    private javax.swing.JButton Login_login_Btn;
    private javax.swing.JLabel Login_wrongPassword_Indicator;
    private javax.swing.JPanel MainCardLayout;
    private javax.swing.JButton Main_Add_Btn;
    private javax.swing.JLabel Main_InsufficientAmount_Indicator;
    private javax.swing.JLabel Main_availableStock_Indicator;
    private javax.swing.JButton Main_cancelPayment_Btn;
    private javax.swing.JButton Main_cancel_Btn;
    private javax.swing.JPanel Main_cartPanel;
    private javax.swing.JLabel Main_cartTotal_txt;
    private javax.swing.JTable Main_cartViewer_Table;
    private javax.swing.JTextField Main_cashAmount_TxtField;
    private javax.swing.JButton Main_confirm_Btn;
    private javax.swing.JPanel Main_inputPanel;
    private javax.swing.JLabel Main_insufficientStock_Indicator;
    private javax.swing.JTextField Main_itmID_TxtField;
    private javax.swing.JTextField Main_itmName_TxtField;
    private javax.swing.JTextField Main_itmPrice_TxtField;
    private javax.swing.JTextField Main_itmQuantity_TxtField;
    private javax.swing.JButton Main_pay_Btn;
    private javax.swing.JPanel Main_paymentInformation;
    private javax.swing.JPanel Main_paymentPanel;
    private javax.swing.JLabel Main_paymentTotal_Txt;
    private javax.swing.JPanel Main_purchaseInformation;
    private javax.swing.JButton Main_receiptBack_Btn;
    private javax.swing.JPanel Main_receiptInformation;
    private javax.swing.JLabel Main_receiptNumRef_Txt;
    private javax.swing.JTextArea Main_receipt_TxtPane;
    private javax.swing.JButton Main_remove_Btn;
    private javax.swing.JPanel ProfitPanel;
    private javax.swing.JPanel Program_Tabs;
    private javax.swing.JPanel ReceiptInformation;
    private javax.swing.JPanel ReceiptsPanel;
    private javax.swing.JPanel RevenueAndProfitInformation;
    private javax.swing.JPanel RevenueSalesPanel;
    private javax.swing.JPanel SalesCard;
    private javax.swing.JTextPane Sales_ReceiptViewer_TxtArea;
    private javax.swing.JTable Sales_receiptList_Table;
    private javax.swing.JTextField Sales_receiptSearch_TxtField;
    private javax.swing.JTable Sales_revenueProfitViewer_Table;
    private javax.swing.JLabel Sales_totalProfit_Txt;
    private javax.swing.JLabel Sales_totalRevenue_Txt;
    private javax.swing.JPanel SideButtons;
    private javax.swing.JPanel SidePanel;
    private javax.swing.JButton SidePanel_inventory_Btn;
    private javax.swing.JButton SidePanel_logout_Btn;
    private javax.swing.JButton SidePanel_main_Btn;
    private javax.swing.JButton SidePanel_sales_Btn;
    private javax.swing.JPanel Tab_inventory;
    private javax.swing.JPanel Tab_inventoryUser;
    private javax.swing.JPanel Tab_main;
    private javax.swing.JPanel Tab_sales;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel35;
    private javax.swing.JLabel jLabel36;
    private javax.swing.JLabel jLabel37;
    private javax.swing.JLabel jLabel38;
    private javax.swing.JLabel jLabel39;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JButton main_printReceipt_btn;
    private javax.swing.JTextField sales_iteamSearch_TxtField;
    private javax.swing.JLabel totalProfit;
    private javax.swing.JLabel totalReceipt;
    private javax.swing.JLabel totalRevenue;
    // End of variables declaration//GEN-END:variables
}