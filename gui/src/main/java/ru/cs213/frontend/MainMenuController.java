package ru.cs213.frontend;

import ru.cs213.backend.*;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import javafx.stage.WindowEvent;

/**
 * A class defining the behaviours of the Main Menu of the
 * RU Cafe Main Menu GUI. This controller establishes the GUI's
 * current order and store orders which is referenced to the other controllers.
 * The GUI has 4 buttons which helps a customer to navigate through 4 different GUIs
 * of Ordering Coffee, Ordering Donuts, Viewing and Placing the order, Viewing all orders
 * and being able to save the Store Orders.
 *
 * @author Swarnendu Roy
 * @author James Prial
 */
public class MainMenuController {
    @FXML
    private Button orderDonutsButton;

    @FXML
    private Button orderCoffeeButton;

    @FXML
    private Button showCurrentOrderButton;

    @FXML
    private Button showAllOrdersButton;

    private StoreOrders orders;
    private Order currentOrder;
    private DonutController donutCont;
    private CoffeeController coffeeCont;
    private CurrentOrderController currentCont;
    private StoreOrdersController storeCont;
    private FXMLLoader donutLoader;
    private FXMLLoader coffeeLoader;
    private FXMLLoader currentLoader;
    private FXMLLoader storeLoader;
    private Stage donutStage;
    private Stage coffeeStage;
    private Stage currentStage;
    private Stage storeStage;



    /**
     * A Constructor of the MainMenuController to help define
     * the required variables of GUI and also to define variables to load
     * the 4 other GUI while running itself simultaneously.
     *
     *
     */
    public MainMenuController(){
        try {
             donutLoader =
                    new FXMLLoader(getClass().getResource("Donut.fxml"));
             coffeeLoader =
                    new FXMLLoader(getClass().getResource("Coffee.fxml"));
             currentLoader =
                    new FXMLLoader(getClass().getResource("CurrentOrder.fxml"));


            storeLoader =
                    new FXMLLoader(getClass().getResource("StoreOrders.fxml"));




            donutLoader.load();
            coffeeLoader.load();
            currentLoader.load();
            storeLoader.load();


            donutCont = donutLoader.getController();
            coffeeCont = coffeeLoader.getController();
            currentCont = currentLoader.getController();
            storeCont = storeLoader.getController();
            orders = new StoreOrders();

            currentOrder = new Order(0);
            donutStage = null;
            coffeeStage = null;
            currentStage = null;
            storeStage = null;
        }catch(Exception e){
            System.err.println(e);
        }
    }

    /**
     * A handler method that acts in response to the clicking
     * of the Show All Orders Button to be able to view all the orders
     * placed from the store
     *
     * @param clickAllOrderButton The clicking of Show All Orders Button
     */
    @FXML
    void allOrdersHandler(ActionEvent clickAllOrderButton) {
        if(storeStage == null) {
            try {


                Parent root = (Parent) storeLoader.getRoot();

                storeStage = new Stage();
                storeStage.setTitle("Store Orders");

                storeStage.setScene(new Scene(root));


            } catch (Exception e) {
                System.err.println(e);
                return;
            }
        }

        storeCont.init(orders, currentOrder);
        storeStage.show();

    }


    /**
     * A handler method that acts in response to the clicking
     * of the Show Current Orders Button to be able to view all the orders
     * added to the cart from the Donut GUI and the Coffee GUI
     *
     * @param clickCurrentOrderButton The clicking of Show Current Orders Button
     */
    @FXML
    void currentOrderHandler(ActionEvent clickCurrentOrderButton) {
        if(currentStage == null) {
            try {
                Parent root = (Parent) currentLoader.getRoot();

                currentStage = new Stage();
                currentStage.setTitle("Current Order");
                currentStage.setScene(new Scene(root));
            } catch (Exception e) {
                System.err.println(e);
                return;
            }
        }
        currentCont.init(currentOrder, orders);
        currentStage.show();
        currentStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent windowEvent) {
                if(currentCont.isOrderNull()){

                    currentOrder = new Order(orders.getOrderCtr());
                }
            }
        });
        return;
    }

    /**
     * A handler method that acts in response to the clicking
     * of the Order Coffee Button to be able to view the GUI
     * for the ordering of a Coffee with selections ranging
     * from various sizes and add ins.
     *
     * @param clickOrderCoffeeButton The clicking of Order Coffee Button
     */
    @FXML
    void orderCoffeeHandler(ActionEvent clickOrderCoffeeButton) {
        if(coffeeStage == null) {
            try {
                Parent root = (Parent) coffeeLoader.getRoot();
                coffeeStage = new Stage();
                coffeeStage.setTitle("Order Coffee");
                coffeeStage.setScene(new Scene(root));
            } catch (Exception e) {
                System.err.println(e);
                return;
            }
        }
        coffeeCont.init(currentOrder);
        coffeeStage.show();

    }

    /**
     * A handler method that acts in response to the clicking
     * of the Order Donuts Button to be able to view the GUI
     * for the ordering of a Donuts with selections ranging
     * from three types and three flavors.
     *
     * @param clickOrderDonutsButton The clicking of Order Donuts Button
     */
    @FXML
    void orderDonutsHandler(ActionEvent clickOrderDonutsButton) {
        if(donutStage == null) {
            try {


                Parent root = (Parent) donutLoader.getRoot();




                donutStage = new Stage();
                donutStage.setTitle("Order Donuts");
                donutStage.setScene(new Scene(root));


            } catch (Exception e) {
                System.err.println(e);
                return;
            }
        }
        donutCont.init(currentOrder);
        donutStage.show();
    }

    /**
     * a getter for orders
     * @return orders
     */
    public StoreOrders getOrders(){
        return orders;
    }





    /**
     * An initializing method that sets the default settings and values of
     * the Main Menu of the program.
     *
     * @param url
     * @param rb
     */
    @FXML
    void initialize(URL url, ResourceBundle rb) {

        orders = new StoreOrders();

        currentOrder = new Order(orders.getOrderCtr());

    }

    /**
     * A getter function to be able to get the
     * current order in any controller.
     *
     * @return the current order of the items added and is of Order type.
     */
    public Order getCurrentOrder(){
        return currentOrder;
    }

    /**
     * A setter function to be able to set the
     * current order in any controller.
     *
     *
     */
    public void setCurrentOrder(Order order){
        currentOrder = order;
        return;
    }

}
