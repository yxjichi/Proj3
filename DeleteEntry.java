import java.awt.*;
import javax.swing.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class DeleteEntry extends JFrame 
{
    private JTextField index;     

    private JButton     cmdSave;
    private JButton     cmdClose;

    private JPanel      pnlCommand;
    private JPanel      pnlDisplay;

    private TableListing list;
    private DeleteEntry entry;

    public DeleteEntry(TableListing list)
    {
        entry = this;
        this.list = list;

        setTitle("Deleting Entry");
        pnlCommand = new JPanel();
        pnlDisplay = new JPanel();

        pnlDisplay.add(new JLabel("Index"));
        index = new JTextField(10);
        pnlDisplay.add(index);
        
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
            int i = Integer.parseInt(index.getText());
            TableRow r = list.getRow(i);
            
            Service s = r.getService();
            Vehicle v = r.getVehicle();
            v.delService(s);

            list.getTable().deleteRow(i);

            list.getTable().popTableRows();

            list.refresh();
            entry.setVisible(false); 
        }
                      
    }

}
