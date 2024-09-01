package com.itbd.energymanager.view.ui.manager.module.home;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import jakarta.annotation.PreDestroy;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import net.rgielen.fxweaver.core.FxWeaver;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;


@Component
@FxmlView("/view/ui/manager/module/home/dashboard_view.fxml")
@Slf4j
public class DashboardView {

    private final FxWeaver fxWeaver;

    public DashboardView(FxWeaver fxWeaver) {
        this.fxWeaver = fxWeaver;
    }

    @FXML
    private BorderPane root;
    @FXML
    private PieChart chPie;
    @FXML
    private LineChart<String, Integer> chLine;

    @FXML
    private TableColumn<BillData, Integer> tbBill;

    @FXML
    private TableColumn<BillData, Integer> tbBillDue;

    @FXML
    private TableColumn<BillData, Integer> tbBillPaid;

    @FXML
    private TableColumn<BillData, Integer> tbBillMonth;

    @FXML
    private TableView<BillData> tbvBill;

    @Getter
    @Setter
    @AllArgsConstructor
    public class BillData {
        private Integer month;
        private Integer bill;
        private Integer paid;
        private Integer due;
    }

    @FXML
    public void initialize() {
        // Add data from the map to the PieChart
        initPie();
        initLine();
        initBillReport();
    }

    private void initBillReport() {
        tbBill.setCellValueFactory(new PropertyValueFactory<>("bill"));
        tbBillDue.setCellValueFactory(new PropertyValueFactory<>("due"));
        tbBillPaid.setCellValueFactory(new PropertyValueFactory<>("paid"));
        tbBillMonth.setCellValueFactory(new PropertyValueFactory<>("month"));

        List<BillData> billDataList = List.of(
                new BillData(1, 150, 100, 50),
                new BillData(2, 120, 120, 0),
                new BillData(3, 180, 150, 30),
                new BillData(4, 140, 100, 40),
                new BillData(5, 165, 165, 0),
                new BillData(6, 110, 80, 30),
                new BillData(7, 130, 130, 0),
                new BillData(8, 145, 120, 25),
                new BillData(9, 175, 150, 25),
                new BillData(10, 160, 160, 0),
                new BillData(11, 155, 130, 25),
                new BillData(12, 190, 180, 10),
                new BillData(1, 140, 120, 20),
                new BillData(2, 175, 165, 10),
                new BillData(3, 160, 160, 0),
                new BillData(4, 135, 100, 35),
                new BillData(5, 125, 125, 0),
                new BillData(6, 145, 130, 15),
                new BillData(7, 115, 115, 0),
                new BillData(8, 155, 150, 5)
        );

        final ObservableList<BillData> data = FXCollections.observableArrayList(billDataList);
        tbvBill.getItems().clear();
        tbvBill.refresh();
        tbvBill.setItems(data);

    }

    private void initPie() {
        Map<String, Integer> dataMap = new HashMap<>();
        dataMap.put("Due", 3256);
        dataMap.put("Paid", 7061);

        dataMap.forEach((k, v) -> chPie.getData().add(new PieChart.Data(k, v)));
    }

    private void initLine() {
        Map<String, Integer> dataMap = new HashMap<>();
        dataMap.put("1", 3650);
        dataMap.put("2", 1568);
        dataMap.put("3", 200);
        dataMap.put("4", 153);
        dataMap.put("5", 100);
        dataMap.put("6", 100);
        dataMap.put("7", 800);

        XYChart.Series series = new XYChart.Series();
        series.setName("My portfolio");
        dataMap.forEach((k, v) -> series.getData().add(new XYChart.Data(k, v)));
        chLine.getData().add(series);
    }


    private Alert customAlert(String title, String headerText, String contentText, ButtonType... buttonType) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(title);
        alert.setHeaderText(headerText);
        alert.setResizable(false);
        if (Objects.nonNull(contentText)) alert.setContentText(contentText);
        if (Objects.nonNull(buttonType)) alert.getButtonTypes().addAll(buttonType);
        new FontAwesomeIconView(FontAwesomeIcon.HOME);
        return alert;
    }

    @PreDestroy
    private void cleanup() {
        log.info("I am finish distributor!");
    }
}
