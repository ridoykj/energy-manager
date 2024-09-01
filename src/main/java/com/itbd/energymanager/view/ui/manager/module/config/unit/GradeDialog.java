package com.itbd.energymanager.view.ui.manager.module.config.unit;

import com.itbd.energymanager.dao.GradeDao;
import com.itbd.energymanager.services.GradeService;
import jakarta.annotation.PreDestroy;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import lombok.extern.slf4j.Slf4j;
import net.rgielen.fxweaver.core.FxWeaver;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;


@Component
@FxmlView("/view/ui/manager/module/config/unit/unit_dialog.fxml")
@Slf4j
public class GradeDialog {
    private final FxWeaver fxWeaver;
    private final GradeService gradeService;
    private Stage stage;
    @FXML
    private BorderPane root;
    @FXML
    private TextField txMax;

    @FXML
    private TextField txMin;

    @FXML
    private TextField txRate;

    @FXML
    private TextField txSc;

    @FXML
    private TextField txSd;

    @FXML
    private TextField txVat;

    public GradeDialog(FxWeaver fxWeaver, GradeService gradeService) {
        this.fxWeaver = fxWeaver;
        this.gradeService = gradeService;
    }

    @FXML
    public void initialize() {
        this.stage = new Stage();
        stage.setScene(new Scene(root));
        stage.setTitle("Grade Entry");
        stage.setAlwaysOnTop(true);
    }

    public void load(GradeDao grade) {
        txMax.setText(String.valueOf(grade.getMaxUnit() != null ? grade.getMaxUnit() : BigDecimal.ZERO));
        txMin.setText(String.valueOf(grade.getMinUnit() != null ? grade.getMinUnit() : BigDecimal.ZERO));
        txRate.setText(String.valueOf(grade.getRate() != null ? grade.getRate() : BigDecimal.ZERO));
        txSc.setText(String.valueOf(grade.getSc() != null ? grade.getSc() : BigDecimal.ZERO));
        txSd.setText(String.valueOf(grade.getSd() != null ? grade.getSd() : BigDecimal.ZERO));
        txVat.setText(String.valueOf(grade.getVat() != null ? grade.getVat() : BigDecimal.ZERO));
    }

    @FXML
    void save(ActionEvent event) {
        gradeService.save(process());
    }

    public void show() {
        stage.show();
    }

    @FXML
    void close(ActionEvent event) {
        stage.close();
    }

    private GradeDao process() {
        GradeDao area = new GradeDao();
        area.setMinUnit(new BigDecimal(txMin.getText()));
        area.setMaxUnit(new BigDecimal(txMax.getText()));
        area.setRate(new BigDecimal(txRate.getText()));
        area.setSc(new BigDecimal(txSc.getText()));
        area.setSd(new BigDecimal(txSd.getText()));
        area.setVat(new BigDecimal(txVat.getText()));
        return area;
    }

    @PreDestroy
    private void cleanup() {
        log.info("I am finish distributor!");
    }
}
