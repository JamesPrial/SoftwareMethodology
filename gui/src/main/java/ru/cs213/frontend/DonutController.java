package ru.cs213.frontend;

import ru.cs213.backend.*;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.TextArea;
import javafx.event.ActionEvent;

/**
 * A class defines the DonutController which helps the user
 * navigate through the Donut GUI and place the order
 * of a donut.
 *
 * @author Swarnendu Roy
 * @author James Prial
 *
 */
public class DonutController implements Initializable {

    private final int YEAST = 0;
    private final int CAKE = 1;
    private final int HOLE = 2;
    private final int CHOCOLATE = 0;
    private final int STRAWBERRY = 1;
    private final int VANILLA = 2;

    @FXML
    private RadioButton rbYeast;

    @FXML
    private ToggleGroup donutToggle;

    @FXML
    private RadioButton rbCake;

    @FXML
    private RadioButton rbHole;

    @FXML
    private ComboBox<String> flavorComboBox;

    @FXML
    private TextField quantityTextField;

    @FXML
    private Button addToOrderButton;

    @FXML
    private ListView<String> donutOrderListView;

    @FXML
    private Button removeOrderButton;

    @FXML
    private TextField subTotalTextField;

    @FXML
    private TextArea messageTextArea;

    private StoreOrders orders;
    private Order order;

    /**
     * A method to initalize the default values of the GUI
     *
     *
     * @param location
     * @param resources
     */
    @FXML
    public void initialize(URL location, ResourceBundle resources) {
        /*FXMLLoader loader = new FXMLLoader(getClass().getResource("/application/MainMenu.fxml"));
        try{
            loader.load();
        }
        catch(Exception e){
            System.err.println(e);
            return;
        }
        cont = loader.getController();
        orders = cont.getOrders();

        order = cont.getCurrentOrder();

         */
        ObservableList<String> options =
                FXCollections.observableArrayList(
                        "Chocolate",
                        "Strawberry",
                        "Vanilla"
                );
        flavorComboBox.getItems().clear();
        flavorComboBox.setItems(options);
        subTotalTextField.appendText("0.00");
        messageTextArea.setText("");
    }

    /**
     *
     * A helper initializing method to pass the global current order
     * object in the controller.
     *
     * @param order current order object from main menu controller.
     */
    public void init(Order order){
        this.order = order;
        donutOrderListView.setItems(FXCollections.observableArrayList(order.ordersAsArrayListString()));
        subTotalTextField.setText("" + order.subtotal());
    }

    /**
     *
     * A handler that handles the clicking
     * of add order button and then adds the donut to the order
     *
     *
     * @param event clicking add order button.
     */
    @FXML
    void addOrderHandler(ActionEvent event) {
        int type = getType();
        int flavor = getFlavor();
        int quantity = getQuantity();
        if(type == -1){
            messageTextArea.appendText("Donut Type Not Selected!\n");
            return;
        }else if(flavor == -1){
            messageTextArea.appendText("Donut Flavor Not Selected!\n");
            return;
        }else if(quantity == -1){
            messageTextArea.appendText("Invalid Quantity!\n");
        }
        DonutFlavors flavorOfDonut;
        switch(flavor){
            case CHOCOLATE: flavorOfDonut = DonutFlavors.CHOCOLATE; break;
            case STRAWBERRY: flavorOfDonut = DonutFlavors.STRAWBERRY; break;
            case VANILLA: flavorOfDonut = DonutFlavors.VANILLA; break;
            default:return;
        }
        MenuItem donut;
        switch(type) {
            case YEAST:
                donut = new YeastDonut(flavorOfDonut);
                break;
            case CAKE:
                donut = new CakeDonut(flavorOfDonut);
                break;
            case HOLE:
                donut = new DonutHole(flavorOfDonut);
                break;
            default:
                return;
        }
        for(int i = 0; i < quantity; i++) {
            order.add(donut);
        }



        donutOrderListView.setItems(FXCollections.observableArrayList(order.ordersAsArrayListString()));
        subTotalTextField.setText("" + order.subtotal());
        return;
    }

    /**
     *
     * A function that helps in getting the quantity
     * of donuts the user wants
     *
     * @return an integer value quantity
     */
    private int getQuantity(){
        String quant = quantityTextField.getText();
        if(quant.equals("")) {
            return -1;
        }
        try{
            int num = Integer.parseInt(quant);
            return num;
        }catch(Exception e) {
            return -1;
        }

    }

    /**
     *A method that helps in getting the type
     * of donut ordered.
     *
     * @return donut type as one of the three Donut class objects.
     */
    private int getType(){
        if(!isTypeSelected()){
            return -1;
        }
        if(rbYeast.isSelected()){
            return YEAST;
        }else if(rbCake.isSelected()){
            return CAKE;
        }else{
            return HOLE;
        }


    }

    /**
     * A method that helps getting the flavor
     * selected by the user.
     *
     * @return returns the enum of DonutFlavors.
     */
    private int getFlavor(){
        String flavor = (String)flavorComboBox.getValue();
        if(flavor == null){
            return -1;
        }
        if(flavor.equals("Chocolate")){
            return CHOCOLATE;
        }else if(flavor.equals("Strawberry")){
            return STRAWBERRY;
        }else if(flavor.equals("Vanilla")){
            return VANILLA;
        }else{
            return -1;
        }
    }

    /**
     * A check to make sure a type has been selected by the user,
     *
     * @return true if selected false otherwise.
     */
    private boolean isTypeSelected(){
        if(!rbCake.isSelected() && !rbHole.isSelected() && !rbYeast.isSelected()){
            return false;
        }else{
            return true;
        }
    }

    /**
     * A function that helps the user to remove a donut
     * from the GUI if it has been ordered.
     *
     * @param event clicking the remove order button
     */
    @FXML
    void removeOrderHandler(ActionEvent event) {
        String selected = donutOrderListView.getSelectionModel().getSelectedItem();
        if(selected == null){
            messageTextArea.appendText("Donut Not Selected!\n");
            return;
        }
        MenuItem toRemove;
        DonutFlavors flavor = parseFlavor(selected);
        if(selected.startsWith("Y")){
            toRemove = new YeastDonut(flavor);
            System.out.println("Y\n");
        } else if(selected.startsWith("C")){
            toRemove = new CakeDonut(flavor);
            System.out.println("C\n");
        }else{
            toRemove = new DonutHole(flavor);
            System.out.println("D\n");
        }
        if(order.remove(toRemove)){
            donutOrderListView.setItems(FXCollections.observableArrayList(order.ordersAsArrayListString()));
            subTotalTextField.setText("" + order.subtotal());
            return;
        }else{
            messageTextArea.appendText("Remove failed!\n");
            return;
        }
    }

    /**
     * A parsing method that helps to know
     * which donut has been selected for removal.
     *
     * @param toParse The string selected
     * @return the flavor of donut.
     */
    private DonutFlavors parseFlavor(String toParse){
        int idx = toParse.indexOf(",");
        idx += 2;
        if(toParse.charAt(idx) == 'C'){
            return DonutFlavors.CHOCOLATE;
        }else if(toParse.charAt(idx) == 'S'){
            return DonutFlavors.STRAWBERRY;
        }else{
            return DonutFlavors.VANILLA;
        }
    }


}


