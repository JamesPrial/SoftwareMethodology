import java.util.ArrayList;

/**
 * The order class helps defining the properties of order which implements
 * the interface customizable. The class' properties include
 * an order number to keep track of the orders and an arraylist to contain
 * the orders.
 *
 * @author Swarnendu Roy
 * @author James Prial
 */
public class Order implements Customizable {
	private int orderNum;
	private ArrayList<MenuItem> orderItems;
	private final double tax_rate = 6.625/100;

	/**
	 * A constructor initializing the properties
	 * of the class when no arguments are passed
	 *
	 *
	 */
	public Order() {
		this.orderNum = 0;
		orderItems = new ArrayList<MenuItem>();
	}

	/**
	 * A constructor initializing the properties
	 * of the class when the order number is passed
	 * as an argument.
	 *
	 * @param orderNum the order number of the order
	 */
	public Order(int orderNum) {
		this.orderNum = orderNum;
		orderItems = new ArrayList<MenuItem>();
	}

	/**
	 * A function defined in the customizable
	 * interface being implemented. The function helps in
	 * adding an object which is an instance of the MenuItem class
	 * to the order array list. If any other instance of an object is passed
	 * it returns false.
	 *
	 * @param obj the object being passed is an instance of the MenuItem Class
	 * @return Boolean type. False if object is not MenuItem instance and true otherwise.
	 *
	 */
	public boolean add(Object obj) {
		if(!(obj instanceof MenuItem)) {
			return false;
		}
		orderItems.add((MenuItem)obj);
		return true;
	}

	/**
	 * A function defined in the customizable
	 * interface being implemented. The function helps in
	 * removing an object which is an instance of the MenuItem class
	 * to the order array list. If any other instance of an object is passed
	 * it returns false.
	 *
	 * @param obj the object being passed is an instance of the MenuItem Class
	 * @return Returns a boolean type. False if not found and true if found and removed.
	 */
	public boolean remove(Object obj) {
		if(!(obj instanceof MenuItem)) {
			return false;
		}
		MenuItem toRemove = (MenuItem)obj;
		for(int i = 0; i < orderItems.size(); i++){
			String orderStr = orderItems.get(i).toString();
			if(toRemove.toString().equals(orderStr)){
				orderItems.remove(i);
				return true;
			}
		}
		return false;
	}

	/**
	 * A function defined to help in making
	 * a copy of the object of the Order class
	 *
	 */
	public Order copy(){
		Order cpy = new Order(this.orderNum);
		for(int i = 0; i < orderItems.size(); i++){
			cpy.add(orderItems.get(i).copy());
		}
		return cpy;
	}

	/**
	 * A method that helps in returning the contents of the
	 * Order arraylist as a String.
	 *
	 * @return The function returns a String representation of the order.
	 */
	@Override
	public String toString(){
		String ret = "Order Number: " + orderNum + ". Contains:\n";
		for(int i = 0; i < orderItems.size(); i++){
			ret = ret + orderItems.get(i).toString() + "\n";
		}
		return ret + "Subtotal: " + this.subtotal() + ". Total with tax: " + this.taxCalc();
	}

	/**
	 * A method that helps converting all the orders placed in the list
	 * to be represented as a String ArrayList.
	 *
	 *
	 * @return Returns a String arrayList of all the orders in the cart.
	 */
	public ArrayList<String> ordersAsArrayListString(){
		ArrayList<String> ret = new ArrayList<String>();
		for(int i = 0; i < orderItems.size(); i++){
			ret.add(orderItems.get(i).toString());
		}
		return ret;
	}

	/**
	 * A function that helps in calculating the the sub total
	 * of the orders without including the tax rate.
	 *
	 *
	 * @return Returns the sub total of the orders placed of double type
	 */
	public double subtotal(){
		double sub = 0.0;
		for(int i = 0; i < orderItems.size(); i++){
			sub += orderItems.get(i).itemPrice();
		}
		int temp = (int)(sub * 100.0);
		return temp / 100.0;
	}

	/**
	 * A method that helps in calculating the sales tax
	 * of the order using the tax rate.
	 *
	 * @return returns the sales tax on the sub total after calculation with the sales tax rate.
	 */
	public double taxCalc(){
		double sub = 0.0;
		for(int i = 0; i < orderItems.size(); i++){
			sub += orderItems.get(i).itemPrice();
		}
		double tax = 0.0;
		tax = sub*tax_rate;
		int temp = (int)(tax * 100.0);
		return temp/100.0;

	}

	/**
	 * A method that calculates the total price
	 * of the order after including the tax in the
	 * sub total of the order.
	 *
	 * @return returns the total price of the order after including the tax.
	 */
	public double totalPrice() {
		double sub = 0.0;
		for (int i = 0; i < orderItems.size(); i++) {
			sub += orderItems.get(i).itemPrice();
		}
		double tax = 0.0;
		tax = sub * tax_rate;
		double total = 0.0;
		total = total + tax;
		int temp = (int) (total * 100.0);
		return temp / 100.0;
	}

	/**
	 * A method that helps in removing an order by parsing the
	 * String representation of each order.
	 * @param str
	 * @return returns true or false if removal was successful or not respectively.
	 */
	public boolean removeByStr(String str){
		if(orderItems.size() <= 0){
			return false;
		}
		for(int i = 0; i < orderItems.size(); i++){
			if(orderItems.get(i).toString().equals(str)){
				orderItems.remove(i);
				return true;
			}
		}
		return false;
	}

	/**
	 * A getter function to get the order number
	 * @return returns order number.
	 */
	public int getOrderNum(){
		return this.orderNum;
	}
//

}
