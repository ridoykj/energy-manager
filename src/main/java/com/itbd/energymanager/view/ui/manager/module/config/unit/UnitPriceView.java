package com.itbd.energymanager.view.ui.manager.module.config.unit;

import com.itbd.energymanager.dao.CategoryDao;
import com.itbd.energymanager.dao.CustomerDao;
import com.itbd.energymanager.dao.GradeDao;
import com.itbd.energymanager.services.CategoryService;
import com.itbd.energymanager.services.GradeService;
import jakarta.annotation.PreDestroy;
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

import java.util.List;
import java.util.Objects;


@Component
@FxmlView("/view/ui/manager/module/config/unit/unit_price_view.fxml")
@Slf4j
public class UnitPriceView {
    private final FxWeaver fxWeaver;
    private final CategoryService categoryService;
    private final GradeService gradeService;

    public UnitPriceView(FxWeaver fxWeaver, CategoryService categoryService, GradeService gradeService) {
        this.fxWeaver = fxWeaver;
        this.categoryService = categoryService;
        this.gradeService = gradeService;
    }

    private CategoryDialog categoryDialog;
    private GradeDialog gradeDialog;

    @FXML
    private BorderPane root;
    @FXML
    private TableColumn<CategoryDao, String> tbCategory;

    @FXML
    private TableColumn<CategoryDao, String> tbCategoryDescription;
    @FXML
    private TableColumn<CategoryDao, Void> tbCategoryAction;

    @FXML
    private TableView<CategoryDao> tbvCategory;

    @FXML
    private TableColumn<GradeDao, String> tbGradeMax;

    @FXML
    private TableColumn<GradeDao, String> tbGradeMin;

    @FXML
    private TableColumn<GradeDao, String> tbGradePrice;

    @FXML
    private TableColumn<GradeDao, String> tbGradeSc;

    @FXML
    private TableColumn<GradeDao, String> tbGradeSd;

    @FXML
    private TableColumn<GradeDao, String> tbGradeVat;
    @FXML
    private TableColumn<GradeDao, Void> tbGradeAction;

    @FXML
    private TableView<GradeDao> tbvGrade;

    @FXML
    public void initialize() {
        FxControllerAndView<CategoryDialog, BorderPane> customerMVC = fxWeaver.load(CategoryDialog.class);
        this.categoryDialog = customerMVC.getController();

        FxControllerAndView<GradeDialog, BorderPane> meterMVC = fxWeaver.load(GradeDialog.class);
        this.gradeDialog = meterMVC.getController();

        tableCategoryInit();
        tableGradeInit();
        List<CategoryDao> customers = categoryService.list();
        loadCategoryData(customers);
    }

    @FXML
    void addCategory(ActionEvent event) {
        categoryDialog.load(new CategoryDao());
        categoryDialog.show();
    }

    @FXML
    void addGrade(ActionEvent event) {
        gradeDialog.load(new GradeDao());
        gradeDialog.show();
    }

    @FXML
    void refreshCategory(ActionEvent event) {
        List<CategoryDao> category = categoryService.list();
        loadCategoryData(category);
    }

    @FXML
    void refreshGrade(ActionEvent event) {
        List<GradeDao> category = gradeService.list();
        loadGradeData(category);
    }

    private void tableCategoryInit() {
        // TODO: Category
        tbCategory.setCellValueFactory(new PropertyValueFactory<>("id"));
        tbCategoryDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        tbCategoryAction.setCellFactory(col -> new TableCell<>() {
            private final Button actionButton = new Button("", new ImageView("assets/icon/res/delete.png"));

            {
                actionButton.getStyleClass().add("btn-transparent");
                actionButton.setOnAction(event -> {
                    CategoryDao person = getTableView().getItems().get(getIndex());
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
        tbvCategory.getSelectionModel().selectedItemProperty().addListener((o, oldVal, newVal) -> {
            if (newVal != null) {
//                loadDetailPane(newVal);
                loadGradeData(categoryService.getGrades(newVal.getId()));
            }
        });

        tbvCategory.setRowFactory(tv -> {
            TableRow<CategoryDao> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (!row.isEmpty())) {
                    CategoryDao rowData = row.getItem();
                    categoryDialog.show();
                    categoryDialog.load(rowData);
                }
            });
            return row;
        });
    }

    private void tableGradeInit() {
        // TODO: Grade
        tbGradeMin.setCellValueFactory(new PropertyValueFactory<>("minUnit"));
        tbGradeMax.setCellValueFactory(new PropertyValueFactory<>("maxUnit"));
        tbGradePrice.setCellValueFactory(new PropertyValueFactory<>("rate"));
        tbGradeVat.setCellValueFactory(new PropertyValueFactory<>("vat"));
        tbGradeSd.setCellValueFactory(new PropertyValueFactory<>("sd"));
        tbGradeSc.setCellValueFactory(new PropertyValueFactory<>("sc"));
        tbGradeAction.setCellFactory(col -> new TableCell<>() {
            private final Button actionButton = new Button("", new ImageView("assets/icon/res/delete.png"));

            {
                actionButton.getStyleClass().add("btn-transparent");
                actionButton.setOnAction(event -> {
                    GradeDao person = getTableView().getItems().get(getIndex());
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
        tbvGrade.getSelectionModel().selectedItemProperty().addListener((o, oldVal, newVal) -> {
            if (newVal != null) {
//                loadDetailPane(newVal);
            }
        });

        tbvGrade.setRowFactory(tv -> {
            TableRow<GradeDao> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (!row.isEmpty())) {
                    GradeDao rowData = row.getItem();
                    gradeDialog.show();
                    gradeDialog.load(rowData);
                }
            });
            return row;
        });
    }

    private void loadDetailPane(CustomerDao customer) {
    }

    private void loadCategoryData(List<CategoryDao> category) {
        try {
            final ObservableList<CategoryDao> data = FXCollections.observableArrayList(category);
            tbvCategory.getItems().clear();
            tbvCategory.refresh();
            tbvCategory.setItems(data);
        } catch (Exception ignored) {
        }
    }

    private void loadGradeData(List<GradeDao> category) {
        try {
            final ObservableList<GradeDao> data = FXCollections.observableArrayList(category);
            tbvGrade.getItems().clear();
            tbvGrade.refresh();
            tbvGrade.setItems(data);
        } catch (Exception ignored) {
        }
    }

    private Alert customAlert(String title, String headerText, String contentText, ButtonType... buttonType) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(title);
        alert.setHeaderText(headerText);
        alert.setResizable(false);
        if (Objects.nonNull(contentText)) alert.setContentText(contentText);
        if (Objects.nonNull(buttonType)) alert.getButtonTypes().addAll(buttonType);
        return alert;
    }

    @PreDestroy
    private void cleanup() {
        log.info("I am finish distributor!");
    }
}
