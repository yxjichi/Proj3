import java.awt.*;
import javax.swing.*;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.awt.event.ActionEvent;

public class CustomerEntry extends JFrame 
{

    private JTextField  txtName;       //name   
    private JTextField model;
    private JTextField plate;
    private JTextField mileage;
    private JTextField date;

    private JButton     cmdSave;
    private JButton     cmdClose;
    private JPanel      pnlCommand;
    private JPanel      pnlDisplay;
    private TableListing list;
    private CustomerEntry entry;

    public CustomerEntry(TableListing list)
    {
        entry = this;
        this.list = list;

        setTitle("Entering new Customer");
        pnlCommand = new JPanel();
        pnlDisplay = new JPanel();

        pnlDisplay.add(new JLabel("Name:")); 
        txtName = new JTextField(20);
        pnlDisplay.add(txtName);

        pnlDisplay.add(new JLabel("Model"));
        model = new JTextField(10);
        pnlDisplay.add(model);

        pnlDisplay.add(new JLabel("Plate"));
        plate = new JTextField(10);
        pnlDisplay.add(plate);

        pnlDisplay.add(new JLabel("Mileage:"));
        mileage = new JTextField(3);
        pnlDisplay.add(mileage);

        pnlDisplay.add(new JLabel("Service Date"));
        date = new JTextField(10);
        pnlDisplay.add(date);
        

        pnlDisplay.setLayout(new GridLayout(3,4));
       
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
            String[] text = txtName.getText().split(" ");
            String mod = model.getText();
            String  plat = plate.getText();
            int mile = Integer.parseInt(mileage.getText());
            String dat = date.getText();

            Pattern datePattern = Pattern.compile("[0-9]{1,2}-{1}[0-9]{1,2}-{1}[0-9]{4}$");
            Matcher match = datePattern.matcher(dat); // string is whatever ur checking for

            System.out.println(match.matches());

            if(text.length == 2 && match.matches())
            {
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
                Service s = new Service(d);
                v.addService(s);
                c.getOwnership().add(v);

                list.getTable().addCust(c); ;
                list.getTable().popTableRows();

                list.refresh();
            }
            entry.setVisible(false);           
        }

    }
}
