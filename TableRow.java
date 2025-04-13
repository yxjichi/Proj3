import java.util.Date;
/**
 * TableRow class - Represents row of customer data
 */
public class TableRow 
{
    //data fields
    private String name;
    private String plate;
    private String model;
    private int mileage;
    private Date serviceDate;
    //reference to customer, service, and vehicle objects
    private Customer cust;
    private Service serv;
    private Vehicle veh;
    /**
     * TableRow Constructor - populates tablerow using customer objects
     * @param cust Customer object
     * @param serv Service object
     * @param veh Vehicle object
     */
    public TableRow(Customer cust, Service serv, Vehicle veh)
    {
        //set data fields
        this.name = cust.getName();
        this.plate = veh.getPlate();
        this.model = veh.getModel();
        this.mileage = veh.getMileage();
        this.serviceDate = serv.getServiceDate();
        //set references
        this.cust = cust;
        this.serv = serv;
        this.veh = veh;
    }
    /**
     * getLoyalty() - returns customer loyalty boolean
     * @return loyalty bool
     */
    public Boolean getLoyalty()
    {
        return cust.getLoyalty();
    }
    /**
     * getCustomer() - returns customer object
     * @return customer object
     */
    public Customer getCustomer()
    {
        return cust;
    }
    /**
     * getService() - returns service object
     * @return service object
     */
    public Service getService()
    {
        return serv;
    }
    /**
     * getVehicle() - return vehicle object
     * @return vehicle object
     */
    public Vehicle getVehicle()
    {
        return veh;
    }
    /**
     * getName() - return name
     * @return name string
     */
    public String getName()
    {
        return name;
    }
    /**
     * getPlate() - return plate
     * @return plate string
     */
    public String getPlate()
    {
        return plate;
    }
    /**
     * getModel() - return model
     * @return model string
     */
    public String getModel()
    {
        return model;
    }
    /**
     * getMileage() - return mileage
     * @return mileage integer
     */
    public int getMileage()
    {
        return mileage;
    }
    /**
     * getServiceDate() - return service date
     * @return service date object
     */
    public Date getServiceDate()
    {
        return serviceDate;
    }
    /**
     * updateRow() - repopulate fields with customer objects
     */
    public void updateRow()
    {
        this.name = cust.getName();
        this.plate = veh.getPlate();
        this.model = veh.getModel();
        this.mileage = veh.getMileage();
    }
    
    public String toString()
    {
        return getName() + "    " + getModel() + "  " + getPlate() + "  " + getMileage() + "    " +getServiceDate();
    }
}
