import java.util.Date;
/**
 * Service Object - Represents vehicle visit to mechanic
 */
public class Service implements Comparable<Service>
{
    private static int nextId = 0;
    private Date serviceDate;
    private int id;
    /**
     * Service Constructor - initalizes service object with given Data
     * @param serviceDate
     */
    public Service(Date serviceDate)
    {
        this.serviceDate = serviceDate;
        id = nextId;
        nextId++;
    }
    /**
     * getId() - returns id field
     * @return id
     */
    public int getId()
    {
        return id;
    }
    /**
     * getServiceDate() - returns service date
     * @return date
     */
    public Date getServiceDate()
    {
        return serviceDate;
    }
    public int compareTo(Service a) {
        return this.serviceDate.compareTo(a.serviceDate);
    }

    

}
