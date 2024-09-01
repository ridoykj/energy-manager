package com.itbd.energymanager.view.ui.manager.module.user.employee;

import com.itbd.energymanager.dao.DesignationDao;
import com.itbd.energymanager.services.DesignationService;
import jakarta.annotation.PreDestroy;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import lombok.extern.slf4j.Slf4j;
import net.rgielen.fxweaver.core.FxWeaver;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.stereotype.Component;


@Component
@FxmlView("/view/ui/manager/module/user/employee/designation_dialog.fxml")
@Slf4j
public class DesignationDialog {
    private final FxWeaver fxWeaver;
    private final DesignationService designationService;
    private Stage stage;
    @FXML
    private BorderPane root;
    @FXML
    private TextField txCode;
    @FXML
    private TextArea taDescription;

    public DesignationDialog(FxWeaver fxWeaver, DesignationService designationService) {
        this.fxWeaver = fxWeaver;
        this.designationService = designationService;
    }

    @FXML
    public void initialize() {
        this.stage = new Stage();
        stage.setScene(new Scene(root));
        stage.setTitle("Customer Entry");
        stage.setAlwaysOnTop(true);
    }

    public void load(DesignationDao customer) {
        txCode.setText(customer.getId());
        txCode.setDisable(customer.getId() != null);
        taDescription.setText(customer.getDescription());
    }

    @FXML
    void save(ActionEvent event) {
        designationService.save(process());
    }

    public void show() {
        stage.show();
    }

    @FXML
    void close(ActionEvent event) {
        stage.close();
    }

    private DesignationDao process() {
        DesignationDao customer = new DesignationDao();
        customer.setId(txCode.getText());
        customer.setDescription(taDescription.getText());
        return customer;
    }

    @PreDestroy
    private void cleanup() {
        log.info("I am finish distributor!");
    }
}
