package com.cret.staticData;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentLinkedQueue;

import com.cret.can.CanTable;

import javafx.collections.ObservableList;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;

/**
 * @author Adrian Marcos
 *
 */
public class structures {
	
	public static String mode = "RAW"; 
	 // Analysis or Dashboard or RAW

	public static Map<String, List<String>> valuesInDashboard; // To save in database


	//ANALYSIS
	
	public static Map<String, List<LineChart<Number, Number>>> dataLineChart;

	public static Map<String, List<ConcurrentLinkedQueue<Number>>> dataQ1;
	
	public static Map<String, List<NumberAxis>> xAxis;

	public static Map<String, List<NumberAxis>> yAxis;

	public static Map<String, List<XYChart.Series<Number, Number>>> xySeries;

	public static Map<String, List<Integer>> xSeriesData;

	
	//DASHBOARD
	
	public static Map<String, List<LineChart<Number, Number>>> dataLineChartDash;

	public static Map<String, List<ConcurrentLinkedQueue<Number>>> dataQ1Dash;

	public static Map<String, List<NumberAxis>> xAxisDash;

	public static Map<String, List<NumberAxis>> yAxisDash;

	public static Map<String, List<XYChart.Series<Number, Number>>> xySeriesDash;

	public static Map<String, List<Integer>> xSeriesDataDash;
	
	
	
	//RAW
	public static ObservableList<CanTable> dataTable;
	
	public static boolean monitor = true;
	
	
	public static void resetDashboardStructures() {
		
		dataLineChartDash = new HashMap<>();
		dataQ1Dash = new HashMap<>();
		xAxisDash = new HashMap<>();
		yAxisDash = new HashMap<>();
		xySeriesDash = new HashMap<>();
		xSeriesDataDash = new HashMap<>();
	}
	
	public static void resetAnalisysStructures() {
		
		dataLineChart = new HashMap<>();
		dataQ1 = new HashMap<>();
		xAxis = new HashMap<>();
		yAxis = new HashMap<>();
		xySeries = new HashMap<>();
		xSeriesData = new HashMap<>();
		
		
	}

	public static void resetRAWStructures() {
		//TODO
		
	}
}
