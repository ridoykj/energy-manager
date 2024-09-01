package com.itbd.energymanager;

import com.itbd.energymanager.repos.UserRepo;
import com.itbd.energymanager.view.LoginView;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.stage.Stage;
import javafx.util.Duration;
import lombok.extern.slf4j.Slf4j;
import net.rgielen.fxweaver.core.FxWeaver;
import org.controlsfx.control.Notifications;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class StageInitializer implements ApplicationListener<JavaFXApplication.StageReadyEvent> {
    private final String applicationTitle;
    private final FxWeaver fxWeaver;

    private final UserRepo userRepo;

    public StageInitializer(@Value("${spring.application.ui.title}") String applicationTitle,
                            FxWeaver fxWeaver, UserRepo userRepo) {
        this.applicationTitle = applicationTitle;
        this.fxWeaver = fxWeaver;
        this.userRepo = userRepo;
    }

    @Override
    public void onApplicationEvent(JavaFXApplication.StageReadyEvent event) {
        Stage stage = event.getStage();
//        stage.setScene(new Scene(fxWeaver.loadView(MainViewController.class), 800, 600));
        KeyCombination combination = new KeyCodeCombination(KeyCode.H, KeyCodeCombination.CONTROL_DOWN);
        Scene scene = new Scene(fxWeaver.loadView(LoginView.class));
        scene.setOnKeyPressed(action -> {
            if (combination.match(action)) {
                log.info("CTRL + H" + action.getCode().toString());
            }
        });
        stage.setScene(scene);
        stage.setTitle(applicationTitle);
        stage.setResizable(false);
        stage.getIcons().add(new Image("assets/icon/appicon/energy.png"));
        stage.setOnCloseRequest(eventE -> {
            userRepo.findAll().stream().findFirst().ifPresent(user -> {
                user.setIsLogin(false);
                userRepo.save(user);
            });
//            disableCloseBtn();
//            eventE.consume();
        });
        stage.show();
    }

    public void disableCloseBtn() {
        ImageView warningIcon = new ImageView("assets/icon/res/warning.png");
        warningIcon.setFitHeight(50);
        warningIcon.setFitWidth(50);

        Notifications notifications = Notifications.create()
                .title("Alert")
                .text("This window cannot be closed")
                .graphic(warningIcon)
                .hideAfter(Duration.seconds(2))
                .position(Pos.TOP_RIGHT)
                .hideCloseButton()
                .onAction(event -> System.out.println("Clicked on Notification"));
        notifications.show();
    }
}
