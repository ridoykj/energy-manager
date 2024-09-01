package com.itbd.energymanager.view;


import com.itbd.energymanager.repos.UserRepo;
import com.itbd.energymanager.view.ui.manager.MainLayoutView;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import lombok.extern.slf4j.Slf4j;
import net.rgielen.fxweaver.core.FxWeaver;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@FxmlView("/view/login_view.fxml")
@Slf4j
public class LoginView {
    public static Image serverHostImage = new Image("assets/icon/module/login/cloud.png");
    @Autowired
    protected UserRepo userRepo;
//    @Autowired
//    private UserApiService userApiService;

    @Autowired
    private FxWeaver fxWeaver;
    @FXML
    private TextField tx_user_id;
    @FXML
    private PasswordField tx_user_pass;
    @FXML
    private Button btn_login;

    @FXML
    private Button btn_host_server;

    @FXML
    public void initialize() {
    }

    @FXML
    void loginEvent(ActionEvent event) {
        loginAction();
    }

    @FXML
    void textFieldLoginEvent(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            loginAction();
        }
    }

    private void loginAction() {
        //TODO: Login Auth check [with websocket]
//        UserModel user = userApiService.userLogin(tx_user_id.getText(), tx_user_pass.getText());
        boolean dev = true;
        if (dev) {
//        if (Objects.nonNull(user)) {

            Stage stage = new Stage();
            stage.setScene(new Scene(fxWeaver.loadView(MainLayoutView.class)));
            stage.setTitle("Energy Manager - Cost Calculator");
            stage.getIcons().add(new Image("assets/icon/appicon/energy.png"));
            stage.show();
            stage.setOnCloseRequest(eventE -> {
                userRepo.findAll().stream().findFirst().ifPresent(userE -> {
                    userE.setIsLogin(false);
                    userRepo.save(userE);
                });
            });

            Stage loginStage = (Stage) btn_login.getScene().getWindow();
            // do what you have to do
            loginStage.close();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("System Login");
            alert.setHeaderText("Login: [Failed]");
            alert.setContentText("Please check your user, password or server connection...!");
            alert.showAndWait();
            log.error("Can't login - user: {}", tx_user_id.getText());
        }
    }
}
