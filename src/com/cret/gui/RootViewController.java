package com.cret.gui;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.ConcurrentModificationException;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

import com.cret.db.DbUtils;
import com.cret.can.CanConnection;
import com.cret.can.CanListener;
import com.cret.can.CanUtils;
import com.cret.db.Database;
import com.cret.projects.json;
import com.cret.projects.Project;

import de.fischl.usbtin.CANMessage;
import de.fischl.usbtin.CANMessageListener;
import de.fischl.usbtin.USBtin;
import de.fischl.usbtin.USBtinException;
import javafx.animation.AnimationTimer;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Data;
import javafx.scene.chart.XYChart.Series;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;

public class RootViewController {
	
	
	public static String mode = "Dashboard"; //Analysis or Dashboard
	
	public static boolean test = false;
	
	public static StringProperty projectName = new SimpleStringProperty();
	
	public static Project project;
	
	private Database database;
	
	public static boolean dashStarted = false;
	
	private boolean savedProject = false;

	private static List<AnchorPane> dataAnchor;
	
	private static List<AnchorPane> dataAnchorDash;
			
	private static List<TextField> dataText;
	
	private static List<TextField> dataTextDash;

	private static List<CheckBox> dataCheck;

	private static List<Button> dataRemove;
	
	private static List<Button> dataRemoveDash;
	
	private static List<EditableLabel> dataEditable;
	
	private static List<Button> dataData;
	
	private static List<Button> dataDataDash;
	
	public static Map<String, List<String>> valuesInDashboard; //To save in database
	
	private CanConnection can;
	
	private static int col = 0;
	private static int colDash = 0;
	private static int row = 0;
	private static int rowDash = 0;
	
	
    private static final int MAX_DATA_POINTS = 300;
    
    private static Map<String, List<LineChart<Number,Number>>> dataLineChart;
	
    public static Map<String, List<ConcurrentLinkedQueue<Number>>> dataQ1;
	
	private static Map<String, List<NumberAxis>> xAxis;
	
	private static Map<String, List<NumberAxis>> yAxis;
	
	public static Map<String, List<XYChart.Series<Number,Number>>> xySeries;

	private static Map<String, List<Integer>> xSeriesData;
	
	
	
    private static Map<String, List<LineChart<Number,Number>>> dataLineChartDash;
	
    public static Map<String, List<ConcurrentLinkedQueue<Number>>> dataQ1Dash;
	
	private static Map<String, List<NumberAxis>> xAxisDash;
	
	private static Map<String, List<NumberAxis>> yAxisDash;
	
	public static Map<String, List<XYChart.Series<Number,Number>>> xySeriesDash;

	private static Map<String, List<Integer>> xSeriesDataDash;
	
	
	
	@FXML
	private Button btnSaveProject;
	
	@FXML
	private Button btnAdd;
	
	@FXML
	private Button btnNewProject;
	
	@FXML
	private Button btnStartDash;
	
	@FXML
	private Button btnStopDash;
	
	@FXML
	private TextField textNumber;
	
	@FXML
	public Button startButton;
	
	@FXML
	private Button btnPause;
	
	@FXML
	public Button stopButton;
	
	@FXML
	private Button btnOpenProject;
	
	@FXML
	private Button generateButton;
	
	@FXML
	private ScrollPane scrollTest;
	
	@FXML
	private Tab tabInterface2;
	
	@FXML
	private Tab tabInterface1;
	
	@FXML
	private Tab tabDashboard1;
	
	@FXML
	private Tab tabDashboard2;
	
	@FXML
	private Tab tabRAW1;
	
	@FXML
	private Tab tabRAW2;
	
	@FXML
	private AnchorPane anchorInterface2;
	
	@FXML
	private AnchorPane anchorGraph1;
	
	@FXML
	private TabPane tabPane;
	
	@FXML
	private static GridPane gridPane2;
	
	@FXML
	private static GridPane gridPaneDash;
	
	@FXML
	private Button btnConfigure;
	
	@FXML
	private Label lblProjectName;
	
	@FXML
	private MenuItem MenuListProjects;
	
	@FXML
	private MenuItem MenuNewProject;
	
	@FXML
	private MenuItem MenuOpenProject;
	
	@FXML
	private MenuItem MenuSaveProject;
	
	@FXML
	private MenuItem MenuImportProjects;
	
	@FXML
	private MenuItem MenuExportProjects;
	
	@FXML
	private MenuItem MenuCloseProjects;
	
	@FXML
	private Button btnClear;
	
	@FXML
	private ScrollPane scrollDash;
	
	@FXML
	private TableView traceTableInterface1;
	
	

	

	
	
	@FXML
	 void sendToDash(ActionEvent event) {
		//AddGraphOnDashboard
	}
	
	@FXML
	private void startDash(ActionEvent event) throws IOException, USBtinException {
		
		
		mode = "Dashboard";
		
		if (CanUtils.get1State()) { //port set ok
			
			String port = CanUtils.getConfigPort1();
			String speed = CanUtils.getConfigSpeed1();
			String mode = CanUtils.getConfigMode1();
			
			
			can = new CanConnection(port, speed, mode, this);
			
			can.connectToPort();
			System.out.println("Started");
			//btnClear.setDisable(true);
			
			try {
				Thread.sleep(200);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			dashStarted = true;
		} else {
			GuiUtils.generateAlert(AlertType.INFORMATION, "CAN disabled", "No correct CAN interface has been configured");
		}
		
	}
	
	@FXML
	private void stopDash(ActionEvent event) {
		
	}
	
	@FXML
	private void clearGraphs(ActionEvent event) {
		
		
		
		
		dataLineChart = new HashMap<>();
		dataQ1 = new HashMap<>();
		xySeries = new HashMap<>();
		xAxis = new HashMap<>();
		yAxis = new HashMap<>();
		xSeriesData = new HashMap<>();
		
		dataAnchor = new ArrayList<AnchorPane>();
		dataAnchorDash = new ArrayList<AnchorPane>();
		dataText = new ArrayList<TextField>();
		dataTextDash = new ArrayList<TextField>();
		dataCheck = new ArrayList<CheckBox>();
		dataRemove = new ArrayList<Button>();
		dataRemoveDash = new ArrayList<Button>();
		dataEditable = new ArrayList<EditableLabel>();
		dataData = new ArrayList<Button>();
		
		col = 0;
		row = 0;
		
		
		ColumnConstraints column1 = new ColumnConstraints();
		ColumnConstraints column2 = new ColumnConstraints();
		ColumnConstraints column3 = new ColumnConstraints();
		
		//Set fixed width for the columns
		column1.setPercentWidth(33.33);
		column2.setPercentWidth(33.33);
		column3.setPercentWidth(33.33);
		gridPaneDash = new GridPane();
		gridPaneDash.setGridLinesVisible(true);
		gridPaneDash.getColumnConstraints().add(column1);
		gridPaneDash.getColumnConstraints().add(column2);
		gridPaneDash.getColumnConstraints().add(column3);
		
		gridPaneDash.setHgap(10.0);
		gridPaneDash.setVgap(10.0);
		
		//GridPane
		gridPane2 = new GridPane();
		gridPane2.setGridLinesVisible(true);
		//Add columns to gridpane
		gridPane2.getColumnConstraints().add(column1);
		gridPane2.getColumnConstraints().add(column2);
		gridPane2.getColumnConstraints().add(column3);
		
		gridPane2.setHgap(10.0);
		gridPane2.setVgap(10.0);
		scrollTest.setContent(gridPane2);
		
		CanUtils.clearInterface1();
		//DELETE DATA FROM DATA STRUCTRUES
	}
	
	//TO TEST
	@FXML
	private void importProjects(ActionEvent event) {
		
		FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("JSON Files (*.json)", "*.json");
		
		Stage stage = new Stage();
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Select a project file");
		fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));
		fileChooser.getExtensionFilters().add(extFilter);
		File selectedFile = fileChooser.showOpenDialog(stage);
		
		try {
			if (json.isTextFile(selectedFile)) {
				String jsonFile = json.fileToString(selectedFile);
				if (json.isJSON(jsonFile)) {
					
					json.importJSONProjects(jsonFile);
					
				} else {
					GuiUtils.generateAlert(AlertType.WARNING, "Not valid file", "Selected file is not a JSON file");
				}
				
			} else {
				GuiUtils.generateAlert(AlertType.WARNING, "Not valid file", "Selected file is not a JSON file");
			}
		} catch (IOException e) {
			GuiUtils.generateAlert(AlertType.ERROR, "Not valid file", "Selected file is not a JSON file");
			e.printStackTrace();
		}
		
	}

	
	
	@FXML
	private void exportProjects(ActionEvent event) {
		//json.exportJSONProjects(database.getDatabaseName());
	}
	
	
	@FXML
	private void closeProject(ActionEvent event) {
		
		if (savedProject) { //Project has been saved
			project = null;
			
			lblProjectName.setText("Project: -");
			//DISABLE GUI
		}
	}
	
	
	@FXML
	private void saveProject(ActionEvent event) {
		
		project.setCompleteMap(valuesInDashboard);
		
		project.saveProject();
		savedProject = true;
	}
	
	
	
	@FXML
	private void openProjectList(ActionEvent event) {
		try {
	        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/cret/gui/ProjectList.fxml"));
	        Parent configDialog = (Parent) fxmlLoader.load();
	        Stage stage = new Stage();
	        stage.setResizable(false);
	        stage.initStyle(StageStyle.UNDECORATED);
	        stage.setTitle("CRET - Project list");
	        stage.setScene(new Scene(configDialog));
	        stage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	@FXML
	private void openConfigDialog(ActionEvent event) throws Exception {
		 try {
		        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/cret/gui/ConfigurationView.fxml"));
		        Parent configDialog = (Parent) fxmlLoader.load();
		        Stage stage = new Stage();
		        stage.getIcons().add(new Image("file:resources/icon.png"));
		        stage.setTitle("CRET - Configuration");
		        stage.setScene(new Scene(configDialog));  
		        stage.setResizable(false);
		        stage.show();
		        } catch(Exception e) {
		        	e.printStackTrace();
		        }
	}
	
	public static void returnNewName(String name) {
		 projectName.setValue(name);
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
	        
	        stage.setOnHiding(new EventHandler<WindowEvent>() {
	            public void handle(WindowEvent we) {
	            	//CHECK IF PROJECT IS CLOSED AND SAVED
	            	project = new Project(projectName.toString());
	                System.out.println("Stage is closing");
	                System.out.println(projectName.getValue());
	                project.setProjectName(projectName.getValue());
	                valuesInDashboard = new HashMap<>();
	            }
	        });
	        System.out.println("Testing bro");
	        
	        btnSaveProject.setDisable(false);
	        savedProject = false;
	        
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	@FXML
	private void disconnectCan1(ActionEvent event) throws USBtinException {
		
		if (can != null) {
			can.disconnect();
			can = null;
			startButton.setDisable(false);
			stopButton.setDisable(true);
			btnClear.setDisable(false);
		} else {
			GuiUtils.generateAlert(AlertType.INFORMATION, "Not running", "No CAN interface is running");
		}
		
	}
	
	@FXML
	private void connectToCan1(ActionEvent event) throws IOException, USBtinException {
		
		
		mode = "Analysis";
		if (CanUtils.get1State()) { //port set ok
			
			String port = CanUtils.getConfigPort1();
			String speed = CanUtils.getConfigSpeed1();
			String mode = CanUtils.getConfigMode1();
			
			
			can = new CanConnection(port, speed, mode, this);
			
			can.connectToPort();
			
			btnClear.setDisable(true);
			
			try {
				Thread.sleep(200);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		} else {
			GuiUtils.generateAlert(AlertType.INFORMATION, "CAN disabled", "No correct CAN interface has been configured");
		}
		 
	}
	
	public static void addIDDash(String id, int length) {
		
		
		dataLineChartDash = new HashMap<>();
		xySeriesDash = new HashMap<>();
		dataQ1Dash = new HashMap<>();
		xAxisDash = new HashMap<>();
		yAxisDash = new HashMap<>();
		xSeriesDataDash = new HashMap<>();
		
		dataLineChartDash.put(id, new ArrayList<>());
		xySeriesDash.put(id, new ArrayList<>());
		dataQ1Dash.put(id, new ArrayList<>());
		xAxisDash.put(id, new ArrayList<>());
		yAxisDash.put(id, new ArrayList<>());
		xSeriesDataDash.put(id, new ArrayList<>());
		
		for (int i=0; i <= length; i++) {
			dataQ1Dash.get(id).add(new ConcurrentLinkedQueue<>());
			xAxisDash.get(id).add(new NumberAxis(0, MAX_DATA_POINTS, MAX_DATA_POINTS/10));
			yAxisDash.get(id).add(new NumberAxis());
			xSeriesDataDash.get(id).add(0);
			LineChart<Number,Number> lineChart;
			lineChart = new LineChart<Number, Number>(xAxisDash.get(id).get(i), yAxisDash.get(id).get(i)){
				@Override
	            protected void dataItemAdded(Series<Number, Number> series, int itemIndex, Data<Number, Number> item) {
	            }
			};
			dataLineChartDash.get(id).add(lineChart);
			xySeriesDash.get(id).add(new XYChart.Series<>());
		}
		
		
	}
	

	public static void addID(String id, int length) {
		
		//Initialize lists
		dataLineChart.put(id, new ArrayList<>());
		xySeries.put(id, new ArrayList<>());
		dataQ1.put(id, new ArrayList<>());
		xAxis.put(id, new ArrayList<>());
		yAxis.put(id, new ArrayList<>());
		xSeriesData.put(id, new ArrayList<>());
		
		
		for (int i=0; i <= length; i++) {
			dataQ1.get(id).add(new ConcurrentLinkedQueue<>());
			xAxis.get(id).add(new NumberAxis(0, MAX_DATA_POINTS, MAX_DATA_POINTS/10));
			yAxis.get(id).add(new NumberAxis());
			xSeriesData.get(id).add(0);
			LineChart<Number,Number> lineChart;
			lineChart = new LineChart<Number, Number>(xAxis.get(id).get(i), yAxis.get(id).get(i)){
				@Override
	            protected void dataItemAdded(Series<Number, Number> series, int itemIndex, Data<Number, Number> item) {
	            }
			};
			dataLineChart.get(id).add(lineChart);
			xySeries.get(id).add(new XYChart.Series<>());
		}
		
		System.out.println(dataQ1.get(id).size());
		
	}
	
	public void addNewCellToDashboard(String id, int byteNumber, String dataName) {
		
		dataAnchorDash.add(new AnchorPane());
		dataTextDash.add(new TextField());
		dataRemoveDash.add(new Button());
		dataEditable.add(new EditableLabel());
		
		if (colDash <=2) {
			gridPaneDash.add(dataAnchorDash.get(dataAnchorDash.size()-1), colDash, rowDash);
			colDash++;
		} else {
			colDash = 0;
			rowDash++;
			gridPaneDash.add(dataAnchorDash.get(dataAnchorDash.size()-1), colDash, rowDash);
			colDash++;
		}
		
		AnchorPane temp = dataAnchorDash.get(dataAnchorDash.size()-1);
		TextField tempText = dataTextDash.get(dataTextDash.size()-1);
		Button tempRemove = dataRemoveDash.get(dataRemoveDash.size()-1);
		EditableLabel tempEditable = dataEditable.get(dataEditable.size()-1);
		//Button tempData = dataData.get(dataData.size()-1);
		
		tempText.setPrefWidth(Control.USE_COMPUTED_SIZE);
		tempText.setPrefHeight(Control.USE_COMPUTED_SIZE);
		
		
		tempRemove.setText("Remove");
		tempRemove.setPrefHeight(Control.USE_COMPUTED_SIZE);
		tempRemove.setPrefHeight(Control.USE_COMPUTED_SIZE);
		
		tempEditable.setText(dataName);
		tempEditable.setPrefHeight(Control.USE_COMPUTED_SIZE);
		tempEditable.setPrefHeight(Control.USE_COMPUTED_SIZE);
		tempEditable.setFont(Font.font(16));
		
		
		//tempData.setText("View RAW");
		//tempData.setPrefHeight(Control.USE_COMPUTED_SIZE);
		//tempData.setPrefHeight(Control.USE_COMPUTED_SIZE);
		
		tempRemove.setOnAction(arg0 -> {
			try {
				temp.getChildren().clear();
				dataAnchorDash.remove(dataAnchorDash.indexOf(temp));
				valuesInDashboard.put(tempEditable.getText(), new ArrayList<>());
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
		
		
		String strByte = Integer.toString(byteNumber);
		
		dataLineChartDash.get(id).get(byteNumber).setTitle("Node: " + id + " Byte: " + strByte );
		dataLineChartDash.get(id).get(byteNumber).setLegendVisible(false);
		dataLineChartDash.get(id).get(byteNumber).getStyleClass().add("thick-chart");

		dataLineChartDash.get(id).get(byteNumber).getData().add(xySeriesDash.get(id).get(byteNumber));


		xAxisDash.get(id).get(byteNumber).setForceZeroInRange(false);
		xAxisDash.get(id).get(byteNumber).setAutoRanging(false);
		xAxisDash.get(id).get(byteNumber).setTickLabelsVisible(false);
		xAxisDash.get(id).get(byteNumber).setTickMarkVisible(false);
		xAxisDash.get(id).get(byteNumber).setMinorTickVisible(false);

		
		dataLineChartDash.get(id).get(byteNumber).setCreateSymbols(false);
	        
	    AnchorPane.setBottomAnchor(dataLineChartDash.get(id).get(byteNumber), 50.0);
	    AnchorPane.setTopAnchor(dataLineChartDash.get(id).get(byteNumber), 0.0);
	    AnchorPane.setLeftAnchor(dataLineChartDash.get(id).get(byteNumber), 0.0);
	    AnchorPane.setRightAnchor(dataLineChartDash.get(id).get(byteNumber), 0.0);

		AnchorPane.setLeftAnchor(tempEditable, 14.0);
		AnchorPane.setBottomAnchor(tempEditable, 14.0);
		
		
		AnchorPane.setBottomAnchor(tempRemove, 14.0);
		AnchorPane.setRightAnchor(tempRemove, 14.0);

		
		dataLineChartDash.get(id).get(byteNumber).setAnimated(false);
			
		temp.getChildren().add(dataLineChartDash.get(id).get(byteNumber));
		temp.getChildren().add(tempEditable);
		temp.getChildren().add(tempRemove);
		//temp.getChildren().add(tempData);
			
		scrollDash.setFitToWidth(true);
		
		scrollDash.setContent(gridPaneDash);
		
	}
	
	public void addCellFromTo(String id, int byteNumber, String dataName) {
		
		System.out.println("Executing");
		dataAnchorDash.add(new AnchorPane());
		dataTextDash.add(new TextField());
		dataRemoveDash.add(new Button());
		dataEditable.add(new EditableLabel());
		
		if (colDash <=2) {
			gridPaneDash.add(dataAnchorDash.get(dataAnchorDash.size()-1), colDash, rowDash);
			colDash++;
		} else {
			colDash = 0;
			rowDash++;
			gridPaneDash.add(dataAnchorDash.get(dataAnchorDash.size()-1), colDash, rowDash);
			colDash++;
		}
		
		AnchorPane temp = dataAnchorDash.get(dataAnchorDash.size()-1);
		TextField tempText = dataTextDash.get(dataTextDash.size()-1);
		Button tempRemove = dataRemoveDash.get(dataRemoveDash.size()-1);
		EditableLabel tempEditable = dataEditable.get(dataEditable.size()-1);
		//Button tempData = dataData.get(dataData.size()-1);
		
		tempText.setPrefWidth(Control.USE_COMPUTED_SIZE);
		tempText.setPrefHeight(Control.USE_COMPUTED_SIZE);
		
		
		tempRemove.setText("Remove");
		tempRemove.setPrefHeight(Control.USE_COMPUTED_SIZE);
		tempRemove.setPrefHeight(Control.USE_COMPUTED_SIZE);
		
		tempEditable.setText(dataName);
		tempEditable.setPrefHeight(Control.USE_COMPUTED_SIZE);
		tempEditable.setPrefHeight(Control.USE_COMPUTED_SIZE);
		tempEditable.setFont(Font.font(16));
		
		
		
		tempRemove.setOnAction(arg0 -> {
			try {
				temp.getChildren().clear();
				dataAnchorDash.remove(dataAnchorDash.indexOf(temp));
				valuesInDashboard.put(tempEditable.getText(), new ArrayList<>());
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
		
		
		String strByte = Integer.toString(byteNumber);
		
		dataLineChartDash.get(id).get(byteNumber).setTitle("Node: " + id + " Byte: " + strByte );
		dataLineChartDash.get(id).get(byteNumber).setLegendVisible(false);
		dataLineChartDash.get(id).get(byteNumber).getStyleClass().add("thick-chart");

	    //dataLineChartDash.get(id).get(byteNumber).getData().add(xySeries.get(id).get(byteNumber));
	    System.out.println("DENTRO DONDE");

		xAxisDash.get(id).get(byteNumber).setForceZeroInRange(false);
		xAxisDash.get(id).get(byteNumber).setAutoRanging(false);
		xAxisDash.get(id).get(byteNumber).setTickLabelsVisible(false);
		xAxisDash.get(id).get(byteNumber).setTickMarkVisible(false);
		xAxisDash.get(id).get(byteNumber).setMinorTickVisible(false);

		
		dataLineChartDash.get(id).get(byteNumber).setCreateSymbols(false);
	        
	    AnchorPane.setBottomAnchor(dataLineChartDash.get(id).get(byteNumber), 50.0);
	    AnchorPane.setTopAnchor(dataLineChartDash.get(id).get(byteNumber), 0.0);
	    AnchorPane.setLeftAnchor(dataLineChartDash.get(id).get(byteNumber), 0.0);
	    AnchorPane.setRightAnchor(dataLineChartDash.get(id).get(byteNumber), 0.0);

		AnchorPane.setLeftAnchor(tempEditable, 14.0);
		AnchorPane.setBottomAnchor(tempEditable, 14.0);
		
		
		AnchorPane.setBottomAnchor(tempRemove, 14.0);
		AnchorPane.setRightAnchor(tempRemove, 14.0);

		
		dataLineChartDash.get(id).get(byteNumber).setAnimated(false);
			
		temp.getChildren().add(dataLineChartDash.get(id).get(byteNumber));
		temp.getChildren().add(tempEditable);
		temp.getChildren().add(tempRemove);
		//temp.getChildren().add(tempData);
			
		scrollDash.setFitToWidth(true);
		
		scrollDash.setContent(gridPaneDash);
		
	}
	
	public void addNewCell(String id, int byteNumber) {
		
		//Create elmenets
		dataAnchor.add(new AnchorPane());
		dataText.add(new TextField());
		dataCheck.add(new CheckBox());
		dataRemove.add(new Button());
		dataData.add(new Button());
		
		if (col <= 2) {
			gridPane2.add(dataAnchor.get(dataAnchor.size() - 1), col, row);
			System.out.println(row + " " + col);
			col++;
		} else {
			col=0;
			row++;
			gridPane2.add(dataAnchor.get(dataAnchor.size() - 1 ), col, row);
			col++;
		}
		
		
		AnchorPane temp = dataAnchor.get(dataAnchor.size()-1);
		CheckBox tempCheck = dataCheck.get(dataCheck.size()-1);
		TextField tempText = dataText.get(dataText.size()-1);
		Button tempRemove = dataRemove.get(dataRemove.size()-1);
		Button tempData = dataData.get(dataData.size()-1);
		
		tempText.setPrefWidth(Control.USE_COMPUTED_SIZE);
		tempText.setPrefHeight(Control.USE_COMPUTED_SIZE);
		
		tempCheck.setText("Dashboard");
		tempCheck.setPrefHeight(Control.USE_COMPUTED_SIZE);
		tempCheck.setPrefWidth(Control.USE_COMPUTED_SIZE);
		
		
		tempRemove.setText("Remove");
		tempRemove.setPrefHeight(Control.USE_COMPUTED_SIZE);
		tempRemove.setPrefHeight(Control.USE_COMPUTED_SIZE);
		
		tempData.setText("View RAW");
		tempData.setPrefHeight(Control.USE_COMPUTED_SIZE);
		tempData.setPrefHeight(Control.USE_COMPUTED_SIZE);
		
		tempRemove.setOnAction(arg0 -> {
			try {
				temp.getChildren().clear();
				dataAnchor.remove(dataAnchor.indexOf(temp));
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
		
		//Action of the dashboard checkbox
		tempCheck.setOnAction(arg0 -> {
			if (tempText.getText().equals("")) {
				tempCheck.setSelected(false);
				GuiUtils.generateAlert(AlertType.INFORMATION, "Empty value", "You must tag this value!");
			} else {
				if (project==null) {
					GuiUtils.generateAlert(AlertType.INFORMATION, "Empty project", "A project must be opened to save this value.");
					tempCheck.setSelected(false);
				} else {
					
					if (!valuesInDashboard.containsKey(tempText.getText())) {
						
						valuesInDashboard.put(tempText.getText(), new ArrayList<String>());
						valuesInDashboard.get(tempText.getText()).add(0, id);
						valuesInDashboard.get(tempText.getText()).add(1, String.valueOf(byteNumber));
						
						addIDDash(id, byteNumber);
						//addNewCellToDashboard(id, byteNumber, tempText.getText());
						addCellFromTo(id, byteNumber, tempText.getText());
						
						
						
					    dataLineChartDash.get(id).get(byteNumber).getData().add(xySeries.get(id).get(byteNumber));

					   // dashStarted = true;
						dataAnchor.remove(dataAnchor.indexOf(temp));
						dataLineChart.remove(dataAnchor.indexOf(temp));
						temp.getChildren().clear();
					} else {
						GuiUtils.generateAlert(AlertType.WARNING, "Invalid name", "The tagged name is duplicated");
					}
				}
			}
		});
		
		tempData.setOnAction(arg0 -> {
			String tempid = id;
			String num = Integer.toString(byteNumber);
			int num2 = byteNumber;
			
			try {
		        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/cret/gui/FrameViewer.fxml"));
		        Parent frameDialog = (Parent) fxmlLoader.load();
		        
		        FrameViewerController controller = fxmlLoader.getController();
		        controller.byteNumber = byteNumber;
		        controller.id = tempid;
		        
		        Stage stage = new Stage();
		        stage.getIcons().add(new Image("file:resources/icon.png"));
		        Platform.runLater(()->{
		        	try {
						Thread.sleep(200);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
		        });
		        
		        stage.setResizable(false);
		        stage.setTitle("CRET - CAN Frame Viewer");
		        
		        stage.setScene(new Scene(frameDialog));

		        stage.show();

		        

		        
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			//GuiUtils.generateAlert(AlertType.INFORMATION, "Testing only", id + " " + num + "" + dataQ1.get(id).get(num2).peek());
			
			
		});

		
		
		String strByte = Integer.toString(byteNumber);
		dataLineChart.get(id).get(byteNumber).setTitle("Node: " + id + " Byte: " + strByte );
		dataLineChart.get(id).get(byteNumber).setLegendVisible(false);
		dataLineChart.get(id).get(byteNumber).getStyleClass().add("thick-chart");
		
		dataLineChart.get(id).get(byteNumber).getData().add(xySeries.get(id).get(byteNumber));
		
		xAxis.get(id).get(byteNumber).setForceZeroInRange(false);
		xAxis.get(id).get(byteNumber).setAutoRanging(false);
		xAxis.get(id).get(byteNumber).setTickLabelsVisible(false);
		xAxis.get(id).get(byteNumber).setTickMarkVisible(false);
		xAxis.get(id).get(byteNumber).setMinorTickVisible(false);

		
		dataLineChart.get(id).get(byteNumber).setCreateSymbols(false);
	        
	    AnchorPane.setBottomAnchor(dataLineChart.get(id).get(byteNumber), 50.0);
	    AnchorPane.setTopAnchor(dataLineChart.get(id).get(byteNumber), 0.0);
	    AnchorPane.setLeftAnchor(dataLineChart.get(id).get(byteNumber), 0.0);
	    AnchorPane.setRightAnchor(dataLineChart.get(id).get(byteNumber), 0.0);

		AnchorPane.setLeftAnchor(tempText, 14.0);
		AnchorPane.setBottomAnchor(tempText, 14.0);
			
		AnchorPane.setBottomAnchor(tempCheck, 18.0);
		AnchorPane.setLeftAnchor(tempCheck, 175.0);
			
		AnchorPane.setBottomAnchor(tempRemove, 14.0);
		AnchorPane.setRightAnchor(tempRemove, 14.0);
		
		AnchorPane.setBottomAnchor(tempData, 14.0);
		AnchorPane.setRightAnchor(tempData, 84.0);
			
		dataLineChart.get(id).get(byteNumber).setAnimated(false);
			
		temp.getChildren().add(dataLineChart.get(id).get(byteNumber));
		temp.getChildren().add(tempText);
		temp.getChildren().add(tempCheck);
		temp.getChildren().add(tempRemove);
		temp.getChildren().add(tempData);
			
		scrollTest.setFitToWidth(true);
		
		scrollTest.setContent(gridPane2);

	}
	
	
	@FXML
	private void initialize() throws IOException, USBtinException {
		
		
		if (!DbUtils.checkDatabaseExist("cret.db")) {
			System.out.println("db no existe");
			File f = new File("cret.db");
			
			if (f.exists()) {
				
				if(!f.delete()){
					GuiUtils.generateAlert(AlertType.ERROR, "Error creating database", "Error produced creating database.");
					System.exit(0);
				}
				
			} else {
				
				database = new Database("cret.db");
				database.createTables("cret.db");
			}

		} else {
			System.out.println("db existe");
		}
		
		//SETUP PROJECT
		
		
		//tabInterface1.setGraphic(new Image("file:resources/icon.png"));
		tabInterface1.setGraphic(GuiUtils.buildImage("file:resources/analyze.png"));
		tabInterface2.setGraphic(GuiUtils.buildImage("file:resources/analyze.png"));
		tabDashboard1.setGraphic(GuiUtils.buildImage("file:resources/dash.png"));
		tabDashboard2.setGraphic(GuiUtils.buildImage("file:resources/dash.png"));
		
		btnConfigure.setGraphic(GuiUtils.buildImage("file:resources/config.png"));
		btnNewProject.setGraphic(GuiUtils.buildImage("file:resources/project.png"));
		startButton.setGraphic(GuiUtils.buildImage("file:resources/start.png"));
		stopButton.setGraphic(GuiUtils.buildImage("file:resources/stop.png"));
		btnClear.setGraphic(GuiUtils.buildImage("file:resources/delete.png"));
		btnPause.setGraphic(GuiUtils.buildImage("file:resources/pause.png"));
		
		//project = new Project("-");
		
		lblProjectName.setText("Project: -");
		btnSaveProject.setDisable(true);
		
		//INITALIZE ELEMENTS
		
		dataAnchor = new ArrayList<AnchorPane>();
		dataAnchorDash = new ArrayList<AnchorPane>();
		dataText = new ArrayList<TextField>();
		dataTextDash = new ArrayList<TextField>();
		dataCheck = new ArrayList<CheckBox>();
		dataRemove = new ArrayList<Button>();
		dataEditable = new ArrayList<EditableLabel>();
		dataRemoveDash = new ArrayList<Button>();
		dataData = new ArrayList<Button>();
		
		dataLineChart = new HashMap<>();
		dataQ1 = new HashMap<>();
		xySeries = new HashMap<>();
		xAxis = new HashMap<>();
		yAxis = new HashMap<>();
		xSeriesData = new HashMap<>();
		//GENERATE GRID
		
		//Create columns for the gridpane
		ColumnConstraints column1 = new ColumnConstraints();
		ColumnConstraints column2 = new ColumnConstraints();
		ColumnConstraints column3 = new ColumnConstraints();
		
		//Set fixed width for the columns
		column1.setPercentWidth(33.33);
		column2.setPercentWidth(33.33);
		column3.setPercentWidth(33.33);
		
		
		
		gridPaneDash = new GridPane();
		gridPaneDash.setGridLinesVisible(true);
		gridPaneDash.getColumnConstraints().add(column1);
		gridPaneDash.getColumnConstraints().add(column2);
		gridPaneDash.getColumnConstraints().add(column3);
		
		gridPaneDash.setHgap(10.0);
		gridPaneDash.setVgap(10.0);
		
		//GridPane
		gridPane2 = new GridPane();
		gridPane2.setGridLinesVisible(true);
		//Add columns to gridpane
		gridPane2.getColumnConstraints().add(column1);
		gridPane2.getColumnConstraints().add(column2);
		gridPane2.getColumnConstraints().add(column3);
		
		gridPane2.setHgap(10.0);
		gridPane2.setVgap(10.0);
		
		startButton.setDisable(false);
		stopButton.setDisable(true);
		btnPause.setDisable(true);
		prepareTimeline();
		
		
		lblProjectName.textProperty().bind(projectName);
		projectName.setValue("Project: -");

		
	}

    private void addDataToSeries() {
    	for (String id : dataQ1.keySet()) {
    		for (int byteNumber = 0; byteNumber < dataQ1.get(id).size(); byteNumber++) {
    	        for (int i = 0; i < 20; i++) { //-- add 20 numbers to the plot+
    	            if (dataQ1.get(id).get(byteNumber).isEmpty()) break;
    	            xySeries.get(id).get(byteNumber).getData().add(new XYChart.Data<>(xSeriesData.get(id).set(byteNumber, xSeriesData.get(id).get(byteNumber)+1), dataQ1.get(id).get(byteNumber).remove()));
    	        }
    	        // remove points to keep us at no more than MAX_DATA_POINTS
    	        if (xySeries.get(id).get(byteNumber).getData().size() > MAX_DATA_POINTS) {
    	            xySeries.get(id).get(byteNumber).getData().remove(0, xySeries.get(id).get(byteNumber).getData().size() - MAX_DATA_POINTS);
    	        }
    	        // update
    	        xAxis.get(id).get(byteNumber).setLowerBound(xSeriesData.get(id).get(byteNumber) - MAX_DATA_POINTS);
    	        xAxis.get(id).get(byteNumber).setUpperBound(xSeriesData.get(id).get(byteNumber) - 1);
    		}
    		
    	}
    	
    	if (dashStarted) {
    		
    	for (String id : dataQ1Dash.keySet()) {
    		for (int byteNumber = 0; byteNumber < dataQ1Dash.get(id).size(); byteNumber++) {
    	        for (int i = 0; i < 20; i++) { //-- add 20 numbers to the plot+
    	            if (dataQ1Dash.get(id).get(byteNumber).isEmpty()) break;
    	            xySeriesDash.get(id).get(byteNumber).getData().add(new XYChart.Data<>(xSeriesDataDash.get(id).set(byteNumber, xSeriesDataDash.get(id).get(byteNumber)+1), dataQ1Dash.get(id).get(byteNumber).remove()));
    	        }
    	        // remove points to keep us at no more than MAX_DATA_POINTS
    	        if (xySeriesDash.get(id).get(byteNumber).getData().size() > MAX_DATA_POINTS) {
    	            xySeriesDash.get(id).get(byteNumber).getData().remove(0, xySeriesDash.get(id).get(byteNumber).getData().size() - MAX_DATA_POINTS);
    	        }
    	        // update
    	        xAxisDash.get(id).get(byteNumber).setLowerBound(xSeriesDataDash.get(id).get(byteNumber) - MAX_DATA_POINTS);
    	        xAxisDash.get(id).get(byteNumber).setUpperBound(xSeriesDataDash.get(id).get(byteNumber) - 1);
    		}
    		
    	}
    	}

    }
    private void prepareTimeline() {
        // Every frame to take any data from queue and add to chart
        new AnimationTimer() {
            @Override
            public void handle(long now) {
                addDataToSeries();
            }
        }.start();
    }
    


	
}
