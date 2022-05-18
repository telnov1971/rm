package ru.omel.rm.views.admin;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.router.*;
import org.springframework.security.core.context.SecurityContextHolder;
import ru.omel.rm.data.entity.Role;
import ru.omel.rm.data.entity.User;
import ru.omel.rm.data.service.UserService;
import ru.omel.rm.views.main.MainView;

@PageTitle("Показания счетчиков")
@Route(value = "users", layout = MainView.class)
public class ListUsers  extends Div implements BeforeEnterObserver {
    private final UserService userService;

    public ListUsers(UserService userService) {
        this.userService = userService;
    }

    @Override
    public void beforeEnter(BeforeEnterEvent event) {
        User currentUser =  this.userService.findByUsername(
                SecurityContextHolder.getContext().getAuthentication().getName()
        );
        if(!currentUser.getRoles().contains(Role.ADMIN)) {
            UI.getCurrent().navigate("/");
        }
    }
}
