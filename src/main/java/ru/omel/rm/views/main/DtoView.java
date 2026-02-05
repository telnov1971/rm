package ru.omel.rm.views.main;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.grid.ColumnTextAlign;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.*;
import org.springframework.security.core.context.SecurityContextHolder;
import ru.omel.rm.data.dto.DtoIndMet;
import ru.omel.rm.data.entity.Contract;
import ru.omel.rm.data.entity.Role;
import ru.omel.rm.data.entity.User;
import ru.omel.rm.data.service.*;

import java.time.LocalDateTime;
import java.util.*;

@PageTitle("Показания счетчиков")
@Route(value = "meters", layout = MainView.class)
@RouteAlias(value = "", layout = MainView.class)
public class DtoView extends Div implements BeforeEnterObserver {
    private final Grid<DtoIndMet> gridIndMet = new Grid<>(DtoIndMet.class, false);
    private List<DtoIndMet> dtoIndMetList;
    private final ContractService contractService;
    private final MeterDeviceService meterDeviceService;
    private final IndicationService indicationService;
    private final Label tNumber = new Label();
    private final Label tNum = new Label();
    private final Label tName = new Label();
    private final Label tInn = new Label();
    private final DatePicker startDate = new DatePicker("Начало периода");
    private final DatePicker endDate = new DatePicker("Конец периода");
    private final UserService userService;

    public DtoView(UserService userService
            , ContractService contractService
            , MeterDeviceService meterDeviceService
            , IndicationService indicationService) {
        this.userService = userService;
        this.contractService = contractService;
        this.meterDeviceService = meterDeviceService;
        this.indicationService = indicationService;
        HorizontalLayout filterLayout = new HorizontalLayout();
        HorizontalLayout header = new HorizontalLayout();
        VerticalLayout vlLabel = new VerticalLayout();
        VerticalLayout vlDog = new VerticalLayout();
        VerticalLayout superHeader = new VerticalLayout();
        Label lNumber = new Label("№ договора в системе ООО ОЭСК");
        Label lNum = new Label("№ договора в системе АО Омскэлектро");
        Label lName = new Label("Наименование договора");
        Label lInn = new Label("ИНН");
        vlLabel.add(lNumber,lNum,lName,lInn);
        vlDog.add(tNumber,tNum,tName,tInn);
        header.add(vlLabel,vlDog);
        header.setWidthFull();
        filterLayout.add(startDate,endDate);
        filterLayout.setWidthFull();
        superHeader.add(header,filterLayout);
        superHeader.setWidthFull();
        addClassNames("master-detail-view", "flex", "flex-col");

        // Configure Grid
        gridSetting();

        VerticalLayout ltz = new VerticalLayout();
        ltz.add(new Label("Тарифная"), new Label("зона"));

        //
        gridIndMet.addColumn(DtoIndMet::getObjName)
                .setHeader("Объект")
                .setSortable(true)
                .setAutoWidth(true);
        gridIndMet.addColumn(DtoIndMet::getObjAddress).setHeader("Адресс").setAutoWidth(true);
        gridIndMet.addColumn(DtoIndMet::getTypeDevice).setHeader("Тип ПУ")
                .setAutoWidth(true).setTextAlign(ColumnTextAlign.CENTER);
        gridIndMet.addColumn(DtoIndMet::getNumDevice)
                .setHeader("№ ПУ")
                .setSortable(true)
                .setAutoWidth(true)
                .setTextAlign(ColumnTextAlign.CENTER);
        gridIndMet.addColumn(DtoIndMet::getRatio).setHeader("Коэф.")
                .setAutoWidth(true).setTextAlign(ColumnTextAlign.CENTER);
        gridIndMet.addColumn(DtoIndMet::getDate)
                .setHeader("Дата")
                .setAutoWidth(true)
                .setSortable(true)
                .setTextAlign(ColumnTextAlign.CENTER);
        gridIndMet.addColumn(DtoIndMet::getTzona).setHeader(ltz)
                .setAutoWidth(true).setTextAlign(ColumnTextAlign.CENTER);
        gridIndMet.addColumn(DtoIndMet::getVid).setHeader("Вид")
                .setAutoWidth(true).setTextAlign(ColumnTextAlign.CENTER);
        gridIndMet.addColumn(DtoIndMet::getMeter).setHeader("Показания")
                .setAutoWidth(true).setTextAlign(ColumnTextAlign.END);
        gridIndSetting();
        gridIndMet.setItems(filterList(dtoIndMetList));
        gridIndMet.addThemeVariants(GridVariant.LUMO_COLUMN_BORDERS);
        startDate.addValueChangeListener(e -> {
            endDate.setMin(e.getValue());
            gridIndMet.setItems(filterList(dtoIndMetList));
        });
        endDate.addValueChangeListener(e -> {
            startDate.setMax(e.getValue());
            gridIndMet.setItems(filterList(dtoIndMetList));
        });
//

        TextField space = new TextField();
        space.setWidthFull();
        space.setReadOnly(true);

        add(superHeader,gridIndMet,space);
    }


    private void gridIndSetting() {
        User currentUser =  this.userService.findByUsername(
                SecurityContextHolder.getContext().getAuthentication().getName()
        );
        gridIndMet.setPageSize(20);
        dtoIndMetList = DataHelper.createTableIndMet(currentUser.getUsername(),
                contractService, meterDeviceService, indicationService);
        if(contractService.findByStrNumber(currentUser.getUsername()).isPresent()){
            Contract currentContract = contractService
                    .findByStrNumber(currentUser.getUsername()).orElse(null);
            if(currentContract != null) {
                tNumber.setText(currentContract.getNumgp());
                tNum.setText(currentContract.getStrNumber());
                tName.setText(currentContract.getStrName());
                tInn.setText(currentContract.getINN());
            }
        }

    }

    private void gridSetting() {
        // Определим текущего пользователя
        User currentUser =  this.userService.findByUsername(
                SecurityContextHolder.getContext().getAuthentication().getName()
        );
        currentUser.setVisitDate(LocalDateTime.now());
        userService.update(currentUser);
        gridIndMet.setPageSize(20);
    }

    @Override
    public void beforeEnter(BeforeEnterEvent event) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        if (username != null) {
            User currentUser = this.userService.findByUsername(username);
            if (currentUser != null) {
                if(currentUser.getRoles().contains(Role.ADMIN)) {
                    UI.getCurrent().navigate("/users");
                } else {
                    UI.getCurrent().navigate("/meters");
                }
            }
        }

    }

    private List<DtoIndMet> filterList(List<DtoIndMet> list) {
        if((startDate.getValue() != null) &&
                endDate.getValue() != null) {
            Date start = java.sql.Date.valueOf(startDate.getValue());
            Date end = java.sql.Date.valueOf(endDate.getValue());
            return list.stream()
                    .filter(item -> item.getDate()
                            .after(start))
                    .filter(item -> item.getDate()
                            .before(end))
                    .toList();
        }
        return list;
    }
}