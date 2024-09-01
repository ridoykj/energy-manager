package com.itbd.energymanager.view.ui.manager.module.user.employee;

import com.itbd.energymanager.dao.DesignationDao;
import com.itbd.energymanager.dao.EmployeeDao;
import com.itbd.energymanager.services.DesignationService;
import com.itbd.energymanager.services.EmployeeService;
import jakarta.annotation.PreDestroy;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.stage.FileChooser;
import lombok.extern.slf4j.Slf4j;
import net.rgielen.fxweaver.core.FxControllerAndView;
import net.rgielen.fxweaver.core.FxWeaver;
import net.rgielen.fxweaver.core.FxmlView;
import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.text.MessageFormat;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;


@Component
@FxmlView("/view/ui/manager/module/user/employee/employee_view.fxml")
@Slf4j
public class EmployeeView {

    private final FxWeaver fxWeaver;

    private final DesignationService designationService;
    private final EmployeeService employeeService;

    public EmployeeView(FxWeaver fxWeaver, DesignationService designationService, EmployeeService employeeService) {
        this.fxWeaver = fxWeaver;
        this.designationService = designationService;
        this.employeeService = employeeService;
    }

    @FXML
    private BorderPane root;
    private DesignationDialog designationDialog;
    private EmployeeDialog employeeDialog;

    @FXML
    private Label lbEmpAddress;

    @FXML
    private Label lbEmpArea;

    @FXML
    private Label lbEmpBirthDate;

    @FXML
    private Label lbEmpCode;

    @FXML
    private Label lbEmpDesignation;

    @FXML
    private Label lbEmpGender;

    @FXML
    private Label lbEmpName;

    @FXML
    private Label lbEmpNid;

    @FXML
    private Label lbEmpSalary;

    @FXML
    private TableColumn<DesignationDao, String> tbDesignation;

    @FXML
    private TableColumn<DesignationDao, String> tbDesignationDescription;

    @FXML
    private TableColumn<DesignationDao, Void> tbDesignationAction;

    @FXML
    private TableView<DesignationDao> tbvDesignation;

    @FXML
//    private TableColumn<EmployeeDao, DesignationDao> tbEmpDesignation;
    private TableColumn<EmployeeDao, String> tbEmpDesignation;

    @FXML
    private TableColumn<EmployeeDao, String> tbEmpGender;

    @FXML
    private TableColumn<EmployeeDao, String> tbEmpId;
    @FXML
    private TableColumn<EmployeeDao, LocalDate> tbBirthDate;
    @FXML
    private TableColumn<EmployeeDao, String> tbEmpName;

    @FXML
    private TableColumn<EmployeeDao, String> tbEmpNid;

    @FXML
    private TableColumn<EmployeeDao, Void> tbEmpAction;
    @FXML
    private TableView<EmployeeDao> tbvEmployee;

    @FXML
    private ImageView imgEmployee;

    private EmployeeDao employee;

    @FXML
    public void initialize() {
        FxControllerAndView<DesignationDialog, BorderPane> designationMVC = fxWeaver.load(DesignationDialog.class);
        this.designationDialog = designationMVC.getController();

        FxControllerAndView<EmployeeDialog, BorderPane> employeeMVC = fxWeaver.load(EmployeeDialog.class);
        this.employeeDialog = employeeMVC.getController();

        tableDesignationInit();
        tableEmployeeInit();

        List<DesignationDao> designations = designationService.list();
        loadDesignationData(designations);

        List<EmployeeDao> employees = employeeService.list();
        loadEmployeeData(employees);
    }

    @FXML
    void addDesignation(ActionEvent event) {
        designationDialog.load(new DesignationDao());
        designationDialog.show();
    }

    @FXML
    void refreshDesignation(ActionEvent event) {
        List<DesignationDao> customers = designationService.list();
        loadDesignationData(customers);
    }

    @FXML
    void addEmployee(ActionEvent event) {
        employeeDialog.load(new EmployeeDao());
        employeeDialog.show();
    }

    @FXML
    void refreshEmployee(ActionEvent event) {

    }

    @FXML
    void updateImage(ActionEvent event) throws IOException {
        FileChooser fileChooser = new FileChooser();
        File selectedFile = fileChooser.showOpenDialog(null);
        employeeService.store(this.employee, FileUtils.readFileToByteArray(selectedFile));
    }

    private void tableDesignationInit() {
        // TODO: Designation
        tbDesignation.setCellValueFactory(new PropertyValueFactory<>("id"));
        tbDesignationDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        tbDesignationAction.setCellFactory(col -> new TableCell<>() {
            private final Button actionButton = new Button("", new ImageView("assets/icon/res/delete.png"));

            {
                actionButton.getStyleClass().add("btn-transparent");
                actionButton.setOnAction(event -> {
                    DesignationDao person = getTableView().getItems().get(getIndex());
                    // Perform the delete action or any other action you need
                    getTableView().getItems().remove(person);
//                    System.out.println("Deleted: " + person.getName());
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(actionButton);
                }
            }
        });

        tbvDesignation.getSelectionModel().selectedItemProperty().addListener((o, oldVal, newVal) -> {
            if (newVal != null) {
                loadDesignationDetailPane(newVal);
            }
        });
        tbvDesignation.setRowFactory(tv -> {
            TableRow<DesignationDao> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (!row.isEmpty())) {
                    DesignationDao rowData = row.getItem();
                    designationDialog.show();
                    designationDialog.load(rowData);
                }
            });
            return row;
        });
    }

    private void tableEmployeeInit() {
        // TODO: Employee
        tbEmpId.setCellValueFactory(new PropertyValueFactory<>("id"));
        tbEmpName.setCellValueFactory(new PropertyValueFactory<>("name"));
        tbEmpGender.setCellValueFactory(new PropertyValueFactory<>("gender"));
        tbBirthDate.setCellValueFactory(new PropertyValueFactory<>("birthDate"));
        tbEmpDesignation.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDesignation().getId()));
        tbEmpNid.setCellValueFactory(new PropertyValueFactory<>("nid"));
        tbEmpAction.setCellFactory(col -> new TableCell<>() {
            private final Button actionButton = new Button("", new ImageView("assets/icon/res/delete.png"));

            {
                actionButton.getStyleClass().add("btn-transparent");
                actionButton.setOnAction(event -> {
                    EmployeeDao person = getTableView().getItems().get(getIndex());
                    // Perform the delete action or any other action you need
                    getTableView().getItems().remove(person);
                    System.out.println("Deleted: " + person.getName());
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(actionButton);
                }
            }
        });
        tbvEmployee.getSelectionModel().selectedItemProperty().addListener((o, oldVal, newVal) -> {
            if (newVal != null) {
                loadEmployeeDetailPane(newVal);
            }
        });
        tbvEmployee.setRowFactory(tv -> {
            TableRow<EmployeeDao> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (!row.isEmpty())) {
                    EmployeeDao rowData = row.getItem();
                    employeeDialog.show();
                    employeeDialog.load(rowData);
                }
            });
            return row;
        });

    }

    private void loadDesignationDetailPane(DesignationDao designation) {

    }

    private void loadEmployeeDetailPane(EmployeeDao employee) {
        this.employee = employee;
        lbEmpCode.setText(MessageFormat.format("ID: {0}", employee.getId()));
        lbEmpDesignation.setText(MessageFormat.format("Designation: {0}", employee.getDesignation() != null ? employee.getDesignation().getId() : ""));
        lbEmpName.setText(MessageFormat.format("Name: {0}", employee.getName()));
        lbEmpGender.setText(MessageFormat.format("Gender: {0}", employee.getGender()));
        lbEmpBirthDate.setText(MessageFormat.format("Birth Date: {0}", employee.getBirthDate()));
        lbEmpNid.setText(MessageFormat.format("NID: {0}", employee.getNid()));
        lbEmpAddress.setText(MessageFormat.format("Address: {0}", employee.getAddress()));
//        lbEmpArea.setText(MessageFormat.format("Area: {0}", employee.area()));
        lbEmpSalary.setText(MessageFormat.format("Salary: {0}", employee.getSalary()));
        if (employee.getImage() != null) {
            ByteArrayInputStream imageStream = new ByteArrayInputStream(employee.getImage());
            imgEmployee.setImage(new Image(imageStream));
        } else imgEmployee.setImage(null);
    }

    private void loadDesignationData(List<DesignationDao> designations) {
        try {
            final ObservableList<DesignationDao> data = FXCollections.observableArrayList(designations);
            tbvDesignation.getItems().clear();
            tbvDesignation.refresh();
            tbvDesignation.setItems(data);
        } catch (Exception ignored) {
        }
    }

    private void loadEmployeeData(List<EmployeeDao> employees) {
        try {
            final ObservableList<EmployeeDao> data = FXCollections.observableArrayList(employees);
            tbvEmployee.getItems().clear();
            tbvEmployee.refresh();
            tbvEmployee.setItems(data);
        } catch (Exception ignored) {
        }
    }


    private Alert customAlert(String title, String headerText, String contentText, ButtonType... buttonType) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(title);
        alert.setHeaderText(headerText);
        alert.setResizable(false);
        if (Objects.nonNull(contentText)) alert.setContentText(contentText);
        if (Objects.nonNull(buttonType)) alert.getButtonTypes().addAll(buttonType);
        return alert;
    }

    @PreDestroy
    private void cleanup() {
        log.info("I am finish distributor!");
    }
}
