import java.util.Date;

public class Service implements Comparable<Service>
{
    private static int nextId = 0;
    private Date serviceDate;
    private int id;

    public Service(Date serviceDate)
    {
        this.serviceDate = serviceDate;
        id = nextId;
        nextId++;
    }

    public int getId()
    {
        return id;
    }
    public Date getServiceDate()
    {
        return serviceDate;
    }
    public int compareTo(Service a) {
        return this.serviceDate.compareTo(a.serviceDate);
    }

}