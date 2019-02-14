package com.cret.staticdata;

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
	
	public static String modeInterface1 = "RAW"; 
	 // Analysis or Dashboard or RAW

	public static String modeInterface2 = "RAW";
	
	public static Map<String, List<String>> valuesInDashboard1; // To save in database

	public static Map<String, List<String>> valuesInDashboard2;

	//ANALYSIS
	
	public static Map<String, List<LineChart<Number, Number>>> dataLineChart1;

	public static Map<String, List<ConcurrentLinkedQueue<Number>>> dataQ1;
	
	public static Map<String, List<NumberAxis>> xAxis1;

	public static Map<String, List<NumberAxis>> yAxis1;

	public static Map<String, List<XYChart.Series<Number, Number>>> xySeries1;

	public static Map<String, List<Integer>> xSeriesData1;

	
	//DASHBOARD
	
	public static Map<String, List<LineChart<Number, Number>>> dataLineChartDash1;

	public static Map<String, List<ConcurrentLinkedQueue<Number>>> dataQ1Dash1;

	public static Map<String, List<NumberAxis>> xAxisDash1;

	public static Map<String, List<NumberAxis>> yAxisDash1;

	public static Map<String, List<XYChart.Series<Number, Number>>> xySeriesDash1;

	public static Map<String, List<Integer>> xSeriesDataDash1;
	
	//RAW
	public static ObservableList<CanTable> dataTable1;
	
	public static ObservableList<CanTable> dataTableMonitor1;
	
	public static boolean monitor1 = true;
	
	
	public static void resetDashboardStructures1() {
		
		dataLineChartDash1 = new HashMap<>();
		dataQ1Dash1 = new HashMap<>();
		xAxisDash1 = new HashMap<>();
		yAxisDash1 = new HashMap<>();
		xySeriesDash1 = new HashMap<>();
		xSeriesDataDash1 = new HashMap<>();

	}
	
	public static void resetAnalisysStructures1() {
		
		dataLineChart1 = new HashMap<>();
		dataQ1 = new HashMap<>();
		xAxis1 = new HashMap<>();
		yAxis1 = new HashMap<>();
		xySeries1 = new HashMap<>();
		xSeriesData1 = new HashMap<>();
		
		
	}

	public static void resetRAWStructures() {
		//TODO
		
	}
}
