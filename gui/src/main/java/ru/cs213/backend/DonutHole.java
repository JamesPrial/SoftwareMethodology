package ru.cs213.backend;

/**
 * A class defining the properties of the Donut hole
 * type of donut with its specific prices.
 *
 * @author Swarnendu Roy
 * @author James Prial
 *
 */
public class DonutHole extends MenuItem{
    private final double HOLE_PRICE = 0.33;

    private DonutFlavors flavor;

    /**
     * A constructor that helps in initializing the donut hole
     * with the flavor type passed as an argument.
     * @param flavor a flavor type as defined in the enum class Donut Flavors.
     */

    public DonutHole(DonutFlavors flavor){
        this.flavor = flavor;
        itemPrice();
    }

    /**
     * A method defined to be able
     * to copy an object of the DonutHole class.
     * @return a copy of the DonutHole.
     */

    public DonutHole copy(){
        return new DonutHole(this.flavor);
    }

    /**
     * A method defined to be able to calculate the price
     * of a DonutHole.
     * @return returns the price of the DonutHole ordered.
     */

    @Override
    public double itemPrice(){
        super.price = HOLE_PRICE;
        return HOLE_PRICE;
    }

    /**
     *
     * A method defined to get a String representation
     * of the DonutHole object.
     *
     * @return a String representation of the DonutHole Object.
     */
    @Override
    public String toString(){
        return "Donut Hole, " + flavor + " flavor, cost = " + super.price + "";
    }
}
