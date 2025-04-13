public class Driver {

    public static void main(String[] args)
    {
        Table t = new Table("customer.dat");
        t.printTableRows();

        t.sortModel();
        t.printTableRows();

        t.storeFile();
    }
    
}
