package com.itbd.energymanager.view.ui.manager.module.config.area;

import com.itbd.energymanager.dao.AreaDao;
import com.itbd.energymanager.services.AreaService;
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
@FxmlView("/view/ui/manager/module/config/area/area_dialog.fxml")
@Slf4j
public class AreaDialog {
    private final FxWeaver fxWeaver;
    private final AreaService areaService;
    private Stage stage;
    @FXML
    private BorderPane root;
    @FXML
    private TextField txCode;
    @FXML
    private TextField txCity;
    @FXML
    private TextField txUnion;
    @FXML
    private TextField txRoad;
    @FXML
    private TextArea taAddress;

    public AreaDialog(FxWeaver fxWeaver, AreaService areaService) {
        this.fxWeaver = fxWeaver;
        this.areaService = areaService;
    }

    @FXML
    public void initialize() {
        this.stage = new Stage();
        stage.setScene(new Scene(root));
        stage.setTitle("Customer Entry");
        stage.setAlwaysOnTop(true);
    }

    public void load(AreaDao customer) {
        txCode.setText(customer.getId());
        txCode.setDisable(customer.getId() != null);
        txCity.setText(customer.getCity());
        txUnion.setText(customer.getUnion());
        txRoad.setText(customer.getRoad());
        taAddress.setText(customer.getAddress());
    }

    @FXML
    void save(ActionEvent event) {
        areaService.save(process());
    }

    public void show() {
        stage.show();
    }

    @FXML
    void close(ActionEvent event) {
        stage.close();
    }

    private AreaDao process() {
        AreaDao area = new AreaDao();
        area.setId(txCode.getText());
        area.setCity(txCity.getText());
        area.setUnion(txUnion.getText());
        area.setRoad(txRoad.getText());
        area.setAddress(taAddress.getText());
        return area;
    }

    @PreDestroy
    private void cleanup() {
        log.info("I am finish distributor!");
    }
}
