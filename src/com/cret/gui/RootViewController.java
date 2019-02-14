package com.cret.gui;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentLinkedQueue;
import com.cret.db.DbUtils;
import com.cret.can.CanConnection;
import com.cret.can.CanTable;
import com.cret.can.CanUtils;
import com.cret.db.Database;
import com.cret.projects.Project;
import com.cret.staticdata.structures;
import de.fischl.usbtin.USBtinException;
import javafx.animation.Animation;
import javafx.animation.AnimationTimer;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
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
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
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
import javafx.scene.control.Tooltip;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;
import javafx.util.Duration;

/**
 * 
 * Main GUI controller.
 * 
 * @author Adrian Marcos
 * @version 1.0
 *
 */
public class RootViewController {

	private static final String INTERNAL_ERROR = "INTERNAL ERROR";
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

	private static List<Button> buttonLiveCellDashboard1;

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
	public Button btnSaveProjectDashboard1;

	@FXML
	private Button btnSaveProjectDashboard2;

	/**
	 * 
	 */
	@FXML
	private Button btnNewProjectRoot;

	/**
	 * FXML element.
	 * 
	 */
	@FXML
	public Button btnStartDashboard1;

	@FXML
	private Button btnStartDashboard2;

	/**
	 * FXML element.
	 * 
	 */
	@FXML
	public Button btnStopDashboard1;

	@FXML
	private Button btnStopDashboard2;

	/**
	 * FXML element.
	 * 
	 */
	@FXML
	Button btnCloseProjectDashboard1;

	@FXML
	private Button btnCloseProjectDashboard2;

	/**
	 * 
	 */
	@FXML
	public Button btnStartAnalysis1;

	@FXML
	private Button btnStartAnalysis2;

	/**
	 * 
	 */
	@FXML
	public Button btnStopAnalysis1;

	@FXML
	private Button btnStopAnalysis2;

	/**
	 * 
	 */
	@FXML
	public Button btnOpenProjectDashboard1;

	/**
	 * 
	 */
	@FXML
	public Button btnOpenProjectDashboard2;

	/**
	 * 
	 */
	@FXML
	private ScrollPane scrollAnalysisInterface1;

	@FXML
	private ScrollPane scrollAnalysisInterface2;

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
	public Button btnClearAnalysis1;

	@FXML
	private Button btnClearAnalysis2;

	/**
	 * 
	 */
	@FXML
	private ScrollPane scrollDashboardInterface1;

	@FXML
	private ScrollPane scrollDashboardInterface2;

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

	@FXML
	private TableView<CanTable> tableMonitorInterface2;

	/**
	 * 
	 */
	@FXML
	public Button btnStartTraceRAW1;

	@FXML
	private Button btnStartTraceRAW2;

	/**
	 * 
	 */
	@FXML
	public Button btnStopTraceRAW1;

	@FXML
	private Button btnStopTraceRAW2;

	/**
	 * 
	 */
	@FXML
	public Button btnClearTraceRAW1;

	@FXML
	private Button btnClearTraceRAW2;

	/**
	 * 
	 */
	@FXML
	public Button btnStartMonitorRAW1;

	@FXML
	private Button btnStartMonitorRAW2;

	/**
	 * 
	 */
	@FXML
	public Button btnStopMonitorRAW1;

	@FXML
	private Button btnStopMonitorRAW2;

	/**
	 * 
	 */
	@FXML
	public Button btnClearMonitorRAW1;

	@FXML
	private Button btnClearMonitorRAW2;

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

	@FXML
	private static GridPane gridPaneAnalysis2;

	/**
	 * 
	 */
	@FXML
	private static GridPane gridPaneDashboard1;

	@FXML
	private static GridPane gridPaneDashboard2;

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

	/**
	 * Closes project dashboard 1.
	 * @param event
	 */
	@FXML
	private void closeProjectDashboard1(ActionEvent event) {

		// Ask for confirmation.
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Close project");
		alert.setHeaderText("Do you want to save changes?");

		Optional<ButtonType> result = alert.showAndWait();
		if (result.isPresent()) {
			if (result.get() == ButtonType.OK) {

				projectObj.saveProject();
				projectObj = null;
			} else {
				projectObj = null;
			}
		}
		structures.resetDashboardStructures1();
		structures.resetAnalisysStructures1();
		structures.resetRAWStructures();

		gridPaneDashboard1.getChildren().clear();
		gridPaneDashboard1 = new GridPane();

		projectName.set("-");

	}

	/**
	 * Closes project dashboard 2.
	 * @param event
	 */
	@FXML
	private void closeProjectDashboard2(ActionEvent event) {
		// Ask for confirmation.
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Close project");
		alert.setHeaderText("Do you want to save changes?");

		Optional<ButtonType> result = alert.showAndWait();
		if (result.isPresent()) {
			if (result.get() == ButtonType.OK) {

				projectObj.saveProject();
				projectObj = null;
			} else {
				projectObj = null;
			}
		}

		gridPaneDashboard2.getChildren().clear();
		gridPaneDashboard2 = new GridPane();

		projectName.set("-");
	}

	/**
	 * Shows about window.
	 * @param event
	 */
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
			GuiUtils.generateAlert(AlertType.ERROR, INTERNAL_ERROR, "Error loading window.");
		}
	}

	/**
	 * Starts CAN interface.
	 * 
	 * @param event
	 * @throws IOException
	 * @throws USBtinException
	 */
	@FXML
	private void startDashboardInterface1(ActionEvent event) throws IOException, USBtinException {

		if (CanUtils.getInterface1State()) {
			structures.resetDashboardStructures1();
			structures.resetAnalisysStructures1();
			structures.resetRAWStructures();

			ColumnConstraints column1 = new ColumnConstraints();
			ColumnConstraints column2 = new ColumnConstraints();
			ColumnConstraints column3 = new ColumnConstraints();

			// Set fixed width for the columns
			column1.setPercentWidth(33.33);
			column2.setPercentWidth(33.33);
			column3.setPercentWidth(33.33);

			gridPaneDashboard1.getChildren().clear();
			gridPaneDashboard1 = new GridPane();
			gridPaneDashboard1.setGridLinesVisible(true);
			gridPaneDashboard1.getColumnConstraints().add(column1);
			gridPaneDashboard1.getColumnConstraints().add(column2);
			gridPaneDashboard1.getColumnConstraints().add(column3);

			gridPaneDashboard1.setHgap(10.0);
			gridPaneDashboard1.setVgap(10.0);

			scrollDashboardInterface1.setContent(gridPaneDashboard1);

			// port set ok

			structures.modeInterface1 = "Dashboard";

			String port = CanUtils.getConfigurationPortInterface1();
			String speed = CanUtils.getConfigurationSpeedInterface1();
			String mode = CanUtils.getConfigurationModeInterface1();

			canObj = new CanConnection(port, speed, mode, this);

			canObj.connect();

			btnConfigureRoot.setDisable(true);

			// Analysis tab
			btnStartAnalysis1.setDisable(true);
			btnStopAnalysis1.setDisable(true);
			btnClearAnalysis1.setDisable(true);

			// Dashboard tab
			btnStartDashboard1.setDisable(true);
			btnStopDashboard1.setDisable(false);
			btnOpenProjectDashboard1.setDisable(true);
			btnSaveProjectDashboard1.setDisable(true);

			// Trace tab
			btnStartTraceRAW1.setDisable(true);
			btnStopTraceRAW1.setDisable(true);
			btnClearTraceRAW1.setDisable(true);

			// Monitor tab
			btnStartMonitorRAW1.setDisable(true);
			btnStopMonitorRAW1.setDisable(true);
			btnClearMonitorRAW1.setDisable(true);

			try {
				Thread.sleep(200);
			} catch (InterruptedException e) {
				GuiUtils.generateAlert(AlertType.ERROR, INTERNAL_ERROR, "Thread error.");
				Thread.currentThread().interrupt();
			}

		} else {
			GuiUtils.generateAlert(AlertType.INFORMATION, "CAN disabled",
					"No correct CAN interface has been configured");
		}

	}

	@FXML
	private void startDashboardInterface2(ActionEvent event) {
		if (CanUtils.getInterface2State()) {
			GuiUtils.generateAlert(AlertType.INFORMATION, "Start", "Interface already started.");
		} else {
			GuiUtils.generateAlert(AlertType.INFORMATION, "CAN disabled",
					"No correct CAN interface has been configured");
		}
	}

	@FXML
	private void clearAnalysisInterface1(ActionEvent event) {

		structures.dataLineChart1 = new HashMap<>();
		structures.dataQ1 = new HashMap<>();
		structures.xySeries1 = new HashMap<>();
		structures.xAxis1 = new HashMap<>();
		structures.yAxis1 = new HashMap<>();
		structures.xSeriesData1 = new HashMap<>();

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

		CanUtils.clearIdentifiedInterface1();

		btnStartAnalysis1.setDisable(false);
		btnStopAnalysis1.setDisable(true);
		// DELETE DATA FROM DATA STRUCTRUES
	}

	@FXML
	private void clearAnalysisInterface2(ActionEvent event) {
	}

	@FXML
	private void importProject(ActionEvent event) throws IOException {

		// Load FXML file.
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/cret/gui/ImportView.fxml"));
		Parent importDialog = (Parent) fxmlLoader.load();
		Stage stage = new Stage();
		// Set icon.
		stage.getIcons().add(
				new Image(Thread.currentThread().getContextClassLoader().getResourceAsStream("resources/icon.png")));
		// Set title.
		stage.setTitle("CRET - Import project");
		stage.setScene(new Scene(importDialog));
		stage.setResizable(false);
		// Show window.
		stage.show();

	}

	@FXML
	private void exportProject(ActionEvent event) throws IOException {
		// Load FXML file.
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/cret/gui/ExportView.fxml"));
		Parent exportDialog = (Parent) fxmlLoader.load();
		Stage stage = new Stage();
		// Set icon.
		stage.getIcons().add(
				new Image(Thread.currentThread().getContextClassLoader().getResourceAsStream("resources/icon.png")));
		// Set title.
		stage.setTitle("CRET - Export project");
		stage.setScene(new Scene(exportDialog));
		stage.setResizable(false);
		// Show window.
		stage.show();
	}

	/**
	 * @param event
	 */
	@FXML
	private void saveProjectDashboard1(ActionEvent event) {

		projectObj.setCompleteMap(structures.valuesInDashboard1);

		projectObj.saveProject();
		savedProject = true;
	}

	@FXML
	private void saveProjectDashboard2(ActionEvent event) {

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
			stage.getIcons().add(new Image(
					Thread.currentThread().getContextClassLoader().getResourceAsStream("resources/icon.png")));
			stage.setResizable(false);
			btnSaveProjectDashboard1.setDisable(true);
			stage.setTitle("CRET - Project list");
			stage.setScene(new Scene(configDialog));
			stage.show();
		} catch (Exception e) {
			GuiUtils.generateAlert(AlertType.ERROR, INTERNAL_ERROR, "Error loading window.");
		}
	}


	/**
	 * @param event
	 * @throws Exception
	 */
	@FXML
	private void openConfiguration(ActionEvent event) throws Exception {

		try {

			// Load FXML file.
			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/cret/gui/ConfigurationView.fxml"));
			Parent configDialog = (Parent) fxmlLoader.load();
			Stage stage = new Stage();
			// Set icon.
			stage.getIcons().add(new Image(
					Thread.currentThread().getContextClassLoader().getResourceAsStream("resources/icon.png")));
			// Set title.
			stage.setTitle("CRET - Configuration");
			stage.setScene(new Scene(configDialog));
			stage.setResizable(false);
			// Show window.
			stage.show();

		} catch (Exception e) {

			GuiUtils.generateAlert(AlertType.ERROR, "INTERNAL ERROR", "Could not open configuration window.");

		}
	}

	static void returnNewName(String name) {
		projectName.setValue(name);
	}

	/**
	 * 
	 * @param event
	 * @throws Exception
	 */
	@FXML
	private void openNewProject(ActionEvent event) throws Exception {

		try {

			// Load FXML file.
			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/cret/gui/NewProject.fxml"));
			Parent configDialog = (Parent) fxmlLoader.load();
			Stage stage = new Stage();
			stage.setResizable(false);
			stage.initStyle(StageStyle.UNDECORATED);
			// Set title.
			stage.setTitle("CRET - New project");
			stage.setScene(new Scene(configDialog));
			// Show widnow.
			stage.show();

			// Returns the name of the project when stage is closed.
			stage.setOnHiding(new EventHandler<WindowEvent>() {
				public void handle(WindowEvent we) {
					// CHECK IF PROJECT IS CLOSED AND SAVED
					if (!projectName.getValue().equals("Project: -")) {
						projectObj = new Project(projectName.toString());
						projectObj.setProjectName(projectName.getValue());
						structures.valuesInDashboard1 = new HashMap<>();
						btnSaveProjectDashboard1.setDisable(false);
						savedProject = false;

					}

				}
			});

		} catch (Exception e) {
			GuiUtils.generateAlert(AlertType.ERROR, "INTERNAL ERROR", "Could not open new project window.");
		}
	}

	private void restoreStateFXMLElements() {

		btnConfigureRoot.setDisable(false);

		// Analysis tab
		btnStartAnalysis1.setDisable(false);
		btnStopAnalysis1.setDisable(true);
		btnClearAnalysis1.setDisable(false);

		// Dashboard tab
		btnStartDashboard1.setDisable(false);
		btnStopDashboard1.setDisable(true);
		btnOpenProjectDashboard1.setDisable(false);
		btnSaveProjectDashboard1.setDisable(true);
		btnCloseProjectDashboard1.setDisable(true);

		// Trace tab.
		btnStartTraceRAW1.setDisable(false);
		btnStopTraceRAW1.setDisable(true);
		btnClearTraceRAW1.setDisable(false);

		// Monitor tab
		btnStartMonitorRAW1.setDisable(false);
		btnStopMonitorRAW1.setDisable(false);
		btnClearMonitorRAW1.setDisable(false);
	}

	/**
	 * @param event
	 * @throws USBtinException
	 */
	@FXML
	private void stopInterface1(ActionEvent event) throws USBtinException {

		if (canObj != null) {
			canObj.disconnect();
			canObj = null;

			/*
			 * Restore FXML elements state.
			 */
			restoreStateFXMLElements();

		} else {
			GuiUtils.generateAlert(AlertType.INFORMATION, "Not running", "No CAN interface is running");
		}
	}

	@FXML
	private void stopInterface2(ActionEvent event) throws USBtinException {

		if (canObj != null) {
			canObj.disconnect();
			canObj = null;

			/*
			 * Restore FXML elements state.
			 */
			restoreStateFXMLElements();

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

		if (CanUtils.getInterface1State()) {

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

			// GridPane
			gridPaneAnalysis1 = new GridPane();
			gridPaneAnalysis1.setGridLinesVisible(true);
			// Add columns to gridpane
			gridPaneAnalysis1.getColumnConstraints().add(column1);
			gridPaneAnalysis1.getColumnConstraints().add(column2);
			gridPaneAnalysis1.getColumnConstraints().add(column3);

			scrollAnalysisInterface1.setContent(gridPaneAnalysis1);

			CanUtils.clearIdentifiedInterface1();

			btnStartAnalysis1.setDisable(false);
			btnStopAnalysis1.setDisable(true);

			// Clear structures
			structures.resetAnalisysStructures1();
			structures.resetDashboardStructures1();
			structures.resetRAWStructures();

			// Clear GUI

			structures.modeInterface1 = "Analysis";

			String port = CanUtils.getConfigurationPortInterface1();
			String speed = CanUtils.getConfigurationSpeedInterface1();
			String mode = CanUtils.getConfigurationModeInterface1();

			canObj = new CanConnection(port, speed, mode, this);

			canObj.connect();

			/**
			 * Set FXML elements
			 */

			btnConfigureRoot.setDisable(true);

			// Analysis tab
			btnStartAnalysis1.setDisable(true);
			btnStopAnalysis1.setDisable(false);
			btnClearAnalysis1.setDisable(true);

			// Dashboard tab
			btnStartDashboard1.setDisable(true);
			btnStopDashboard1.setDisable(true);
			btnOpenProjectDashboard1.setDisable(true);
			btnSaveProjectDashboard1.setDisable(true);
			btnCloseProjectDashboard1.setDisable(true);

			// Trace tab
			btnStartTraceRAW1.setDisable(true);
			btnStopTraceRAW1.setDisable(true);
			btnClearTraceRAW1.setDisable(true);

			// Monitor tab
			btnStartMonitorRAW1.setDisable(true);
			btnStopMonitorRAW1.setDisable(true);
			btnClearMonitorRAW1.setDisable(true);

			try {
				Thread.sleep(200);
			} catch (InterruptedException e) {
				GuiUtils.generateAlert(AlertType.ERROR, INTERNAL_ERROR, "Thread error.");
				Thread.currentThread().interrupt();
			}

		} else {
			GuiUtils.generateAlert(AlertType.INFORMATION, "CAN disabled",
					"No correct CAN interface has been configured");
		}

	}

	@FXML
	private void startAnalysisInterface2(ActionEvent event) throws IOException, USBtinException {
		if (CanUtils.getInterface2State()) {
			GuiUtils.generateAlert(AlertType.INFORMATION, "Start", "Interface already started.");

		} else {
			GuiUtils.generateAlert(AlertType.INFORMATION, "CAN disabled",
					"No correct CAN interface has been configured");
		}

	}

	public static void addIDDashboard1(String id, int length) {

		structures.dataLineChartDash1.put(id, new ArrayList<>());
		structures.xySeriesDash1.put(id, new ArrayList<>());
		structures.dataQ1Dash1.put(id, new ArrayList<>());
		structures.xAxisDash1.put(id, new ArrayList<>());
		structures.yAxisDash1.put(id, new ArrayList<>());
		structures.xSeriesDataDash1.put(id, new ArrayList<>());

		for (int i = 0; i <= length; i++) {
			structures.dataQ1Dash1.get(id).add(new ConcurrentLinkedQueue<>());
			structures.xAxisDash1.get(id).add(new NumberAxis(0, MAX_DATA_POINTS, MAX_DATA_POINTS / 10));
			structures.yAxisDash1.get(id).add(new NumberAxis());
			structures.xSeriesDataDash1.get(id).add(0);
			LineChart<Number, Number> lineChart;
			if (structures.modeInterface1.equals("Dashboard")) {
				lineChart = new LineChart<Number, Number>(structures.xAxisDash1.get(id).get(i),
						structures.yAxisDash1.get(id).get(i)) {
					@Override
					protected void dataItemAdded(Series<Number, Number> series, int itemIndex,
							Data<Number, Number> item) {
					}
				};
			} else {
				lineChart = new LineChart<Number, Number>(structures.xAxis1.get(id).get(i),
						structures.yAxis1.get(id).get(i)) {

					@Override
					protected void dataItemAdded(Series<Number, Number> series, int itemIndex,
							Data<Number, Number> item) {
					}

				};
			}

			structures.dataLineChartDash1.get(id).add(lineChart);
			structures.xySeriesDash1.get(id).add(new XYChart.Series<>());
		}
	}

	public void addID(String id, int length) {

		// Initialize lists
		structures.dataLineChart1.put(id, new ArrayList<>());
		structures.xySeries1.put(id, new ArrayList<>());
		structures.dataQ1.put(id, new ArrayList<>());
		structures.xAxis1.put(id, new ArrayList<>());
		structures.yAxis1.put(id, new ArrayList<>());
		structures.xSeriesData1.put(id, new ArrayList<>());

		for (int i = 0; i <= length; i++) {
			structures.dataQ1.get(id).add(new ConcurrentLinkedQueue<>());
			structures.xAxis1.get(id).add(new NumberAxis(0, MAX_DATA_POINTS, MAX_DATA_POINTS / 10));
			structures.yAxis1.get(id).add(new NumberAxis());
			structures.xSeriesData1.get(id).add(0);
			LineChart<Number, Number> lineChart;
			lineChart = new LineChart<Number, Number>(structures.xAxis1.get(id).get(i),
					structures.yAxis1.get(id).get(i)) {
				@Override
				protected void dataItemAdded(Series<Number, Number> series, int itemIndex, Data<Number, Number> item) {
				}
			};
			structures.dataLineChart1.get(id).add(lineChart);
			structures.xySeries1.get(id).add(new XYChart.Series<>());
		}

	}

	public static void addIDDashboard2(String id, int length) {

	}

	public void addNewCellToDashboard1(String id, int byteNumber, String dataName) {

		progressRoot.setVisible(true);

		cellAnchorDashboard1.add(new AnchorPane());
		textSetNameDashboard1.add(new TextField());
		buttonRemoveCellDashboard1.add(new Button());
		buttonLiveCellDashboard1.add(new Button());
		dataEditable.add(new EditableLabel());

		if (colDashboard <= 2) {
			gridPaneDashboard1.add(cellAnchorDashboard1.get(cellAnchorDashboard1.size() - 1), colDashboard,
					rowDashboard);
			colDashboard++;
		} else {
			colDashboard = 0;
			rowDashboard++;
			gridPaneDashboard1.add(cellAnchorDashboard1.get(cellAnchorDashboard1.size() - 1), colDashboard,
					rowDashboard);
			colDashboard++;
		}

		AnchorPane temp = cellAnchorDashboard1.get(cellAnchorDashboard1.size() - 1);
		TextField tempText = textSetNameDashboard1.get(textSetNameDashboard1.size() - 1);
		Button tempRemove = buttonRemoveCellDashboard1.get(buttonRemoveCellDashboard1.size() - 1);
		EditableLabel tempEditable = dataEditable.get(dataEditable.size() - 1);
		Button tempData = buttonLiveCellDashboard1.get(buttonLiveCellDashboard1.size() - 1);

		tempText.setPrefWidth(Control.USE_COMPUTED_SIZE);
		tempText.setPrefHeight(Control.USE_COMPUTED_SIZE);

		tempRemove.setText("Remove");
		tempRemove.setGraphic(GuiUtils.buildImage("resources/delete.png"));
		tempRemove.setPrefHeight(Control.USE_COMPUTED_SIZE);
		tempRemove.setPrefHeight(Control.USE_COMPUTED_SIZE);

		tempEditable.setText(dataName);
		tempEditable.setPrefHeight(Control.USE_COMPUTED_SIZE);
		tempEditable.setPrefHeight(Control.USE_COMPUTED_SIZE);
		tempEditable.setFont(Font.font(16));

		tempData.setText("Live view");
		tempData.setGraphic(GuiUtils.buildImage("resources/live.png"));
		tempData.setPrefHeight(Control.USE_COMPUTED_SIZE);
		tempData.setPrefHeight(Control.USE_COMPUTED_SIZE);

		tempRemove.setOnAction(arg0 -> {
			try {
				temp.getChildren().clear();
				cellAnchorDashboard1.remove(cellAnchorDashboard1.indexOf(temp));
				structures.valuesInDashboard1.put(tempEditable.getText(), new ArrayList<>());
			} catch (Exception e) {
				GuiUtils.generateAlert(AlertType.ERROR, INTERNAL_ERROR, "Error generating cell.");
			}
		});

		tempData.setOnAction(arg0 -> {
			String tempid = id;

			try {
				FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/cret/gui/FrameViewer.fxml"));
				Parent frameDialog = (Parent) fxmlLoader.load();

				FrameViewerController controller = fxmlLoader.getController();
				controller.byteNumber = byteNumber;
				controller.id = tempid;
				controller.valueName = tempEditable.getText();

				Stage stage = new Stage();
				stage.getIcons().add(new Image("resources/icon.png"));
				Platform.runLater(() -> {
					try {
						Thread.sleep(200);
					} catch (InterruptedException e) {
						GuiUtils.generateAlert(AlertType.ERROR, INTERNAL_ERROR, "Thread error.");
						Thread.currentThread().interrupt();
					}
				});

				stage.setResizable(false);
				stage.setTitle("CRET - CAN Frame Viewer");

				stage.setScene(new Scene(frameDialog));

				stage.show();

			} catch (Exception e) {
				GuiUtils.generateAlert(AlertType.ERROR, INTERNAL_ERROR, "Error loading window.");
			}

		});
		String strByte = Integer.toString(byteNumber);

		structures.dataLineChartDash1.get(id).get(byteNumber).setTitle("ID: " + id + " - Byte: " + strByte);
		structures.dataLineChartDash1.get(id).get(byteNumber).setLegendVisible(false);
		structures.dataLineChartDash1.get(id).get(byteNumber).getStyleClass().add("thick-chart");

		structures.dataLineChartDash1.get(id).get(byteNumber).getData()
				.add(structures.xySeriesDash1.get(id).get(byteNumber));

		structures.xAxisDash1.get(id).get(byteNumber).setForceZeroInRange(false);
		structures.xAxisDash1.get(id).get(byteNumber).setAutoRanging(false);
		structures.xAxisDash1.get(id).get(byteNumber).setTickLabelsVisible(false);
		structures.xAxisDash1.get(id).get(byteNumber).setTickMarkVisible(false);
		structures.xAxisDash1.get(id).get(byteNumber).setMinorTickVisible(false);

		structures.dataLineChartDash1.get(id).get(byteNumber).setCreateSymbols(false);

		AnchorPane.setBottomAnchor(structures.dataLineChartDash1.get(id).get(byteNumber), 50.0);
		AnchorPane.setTopAnchor(structures.dataLineChartDash1.get(id).get(byteNumber), 0.0);
		AnchorPane.setLeftAnchor(structures.dataLineChartDash1.get(id).get(byteNumber), 0.0);
		AnchorPane.setRightAnchor(structures.dataLineChartDash1.get(id).get(byteNumber), 0.0);

		AnchorPane.setLeftAnchor(tempEditable, 14.0);
		AnchorPane.setBottomAnchor(tempEditable, 14.0);

		AnchorPane.setBottomAnchor(tempRemove, 14.0);
		AnchorPane.setRightAnchor(tempRemove, 14.0);

		AnchorPane.setBottomAnchor(tempData, 14.0);
		AnchorPane.setRightAnchor(tempData, 100.0);

		structures.dataLineChartDash1.get(id).get(byteNumber).setAnimated(false);

		temp.getChildren().add(structures.dataLineChartDash1.get(id).get(byteNumber));
		temp.getChildren().add(tempEditable);
		temp.getChildren().add(tempRemove);
		temp.getChildren().add(tempData);

		scrollDashboardInterface1.setFitToWidth(true);

		scrollDashboardInterface1.setContent(gridPaneDashboard1);

	}


	public void addCellFromTo1(String id, int byteNumber, String dataName) {

		cellAnchorDashboard1.add(new AnchorPane());
		textSetNameDashboard1.add(new TextField());
		buttonRemoveCellDashboard1.add(new Button());
		buttonLiveCellDashboard1.add(new Button());
		dataEditable.add(new EditableLabel());

		if (colDashboard <= 2) {
			gridPaneDashboard1.add(cellAnchorDashboard1.get(cellAnchorDashboard1.size() - 1), colDashboard,
					rowDashboard);
			colDashboard++;
		} else {
			colDashboard = 0;
			rowDashboard++;
			gridPaneDashboard1.add(cellAnchorDashboard1.get(cellAnchorDashboard1.size() - 1), colDashboard,
					rowDashboard);
			colDashboard++;
		}

		AnchorPane temp = cellAnchorDashboard1.get(cellAnchorDashboard1.size() - 1);
		TextField tempText = textSetNameDashboard1.get(textSetNameDashboard1.size() - 1);
		Button tempRemove = buttonRemoveCellDashboard1.get(buttonRemoveCellDashboard1.size() - 1);
		EditableLabel tempEditable = dataEditable.get(dataEditable.size() - 1);
		Button tempData = buttonLiveCellDashboard1.get(buttonLiveCellDashboard1.size() - 1);

		tempText.setPrefWidth(Control.USE_COMPUTED_SIZE);
		tempText.setPrefHeight(Control.USE_COMPUTED_SIZE);

		tempRemove.setText("Remove");
		tempRemove.setGraphic(GuiUtils.buildImage("resources/delete.png"));
		tempRemove.setPrefHeight(Control.USE_COMPUTED_SIZE);
		tempRemove.setPrefWidth(Control.USE_COMPUTED_SIZE);

		tempData.setText("Live view");
		tempData.setGraphic(GuiUtils.buildImage("resources/live.png"));
		tempData.setPrefHeight(Control.USE_COMPUTED_SIZE);
		tempData.setPrefWidth(Control.USE_COMPUTED_SIZE);

		tempEditable.setText(dataName);
		tempEditable.setPrefHeight(Control.USE_COMPUTED_SIZE);
		tempEditable.setPrefWidth(Control.USE_COMPUTED_SIZE);

		tempEditable.setFont(Font.font(16));

		tempRemove.setOnAction(arg0 -> {
			try {
				temp.getChildren().clear();
				cellAnchorDashboard1.remove(cellAnchorDashboard1.indexOf(temp));
				structures.valuesInDashboard1.put(tempEditable.getText(), new ArrayList<>());
			} catch (Exception e) {
				GuiUtils.generateAlert(AlertType.ERROR, INTERNAL_ERROR, "Error generating cell.");
			}
		});

		tempData.setOnAction(arg0 -> {
			String tempid = id;

			try {
				FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/cret/gui/FrameViewer.fxml"));
				Parent frameDialog = (Parent) fxmlLoader.load();

				FrameViewerController controller = fxmlLoader.getController();
				controller.byteNumber = byteNumber;
				controller.id = tempid;
				controller.valueName = tempEditable.getText();

				Stage stage = new Stage();
				stage.getIcons().add(new Image("resources/icon.png"));
				Platform.runLater(() -> {
					try {
						Thread.sleep(200);
					} catch (InterruptedException e) {
						GuiUtils.generateAlert(AlertType.ERROR, INTERNAL_ERROR, "Thread error.");
						Thread.currentThread().interrupt();
					}
				});

				stage.setResizable(false);
				stage.setTitle("CRET - CAN Frame Viewer");

				stage.setScene(new Scene(frameDialog));

				stage.show();

			} catch (Exception e) {
				GuiUtils.generateAlert(AlertType.ERROR, INTERNAL_ERROR, "Error loading window.");
			}

		});
		String strByte = Integer.toString(byteNumber);

		structures.dataLineChartDash1.get(id).get(byteNumber).setTitle("ID: " + id + " - Byte: " + strByte);
		structures.dataLineChartDash1.get(id).get(byteNumber).setLegendVisible(false);
		structures.dataLineChartDash1.get(id).get(byteNumber).getStyleClass().add("thick-chart");

		structures.dataLineChartDash1.get(id).get(byteNumber).getData()
				.add(structures.xySeries1.get(id).get(byteNumber));

		structures.xAxisDash1.get(id).get(byteNumber).setForceZeroInRange(false);
		structures.xAxisDash1.get(id).get(byteNumber).setAutoRanging(false);
		structures.xAxisDash1.get(id).get(byteNumber).setTickLabelsVisible(false);
		structures.xAxisDash1.get(id).get(byteNumber).setTickMarkVisible(false);
		structures.xAxisDash1.get(id).get(byteNumber).setMinorTickVisible(false);

		structures.dataLineChartDash1.get(id).get(byteNumber).setCreateSymbols(false);

		AnchorPane.setBottomAnchor(structures.dataLineChartDash1.get(id).get(byteNumber), 50.0);
		AnchorPane.setTopAnchor(structures.dataLineChartDash1.get(id).get(byteNumber), 0.0);
		AnchorPane.setLeftAnchor(structures.dataLineChartDash1.get(id).get(byteNumber), 0.0);
		AnchorPane.setRightAnchor(structures.dataLineChartDash1.get(id).get(byteNumber), 0.0);

		AnchorPane.setLeftAnchor(tempEditable, 14.0);
		AnchorPane.setBottomAnchor(tempEditable, 14.0);

		AnchorPane.setBottomAnchor(tempRemove, 14.0);
		AnchorPane.setRightAnchor(tempRemove, 14.0);

		AnchorPane.setBottomAnchor(tempData, 14.0);
		AnchorPane.setRightAnchor(tempData, 100.0);

		structures.dataLineChartDash1.get(id).get(byteNumber).setAnimated(false);

		temp.getChildren().add(structures.dataLineChartDash1.get(id).get(byteNumber));
		temp.getChildren().add(tempEditable);
		temp.getChildren().add(tempRemove);
		temp.getChildren().add(tempData);

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
		tempRemove.setGraphic(GuiUtils.buildImage("resources/delete.png"));
		tempRemove.setPrefHeight(Control.USE_COMPUTED_SIZE);
		tempRemove.setPrefHeight(Control.USE_COMPUTED_SIZE);

		tempData.setText("Live view");
		tempData.setGraphic(GuiUtils.buildImage("resources/live.png"));
		tempData.setPrefHeight(Control.USE_COMPUTED_SIZE);
		tempData.setPrefHeight(Control.USE_COMPUTED_SIZE);

		tempRemove.setOnAction(arg0 -> {
			try {
				temp.getChildren().clear();
				cellAnchorAnalysis1.remove(cellAnchorAnalysis1.indexOf(temp));

			} catch (Exception e) {
				GuiUtils.generateAlert(AlertType.ERROR, INTERNAL_ERROR, "Error generating cell.");
			}
		});

		// Action of the dashboard checkbox
		tempCheck.setOnAction(arg0 -> {
			if (tempText.getText().equals("")) {
				tempCheck.setSelected(false);
				GuiUtils.generateAlert(AlertType.INFORMATION, "Empty value", "You must tag this value!");
			} else {
				if (projectObj == null) {
					GuiUtils.generateAlert(AlertType.INFORMATION, "Empty project",
							"A project must be opened to save this value.");
					tempCheck.setSelected(false);
				} else {

					if (!structures.valuesInDashboard1.containsKey(tempText.getText())) {

						structures.valuesInDashboard1.put(tempText.getText(), new ArrayList<String>());
						structures.valuesInDashboard1.get(tempText.getText()).add(0, id);
						structures.valuesInDashboard1.get(tempText.getText()).add(1, String.valueOf(byteNumber));

						addIDDashboard1(id, byteNumber);

						addCellFromTo1(id, byteNumber, tempText.getText());

						temp.getChildren().clear();
					} else {
						GuiUtils.generateAlert(AlertType.WARNING, "Invalid name", "The tagged name is duplicated");
					}
				}
			}
		});

		tempData.setOnAction(arg0 -> {
			String tempid = id;

			try {
				FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/cret/gui/FrameViewer.fxml"));
				Parent frameDialog = (Parent) fxmlLoader.load();

				FrameViewerController controller = fxmlLoader.getController();
				controller.byteNumber = byteNumber;
				controller.id = tempid;

				Stage stage = new Stage();
				stage.getIcons().add(new Image("resources/icon.png"));
				Platform.runLater(() -> {
					try {
						Thread.sleep(200);
					} catch (InterruptedException e) {
						GuiUtils.generateAlert(AlertType.ERROR, INTERNAL_ERROR, "Thread error.");
						Thread.currentThread().interrupt();
					}
				});

				stage.setResizable(false);
				stage.setTitle("CRET - CAN Frame Viewer");

				stage.setScene(new Scene(frameDialog));

				stage.show();

			} catch (Exception e) {
				GuiUtils.generateAlert(AlertType.ERROR, INTERNAL_ERROR, "Error loading window.");
			}

		});

		String strByte = Integer.toString(byteNumber);
		structures.dataLineChart1.get(id).get(byteNumber).setTitle("ID: " + id + " - Byte: " + strByte);
		structures.dataLineChart1.get(id).get(byteNumber).setLegendVisible(false);
		structures.dataLineChart1.get(id).get(byteNumber).getStyleClass().add("thick-chart");
		structures.dataLineChart1.get(id).get(byteNumber).setAnimated(false);
		structures.dataLineChart1.get(id).get(byteNumber).getData().add(structures.xySeries1.get(id).get(byteNumber));

		structures.xAxis1.get(id).get(byteNumber).setForceZeroInRange(false);
		structures.xAxis1.get(id).get(byteNumber).setAutoRanging(false);
		structures.xAxis1.get(id).get(byteNumber).setTickLabelsVisible(false);
		structures.xAxis1.get(id).get(byteNumber).setTickMarkVisible(false);
		structures.xAxis1.get(id).get(byteNumber).setMinorTickVisible(false);

		structures.dataLineChart1.get(id).get(byteNumber).setCreateSymbols(false);

		AnchorPane.setBottomAnchor(structures.dataLineChart1.get(id).get(byteNumber), 50.0);
		AnchorPane.setTopAnchor(structures.dataLineChart1.get(id).get(byteNumber), 0.0);
		AnchorPane.setLeftAnchor(structures.dataLineChart1.get(id).get(byteNumber), 0.0);
		AnchorPane.setRightAnchor(structures.dataLineChart1.get(id).get(byteNumber), 0.0);

		AnchorPane.setLeftAnchor(tempText, 14.0);
		AnchorPane.setBottomAnchor(tempText, 14.0);

		AnchorPane.setBottomAnchor(tempCheck, 18.0);
		AnchorPane.setLeftAnchor(tempCheck, 175.0);

		AnchorPane.setBottomAnchor(tempRemove, 14.0);
		AnchorPane.setRightAnchor(tempRemove, 14.0);

		AnchorPane.setBottomAnchor(tempData, 14.0);
		AnchorPane.setRightAnchor(tempData, 100.0);

		structures.dataLineChart1.get(id).get(byteNumber).setAnimated(false);

		temp.getChildren().add(structures.dataLineChart1.get(id).get(byteNumber));
		temp.getChildren().add(tempText);
		temp.getChildren().add(tempCheck);
		temp.getChildren().add(tempRemove);
		temp.getChildren().add(tempData);

		scrollAnalysisInterface1.setFitToWidth(true);

		scrollAnalysisInterface1.setContent(gridPaneAnalysis1);

	}

	private void update2() {
		tableMonitorInterface1.refresh();
	}

	/**
	 * @param event
	 */
	@FXML
	private void startMonitorRAWInterface1(ActionEvent event) {

		if (CanUtils.getInterface1State()) {

			structures.modeInterface1 = "RAW";
			structures.monitor1 = true;
			String port = CanUtils.getConfigurationPortInterface1();
			String speed = CanUtils.getConfigurationSpeedInterface1();
			String mode = CanUtils.getConfigurationModeInterface1();

			structures.resetAnalisysStructures1();
			structures.resetDashboardStructures1();
			structures.resetRAWStructures();

			try {
				canObj = new CanConnection(port, speed, mode, this);
			} catch (USBtinException e) {
				GuiUtils.generateAlert(AlertType.ERROR, "CAN ERROR", "Can't connect to CAN network.");
			}

			TableColumn<CanTable, String> timeColumn = new TableColumn<CanTable, String>("Time");
			timeColumn.setCellValueFactory(new PropertyValueFactory<CanTable, String>("time"));

			timeColumn.setSortable(false);

			TableColumn<CanTable, String> idColumn = new TableColumn<CanTable, String>("ID");
			idColumn.setCellValueFactory(new PropertyValueFactory<CanTable, String>("id"));

			idColumn.setSortable(false);

			TableColumn<CanTable, String> lenColumn = new TableColumn<CanTable, String>("Lenght");
			lenColumn.setCellValueFactory(new PropertyValueFactory<CanTable, String>("length"));

			lenColumn.setSortable(false);
			TableColumn<CanTable, String> dataColumn = new TableColumn<CanTable, String>("Data");
			dataColumn.setCellValueFactory(new PropertyValueFactory<CanTable, String>("data"));

			dataColumn.setSortable(false);

			TableColumn<CanTable, String> asciiColumn = new TableColumn<CanTable, String>("Ascii");
			asciiColumn.setCellValueFactory(new PropertyValueFactory<CanTable, String>("ascii"));

			List<CanTable> list = new ArrayList<>();

			tableMonitorInterface1.getItems().clear();
			tableMonitorInterface1.getColumns().clear();

			tableMonitorInterface1.getColumns().addAll(timeColumn, idColumn, lenColumn, dataColumn, asciiColumn);

			structures.dataTableMonitor1 = FXCollections.observableList(list);
			// structures.dataTableMonitor = FXCollections.observableArrayList();
			tableMonitorInterface1.setItems(structures.dataTableMonitor1);

			canObj.connect();
			Timeline timeline2 = new Timeline(new KeyFrame(Duration.millis(50), ae -> update2()));

			timeline2.setCycleCount(Animation.INDEFINITE);
			timeline2.play();

			/**
			 * Set FXML elements.
			 */

			btnConfigureRoot.setDisable(true);

			// Analysis tab
			btnStartAnalysis1.setDisable(true);
			btnStopAnalysis1.setDisable(true);
			btnClearAnalysis1.setDisable(true);

			// Dashboard tab
			btnStartDashboard1.setDisable(true);
			btnStopDashboard1.setDisable(true);
			btnOpenProjectDashboard1.setDisable(true);
			btnSaveProjectDashboard1.setDisable(true);
			btnCloseProjectDashboard1.setDisable(true);

			// Trace tab
			btnStartTraceRAW1.setDisable(true);
			btnStopTraceRAW1.setDisable(true);
			btnClearTraceRAW1.setDisable(true);

			// Monitor tab
			btnStartMonitorRAW1.setDisable(true);
			btnStopMonitorRAW1.setDisable(false);
			btnClearMonitorRAW1.setDisable(true);

		} else {
			GuiUtils.generateAlert(AlertType.INFORMATION, "CAN disabled",
					"No correct CAN interface has been configured");
		}

	}

	@FXML
	private void startMonitorRAWInterface2(ActionEvent event) {
		if (CanUtils.getInterface2State()) {
			GuiUtils.generateAlert(AlertType.WARNING, "Started", "Interface already started");
		} else {
			GuiUtils.generateAlert(AlertType.INFORMATION, "CAN disabled",
					"No correct CAN interface has been configured");
		}
	}

	/**
	 * @param event
	 */
	@FXML
	private void clearMonitorRAWInterface1(ActionEvent event) {

		tableMonitorInterface1.getItems().clear();
		tableMonitorInterface1.getColumns().clear();
		structures.resetRAWStructures();

	}

	@FXML
	private void clearMonitorRAWInterface2(ActionEvent event) {

		tableMonitorInterface1.getItems().clear();
		tableMonitorInterface1.getColumns().clear();
		structures.resetRAWStructures();
	}

	/**
	 * @param event
	 */
	@FXML
	private void clearTraceRAWInterface1(ActionEvent event) {

		tableTraceInterface1.getItems().clear();
		tableTraceInterface1.getColumns().clear();
		structures.resetRAWStructures();

	}

	@FXML
	private void clearTraceRAWInterface2(ActionEvent event) {
		tableTraceInterface1.getItems().clear();
		tableTraceInterface1.getColumns().clear();
		structures.resetRAWStructures();
	}

	/**
	 * 
	 */
	@FXML
	private void startTraceRAWInterface1(ActionEvent event) {

		if (CanUtils.getInterface1State()) {

			structures.modeInterface1 = "RAW";
			structures.monitor1 = false;

			String port = CanUtils.getConfigurationPortInterface1();
			String speed = CanUtils.getConfigurationSpeedInterface1();
			String mode = CanUtils.getConfigurationModeInterface1();

			structures.resetAnalisysStructures1();
			structures.resetDashboardStructures1();
			structures.resetRAWStructures();

			try {
				canObj = new CanConnection(port, speed, mode, this);
			} catch (USBtinException e) {
				GuiUtils.generateAlert(AlertType.ERROR, "CAN ERROR", "Can't connect to CAN network.");
			}

			TableColumn<CanTable, String> timeColumn = new TableColumn<CanTable, String>("Time");
			timeColumn.setCellValueFactory(new PropertyValueFactory<CanTable, String>("time"));

			timeColumn.setSortable(false);

			TableColumn<CanTable, String> idColumn = new TableColumn<CanTable, String>("ID");
			idColumn.setCellValueFactory(new PropertyValueFactory<CanTable, String>("id"));

			idColumn.setSortable(false);

			TableColumn<CanTable, String> lenColumn = new TableColumn<CanTable, String>("Lenght");
			lenColumn.setCellValueFactory(new PropertyValueFactory<CanTable, String>("length"));

			lenColumn.setSortable(false);

			TableColumn<CanTable, String> dataColumn = new TableColumn<CanTable, String>("Data");
			dataColumn.setCellValueFactory(new PropertyValueFactory<CanTable, String>("data"));

			dataColumn.setSortable(false);

			TableColumn<CanTable, String> asciiColumn = new TableColumn<CanTable, String>("Ascii");
			asciiColumn.setCellValueFactory(new PropertyValueFactory<CanTable, String>("ascii"));

			asciiColumn.setSortable(false);

			List<CanTable> list = new ArrayList<>();
			tableTraceInterface1.getItems().clear();
			tableTraceInterface1.getColumns().clear();

			tableTraceInterface1.getColumns().addAll(timeColumn, idColumn, lenColumn, dataColumn, asciiColumn);

			structures.dataTable1 = FXCollections.observableList(list);
			tableTraceInterface1.setItems(structures.dataTable1);

			canObj.connect();

			/**
			 * Set FXML elements.
			 */

			btnConfigureRoot.setDisable(true);

			// Analysis tab
			btnStartAnalysis1.setDisable(true);
			btnStopAnalysis1.setDisable(true);
			btnClearAnalysis1.setDisable(true);

			// Dashboard tab
			btnStartDashboard1.setDisable(true);
			btnStopDashboard1.setDisable(true);
			btnOpenProjectDashboard1.setDisable(true);
			btnSaveProjectDashboard1.setDisable(true);
			btnCloseProjectDashboard1.setDisable(true);

			// Trace tab
			btnStartTraceRAW1.setDisable(true);
			btnStopTraceRAW1.setDisable(false);
			btnClearTraceRAW1.setDisable(true);

			// Monitor tab
			btnStartMonitorRAW1.setDisable(true);
			btnStopMonitorRAW1.setDisable(true);
			btnClearMonitorRAW1.setDisable(true);

		} else {
			GuiUtils.generateAlert(AlertType.INFORMATION, "CAN disabled",
					"No correct CAN interface has been configured");
		}
	}

	@FXML
	private void startTraceRAWInterface2(ActionEvent event) {
		if (CanUtils.getInterface2State()) {
			GuiUtils.generateAlert(AlertType.INFORMATION, "Used", "Interface in use");
		} else {
			GuiUtils.generateAlert(AlertType.INFORMATION, "CAN disabled",
					"No correct CAN interface has been configured");
		}
	}

	/**
	 * FXML funciton.
	 * 
	 * Executed when GUI loads.
	 * 
	 * @throws IOException
	 * @throws USBtinException
	 */
	@FXML
	private void initialize() throws IOException, USBtinException {

		/*
		 * Check if database exist. If not, create a new one.
		 */
		if (!DbUtils.checkDatabaseExist("cret.db")) {
			File f = new File("cret.db");

			if (f.exists()) {

				if (!f.delete()) {
					GuiUtils.generateAlert(AlertType.ERROR, "INTERNAL ERROR", "Error produced creating database.");
					System.exit(0);
				}

			} else {

				databaseObj = new Database("cret.db");
				databaseObj.createTables("cret.db");
			}

		}
		/*
		 * END of database check
		 */

		lblProjectNameRoot.textProperty().bind(projectName);
		projectName.setValue("Project: -");

		// Set FXML elements
		progressRoot.setVisible(false);
		btnSaveProjectDashboard1.setDisable(true);
		btnSaveProjectDashboard2.setDisable(true);
		btnStartDashboard1.setDisable(false);
		btnStartDashboard2.setDisable(false);
		btnStopDashboard1.setDisable(true);
		btnStopDashboard2.setDisable(true);
		btnCloseProjectDashboard1.setDisable(true);
		btnCloseProjectDashboard2.setDisable(true);

		btnStartTraceRAW1.setDisable(false);
		btnStartTraceRAW2.setDisable(false);
		btnStopTraceRAW1.setDisable(true);
		btnStopTraceRAW2.setDisable(true);
		btnClearTraceRAW1.setDisable(true);
		btnClearTraceRAW2.setDisable(true);

		btnStartMonitorRAW1.setDisable(false);
		btnStartMonitorRAW2.setDisable(false);
		btnStopMonitorRAW1.setDisable(true);
		btnStopMonitorRAW2.setDisable(true);
		btnClearMonitorRAW1.setDisable(true);
		btnClearMonitorRAW2.setDisable(true);

		btnStartAnalysis1.setDisable(false);
		btnStartAnalysis2.setDisable(false);
		btnStopAnalysis1.setDisable(true);
		btnStopAnalysis2.setDisable(true);
		btnClearAnalysis1.setDisable(true);
		btnClearAnalysis2.setDisable(true);

		// End of setting FXML elements.

		// Set icons to FXML elements.
		tabAnalysisInterface1.setGraphic(GuiUtils.buildImage("resources/analyze.png"));
		tabAnalysisInterface1
				.setTooltip(new Tooltip("Analysis tab for interface 1. Before use, configure interface 1."));
		tabAnalysisInterface2.setGraphic(GuiUtils.buildImage("resources/analyze.png"));
		tabAnalysisInterface2
				.setTooltip(new Tooltip("Analysis tab for interface 2. Before use, configure interface 2."));
		tabDashboardInterface1.setGraphic(GuiUtils.buildImage("resources/dash.png"));
		tabDashboardInterface1.setTooltip(
				new Tooltip("Dashboard for interface 1. Before use, configure interface 1 and open a project."));
		tabDashboardInterface2.setGraphic(GuiUtils.buildImage("resources/dash.png"));
		tabDashboardInterface2.setTooltip(
				new Tooltip("Dashboard for interface 2. Before use, configure interface 2 and open a project."));
		btnConfigureRoot.setGraphic(GuiUtils.buildImage("resources/config.png"));
		btnConfigureRoot.setTooltip(new Tooltip("Configures the interfaces and filter options."));
		btnNewProjectRoot.setGraphic(GuiUtils.buildImage("resources/project.png"));
		btnNewProjectRoot.setTooltip(new Tooltip("Creates a new project."));
		btnStartAnalysis1.setGraphic(GuiUtils.buildImage("resources/start.png"));
		btnStartAnalysis1.setTooltip(
				new Tooltip("Starts reading data from CAN and plots it. Interface 1 must be configured to use."));
		btnStartAnalysis2.setGraphic(GuiUtils.buildImage("resources/start.png"));
		btnStartAnalysis2.setTooltip(
				new Tooltip("Starts reading data from CAN and plots it. Interface 1 must be configured to use."));
		btnStopAnalysis1.setGraphic(GuiUtils.buildImage("resources/stop.png"));
		btnStopAnalysis1.setTooltip(new Tooltip("Stops reading from CAN network."));
		btnStopAnalysis2.setGraphic(GuiUtils.buildImage("resources/stop.png"));
		btnStopAnalysis2.setTooltip(new Tooltip("Stops reading from CAN network."));
		btnClearAnalysis1.setGraphic(GuiUtils.buildImage("resources/delete.png"));
		btnClearAnalysis1.setTooltip(new Tooltip("Clears the window. Deletes all graphs."));
		btnClearAnalysis2.setGraphic(GuiUtils.buildImage("resources/delete.png"));
		btnClearAnalysis2.setTooltip(new Tooltip("Clears the window. Deletes all graphs."));
		btnStartDashboard1.setGraphic(GuiUtils.buildImage("resources/start.png"));
		btnStartDashboard1.setTooltip(new Tooltip(
				"Starts reading data from CAN and plots it. A project must be opened and interface 1 configured to use it."));
		btnStartDashboard2.setGraphic(GuiUtils.buildImage("resources/start.png"));
		btnStartDashboard2.setTooltip(new Tooltip(
				"Starts reading data from CAN and plots it. A project must be opened and interface 1 configured to use it."));
		btnStopDashboard1.setGraphic(GuiUtils.buildImage("resources/stop.png"));
		btnStopDashboard1.setTooltip(new Tooltip("Stops reading from CAN network."));
		btnStopDashboard2.setGraphic(GuiUtils.buildImage("resources/stop.png"));
		btnStopDashboard2.setTooltip(new Tooltip("Stops reading from CAN network."));
		btnStartTraceRAW1.setGraphic(GuiUtils.buildImage("resources/start.png"));
		btnStartTraceRAW1
				.setTooltip(new Tooltip("Starts reading data from CAN. Interface 1 must be configured to use."));
		btnStartTraceRAW2.setGraphic(GuiUtils.buildImage("resources/start.png"));
		btnStartTraceRAW2
				.setTooltip(new Tooltip("Starts reading data from CAN. Interface 1 must be configured to use."));
		btnStopTraceRAW1.setGraphic(GuiUtils.buildImage("resources/stop.png"));
		btnStopTraceRAW1.setTooltip(new Tooltip("Stops reading from CAN network."));
		btnStopTraceRAW2.setGraphic(GuiUtils.buildImage("resources/stop.png"));
		btnStopTraceRAW2.setTooltip(new Tooltip("Stops reading from CAN network."));
		tabRAWInterface1.setGraphic(GuiUtils.buildImage("resources/binary.png"));
		tabRAWInterface1
				.setTooltip(new Tooltip("Reads data from CAN network. Interface 1 must be configured before use."));
		tabRAWInterface2.setGraphic(GuiUtils.buildImage("resources/binary.png"));
		tabRAWInterface2
				.setTooltip(new Tooltip("Reads data from CAN network. Interface 2 must be configured before use."));
		btnOpenProjectDashboard1.setGraphic(GuiUtils.buildImage("resources/open.png"));
		btnOpenProjectDashboard1.setTooltip(new Tooltip("Opens project. Interface 1 must be configured before use."));
		btnOpenProjectDashboard2.setGraphic(GuiUtils.buildImage("resources/open.png"));
		btnOpenProjectDashboard2.setTooltip(new Tooltip("Opens project. Interface 1 must be configured before use."));
		btnSaveProjectDashboard1.setGraphic(GuiUtils.buildImage("resources/save.png"));
		btnSaveProjectDashboard1.setTooltip(new Tooltip("Saves project."));
		btnSaveProjectDashboard2.setGraphic(GuiUtils.buildImage("resources/save.png"));
		btnSaveProjectDashboard2.setTooltip(new Tooltip("Saves project."));
		btnClearTraceRAW1.setGraphic(GuiUtils.buildImage("resources/delete.png"));
		btnClearTraceRAW1.setTooltip(new Tooltip("Clears the table."));
		btnClearTraceRAW2.setGraphic(GuiUtils.buildImage("resources/delete.png"));
		btnClearTraceRAW2.setTooltip(new Tooltip("Clears the table."));
		btnClearMonitorRAW1.setGraphic(GuiUtils.buildImage("resources/delete.png"));
		btnClearMonitorRAW1.setTooltip(new Tooltip("Clears the table."));
		btnClearMonitorRAW2.setGraphic(GuiUtils.buildImage("resources/delete.png"));
		btnClearMonitorRAW2.setTooltip(new Tooltip("Clears the table."));
		btnStartMonitorRAW1.setGraphic(GuiUtils.buildImage("resources/start.png"));
		btnStartMonitorRAW1
				.setTooltip(new Tooltip("Starts reading data from CAN. Interface 1 must be configured to use."));
		btnStartMonitorRAW2.setGraphic(GuiUtils.buildImage("resources/start.png"));
		btnStartMonitorRAW2
				.setTooltip(new Tooltip("Starts reading data from CAN. Interface 1 must be configured to use."));
		btnStopMonitorRAW1.setGraphic(GuiUtils.buildImage("resources/stop.png"));
		btnStopMonitorRAW1.setTooltip(new Tooltip("Stops reading from CAN network."));
		btnStopMonitorRAW2.setGraphic(GuiUtils.buildImage("resources/stop.png"));
		btnStopMonitorRAW2.setTooltip(new Tooltip("Stops reading from CAN network."));
		btnCloseProjectDashboard1.setGraphic(GuiUtils.buildImage("resources/close.png"));
		btnCloseProjectDashboard1.setTooltip(new Tooltip("Closes opened project."));
		btnCloseProjectDashboard2.setGraphic(GuiUtils.buildImage("resources/close.png"));
		btnCloseProjectDashboard2.setTooltip(new Tooltip("Closes opened project."));

		// End of setup icons.

		// INITALIZE FXML ELEMENTS

		cellAnchorAnalysis1 = new ArrayList<AnchorPane>();
		cellAnchorDashboard1 = new ArrayList<AnchorPane>();
		textSetNameAnalysis1 = new ArrayList<TextField>();
		textSetNameDashboard1 = new ArrayList<TextField>();
		checkSendToDashboard1 = new ArrayList<CheckBox>();
		buttonRemoveCellAnalysis1 = new ArrayList<Button>();
		dataEditable = new ArrayList<EditableLabel>();
		buttonRemoveCellDashboard1 = new ArrayList<Button>();
		buttonLiveCellDashboard1 = new ArrayList<Button>();
		buttonShowRAWAnalysis1 = new ArrayList<Button>();

		structures.dataLineChart1 = new HashMap<>();
		structures.dataQ1 = new HashMap<>();
		structures.xySeries1 = new HashMap<>();
		structures.xAxis1 = new HashMap<>();
		structures.yAxis1 = new HashMap<>();
		structures.xSeriesData1 = new HashMap<>();

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

		// GridPane
		gridPaneAnalysis1 = new GridPane();
		gridPaneAnalysis1.setGridLinesVisible(true);
		// Add columns to gridpane
		gridPaneAnalysis1.getColumnConstraints().add(column1);
		gridPaneAnalysis1.getColumnConstraints().add(column2);
		gridPaneAnalysis1.getColumnConstraints().add(column3);

		gridPaneAnalysis1.setHgap(10.0);
		gridPaneAnalysis1.setVgap(10.0);

		// Start graph monitoring
		prepareTimeline();
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			GuiUtils.generateAlert(AlertType.ERROR, INTERNAL_ERROR, "Thread error.");
			Thread.currentThread().interrupt();
		}

	}

	/**
	 * This function gets data from Queues and loads it into Observablelist objects,
	 * where the linecharts will take the data to plot.
	 * 
	 * With this method, we avoid concurrency problems, for example, writing and
	 * reading at the same time in Observablelist objects.
	 */
	private void addDataToSeries() {

		if (!structures.modeInterface1.equals("Dashboard")) {
			for (String id : structures.dataQ1.keySet()) {
				for (int byteNumber = 0; byteNumber < structures.dataQ1.get(id).size(); byteNumber++) {
					for (int i = 0; i < 20; i++) { // -- add 20 numbers to the plot+
						if (structures.dataQ1.get(id).get(byteNumber).isEmpty())
							break;
						structures.xySeries1.get(id).get(byteNumber).getData()
								.add(new XYChart.Data<>(
										structures.xSeriesData1.get(id).set(byteNumber,
												structures.xSeriesData1.get(id).get(byteNumber) + 1),
										structures.dataQ1.get(id).get(byteNumber).remove()));
					}
					// remove points to keep us at no more than MAX_DATA_POINTS
					try {
						if (structures.xySeries1.get(id).get(byteNumber).getData().size() > MAX_DATA_POINTS) {
							structures.xySeries1.get(id).get(byteNumber).getData().remove(0,
									structures.xySeries1.get(id).get(byteNumber).getData().size() - MAX_DATA_POINTS);
						}

						// update
						structures.xAxis1.get(id).get(byteNumber)
								.setLowerBound(structures.xSeriesData1.get(id).get(byteNumber) - MAX_DATA_POINTS);
						structures.xAxis1.get(id).get(byteNumber)
								.setUpperBound(structures.xSeriesData1.get(id).get(byteNumber) - 1);
					} catch (java.lang.IndexOutOfBoundsException e) {
					}

				}

			}
		} else { // DASHBOARD

			for (String id : structures.dataQ1Dash1.keySet()) {
				for (int byteNumber = 0; byteNumber < structures.dataQ1Dash1.get(id).size(); byteNumber++) {
					for (int i = 0; i < 20; i++) { // -- add 20 numbers to the plot+
						if (structures.dataQ1Dash1.get(id).get(byteNumber).isEmpty())
							break;
						structures.xySeriesDash1.get(id).get(byteNumber).getData()
								.add(new XYChart.Data<>(
										structures.xSeriesDataDash1.get(id).set(byteNumber,
												structures.xSeriesDataDash1.get(id).get(byteNumber) + 1),
										structures.dataQ1Dash1.get(id).get(byteNumber).remove()));
					}

					try {
						// remove points to keep us at no more than MAX_DATA_POINTS
						if (structures.xySeriesDash1.get(id).get(byteNumber).getData().size() > MAX_DATA_POINTS) {
							structures.xySeriesDash1.get(id).get(byteNumber).getData().remove(0,
									structures.xySeriesDash1.get(id).get(byteNumber).getData().size()
											- MAX_DATA_POINTS);
						}
						// update
						structures.xAxisDash1.get(id).get(byteNumber)
								.setLowerBound(structures.xSeriesDataDash1.get(id).get(byteNumber) - MAX_DATA_POINTS);
						structures.xAxisDash1.get(id).get(byteNumber)
								.setUpperBound(structures.xSeriesDataDash1.get(id).get(byteNumber) - 1);

					} catch (java.lang.IndexOutOfBoundsException e) {
					}
				}
			}
		}

	}

	/**
	 * Executed in every GUI update. Calls the funciton to check if there is new
	 * data in the queues.
	 */
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
