import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

/**
 *  BarChart Class visualizes Customer data
 *  by comparing # of visits by customer
 *  */ 
public class BarChart extends JPanel {

    // Bar Chart axii
    private ArrayList<Integer> data;
    private ArrayList<String> labels;
    // References to Table and Class object for use in later function
    private Table t;
    private BarChart chart;
    /**
     * Bar Chart Constructor - initializes axis data and triggers GUI display
     * @param t - Table object
     */
    public BarChart(Table t)
    {
        //initializing axis data
        data = new ArrayList<Integer>();
        labels = new ArrayList<String>();
        //initializing table n chart references
        this.t = t;
        chart = this;
        //populating axis data and triggering GUI display
        getChartData(t);
        showGUI();
    }
    
    /**
     * paintComponent() - draws bar on chart for each customer, length determined by values in data
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        //initializing dimension variables
        int width = getWidth();
        int height = getHeight();
        int padding = 25;
        int barWidth = (width - 2 * padding) / data.size();
        int maxData = getMaxData();

        // Draw bars
        for (int i = 0; i < data.size(); i++) {
            //dimensions
            int barHeight = (int)(((double)data.get(i) / maxData) * (height - 2 * padding));
            int x = padding + i * barWidth;
            int y = height - padding - barHeight;
            //draw bar of dimensions at coordinates with pink color
            g.setColor(Color.PINK);
            g.fillRect(x, y, barWidth - 10, barHeight);

            // Draw label
            g.setColor(Color.BLACK);
            g.drawString(labels.get(i), x + (barWidth - 10) / 4, height - 5);
            g.drawString(data.get(i)+" visit(s)", x + (barWidth - 10) / 4,y);
        }
    }

    /**
     * getMaxData() - returns maximum value among data list
     * @return maximum value
     */
    private int getMaxData() {
        int max = Integer.MIN_VALUE;
        for (int val : data) {
            if (val > max) {
                max = val;
            }
        }
        return max;
    }
    /**
     * getChartData() - utilizes table object to populate label and data objects
     * @param t - Table object
     */
    public void getChartData(Table t)
    {
        //getting arraylist of customers from table
        t.sortCust();
        ArrayList<Customer> cus = t.getCustomerList();
        //for each customer
        for(Customer c: cus)
        {   
            //add name to labels list
            labels.add(c.getName());
            //determine number of visits
            int visitCount = 0;
            for(Vehicle v: c.getOwnership())
            {
                for(Service _: v.getServiceHist())
                {
                    visitCount++;
                }
            }
            //add determined visit count to data list
            data.add(visitCount);
        }
    }
    /**
     * showGUI() - creates jframe, adds chart object, reveals window to user
     */
    public void showGUI() {
        JFrame frame = new JFrame("Bar Chart");
        frame.add(chart);
        frame.setSize(800, 400);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
