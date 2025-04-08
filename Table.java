import java.util.ArrayList;

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
                    TableRow row = new TableRow(i.getName(), j.getPlate(), j.getModel(),
                    j.getMileage(),p.getServiceDate());
                    tb.add(row);
                }
            }
        }
    }

    public void sortCust()
    {
        c.sort(null);
    }

    public void sortPlate()
    {

    }
    
    
    public int plateCompare(TableRow a, TableRow b) 
    {
        return a.getPlate().compareTo(b.getPlate());
    }

    public int modelCompare(TableRow a, TableRow b) 
    {
        return a.getPlate().compareTo(b.getPlate());
    }

}
