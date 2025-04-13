import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.Scanner;
import java.util.ArrayList;
import java.io.File;
import java.io.IOException;
import javax.swing.JButton;
import javax.swing.table.*;

import java.util.Comparator;
import java.util.Collections;
import java.awt.Color;
import java.awt.Component;

public class TableListing extends JPanel
{
    private JButton     cmdAddCus;
    private JButton     cmdEditCus;
    private JButton     cmdClose;
    private JButton     cmdSortPlate;
    private JButton     cmdSortName;
    private JButton     cmdSortMileage;
    private JButton     cmdSortDate;
    private JButton     cmdSortModel;

    private JPanel      pnlCommand;
    private JPanel      pnlDisplay;
    private ArrayList<TableRow> plist;
    private TableListing thisForm;
    private  JScrollPane scrollPane;
    private Table t;
    private JTable table;
    private DefaultTableModel model;

    public TableListing() 
    {
        super(new GridLayout(2,1));
        thisForm = this;

        pnlCommand = new JPanel();
        pnlDisplay = new JPanel();

        Table t = new Table("customer.dat");
        String[] columnNames=  {"First Name",
                "Last Name",
                "Model",
                "Mileage",
                "Service Date",
                "Discount"};
        model=new DefaultTableModel(columnNames,0);
        JTable table = new JTable(model) {
            @Override
            public Component prepareRenderer(TableCellRenderer renderer, int row, int column) {
                Component c = super.prepareRenderer(renderer, row, column);

                if (!isRowSelected(row)) {
                    Boolean value = false;
                    if((getValueAt(row, 6).toString()) == "true"){
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

        table.setPreferredScrollableViewportSize(new Dimension(1000, plist.size()*15 +50));
        table.setFillsViewportHeight(true);

        scrollPane = new JScrollPane(table);
       
        add(scrollPane);

        cmdAddCus = new JButton("Add Customer");
        cmdEditCus = new JButton("Edit Customer");
        cmdSortName = new JButton("Sort by Name");
        cmdSortModel = new JButton("Sort by Model");
        cmdSortPlate = new JButton("Sort by Plate");
        cmdSortMileage = new JButton("Sort by Mileage");
        cmdSortDate = new JButton("Sort by Service Date");
        cmdClose = new JButton("Close");

        cmdClose.setBackground(Color.PINK);
        cmdAddCus.setBackground(Color.PINK);
        cmdSortModel.setBackground(Color.PINK);
        cmdSortName.setBackground(Color.PINK);
        cmdSortPlate.setBackground(Color.PINK);
        cmdSortMileage.setBackground(Color.PINK);
        cmdSortDate.setBackground(Color.PINK);

        cmdClose.addActionListener(new CloseButtonListener());
        cmdAddCus.addActionListener(new AddButtonListener() );
        cmdSortModel.addActionListener(new SortModelButtonListener());
        cmdSortName.addActionListener(new SortNameButtonListener());
        cmdSortPlate.addActionListener(new SortPlateButtonListener());
        cmdSortMileage.addActionListener(new SortMileageButtonListener());
        cmdSortDate.addActionListener(new SortServiceButtonListener());

        pnlCommand.add(cmdAddCus);
        pnlCommand.add(cmdEditCus);
        pnlCommand.add(cmdSortName);
        pnlCommand.add(cmdSortModel);
        pnlCommand.add(cmdSortPlate);
        pnlCommand.add(cmdSortMileage);
        pnlCommand.add(cmdSortDate);
        pnlCommand.add(cmdClose);

        add(pnlCommand);
    }

    
    public Table getTable()
    {
        return t;
    }

    public void refresh()
    {
        showTable(t.getTableRows());
    }
    
    private void showTable(ArrayList<TableRow> plist)
    {
        if (plist.size()>0)
        {
            int i = 0;
            while(i < plist.size())
            {
                addToTable(plist.get(i));
                i++;
            }
        }
            
    }
    private void addToTable(TableRow p)
    {
        String[] name= p.getName().split(" ");
        String[] item={name[0],name[1],""+ p.getModel(),""+p.getPlate(),""+
                        p.getMileage(),""+ p.getServiceDate(),""+p.getCustomer().getLoyalty()};
        model.addRow(item);        

    }

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
            System.exit(0);
        }

    }

    /**
     * Allows Add button to respond to user choice
     */
    private class AddButtonListener implements ActionListener
    {
        public void actionPerformed(ActionEvent e)
        {
            CustomerEntry p = new CustomerEntry(thisForm);
        }

    }

     /**
     * Allows Add button to respond to user choice
     */
    private class EditButtonListener implements ActionListener
    {
        public void actionPerformed(ActionEvent e)
        {
            CustomerEdit p = new CustomerEdit(thisForm);
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
            for(TableRow i:t.getTableRows())
            {
                addToTable(i);
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
            for(TableRow i:t.getTableRows())
            {
                addToTable(i);
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
            for(TableRow i:t.getTableRows())
            {
                addToTable(i);
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
            for(TableRow i:t.getTableRows())
            {
                addToTable(i);
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
            for(TableRow i:t.getTableRows())
            {
                addToTable(i);
            }
        }
    }
}
