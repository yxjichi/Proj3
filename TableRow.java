import java.util.Date;

public class TableRow 
{
    String name;
    String plate;
    String model;
    int mileage;
    Date serviceDate;
    
    public TableRow(String name, String plate, String model, int mileage, Date serviceDate)
    {
        this.name = name;
        this.plate = plate;
        this.model = model;
        this.mileage = mileage;
        this.serviceDate = serviceDate;
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

    public Date serviceDate()
    {
        return serviceDate;
    }
}
