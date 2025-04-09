import java.util.Date;

public class TableRow 
{
    // private static int nextNum = 0;
    // private int num;
    private String name;
    private String plate;
    private String model;
    private int mileage;
    private Date serviceDate;

    private Customer cust;
    private Service serv;
    private Vehicle veh;

    public TableRow(Customer cust, Service serv, Vehicle veh)
    {
        this.name = cust.getName();
        this.plate = veh.getPlate();
        this.model = veh.getModel();
        this.mileage = veh.getMileage();
        this.serviceDate = serv.getServiceDate();
        this.cust = cust;
        this.serv = serv;
        this.veh = veh;
        // num = nextNum;
        // nextNum++;
    }

    public Customer getCustomer()
    {
        return cust;
    }
    public Service getService()
    {
        return serv;
    }

    public Vehicle getVehicle()
    {
        return veh;
    }

    public String getName()
    {
        return name;
    }

    public String getPlate()
    {
        return plate;
    }

    public String getModel()
    {
        return model;
    }

    public int getMileage()
    {
        return mileage;
    }

    public Date getServiceDate()
    {
        return serviceDate;
    }
}
