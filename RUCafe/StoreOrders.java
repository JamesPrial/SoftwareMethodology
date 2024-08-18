import java.util.ArrayList;

/**
 * A class defines the methods and variables required
 * to be able to construct the all the orders placed
 * in the store. The class implements the interface
 * Customizable which helps in cancelling orders
 * or adding final orders to the store purchase history.
 *
 * @author Swarnendu Roy
 * @author James Prial
 *
 *
 */
public class StoreOrders implements Customizable {
	private ArrayList<Order> orders;
	private int orderCtr;

	/**
	 * A constructor of the Store Order class
	 * that helps in initializing the array list required
	 * to contain all the orders.
	 *
	 */
	public StoreOrders() {
		orders = new ArrayList<Order>();
		this.orderCtr = 0;
	}

	/**
	 * A method that helps in adding an order to the arraylist
	 * of store orders. Only an instance of the Order class
	 * may be used in this function. It is a method implemented
	 * from the customizable interface.
	 *
	 * @param obj An object is passed an argument which should be an instance of the order class
	 * @return boolean type. Returns true if add was successful and returns false if add failed.
	 */
	public boolean add(Object obj) {
		if(!(obj instanceof Order)) {
			return false;
		}
		orders.add((Order)obj);
		orderCtr = (orderCtr+1);

		return true;
	}

	/**
	 * a getter function to get the order number.
	 * @return returns the order number
	 */
	public int getOrderCtr(){
		return this.orderCtr;
	}

	/**
	 * A method that helps in removing an order from the arraylist
	 * of store orders. Only an instance of the Order class
	 * may be used in this function. It is a method implemented
	 * from the customizable interface.
	 *
	 * @param obj An object is passed an argument which should be an instance of the order class
	 * @return boolean type. Returns true if remove was successful and returns false if remove failed.
	 */
	public boolean remove(Object obj) {
		if(!(obj instanceof Order)) {
			return false;
		}
		int i;
		for(i = 0; i < orders.size(); i++){
			if(((Order)(obj)).getOrderNum() == orders.get(i).getOrderNum() ){
				orders.remove(i);
				return true;
			}
		}


		return false;
	}

	/**
	 * creates a copy
	 * @return cpy
	 */
	public StoreOrders copy(){
		StoreOrders cpy = new StoreOrders();
		for(int i = 0; i < orders.size(); i++){
			cpy.add(orders.get(i).copy());
		}
		return cpy;
	}
	@Override
	public String toString(){
		String ret = "";
		for(int i = 0; i < orders.size(); i++){
			ret = ret + orders.get(i).toString() + "\n";
		}
		return ret;
	}

	/**
	 * provides the orders as an ArrayList of strings
	 * @return ret
	 */
	public ArrayList<String> ordersAsArrayListString(){
		ArrayList<String> ret = new ArrayList<String>();
		for(int i = 0; i < orders.size(); i++){
			ret.add(orders.get(i).toString());
		}
		return ret;
	}

	/**
	 * returns orders
	 * @return orders
	 */
	public ArrayList<Order> getOrders(){
		return orders;
	}
}
