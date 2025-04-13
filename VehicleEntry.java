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
 * VehicleEntry Class - Permits adding a new vehicle
 */
public class VehicleEntry extends JFrame 
{
    //textfields
    private JTextField  index;     
    private JTextField model;
    private JTextField plate;
    private JTextField mileage;
    private JTextField date;
    //button objects
    private JButton     cmdSave;
    private JButton     cmdClose;
    //panels
    private JPanel      pnlCommand;
    private JPanel      pnlDisplay;
    //references to tablelisting and this class object
    private TableListing list;
    private VehicleEntry entry;
    /**
     * VehicleEntry Constructor - establishes textfields, enables button functionality
     * @param list TableListing object
     */
    public VehicleEntry(TableListing list)
    {
        //initializing tablelisting and this class references
        entry = this;
        this.list = list;
        //setting window title
        setTitle("Entering new Vehicle");
        pnlCommand = new JPanel();
        pnlDisplay = new JPanel();
        //index entry
        pnlDisplay.add(new JLabel("Customer Index"));
        index = new JTextField(10);
        pnlDisplay.add(index);
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
            int i = Integer.parseInt(index.getText());
            String mod = model.getText();
            String  plat = plate.getText();
            int mile = Integer.parseInt(mileage.getText());
            String dat = date.getText();
            //checking if the date is in the correct format
            Pattern datePattern = Pattern.compile("[0-9]{1,2}-{1}[0-9]{1,2}-{1}[0-9]{4}$");
            Matcher match = datePattern.matcher(dat);
            //if format is correct
            if(match.matches())
            {
                //create new vehicle objects
                Customer c = list.getCust(i);
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
                //consolidating service into vehicle
                v.addService(s);
                c.getOwnership().add(v);
                //adding populated vehicle object into tablelisting, populating, and refreshing
                list.getTable().popTableRows();

                list.refresh();
            }
            entry.setVisible(false);           
        }

    }
}
