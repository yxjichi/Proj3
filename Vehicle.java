import java.util.Date;
import java.util.ArrayList;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class Vehicle implements Comparable<Vehicle>
{
    private static int nextId = 0;
    private int id;
    private String model;
    private String plate;
    private int mileage;
    private ArrayList<Service> serviceHistory;

    public Vehicle(String model, String plate, int mileage)
    {
        this.model = model;
        this.plate = plate;
        this.mileage = mileage;
        serviceHistory = new ArrayList<Service>();

        id = nextId;
        nextId++;
    }

    public int getMileage()
    {
        return mileage;
    }

    public void setMileage(int mileage)
    {
        this.mileage = mileage;
    }

    public String getModel()
    {
        return model;
    }

    public void setModel(String model)
    {
        this.model = model;
    }

    public String getPlate()
    {
        return plate;
    }

    public ArrayList<Service> getServiceHist()
    {
        return serviceHistory;
    }
    public void setPlate(String plate)
    {

    }
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

    public void delService(Service s)
    {
        serviceHistory.remove(s);
    }

    public int compareTo(Vehicle a) {
        return this.mileage - a.getMileage();
    }
}
