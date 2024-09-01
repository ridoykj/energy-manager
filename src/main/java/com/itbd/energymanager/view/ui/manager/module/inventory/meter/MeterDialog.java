package com.itbd.energymanager.view.ui.manager.module.inventory.meter;

import com.itbd.energymanager.dao.MeterDao;
import com.itbd.energymanager.services.MeterService;
import jakarta.annotation.PreDestroy;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import lombok.extern.slf4j.Slf4j;
import net.rgielen.fxweaver.core.FxWeaver;
import net.rgielen.fxweaver.core.FxmlView;
import org.controlsfx.control.textfield.AutoCompletionBinding;
import org.controlsfx.control.textfield.TextFields;
import org.springframework.stereotype.Component;

import java.util.List;


@Component
@FxmlView("/view/ui/manager/module/inventory/meter/meter_dialog.fxml")
@Slf4j
public class MeterDialog {
    private final FxWeaver fxWeaver;
    private final MeterService meterService;
    private Stage stage;
    @FXML
    private BorderPane root;
    @FXML
    private CheckBox cbState;
    @FXML
    private DatePicker dtPurchase;
    @FXML
    private TextArea taDescription;
    @FXML
    private TextField txBrand;
    @FXML
    private TextField txName;
    @FXML
    private TextField txCategory;
    @FXML
    private TextField txMeterId;
    @FXML
    private TextField txModel;
    private AutoCompletionBinding<String> autoCompletionBinding;

    public MeterDialog(FxWeaver fxWeaver, MeterService meterService) {
        this.fxWeaver = fxWeaver;
        this.meterService = meterService;
    }

    @FXML
    public void initialize() {
        this.stage = new Stage();
        stage.setScene(new Scene(root));
        stage.setTitle("Meter Entry");
        stage.setAlwaysOnTop(true);

        txMeterId.setOnKeyPressed(e -> {
            log.info("txMeterId: " + e.getCode());
            if (autoCompletionBinding != null) autoCompletionBinding.dispose();
            // TODO: Need to implement database customer table data
            List<String> customerId = meterService.list().stream().map(MeterDao::getId).toList();
            autoCompletionBinding = TextFields.bindAutoCompletion(txMeterId, customerId);
        });
    }

    public void load(MeterDao meter) {
        txMeterId.setText(meter.getId());
        txName.setText(meter.getName());
        txBrand.setText(meter.getBrand());
        txModel.setText(meter.getModel());
        dtPurchase.setValue(meter.getPurchase());
//        txCategory.setText(meter.getCategory());
        cbState.setSelected(meter.getIsState() != null ? meter.getIsState() : false);
        taDescription.setText(meter.getDescription());
    }

    private MeterDao process() {
        MeterDao meter = new MeterDao();
        meter.setId(txMeterId.getText());
        meter.setId(txName.getText());
        meter.setBrand(txBrand.getText());
        meter.setModel(txModel.getText());
        meter.setPurchase(dtPurchase.getValue());
//        meter.setCategory(txCategory.getText());
        meter.setIsState(cbState.isSelected());
        meter.setDescription(taDescription.getText());
        return meter;
    }

    @FXML
    void save(ActionEvent event) {
        meterService.save(process());
    }

    public void show() {
        stage.show();
    }

    @FXML
    void close(ActionEvent event) {
        stage.close();
    }


    @PreDestroy
    private void cleanup() {
        log.info("I am finish distributor!");
    }
}
