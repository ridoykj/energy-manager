package com.itbd.energymanager.view.ui.manager.module.user.customer;

import com.itbd.energymanager.dao.CustomerDao;
import com.itbd.energymanager.dao.MeterDao;
import com.itbd.energymanager.services.CustomerService;
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
import java.util.List;
import java.util.Objects;


@Component
@FxmlView("/view/ui/manager/module/user/customer/customer_view.fxml")
@Slf4j
public class CustomerView {
    //    @Autowired
    private final FxWeaver fxWeaver;
    private final CustomerService customerService;
    @FXML
    private BorderPane root;
    private CustomerDialog customerDialog;
    private CustomerMeterDialog customerMeterDialog;
    @FXML
    private TableColumn<CustomerDao, String> tbId;
    @FXML
    private TableColumn<CustomerDao, String> tbMobile;
    @FXML
    private TableColumn<CustomerDao, String> tbName;
    @FXML
    private TableColumn<CustomerDao, String> tbNid;
    @FXML
    private TableColumn<CustomerDao, Boolean> tbState;
    @FXML
    private TableColumn<CustomerDao, Void> tbAction;

    @FXML
    private TableView<CustomerDao> tbvCustomers;
    @FXML
    private TableColumn<MeterDao, String> tbMeterCategory;
    @FXML
    private TableColumn<MeterDao, String> tbMeterId;
    @FXML
    private TableColumn<MeterDao, String> tbMeterRegister;
    @FXML
    private TableColumn<MeterDao, Boolean> tbMeterState;
    @FXML
    private TableColumn<MeterDao, Void> tbMeterAction;
    @FXML
    private TableView<MeterDao> tbvMeter;

    @FXML
    private ImageView imgCustomer;

    @FXML
    private Label lbCode, lbName, lbGender, lbMobile, lbNid, lbState, lbAddress;

    private CustomerDao customer;

    public CustomerView(FxWeaver fxWeaver, CustomerService customerService) {
        this.fxWeaver = fxWeaver;
        this.customerService = customerService;
    }

    @FXML
    public void initialize() {
        FxControllerAndView<CustomerDialog, BorderPane> customerMVC = fxWeaver.load(CustomerDialog.class);
        this.customerDialog = customerMVC.getController();

        FxControllerAndView<CustomerMeterDialog, BorderPane> meterMVC = fxWeaver.load(CustomerMeterDialog.class);
        this.customerMeterDialog = meterMVC.getController();

        tableCustomerInit();
        tableMeterInit();
        List<CustomerDao> customers = customerService.list();
        loadCustomerData(customers);
    }

    private void tableCustomerInit() {
        tbId.setCellValueFactory(new PropertyValueFactory<>("id"));
        tbName.setCellValueFactory(new PropertyValueFactory<>("name"));
        tbMobile.setCellValueFactory(new PropertyValueFactory<>("mobile"));
        tbNid.setCellValueFactory(new PropertyValueFactory<>("nid"));
        tbState.setCellValueFactory(new PropertyValueFactory<>("isState"));
        tbState.setCellFactory((tableColumn) -> new TableCell<>() {
            @Override
            protected void updateItem(Boolean item, boolean empty) {
                super.updateItem(item, empty);
                this.setText(null);
                this.setGraphic(null);
                if (!empty) {
                    if (Boolean.TRUE.equals(item)) this.styleProperty().setValue("-fx-background-color: #a8fa8e ;");
                    else this.styleProperty().setValue("-fx-background-color: #f56b56 ;");
                    this.setText(Boolean.TRUE.equals(item) ? "Active" : "N/A");
                }
            }
        });
        tbAction.setCellFactory(col -> new TableCell<>() {
            private final Button actionButton = new Button("", new ImageView("assets/icon/res/delete.png"));

            {
                actionButton.getStyleClass().add("btn-transparent");
                actionButton.setOnAction(event -> {
                    CustomerDao person = getTableView().getItems().get(getIndex());
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

        tbvCustomers.getSelectionModel().selectedItemProperty().addListener((o, oldVal, newVal) -> {
            if (newVal != null) {
                loadDetailPane(newVal);
                loadMeterData(customerService.getMeters(newVal.getId()));
            }
        });

        tbvCustomers.setRowFactory(tv -> {
            TableRow<CustomerDao> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (!row.isEmpty())) {
                    CustomerDao rowData = row.getItem();
                    customerDialog.show();
                    customerDialog.load(rowData);
                }
            });
            return row;
        });
    }


    private void tableMeterInit() {
        tbMeterId.setCellValueFactory(new PropertyValueFactory<>("id"));
        tbMeterCategory.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCategory().getId()));
        tbMeterRegister.setCellValueFactory(new PropertyValueFactory<>("registration"));
        tbMeterState.setCellValueFactory(new PropertyValueFactory<>("isState"));
        tbMeterState.setCellFactory((tableColumn) -> new TableCell<>() {
            @Override
            protected void updateItem(Boolean item, boolean empty) {
                super.updateItem(item, empty);
                this.setText(null);
                this.setGraphic(null);
                if (!empty) {
                    if (Boolean.TRUE.equals(item)) this.styleProperty().setValue("-fx-background-color: #a8fa8e ;");
                    else this.styleProperty().setValue("-fx-background-color: #f56b56 ;");
                    this.setText(Boolean.TRUE.equals(item) ? "Active" : "N/A");
                }
            }
        });
        tbMeterAction.setCellFactory(col -> new TableCell<>() {
            private final Button actionButton = new Button("", new ImageView("assets/icon/res/delete.png"));

            {
                actionButton.getStyleClass().add("btn-transparent");
                actionButton.setOnAction(event -> {
                    MeterDao person = getTableView().getItems().get(getIndex());
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

        tbvMeter.getSelectionModel().selectedItemProperty().addListener((o, oldVal, newVal) -> {
            if (newVal != null) {
//                loadDetailPane(newVal);
            }
        });

        tbvMeter.setRowFactory(tv -> {
            TableRow<MeterDao> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (!row.isEmpty())) {
                    MeterDao rowData = row.getItem();
//                    customerDialog.show();
//                    customerDialog.load(rowData);
                }
            });
            return row;
        });
    }

    private void loadDetailPane(CustomerDao customer) {
        this.customer = customer;
        lbCode.setText(MessageFormat.format("Code: {0}", customer.getId()));
        lbName.setText(MessageFormat.format("Name: {0}", customer.getName()));
        lbGender.setText(MessageFormat.format("Gender: {0}", customer.getGender()));
        lbMobile.setText(MessageFormat.format("Mobile: {0}", customer.getMobile()));
        lbNid.setText(MessageFormat.format("NID: {0}", customer.getNid()));
        lbAddress.setText(MessageFormat.format("Address: {0}", customer.getAddress()));
        lbState.setText(MessageFormat.format("State: {0}", customer.getIsState()));
        if (customer.getImage() != null) {
            ByteArrayInputStream imageStream = new ByteArrayInputStream(customer.getImage());
            imgCustomer.setImage(new Image(imageStream));
        } else imgCustomer.setImage(null);
    }

    private void loadCustomerData(List<CustomerDao> customers) {
        try {
            final ObservableList<CustomerDao> data = FXCollections.observableArrayList(customers);
            tbvCustomers.getItems().clear();
            tbvCustomers.refresh();
            tbvCustomers.setItems(data);
        } catch (Exception ignored) {
        }
    }

    private void loadMeterData(List<MeterDao> meters) {
        try {
            final ObservableList<MeterDao> data = FXCollections.observableArrayList(meters);
            tbvMeter.getItems().clear();
            tbvMeter.refresh();
            tbvMeter.setItems(data);
        } catch (Exception ignored) {
        }
    }

    @FXML
    void refreshList(ActionEvent event) {
        List<CustomerDao> customers = customerService.list();
        loadCustomerData(customers);
    }

    @FXML
    void addCustomer(ActionEvent event) {
        customerDialog.load(new CustomerDao());
        customerDialog.show();
    }


    @FXML
    void addMeter(ActionEvent event) {
        this.customerMeterDialog.show();
    }

    @FXML
    void refreshMeter(ActionEvent event) {
        customerDialog.load(new CustomerDao());
        customerDialog.show();
    }

    @FXML
    void updateImage(ActionEvent event) throws IOException {
        FileChooser fileChooser = new FileChooser();
        File selectedFile = fileChooser.showOpenDialog(null);
        customerService.store(this.customer, FileUtils.readFileToByteArray(selectedFile));
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
