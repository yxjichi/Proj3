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

/**
 *  Table Class tabulizes customer data. 
 *  vehicles owned, and displays loyalty discount.
 *  */ 
public class Table {
    //Data references
    ArrayList<Customer> c;
    ArrayList<TableRow> tb;
    String filename;
    /**
     * Table Constructor - initializes customer list and populates tablerows
     * @param pfile filename
     */
    public Table(String pfile)
    {
        c = new ArrayList<Customer>();

        loadFile(pfile);
        popTableRows();
    }
    /**
     * popTableRows() - for each service date, create a new tablerow
     * and calculate loyalty for the customer
     */
    public void popTableRows()
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
    /**
     * checkNull() - check for null service dates and handle accordingly
     */
    public void checkNull()
    {
        for(TableRow r: tb)
        {
            Vehicle v = r.getVehicle();
            if(v.getServiceHist().size() > 1)
            {
                for(Service s: v.getServiceHist())
                {
                    if(s.getServiceDate() == null)
                    {
                        v.delService(s);
                    }
                }
            }
        }
    }
    /**
     * getCustomerList() - return customer data
     * @return
     */
    public ArrayList<Customer> getCustomerList()
    {
        return c;
    }
    /**
     * getTableRows() - return list of tablerows
     * @return
     */
    public ArrayList<TableRow> getTableRows()
    {
        return tb;
    }
    /**
     * addCust() - add new customer to customer list
     * @param cust customer object
     */
    public void addCust(Customer cust)
    {
        c.add(cust);
    }

    /**
     * deleteRow() - deletes specified row from data
     * @param index specified row #
     */
    public void deleteRow(int index)
    {
        TableRow query = tb.get(index);
        Customer target = query.getCustomer();
        //find and delete entry
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
        //repopulate table
        popTableRows();

    }

    /**
     * editName() - change specified name
     * @param index
     * @param newName
     */
    public void editName(int index, String newName)
    {
        TableRow query = tb.get(index);
        query.getCustomer().setName(newName);
        popTableRows();
    }
    /**
     * editPlate() - change specified plate
     * @param index
     * @param newPlate
     */
    public void editPlate(int index, String newPlate)
    {
        TableRow query = tb.get(index);
        query.getVehicle().setPlate(newPlate);
        popTableRows();
    }
    /**
     * editModel() - change specified model
     * @param index
     * @param newModel
     */
    public void editModel(int index, String newModel)
    {
        TableRow query = tb.get(index);
        query.getVehicle().setModel(newModel);
        popTableRows();
    }
    /**
     * editMileage() - change specified mileage
     * @param index
     * @param newMileage
     */
    public void editMileage(int index, int newMileage)
    {
        TableRow query = tb.get(index);
        query.getVehicle().setMileage(newMileage);
        popTableRows();
    }
    /**
     * sortCust() - sort table by name
     */
    public void sortCust()
    {
        NameCompare a = new NameCompare();
        tb.sort(a);
    }
    /**
     * sortPlate() - sort table by plate
     */
    public void sortPlate()
    {
        PlateCompare a = new PlateCompare();
        tb.sort(a);
    }
    /**
     * sortModel() - sort table by model
     */
    public void sortModel()
    {
        ModelCompare a = new ModelCompare();
        tb.sort(a);
    }
    /**
     * sortMileage() - sort table by mileage
     */
    public void sortMileage()
    {
        MileageCompare a = new MileageCompare();
        tb.sort(a);
    }
    /**
     * sortCust() - sort table by name
     */
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
            if(a.getServiceDate() == null)
            {
                if(b.getServiceDate() == null)
                {
                    return 0;
                }
                else {
                    return -1;
                }
            }  else if(b.getServiceDate() == null){
                if(a.getServiceDate()!=null){
                    return 1;
                }
            }
            return a.getServiceDate().compareTo(b.getServiceDate());
        }
        
    }

    /**
     * loadFile() - loads in table data from file
     * @param pfile file name
     */
    private void loadFile(String pfile)
    {
        Scanner pscan = null;
        ArrayList<Customer> plist = new ArrayList<Customer>();
        try
        {
            //initialize
            pscan  = new Scanner(new File(pfile));
            this.filename = pfile;
            Customer cursorCust = null;
            //while there is more data to be read in
            while(pscan.hasNext())
            {
                //divy up fields
                String [] nextLine = pscan.nextLine().split(" ");
                int length = nextLine.length;
                String name = nextLine[0]+ " " + nextLine[1];
                String model = nextLine[2];
                String plate = nextLine[3];
                int mileage = Integer.parseInt(nextLine[4]);
                //Collate dates in line
                ArrayList<String> dates = new ArrayList<String>();
                for(int i = 5; i < length; i++)
                {
                    dates.add(nextLine[i]);
                }
                //if there is no date add substitute value
                if(dates.size() == 0)
                {
                    dates.add("00-00-0000");
                }
                //first line
                if(cursorCust == null)
                {
                    cursorCust = new Customer(name);
                } else { //not first line
                    if(cursorCust.getName().compareTo(name) == 0)
                    {
            
                    } else {
                        plist.add(cursorCust);
                        cursorCust = new Customer(name);
                    }
                }
                //create vehicle object with data read
                Vehicle v = new Vehicle(model, plate, mileage);
                //for each date add a service object to vehicle
                for(String i: dates)
                {
                    Date d = null;
                    if(!i.equals("00-00-0000"))
                    {
                        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
                        try 
                        {
                            d = formatter.parse(i);
                        }   
                        catch (ParseException e)
                        {
                            e.printStackTrace();
                        }
                    } 
                    Service s = new Service(d);
                    v.addService(s);
                }
                //consolidate populated vehicle into customer object
                cursorCust.addVehicle(v);
                //if there is no more to be read
                if(!pscan.hasNext())
                {
                    //add last object into customer list
                    plist.add(cursorCust);
                }
            }

            pscan.close();
        }
        catch(IOException e)
        {}
        // set table customerlist object to new, populated customerlist
        c = plist;
        
    }
    /**
     * storeFile() - save current table data to file
     */
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
                        if(s.getServiceDate() == null) //if date is null
                        {
                            // print nothing to line
                            serviceDates.add("");
                        } else { //print needed format of date to line
                            System.out.println(v.getPlate());
                            Date d = s.getServiceDate();
                            Calendar calendar = new GregorianCalendar(); 
                            calendar.setTime(d);
                            int year = calendar.get(Calendar.YEAR);
                            //Add one to month {0 - 11}
                            int month = calendar.get(Calendar.MONTH) + 1;
                            int day = calendar.get(Calendar.DAY_OF_MONTH);
                            serviceDates.add(""+ day + "-" + month + "-" + year);
                        }
                    }
                    //write current customer data to file
                    writer.write(cust.getName() + " " + v.getModel() + " " + v.getPlate() + " " + v.getMileage()+ " ");
                    for(int i = 0; i < serviceDates.size(); i++)
                    {
                        //print service dates to file
                        if(i == serviceDates.size() - 1)
                        {
                            writer.write(serviceDates.get(i));
                        } else {
                            writer.write(serviceDates.get(i) + " ");
                        }
                        
                    }
                    writer.write("\n"); //new line
                }
            }
            writer.close(); //close filewriter
        }
        catch(IOException e)
        {}

    }
}
