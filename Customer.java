import java.util.ArrayList;

public class Customer implements Comparable<Customer>
{
    private static int nextId = 0;
    private int id;
    private String name;
    private int loyalty;
    private ArrayList<Vehicle> ownership; 
    private Boolean discount;

    public Customer(String name)
    {
        this.name = name;
        loyalty = 0;
        discount = false;

        id = nextId;
        nextId++;
    }

    public String getName()
    {
        return name;
    }

    public int getLoyalty()
    {
        return loyalty;
    }

    private void calcLoyalty(int threshold)
    {
        for(Vehicle i: ownership)
        {
            for(Service j: i.getServiceHist())
            {
                loyalty ++;
            }
        }
        if(loyalty > threshold)
        {
            discount = true;
        }
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