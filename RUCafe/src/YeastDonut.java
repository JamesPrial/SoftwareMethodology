
/**
 * A class defining the properties of the
 * Yeast Donut type to be able to successfully place orders
 * of this donut type.
 *
 * @author Swarnendu Roy
 * @author James Prial
 *
 */

public class YeastDonut extends MenuItem{
    private final double YEAST_PRICE = 1.39;

    private DonutFlavors flavor;

    /**
     * A constructor of the Yeast Donut with the parameter
     * of the flavor to be able to initialize the class object
     * with the flavor.
     * @param flavor the type of flavor as defined in the enum class DonutFlavors.
     */

    public YeastDonut(DonutFlavors flavor){
        this.flavor = flavor;
        itemPrice();
    }

    /**
     * A method that helps in making a
     * copy of an YeastDonut object.
     *
     * @return the copy of the YeastDonut object.
     */
    public YeastDonut copy(){
        return new YeastDonut(this.flavor);
    }

    /**
     * A method that helps in calculation of the price of a
     * Yeast Donut.
     * @return returns the price of a yeast donut.
     */

    @Override
    public double itemPrice(){
        super.price = YEAST_PRICE;
        return YEAST_PRICE;
    }

    /**
     * A method that helps in representation of the yeast donut object
     * as a String.
     *
     *
     * @return the String representation of the Yeast Donut object
     */

    @Override
    public String toString(){
        return "Yeast Donut, " + flavor + " flavor, cost = " + super.price + "";
    }
}
