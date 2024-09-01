package com.itbd.energymanager.view.ui.manager.module.user.customer;

import com.itbd.energymanager.dao.CustomerDao;
import com.itbd.energymanager.services.CustomerService;
import jakarta.annotation.PreDestroy;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import lombok.extern.slf4j.Slf4j;
import net.rgielen.fxweaver.core.FxWeaver;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.stereotype.Component;


@Component
@FxmlView("/view/ui/manager/module/user/customer/customer_dialog.fxml")
@Slf4j
public class CustomerDialog {
    private final FxWeaver fxWeaver;
    private final CustomerService customerService;
    private Stage stage;
    @FXML
    private BorderPane root;
    @FXML
    private TextArea taAddress;
    @FXML
    private TextField txCode, txName, txNid, txMobile;
    @FXML
    private CheckBox cbState;
    @FXML
    private ComboBox<String> cbGender;

    public CustomerDialog(FxWeaver fxWeaver, CustomerService customerService) {
        this.fxWeaver = fxWeaver;
        this.customerService = customerService;
    }

    @FXML
    public void initialize() {
        this.stage = new Stage();
        stage.setScene(new Scene(root));
        stage.setTitle("Customer Entry");
        stage.setAlwaysOnTop(true);

        ObservableList<String> genderLs = FXCollections.observableArrayList("Male", "Female", "Others");
        cbGender.setItems(genderLs);
    }

    public void load(CustomerDao customer) {
        txCode.setText(customer.getId());
        txCode.setDisable(customer.getId() != null);
        txName.setText(customer.getName());
        cbGender.setValue(customer.getGender());
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
        customer.setGender(cbGender.getValue());
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
