public class Driver {

    public static void main(String[] args)
    {
        Table t = new Table("customer.dat");
        t.printTableRows();
        t.editName(0,"Billy Bob");

        t.sortCust();
        t.printTableRows();

        t.storeFile();
    }
    
}
