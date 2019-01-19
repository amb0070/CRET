package com.cret.gui;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;
import com.cret.db.DbUtils;
import com.cret.can.CanConnection;
import com.cret.can.CanTable;
import com.cret.can.CanUtils;
import com.cret.db.Database;
import com.cret.projects.json;
import com.cret.staticData.structures;
import com.cret.projects.Project;
import de.fischl.usbtin.USBtinException;
import javafx.animation.AnimationTimer;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;

/**
 * @author Adrian Marcos
 *
 */
public class RootViewController {

	
	/**
	 * 
	 */
	private static int colAnalysis = 0;
	private static int colDashboard = 0;
	private static int rowAnalysis = 0;
	private static int rowDashboard = 0;

	/**
	 * 
	 */
	private static final int MAX_DATA_POINTS = 300;
	
	/**
	 * 
	 */
	private CanConnection canObj;
	
	/**
	 * 
	 */
	private Database databaseObj;

	/**
	 * 
	 */
	public static StringProperty projectName = new SimpleStringProperty();

	/**
	 * 
	 */
	public static Project projectObj;

	/**
	 * 
	 */
	private boolean savedProject = false;

	/**
	 * 
	 */
	private static List<AnchorPane> cellAnchorAnalysis1;

	/**
	 * 
	 */
	private static List<AnchorPane> cellAnchorDashboard1;

	/**
	 * 
	 */
	private static List<TextField> textSetNameAnalysis1;

	/**
	 * 
	 */
	private static List<TextField> textSetNameDashboard1;

	/**
	 * 
	 */
	private static List<CheckBox> checkSendToDashboard1;

	/**
	 * 
	 */
	private static List<Button> buttonRemoveCellAnalysis1;

	/**
	 * 
	 */
	private static List<Button> buttonRemoveCellDashboard1;

	/**
	 * 
	 */
	private static List<EditableLabel> dataEditable;

	/**
	 * 
	 */
	private static List<Button> buttonShowRAWAnalysis1;

	/**
	 * 
	 */
	private static List<Button> buttonShowRAWDashboard1;

	
	/*****************
	 * FXML ELEMENTS *
	 *****************/

	/**
	 * 
	 */
	@FXML
	private Button btnSaveProjectDashboard1;

	/**
	 * 
	 */
	@FXML
	private Button btnNewProjectRoot;

	/**
	 * 
	 */
	@FXML
	private Button btnStartDashboard1;

	/**
	 * 
	 */
	@FXML
	private Button btnStopDashboard1;
	
	/**
	 * 
	 */
	@FXML
	private Button btnPauseDashboard1;

	/**
	 * 
	 */
	@FXML
	public Button btnStartAnalysis1;

	/**
	 * 
	 */
	@FXML
	private Button btnPauseAnalysis1;

	/**
	 * 
	 */
	@FXML
	public Button btnStopAnalysis1;

	/**
	 * 
	 */
	@FXML
	private Button btnOpenProjectDashboard1;

	/**
	 * 
	 */
	@FXML
	private ScrollPane scrollAnalysisInterface1;
	
	/**
	 * 
	 */
	@FXML
	private Tab tabTraceRAW1;
	
	/**
	 * 
	 */
	@FXML
	private Tab tabMonitorRAW1;
	
	/**
	 * 
	 */
	@FXML
	private Tab tabTraceRAW2;
	
	/**
	 * 
	 */
	@FXML
	private Tab tabMonitorRAW2;
	
	/**
	 * 
	 */
	@FXML
	private Tab tabAnalysisInterface1;
	
	/**
	 * 
	 */
	@FXML
	private Tab tabAnalysisInterface2;

	/**
	 * 
	 */
	@FXML
	private Tab tabDashboardInterface1;

	/**
	 * 
	 */
	@FXML
	private Tab tabDashboardInterface2;

	/**
	 * 
	 */
	@FXML
	private Tab tabRAWInterface1;

	/**
	 * 
	 */
	@FXML
	private Tab tabRAWInterface2;
	
	/**
	 * 
	 */
	@FXML
	private Button btnConfigureRoot;

	/**
	 * 
	 */
	@FXML
	private Label lblProjectNameRoot;
	
	/**
	 * 
	 */
	@FXML
	private Button btnClearAnalysis1;

	/**
	 * 
	 */
	@FXML
	private ScrollPane scrollDashboardInterface1;

	/**
	 * 
	 */
	@FXML
	private TableView<CanTable> tableTraceInterface1;

	/**
	 * 
	 */
	@FXML
	private TableView<CanTable> tableMonitorInterface1;

	/**
	 * 
	 */
	@FXML
	private Button btnStartTraceRAW1;
	
	/**
	 * 
	 */
	@FXML
	private Button btnStopTraceRAW1;
	
	/**
	 * 
	 */
	@FXML
	private Button btnPauseTraceRAW1;
	
	/**
	 * 
	 */
	@FXML
	private Button btnClearTraceRAW1;
	
	/**
	 * 
	 */
	@FXML
	private Button btnStartMonitorRAW1;
	
	/**
	 * 
	 */
	@FXML
	private Button btnStopMonitorRAW1;

	/**
	 * 
	 */
	@FXML
	private Button btnPauseMonitorRAW1;
	
	/**
	 * 
	 */
	@FXML
	private Button btnClearMonitorRAW1;

	/**
	 * 
	 */
	@FXML
	public ProgressIndicator progressRoot;

	/**
	 * 
	 */
	@FXML
	private static GridPane gridPaneAnalysis1;

	/**
	 * 
	 */
	@FXML
	private static GridPane gridPaneDashboard1;

	/**
	 * 
	 */
	@FXML
	private MenuItem menuImportJson;

	/**
	 * 
	 */
	@FXML
	private MenuItem menuExportJson;
	
	/**
	 * 
	 */
	@FXML
	private MenuItem menuSpeedDetector;

	/**
	 * 
	 */
	@FXML
	private MenuItem menuAbout;
	

	
	
	@FXML
	private AnchorPane testAnchor;
	
	@FXML
	private AnchorPane anchorPaneraro;
	
	@FXML
	private AnchorPane anchorInterface2;

	@FXML
	private AnchorPane anchorGraph1;

	@FXML
	private TabPane tabPane;
	
	/************************
	 * END OF FXML ELEMENTS *
	 ************************/
	
	
	
	@FXML
	private void startMonitor1(ActionEvent event) {
		TableColumn<CanTable, String> timeCol = new TableColumn<CanTable, String>("Time");
		timeCol.setCellValueFactory(new PropertyValueFactory<CanTable, String>("time"));
		
		TableColumn<CanTable, String> idCol = new TableColumn<CanTable, String>("ID");
		idCol.setCellValueFactory(new PropertyValueFactory<CanTable, String>("id"));
		
		
		TableColumn<CanTable, String> lenCol = new TableColumn<CanTable, String>("length");
		lenCol.setCellValueFactory(new PropertyValueFactory<CanTable, String>("length"));
		
		TableColumn<CanTable, String> dataCol = new TableColumn<CanTable, String>("data");
		dataCol.setCellValueFactory(new PropertyValueFactory<CanTable, String>("data"));
		
		List<CanTable> list = new ArrayList<>();
		
		tableMonitorInterface1.getColumns().addAll(timeCol, idCol, lenCol, dataCol);
		list.add(new CanTable("TIME", "ID", "LENGTH", "DATA"));
		structures.dataTable = FXCollections.observableList(list);
		tableMonitorInterface1.setItems(structures.dataTable);

		structures.monitor = true;
		if (CanUtils.get1State()) {

			String port = CanUtils.getConfigPort1();
			String speed = CanUtils.getConfigSpeed1();
			String mode = CanUtils.getConfigMode1();

			try {
				canObj = new CanConnection(port, speed, mode, this);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (USBtinException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			canObj.connectToPort();


		} else {
			GuiUtils.generateAlert(AlertType.INFORMATION, "CAN disabled",
					"No correct CAN interface has been configured");
		}
		
		
	}
	
	@FXML
	void sendToDash(ActionEvent event) {
		// AddGraphOnDashboard
	}

	
	@FXML
	private void showAbout(ActionEvent event) {
		try {
			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/cret/gui/AboutView.fxml"));
			Parent aboutDialog = (Parent) fxmlLoader.load();
			Stage stage = new Stage();
			stage.setResizable(false);
			stage.initStyle(StageStyle.UNDECORATED);
			stage.setTitle("CRET - About");
			stage.setScene(new Scene(aboutDialog));
			stage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * @param event
	 * @throws IOException
	 * @throws USBtinException
	 */
	@FXML
	private void startDashboardInterface1(ActionEvent event) throws IOException, USBtinException {

		
		structures.mode = "Dashboard";
		
		structures.resetDashboardStructures();
		structures.resetAnalisysStructures();

		if (CanUtils.get1State()) { // port set ok

			String port = CanUtils.getConfigPort1();
			String speed = CanUtils.getConfigSpeed1();
			String mode = CanUtils.getConfigMode1();

			canObj = new CanConnection(port, speed, mode, this);

			canObj.connectToPort();
			System.out.println("Started");
			// btnClear.setDisable(true);

			try {
				Thread.sleep(200);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		} else {
			GuiUtils.generateAlert(AlertType.INFORMATION, "CAN disabled",
					"No correct CAN interface has been configured");
		}

	}

	
	/**
	 * @param event
	 */
	@FXML
	private void pauseDashboardInterface1(ActionEvent event) {
		
	}
	
	/**
	 * @param event
	 */
	@FXML
	private void stopDashboardInterface1(ActionEvent event) {

	}

	@FXML
	private void clearGraphs(ActionEvent event) {

		structures.dataLineChart = new HashMap<>();
		structures.dataQ1 = new HashMap<>();
		structures.xySeries = new HashMap<>();
		structures.xAxis = new HashMap<>();
		structures.yAxis = new HashMap<>();
		structures.xSeriesData = new HashMap<>();

		cellAnchorAnalysis1 = new ArrayList<AnchorPane>();
		cellAnchorDashboard1 = new ArrayList<AnchorPane>();
		textSetNameAnalysis1 = new ArrayList<TextField>();
		textSetNameDashboard1 = new ArrayList<TextField>();
		checkSendToDashboard1 = new ArrayList<CheckBox>();
		buttonRemoveCellAnalysis1 = new ArrayList<Button>();
		buttonRemoveCellDashboard1 = new ArrayList<Button>();
		dataEditable = new ArrayList<EditableLabel>();
		buttonShowRAWAnalysis1 = new ArrayList<Button>();

		colAnalysis = 0;
		rowAnalysis = 0;

		ColumnConstraints column1 = new ColumnConstraints();
		ColumnConstraints column2 = new ColumnConstraints();
		ColumnConstraints column3 = new ColumnConstraints();

		// Set fixed width for the columns
		column1.setPercentWidth(33.33);
		column2.setPercentWidth(33.33);
		column3.setPercentWidth(33.33);
		gridPaneDashboard1 = new GridPane();
		gridPaneDashboard1.setGridLinesVisible(true);
		gridPaneDashboard1.getColumnConstraints().add(column1);
		gridPaneDashboard1.getColumnConstraints().add(column2);
		gridPaneDashboard1.getColumnConstraints().add(column3);

		gridPaneDashboard1.setHgap(10.0);
		gridPaneDashboard1.setVgap(10.0);

		// GridPane
		gridPaneAnalysis1 = new GridPane();
		gridPaneAnalysis1.setGridLinesVisible(true);
		// Add columns to gridpane
		gridPaneAnalysis1.getColumnConstraints().add(column1);
		gridPaneAnalysis1.getColumnConstraints().add(column2);
		gridPaneAnalysis1.getColumnConstraints().add(column3);

		gridPaneAnalysis1.setHgap(10.0);
		gridPaneAnalysis1.setVgap(10.0);
		scrollAnalysisInterface1.setContent(gridPaneAnalysis1);

		CanUtils.clearInterface1();
		// DELETE DATA FROM DATA STRUCTRUES
	}


	
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
		// json.exportJSONProjects(database.getDatabaseName());
	}

	@FXML
	private void closeProject(ActionEvent event) {

		if (savedProject) { // Project has been saved
			projectObj = null;

			lblProjectNameRoot.setText("Project: -");
			// DISABLE GUI
		}
	}

	/**
	 * @param event
	 */
	@FXML
	private void saveProjectDashboard1(ActionEvent event) {

		projectObj.setCompleteMap(structures.valuesInDashboard);

		projectObj.saveProject();
		savedProject = true;
	}

	/**
	 * @param event
	 */
	@FXML
	private void openProjectListDashboard1(ActionEvent event) {
		try {
			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/cret/gui/ProjectListView.fxml"));
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

	/**
	 * @param event
	 * @throws Exception
	 */
	@FXML
	private void openConfiguration(ActionEvent event) throws Exception {
		
		try {
			
			//Load FXML file.
			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/cret/gui/ConfigurationView.fxml"));
			Parent configDialog = (Parent) fxmlLoader.load();
			Stage stage = new Stage();
			//Set icon.
			stage.getIcons().add(new Image("file:resources/icon.png"));
			//Set title.
			stage.setTitle("CRET - Configuration");
			stage.setScene(new Scene(configDialog));
			stage.setResizable(false);
			//Show window.
			stage.show();
			
		} catch (Exception e) {
			
			GuiUtils.generateAlert(AlertType.ERROR, "ERROR", "Could not open configuration window.");
			
		}
	}

	static void returnNewName(String name) {
		projectName.setValue(name);
	}

	/**
	 * TODOOOOOO
	 * @param event
	 * @throws Exception
	 */
	@FXML
	private void openNewProject(ActionEvent event) throws Exception {
		
		try {
			
			//Load FXML file.
			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/cret/gui/NewProject.fxml"));
			Parent configDialog = (Parent) fxmlLoader.load();
			Stage stage = new Stage();
			stage.setResizable(false);
			stage.initStyle(StageStyle.UNDECORATED);
			//Set title.
			stage.setTitle("CRET - New project");
			stage.setScene(new Scene(configDialog));
			//Show widnow.
			stage.show();
			
			//Returns the name of the project when stage is closed.
			stage.setOnHiding(new EventHandler<WindowEvent>() {
				public void handle(WindowEvent we) {
					// CHECK IF PROJECT IS CLOSED AND SAVED
					projectObj = new Project(projectName.toString());
					System.out.println("Stage is closing");
					System.out.println(projectName.getValue());
					projectObj.setProjectName(projectName.getValue());
					structures.valuesInDashboard = new HashMap<>();
				}
			});
			btnSaveProjectDashboard1.setDisable(false);
			savedProject = false;

		} catch (Exception e) {
			GuiUtils.generateAlert(AlertType.ERROR, "ERROR", "Could not open new project window.");
		}
	}
	
	/**
	 * @param event
	 */
	@FXML
	private void pauseAnalysisInterface1(ActionEvent event) {
		
	}

	/**
	 * @param event
	 * @throws USBtinException
	 */
	@FXML
	private void stopAnalysisInterface1(ActionEvent event) throws USBtinException {

		if (canObj != null) {
			canObj.disconnect();
			canObj = null;
			btnStartAnalysis1.setDisable(false);
			btnStopAnalysis1.setDisable(true);
			btnClearAnalysis1.setDisable(false);
		} else {
			GuiUtils.generateAlert(AlertType.INFORMATION, "Not running", "No CAN interface is running");
		}

	}

	/**
	 * @param event
	 * @throws IOException
	 * @throws USBtinException
	 */
	@FXML
	private void startAnalysisInterface1(ActionEvent event) throws IOException, USBtinException {

		structures.mode = "Analysis";

		if (CanUtils.get1State()) {

			String port = CanUtils.getConfigPort1();
			String speed = CanUtils.getConfigSpeed1();
			String mode = CanUtils.getConfigMode1();

			canObj = new CanConnection(port, speed, mode, this);

			canObj.connectToPort();

			btnClearAnalysis1.setDisable(true);
			btnStartAnalysis1.setDisable(true);
			btnStopAnalysis1.setDisable(false);
			
			try {
				Thread.sleep(200);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		} else {
			GuiUtils.generateAlert(AlertType.INFORMATION, "CAN disabled",
					"No correct CAN interface has been configured");
		}

	}

	public static void addIDDash(String id, int length) {

		structures.dataLineChartDash = new HashMap<>();
		structures.xySeriesDash = new HashMap<>();
		structures.dataQ1Dash = new HashMap<>();
		structures.xAxisDash = new HashMap<>();
		structures.yAxisDash = new HashMap<>();
		structures.xSeriesDataDash = new HashMap<>();

		structures.dataLineChartDash.put(id, new ArrayList<>());
		structures.xySeriesDash.put(id, new ArrayList<>());
		structures.dataQ1Dash.put(id, new ArrayList<>());
		structures.xAxisDash.put(id, new ArrayList<>());
		structures.yAxisDash.put(id, new ArrayList<>());
		structures.xSeriesDataDash.put(id, new ArrayList<>());

		for (int i = 0; i <= length; i++) {
			structures.dataQ1Dash.get(id).add(new ConcurrentLinkedQueue<>());
			structures.xAxisDash.get(id).add(new NumberAxis(0, MAX_DATA_POINTS, MAX_DATA_POINTS / 10));
			structures.yAxisDash.get(id).add(new NumberAxis());
			structures.xSeriesDataDash.get(id).add(0);
			LineChart<Number, Number> lineChart;
			if (structures.mode.equals("Dashboard")) {
				lineChart = new LineChart<Number, Number>(structures.xAxisDash.get(id).get(i), structures.yAxisDash.get(id).get(i)) {
					@Override
					protected void dataItemAdded(Series<Number, Number> series, int itemIndex, Data<Number, Number> item) {
					}
				};
			} else {
				lineChart = new LineChart<Number, Number>(structures.xAxis.get(id).get(i), structures.yAxis.get(id).get(i)) {
					@Override
					protected void dataItemAdded(Series<Number, Number> series, int itemIndex, Data<Number, Number> item) {
					}
				};
			}

			structures.dataLineChartDash.get(id).add(lineChart);
			structures.xySeriesDash.get(id).add(new XYChart.Series<>());
		}

	}

	public static void addID(String id, int length) {

		// Initialize lists
		structures.dataLineChart.put(id, new ArrayList<>());
		structures.xySeries.put(id, new ArrayList<>());
		structures.dataQ1.put(id, new ArrayList<>());
		structures.xAxis.put(id, new ArrayList<>());
		structures.yAxis.put(id, new ArrayList<>());
		structures.xSeriesData.put(id, new ArrayList<>());

		for (int i = 0; i <= length; i++) {
			structures.dataQ1.get(id).add(new ConcurrentLinkedQueue<>());
			structures.xAxis.get(id).add(new NumberAxis(0, MAX_DATA_POINTS, MAX_DATA_POINTS / 10));
			structures.yAxis.get(id).add(new NumberAxis());
			structures.xSeriesData.get(id).add(0);
			LineChart<Number, Number> lineChart;
			lineChart = new LineChart<Number, Number>(structures.xAxis.get(id).get(i), structures.yAxis.get(id).get(i)) {
				@Override
				protected void dataItemAdded(Series<Number, Number> series, int itemIndex, Data<Number, Number> item) {
				}
			};
			structures.dataLineChart.get(id).add(lineChart);
			structures.xySeries.get(id).add(new XYChart.Series<>());
		}

		System.out.println(structures.dataQ1.get(id).size());

	}

	public void addNewCellToDashboard(String id, int byteNumber, String dataName) {

		cellAnchorDashboard1.add(new AnchorPane());
		textSetNameDashboard1.add(new TextField());
		buttonRemoveCellDashboard1.add(new Button());
		dataEditable.add(new EditableLabel());

		if (colDashboard <= 2) {
			gridPaneDashboard1.add(cellAnchorDashboard1.get(cellAnchorDashboard1.size() - 1), colDashboard, rowDashboard);
			colDashboard++;
		} else {
			colDashboard = 0;
			rowDashboard++;
			gridPaneDashboard1.add(cellAnchorDashboard1.get(cellAnchorDashboard1.size() - 1), colDashboard, rowDashboard);
			colDashboard++;
		}

		AnchorPane temp = cellAnchorDashboard1.get(cellAnchorDashboard1.size() - 1);
		TextField tempText = textSetNameDashboard1.get(textSetNameDashboard1.size() - 1);
		Button tempRemove = buttonRemoveCellDashboard1.get(buttonRemoveCellDashboard1.size() - 1);
		EditableLabel tempEditable = dataEditable.get(dataEditable.size() - 1);
		// Button tempData = dataData.get(dataData.size()-1);

		tempText.setPrefWidth(Control.USE_COMPUTED_SIZE);
		tempText.setPrefHeight(Control.USE_COMPUTED_SIZE);

		tempRemove.setText("Remove");
		tempRemove.setPrefHeight(Control.USE_COMPUTED_SIZE);
		tempRemove.setPrefHeight(Control.USE_COMPUTED_SIZE);

		tempEditable.setText(dataName);
		tempEditable.setPrefHeight(Control.USE_COMPUTED_SIZE);
		tempEditable.setPrefHeight(Control.USE_COMPUTED_SIZE);
		tempEditable.setFont(Font.font(16));

		// tempData.setText("View RAW");
		// tempData.setPrefHeight(Control.USE_COMPUTED_SIZE);
		// tempData.setPrefHeight(Control.USE_COMPUTED_SIZE);

		tempRemove.setOnAction(arg0 -> {
			try {
				temp.getChildren().clear();
				cellAnchorDashboard1.remove(cellAnchorDashboard1.indexOf(temp));
				structures.valuesInDashboard.put(tempEditable.getText(), new ArrayList<>());
			} catch (Exception e) {
				e.printStackTrace();
			}
		});

		String strByte = Integer.toString(byteNumber);

		structures.dataLineChartDash.get(id).get(byteNumber).setTitle("Node: " + id + " Byte: " + strByte);
		structures.dataLineChartDash.get(id).get(byteNumber).setLegendVisible(false);
		structures.dataLineChartDash.get(id).get(byteNumber).getStyleClass().add("thick-chart");

		structures.dataLineChartDash.get(id).get(byteNumber).getData().add(structures.xySeriesDash.get(id).get(byteNumber));

		structures.xAxisDash.get(id).get(byteNumber).setForceZeroInRange(false);
		structures.xAxisDash.get(id).get(byteNumber).setAutoRanging(false);
		structures.xAxisDash.get(id).get(byteNumber).setTickLabelsVisible(false);
		structures.xAxisDash.get(id).get(byteNumber).setTickMarkVisible(false);
		structures.xAxisDash.get(id).get(byteNumber).setMinorTickVisible(false);

		structures.dataLineChartDash.get(id).get(byteNumber).setCreateSymbols(false);

		AnchorPane.setBottomAnchor(structures.dataLineChartDash.get(id).get(byteNumber), 50.0);
		AnchorPane.setTopAnchor(structures.dataLineChartDash.get(id).get(byteNumber), 0.0);
		AnchorPane.setLeftAnchor(structures.dataLineChartDash.get(id).get(byteNumber), 0.0);
		AnchorPane.setRightAnchor(structures.dataLineChartDash.get(id).get(byteNumber), 0.0);

		AnchorPane.setLeftAnchor(tempEditable, 14.0);
		AnchorPane.setBottomAnchor(tempEditable, 14.0);

		AnchorPane.setBottomAnchor(tempRemove, 14.0);
		AnchorPane.setRightAnchor(tempRemove, 14.0);

		structures.dataLineChartDash.get(id).get(byteNumber).setAnimated(false);

		temp.getChildren().add(structures.dataLineChartDash.get(id).get(byteNumber));
		temp.getChildren().add(tempEditable);
		temp.getChildren().add(tempRemove);
		// temp.getChildren().add(tempData);

		scrollDashboardInterface1.setFitToWidth(true);

		scrollDashboardInterface1.setContent(gridPaneDashboard1);

	}

	public void addCellFromTo(String id, int byteNumber, String dataName) {

		System.out.println("Executing");
		cellAnchorDashboard1.add(new AnchorPane());
		textSetNameDashboard1.add(new TextField());
		buttonRemoveCellDashboard1.add(new Button());
		dataEditable.add(new EditableLabel());

		if (colDashboard <= 2) {
			gridPaneDashboard1.add(cellAnchorDashboard1.get(cellAnchorDashboard1.size() - 1), colDashboard, rowDashboard);
			colDashboard++;
		} else {
			colDashboard = 0;
			rowDashboard++;
			gridPaneDashboard1.add(cellAnchorDashboard1.get(cellAnchorDashboard1.size() - 1), colDashboard, rowDashboard);
			colDashboard++;
		}

		AnchorPane temp = cellAnchorDashboard1.get(cellAnchorDashboard1.size() - 1);
		TextField tempText = textSetNameDashboard1.get(textSetNameDashboard1.size() - 1);
		Button tempRemove = buttonRemoveCellDashboard1.get(buttonRemoveCellDashboard1.size() - 1);
		EditableLabel tempEditable = dataEditable.get(dataEditable.size() - 1);
		// Button tempData = dataData.get(dataData.size()-1);

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
				cellAnchorDashboard1.remove(cellAnchorDashboard1.indexOf(temp));
				structures.valuesInDashboard.put(tempEditable.getText(), new ArrayList<>());
			} catch (Exception e) {
				e.printStackTrace();
			}
		});

		String strByte = Integer.toString(byteNumber);

		structures.dataLineChartDash.get(id).get(byteNumber).setTitle("Node: " + id + " Byte: " + strByte);
		structures.dataLineChartDash.get(id).get(byteNumber).setLegendVisible(false);
		structures.dataLineChartDash.get(id).get(byteNumber).getStyleClass().add("thick-chart");

		structures.dataLineChartDash.get(id).get(byteNumber).getData().add(structures.xySeries.get(id).get(byteNumber));
		System.out.println("DENTRO DONDE");

		structures.xAxisDash.get(id).get(byteNumber).setForceZeroInRange(false);
		structures.xAxisDash.get(id).get(byteNumber).setAutoRanging(false);
		structures.xAxisDash.get(id).get(byteNumber).setTickLabelsVisible(false);
		structures.xAxisDash.get(id).get(byteNumber).setTickMarkVisible(false);
		structures.xAxisDash.get(id).get(byteNumber).setMinorTickVisible(false);

		structures.dataLineChartDash.get(id).get(byteNumber).setCreateSymbols(false);

		AnchorPane.setBottomAnchor(structures.dataLineChartDash.get(id).get(byteNumber), 50.0);
		AnchorPane.setTopAnchor(structures.dataLineChartDash.get(id).get(byteNumber), 0.0);
		AnchorPane.setLeftAnchor(structures.dataLineChartDash.get(id).get(byteNumber), 0.0);
		AnchorPane.setRightAnchor(structures.dataLineChartDash.get(id).get(byteNumber), 0.0);

		AnchorPane.setLeftAnchor(tempEditable, 14.0);
		AnchorPane.setBottomAnchor(tempEditable, 14.0);

		AnchorPane.setBottomAnchor(tempRemove, 14.0);
		AnchorPane.setRightAnchor(tempRemove, 14.0);

		structures.dataLineChartDash.get(id).get(byteNumber).setAnimated(false);

		temp.getChildren().add(structures.dataLineChartDash.get(id).get(byteNumber));
		temp.getChildren().add(tempEditable);
		temp.getChildren().add(tempRemove);
		// temp.getChildren().add(tempData);

		scrollDashboardInterface1.setFitToWidth(true);

		scrollDashboardInterface1.setContent(gridPaneDashboard1);

	}

	public void addNewCell(String id, int byteNumber) {

		
		progressRoot.setVisible(true);
		// Create elmenets
		cellAnchorAnalysis1.add(new AnchorPane());
		textSetNameAnalysis1.add(new TextField());
		checkSendToDashboard1.add(new CheckBox());
		buttonRemoveCellAnalysis1.add(new Button());
		buttonShowRAWAnalysis1.add(new Button());

		if (colAnalysis <= 2) {
			gridPaneAnalysis1.add(cellAnchorAnalysis1.get(cellAnchorAnalysis1.size() - 1), colAnalysis, rowAnalysis);
			System.out.println(rowAnalysis + " " + colAnalysis);
			colAnalysis++;
		} else {
			colAnalysis = 0;
			rowAnalysis++;
			gridPaneAnalysis1.add(cellAnchorAnalysis1.get(cellAnchorAnalysis1.size() - 1), colAnalysis, rowAnalysis);
			colAnalysis++;
		}

		AnchorPane temp = cellAnchorAnalysis1.get(cellAnchorAnalysis1.size() - 1);
		CheckBox tempCheck = checkSendToDashboard1.get(checkSendToDashboard1.size() - 1);
		TextField tempText = textSetNameAnalysis1.get(textSetNameAnalysis1.size() - 1);
		Button tempRemove = buttonRemoveCellAnalysis1.get(buttonRemoveCellAnalysis1.size() - 1);
		Button tempData = buttonShowRAWAnalysis1.get(buttonShowRAWAnalysis1.size() - 1);

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
				cellAnchorAnalysis1.remove(cellAnchorAnalysis1.indexOf(temp));

			} catch (Exception e) {
				e.printStackTrace();
			}
		});

		// Action of the dashboard checkbox
		tempCheck.setOnAction(arg0 -> {
			if (tempText.getText().equals("")) {
				tempCheck.setSelected(false);
				GuiUtils.generateAlert(AlertType.INFORMATION, "Empty value", "You must tag this value!");
			} else {
				if (projectObj == null) {
					GuiUtils.generateAlert(AlertType.INFORMATION, "Empty project", "A project must be opened to save this value.");
					tempCheck.setSelected(false);
				} else {

					if (!structures.valuesInDashboard.containsKey(tempText.getText())) {

						structures.valuesInDashboard.put(tempText.getText(), new ArrayList<String>());
						structures.valuesInDashboard.get(tempText.getText()).add(0, id);
						structures.valuesInDashboard.get(tempText.getText()).add(1, String.valueOf(byteNumber));
						
						addIDDash(id, byteNumber);
						
						addCellFromTo(id, byteNumber, tempText.getText());

						//structures.dataLineChartDash.get(id).get(byteNumber).getData().add(structures.xySeries.get(id).get(byteNumber));
						System.out.println("Añadiendo " + id + " con byte " + String.valueOf(byteNumber)); 

						// dashStarted = true;
						//dataAnchor.remove(dataAnchor.indexOf(temp));
						//structures.dataLineChart.remove(dataAnchor.indexOf(temp));
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

			try {
				FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/cret/gui/FrameViewer.fxml"));
				Parent frameDialog = (Parent) fxmlLoader.load();

				FrameViewerController controller = fxmlLoader.getController();
				controller.byteNumber = byteNumber;
				controller.id = tempid;

				Stage stage = new Stage();
				stage.getIcons().add(new Image("file:resources/icon.png"));
				Platform.runLater(() -> {
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

			// GuiUtils.generateAlert(AlertType.INFORMATION, "Testing only", id + " " + num
			// + "" + dataQ1.get(id).get(num2).peek());

		});

		String strByte = Integer.toString(byteNumber);
		structures.dataLineChart.get(id).get(byteNumber).setTitle("Node: " + id + " Byte: " + strByte);
		structures.dataLineChart.get(id).get(byteNumber).setLegendVisible(false);
		structures.dataLineChart.get(id).get(byteNumber).getStyleClass().add("thick-chart");

		structures.dataLineChart.get(id).get(byteNumber).getData().add(structures.xySeries.get(id).get(byteNumber));

		structures.xAxis.get(id).get(byteNumber).setForceZeroInRange(false);
		structures.xAxis.get(id).get(byteNumber).setAutoRanging(false);
		structures.xAxis.get(id).get(byteNumber).setTickLabelsVisible(false);
		structures.xAxis.get(id).get(byteNumber).setTickMarkVisible(false);
		structures.xAxis.get(id).get(byteNumber).setMinorTickVisible(false);

		structures.dataLineChart.get(id).get(byteNumber).setCreateSymbols(false);

		AnchorPane.setBottomAnchor(structures.dataLineChart.get(id).get(byteNumber), 50.0);
		AnchorPane.setTopAnchor(structures.dataLineChart.get(id).get(byteNumber), 0.0);
		AnchorPane.setLeftAnchor(structures.dataLineChart.get(id).get(byteNumber), 0.0);
		AnchorPane.setRightAnchor(structures.dataLineChart.get(id).get(byteNumber), 0.0);

		AnchorPane.setLeftAnchor(tempText, 14.0);
		AnchorPane.setBottomAnchor(tempText, 14.0);

		AnchorPane.setBottomAnchor(tempCheck, 18.0);
		AnchorPane.setLeftAnchor(tempCheck, 175.0);

		AnchorPane.setBottomAnchor(tempRemove, 14.0);
		AnchorPane.setRightAnchor(tempRemove, 14.0);

		AnchorPane.setBottomAnchor(tempData, 14.0);
		AnchorPane.setRightAnchor(tempData, 84.0);

		structures.dataLineChart.get(id).get(byteNumber).setAnimated(false);

		temp.getChildren().add(structures.dataLineChart.get(id).get(byteNumber));
		temp.getChildren().add(tempText);
		temp.getChildren().add(tempCheck);
		temp.getChildren().add(tempRemove);
		temp.getChildren().add(tempData);

		scrollAnalysisInterface1.setFitToWidth(true);

		scrollAnalysisInterface1.setContent(gridPaneAnalysis1);

	}
	
	/**
	 * @param event
	 */
	@FXML
	private void startMonitorRAWInterface1(ActionEvent event) {
		
	}
	
	/**
	 * @param event
	 */
	@FXML
	private void stopMonitorRAWInterface1(ActionEvent event) {
		
	}
	
	/**
	 * @param event
	 */
	@FXML
	private void pauseMonitorRAWInterface1(ActionEvent event) {
		
	}
	
	/**
	 * @param event
	 */
	@FXML
	private void clearMonitorRAWInterface1(ActionEvent event) {
		
	}
	
	/**
	 * @param event
	 */
	@FXML
	private void clearTraceRAWInterface1(ActionEvent event) {
		
	}

	/**
	 * @param event
	 */
	@FXML
	private void stopTraceRAWInterface1(ActionEvent event) {
		
	}
	
	/**
	 * @param event
	 */
	@FXML
	private void pauseTraceRAWInterace1(ActionEvent event) {
		
	}
	
	
	/**
	 * 
	 */
	@FXML
	private void startTraceRAWInterface1(ActionEvent event) {
		
		TableColumn<CanTable, String> timeCol = new TableColumn<CanTable, String>("Time");
		timeCol.setCellValueFactory(new PropertyValueFactory<CanTable, String>("time"));
		
		TableColumn<CanTable, String> idCol = new TableColumn<CanTable, String>("ID");
		idCol.setCellValueFactory(new PropertyValueFactory<CanTable, String>("id"));
		
		
		TableColumn<CanTable, String> lenCol = new TableColumn<CanTable, String>("length");
		lenCol.setCellValueFactory(new PropertyValueFactory<CanTable, String>("length"));
		
		TableColumn<CanTable, String> dataCol = new TableColumn<CanTable, String>("data");
		dataCol.setCellValueFactory(new PropertyValueFactory<CanTable, String>("data"));
		
		List<CanTable> list = new ArrayList<>();
		
		
		tableTraceInterface1.getColumns().addAll(timeCol, idCol, lenCol, dataCol);
		list.add(new CanTable("TIME", "ID", "LENGTH", "DATA"));
		structures.dataTable = FXCollections.observableList(list);
		tableTraceInterface1.setItems(structures.dataTable);

		if (CanUtils.get1State()) {

			String port = CanUtils.getConfigPort1();
			String speed = CanUtils.getConfigSpeed1();
			String mode = CanUtils.getConfigMode1();

			try {
				canObj = new CanConnection(port, speed, mode, this);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (USBtinException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			canObj.connectToPort();


		} else {
			GuiUtils.generateAlert(AlertType.INFORMATION, "CAN disabled",
					"No correct CAN interface has been configured");
		}
	}
	
	
	
	
	@FXML
	private void initialize() throws IOException, USBtinException {

		if (!DbUtils.checkDatabaseExist("cret.db")) {
			System.out.println("db no existe");
			File f = new File("cret.db");

			if (f.exists()) {

				if (!f.delete()) {
					GuiUtils.generateAlert(AlertType.ERROR, "Error creating database",
							"Error produced creating database.");
					System.exit(0);
				}

			} else {

				databaseObj = new Database("cret.db");
				//database.connect();
				databaseObj.createTables("cret.db");
			}

		} else {
			System.out.println("db existe");
		}

		// SETUP PROJECT
		
		progressRoot.setVisible(false);

		// tabInterface1.setGraphic(new Image("file:resources/icon.png"));
		tabAnalysisInterface1.setGraphic(GuiUtils.buildImage("file:resources/analyze.png"));
		tabAnalysisInterface2.setGraphic(GuiUtils.buildImage("file:resources/analyze.png"));
		tabDashboardInterface1.setGraphic(GuiUtils.buildImage("file:resources/dash.png"));
		tabDashboardInterface2.setGraphic(GuiUtils.buildImage("file:resources/dash.png"));

		btnConfigureRoot.setGraphic(GuiUtils.buildImage("file:resources/config.png"));
		btnNewProjectRoot.setGraphic(GuiUtils.buildImage("file:resources/project.png"));
		btnStartAnalysis1.setGraphic(GuiUtils.buildImage("file:resources/start.png"));
		btnStopAnalysis1.setGraphic(GuiUtils.buildImage("file:resources/stop.png"));
		btnClearAnalysis1.setGraphic(GuiUtils.buildImage("file:resources/delete.png"));
		btnPauseAnalysis1.setGraphic(GuiUtils.buildImage("file:resources/pause.png"));
		btnStartDashboard1.setGraphic(GuiUtils.buildImage("file:resources/start.png"));
		btnPauseDashboard1.setGraphic(GuiUtils.buildImage("file:resources/pause.png"));
		btnStopDashboard1.setGraphic(GuiUtils.buildImage("file:resources/stop.png"));

		btnStartTraceRAW1.setGraphic(GuiUtils.buildImage("file:resources/start.png"));
		btnPauseTraceRAW1.setGraphic(GuiUtils.buildImage("file:resources/pause.png"));
		btnStopTraceRAW1.setGraphic(GuiUtils.buildImage("file:resources/stop.png"));
		tabRAWInterface1.setGraphic(GuiUtils.buildImage("file:resources/binary.png"));
		tabRAWInterface2.setGraphic(GuiUtils.buildImage("file:resources/binary.png"));
		btnOpenProjectDashboard1.setGraphic(GuiUtils.buildImage("file:resources/open.png"));
		btnSaveProjectDashboard1.setGraphic(GuiUtils.buildImage("file:resources/save.png"));
		
		btnClearTraceRAW1.setGraphic(GuiUtils.buildImage("file:resources/delete.png"));
		btnClearMonitorRAW1.setGraphic(GuiUtils.buildImage("file:resources/delete.png"));
		
		btnStartMonitorRAW1.setGraphic(GuiUtils.buildImage("file:resources/start.png"));
		btnStopMonitorRAW1.setGraphic(GuiUtils.buildImage("file:resources/stop.png"));
		btnPauseMonitorRAW1.setGraphic(GuiUtils.buildImage("file:resources/pause.png"));

		// project = new Project("-");

		lblProjectNameRoot.setText("Project: -");
		btnSaveProjectDashboard1.setDisable(true);

		// INITALIZE ELEMENTS

		cellAnchorAnalysis1 = new ArrayList<AnchorPane>();
		cellAnchorDashboard1 = new ArrayList<AnchorPane>();
		textSetNameAnalysis1 = new ArrayList<TextField>();
		textSetNameDashboard1 = new ArrayList<TextField>();
		checkSendToDashboard1 = new ArrayList<CheckBox>();
		buttonRemoveCellAnalysis1 = new ArrayList<Button>();
		dataEditable = new ArrayList<EditableLabel>();
		buttonRemoveCellDashboard1 = new ArrayList<Button>();
		buttonShowRAWAnalysis1 = new ArrayList<Button>();

		structures.dataLineChart = new HashMap<>();
		structures.dataQ1 = new HashMap<>();
		structures.xySeries = new HashMap<>();
		structures.xAxis = new HashMap<>();
		structures.yAxis = new HashMap<>();
		structures.xSeriesData = new HashMap<>();
		// GENERATE GRID

		// Create columns for the gridpane
		ColumnConstraints column1 = new ColumnConstraints();
		ColumnConstraints column2 = new ColumnConstraints();
		ColumnConstraints column3 = new ColumnConstraints();

		// Set fixed width for the columns
		column1.setPercentWidth(33.33);
		column2.setPercentWidth(33.33);
		column3.setPercentWidth(33.33);

		gridPaneDashboard1 = new GridPane();
		gridPaneDashboard1.setGridLinesVisible(true);
		gridPaneDashboard1.getColumnConstraints().add(column1);
		gridPaneDashboard1.getColumnConstraints().add(column2);
		gridPaneDashboard1.getColumnConstraints().add(column3);

		gridPaneDashboard1.setHgap(10.0);
		gridPaneDashboard1.setVgap(10.0);

		// GridPane
		gridPaneAnalysis1 = new GridPane();
		gridPaneAnalysis1.setGridLinesVisible(true);
		// Add columns to gridpane
		gridPaneAnalysis1.getColumnConstraints().add(column1);
		gridPaneAnalysis1.getColumnConstraints().add(column2);
		gridPaneAnalysis1.getColumnConstraints().add(column3);

		gridPaneAnalysis1.setHgap(10.0);
		gridPaneAnalysis1.setVgap(10.0);

		btnStartAnalysis1.setDisable(false);
		btnStopAnalysis1.setDisable(true);
		btnPauseAnalysis1.setDisable(true);
		prepareTimeline();

		lblProjectNameRoot.textProperty().bind(projectName);
		projectName.setValue("Project: -");

	}

	private void addDataToSeries() {
		
		if (!structures.mode.equals("Dashboard")) {
			for (String id : structures.dataQ1.keySet()) {
				for (int byteNumber = 0; byteNumber < structures.dataQ1.get(id).size(); byteNumber++) {
					for (int i = 0; i < 20; i++) { // -- add 20 numbers to the plot+
						if (structures.dataQ1.get(id).get(byteNumber).isEmpty())
							break;
						structures.xySeries.get(id).get(byteNumber).getData()
								.add(new XYChart.Data<>(
										structures.xSeriesData.get(id).set(byteNumber, structures.xSeriesData.get(id).get(byteNumber) + 1),
										structures.dataQ1.get(id).get(byteNumber).remove()));
					}
					// remove points to keep us at no more than MAX_DATA_POINTS
					if (structures.xySeries.get(id).get(byteNumber).getData().size() > MAX_DATA_POINTS) {
						structures.xySeries.get(id).get(byteNumber).getData().remove(0,
						structures.xySeries.get(id).get(byteNumber).getData().size() - MAX_DATA_POINTS);
					}
					// update
					structures.xAxis.get(id).get(byteNumber).setLowerBound(structures.xSeriesData.get(id).get(byteNumber) - MAX_DATA_POINTS);
					structures.xAxis.get(id).get(byteNumber).setUpperBound(structures.xSeriesData.get(id).get(byteNumber) - 1);
				}

			}
		} else  { //DASHBOARD

			for (String id : structures.dataQ1Dash.keySet()) {
				for (int byteNumber = 0; byteNumber < structures.dataQ1Dash.get(id).size(); byteNumber++) {
					for (int i = 0; i < 20; i++) { // -- add 20 numbers to the plot+
						if (structures.dataQ1Dash.get(id).get(byteNumber).isEmpty())
							break;
						structures.xySeriesDash.get(id).get(byteNumber).getData()
								.add(new XYChart.Data<>(
										structures.xSeriesDataDash.get(id).set(byteNumber,
												structures.xSeriesDataDash.get(id).get(byteNumber) + 1),
										structures.dataQ1Dash.get(id).get(byteNumber).remove()));
					}
					// remove points to keep us at no more than MAX_DATA_POINTS
					if (structures.xySeriesDash.get(id).get(byteNumber).getData().size() > MAX_DATA_POINTS) {
						structures.xySeriesDash.get(id).get(byteNumber).getData().remove(0,
								structures.xySeriesDash.get(id).get(byteNumber).getData().size() - MAX_DATA_POINTS);
					}
					// update
					structures.xAxisDash.get(id).get(byteNumber)
							.setLowerBound(structures.xSeriesDataDash.get(id).get(byteNumber) - MAX_DATA_POINTS);
					structures.xAxisDash.get(id).get(byteNumber).setUpperBound(structures.xSeriesDataDash.get(id).get(byteNumber) - 1);
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
