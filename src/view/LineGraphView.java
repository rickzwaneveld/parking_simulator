package view;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.Stroke;

import java.util.ArrayList;

import model.SimulatorModel;

/**
 * This class is responsible for drawing a line graph, which will display the
 * total income of the parking garage. The line graph is updated every minute
 * within the simulator.
 * 
 * @author Detmer Struiksma
 * @version 03-02-2018
 */

@SuppressWarnings("serial")
public class LineGraphView extends AbstractView {
	
    private int padding;
    private int labelPadding;
    private Color lineColor;
    private Color pointColor;
    private Color gridColor;
    private static final Stroke GRAPH_STROKE = new BasicStroke(2f);
    private int pointWidth;
    private int numberYDivisions;
	private ArrayList<Integer> data;
	
	/**
	 * The constructor for the class LineGraphView.
	 * 
	 * @param simulator the model
	 */
	public LineGraphView(SimulatorModel simulator) {
		super(simulator);
		
	    padding = 25;
	    labelPadding = 25;
	    lineColor = new Color(44, 102, 230, 180);
	    pointColor = new Color(100, 100, 100, 180);
	    gridColor = new Color(200, 200, 200, 200);
	    pointWidth = 4;
        numberYDivisions = 10;
		data = getModel().getTotalIncomeData();
		data.add(0);
	}

	/**
	 * This method draws the graph and adds the points to the graph.
	 * 
	 * @param g the specified Graphics context
	 */
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        double xScale = ((double) getWidth() - (2 * padding) - labelPadding) / (data.size() - 1);
        double yScale = ((double) getHeight() - 2 * padding - labelPadding) / (getMaxNum() - getMinNum());
        

        ArrayList<Point> graphPoints = new ArrayList<>();
        for (int i = 0; i < data.size(); i++) {
            int x1 = (int) (i * xScale + padding + labelPadding);
            int y1 = (int) ((getMaxNum() - data.get(i)) * yScale + padding);
            graphPoints.add(new Point(x1, y1));
        }

        // Draw a white background.
        g2.setColor(Color.WHITE);
        g2.fillRect(padding + labelPadding, padding, getWidth() - (2 * padding) - labelPadding, getHeight() - 2 * padding - labelPadding);
        g2.setColor(Color.BLACK);

        // Create hatch marks and grid lines for the y axis.
        for (int i = 0; i < numberYDivisions + 1; i++) {
            int x0 = padding + labelPadding;
            int x1 = pointWidth + padding + labelPadding;
            int y0 = getHeight() - ((i * (getHeight() - padding * 2 - labelPadding)) / numberYDivisions + padding + labelPadding);
            int y1 = y0;
            if (data.size() > 0) {
                g2.setColor(gridColor);
                g2.drawLine(padding + labelPadding + 1 + pointWidth, y0, getWidth() - padding, y1);
                g2.setColor(Color.BLACK);
                String yLabel = ((int) ((getMinNum() + (getMaxNum() - getMinNum()) * ((i * 1.0) / numberYDivisions)) * 100)) / 100.0 + "";
                FontMetrics metrics = g2.getFontMetrics();
                int labelWidth = metrics.stringWidth(yLabel);
                g2.drawString(yLabel, x0 - labelWidth - 5, y0 + (metrics.getHeight() / 2) - 3);
            }
            g2.drawLine(x0, y0, x1, y1);
        }

        // Create hatch marks and grid lines for the x axis.
        for (int i = 0; i < data.size(); i++) {
            if (data.size() > 1) {
                int x0 = i * (getWidth() - padding * 2 - labelPadding) / (data.size() - 1) + padding + labelPadding;
                int x1 = x0;
                int y0 = getHeight() - padding - labelPadding;
                int y1 = y0 - pointWidth;
                if ((i % ((int) ((data.size() / 20.0)) + 1)) == 0) {
                    g2.setColor(gridColor);
                    g2.drawLine(x0, getHeight() - padding - labelPadding - 1 - pointWidth, x1, padding);
                    g2.setColor(Color.BLACK);
                    String xLabel = i + "";
                    FontMetrics metrics = g2.getFontMetrics();
                    int labelWidth = metrics.stringWidth(xLabel);
                    g2.drawString(xLabel, x0 - labelWidth / 2, y0 + metrics.getHeight() + 3);
                }
                g2.drawLine(x0, y0, x1, y1);
            }
        }

        // Create the x and y axes.
        g2.drawLine(padding + labelPadding, getHeight() - padding - labelPadding, padding + labelPadding, padding);
        g2.drawLine(padding + labelPadding, getHeight() - padding - labelPadding, getWidth() - padding, getHeight() - padding - labelPadding);

        Stroke oldStroke = g2.getStroke();
        g2.setColor(lineColor);
        g2.setStroke(GRAPH_STROKE);
        for (int i = 0; i < graphPoints.size() - 1; i++) {
            int x1 = graphPoints.get(i).x;
            int y1 = graphPoints.get(i).y;
            int x2 = graphPoints.get(i + 1).x;
            int y2 = graphPoints.get(i + 1).y;
            g2.drawLine(x1, y1, x2, y2);
        }

        g2.setStroke(oldStroke);
        g2.setColor(pointColor);
        for (int i = 0; i < graphPoints.size(); i++) {
            int x = graphPoints.get(i).x - pointWidth / 2;
            int y = graphPoints.get(i).y - pointWidth / 2;
            int ovalW = pointWidth;
            int ovalH = pointWidth;
            g2.fillOval(x, y, ovalW, ovalH);
        }
    }
    
    /**
     * This method returns the lowest number in the data collection. It is used 
     * to draw the graph correctly.
     * 
     * @return minNum the lowest score in the collection
     */
    private double getMinNum() {
    	int minNum= data.get(0);
    	return minNum;
    }

    /**
     * This method returns the highest number in the data collection. It is used 
     * to draw the graph correctly.
     * 
     * @return maxNum the highest score in the collection
     */
    private double getMaxNum() {
        double maxNum = Double.MIN_VALUE;
        for (int d : data) {
        	maxNum = Math.max(maxNum, d);
        }
        return maxNum;
    }
}
