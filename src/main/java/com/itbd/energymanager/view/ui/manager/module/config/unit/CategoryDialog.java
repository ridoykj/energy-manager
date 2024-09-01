package com.itbd.energymanager.view.ui.manager.module.config.unit;

import com.itbd.energymanager.dao.CategoryDao;
import com.itbd.energymanager.services.CategoryService;
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
@FxmlView("/view/ui/manager/module/config/unit/category_dialog.fxml")
@Slf4j
public class CategoryDialog {
    private final FxWeaver fxWeaver;
    private final CategoryService categoryService;
    private Stage stage;
    @FXML
    private BorderPane root;
    @FXML
    private TextField txCode;
    @FXML
    private TextArea taDescription;

    public CategoryDialog(FxWeaver fxWeaver, CategoryService categoryService) {
        this.fxWeaver = fxWeaver;
        this.categoryService = categoryService;
    }

    @FXML
    public void initialize() {
        this.stage = new Stage();
        stage.setScene(new Scene(root));
        stage.setTitle("Customer Entry");
        stage.setAlwaysOnTop(true);
    }

    public void load(CategoryDao customer) {
        txCode.setText(customer.getId());
        txCode.setDisable(customer.getId() != null);
        taDescription.setText(customer.getDescription());
    }

    @FXML
    void save(ActionEvent event) {
        categoryService.save(process());
    }

    public void show() {
        stage.show();
    }

    @FXML
    void close(ActionEvent event) {
        stage.close();
    }

    private CategoryDao process() {
        CategoryDao area = new CategoryDao();
        area.setId(txCode.getText());
        area.setDescription(taDescription.getText());
        return area;
    }

    @PreDestroy
    private void cleanup() {
        log.info("I am finish distributor!");
    }
}
