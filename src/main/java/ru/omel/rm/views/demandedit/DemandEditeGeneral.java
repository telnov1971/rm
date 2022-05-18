package ru.omel.rm.views.demandedit;

import ru.omel.rm.data.entity.DType;
import ru.omel.rm.data.entity.Demand;
import ru.omel.rm.data.entity.DemandType;
import ru.omel.rm.data.entity.General;
import ru.omel.rm.data.service.*;
import ru.omel.rm.views.main.MainView;
import ru.omel.rm.views.support.ExpirationsLayout;
import ru.omel.rm.views.support.GeneralForm;
import ru.omel.rm.views.support.PointsLayout;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;

@Route(value = "demandreciver/:demandID?", layout = MainView.class)
@RouteAlias(value ="demandreciver")
//@Route(value = "demandto15/:demandID?/:action?(edit)", layout = MainView.class)
@PageTitle("Иные категории потребителей")
public class DemandEditeGeneral extends GeneralForm {

    private final PointsLayout pointsLayout;
    private final ExpirationsLayout expirationsLayout;

    public DemandEditeGeneral(ReasonService reasonService,
                              DemandService demandService,
                              DemandTypeService demandTypeService,
                              StatusService statusService,
                              GarantService garantService,
                              PointService pointService,
                              GeneralService generalService,
                              ExpirationService expirationService,
                              UserService userService,
                              VoltageService voltageService,
                              SafetyService safetyService,
                              PlanService planService,
                              PriceService priceService,
                              SendService sendService,
                              FileStoredService fileStoredService,
                              HistoryService historyService,
                              NoteService noteService,
                              Component... components) {
        super(reasonService, demandService,demandTypeService,statusService,garantService,
                pointService,generalService,voltageService,
                safetyService,planService,priceService,sendService,userService,
                historyService, fileStoredService, DType.GENERAL,noteService,components);
        this.MaxPower = 1000000000.0;
        if(demandTypeService.findById(DemandType.GENERAL).isPresent())
            demandType.setValue(demandTypeService.findById(DemandType.GENERAL).get());

        pointsLayout = new PointsLayout(pointService
                , voltageService
                , safetyService
                , historyService
                , this
                , client);

        expirationsLayout = new ExpirationsLayout(expirationService
                , safetyService
                , historyService
                , this
                , client);

        Component[] fields = {typeDemander,
                addressRegistration,addressActual,addressEquals,
                accordionPoints, specification, countTransformations,
                countGenerations, techminGeneration, reservation, accordionExpiration};
        for(Component field : fields){
            field.setVisible(true);
        }

        accordionPoints.add("Точки подключения (открыть/закрыть по клику мышкой)"
                ,this.pointsLayout);
        accordionExpiration.add("Этапы выполнения работ (открыть/закрыть по клику мышкой)"
                ,this.expirationsLayout);
        powerMaximum.addValueChangeListener(e ->
            expirationsLayout.setPowerMax(powerMaximum.getValue())
        );
        add(formDemand,filesLayout,notesLayout,buttonBar,accordionHistory,space);
    }

    @Override
    public void populateForm(Demand value) {
        super.populateForm(value);
        if(value != null) {
            if(generalService.findAllByDemand(demand).isEmpty()) {
                general = new General();
            } else {
                general = generalService.findAllByDemand(demand).get(0);
            }
            pointsLayout.findAllByDemand(demand);
            expirationsLayout.findAllByDemand(demand);
            switch(demand.getStatus().getState()){
                case EDIT:
                    break;
                case ADD:
                case NOTE:
                case FREEZE: {
                    expirationsLayout.setReadOnly();
                } break;
            }
        }
        generalBinder.readBean(general);
    }
    public boolean save() {
        if(!super.save() || (binderDemand.validate().getValidationErrors().size() > 0)) return false;
        generalBinder.writeBeanIfValid(general);
        general.setDemand(demand);
        generalService.update(this.general);
        pointsLayout.setDemand(demand);
        historyExists |= pointsLayout.savePoints();
        expirationsLayout.setDemand(demand);
        historyExists |= expirationsLayout.saveExpirations();
        demand.setChange(demand.isChange() || historyExists);
        demandService.update(demand);

        return true;
    }

    @Override
    protected Boolean verifyField() {
        if(!super.verifyField()) return false;
        if(pointsLayout.getPointSize()==0){
            specification.focus();
            pointsLayout.setFocus();
            Notification.show("Не заполнены точки подключения", 3000,
                    Notification.Position.BOTTOM_START);
            return false;
        }
        if(expirationsLayout.getExpirationsSize()==0){
            safety.focus();
            expirationsLayout.setFocus();
            Notification.show("Не заполнены этапы работ", 3000,
                    Notification.Position.BOTTOM_START);
            return false;
        }
        return true;
    }
}
