package com.itbd.energymanager.view.ui.manager.module.user.employee;

import com.itbd.energymanager.dao.EmployeeDao;
import com.itbd.energymanager.services.EmployeeService;
import jakarta.annotation.PreDestroy;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import lombok.extern.slf4j.Slf4j;
import net.rgielen.fxweaver.core.FxWeaver;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.stereotype.Component;


@Component
@FxmlView("/view/ui/manager/module/user/employee/employee_dialog.fxml")
@Slf4j
public class EmployeeDialog {
    private final FxWeaver fxWeaver;
    private final EmployeeService employeeService;
    private Stage stage;
    @FXML
    private BorderPane root;
    @FXML
    private DatePicker dtEmpBirthDate;

    @FXML
    private TextArea taEmpAddress;
    @FXML
    private TextField txEmpCode, txEmpDesignation, txEmpName, txEmpNid, txEmpMobile, txEmpSalary;
    @FXML
    private CheckBox cbEmpState;
    @FXML
    private ComboBox<String> cbEmpGender;

    public EmployeeDialog(FxWeaver fxWeaver, EmployeeService employeeService) {
        this.fxWeaver = fxWeaver;
        this.employeeService = employeeService;
    }

    @FXML
    public void initialize() {
        this.stage = new Stage();
        stage.setScene(new Scene(root));
        stage.setTitle("Customer Entry");
        stage.setAlwaysOnTop(true);

        ObservableList<String> genderLs = FXCollections.observableArrayList("Male", "Female", "Others");
        cbEmpGender.setItems(genderLs);
    }

    public void load(EmployeeDao employee) {
        txEmpCode.setText(employee.getId());
        txEmpCode.setDisable(employee.getId() != null);
        txEmpDesignation.setText(employee.getName());
        cbEmpGender.setValue(employee.getGender());
        dtEmpBirthDate.setValue(employee.getBirthDate());
        txEmpName.setText(employee.getNid());
        txEmpNid.setText(employee.getMobile());
        cbEmpState.setSelected(employee.getIsState() != null ? employee.getIsState() : false);
        taEmpAddress.setText(employee.getAddress());
    }

    @FXML
    void save(ActionEvent event) {
        employeeService.save(process());
    }

    @FXML
    void close(ActionEvent event) {
        stage.close();
    }


    public void show() {
        stage.show();
    }


    private EmployeeDao process() {
        EmployeeDao employee = new EmployeeDao();
        employee.setId(txEmpCode.getText());
        employee.setName(txEmpDesignation.getText());
        employee.setNid(txEmpName.getText());
        employee.setGender(cbEmpGender.getValue());
        employee.setBirthDate(dtEmpBirthDate.getValue());
        employee.setMobile(txEmpNid.getText());
        employee.setIsState(cbEmpState.isSelected());
        employee.setAddress(taEmpAddress.getText());
        return employee;
    }

    @PreDestroy
    private void cleanup() {
        log.info("I am finish distributor!");
    }
}
