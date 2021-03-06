package ru.omel.rm.views.users;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import ru.omel.rm.data.entity.Role;
import ru.omel.rm.data.entity.User;
import ru.omel.rm.data.service.UserService;
import ru.omel.rm.views.main.MainView;

import java.util.Optional;

@Route(value = "profile/:userID?", layout = MainView.class)
//@Route(value = "demandto15/:demandID?/:action?(edit)", layout = MainView.class)
@PageTitle("Редактор профиля пользователя")
public class Profile extends Div implements BeforeEnterObserver {
    protected final String USER_ID = "userID";
    private boolean editMode = false;
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final User editUser = new User();
    private final BeanValidationBinder<User> userBinder = new BeanValidationBinder<>(User.class);
    private final TextField username;
    private final PasswordField password;
    private final PasswordField passwordVerify;
    private final Label notyfy;
    private final Label note;
    private final Label subnote;
    private final Button saveButton = new Button("Зарегистрировать");
    private User currentUser;

    public Profile(UserService userService,
                   PasswordEncoder passwordEncoder,
                   Component... components) {
        super(components);
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;

        notyfy = new Label("");
        notyfy.setVisible(false);
        notyfy.addClassName("warning");
        username = new TextField("Логин");
        username.setReadOnly(true);
        password = new PasswordField("Пароль");
        passwordVerify = new PasswordField("Проверка пароля");
        userBinder.bindInstanceFields(this);

        password.addValueChangeListener(e -> saveButtonActive());
        passwordVerify.addValueChangeListener(e -> saveButtonActive());

        saveButton.addClickListener(event -> registration());

        Button reset = new Button("Отменить");
        reset.addClickListener(event -> {
            // clear fields by setting null
            userBinder.readBean(null);
            notyfy.setVisible(false);
            if(currentUser.getRoles().contains(Role.ADMIN)) {
                if(UI.getCurrent()!=null) UI.getCurrent().navigate("/users");
            } else {
                if(UI.getCurrent()!=null) UI.getCurrent().navigate("/meters");
            }
        });

        HorizontalLayout buttonBar = new HorizontalLayout();
        buttonBar.setClassName("w-full flex-wrap bg-contrast-5 py-s px-l");
        buttonBar.setSpacing(true);
        reset.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
        saveButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        buttonBar.add(saveButton, reset);

        FormLayout userForm = new FormLayout();
        userForm.add(username, password, passwordVerify);
        note = new Label("Для смены пароля введите новый пароль дважды и сохраните. ");
        note.getElement().getStyle().set("color", "red");
        note.getElement().getStyle().set("font-size", "1.5em");
        note.setHeight("1em");
        subnote = new Label("В качестве логина и пароля рекомендуется " +
                "использовать только латинские буквы, цифры и знаки: @ # _ & . ");
        subnote.getElement().getStyle().set("color", "black");
        subnote.getElement().getStyle().set("font-size", "0.8em");
        subnote.setHeight("0.9em");
        VerticalLayout notesLayout = new VerticalLayout();
        notesLayout.add(note, subnote);
        add(notyfy, notesLayout, userForm, buttonBar);
        this.getElement().getStyle().set("margin", "15px");
        saveButtonActive();
    }

    private void registration() {
        if(verifyFill()) {
            if (password.getValue().equals(passwordVerify.getValue())) {
                User existUser = userService.findByUsername(username.getValue());
                userBinder.writeBeanIfValid(editUser);
                if(!editMode){
                    editUser.setPassword(this.passwordEncoder.encode(password.getValue()));
                } else {
                    if(password.getValue().equals(""))
                        editUser.setPassword(existUser.getPassword());
                    else
                        editUser.setPassword(this.passwordEncoder.encode(password.getValue()));
                }
                userService.update(this.editUser);
                if(currentUser.getRoles().contains(Role.ADMIN)) {
                    if(UI.getCurrent()!=null) UI.getCurrent().navigate("/users");
                } else {
                    if(UI.getCurrent()!=null) UI.getCurrent().navigate("/meters");
                }
            } else {
                notyfy.setText("Пароли не совпадают");
                notyfy.setVisible(true);
            }
        }
    }

    private boolean verifyFill() {
        return true;
    }

    private void saveButtonActive() {
        saveButton.setEnabled(
                (editMode || !password.getValue().equals("")) &&
                (editMode || !passwordVerify.getValue().equals(""))
        );
    }

    @Override
    public void beforeEnter(BeforeEnterEvent event) {
        Optional<Long> userdId = event.getRouteParameters().getLong(USER_ID);
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        if (username != null) {
            currentUser =  this.userService.findByUsername(username);
            if (currentUser != null) {
                if(currentUser.getRoles().contains(Role.ADMIN)) {
                    if(userdId.isPresent()) {
                        if(userService.findById(userdId.get()).isPresent()) {
                            populateForm((userService.findById(userdId.get())).get());
                            return;
                        }
                    }
                }
                if (currentUser.getUsername().
                        equals(SecurityContextHolder.getContext().getAuthentication().getName())) {
                    populateForm(currentUser);
                } else {
                    Notification.show("У Вас нет прав :(", 3000,
                            Notification.Position.BOTTOM_START);
                    clearForm();
                }
            } else {
                clearForm();
            }
        }
    }

    private void clearForm() {
        editMode = false;
        populateForm(null);
        notyfy.setVisible(false);
    }

    private void populateForm(User value) {
        if(value != null) {
            editMode = true;
            editUser.setId(value.getId());
            editUser.setUsername(value.getUsername());
            editUser.setPassword(value.getPassword());
            editUser.setActive(value.getActive());
            editUser.setRoles(value.getRoles());
//            editUser.setFio(value.getFio());
//            editUser.setContact(value.getContact());
            saveButton.setText("Сохранить");
            note.setVisible(false);
        }
        userBinder.readBean(editUser);
        password.setValue("");
        passwordVerify.setValue("");
    }

}
