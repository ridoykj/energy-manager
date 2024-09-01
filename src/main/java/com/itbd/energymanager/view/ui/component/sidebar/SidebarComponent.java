package com.itbd.energymanager.view.ui.component.sidebar;

import com.itbd.energymanager.dao.UserDao;
import com.itbd.energymanager.repos.UserRepo;
import com.itbd.energymanager.view.ui.manager.module.accounts.bill.BillView;
import com.itbd.energymanager.view.ui.manager.module.config.area.AreaView;
import com.itbd.energymanager.view.ui.manager.module.config.unit.UnitPriceView;
import com.itbd.energymanager.view.ui.manager.module.home.DashboardView;
import com.itbd.energymanager.view.ui.manager.module.inventory.meter.MeterView;
import com.itbd.energymanager.view.ui.manager.module.user.customer.CustomerView;
import com.itbd.energymanager.view.ui.manager.module.user.employee.EmployeeView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Side;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import lombok.extern.slf4j.Slf4j;
import net.rgielen.fxweaver.core.FxControllerAndView;
import net.rgielen.fxweaver.core.FxWeaver;
import net.rgielen.fxweaver.core.FxmlView;
import org.controlsfx.control.HiddenSidesPane;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@FxmlView("/view/ui/component/sidebar/sidebar_component.fxml")
@Slf4j
public class SidebarComponent {

    private final FxWeaver fxWeaver;
    private final UserRepo userRepo;
    @FXML
    private ListView<Label> lv_menu;
    private HiddenSidesPane hiddenSidesPane;

    public SidebarComponent(FxWeaver fxWeaver, UserRepo userRepo) {
        this.fxWeaver = fxWeaver;
        this.userRepo = userRepo;
    }

    @FXML
    public void initialize() {
//        UserModel userM = userRepo.findAll().stream().findFirst().get();
        UserDao userM = new UserDao();
        userM.setRole("ADMIN");
        Map<String, Class<?>> menuMap = new HashMap<>();
        menuMap.put("Dashboard", DashboardView.class);
        menuMap.put("Area", AreaView.class);
        menuMap.put("Unit(KW/h)", UnitPriceView.class);
        menuMap.put("Bill", BillView.class);
//        menuMap.put("Bill Collection", BillCollectionView.class);
        menuMap.put("Employees", EmployeeView.class);
        menuMap.put("Customers", CustomerView.class);
        menuMap.put("Meter", MeterView.class);

        List<String> menuItem = switch (userM.getRole()) {
            case "ADMIN" -> List.of("Dashboard", "Area", "Unit(KW/h)", "Bill", "Employees", "Customers", "Meter");
            case "SCANNER" -> List.of("Employee");
            case "DISPLAY" -> List.of("Customer");
            default -> List.of();
        };
        List<Label> menuBtn = menuItem.stream().map(this::menuButtonManager).toList();
        ObservableList<Label> data = FXCollections.observableArrayList(menuBtn);
        lv_menu.setItems(data);
//        loadUI(DashboardView.class);
        lv_menu.getSelectionModel().selectedItemProperty().addListener((o, oldVal, newVal) -> loadUI(menuMap.get(newVal.getText())));
    }


    @FXML
    void pinedSideBar(MouseEvent event) {
        Side side = Side.LEFT;
        if (hiddenSidesPane.getPinnedSide() != null) {
//            setText(text + " (unpinned)");
            hiddenSidesPane.setPinnedSide(null);
        } else {
//            setText(text + " (pinned)");
            hiddenSidesPane.setPinnedSide(side);
        }
    }

    private Label menuButtonManager(String btnTitle) {
        FxControllerAndView<SideBarBtn, Label> serverControllerView = fxWeaver.load(SideBarBtn.class);
        Label menuBtn = serverControllerView.getView().get();
        SideBarBtn df = serverControllerView.getController();
        df.setData(btnTitle);
        return menuBtn;
    }

    private void loadUI(Class<?> controllerClass) {
        Pane root = fxWeaver.loadView(controllerClass);
        root.setPrefHeight(0);
        root.setPrefWidth(0);
        hiddenSidesPane.setContent(root);
    }

    public void setData(HiddenSidesPane centerPane) {
        this.hiddenSidesPane = centerPane;
    }
}
