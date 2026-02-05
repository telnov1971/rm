package ru.omel.rm.views.admin;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import ru.omel.rm.data.entity.*;
import ru.omel.rm.data.service.*;
import ru.omel.rm.views.main.MainView;

import java.io.*;

@PageTitle("Загрузка данных")
@Route(value = "dbload", layout = MainView.class)
public class DbLoad extends Div implements BeforeEnterObserver {

    private final UserService userService;

    @Autowired
    public DbLoad(UserService userService,
                  DbLoadService dbLoadService) {
        this.userService = userService;
        VerticalLayout verticalLayout = new VerticalLayout();
        Label greeting = new Label("Здесь можно загрузить данные в базу");
        Button button = new Button("Загрузить");

        button.addClickListener(e -> {
            try {
                dbLoadService.loadData();
            } catch (FileNotFoundException ex) {
                throw new RuntimeException(ex);
            }
        });

        TextField bingo = new TextField("Ждём...");
        TextField textPok = new TextField("Показаний:");
        TextField textPu = new TextField("Счётчиков:");
        TextField textDog = new TextField("Договоров:");
        verticalLayout.add(greeting, button, textDog, textPu, textPok, bingo);
        add(verticalLayout);
    }

    @Override
    public void beforeEnter(BeforeEnterEvent beforeEnterEvent) {
        User currentUser =  this.userService.findByUsername(
                SecurityContextHolder.getContext().getAuthentication().getName()
        );
        if(!currentUser.getRoles().contains(Role.ADMIN) &&
                (UI.getCurrent()!=null)) {UI.getCurrent().navigate("/meters");
        }
    }

}
