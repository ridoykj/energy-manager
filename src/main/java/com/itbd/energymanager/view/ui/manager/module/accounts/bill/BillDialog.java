package com.itbd.energymanager.view.ui.manager.module.accounts.bill;

import com.itbd.energymanager.dao.BillDao;
import com.itbd.energymanager.services.BillService;
import jakarta.annotation.PreDestroy;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import lombok.extern.slf4j.Slf4j;
import net.rgielen.fxweaver.core.FxWeaver;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;


@Component
@FxmlView("/view/ui/manager/module/accounts/bill/bill_dialog.fxml")
@Slf4j
public class BillDialog {
    private final FxWeaver fxWeaver;
    private final BillService billService;
    private Stage stage;
    @FXML
    private BorderPane root;
    @FXML
    private TextField txMonth;

    @FXML
    private TextField txValue;

    @FXML
    private TextField txYear;

    @FXML
    private DatePicker dtRead;


    public BillDialog(FxWeaver fxWeaver, BillService billService) {
        this.fxWeaver = fxWeaver;
        this.billService = billService;
    }

    @FXML
    public void initialize() {
        this.stage = new Stage();
        stage.setScene(new Scene(root));
        stage.setTitle("Grade Entry");
        stage.setAlwaysOnTop(true);
    }

    public void load(BillDao bill) {
        txMonth.setText(String.valueOf(bill.getMonth() != null ? bill.getMonth() : BigDecimal.ZERO));
        txYear.setText(String.valueOf(bill.getYear() != null ? bill.getYear() : BigDecimal.ZERO));
        txValue.setText(String.valueOf(bill.getValue() != null ? bill.getValue() : BigDecimal.ZERO));
        dtRead.setValue(bill.getRead());
    }

    @FXML
    void save(ActionEvent event) {
        billService.save(process());
    }

    public void show() {
        stage.show();
    }

    @FXML
    void close(ActionEvent event) {
        stage.close();
    }

    private BillDao process() {
        BillDao area = new BillDao();
        area.setYear(Integer.valueOf(txYear.getText()));
        area.setMonth(Integer.valueOf(txMonth.getText()));
        area.setValue(new BigDecimal(txValue.getText()));
        area.setRead(dtRead.getValue());
        return area;
    }

    @PreDestroy
    private void cleanup() {
        log.info("I am finish distributor!");
    }
}
