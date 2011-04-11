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

public class VoltageGraphPanel extends JPanel {
    private TimeSeries series;
    private ChartPanel chartPanel;
    private JFreeChart chart;

    public VoltageGraphPanel() {
        this.series = new TimeSeries("Voltage");
        final TimeSeriesCollection dataset = new TimeSeriesCollection(this.series);
        this.chart =
                ChartFactory.createTimeSeriesChart("Voltage", "Time", "Voltage", dataset, false,
                        false, false);
        chart.setBackgroundPaint(Color.white);
        final XYPlot plot = (XYPlot) chart.getPlot();
        ValueAxis axis = plot.getDomainAxis();
        axis.setAutoRange(true);
        axis.setFixedAutoRange(60000.0);
        axis = plot.getRangeAxis();
        axis.setRange(0.0, 10.0);
        this.chartPanel = new ChartPanel(chart);
        this.add(chartPanel);
        chartPanel.setPreferredSize(new java.awt.Dimension(300, 300));
    }

    public void addPoint(double voltage) {
        voltage = (voltage/4096) * 1.5;
        series.add(new Millisecond(), voltage);
    }
}
