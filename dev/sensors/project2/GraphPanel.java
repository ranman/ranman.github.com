import java.awt.Color;

import javax.swing.JPanel;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.time.Millisecond;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;

/**
 * Awesome class that is a standard graph. You can declare the range, axis
 * names, and all that fun stuff.
 * 
 * @author Joseph Randall Hunt
 * @author Chris Blades
 * @author Ben Rudolph
 * @version Mar 12, 2010
 */
public class GraphPanel extends JPanel {
    private static final long    serialVersionUID = -3655708774473052136L;
    private TimeSeriesCollection seriesCollection;
    private ChartPanel           chartPanel;
    private JFreeChart           chart;
    private String               title;

    /**
     * Creates a new window
     * 
     * @param title
     * @param xName
     * @param yName
     * @param yMin
     * @param yMax
     */
    public GraphPanel(String title, String xName, String yName, double yMin, double yMax) {
        this.seriesCollection = new TimeSeriesCollection();
        this.seriesCollection.addSeries(new TimeSeries("Node 0"));
        this.seriesCollection.addSeries(new TimeSeries("Node 1"));
        this.seriesCollection.addSeries(new TimeSeries("Node 2"));
        this.seriesCollection.addSeries(new TimeSeries("Node 3"));
        this.seriesCollection.addSeries(new TimeSeries("Node 4"));
        this.title = title;
        this.chart =
                ChartFactory.createTimeSeriesChart(title, xName, yName, this.seriesCollection,
                        true, false, false);
        chart.setBackgroundPaint(Color.white);
        final XYPlot plot = chart.getXYPlot();
        ValueAxis axis = plot.getDomainAxis();
        axis.setAutoRange(true);
        axis.setFixedAutoRange(10000.0); // 10 Seconds
        axis = plot.getRangeAxis();
        axis.setRange(yMin, yMax);
        this.chartPanel = new ChartPanel(chart);
        this.add(chartPanel);
        chartPanel.setPreferredSize(new java.awt.Dimension(650, 310));
    }

    /**
     * This adds a point to the graph
     * 
     * @param series
     * @param value
     */
    public void addPoint(int series, double value) {
        this.seriesCollection.getSeries(series).add(new Millisecond(), value);
    }
}
