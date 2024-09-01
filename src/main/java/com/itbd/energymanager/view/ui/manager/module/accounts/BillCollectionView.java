package com.itbd.energymanager.view.ui.manager.module.accounts;

import com.itbd.energymanager.dao.UserDao;
import com.itbd.energymanager.repos.UserRepo;
import jakarta.annotation.PreDestroy;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.BorderPane;
import lombok.extern.slf4j.Slf4j;
import net.rgielen.fxweaver.core.FxWeaver;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Objects;


@Component
@FxmlView("/view/ui/manager/module/accounts/bill_collection_view.fxml")
@Slf4j
public class BillCollectionView {

    @Autowired
    private FxWeaver fxWeaver;
    private UserDao user;
    @Autowired
    private UserRepo userRepo;

    @FXML
    private BorderPane root;

    @FXML
    public void initialize() {
//        root.setCenter(sourceImageZoom);

//        projectTrRepo.findById(user.getProjectKey().longValue()).ifPresent(projectE -> {
//            project = projectE;
//        });
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
