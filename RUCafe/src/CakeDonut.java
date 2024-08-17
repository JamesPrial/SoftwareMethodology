/**
 * A class defining the properties of the
 * Cake Donut type to be able to successfully place orders
 * of this donut type.
 *
 * @author Swarnendu Roy
 * @author James Prial
 *
 */

public class CakeDonut extends MenuItem {
    private final double CAKE_PRICE = 1.59;

    private DonutFlavors flavor;

    /**
     * A constructor that initializes a cake donut
     * with flavor type
     * @param flavor flavor type from enum class Donut flavors.
     */

    public CakeDonut(DonutFlavors flavor){
        this.flavor = flavor;
        itemPrice();
    }

    /**
     * Method to get a copy of
     * the cake donuts
     * @return returns a CakeDonut Object.
     */
    public CakeDonut copy(){
        return new CakeDonut(this.flavor);
    }

    /**
     * A method defined to return the price
     * of the cake donut added to the order.
     *
     * @return the price of the cake donut.
     */
    @Override
    public double itemPrice(){
        super.price = CAKE_PRICE;
        return CAKE_PRICE;
    }

    /**
     * A method defined to return
     * the String representation of a Cake donut
     * Object.
     *
     * @return a String representation of a cake donut object.
     */
    @Override
    public String toString(){
        return "Cake Donut, " + flavor + " flavor, cost = " + super.price + "";
    }
}
