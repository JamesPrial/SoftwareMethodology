package ru.cs213.frontend ;

import ru.cs213.backend.*;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;

/**
 * A class defined that handles the ordering of Coffee
 * in a GUI when opened from the Main menu GUI. The defines
 * events that help in adding and removing add ins, picking the size
 * get the subtotal of the price.
 */

public class CoffeeController implements Initializable {

    @FXML
    private TextArea messageTextArea;

    @FXML
    private Button addToOrderButton;

    @FXML
    private TextField subTotalTextField;

    @FXML
    private ComboBox<String> sizeComboBox;

    @FXML
    private ListView<String> addInListView;

    @FXML
    private Button removeAddInsButton;

    @FXML
    private Spinner<Integer> creamSpinner;


    @FXML
    private Spinner<Integer> syrupSpinner;

    @FXML
    private Spinner<Integer> milkSpinner;

    @FXML
    private Spinner<Integer> caramelSpinner;

    @FXML
    private Spinner<Integer> whippedCreamSpinner;

    @FXML
    private Button addAddInsButton;

    @FXML
    private TextField quantityTextField;

    private final int initialValue = 0;

    private final int SHORT = 0;
    private final int TALL = 1;
    private final int GRANDE = 2;
    private final int VENTI = 3;
    private final int NOT_FOUND = -1;

    private Order order;
    private Coffee lastCoffee;

    SpinnerValueFactory<Integer> svf1 = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 20,initialValue);
    SpinnerValueFactory<Integer> svf2 = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 20,initialValue);
    SpinnerValueFactory<Integer> svf3 = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 20,initialValue);
    SpinnerValueFactory<Integer> svf4 = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 20,initialValue);
    SpinnerValueFactory<Integer> svf5 = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 20,initialValue);

    /**
     * A function that helps in picking the size of
     * the order and simultaneously display the price
     * according to the picked size
     * @param event clicking the combobox of size.
     */
    @FXML
    void sizeBoxHandler(ActionEvent event){
        int size = getSize();
        if(size == NOT_FOUND){
            subTotalTextField.setText("" + order.subtotal());
            return;
        }
        if(lastCoffee == null) {
            lastCoffee = new Coffee(size);
        }
        lastCoffee.setSize(size);
        double newSub = (int)(lastCoffee.itemPrice() * 100);
        newSub = (newSub/100.0) + order.subtotal();
        subTotalTextField.setText("" + newSub);
        return;
    }

    /**
     * A function that helps in adding the final order
     * of the coffee with the add ins to the order list
     * of both donuts and coffees
     * @param event clicking the add order button
     */
    @FXML
    void addOrderHandler(ActionEvent event) {
        int size = getSize();
        String quantText = quantityTextField.getText();
        int quant;
        if(quantText == null){
            printMsg("Enter quantity!");
            return;
        }
        try{
            quant = Integer.parseInt(quantText);
        }catch(NumberFormatException e){
            printMsg("Quantity must be an integer!");
            return;
        }
        if(quant <= 0){
            printMsg("Quantity must be greater than 0!");
            return;
        }
        if(lastCoffee == null){
            printMsg("Coffee will be added with no add ins");
            if(size == NOT_FOUND){
                printMsg("Please select size!");
                return;
            }
            lastCoffee = new Coffee(size);
        }
        if(size != NOT_FOUND || size != lastCoffee.getSize()){
            lastCoffee.setSize(size);
        }
        order.add(lastCoffee);
        for(int i = 1; i < quant; i++){
            order.add(lastCoffee.copy());
        }
        printMsg("Added " + quant + " " + lastCoffee.toString() +", " + (lastCoffee.itemPrice() * quant) + " Total");
        lastCoffee = null;
        refresh();
        return;
    }

    /**
     *
     * A function that helps in adding add ins to the coffee.
     * The user may add multiple instances of add ins to their coffee
     * using the spinners.
     *
     * @param event clicking the add addins button.
     */
    @FXML
    void addAddInsHandler(ActionEvent event) {
        if(lastCoffee == null){
            int size = getSize();
            if(size == NOT_FOUND) {
                printMsg("Please Select Size!");
                return;
            }
            lastCoffee = new Coffee(size);
        }
        int creamCt = creamSpinner.getValue();
        int syrupCt = syrupSpinner.getValue();
        int milkCt = milkSpinner.getValue();
        int caramelCt = caramelSpinner.getValue();
        int whippedCt = whippedCreamSpinner.getValue();
        if(creamCt < 0 || syrupCt < 0 || milkCt < 0 || caramelCt < 0 || whippedCt < 0){
            printMsg("Cannot have negative Add Ins!");
            lastCoffee = null;
            return;
        }
        if(!(addInDriver(lastCoffee, CoffeeAddIns.CREAM, creamCt) && addInDriver(lastCoffee, CoffeeAddIns.SYRUP, syrupCt) && addInDriver(lastCoffee, CoffeeAddIns.MILK, milkCt) && addInDriver(lastCoffee, CoffeeAddIns.CARAMEL, caramelCt) && addInDriver(lastCoffee, CoffeeAddIns.WHIPPED_CREAM, whippedCt)) ){
            printMsg("Adding failed!");
            return;
        }
        printMsg("Add ins selected!");
        addInListView.setItems(FXCollections.observableArrayList(lastCoffee.getAddInsAsStr()));
        double sub = round(lastCoffee.itemPrice() + order.subtotal());
        subTotalTextField.setText("" + sub);
        return;
    }

    /**
     * A function that helps in rounding
     * a double value to two decimal places.
     * @param num the number to be rounded
     * @return returns the new rounded value.
     */
    private double round(double num){
        int x = (int)(num * 100.0);
        return (double)(x / 100.0);
    }

    /**
     * A function defined to be able to remove add ins
     * from the coffee order
     * @param event clicking remove add ins button.
     */
    @FXML
    void removeAddInsHandler(ActionEvent event) {
        if(lastCoffee == null){
            return;
        }
        String selected = addInListView.getSelectionModel().getSelectedItem();
        CoffeeAddIns toRemove;
        if(selected == null){
            printMsg("Select an Add In to remove!");
            return;
        }
        if(selected.equals("CREAM")){
            toRemove = CoffeeAddIns.CREAM;
        }else if(selected.equals("SYRUP")){
            toRemove = CoffeeAddIns.SYRUP;
        }else if(selected.equals("MILK")){
            toRemove = CoffeeAddIns.MILK;
        }else if(selected.equals("CARAMEL")){
            toRemove = CoffeeAddIns.CARAMEL;
        }else if(selected.equals("WHIPPED_CREAM")){
            toRemove = CoffeeAddIns.WHIPPED_CREAM;
        }else{
            printMsg("Select Add In to Remove it! selected = " + selected);

            return;
        }
        if(lastCoffee.remove(toRemove)){
            printMsg("Removed!");
        }else{
            printMsg("Error removing!");
            return;
        }
        addInListView.setItems(FXCollections.observableArrayList(lastCoffee.getAddInsAsStr()));
        return;
    }

    /**
     * Function to initialize the GUI values to its defaults.
     * @param url
     * @param rb
     */
    @FXML
    public void initialize(URL url, ResourceBundle rb) {

        lastCoffee = null;

        sizeComboBox.getSelectionModel().selectFirst();
        subTotalTextField.appendText("0.00");
        creamSpinner.setValueFactory(svf1);
        caramelSpinner.setValueFactory(svf2);
        syrupSpinner.setValueFactory(svf3);
        whippedCreamSpinner.setValueFactory(svf4);
        milkSpinner.setValueFactory(svf5);


    }

    /**
     * A helper initializing method which helps in getting the
     * global current order arraylist.
     * @param order the current order object.
     */
    public void init(Order order){
        this.order = order;
        printMsg("Order = " + order.toString());
        subTotalTextField.setText("" + order.subtotal());
    }

    /**
     * A method to get the size of the coffee
     * @return an integer which gives the size.
     */
    private int getSize(){
        String size = sizeComboBox.getValue();
        if(size.equals("Short")){
            return SHORT;
        }else if(size.equals("Tall")){
            return TALL;
        }else if(size.equals("Grande")){
            return GRANDE;
        }else if(size.equals("Venti")){
            return VENTI;
        }else{
            return NOT_FOUND;
        }
    }

    /**
     * A method to display messages whenever an action is performed.
     * @param msg the string required to be appended.
     */
    private void printMsg(String msg){
        messageTextArea.appendText(msg + "\n");
        return;
    }

    /**
     * A heling method to test the addition of add ins.
     * @param coffee Coffee object
     * @param addIn Coffee add in enum object.
     * @param num the number of addins
     * @return boolean to check if addition was successful or not.
     */
    private boolean addInDriver(Coffee coffee, CoffeeAddIns addIn, int num){
        for(int i = 0; i < num; i++){
            if(!coffee.add(addIn)){
              return false;
            }
        }
        return true;
    }

    /**
     * A method to refresh the observable list and the subtotal.
     *
     */
    private void refresh(){
        addInListView.setItems(FXCollections.observableArrayList(""));
        subTotalTextField.setText("" + order.subtotal());
    }
}
