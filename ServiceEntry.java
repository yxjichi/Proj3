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
 * ServiceEntry - Permits adding new service to a specified vehicle
 */
public class ServiceEntry extends JFrame 
{
    //textfields 
    private JTextField index;     
    private JTextField date;
    //button objects
    private JButton     cmdSave;
    private JButton     cmdClose;

    private JPanel      pnlCommand;
    private JPanel      pnlDisplay;
    //references to tablelisting and this class object
    private TableListing list;
    private ServiceEntry entry;
    /**
     * ServiceEntry Constructor - establises textfields, enables button functionality
     * @param list TableListing object
     */
    public ServiceEntry(TableListing list)
    {
        //initializing tablelisting and this class references
        entry = this;
        this.list = list;
        //setting window title
        setTitle("Entering new Service");
        pnlCommand = new JPanel();
        pnlDisplay = new JPanel();
        //vehicle index entry
        pnlDisplay.add(new JLabel("Vehicle Index"));
        index = new JTextField(10);
        pnlDisplay.add(index);
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
            String dat = date.getText();
             //checking if the date is in the correct format
            Pattern datePattern = Pattern.compile("[0-9]{1,2}-{1}[0-9]{1,2}-{1}[0-9]{4}$");
            Matcher match = datePattern.matcher(dat);
            //if format is correct
            if(match.matches())
            {
                //get specified vehicle object
                Vehicle v = list.getVehicle(i);

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
                //populating and refreshing tablelisting
                list.getTable().checkNull();
                list.getTable().popTableRows();

                list.refresh();
            }
            entry.setVisible(false);           
        }

    }
}
