import java.awt.*;
import java.awt.event.WindowEvent;
import java.awt.event.WindowFocusListener;
import java.awt.geom.AffineTransform;
import java.util.Collection;
import javax.swing.*;

/**
 * A Frame for the Bar Chart to be displayed in
 */
public class BarChart extends JFrame implements WindowFocusListener{
	
	/**The Chart displayed in the JFrame*/
	private final Chart bars;
	
	/**The dimensions of the frame*/
	private final int CHART_WIDTH = 700;
	private final int CHART_HEIGHT = 400;
	private final int CHART_LOCATION_X_AXIS = 400;
	private final int CHART_LOCATION_Y_AXIS = 250;
	
	/**
	 * The constructor for the BarChart
	 * @param schedule a MatchSchedule object which manages the list of referees
	 */
	public BarChart(MatchSchedule schedule){
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setTitle("Number of Referee Allocations");
        setSize(CHART_WIDTH, CHART_HEIGHT);
        setLocation(CHART_LOCATION_X_AXIS, CHART_LOCATION_Y_AXIS);
        this.setResizable(false);
        // WindowFocusListener added to update the chart when focus is returned
        addWindowFocusListener(this);
        // Create a new Bar Chart and add it to the JFrame
        bars = new Chart(schedule);
        this.add(bars);
	}
	
	@Override
	/**
	 * Method to handle WindowEvents
	 */
	public void windowGainedFocus(WindowEvent e) {
		// Repaint the Bar Chart when focus is returned
		bars.repaint();
	}

	@Override
	/**
	 * Method to handle WindowEvents
	 */
	public void windowLostFocus(WindowEvent e) {
		// Do nothing when the focus is lost from the window
	} 
	
	/**
	 * Class to draw the Bar Chart reflecting the number of match
	 * allocations of each referee stored in the system.
	 */
	private class Chart extends JComponent {
		
		/** The referees to be displayed*/
		private Collection<Referee> referees;
		
		/**The dimensions associated with the bar chart*/
		private final int LABEL_FONT_SIZE = 12;
		private final int TITLE_FONT_SIZE = 14;
		
		/**
		 * The constructor for the Chart
		 * @param schedule a MatchSchedule object which manages the list of referees
		 */
		private Chart(MatchSchedule schedule) {
			referees = schedule.sortRefsByID();
		}
		
		/**
		 * Method to draw the graphical components of the chart
		 */
		public void paintComponent(Graphics g) {
			Graphics2D g2 = (Graphics2D) g;
			
			//In order to scale the bars of the chart determine the maximum number of match allocations 
			int maxNumberOfMatchAllocations = 0;
			for (Referee ref: referees) {
				if (ref.getMatchAllocations() > maxNumberOfMatchAllocations)
					maxNumberOfMatchAllocations = ref.getMatchAllocations();
			}
			
			// Generate a new font for displaying the details of each referee
			Font refFont = new Font("Sans Serif",Font.PLAIN, LABEL_FONT_SIZE);
			FontMetrics refLabelMetrics = g2.getFontMetrics(refFont); 
			int refLabelHeight = refLabelMetrics.getHeight();
			g2.setFont(refFont);
			
			// Determine the dimensions of the chart based upon the size of the axis labels and frame dimensions
			int borderHeight = 3*(refLabelHeight);
			// The size of the border for determined based upon aesthetic preference
			int borderWidth = borderHeight;
			// The chart dimensions are the dimensions of the frame subtracted by the border
			int chartWidth = this.getWidth()-(2*borderWidth);
			int chartHeight = this.getHeight()-(2*borderHeight);
			int chartBaseline = this.getHeight() - borderHeight;
			
			if (referees.size()!=0){
				// The size of each bar is the width of the chart divided by the number of referees to be displayed
				int widthOfEachBar = chartWidth / referees.size();
				// Determine the height of one match allocation 
				double scaleOneMatchAllocation = chartHeight / maxNumberOfMatchAllocations;
				// The bars of the chart and referee detail labels are generated from left to right
				int refereeIndex = 0;
				for (Referee ref: referees) {
					// Determine the dimensions, then draw the bar in the chart
					int refLabelWidth = refLabelMetrics.stringWidth(ref.getRefID());
					int valueXAxis = refereeIndex * widthOfEachBar + borderWidth;
					int valueYAxis = (int)((chartBaseline - (ref.getMatchAllocations()) * scaleOneMatchAllocation));
					int barHeight = (int) (ref.getMatchAllocations() * scaleOneMatchAllocation);
					g2.setColor(Color.orange);
					g2.fillRect(valueXAxis, valueYAxis, widthOfEachBar, barHeight);
					g2.setColor(Color.black);
					g2.drawRect(valueXAxis, valueYAxis, widthOfEachBar, barHeight);
					
					// Draw the id and match allocation labels for the referee 
					Integer allocations = ref.getMatchAllocations();
					int allocationLabelWidth = refLabelMetrics.stringWidth(allocations.toString());
					int idLabelXPosition = refereeIndex * widthOfEachBar + ((widthOfEachBar-refLabelWidth)/2) + borderWidth;
					int allocationLabelXPosition = refereeIndex * widthOfEachBar + ((widthOfEachBar-allocationLabelWidth)/2) + borderWidth;
					g2.drawString(ref.getRefID(), idLabelXPosition, chartBaseline+refLabelHeight);				
					g2.drawString(allocations.toString(), allocationLabelXPosition, chartBaseline-barHeight-refLabelHeight);
					refereeIndex++;
				}
			}
			
			// Draw the x and y axis title
			Font title = new Font("Sans Serif",Font.BOLD, TITLE_FONT_SIZE);
			FontMetrics titleMetrics = g2.getFontMetrics(title);
			g2.setFont(title);
			int xTitleHeight = titleMetrics.getHeight();
			String xAxisTitle = "Referee ID";
			String yAxisTitle = "Number of Match Allocations";
			int xAxisTitleWidth = titleMetrics.stringWidth(xAxisTitle);
			g2.drawString(xAxisTitle, ((this.getWidth()-xAxisTitleWidth)/2), chartBaseline+xTitleHeight+refLabelHeight);
			
			Font theFont = g2.getFont();
			// The y-axis title is rotated through 90 degrees
		    // Create a rotation transformation for the font.
		    AffineTransform fontAT = new AffineTransform();
		    // Create a new font using a rotation transform
		    double rotation = 270*Math.PI/180;
		    fontAT.rotate(rotation);
		    Font theDerivedFont = theFont.deriveFont(fontAT);
		    g2.setFont(theDerivedFont);
			int yAxisTitleWidth = titleMetrics.getHeight();
			int yTitleHeight = titleMetrics.stringWidth(yAxisTitle);
			g2.drawString(yAxisTitle, borderWidth-yAxisTitleWidth, ((this.getHeight()-yTitleHeight)/2)+yTitleHeight);
		}
	}
}