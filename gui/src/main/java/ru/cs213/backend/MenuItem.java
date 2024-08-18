package ru.cs213.backend;

/**
 * A class defining the Menu Items of the store.
 * This class helps defining the basic functions which are
 * further populated in their respective extended classes.
 *
 * @author Swarnendu Roy
 * @author James Prial
 */

public class MenuItem {
	protected double price;

	/**
	 * A constructor of the Menu Item class that helps
	 * defining the property, price initialized to zero.
	 */

	public MenuItem() {
		this.price = 0;
	}

	/**
	 * A constructor of the Menu Item class that helps
	 * initializing the price of the item with a given argument.
	 *
	 *
	 * @param price a double parameter passed into the constructor
	 */

	
	public MenuItem(double price) {
		this.price = price;
	}

	/**
	 * A public method that helps calculation
	 * of the item price
	 *
	 * @return the price of the items in the order.
	 */


	public double itemPrice() {
		return this.price;
	}

	/**
	 * A public method that helps making a copy
	 * of an item on the menu.
	 *
	 * @return copy of a MenuItem object
	 */

	public MenuItem copy(){
		return new MenuItem (this.price);
	}
}
