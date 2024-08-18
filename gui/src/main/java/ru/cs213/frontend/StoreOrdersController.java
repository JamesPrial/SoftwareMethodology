package ru.cs213.frontend;

import ru.cs213.backend.*;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.io.FileWriter;
import java.util.StringTokenizer;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ListView;

/**
 * A controller defined to handle the all the
 * orders that have been placed by the user at the Store
 * and user may either export the purchase summary or cancel
 * a placed order.
 *
 * @author Swarnendu Roy
 * @author James Prial
 *
 */

public class StoreOrdersController implements Initializable {



    @FXML
    private ListView<String> storeOrderListView;

    @FXML
    private Button exportOrderButton;

    @FXML
    private Button cancelOrderButton;

    private StoreOrders orders;
    private Order order;

    private final int SPACE_AFTER_COLON = 1;

    /**
     *
     * A helper initializing method to pass the global current order
     * object in the controller.
     *
     * @param order current order object from main menu controller.
     * @param orders current Store orders.
     */

    public void init(StoreOrders orders, Order order){
        this.orders = orders;
        this.order = order;
        storeOrderListView.setItems(FXCollections.observableArrayList(orders.ordersAsArrayListString()));


    }

    /**
     * A function that handles the clicking of cancel order
     * from the GUI.
     *
     * @param event clicking cancel order.
     */

    @FXML
    void cancelOrderHandler(ActionEvent event) {

        String selected = storeOrderListView.getSelectionModel().getSelectedItem();
        if(selected == ""){
            Alert dialog = new Alert(Alert.AlertType.ERROR, "No order selected to remove!", ButtonType.OK);
            dialog.show();
        }
        ArrayList<Order> ordersList = orders.getOrders();
        StringTokenizer tokenized = new StringTokenizer(selected, ":");
        tokenized.nextToken();
        char orderNumAsStr = tokenized.nextToken().charAt(SPACE_AFTER_COLON);
        int orderNum = Character.getNumericValue(orderNumAsStr);
        if(orderNum == -1 || !(orders.remove(new Order(orderNum)))){
            Alert dialog = new Alert(Alert.AlertType.ERROR, "Error removing!", ButtonType.OK);
            dialog.show();
        }
        storeOrderListView.setItems(FXCollections.observableArrayList(orders.ordersAsArrayListString()));
    }

    /**
     * A function that handles the clicking of export order
     * from the GUI that creates an output.txt file.
     *
     * @param event clicking export order.
     */

    @FXML
    void exportOrderHandler (ActionEvent event) throws IOException {
        if(orders.ordersAsArrayListString().isEmpty()){
            Platform.runLater(() -> {
                Alert dialog = new Alert(Alert.AlertType.ERROR, "No orders available to export!", ButtonType.OK);
                dialog.show();
            });
            return;
        }
        FileWriter writer = new FileWriter("output.txt");
        for(String str: orders.ordersAsArrayListString()) {
            writer.write(str + System.lineSeparator());
        }
        writer.close();
        Platform.runLater(() -> {
            Alert dialog = new Alert(Alert.AlertType.INFORMATION, "Order Exported Successfully!", ButtonType.OK);
            dialog.show();
        });

    }

    /**
     *
     * A method initializes default values to the GUI
     *
     * @param url
     * @param resources
     */

    @FXML
    public void initialize(URL url, ResourceBundle resources) {



    }
}

