package com.itbd.energymanager.view.ui.manager;

import com.itbd.energymanager.dao.UserDao;
import com.itbd.energymanager.repos.UserRepo;
import com.itbd.energymanager.view.ui.component.sidebar.SidebarComponent;
import com.itbd.energymanager.view.ui.manager.module.home.DashboardView;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import lombok.extern.slf4j.Slf4j;
import net.rgielen.fxweaver.core.FxControllerAndView;
import net.rgielen.fxweaver.core.FxWeaver;
import net.rgielen.fxweaver.core.FxmlView;
import org.controlsfx.control.HiddenSidesPane;
import org.controlsfx.control.StatusBar;
import org.springframework.stereotype.Component;

import static javafx.geometry.Orientation.VERTICAL;

@Component
@FxmlView("/view/ui/manager/mainlayout_view.fxml")
@Slf4j
public class MainLayoutView {

    private final FxWeaver fxWeaver;
    @FXML
    private BorderPane sidebarWindow;
    private StatusBar statusBar;

    private final UserRepo userRepo;

    public MainLayoutView(FxWeaver fxWeaver, UserRepo userRepo) {
        this.fxWeaver = fxWeaver;
        this.userRepo = userRepo;
    }

    @FXML
    public void initialize() {
//        stage.setTitle(String.format("Digital Election - De-Soft (user:%s, counter:%d, role:[%s], project:[%s]) ", user.getUsername(), user.getCounter(), user.getRole(), projectTrRepo.findById(user.getProjectKey().longValue()).get().getName()));
//        userRepo.findAll().stream().findFirst().ifPresent(user -> {
//            statusBar = new StatusBar();
//            user.setIsLogin(true);
//            userRepo.save(user);
//
//            FxControllerAndView<ElectionProcessSideBar, VBox> serverControllerView = fxWeaver.load(ElectionProcessSideBar.class);
//            VBox menuBtn = serverControllerView.getView().orElse(null);
//            ElectionProcessSideBar df = serverControllerView.getController();
//            HiddenSidesPane pane = new HiddenSidesPane();
//
//            df.setData(pane);
//            pane.setLeft(menuBtn);
//            sidebarWindow.setCenter(pane);
//
//            setStatusBar(user);
//            sidebarWindow.setBottom(statusBar);
//        });

        UserDao user = new UserDao();
        user.setName("");
        user.setCounter(14);
        user.setRole("ROLE_ADMIN");
        user.setCenterName("IT-BD360");
        user.setTemplateName("RED-Template");
        statusBar = new StatusBar();
//        user.setIsLogin(true);
//        userRepo.save(user);

        FxControllerAndView<SidebarComponent, VBox> serverControllerView = fxWeaver.load(SidebarComponent.class);
        FxControllerAndView<DashboardView, BorderPane> dashboardControllerView = fxWeaver.load(DashboardView.class);
        HiddenSidesPane pane = new HiddenSidesPane();
        dashboardControllerView.getView().ifPresent(pane::setContent);

        SidebarComponent df = serverControllerView.getController();
        df.setData(pane);

        VBox menuBtn = serverControllerView.getView().orElse(null);
        pane.setLeft(menuBtn);
        sidebarWindow.setCenter(pane);

        setStatusBar(user);
        sidebarWindow.setBottom(statusBar);
    }

    private void setStatusBar(UserDao user) {
        statusBar.setText("");
        statusBar.getLeftItems().add(getLabelText(" User: " + user.getName()));
        statusBar.getLeftItems().add(new Separator(VERTICAL));
        statusBar.getLeftItems().add(getLabelText(" Counter: " + user.getCounter()));
        statusBar.getLeftItems().add(new Separator(VERTICAL));
        statusBar.getLeftItems().add(getLabelText(" Role: " + user.getRole()));
        statusBar.getLeftItems().add(new Separator(VERTICAL));
        statusBar.getLeftItems().add(getLabelText(" Center: " + user.getCenterName()));
        statusBar.getLeftItems().add(new Separator(VERTICAL));
        statusBar.getLeftItems().add(getLabelText(" Template: " + user.getTemplateName()));
        statusBar.getLeftItems().add(new Separator(VERTICAL));
//        projectTrRepo.findById(user.getProjectKey().longValue()).ifPresent(project -> {
//            statusBar.getLeftItems().add(getLabelText(" Project: " + project.getName()));
//        });
    }

    private Label getLabelText(String text) {
        Label label = new Label(text);
        label.setBackground(new Background(new BackgroundFill(Color.ORANGE, new CornerRadii(2), new Insets(0))));
        return label;
    }
}
