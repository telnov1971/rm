package ru.omel.rm.views.main;

import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;
import com.vaadin.flow.router.RouteParameters;
import com.vaadin.flow.spring.data.VaadinSpringDataHelpers;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.context.SecurityContextHolder;
import ru.omel.rm.data.dto.DtoPokPu;
import ru.omel.rm.data.entity.Demand;
import ru.omel.rm.data.entity.DemandType;
import ru.omel.rm.data.entity.Role;
import ru.omel.rm.data.entity.User;
import ru.omel.rm.data.service.*;
import ru.omel.rm.views.demandedit.DemandEditTemporal;
import ru.omel.rm.views.demandedit.DemandEditTo15;
import ru.omel.rm.views.demandedit.DemandEditTo150;
import ru.omel.rm.views.demandedit.DemandEditeGeneral;
import ru.omel.rm.views.support.ViewHelper;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@PageTitle("Показания счетчиков")
@Route(value = "meters", layout = MainView.class)
@RouteAlias(value = "", layout = MainView.class)
public class DtoView extends Div {
    private final TextField filterId = new TextField();
    private final TextField filterText = new TextField();
    private Select<DemandType> demandTypeSelect;
    private final Grid.Column<Demand> demanderColumn;
    private final Button clearFilter = new Button(new Icon(VaadinIcon.ERASER));
    private final Grid<Demand> grid = new Grid<>(Demand.class, false);
    private final Grid<DtoPokPu> gridPok = new Grid<>(DtoPokPu.class, false);

    private List<DtoPokPu> pokPuDtoList = new LinkedList<>();
    private final DogService dogService;
    private final PuService puService;
    private final PokService pokService;

    private final DemandService demandService;
    private final UserService userService;
    private final DemandTypeService demandTypeService;

    //@Autowired
    public DtoView(DemandService demandService
            , UserService userService
            , DemandTypeService demandTypeService
            , DogService dogService
            , PuService puService
            , PokService pokService) {
        HorizontalLayout filterLayout = new HorizontalLayout();
        filterLayout.getElement().getStyle().set("margin", "10px");
        this.demandService = demandService;
        this.userService = userService;
        this.demandTypeService = demandTypeService;
        this.dogService = dogService;
        this.pokService = pokService;
        this.puService = puService;
        addClassNames("master-detail-view", "flex", "flex-col", "h-full");

        demandTypeSelect = ViewHelper.createSelect(DemandType::getName, demandTypeService.findAll(),
                "Тип заявки", DemandType.class);

        // Configure Grid
        Collection<Button> editButtons = Collections.newSetFromMap(new WeakHashMap<>());
        grid.addComponentColumn(demand -> {
            Button edit = new Button(new Icon(VaadinIcon.EDIT));
//            edit.setText("ОТКРЫТЬ");
            edit.addClassName("edit");
            edit.getElement().setAttribute("title","открыть");
            edit.addClickListener(event -> {
                if (Objects.equals(demand.getDemandType().getId(), DemandType.TO15)) {
                    UI.getCurrent().navigate(DemandEditTo15.class, new RouteParameters("demandID",
                            String.valueOf(demand.getId())));
                }
                if (Objects.equals(demand.getDemandType().getId(), DemandType.TO150)) {
                    UI.getCurrent().navigate(DemandEditTo150.class, new RouteParameters("demandID",
                            String.valueOf(demand.getId())));
                }
                if (Objects.equals(demand.getDemandType().getId(), DemandType.TEMPORAL)) {
                    UI.getCurrent().navigate(DemandEditTemporal.class, new RouteParameters("demandID",
                            String.valueOf(demand.getId())));
                }
                if (Objects.equals(demand.getDemandType().getId(), DemandType.GENERAL)) {
                    UI.getCurrent().navigate(DemandEditeGeneral.class, new RouteParameters("demandID",
                            String.valueOf(demand.getId())));
                }
            });
            edit.setEnabled(true);
            editButtons.add(edit);
            return edit;
        }).setResizable(true).setAutoWidth(true);
        grid.addColumn("id").setResizable(true).setAutoWidth(true).setHeader("ID");
        demanderColumn = grid.addColumn("demander").setHeader("Заявитель")
                .setResizable(true).setAutoWidth(true);
        grid.addColumn("status.name").setResizable(true).setAutoWidth(true).setHeader("Статус");
        grid.addColumn("object").setHeader("Объект")
                .setResizable(true).setAutoWidth(true);
        grid.addColumn("address").setHeader("Адрес объекта")
                .setResizable(true).setAutoWidth(true);
        grid.addColumn("demandType.name").setResizable(true).setAutoWidth(true).setHeader("Тип");
        grid.addComponentColumn(demand -> new Label(demand.getCreateDate()
                        .format(DateTimeFormatter.ofPattern("uuuu-MM-dd _ HH:mm:ss"))))
                .setHeader("Дата и время").setResizable(true).setAutoWidth(true);

        gridSetting(null,"");
        grid.addThemeVariants(GridVariant.LUMO_NO_BORDER);
        grid.setHeightFull();

        grid.addItemDoubleClickListener(event ->{
            if (Objects.equals(event.getItem().getDemandType().getId(), DemandType.TO15)) {
                UI.getCurrent().navigate(DemandEditTo15.class, new RouteParameters("demandID",
                        String.valueOf(event.getItem().getId())));
            }
            if (Objects.equals(event.getItem().getDemandType().getId(), DemandType.TO150)) {
                UI.getCurrent().navigate(DemandEditTo150.class, new RouteParameters("demandID",
                        String.valueOf(event.getItem().getId())));
            }
            if (Objects.equals(event.getItem().getDemandType().getId(), DemandType.TEMPORAL)) {
                UI.getCurrent().navigate(DemandEditTemporal.class, new RouteParameters("demandID",
                        String.valueOf(event.getItem().getId())));
            }
            if (Objects.equals(event.getItem().getDemandType().getId(), DemandType.GENERAL)) {
                UI.getCurrent().navigate(DemandEditeGeneral.class, new RouteParameters("demandID",
                        String.valueOf(event.getItem().getId())));
            }
        });

        pokPuDtoList = DataHelper.createTable("1-0073",
                dogService, puService, pokService);

        gridPok.addColumn(DtoPokPu::getObjName).setHeader("Объект").setAutoWidth(true);
        gridPok.addColumn(DtoPokPu::getObjAddress).setHeader("Адресс").setAutoWidth(true);
        gridPok.addColumn(DtoPokPu::getTypeDevice).setHeader("Тип ПУ").setAutoWidth(true);
        gridPok.addColumn(DtoPokPu::getNumDevice).setHeader("№ ПУ").setAutoWidth(true);
        gridPok.addColumn(DtoPokPu::getRatio).setHeader("Коэф.").setAutoWidth(true);
        gridPok.addColumn(DtoPokPu::getDate).setHeader("Дата").setAutoWidth(true);
        gridPok.addColumn(DtoPokPu::getMeter).setHeader("Показания").setAutoWidth(true);
        gridPokSetting();
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
        demandTypeSelect.setValue(null);
        demandTypeSelect.addValueChangeListener(e -> {
            Long id;
            String text;
            if(filterId.getValue().isEmpty()) id = null;
            else id = Long.valueOf(filterId.getValue());
            if(filterText.getParent().isEmpty()) text = "";
            else text = filterText.getValue();
            gridSetting(id,text);
        });
        clearFilter.setText("Очистить фильтр");
        clearFilter.addClickListener(event -> {
            filterId.setValue("");
            filterText.setValue("");
            gridSetting(null,"");
            demandTypeSelect.setValue(null);
        });

        filterLayout.add(filterId,filterText, demandTypeSelect,clearFilter);
        filterLayout.setAlignItems(FlexComponent.Alignment.BASELINE);
//        grid.getElement().setAttribute("title","кликните дважды для открытия заявки");
        TextField space = new TextField();
        space.setWidthFull();
        space.setReadOnly(true);

        add(gridPok,space);
    }

    private void gridPokSetting() {
        User currentUser =  this.userService.findByUsername(
                SecurityContextHolder.getContext().getAuthentication().getName()
        );
        gridPok.setPageSize(20);
        pokPuDtoList = DataHelper.createTable(currentUser.getUsername(),
                dogService, puService, pokService);
    }

    private void gridSetting(Long id, String text) {
        Role role = Role.ANONYMOUS;
        // Определим текущего пользователя
        User currentUser =  this.userService.findByUsername(
                SecurityContextHolder.getContext().getAuthentication().getName()
        );
        currentUser.setVisitDate(LocalDateTime.now());
        userService.update(currentUser);
        grid.setPageSize(20);
        grid.setSortableColumns("id","object","address");

        // Определим роль и кол-во заявок
        if(currentUser.getRoles().contains(Role.ADMIN)) {
            filterVisible(true);
            role = Role.ADMIN;
        } else if(currentUser.getRoles().contains(Role.GARANT)) {
            filterVisible(true);
            role = Role.GARANT;
        } else if(currentUser.getRoles().contains(Role.USER)) {
            filterVisible(false);
            role = Role.USER;
        }
        // Поиск по номеру заявки
        if(id != null && text.equals("")) {
            if (demandService
                    .findByIdAndUserAndDemandType(id
                            ,currentUser
                            ,demandTypeSelect.getValue()).isPresent())
                grid.setItems(demandService
                        .findByIdAndUserAndDemandType(id
                                ,currentUser
                                ,demandTypeSelect.getValue()).get());
            else {
                Notification notification = new Notification(
                        "Задача с таким номером и типом не найдена", 5000,
                        Notification.Position.MIDDLE);
                notification.open();
            }
            return;
        }
        // поиск по тексту в Заявителе, Объекте или Адресе
        if (!text.equals("") && id == null) {
            List<Demand> demandList = demandService.findText(text
                    ,currentUser
                    ,demandTypeSelect.getValue());
            if(demandList != null) {
                grid.setItems(demandList);
            }
            else {
                Notification notification = new Notification(
                        "Такой текст в этом типе не найден", 5000,
                        Notification.Position.MIDDLE);
                notification.open();
            }
            return;
        }
        // вывод всех заявок доступных пользователю
        grid.setItems(query ->
                demandService.findAllByUser(currentUser
                        ,demandTypeSelect.getValue()
                        , PageRequest.of(query.getPage(), query.getPageSize(),
                                VaadinSpringDataHelpers.toSpringDataSort(query))).stream());
    }

    private void filterVisible(Boolean visible) {
        filterText.setVisible(visible);
        filterId.setVisible(visible);
        demandTypeSelect.setVisible(visible);
        clearFilter.setVisible(visible);
        demanderColumn.setVisible(visible);
    }
}