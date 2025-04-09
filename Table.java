import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.Scanner;

public class Table {
    ArrayList<Customer> c;
    ArrayList<TableRow> tb;
    String filename;

    public Table(ArrayList<Customer> c)
    {
        this.c = c;
        tb = new ArrayList<TableRow>();
        popTableRows();
    }

    private void popTableRows()
    {
        for(Customer i: c ){
            for(Vehicle j: i.getOwnership())
            {
                for(Service p: j.getServiceHist())
                {
                    TableRow row = new TableRow(i,p,j);
                    tb.add(row);
                }
            }
        }
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
                        return;
                    }
                }
            }
        }

    }

    public void editName(int index, String newName)
    {
        TableRow query = tb.get(index);
        query.getCustomer().setName(newName);
    }

    public void editPlate(int index, String newPlate)
    {
        TableRow query = tb.get(index);
        query.getVehicle().setPlate(newPlate);
    }

    public void editModel(int index, String newModel)
    {
        TableRow query = tb.get(index);
        query.getVehicle().setModel(newModel);
    }

    public void editMileage(int index, int newMileage)
    {
        TableRow query = tb.get(index);
        query.getVehicle().setMileage(newMileage);
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
            return a.getPlate().compareTo(b.getPlate());
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

    private ArrayList<TableRow> loadRows(String pfile)
    {
        Scanner pscan = null;
        ArrayList<TableRow> plist = new ArrayList<TableRow>();
        try
        {
            pscan  = new Scanner(new File(pfile));
            this.filename = pfile;
            Customer cursorCust = null;
            Vehicle cursorVehicle = null;
            Service cursorService = null;
            
            while(pscan.hasNext())
            {
                String [] nextLine = pscan.nextLine().split(" ");
                String name = nextLine[0]+ " " + nextLine[1];
                String plate = nextLine[2];
                String model = nextLine[3];
                int mileage = Integer.parseInt(nextLine[4]);
                String date = nextLine[5];

                Customer c = new Customer(name);
                Vehicle v = new Vehicle(model, plate, mileage);

                Date d = null;
                SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
                try 
                {
                    d = formatter.parse(date);
                }   
                catch (ParseException e)
                {
                    e.printStackTrace();
                }
                
                Service s = new Service(d);

                if(cursorCust == null)
                {
                    cursorCust = c;
                }
                if(cursorVehicle == null)
                {
                    cursorVehicle = v;
                }
                if(cursorService == null)
                {
                    cursorService = s;
                }

                if(c.getName() == cursorCust.getName())
                {
                    if(v.getPlate() == cursorVehicle.getPlate())
                    {
                        if(s.getId() == cursorService.getId())
                        {

                        } else {
                            cursorService = s;
                        }
                    } else {
                        cursorVehicle = v;
                    }
                } else {
                    cursorCust = c;
                }
               
                TableRow tr = new TableRow(c,s,v);
                plist.add(tr);
            }

            pscan.close();
        }
        catch(IOException e)
        {}
        return plist;
        
    }
}
