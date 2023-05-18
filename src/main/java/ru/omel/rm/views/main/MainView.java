package ru.omel.rm.views.main;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.RouterLink;
import org.springframework.security.core.context.SecurityContextHolder;
import ru.omel.rm.data.entity.Role;
import ru.omel.rm.data.entity.User;
import ru.omel.rm.data.service.UserService;
import ru.omel.rm.views.admin.DbLoad;
import ru.omel.rm.views.admin.ListUsers;
import ru.omel.rm.views.users.Profile;

/**
 * The main view is a top-level placeholder for other views.
 */
//@PWA(name = "Личный кабинет", shortName = "Кабинет")
public class MainView extends AppLayout {
    private H1 viewTitle;
    private final UserService userService;

    private RouterLink meter;
    private RouterLink profile;
    private RouterLink admin;
    private RouterLink dbload;

    public static class MenuItemInfo {

        private final String text;
        private final String iconClass;
        private final Class<? extends Component> view;

        public MenuItemInfo(String text, String iconClass, Class<? extends Component> view) {
            this.text = text;
            this.iconClass = iconClass;
            this.view = view;
        }

        public String getText() {
            return text;
        }

        public String getIconClass() {
            return iconClass;
        }

        public Class<? extends Component> getView() {
            return view;
        }

    }

    public MainView(UserService userService) {
        setPrimarySection(Section.DRAWER);
        addToNavbar(true, createHeaderContent());
        addToDrawer(createDrawerContent());
        this.userService = userService;
    }

    private Component createHeaderContent() {
        DrawerToggle toggle = new DrawerToggle();
        toggle.addClassName("text-secondary");
        toggle.addThemeVariants(ButtonVariant.LUMO_CONTRAST);
        toggle.getElement().setAttribute("aria-label", "Menu toggle");
        toggle.getElement().setAttribute("title","Меню");

        viewTitle = new H1();
        viewTitle.addClassNames("m-0", "text-l");

        Header header = new Header(toggle, viewTitle);
        header.addClassNames("bg-base", "border-b", "border-contrast-10", "box-border", "flex", "h-xl", "items-center",
                "w-full");
        return header;
    }

    private Component createDrawerContent() {
        HorizontalLayout main = new HorizontalLayout();
        Image logo = new Image("images/logo.png", "Омскэлектро");
        logo.setMaxWidth("20%");
        logo.getElement().getStyle().set("padding", "10px");
        H2 appName = new H2("Показания счётчиков");
        main.add(logo,appName);
        appName.addClassNames("flex", "items-center", "h-xl", "m-0", "px-m", "text-m");

        com.vaadin.flow.component.html.Section section = new com.vaadin.flow.component.html.Section(
                main, createNavigation(), createFooter());
        section.addClassNames("flex", "flex-col", "items-stretch", "max-h-full", "min-h-full");
        return section;
    }

    private Nav createNavigation() {
        Nav nav = new Nav();
        nav.addClassNames("border-b", "border-contrast-10", "flex-grow", "overflow-auto");
        nav.getElement().setAttribute("aria-labelledby", "views");

        meter = createLinksList();
        nav.add(meter);
        profile = createLinksProfile();
        nav.add(profile);
        admin = createLinksAdmin();
        nav.add(admin);
        dbload = createLinksDbLoad();
        nav.add(dbload);
        Button button = new Button(new Icon(VaadinIcon.EXIT));
        button.addClickListener(event -> {
            if(getUI().isPresent()){
                UI ui = getUI().get();
                ui.getSession().getSession().invalidate();
                if(ui.getCurrent()!=null) ui.getCurrent().navigate("/");
            }
        });
        button.getElement().setAttribute("title","Выход");
        button.setText("ВЫХОД");
        button.getElement().getStyle().set("margin", "10px");
        nav.add(button);
        return nav;
    }

    private RouterLink createLinksList() {
        MenuItemInfo menuItem = new MenuItemInfo("Показания счётчиков", "globe-solid", DtoView.class);
        return createLink(menuItem);
    }

    private RouterLink createLinksAdmin() {
        MenuItemInfo menuItem = new MenuItemInfo("Список пользователей", "globe-solid", ListUsers.class);
        return createLink(menuItem);
    }

    private RouterLink createLinksDbLoad() {
        MenuItemInfo menuItem = new MenuItemInfo("Загрузка данных", "globe-solid", DbLoad.class);
        return createLink(menuItem);
    }
    private RouterLink createLinksProfile() {
        MenuItemInfo menuItem = new MenuItemInfo("Смена пароля", "la la-globe", Profile.class);
        return createLink(menuItem);
    }

    private static RouterLink createLink(MenuItemInfo menuItemInfo) {
        RouterLink link = new RouterLink();
        link.addClassNames("flex", "mx-s", "p-s", "relative", "text-secondary");
        link.setRoute(menuItemInfo.getView());

        Span icon = new Span();
        icon.addClassNames("me-s", "text-l");
        if (!menuItemInfo.getIconClass().isEmpty()) {
            icon.addClassNames(menuItemInfo.getIconClass());
        }

        Span text = new Span(menuItemInfo.getText());
        text.addClassNames("font-medium", "text-s");

        link.add(icon, text);
        return link;
    }

    private Footer createFooter() {
        Footer layout = new Footer();
        layout.addClassNames("flex", "items-center", "my-s", "px-m", "py-xs");

        Label support = new Label("По всем вопросам звонить по тел.: xx-xx-xx.");
        support.addClassNames("flex", "mx-s", "p-s", "relative", "text-secondary");
        support.getElement().getStyle().set("font-size","0.8em");
        layout.add(support);

        return layout;
    }

    @Override
    protected void afterNavigation() {
        super.afterNavigation();
        viewTitle.setText(getCurrentPageTitle());
        User currentUser = userService.findByUsername(
                SecurityContextHolder.getContext().getAuthentication().getName());
        if (currentUser != null) {
            Role role = currentUser.getRoles().contains(Role.ADMIN) ?
                    Role.ADMIN :
                    Role.USER;
            switch (role) {
                case ADMIN:
                    meter.setVisible(false);
                    profile.setVisible(true);
                    admin.setVisible(true);
                    dbload.setVisible(true);
                    break;
                case USER:
                    meter.setVisible(true);
                    profile.setVisible(true);
                    admin.setVisible(false);
                    dbload.setVisible(false);
                    break;
            }
        }
    }

    private String getCurrentPageTitle() {
        PageTitle title = getContent().getClass().getAnnotation(PageTitle.class);
        return title == null ? "" : title.value();
    }
}
