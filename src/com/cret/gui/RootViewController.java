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
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;

public class RootViewController {
	
	private static String newProjectName = "";
	
	private Project project;
	
	private Database database;

	private boolean savedProject = false;

	


	
	//Anchor panes inside grid cells
	private static List<AnchorPane> dataAnchor;
			
	//TextFields inside grid cells
	private static List<TextField> dataText;

	//Checkboxes inside grid cells
	private static List<CheckBox> dataCheck;
			
	//Remove buttons inside grid cells
	private static List<Button> dataRemove;
	
	private static List<Button> dataData;
	
	
			
	private static Map<String, List<LineChart<Number,Number>>> dataLineChart;
	
	
	private static int col = 0;
	private static int row = 0;

    private CanConnection can;

    private static final int MAX_DATA_POINTS = 300;
	//NODE ID, 0-8 byte data
	
    public static Map<String, List<ConcurrentLinkedQueue<Number>>> dataQ1;
	
	private static Map<String, List<NumberAxis>> xAxis;
	
	private static Map<String, List<NumberAxis>> yAxis;
	
	public static Map<String, List<XYChart.Series<Number,Number>>> xySeries;

	private static Map<String, List<Integer>> xSeriesData;
	
	@FXML
	private Button btnAdd;
	
	@FXML
	private TextField textNumber;
	
	@FXML
	public Button startButton;
	
	@FXML
	public Button stopButton;
	
	@FXML
	private Button generateButton;
	
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
	private static GridPane gridPane2;
	
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
		json.exportJSONProjects(database.getDatabaseName());
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
	
	public static void returnNewName(String name) {
		newProjectName = name;
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
	            	project = new Project(newProjectName);
	                System.out.println("Stage is closing");
	                System.out.println(newProjectName);
	                project.setProjectName(newProjectName);
	                lblProjectName.setText("Project: " + project.getProjetName());
	            }
	        });
	        System.out.println("Testing bro");
	         
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
		} else {
			GuiUtils.generateAlert(AlertType.INFORMATION, "Not running", "No CAN interface is running");
		}
		
	}
	
	@FXML
	private void connectToCan1(ActionEvent event) throws IOException, USBtinException {
		
		if (CanUtils.get1State()) { //port set ok
			
			String port = CanUtils.getConfigPort1();
			String speed = CanUtils.getConfigSpeed1();
			String mode = CanUtils.getConfigMode1();
			
			
			can = new CanConnection(port, speed, mode, this);
			
			can.connectToPort();

		} else {
			GuiUtils.generateAlert(AlertType.INFORMATION, "CAN disabled", "No correct CAN interface has been configured");
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

	
	public void addNewCell(String id, int byteNumber) {
		
		//Create elmenets
		dataAnchor.add(new AnchorPane());
		dataText.add(new TextField());
		dataCheck.add(new CheckBox());
		dataRemove.add(new Button());
		dataData.add(new Button());
		


		//xAxis.get(id).get(byteNumber).setLabel("");
		
		
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
				//TO DOd
				temp.getChildren().clear();
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
		        Thread.sleep(200);
		        Stage stage = new Stage();
		        Platform.runLater(()->{

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
		
		project = new Project("Empty");
		
		lblProjectName.setText("Project: " + project.getProjetName());
		
		
		//INITALIZE ELEMENTS
		
		dataAnchor = new ArrayList<AnchorPane>();
		dataText = new ArrayList<TextField>();
		dataCheck = new ArrayList<CheckBox>();
		dataRemove = new ArrayList<Button>();
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
		prepareTimeline();
		
		


		
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
