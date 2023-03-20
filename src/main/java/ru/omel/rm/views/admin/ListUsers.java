package ru.omel.rm.views.admin;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.data.provider.ListDataProvider;
import com.vaadin.flow.router.*;
import org.springframework.security.core.context.SecurityContextHolder;
import ru.omel.rm.data.entity.Role;
import ru.omel.rm.data.entity.User;
import ru.omel.rm.data.service.UserService;
import ru.omel.rm.views.main.MainView;
import ru.omel.rm.views.users.Profile;

import java.util.*;

@PageTitle("Показания счетчиков")
@Route(value = "users", layout = MainView.class)
public class ListUsers  extends Div implements BeforeEnterObserver {
    private final UserService userService;
    private List<User> users = new ArrayList<>();
    private Grid<User> grid = new Grid<>(User.class, false);
    private ListDataProvider<User> userDataProvider;

    public ListUsers(UserService userService) {
        this.userService = userService;
        grid.setHeightByRows(true);
        Collection<Button> editButtons = Collections.newSetFromMap(new WeakHashMap<>());
        Grid.Column<User> columnUsername =
                grid.addColumn(User::getUsername)
                        .setHeader("Пользователь")
                        .setResizable(true)
                        .setAutoWidth(true);
        Grid.Column<User> editorColumn = grid.addComponentColumn(user -> {
            Button edit = new Button(new Icon(VaadinIcon.EDIT));
            edit.addClassName("edit");
            edit.getElement().setAttribute("title","открыть");
            edit.addClickListener(e -> {
                UI.getCurrent().navigate(Profile.class, new RouteParameters("userID",
                        String.valueOf(user.getId())));
            });
            editButtons.add(edit);
            return edit;

        }).setAutoWidth(true).setResizable(true);
        grid.setAllRowsVisible(true);
        grid.setPageSize(10);
        add(grid);
    }

    @Override
    public void beforeEnter(BeforeEnterEvent event) {
        User currentUser =  this.userService.findByUsername(
                SecurityContextHolder.getContext().getAuthentication().getName()
        );
        if(!currentUser.getRoles().contains(Role.ADMIN)) {
            if(UI.getCurrent()!=null) UI.getCurrent().navigate("/meters");
        } else {
            users = userService.findAll();
            grid.setItems(users);
        }
    }
}
