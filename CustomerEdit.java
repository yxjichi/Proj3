import java.awt.*;
import javax.swing.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

/**
 * CustomerEdit Class - allows user to edit specified field of data
 */
public class CustomerEdit extends JFrame 
{
    //entered data
    private JTextField  txtData;       
    private JTextField  txtIndex;     
    //button objects
    private JButton     cmdName;        
    private JButton     cmdModel;
    private JButton     cmdPlate;
    private JButton     cmdMileage;
    private JButton     cmdClose;
    //panel objects
    private JPanel      pnlCommand;      
    private JPanel      pnlDisplay;
    //references to tablelisting object and class object
    private TableListing list;
    private CustomerEdit edit;
    /**
     * CustomerEdit Constructor - establises textfields, enables button functionality
     * @param list TableListing object
     */
    public CustomerEdit(TableListing list)
    {
        edit = this;
        this.list = list;

        setTitle("Edit Customer");
        pnlCommand = new JPanel();
        pnlDisplay = new JPanel();

        pnlDisplay.add(new JLabel("Index:")); 
        txtIndex = new JTextField(20);
        pnlDisplay.add(txtIndex);

        pnlDisplay.add(new JLabel("New Data:")); 
        txtData = new JTextField(20);
        pnlDisplay.add(txtData);

        pnlDisplay.setLayout(new GridLayout(3,4));
       
        cmdName    = new JButton("Edit Name");
        cmdModel    = new JButton("Edit Model");
        cmdPlate    = new JButton("Edit Plate");
        cmdMileage = new JButton("Edit Mileage");
        cmdClose   = new JButton("Close");

        cmdName.addActionListener(new NameButtonListener());
        cmdModel.addActionListener(new ModelButtonListener());
        cmdPlate.addActionListener(new PlateButtonListener());
        cmdMileage.addActionListener(new MileageButtonListener());
        cmdClose.addActionListener(new CloseButtonListener());


        pnlCommand.add(cmdName);
        pnlCommand.add(cmdModel);
        pnlCommand.add(cmdPlate);
        pnlCommand.add(cmdMileage);
        pnlCommand.add(cmdClose);
        
        add(pnlDisplay, BorderLayout.CENTER);
        add(pnlCommand, BorderLayout.SOUTH);
        pack();
        setVisible(true);
    }

      /**
     * Allows Close button to respond to user choice and close the edit screen
     */
    private class CloseButtonListener implements ActionListener
    {
        public void actionPerformed(ActionEvent e)
        {
            edit.setVisible(false);
        }

    }

    /**
     * Allows Name button to respond to user choice and save the new data
     */
    private class NameButtonListener implements ActionListener
    {
        public void actionPerformed(ActionEvent e)
        {
            int i = Integer.parseInt(txtIndex.getText());
            String name = txtData.getText();
            
            list.getTable().editName(i, name);
            list.refresh();
        }
    }

    /**
     * Allows Model button to respond to user choice and save the new data
     */
    private class ModelButtonListener implements ActionListener
    {
        public void actionPerformed(ActionEvent e)
        {
            try
            {
                int i = Integer.parseInt(txtIndex.getText());
                String model = txtData.getText();
            
                list.getTable().editModel(i, model);
                list.refresh();

            } catch(NumberFormatException n) {
                System.out.println("Number Format Exception");
                edit.setVisible(false);
            }
            
        }
    }

    /**
     * Allows Mileage button to respond to user choice and save the new data
     */
    private class MileageButtonListener implements ActionListener
    {
        public void actionPerformed(ActionEvent e)
        {
            int i = Integer.parseInt(txtIndex.getText());
            int mile = Integer.parseInt(txtData.getText());
            
            list.getTable().editMileage(i, mile);
            list.refresh();
        }

    }

    /**
     * Allows Plate button to respond to user choice and save the new data
     */
    private class PlateButtonListener implements ActionListener
    {
        public void actionPerformed(ActionEvent e)
        {
            int i = Integer.parseInt(txtIndex.getText());
            String plate = txtData.getText();
            
            list.getTable().editPlate(i, plate);
            list.refresh();
        }
    }
}
