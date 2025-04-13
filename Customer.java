import java.util.ArrayList;

/**
 *  Customer Class stores customer data. 
 *  vehicles owned, and determines loyalty discount.
 *  */ 
public class Customer implements Comparable<Customer>
{
    private String name;
    private int loyalty;
    private ArrayList<Vehicle> ownership; 
    private Boolean discount;

    /**
     * Customer Constructor - initializes customer object
     * @param name
     */
    public Customer(String name)
    {
        this.name = name;
        loyalty = 0;
        discount = false;
        ownership = new ArrayList<Vehicle>();
    }

    /**
     * getName() - returns customer name
     * @return name
     */
    public String getName()
    {
        return name;
    }

    /**
     * setName() - sets customer name
     * @param name
     */
    public void setName(String name)
    {
        this.name = name;
    }
    
    /**
     * getLoyalty() - returns discount boolean
     * @return discount
     */
    public boolean getLoyalty()
    {
        return discount;
    }

    /**
     * calcLoyalty() - determines whether customer is eligible for a discount
     * based on service history
     * @param threshold int
     */
    public void calcLoyalty(int threshold)
    {
        loyalty = 0;
        for(Vehicle i: ownership)
        {
            for(Service _ : i.getServiceHist())
            {
                loyalty ++;
            }
        }
        if(loyalty >= threshold)
        {
            discount = true;
        }
    }

    /**
     * addVehicle() - add vehicle to customer ownership
     * @param v vehicle object
     */
    public void addVehicle(Vehicle v)
    {
        ownership.add(v);
    }

    /**
     * getOwnership() - return customer arraylist of vehicles
     * @return ownership - arraylist of vehicles
     */
    public ArrayList<Vehicle> getOwnership()
    {
        return ownership;
    }

    /**
     * compareTo() - permits sorting and comparison of customer objects
     * @return integer result of comparison
     */
    public int compareTo(Customer a) {
        return this.name.compareTo(a.getName());
    }
}