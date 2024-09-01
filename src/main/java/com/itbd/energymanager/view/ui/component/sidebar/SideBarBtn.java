package com.itbd.energymanager.view.ui.component.sidebar;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import lombok.extern.slf4j.Slf4j;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.stereotype.Component;

@Component
@FxmlView("/view/ui/component/sidebar/sidebar_btn.fxml")
@Slf4j
public class SideBarBtn {
    @FXML
    private Label lb_menu;

    @FXML
    public void initialize() {

    }

    public void setData(String btnTitle) {
        lb_menu.setText(btnTitle);
    }
}
