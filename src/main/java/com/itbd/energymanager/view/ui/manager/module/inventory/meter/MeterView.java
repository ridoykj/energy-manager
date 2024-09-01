package com.itbd.energymanager.view.ui.manager.module.inventory.meter;

import com.itbd.energymanager.dao.MeterDao;
import com.itbd.energymanager.services.MeterService;
import com.itbd.energymanager.services.reports.WReportBuilder;
import jakarta.annotation.PreDestroy;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
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
import java.time.LocalDate;
import java.util.List;


@Component
@FxmlView("/view/ui/manager/module/inventory/meter/meter_view.fxml")
@Slf4j
public class MeterView {

    private final FxWeaver fxWeaver;
    private final MeterService meterService;
    private final WReportBuilder wReportBuilder;
    private MeterDialog meterDialog;
    @FXML
    private BorderPane root;

    @FXML
    private Label lbAddrsss;
    @FXML
    private Label lbBrand;
    @FXML
    private Label lbCategory;
    @FXML
    private Label lbCustomerId;
    @FXML
    private Label lbCustomerName;
    @FXML
    private Label lbDescription;
    @FXML
    private Label lbMeterId;
    @FXML
    private Label lbModel;
    @FXML
    private Label lbPurchaseDate;
    @FXML
    private Label lbState;
    @FXML
    private TableColumn<MeterDao, String> tbBrand;
    @FXML
    private TableColumn<MeterDao, String> tbCategory;
    @FXML
    private TableColumn<MeterDao, String> tbId;
    @FXML
    private TableColumn<MeterDao, String> tbModel;
    @FXML
    private TableColumn<MeterDao, String> tbName;
    @FXML
    private TableColumn<MeterDao, Boolean> tbState;

    @FXML
    private TableColumn<MeterDao, Void> tbAction;
    @FXML
    private TableView<MeterDao> tbvMeter;
//    @FXML
//    private WebView wMeter;

    public MeterView(FxWeaver fxWeaver, MeterService meterService, WReportBuilder wReportBuilder) {
        this.fxWeaver = fxWeaver;
        this.meterService = meterService;
        this.wReportBuilder = wReportBuilder;
    }

    @FXML
    public void initialize() {
        FxControllerAndView<MeterDialog, BorderPane> meterMVC = fxWeaver.load(MeterDialog.class);
        this.meterDialog = meterMVC.getController();

        tableInit();
        List<MeterDao> customers = meterService.list();
        loadData(customers);
    }

    private void tableInit() {
        tbId.setCellValueFactory(new PropertyValueFactory<>("id"));
        tbName.setCellValueFactory(new PropertyValueFactory<>("name"));
        tbBrand.setCellValueFactory(new PropertyValueFactory<>("brand"));
        tbModel.setCellValueFactory(new PropertyValueFactory<>("model"));
        tbCategory.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCategory().getId()));
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
                loadDetailPane(newVal);
//                WebEngine webEngine = wMeter.getEngine();
//                Map<String, Object> parameters = new HashMap<>();
//                String html = wReportBuilder.getHtml("class/Blank_A4.jrxml", parameters, "class_schedule_rpt");
//                webEngine.loadContent(html);
            }
        });

        tbvMeter.setRowFactory(tv -> {
            TableRow<MeterDao> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (!row.isEmpty())) {
                    MeterDao rowData = row.getItem();
                    meterDialog.show();
                    meterDialog.load(rowData);
                }
            });
            return row;
        });
    }

    private void loadData(List<MeterDao> customers) {
        Platform.runLater(() -> {
            try {
                final ObservableList<MeterDao> data = FXCollections.observableArrayList(customers);
                tbvMeter.getItems().clear();
                tbvMeter.refresh();
                tbvMeter.setItems(data);
            } catch (Exception ignored) {
            }
        });
    }

    private void loadDetailPane(MeterDao customer) {
        lbMeterId.setText(MessageFormat.format("Meter ID: {0}", customer.getId()));
        lbBrand.setText(MessageFormat.format("Brand: {0}", customer.getBrand()));
        lbModel.setText(MessageFormat.format("Model: {0}", customer.getModel()));
        lbPurchaseDate.setText(MessageFormat.format("Purchase Date: {0}", customer.getPurchase()));
        lbState.setText(MessageFormat.format("State: {0}", customer.getIsState()));
        lbCategory.setText(MessageFormat.format("Category: {0}", customer.getCategory().getId()));
        lbCustomerId.setText(MessageFormat.format("Customer ID: {0}", customer.getCustomer().getId()));
        lbCustomerName.setText(MessageFormat.format("Customer Name: {0}", customer.getCustomer().getName()));
        lbAddrsss.setText(MessageFormat.format("Address: {0}", customer.getCustomer().getAddress()));
        lbDescription.setText(MessageFormat.format("Description: {0}", customer.getDescription()));
    }


    @FXML
    void addMeter(ActionEvent event) {
        meterDialog.load(new MeterDao());
        meterDialog.show();
    }

    @PreDestroy
    private void cleanup() {
        log.info("I am finish distributor!");
    }
}
