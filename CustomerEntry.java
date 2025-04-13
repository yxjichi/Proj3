import java.awt.*;
import javax.swing.*;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.awt.event.ActionEvent;
/**
 * CustomerEntry Class - Permits adding a new customer
 */
public class CustomerEntry extends JFrame 
{
    //textfields 
    private JTextField  txtName;         
    private JTextField model;
    private JTextField plate;
    private JTextField mileage;
    private JTextField date;
    //button objects
    private JButton     cmdSave;
    private JButton     cmdClose;
    private JPanel      pnlCommand;
    private JPanel      pnlDisplay;
    //references to tablelisting and this class object
    private TableListing list;
    private CustomerEntry entry;
    /**
     * CustomerEntry Constructor - establises textfields, enables button functionality
     * @param list TableListing object
     */
    public CustomerEntry(TableListing list)
    {
        //initializing tablelisting and this class references
        entry = this;
        this.list = list;
        //setting window title
        setTitle("Entering new Customer");
        pnlCommand = new JPanel();
        pnlDisplay = new JPanel();
        //name entry
        pnlDisplay.add(new JLabel("Name:")); 
        txtName = new JTextField(20);
        pnlDisplay.add(txtName);
        //model entry
        pnlDisplay.add(new JLabel("Model"));
        model = new JTextField(10);
        pnlDisplay.add(model);
        //plate entry
        pnlDisplay.add(new JLabel("Plate"));
        plate = new JTextField(10);
        pnlDisplay.add(plate);
        //mileage entry
        pnlDisplay.add(new JLabel("Mileage:"));
        mileage = new JTextField(3);
        pnlDisplay.add(mileage);
        //service date entry
        pnlDisplay.add(new JLabel("Service Date"));
        date = new JTextField(10);
        pnlDisplay.add(date);
        

        pnlDisplay.setLayout(new GridLayout(3,4));
        //setting up button functionality
        cmdSave    = new JButton("Save");
        cmdClose   = new JButton("Close");

        cmdSave.addActionListener(new SaveButtonListener());
        cmdClose.addActionListener(new CloseButtonListener());


        pnlCommand.add(cmdSave);
        pnlCommand.add(cmdClose);

        add(pnlDisplay, BorderLayout.CENTER);
        add(pnlCommand, BorderLayout.SOUTH);
        pack();
        setVisible(true);
    }

      /**
     * Allows Close button to respond to user choice and close the program
     */
    private class CloseButtonListener implements ActionListener
    {
        public void actionPerformed(ActionEvent e)
        {
            entry.setVisible(false);
        }

    }

    /**
     * Allows Save button to respond to user choice and save the new data
     */
    private class SaveButtonListener implements ActionListener
    {
        public void actionPerformed(ActionEvent e)
        {
            //accepting inputted data to prepare them for saving
            String[] text = txtName.getText().split(" ");
            String mod = model.getText();
            String  plat = plate.getText();
            int mile = Integer.parseInt(mileage.getText());
            String dat = date.getText();
            //checking if the date is in the correct format
            Pattern datePattern = Pattern.compile("[0-9]{1,2}-{1}[0-9]{1,2}-{1}[0-9]{4}$");
            Matcher match = datePattern.matcher(dat); // string is whatever ur checking for
            //if format is correct
            if(text.length == 2 && match.matches())
            {
                //create new customer and vehicle objects
                Customer c = new Customer(text[0]+" "+text[1]);
                Vehicle v = new Vehicle(mod, plat, mile);

                Date d = null;
                SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
                try 
                {
                    d = formatter.parse(dat);
                }   
                catch (ParseException f)
                {
                    f.printStackTrace();
                }
                // create new service object
                Service s = new Service(d);
                //consolidating service into vehicle into customer
                v.addService(s);
                c.getOwnership().add(v);
                //adding populated customer object into tablelisting, populating, and refreshing
                list.getTable().addCust(c); ;
                list.getTable().popTableRows();

                list.refresh();
            }
            entry.setVisible(false);           
        }

    }
}
