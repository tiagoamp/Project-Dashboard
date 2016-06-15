package br.com.tiagoamp.dashboard.service;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import br.com.tiagoamp.dashboard.model.Item;
import br.com.tiagoamp.dashboard.model.Project;
import br.com.tiagoamp.dashboard.model.Sprint;
import br.com.tiagoamp.dashboard.model.Status;

/**
 * Project Chart Generator.
 * 
 * @author tiagoamp
 */
public class ProjectChartsGenerator {
	
	public ProjectChartsGenerator(Project project) {
		this.project = project;
	}
	
	private Project project;
	
	
	public String generateChartsCode() {
		StringBuilder strB = new StringBuilder();
		strB.append( "// Load the Visualization API and the piechart package. \n" +
					 "google.load('visualization', '1.0', {'packages':['corechart']}); \n" +
					 "// Set a callback to run when the Google Visualization API is loaded. \n" +
					 "google.setOnLoadCallback(drawChart); \n" +
          			 "// Callback that creates and populates a data table, instantiates the pie chart, passes in the data and draws it. \n" +
					 "function drawChart() { \n" );
		
		// pie charts
		
			// Itens per status
		strB.append( "// Create the data table. \n" +
					 "var data = google.visualization.arrayToDataTable([ \n" +
					 	"['Status', 'Itens'], \n" +
					 	"['TO DO'," + project.getItensPerStatus(Status.TO_DO).size() + "], \n" +  
					 	"['DOING'," + project.getItensPerStatus(Status.DOING).size() + "], \n" + 
					 	"['DONE', " + project.getItensPerStatus(Status.DONE).size() + "], \n" + 
					 	"['TESTS', " + project.getItensPerStatus(Status.TESTS).size() + "], \n" + 
					 	"['BLOCKED', " + project.getItensPerStatus(Status.BLOCKED).size() + "], \n" + 
					 	"]); \n" );
		
			// Work done x not done (quantity)
		List<Item> itensReady = project.getItensPerStatus(Status.DONE);
		List<Item> itensNotReady = new ArrayList<>();
		itensNotReady.addAll(project.getBacklog());
		itensNotReady.removeAll(itensReady);
		
		strB.append( "// Create the data table. \n" +
				 "var data3 = google.visualization.arrayToDataTable([ \n" +
				 	"['Status', 'Itens'], \n" +
				 	"['DONE', " + itensReady.size() + "], \n" + 
				 	"['NOT READY', " + itensNotReady.size() + "], \n" +				 	 
				 	"]); \n" );
		
			// Work done x not done (points)
		int totalReadyPonts = 0;
		for (Item item : itensReady) {
			totalReadyPonts += item.getPoints();
		}
		
		strB.append( "// Create the data table. \n" +
				 "var data4 = google.visualization.arrayToDataTable([ \n" +
				 	"['Status', 'Itens'], \n" +
				 	"['DONE', " + totalReadyPonts + "], \n" + 
				 	"['NOT READY', " + (project.getEstimatedTotalPoints() - totalReadyPonts) + "], \n" +				 	 
				 	"]); \n" );
		
		// burndown chart		
		strB.append( "// Create the data table. \n" +
					 "var data2 = new google.visualization.DataTable(); \n" +
					 "data2.addColumn('number', 'Dias'); \n" + 
					 "data2.addColumn('number', 'Planejado'); \n" + 
					 "data2.addColumn('number', 'Executado'); \n" + 
					 "data2.addRows([ \n" );
		
		float totalPoints = project.getEstimatedTotalPoints();
		Calendar cal = Calendar.getInstance();
		cal.setTime(project.getInitialDate());
		int qtDaysPreviousSprint = 0;
				
		for (Sprint sprint : project.getSprints()) { // for each sprint
			float sprintPoints = project.getTotalPointsInSprint(sprint.getNumber());
			float r = sprint.getDailyRatio(sprintPoints);	
			
			float[] plannedCoordinates = getDailyCoordinates(r, sprint.getQtDaysInSprint(), totalPoints);
						
			for (int i = 0; i < plannedCoordinates.length; i++) {
				float y1 = plannedCoordinates[i];
				float y2 = this.getActualCoordenate(cal.getTime());
				if (y2 == 0) y2 = project.getEstimatedTotalPoints();
				
				int x = qtDaysPreviousSprint + i;
				
				strB.append( String.format("[%d,%.2f,%.2f], <!-- %s--> %n", x,y1,y2,DateFormat.getDateInstance().format(cal.getTime())) );
				//System.out.printf("[%d,%.2f,%.2f], <!-- %s--> %n", x,y1,y2,DateFormat.getDateInstance().format(cal.getTime()) );
				cal.add(Calendar.DAY_OF_MONTH, 1);				
			}
			totalPoints -= sprintPoints;
			qtDaysPreviousSprint += sprint.getQtDaysInSprint();
		}		 
		
		strB.append( String.format("[%d,%.2f,%.2f], <!-- %s--> %n", qtDaysPreviousSprint,0F,0F,DateFormat.getDateInstance().format(cal.getTime()) ) );
		strB.append("]);" + "\n" );
		
		strB.append( "// Set chart options \n" +
					 "var options = {'title':'Itens per status', \n" + 
					 "'width':300, \n" + 
					 "'height':180}; \n" );
		
		strB.append( "// Set chart options \n" +
				 "var options3 = {'title':'Work Done (itens)', \n" + 
				 "'width':300, \n" + 
				 "'height':180}; \n" );
		
		strB.append( "// Set chart options \n" +
				 "var options4 = {'title':'Work Done (points)', \n" + 
				 "'width':300, \n" + 
				 "'height':180}; \n" );
		
		strB.append("var options2 = {'title':'Burndown (ptos de função X qtd dias)', \n" +
                    "'width':700, \n" +
                    "'height':400}; \n" );
		
		strB.append( "// Instantiate and draw our chart, passing in some options. \n" + 
					 "var chart = new google.visualization.PieChart(document.getElementById('chart_div')); \n" +
					 "chart.draw(data, options); \n" + 
					 "var chart3 = new google.visualization.PieChart(document.getElementById('chart_div3')); \n" +
					 "chart3.draw(data3, options3); \n" +
					 "var chart4 = new google.visualization.PieChart(document.getElementById('chart_div4')); \n" +
					 "chart4.draw(data4, options4); \n" +
					 "var chart2 = new google.visualization.LineChart(document.getElementById('chart_div2')); \n" + 
					 "chart2.draw(data2, options2); \n" + 
					 "} \n" );
		
		return strB.toString();
	}
	
	/**
	 * Formula: f(x) = p - x*r
	 * @param points Total points
	 * @param x
	 * @param r ratio
	 * @return
	 */
	private float getPlannedCoordenate(float points, float x, float r){
		float y = points - x*r;
		return y;
	}
	
	private float getActualCoordenate(Date date){
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.set(Calendar.HOUR, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);		
		date = cal.getTime();
		
		float totalDailyPoints = 0;
		for (Item item : project.getBacklog()) {
			if (item.getStatus() == Status.DONE && item.getEnd().compareTo(date) == 0) {
				totalDailyPoints += item.getPoints();
			}
		}
		return totalDailyPoints;
	}
	
	/**
	 * Returns an array with coordinates.
	 * @param r ratio
	 * @param qtDays
	 * @param points
	 * @return Float[] Float array
	 */
	private float[] getDailyCoordinates(float r, int qtDays, float points){
		float[] coordinates = new float[qtDays];
		for(int x = 0; x < qtDays; x++) {
			float y = getPlannedCoordenate(points, x, r);
			coordinates[x] = y;			
		}		
		return coordinates;
	}

}
