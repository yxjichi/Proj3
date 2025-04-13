import java.awt.*;
import javax.swing.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
/**
 * DeleteEntry class - Permits User to delete service entry
 */
public class DeleteEntry extends JFrame 
{
    //selected row/entry
    private JTextField index;     

    private JButton     cmdSave;
    private JButton     cmdClose;

    private JPanel      pnlCommand;
    private JPanel      pnlDisplay;

    private TableListing list;
    private DeleteEntry entry;

    /**
     * DeleteEntry Constructor - establises textfields, enables button functionality
     * @param list TableListing object
     */
    public DeleteEntry(TableListing list)
    {
        //initializing tablelisting and this class references
        entry = this;
        this.list = list;
        //setting window title
        setTitle("Deleting Entry");
        pnlCommand = new JPanel();
        pnlDisplay = new JPanel();
        //index entry
        pnlDisplay.add(new JLabel("Index"));
        index = new JTextField(10);
        pnlDisplay.add(index);
        
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
            //parsing accepted index input
            int i = Integer.parseInt(index.getText());
            TableRow r = list.getRow(i);
            
            Service s = r.getService();
            Vehicle v = r.getVehicle();
            //delete service and reflect change in Table and TableListing
            v.delService(s);
            list.getTable().deleteRow(i);
            list.getTable().popTableRows();
            list.refresh();
            entry.setVisible(false); 
        }
                      
    }

}
