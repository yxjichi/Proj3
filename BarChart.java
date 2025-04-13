import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class BarChart extends JPanel {

    // Example data for the bar chart
    private ArrayList<Integer> data;
    private ArrayList<String> labels;
    private Table t;
    private BarChart chart;
    public BarChart(Table t)
    {
        data = new ArrayList<Integer>();
        labels = new ArrayList<String>();

        this.t = t;
        chart = this;
        getChartData(t);
        showGUI();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        int width = getWidth();
        int height = getHeight();
        int padding = 25;
        int barWidth = (width - 2 * padding) / data.size();
        int maxData = getMaxData();

        // Draw bars
        for (int i = 0; i < data.size(); i++) {
            int barHeight = (int)(((double)data.get(i) / maxData) * (height - 2 * padding));
            int x = padding + i * barWidth;
            int y = height - padding - barHeight;

            g.setColor(Color.PINK);
            g.fillRect(x, y, barWidth - 10, barHeight);

            // Draw label
            g.setColor(Color.BLACK);
            g.drawString(labels.get(i), x + (barWidth - 10) / 4, height - 5);
            g.drawString(data.get(i)+" visit(s)", x + (barWidth - 10) / 4,y);
        }
    }

    private int getMaxData() {
        int max = Integer.MIN_VALUE;
        for (int val : data) {
            if (val > max) {
                max = val;
            }
        }
        return max;
    }

    public void getChartData(Table t)
    {
        ArrayList<Customer> cus = t.getCustomerList();
        for(Customer c: cus)
        {
            labels.add(c.getName());
            int visitCount = 0;
            for(Vehicle v: c.getOwnership())
            {
                for(Service _: v.getServiceHist())
                {
                    visitCount++;
                }
            }
            data.add(visitCount);
        }
    }
    public void showGUI() {
        JFrame frame = new JFrame("Bar Chart");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(chart);
        frame.setSize(500, 400);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        
    }
}
