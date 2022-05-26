package ru.omel.rm.views.main;

import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.ColumnTextAlign;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.*;
import com.vaadin.flow.spring.data.VaadinSpringDataHelpers;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.context.SecurityContextHolder;
import ru.omel.rm.data.dto.DtoPokPu;
import ru.omel.rm.data.entity.*;
import ru.omel.rm.data.service.*;
import ru.omel.rm.views.admin.ListUsers;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@PageTitle("Показания счетчиков")
@Route(value = "meters", layout = MainView.class)
@RouteAlias(value = "", layout = MainView.class)
public class DtoView extends Div implements BeforeEnterObserver {
    private final TextField filterId = new TextField();
    private final TextField filterText = new TextField();
    private final Button clearFilter = new Button(new Icon(VaadinIcon.ERASER));
    private final Grid<DtoPokPu> gridPok = new Grid<>(DtoPokPu.class, false);

    private List<DtoPokPu> pokPuDtoList = new LinkedList<>();
    private final DogService dogService;
    private final PuService puService;
    private final PokService pokService;

    private final Label tNumber = new Label();
    private final Label tNum = new Label();
    private final Label tName = new Label();
    private final Label tInn = new Label();


    private final UserService userService;

    private User currentUser;
    //@Autowired
    public DtoView(UserService userService
            , DogService dogService
            , PuService puService
            , PokService pokService) {
        HorizontalLayout filterLayout = new HorizontalLayout();
        HorizontalLayout header = new HorizontalLayout();
        VerticalLayout vlLabel = new VerticalLayout();
        VerticalLayout vlDog = new VerticalLayout();
        Label lNumber = new Label("№ договора в системе ООО ОЭСК");
        Label lNum = new Label("№ договора в системе АО Омскэлектро");
        Label lName = new Label("Наименование договора");
        Label lInn = new Label("ИНН");
        vlLabel.add(lNumber,lNum,lName,lInn);
        vlDog.add(tNumber,tNum,tName,tInn);
        header.add(vlLabel,vlDog);
        filterLayout.getElement().getStyle().set("margin", "10px");
        this.userService = userService;
        this.dogService = dogService;
        this.pokService = pokService;
        this.puService = puService;
        addClassNames("master-detail-view", "flex", "flex-col", "h-full");

        // Configure Grid
        Collection<Button> editButtons = Collections.newSetFromMap(new WeakHashMap<>());

        gridSetting(null,"");

        pokPuDtoList = DataHelper.createTable("1-0073",
                dogService, puService, pokService);

        gridPok.addColumn(DtoPokPu::getObjName).setHeader("Объект").setAutoWidth(true);
        gridPok.addColumn(DtoPokPu::getObjAddress).setHeader("Адресс").setAutoWidth(true);
        gridPok.addColumn(DtoPokPu::getTypeDevice).setHeader("Тип ПУ")
                .setAutoWidth(true).setTextAlign(ColumnTextAlign.CENTER);
        gridPok.addColumn(DtoPokPu::getNumDevice)
                .setHeader("№ ПУ")
                .setSortable(true)
                .setAutoWidth(true)
                .setTextAlign(ColumnTextAlign.CENTER);
        VerticalLayout ltz = new VerticalLayout();
        ltz.add(new Label("Тарифная"), new Label("зона"));
        gridPok.addColumn(DtoPokPu::getRatio).setHeader("Коэф.")
                .setAutoWidth(true).setTextAlign(ColumnTextAlign.CENTER);
        gridPok.addColumn(DtoPokPu::getDate).setHeader("Дата")
                .setAutoWidth(true).setTextAlign(ColumnTextAlign.CENTER);
//        gridPok.addColumn(DtoPokPu::getTzona).setHeader("Тарифная зона").setAutoWidth(true);
        gridPok.addColumn(DtoPokPu::getTzona).setHeader(ltz)
                .setAutoWidth(true).setTextAlign(ColumnTextAlign.CENTER);
        gridPok.addColumn(DtoPokPu::getVid).setHeader("Вид")
                .setAutoWidth(true).setTextAlign(ColumnTextAlign.CENTER);
        gridPok.addColumn(DtoPokPu::getMeter).setHeader("Показания")
                .setAutoWidth(true).setTextAlign(ColumnTextAlign.END);
        gridPokSetting();
        Collections.sort(pokPuDtoList);
        gridPok.setItems(pokPuDtoList);
        gridPok.addThemeVariants(GridVariant.LUMO_COLUMN_BORDERS);

        filterId.setLabel("Поиск по номеру задачи");
        filterId.setHelperText("После ввода номера нажмите Enter");
        filterId.setPlaceholder("Номер заявки");
        filterId.addKeyDownListener(Key.ENTER, event -> {
            filterText.setValue("");
            if(filterId.getValue()!=null){
                try {
                    gridSetting(Long.valueOf(filterId.getValue()),"");
                } catch (Exception e) {
                    Notification notification = new Notification(
                            "Задачи с таким номером не найдено", 5000,
                            Notification.Position.MIDDLE);
                    notification.open();
                    gridSetting(null,"");
                }
            } else {
                gridSetting(null,"");
            }
        });
        filterText.setLabel("Поиск по содержимому полей Заявитель, Объект и Адрес");
        filterText.setHelperText("После ввода текста нажмите Enter");
        filterText.setPlaceholder("Любой текст");
        filterText.setWidthFull();
        filterText.addKeyDownListener(Key.ENTER,event -> {
            filterId.setValue("");
            if(filterText.getValue()!=null){
                gridSetting(null,filterText.getValue());
            } else {
                gridSetting(null,"");
            }
        });
        clearFilter.setText("Очистить фильтр");
        clearFilter.addClickListener(event -> {
            filterId.setValue("");
            filterText.setValue("");
            gridSetting(null,"");
        });

        filterLayout.add(filterId,filterText,clearFilter);
        filterLayout.setAlignItems(FlexComponent.Alignment.BASELINE);
//        grid.getElement().setAttribute("title","кликните дважды для открытия заявки");
        TextField space = new TextField();
        space.setWidthFull();
        space.setReadOnly(true);

        add(header,gridPok,space);
    }

    private void gridPokSetting() {
        User currentUser =  this.userService.findByUsername(
                SecurityContextHolder.getContext().getAuthentication().getName()
        );
        gridPok.setPageSize(20);
        pokPuDtoList = DataHelper.createTable(currentUser.getUsername(),
                dogService, puService, pokService);
        if(dogService.findByAbNum(currentUser.getUsername()).isPresent()){
            Dog currentDog = dogService.findByAbNum(currentUser.getUsername()).get();
            tNumber.setText(currentDog.getAbNumgp());
            tNum.setText(currentDog.getAbNum());
            tName.setText(currentDog.getAbName());
            tInn.setText(currentDog.getInn());
        }

    }

    private void gridSetting(Long id, String text) {
        // Определим текущего пользователя
        User currentUser =  this.userService.findByUsername(
                SecurityContextHolder.getContext().getAuthentication().getName()
        );
        currentUser.setVisitDate(LocalDateTime.now());
        userService.update(currentUser);
        gridPok.setPageSize(20);
//        grid.setSortableColumns("id","object","address");
        // вывод всех заявок доступных пользователю
    }

    @Override
    public void beforeEnter(BeforeEnterEvent event) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        if (username != null) {
            currentUser =  this.userService.findByUsername(username);
            if (currentUser != null) {
                if(currentUser.getRoles().contains(Role.ADMIN)) {
                    UI.getCurrent().navigate("/users");
//                    UI.getCurrent().navigate(ListUsers.class);
                } else {
                    UI.getCurrent().navigate("/meters");
                }
            }
        }

    }
}