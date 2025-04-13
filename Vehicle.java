import java.util.Date;
import java.util.ArrayList;
import java.text.ParseException;
import java.text.SimpleDateFormat;
/**
 * Vehicle Class - Represents customer vehicle info
 */
public class Vehicle implements Comparable<Vehicle>
{
    //data fields
    private String model;
    private String plate;
    private int mileage;
    //service list
    private ArrayList<Service> serviceHistory;
    /**
     * Vehicle Constructor - populates fields with given data
     * @param model string
     * @param plate string
     * @param mileage int
     */
    public Vehicle(String model, String plate, int mileage)
    {
        this.model = model;
        this.plate = plate;
        this.mileage = mileage;
        serviceHistory = new ArrayList<Service>();
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
     * setMileage() - set mileage
     * @param mileage integer
     */
    public void setMileage(int mileage)
    {
        this.mileage = mileage;
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
     * setModel() - set model
     * @param model string
     */
    public void setModel(String model)
    {
        this.model = model;
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
     * setPlate() - set plate
     * @param plate string
     */
    public void setPlate(String plate)
    {
        this.plate = plate;
    }
    /**
     * getServiceHist() - return service arraylist
     * @return service list
     */
    public ArrayList<Service> getServiceHist()
    {
        return serviceHistory;
    }
    /**
     * addService - add new service to vehicle using given date
     * @param date string
     */
    public void addService(String date)
    {
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
        try 
        {
            Date d = formatter.parse(date);
            Service s = new Service(d);
            serviceHistory.add(s);
        }
        catch (ParseException e) {
            e.printStackTrace();
        }
        
    }
    /**
     * addService() - add new service to vehicle history
     * @param s
     */
    public void addService(Service s)
    {
        serviceHistory.add(s);
    }
    /**
     * delService() - delete specified service from vehicle history
     * @param s
     */
    public void delService(Service s)
    {
        serviceHistory.remove(s);
    }

    public int compareTo(Vehicle a) {
        return this.mileage - a.getMileage();
    }
}
