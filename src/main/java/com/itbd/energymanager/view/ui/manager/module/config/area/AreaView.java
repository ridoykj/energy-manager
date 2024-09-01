package com.itbd.energymanager.view.ui.manager.module.config.area;

import com.itbd.energymanager.dao.AreaDao;
import com.itbd.energymanager.dao.CustomerDao;
import com.itbd.energymanager.dao.EmployeeDao;
import com.itbd.energymanager.services.AreaService;
import jakarta.annotation.PreDestroy;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import lombok.extern.slf4j.Slf4j;
import net.rgielen.fxweaver.core.FxControllerAndView;
import net.rgielen.fxweaver.core.FxWeaver;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.stereotype.Component;

import java.text.MessageFormat;
import java.util.List;
import java.util.Objects;


@Component
@FxmlView("/view/ui/manager/module/config/area/area_view.fxml")
@Slf4j
public class AreaView {

    private final FxWeaver fxWeaver;
    private AreaDialog areaDialog;

    private final AreaService areaService;

    @FXML
    private BorderPane root;

    @FXML
    private Label lbAddress;

    @FXML
    private Label lbCity;

    @FXML
    private Label lbCode;

    @FXML
    private Label lbRoad;

    @FXML
    private Label lbUnion;

    @FXML
    private TableColumn<AreaDao, String> tbCode;
    @FXML
    private TableColumn<AreaDao, String> tbCity;
    @FXML
    private TableColumn<AreaDao, String> tbUnion;
    @FXML
    private TableColumn<AreaDao, String> tbRoad;
    @FXML
    private TableColumn<AreaDao, String> tbAddress;
    @FXML
    private TableColumn<AreaDao, Void> tbAction;
    @FXML
    private TableView<AreaDao> tbvArea;

    @FXML
    private TableColumn<CustomerDao, String> tbCustId;
    @FXML
    private TableColumn<CustomerDao, String> tbCustName;
    @FXML
    private TableView<CustomerDao> tbvCustomer;
    @FXML
    private TableColumn<EmployeeDao, String> tbEmpId;
    @FXML
    private TableColumn<EmployeeDao, String> tbEmpName;
    @FXML
    private TableView<EmployeeDao> tbvEmployee;


    public AreaView(FxWeaver fxWeaver, AreaService customerService) {
        this.fxWeaver = fxWeaver;
        this.areaService = customerService;
    }

    @FXML
    public void initialize() {
        FxControllerAndView<AreaDialog, BorderPane> customerMVC = fxWeaver.load(AreaDialog.class);
        this.areaDialog = customerMVC.getController();

        tableAreaInit();
        tableEmployeeInit();
        tableCustomerInit();

        List<AreaDao> areas = areaService.list();
        loadAreaData(areas);
    }

    private void tableAreaInit() {
        tbCode.setCellValueFactory(new PropertyValueFactory<>("id"));
        tbCity.setCellValueFactory(new PropertyValueFactory<>("city"));
        tbUnion.setCellValueFactory(new PropertyValueFactory<>("union"));
        tbRoad.setCellValueFactory(new PropertyValueFactory<>("road"));
        tbAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        tbAction.setCellFactory(col -> new TableCell<>() {
            private final Button actionButton = new Button("", new ImageView("assets/icon/res/delete.png"));

            {
                actionButton.getStyleClass().add("btn-transparent");
                actionButton.setOnAction(event -> {
                    AreaDao person = getTableView().getItems().get(getIndex());
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
        tbvArea.getSelectionModel().selectedItemProperty().addListener((o, oldVal, newVal) -> {
            if (newVal != null) {
                loadDetailPane(newVal);
                loadEmployeeData(areaService.getEmployees(newVal.getId()));
                loadCustomerData(areaService.getCustomers(newVal.getId()));
            }
        });
        tbvArea.setRowFactory(tv -> {
            TableRow<AreaDao> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (!row.isEmpty())) {
                    AreaDao rowData = row.getItem();
                    areaDialog.show();
                    areaDialog.load(rowData);
                }
            });
            return row;
        });
    }

    private void tableCustomerInit() {
        tbCustId.setCellValueFactory(new PropertyValueFactory<>("id"));
        tbCustName.setCellValueFactory(new PropertyValueFactory<>("name"));
    }

    private void tableEmployeeInit() {
        tbEmpId.setCellValueFactory(new PropertyValueFactory<>("id"));
        tbEmpName.setCellValueFactory(new PropertyValueFactory<>("name"));
    }

    private void loadDetailPane(AreaDao customer) {
        lbCode.setText(MessageFormat.format("Code: {0}", customer.getId()));
        lbCity.setText(MessageFormat.format("City: {0}", customer.getCity()));
        lbUnion.setText(MessageFormat.format("Union: {0}", customer.getUnion()));
        lbRoad.setText(MessageFormat.format("Road: {0}", customer.getRoad()));
        lbAddress.setText(MessageFormat.format("Address: {0}", customer.getAddress()));
    }

    private void loadAreaData(List<AreaDao> areas) {
        try {
            final ObservableList<AreaDao> data = FXCollections.observableArrayList(areas);
            tbvArea.getItems().clear();
            tbvArea.refresh();
            tbvArea.setItems(data);
        } catch (Exception ignored) {
        }
    }

    private void loadCustomerData(List<CustomerDao> customers) {
        try {
            final ObservableList<CustomerDao> data = FXCollections.observableArrayList(customers);
            tbvCustomer.getItems().clear();
            tbvCustomer.refresh();
            tbvCustomer.setItems(data);
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

    @FXML
    void refreshArea(ActionEvent event) {
        List<AreaDao> customers = areaService.list();
        loadAreaData(customers);
    }

    @FXML
    void addArea(ActionEvent event) {
        areaDialog.load(new AreaDao());
        areaDialog.show();
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
