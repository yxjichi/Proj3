import java.util.ArrayList;
import java.util.Comparator;

public class Table {
    ArrayList<Customer> c;
    ArrayList<TableRow> tb;
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

    public void editRow(int index)
    {
        TableRow query = tb.get(index);

        
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
}
