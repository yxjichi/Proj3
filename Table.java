import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Scanner;

public class Table {
    ArrayList<Customer> c;
    ArrayList<TableRow> tb;
    String filename;

    public Table(String pfile)
    {
        c = new ArrayList<Customer>();

        loadFile(pfile);
        popTableRows();
    }

    private void popTableRows()
    {
        tb = new ArrayList<TableRow>();
        for(Customer i: c ){
            for(Vehicle j: i.getOwnership())
            {
                for(Service p: j.getServiceHist())
                {
                    TableRow row = new TableRow(i,p,j);
                    tb.add(row);
                }
            }
            i.calcLoyalty(3);
            //when interlocking with gui, make it so that when a customer is loyal, the background of their text is a special colour
        }
    }

    public void printTableRows()
    {
        for(TableRow i: tb)
        {
            System.out.println(i);
        }
        System.out.println();
    }

    public void deleteRow(int index)
    {
        TableRow query = tb.get(index);
        Customer target = query.getCustomer();
        for(Vehicle i: target.getOwnership())
        {
            if(i.getPlate().compareTo(query.getPlate()) == 0)
            {
                for(Service s: i.getServiceHist())
                {
                    if(s.getId() == query.getService().getId())
                    {
                        i.delService((s));
                        break;
                    }
                }
            }
        }
        popTableRows();

    }

    public void editName(int index, String newName)
    {
        TableRow query = tb.get(index);
        query.getCustomer().setName(newName);
        popTableRows();
    }

    public void editPlate(int index, String newPlate)
    {
        TableRow query = tb.get(index);
        query.getVehicle().setPlate(newPlate);
        popTableRows();
    }

    public void editModel(int index, String newModel)
    {
        TableRow query = tb.get(index);
        query.getVehicle().setModel(newModel);
        popTableRows();
    }

    public void editMileage(int index, int newMileage)
    {
        TableRow query = tb.get(index);
        query.getVehicle().setMileage(newMileage);
        popTableRows();
    }

    public void sortCust()
    {
        NameCompare a = new NameCompare();
        tb.sort(a);
    }

    public void sortPlate()
    {
        PlateCompare a = new PlateCompare();
        tb.sort(a);
    }

    public void sortModel()
    {
        ModelCompare a = new ModelCompare();
        tb.sort(a);
    }

    public void sortMileage()
    {
        MileageCompare a = new MileageCompare();
        tb.sort(a);
    }

    public void sortDate()
    {
        DateCompare a = new DateCompare();
        tb.sort(a);
    }

    public class NameCompare implements Comparator<TableRow>{
        public int compare(TableRow a, TableRow b)
        {
            return a.getName().compareTo(b.getName());
        }
        
    } 

    public class PlateCompare implements Comparator<TableRow>{
        public int compare(TableRow a, TableRow b)
        {
            return a.getPlate().compareTo(b.getPlate());
        }
        
    } 

    public class ModelCompare implements Comparator<TableRow>{
        public int compare(TableRow a, TableRow b)
        {
            return a.getModel().compareTo(b.getModel());
        }
        
    } 

    public class MileageCompare implements Comparator<TableRow>{
        public int compare(TableRow a, TableRow b)
        {
            return a.getMileage() - b.getMileage();
        }
        
    }

    public class DateCompare implements Comparator<TableRow>{
        public int compare(TableRow a, TableRow b)
        {
            return a.getServiceDate().compareTo(b.getServiceDate());
        }
        
    }

    private void loadFile(String pfile)
    {
        Scanner pscan = null;
        ArrayList<Customer> plist = new ArrayList<Customer>();
        try
        {
            pscan  = new Scanner(new File(pfile));
            this.filename = pfile;
            Customer cursorCust = null;
            
            while(pscan.hasNext())
            {
                String [] nextLine = pscan.nextLine().split(" ");
                int length = nextLine.length;
                String name = nextLine[0]+ " " + nextLine[1];
                String model = nextLine[2];
                String plate = nextLine[3];
                int mileage = Integer.parseInt(nextLine[4]);

                ArrayList<String> dates = new ArrayList<String>();
                for(int i = 5; i < length; i++)
                {
                    dates.add(nextLine[i]);
                }

                if(cursorCust == null)
                {
                    cursorCust = new Customer(name);
                } else {
                    if(cursorCust.getName().compareTo(name) == 0)
                    {
            
                    } else {
                        plist.add(cursorCust);
                        cursorCust = new Customer(name);
                    }
                }
                
                Vehicle v = new Vehicle(model, plate, mileage);

                for(String i: dates)
                {
                    Date d = null;
                    SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
                    try 
                    {
                        d = formatter.parse(i);
                    }   
                    catch (ParseException e)
                    {
                        e.printStackTrace();
                    }
                    Service s = new Service(d);
                    v.addService(s);
                }
                cursorCust.addVehicle(v);
                if(!pscan.hasNext())
                {
                    plist.add(cursorCust);
                }
            }

            pscan.close();
        }
        catch(IOException e)
        {}
        c = plist;
        
    }

    public void storeFile()
    {
        FileWriter writer = null;
        try
        {
            writer  = new FileWriter(new File(filename));
            for(Customer cust: c)
            {
                for(Vehicle v: cust.getOwnership())
                {
                    ArrayList<String> serviceDates = new ArrayList<String>();

                    for(Service s: v.getServiceHist())
                    {
                        Date d = s.getServiceDate();
                        Calendar calendar = new GregorianCalendar(); 
                        calendar.setTime(d);
                        int year = calendar.get(Calendar.YEAR);
                        //Add one to month {0 - 11}
                        int month = calendar.get(Calendar.MONTH) + 1;
                        int day = calendar.get(Calendar.DAY_OF_MONTH);
                        serviceDates.add(""+ day + "-" + month + "-" + year);
                    }
                    writer.write(cust.getName() + " " + v.getModel() + " " + v.getPlate() + " " + v.getMileage()+ " ");
                    for(int i = 0; i < serviceDates.size(); i++)
                    {
                        if(i == serviceDates.size() - 1)
                        {
                            writer.write(serviceDates.get(i));
                        } else {
                            writer.write(serviceDates.get(i) + " ");
                        }
                        
                    }
                    writer.write("\n");
                }
            }
            writer.close();
        }
        catch(IOException e)
        {}

    }
}
