import java.util.ArrayList;

public class Customer implements Comparable<Customer>
{
    private String name;
    private int loyalty;
    private ArrayList<Vehicle> ownership; 


    public Customer(String name)
    {
        this.name = name;
        loyalty = 0;
    }

    public String getName()
    {
        return name;
    }

    public int getLoyalty()
    {
        return loyalty;
    }

    public void addVehicle(Vehicle v)
    {
        ownership.add(v);
    }

    public ArrayList<Vehicle> getOwnership()
    {
        return ownership;
    }

    public int compareTo(Customer a) {
        return this.name.compareTo(a.getName());
    }
}