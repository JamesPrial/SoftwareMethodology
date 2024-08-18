package ru.cs213.frontend ;

import ru.cs213.backend.*;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.StringTokenizer;


import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

/**
 * A controller that helps the user to be able
 * to review and place the order in the GUI.
 *
 * @author JamesPrial
 * @author Swarnendu Roy
 */
public class CurrentOrderController implements Initializable {

    private final int SMALL = 0;
    private final int TALL = 1;
    private final int GRAND = 2;
    private final int VENTI = 3;


    @FXML
    private TextField itemPriceTextField;

    @FXML
    private TextField salesTaxTextField;

    @FXML
    private TextField totalPriceTextField;

    @FXML
    private Button placeOrderButton;

    @FXML
    private ListView<String> orderListView;

    @FXML
    private Button removeOrderButton;

    private Order order;
    private StoreOrders orders;

    /**
     *
     * A method to initialize the current order and
     * current store orders.
     *
     * @param order current order
     * @param orders current store order.
     */
    public void init(Order order, StoreOrders orders){
        this.order = order;
        this.orders = orders;
        orderListView.setItems(FXCollections.observableArrayList(order.ordersAsArrayListString()));
        itemPriceTextField.setText(""+order.subtotal());
        salesTaxTextField.setText(""+order.taxCalc());//
        totalPriceTextField.setText(""+order.totalPrice());
        placeOrderButton.setDisable(false);
    }


     /**
     * A method to check whether current orders is empty after placing
     * the order
     * @return true or false depending on whether it is empty or not.
     */
    public boolean isOrderNull(){
        if(order == null){
            return true;
        }else{
            return false;
        }
    }

    /**
     *
     * A function defined to place order
     *
     * @param event clicking place order button
     */
    @FXML
    void placeOrderHandler(ActionEvent event) {
        if(order.ordersAsArrayListString().isEmpty()){
            Platform.runLater(() -> {
                Alert dialog = new Alert(Alert.AlertType.ERROR, "No orders available to place!", ButtonType.OK);
                dialog.show();
            });
            return;
        }

        int orderNum = order.getOrderNum();
        orders.add(order);
        Platform.runLater(() -> {
            Alert dialog = new Alert(Alert.AlertType.INFORMATION, "Order Has Been Placed Successfully! Order num " + orderNum, ButtonType.OK);
            dialog.show();
        });

        order = null;
        orderListView.setItems(FXCollections.observableArrayList(""));
        itemPriceTextField.setText("0.0");
        salesTaxTextField.setText("0.0");
        totalPriceTextField.setText("0.0");
        placeOrderButton.setDisable(true);
    }


    /**
     * A function to remove an order from the GUI
     *
     *
     * @param event clicking remove order button.
     */
    @FXML
    void removeOrderHandler(ActionEvent event) {
        String selected = orderListView.getSelectionModel().getSelectedItem();
        if(selected == null){
            Platform.runLater(() -> {
                Alert dialog = new Alert(Alert.AlertType.ERROR, "No order selected to remove!", ButtonType.OK);
                dialog.show();
            });
            return;
        }
        if(order.removeByStr(selected)){
            orderListView.setItems(FXCollections.observableArrayList(order.ordersAsArrayListString()));
            itemPriceTextField.setText("" + order.subtotal());
            salesTaxTextField.setText(""+order.taxCalc());
            totalPriceTextField.setText(""+order.totalPrice());
            return;
        }else{
            Platform.runLater(() -> {
                Alert dialog = new Alert(Alert.AlertType.ERROR, "Remove Failed!", ButtonType.OK);
                dialog.show();
            });
            return;
        }
    }

/**
 * A function to initialize default values of the GUI
 *
 * @param url
 * @param resources
 */
    @FXML

    public void initialize(URL url, ResourceBundle resources) {




    }



}
