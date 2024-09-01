package com.itbd.energymanager.view.ui.manager.module.user.customer;

import com.itbd.energymanager.dao.CustomerDao;
import com.itbd.energymanager.services.CustomerService;
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

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Component
@FxmlView("/view/ui/manager/module/user/customer/customer_meter_dialog.fxml")
@Slf4j
public class CustomerMeterDialog {
    private final FxWeaver fxWeaver;
    private final CustomerService customerService;
    private Stage stage;
    @FXML
    private BorderPane root;
    @FXML
    private TextArea taAddress;
    @FXML
    private TextField txCode, txGender, txName, txNid, txMobile;
    @FXML
    private TextField txCategory;
    @FXML
    private TextField txMeterId;
    @FXML
    private CheckBox cbState;
    @FXML
    private DatePicker dtRegister;
    private AutoCompletionBinding<String> autoCompletionBinding;
    private String[] suggestions = {"Ridoy", "Kumar", "Joy"};
    private Set<String> suggestionSet = new HashSet<>(Arrays.asList(suggestions));
    public CustomerMeterDialog(FxWeaver fxWeaver, CustomerService customerService) {
        this.fxWeaver = fxWeaver;
        this.customerService = customerService;
    }

    @FXML
    public void initialize() {
        this.stage = new Stage();
        stage.setScene(new Scene(root));
        stage.setTitle("Customer Entry");
        stage.setAlwaysOnTop(true);
//        autoCompletionBinding = TextFields.bindAutoCompletion(txMeterId, suggestionSet);
//        txMeterId.setOnKeyPressed(e -> {
//            log.info("txMeterId: " + e.getCode());
//            if (autoCompletionBinding != null) autoCompletionBinding.dispose();
//            // TODO: Need to implement database Meter table data
//            autoCompletionBinding = TextFields.bindAutoCompletion(txMeterId, suggestionSet);
//        });

//        txCategory.setOnKeyPressed(e -> {
        txMeterId.setOnKeyPressed(e -> {
            log.info("txMeterId: " + e.getCode());
            if (autoCompletionBinding != null) autoCompletionBinding.dispose();
            // TODO: Need to implement database customer table data
            List<String> customerId = customerService.list().stream().map(CustomerDao::getId).toList();
            autoCompletionBinding = TextFields.bindAutoCompletion(txMeterId, customerId);
        });


    }

    public void load(CustomerDao customer) {
        txCode.setText(customer.getId());
        txCode.setDisable(customer.getId() != null);
        txName.setText(customer.getName());
        txGender.setText(customer.getGender());
        txNid.setText(customer.getNid());
        txMobile.setText(customer.getMobile());
        cbState.setSelected(customer.getIsState() != null ? customer.getIsState() : false);
        taAddress.setText(customer.getAddress());

    }

    @FXML
    void save(ActionEvent event) {
        customerService.save(process());
    }

    public void show() {
        stage.show();
    }

    @FXML
    void close(ActionEvent event) {
        stage.close();
    }

    private CustomerDao process() {
        CustomerDao customer = new CustomerDao();
        customer.setId(txCode.getText());
        customer.setName(txName.getText());
        customer.setNid(txNid.getText());
        customer.setGender(txGender.getText());
        customer.setMobile(txMobile.getText());
        customer.setIsState(cbState.isSelected());
        customer.setAddress(taAddress.getText());
        return customer;
    }

    @PreDestroy
    private void cleanup() {
        log.info("I am finish distributor!");
    }
}
