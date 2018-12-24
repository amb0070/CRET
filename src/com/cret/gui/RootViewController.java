package com.cret.gui;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import com.cret.interfaces.CanUtils;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class RootViewController {

	private Task<Date> task;
	
	@FXML
	private TextField textNumber;
	
	@FXML
	private Button startButton;
	
	@FXML
	private ScrollPane scrollTest;
	
	@FXML
	private Tab tabInterface2;
	
	@FXML
	private AnchorPane anchorInterface2;
	
	@FXML
	private AnchorPane anchorGraph1;
	
	@FXML
	private TabPane tabPane;
	
	@FXML
	private GridPane gridPane2;
	
	@FXML
	private Button btnConfigure;
	
	@FXML
	private Label lblProjectName;
	
	
	@FXML
	private void openConfigDialog(ActionEvent event) throws Exception {
		 try {
		        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/cret/gui/configuration.fxml"));
		        Parent configDialog = (Parent) fxmlLoader.load();
		        Stage stage = new Stage();
		        stage.setTitle("CRET - Configuration");
		        stage.setScene(new Scene(configDialog));  
		        stage.show();
		        } catch(Exception e) {
		        	e.printStackTrace();
		        }
	}
	
	@FXML
	private void newProject(ActionEvent event) throws Exception {
		try {
	        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/cret/gui/NewProject.fxml"));
	        Parent configDialog = (Parent) fxmlLoader.load();
	        Stage stage = new Stage();
	        stage.setResizable(false);
	        stage.initStyle(StageStyle.UNDECORATED);
	        stage.setTitle("CRET - New project");
	        stage.setScene(new Scene(configDialog));
	        stage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@FXML
	private void removeAnchor(ActionEvent event) throws Exception{

		
	}
	
	@FXML
	private void generate(ActionEvent event) throws Exception{
		
		System.out.println(textNumber.getText());
		
		if (textNumber.getText() != "") {
			int number = Integer.parseInt(textNumber.getText());
			
			generateGrid(number);
			
		} else {
			System.out.println("Vacio bro");
		}
	}

	
	private void generateGrid(int number) {
		
		//Create columns for the gridpane
		ColumnConstraints column1 = new ColumnConstraints();
		ColumnConstraints column2 = new ColumnConstraints();
		ColumnConstraints column3 = new ColumnConstraints();
		
		//Set fixed width for the columns
		column1.setPercentWidth(33.33);
		column2.setPercentWidth(33.33);
		column3.setPercentWidth(33.33);
		
		//GridPane
		gridPane2 = new GridPane();
		//gridPane2.setGridLinesVisible(true);
		
		//Add columns to gridpane
		gridPane2.getColumnConstraints().add(column1);
		gridPane2.getColumnConstraints().add(column2);
		gridPane2.getColumnConstraints().add(column3);

		
		
		//AnchorPanes to fix in the grids
		
		List<AnchorPane> dataAnchor = new ArrayList<AnchorPane>();
		
		List<Button> dataButton = new ArrayList<Button>();
		
		List<TextField> dataText = new ArrayList<TextField>();
		
		List<CheckBox> dataCheck = new ArrayList<CheckBox>();
		
		List<Button> dataRemove = new ArrayList<Button>();
		
		List<Task<Date>> dataTask = new ArrayList<Task<Date>>();
		
		final NumberAxis xAxis = new NumberAxis();
		final NumberAxis yAxis = new NumberAxis();
		
		List<LineChart<Number,Number>> dataLineChart = new ArrayList<LineChart<Number,Number>>();
		
		
		//Generate AnchorPanes, lineCharts, buttons, etc
		for (int i=0; i < number; i++) {
			dataAnchor.add(new AnchorPane());
			//dataButton.add(new Button());
			dataText.add(new TextField());
			dataCheck.add(new CheckBox());
			dataRemove.add(new Button());
			dataLineChart.add(new LineChart<Number,Number>(xAxis, yAxis));
		}
		
		int col = 0;
		int row = 0;

		System.out.println(dataAnchor.size());
		
		//Add anchorpanes to grid
		for (AnchorPane a : dataAnchor) {
			
			if (col <= 2) {
				gridPane2.add(a, col, row);
				System.out.println(row + " " + col);
				col++;
			} else {
				col=0;
				row++;
				gridPane2.add(a, col, row);
				col++;
			}

		}
		
		gridPane2.setHgap(10.0);
		gridPane2.setVgap(10.0);
		
		Iterator<AnchorPane> iterador = dataAnchor.iterator();
		//Iterator<Button> iterador2 = dataButton.iterator();
		Iterator<CheckBox> itrCheckBox = dataCheck.iterator();
		Iterator<TextField> itrTextField = dataText.iterator();
		Iterator<Button> itrRemove = dataRemove.iterator();
		Iterator<LineChart<Number, Number>> itrLine = dataLineChart.iterator();
		
		while(iterador.hasNext() && itrLine.hasNext() && itrCheckBox.hasNext() && itrTextField.hasNext() && itrRemove.hasNext()){
			
			AnchorPane temp = iterador.next();
			//Button temp2 = iterador2.next();
			CheckBox tempCheck = itrCheckBox.next();
			TextField tempText = itrTextField.next();
			Button tempRemove = itrRemove.next();
			LineChart tempLine = itrLine.next();
			
			tempText.setPrefWidth(Control.USE_COMPUTED_SIZE);
			tempText.setPrefHeight(Control.USE_COMPUTED_SIZE);
			
			tempCheck.setText("Dashboard");
			tempCheck.setPrefHeight(Control.USE_COMPUTED_SIZE);
			tempCheck.setPrefWidth(Control.USE_COMPUTED_SIZE);
			
			
			tempRemove.setText("Remove");
			tempRemove.setPrefHeight(Control.USE_COMPUTED_SIZE);
			tempRemove.setPrefHeight(Control.USE_COMPUTED_SIZE);
			
			tempRemove.setOnAction(arg0 -> {
				try {
					temp.getChildren().clear();
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			});
			
			
			tempLine.setTitle("Testing");
			tempLine.setLegendVisible(false);
			tempLine.getStyleClass().add("thick-chart");
			
			
			XYChart.Series series = new XYChart.Series();
			
			series.setName("1");
	        series.getData().add(new XYChart.Data(1, 23));
	        series.getData().add(new XYChart.Data(2, 14));
	        series.getData().add(new XYChart.Data(3, 15));
	        series.getData().add(new XYChart.Data(4, 24));
	        series.getData().add(new XYChart.Data(5, 34));
	        series.getData().add(new XYChart.Data(6, 36));
	        series.getData().add(new XYChart.Data(7, 22));
	        series.getData().add(new XYChart.Data(8, 45));
	        series.getData().add(new XYChart.Data(9, 43));
	        series.getData().add(new XYChart.Data(10, 17));
	        series.getData().add(new XYChart.Data(11, 29));
	        series.getData().add(new XYChart.Data(12, 250));
	        
			
	        tempLine.getData().add(series);
			
	        tempLine.setCreateSymbols(false);
	        
			temp.setBottomAnchor(tempLine, 50.0);
			temp.setTopAnchor(tempLine, 0.0);
			temp.setLeftAnchor(tempLine, 0.0);
			temp.setRightAnchor(tempLine, 0.0);
			
			temp.setLeftAnchor(tempText, 14.0);
			temp.setBottomAnchor(tempText, 14.0);
			
			temp.setBottomAnchor(tempCheck, 18.0);
			temp.setLeftAnchor(tempCheck, 175.0);
			
			temp.setBottomAnchor(tempRemove, 14.0);
			temp.setRightAnchor(tempRemove, 14.0);
			
			
			temp.getChildren().add(tempLine);
			temp.getChildren().add(tempText);
			temp.getChildren().add(tempCheck);
			temp.getChildren().add(tempRemove);
		}
		

		



		scrollTest.setFitToWidth(true);
		
		
		scrollTest.setContent(gridPane2);
	    
	}
	
	
	@FXML
	public void initialize() {


	}
	
	
	
}
