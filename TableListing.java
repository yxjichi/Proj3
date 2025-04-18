import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.table.*;

import java.awt.Color;
import java.awt.Component;
/**
 * TableListing Class utilizes Table Object to display customer data, permit user
 * input, sorting and display bar chart
 */
public class TableListing extends JPanel
{
    //button objects
    private JButton     cmdAddCus;
    private JButton     cmdEditCus;
    private JButton     cmdAddVehicle;
    private JButton     cmdAddService;
    private JButton     cmdDelete;
    private JButton     cmdClose;
    private JButton     cmdSortPlate;
    private JButton     cmdSortName;
    private JButton     cmdSortMileage;
    private JButton     cmdSortDate;
    private JButton     cmdSortModel;
    private JButton     cmdShowVisitChart;
    //panel objects
    private JPanel      pnlCommand;
    private JPanel      pnlDisplay;
    //reference to tablelisting object
    private TableListing thisForm;
    private  JScrollPane scrollPane;
    private Table t;
    private DefaultTableModel model;

    public TableListing() 
    {
        super(new GridLayout(2,1));
        thisForm = this;

        pnlCommand = new JPanel(new WrapLayout());
        pnlDisplay = new JPanel();
        //populate table
        t = new Table("customer.dat");
        String[] columnNames=  {"Index","First Name",
                "Last Name",
                "Model",
                "Plate",
                "Mileage",
                "Service Date",
                "Discount"};
        model=new DefaultTableModel(columnNames,0);
        //create new table with column names
        JTable table = new JTable(model) {
            @Override
            //if customer eligible for discount display specified row Colour
            public Component prepareRenderer(TableCellRenderer renderer, int row, int column) {
                Component c = super.prepareRenderer(renderer, row, column);

                if (!isRowSelected(row)) {
                    Boolean value = false;
                    if((getValueAt(row, 7).toString()) .equals("true")){
                        value = true;
                    }
                    if (value ==  true) {
                        c.setBackground(Color.PINK);
                    } else {
                        c.setBackground(Color.WHITE);
                    }
                }

            return c;
            }
        };
        showTable(t.getTableRows());

        table.setPreferredScrollableViewportSize(new Dimension(600, t.getTableRows().size()*15 + 20));
        table.setFillsViewportHeight(true);

        scrollPane = new JScrollPane(table);
       
        add(scrollPane);
        //initialize button objects
        cmdAddCus = new JButton("Add Customer");
        cmdEditCus = new JButton("Edit Customer");
        cmdAddVehicle = new JButton("Add Vehicle");
        cmdAddService = new JButton("Add Service");
        cmdSortName = new JButton("Sort by Name");
        cmdSortModel = new JButton("Sort by Model");
        cmdSortPlate = new JButton("Sort by Plate");
        cmdSortMileage = new JButton("Sort by Mileage");
        cmdSortDate = new JButton("Sort by Service Date");
        cmdDelete = new JButton("Delete Row");
        cmdShowVisitChart = new JButton("Show Visit Chart");
        cmdClose = new JButton("Close");
        //set color of buttons
        cmdClose.setBackground(Color.PINK);
        cmdDelete.setBackground(Color.PINK);
        cmdAddCus.setBackground(Color.PINK);
        cmdAddVehicle.setBackground(Color.PINK);
        cmdAddService.setBackground(Color.PINK);
        cmdShowVisitChart.setBackground(Color.PINK);
        cmdSortModel.setBackground(Color.PINK);
        cmdSortName.setBackground(Color.PINK);
        cmdSortPlate.setBackground(Color.PINK);
        cmdSortMileage.setBackground(Color.PINK);
        cmdSortDate.setBackground(Color.PINK);
        cmdEditCus.setBackground(Color.PINK);
        //add action listeners to buttons
        cmdDelete.addActionListener(new DeleteButtonListener());
        cmdClose.addActionListener(new CloseButtonListener());
        cmdAddCus.addActionListener(new AddButtonListener() );
        cmdAddVehicle.addActionListener(new AddVehicleButtonListener() );
        cmdAddService.addActionListener(new AddServiceButtonListener() );
        cmdSortModel.addActionListener(new SortModelButtonListener());
        cmdSortName.addActionListener(new SortNameButtonListener());
        cmdSortPlate.addActionListener(new SortPlateButtonListener());
        cmdSortMileage.addActionListener(new SortMileageButtonListener());
        cmdSortDate.addActionListener(new SortServiceButtonListener());
        cmdEditCus.addActionListener(new EditButtonListener());
        cmdShowVisitChart.addActionListener(new ChartButtonListener());
        //add buttons to command pane
        pnlCommand.add(cmdAddCus);
        pnlCommand.add(cmdEditCus);
        pnlCommand.add(cmdAddVehicle);
        pnlCommand.add(cmdAddService);
        pnlCommand.add(cmdSortName);
        pnlCommand.add(cmdSortModel);
        pnlCommand.add(cmdSortPlate);
        pnlCommand.add(cmdSortMileage);
        pnlCommand.add(cmdSortDate);
        pnlCommand.add(cmdDelete);
        pnlCommand.add(cmdShowVisitChart);
        pnlCommand.add(cmdClose);

        pnlCommand.setSize(new Dimension(400,1));
        //add pane to frame
        add(pnlCommand);
    }

    /**
     * getTable() - return table object
     * @return table
     */
    public Table getTable()
    {
        return t;
    }
    /**
     * getRow() - return specified tablerow
     * @param index
     * @return tablerow
     */
    public TableRow getRow(int index)
    {
        return t.getTableRows().get(index);
    }
    /**
     * getCust() - return specified Customer object
     * @param index
     * @return customer object
     */
    public Customer getCust(int index)
    {
        return t.getTableRows().get(index).getCustomer();
    }
    /**
     * getVehicle() - return specified vehicle object
     * @param index
     * @return vehicle object
     */
    public Vehicle getVehicle(int index)
    {
        return t.getTableRows().get(index).getVehicle();
    }
    /**
     * refresh() - reset table and repopulate using tablerows
     */
    public void refresh()
    {
        model.setRowCount(0);
        showTable(t.getTableRows());
    }
    /**
     * showTable() - populate graphic table using tablerowlist
     * @param plist tablerow arraylist
     */
    private void showTable(ArrayList<TableRow> plist)
    {
        if (plist.size()>0)
        {
            int i = 0;
            while(i < plist.size())
            {
                addToTable(plist.get(i),i);
                i++;
            }
        }
            
    }
    /**
     * addToTable() - add tablerow to graphic table
     * @param p tablerow object
     */
    private void addToTable(TableRow p, int index)
    {
        String[] name= p.getName().split(" ");
        if(name.length == 1)
        {
            String[] item={""+index, name[0],"n/a",""+ p.getModel(),""+p.getPlate(),""+
                        p.getMileage(),""+ p.getServiceDate(),""+p.getLoyalty()};
            model.addRow(item);  
        } else {
            String[] item={""+index,name[0],name[1],""+ p.getModel(),""+p.getPlate(),""+
                        p.getMileage(),""+ p.getServiceDate(),""+p.getLoyalty()};
            model.addRow(item);  
        }
              

    }
    /**
     * createAndShowGUI() - display window gui
     */
    private static void createAndShowGUI() {
        //Create and set up the window.
        JFrame frame = new JFrame("List of Customers and Their Service Histories");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Create and set up the content pane.
        TableListing newContentPane = new TableListing();
        newContentPane.setOpaque(true); //content panes must be opaque
        frame.setContentPane(newContentPane);

        //Display the window.
        frame.pack();
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        //Schedule a job for the event-dispatching thread:
        //creating and showing this application's GUI.
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
                public void run() {
                    createAndShowGUI();
                }
            });
    }

        /**
     * Allows Close button to respond to user choice and close the program
     */
    private class CloseButtonListener implements ActionListener
    {
        public void actionPerformed(ActionEvent e)
        {
            t.checkNull();
            t.storeFile();
            System.exit(0);
        }
    }

    /**
     * Allows Delete button to respond to user choice and close the program
     */
    private class DeleteButtonListener implements ActionListener
    {
        public void actionPerformed(ActionEvent e)
        {
            DeleteEntry _ = new DeleteEntry(thisForm);
        }

    }

    /**
     * Allows Add button to respond to user choice
     */
    private class AddButtonListener implements ActionListener
    {
        public void actionPerformed(ActionEvent e)
        {
            CustomerEntry _ = new CustomerEntry(thisForm);
        }

    }

    private class AddVehicleButtonListener implements ActionListener
    {
        public void actionPerformed(ActionEvent e)
        {
            VehicleEntry _ = new VehicleEntry(thisForm);
        }

    }

    private class AddServiceButtonListener implements ActionListener
    {
        public void actionPerformed(ActionEvent e)
        {
            ServiceEntry _ = new ServiceEntry(thisForm);
        }

    }

     /**
     * Allows Add button to respond to user choice
     */
    private class EditButtonListener implements ActionListener
    {
        public void actionPerformed(ActionEvent e)
        {
            CustomerEdit _ = new CustomerEdit(thisForm);
        }

    }

    /**
     * Allows Add button to respond to user choice
     */
    private class ChartButtonListener implements ActionListener
    {
        public void actionPerformed(ActionEvent e)
        {
            BarChart _ = new BarChart(t);
        }

    }

    /**
     * Allows Sort Name button to respond to user choice
     */
    private class SortNameButtonListener implements ActionListener
    {
        public void actionPerformed(ActionEvent e)
        {
            t.sortCust();
            model.setRowCount(0);
            int index = 0;
            for(TableRow i:t.getTableRows())
            {
                addToTable(i, index);
                index++;
            }
        }

    }

    /**
     * Allows Sort Model button to respond to user choice
     */
    private class SortModelButtonListener implements ActionListener
    {
        public void actionPerformed(ActionEvent e)
        {
            t.sortModel();
            model.setRowCount(0);
            int index = 0;
            for(TableRow i:t.getTableRows())
            {
                addToTable(i, index);
                index++;
            }
        }

    }

    /**
     * Allows Sort Plate button to respond to user choice
     */
    private class SortPlateButtonListener implements ActionListener
    {
        public void actionPerformed(ActionEvent e)
        {
            t.sortPlate();
            model.setRowCount(0);
            int index = 0;
            for(TableRow i:t.getTableRows())
            {
                addToTable(i, index);
                index++;
            }
        }

    }

    /**
     * Allows Sort Mileage button to respond to user choice
     */
    private class SortMileageButtonListener implements ActionListener
    {
        public void actionPerformed(ActionEvent e)
        {
            t.sortMileage();
            model.setRowCount(0);
            int index = 0;
            for(TableRow i:t.getTableRows())
            {
                addToTable(i, index);
                index++;
            }
        }

    }

    /**
     * Allows Sort Service button to respond to user choice
     */
    private class SortServiceButtonListener implements ActionListener
    {
        public void actionPerformed(ActionEvent e)
        {
            t.sortDate();
            model.setRowCount(0);
            int index = 0;
            for(TableRow i:t.getTableRows())
            {
                addToTable(i, index);
                index++;
            }
        }
    }
}
