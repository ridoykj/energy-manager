package com.itbd.energymanager.view.ui.manager.module.accounts.bill;

import com.itbd.energymanager.dao.BillDao;
import com.itbd.energymanager.dao.MeterDao;
import com.itbd.energymanager.services.BillService;
import com.itbd.energymanager.services.MeterService;
import com.itbd.energymanager.services.reports.WReportBuilder;
import jakarta.annotation.PreDestroy;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import lombok.extern.slf4j.Slf4j;
import net.rgielen.fxweaver.core.FxControllerAndView;
import net.rgielen.fxweaver.core.FxWeaver;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileInputStream;
import java.text.MessageFormat;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Component
@FxmlView("/view/ui/manager/module/accounts/bill/bill_view.fxml")
@Slf4j
public class BillView {

    private final FxWeaver fxWeaver;
    private final BillService billService;
    private final MeterService meterService;
    private final WReportBuilder wReportBuilder;

    public BillView(FxWeaver fxWeaver, BillService billService, MeterService meterService, WReportBuilder wReportBuilder) {
        this.fxWeaver = fxWeaver;
        this.billService = billService;
        this.meterService = meterService;
        this.wReportBuilder = wReportBuilder;
    }

    private BillDialog billDialog;

    @FXML
    private BorderPane root;
    @FXML
    private Label lbMeterCustomerId;

    @FXML
    private Label lbMeterDescription;

    @FXML
    private Label lbMeterId;

    @FXML
    private Label lbMeterReg;

    @FXML
    private Label lbMeterState;
    @FXML
    private TableColumn<MeterDao, String> tbMeterCustomerId;

    @FXML
    private TableColumn<MeterDao, String> tbMeterId;

    @FXML
    private TableColumn<MeterDao, Boolean> tbMeterState;

    @FXML
    private TableColumn<MeterDao, LocalDate> tbMeterRegister;

    @FXML
    private TableView<MeterDao> tbvMeter;

    @FXML
    private TableColumn<BillDao, Void> tbBillAction;

    @FXML
    private TableColumn<BillDao, String> tbBillCost;

    @FXML
    private TableColumn<BillDao, String> tbBillMonth;

    @FXML
    private TableColumn<BillDao, Boolean> tbBillPaid;

    @FXML
    private TableColumn<BillDao, LocalDate> tbBillReadDate;

    @FXML
    private TableColumn<BillDao, String> tbBillUnit;

    @FXML
    private TableColumn<BillDao, String> tbBillYear;

    @FXML
    private TableView<BillDao> tbvBill;

    @FXML
    public void initialize() {
        FxControllerAndView<BillDialog, BorderPane> customerMVC = fxWeaver.load(BillDialog.class);
        this.billDialog = customerMVC.getController();

        tableMeterInit();
        tableBillInit();

        List<MeterDao> meters = meterService.list();
        loadMeterData(meters);
    }

    @FXML
    void refreshBillList(ActionEvent event) {
        List<BillDao> customers = billService.list();
        loadBillData(customers);
    }

    @FXML
    void updateReading(ActionEvent event) {
        billDialog.load(new BillDao());
        billDialog.show();
    }

    private void tableMeterInit() {
        // TODO: Meter
        tbMeterId.setCellValueFactory(new PropertyValueFactory<>("id"));
        tbMeterCustomerId.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCustomer().getId()));
        tbMeterState.setCellValueFactory(new PropertyValueFactory<>("isState"));
        tbMeterRegister.setCellValueFactory(new PropertyValueFactory<>("registration"));
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

        tbvMeter.getSelectionModel().selectedItemProperty().addListener((o, oldVal, newVal) -> {
            if (newVal != null) {
                loadDetailPane(newVal);
                loadBillData(meterService.getBills(newVal.getId()));
            }
        });

        tbvMeter.setRowFactory(tv -> {
            TableRow<MeterDao> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (!row.isEmpty())) {
                    MeterDao rowData = row.getItem();
//                    billDialog.show();
//                    billDialog.load(rowData);
                }
            });
            return row;
        });
    }

    private void tableBillInit() {
        // TODO: Bill
        tbBillYear.setCellValueFactory(new PropertyValueFactory<>("year"));
        tbBillMonth.setCellValueFactory(new PropertyValueFactory<>("month"));
        tbBillReadDate.setCellValueFactory(new PropertyValueFactory<>("read"));
        tbBillUnit.setCellValueFactory(new PropertyValueFactory<>("unit"));
//        tbBillCost.setCellValueFactory(new PropertyValueFactory<>("mobile"));
        tbBillPaid.setCellValueFactory(new PropertyValueFactory<>("isPaid"));
//        tbBillAction.setCellValueFactory(new PropertyValueFactory<>("mobile"));
        tbBillAction.setCellFactory(col -> new TableCell<>() {
            private final Button printBtn = new Button("", new ImageView("assets/icon/res/print.png"));
            private final Button deleteBtn = new Button("", new ImageView("assets/icon/res/delete.png"));

            {
                printBtn.getStyleClass().add("btn-transparent");
                deleteBtn.getStyleClass().add("btn-transparent");
                printBtn.setOnAction(event -> {
                    BillDao person = getTableView().getItems().get(getIndex());
                    Map<String, Object> parameters = new HashMap<>();
                    try {
                        File file = ResourceUtils.getFile("classpath:./assets/icon/appicon/logo.png");
                        FileInputStream fileInputStream = new FileInputStream(file);
                        if (file.exists()) {
                            parameters.put("file", fileInputStream);
                        }
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                    wReportBuilder.reportDialog("class", "Blank_A4.jrxml", parameters, "Bill Viewer");
                    // Perform the delete action or any other action you need
//                    getTableView().getItems().remove(person);
//                    System.out.println("Deleted: " + person.getName());
                });
                deleteBtn.setOnAction(event -> {
                    BillDao person = getTableView().getItems().get(getIndex());
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
                    HBox hBox = new HBox(printBtn, deleteBtn);
                    hBox.setSpacing(8);
                    hBox.setPadding(Insets.EMPTY);
                    setGraphic(hBox);
                }
            }
        });

        tbvBill.setRowFactory(tv -> {
            TableRow<BillDao> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (!row.isEmpty())) {
                    BillDao rowData = row.getItem();
//                    billDialog.show();
//                    billDialog.load(rowData);
                }
            });
            return row;
        });
    }

    private void loadDetailPane(MeterDao meter) {
        lbMeterId.setText(MessageFormat.format("Code: {0}", meter.getId()));
        lbMeterCustomerId.setText(MessageFormat.format("Name: {0}", meter.getName()));
        lbMeterState.setText(MessageFormat.format("Gender: {0}", meter.getIsState()));
        lbMeterReg.setText(MessageFormat.format("Mobile: {0}", meter.getRegistration()));
        lbMeterDescription.setText(MessageFormat.format("NID: {0}", meter.getDescription()));
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

    private void loadBillData(List<BillDao> bills) {
        try {
            final ObservableList<BillDao> data = FXCollections.observableArrayList(bills);
            tbvBill.getItems().clear();
            tbvBill.refresh();
            tbvBill.setItems(data);
        } catch (Exception ignored) {
        }
    }

    @PreDestroy
    private void cleanup() {
        log.info("I am finish distributor!");
    }
}
