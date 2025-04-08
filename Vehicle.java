import java.util.Comparator;
import java.util.Collections;
import java.security.Provider.Service;
import java.util.ArrayList;

public class Vehicle implements Comparable<Vehicle>
{
    private String model;
    private String plate;
    private int mileage;
    private ArrayList<Service> serviceHistory;

    public Vehicle(String model, String plate, int mileage)
    {
        this.model = model;
        this.plate = plate;
        this.mileage = mileage;
    }

    public int getMileage()
    {
        return mileage;
    }

    public void setMileage(int mileage)
    {
        this.mileage = mileage;
    }
    public int compareTo(Vehicle a) {
        return this.mileage - a.getMileage();
    }
}
