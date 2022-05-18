package ru.omel.rm.views.demandedit;

import ru.omel.rm.data.service.*;
import ru.omel.rm.data.entity.DType;
import ru.omel.rm.data.entity.Demand;
import ru.omel.rm.data.entity.DemandType;
import ru.omel.rm.data.entity.Point;
import ru.omel.rm.views.main.MainView;
import ru.omel.rm.views.support.GeneralForm;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;

@Route(value = "demandtemporary/:demandID?", layout = MainView.class)
@RouteAlias(value ="demandtemporary")
//@Route(value = "demandto15/:demandID?/:action?(edit)", layout = MainView.class)
@PageTitle("Временное присоединение")
public class DemandEditTemporal extends GeneralForm {

    public DemandEditTemporal(ReasonService reasonService,
                              DemandService demandService,
                              DemandTypeService demandTypeService,
                              StatusService statusService,
                              GarantService garantService,
                              PointService pointService,
                              GeneralService generalService,
                              //ExpirationService expirationService,
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
                historyService, fileStoredService, DType.TEMPORAL,noteService,components);
        this.MaxPower = 1000000.0;
        if(demandTypeService.findById(DemandType.TEMPORAL).isPresent())
            demandType.setValue(demandTypeService.findById(DemandType.TEMPORAL).get());

        // inn, innDate, passportSerries,passportNumber, pasportIssued
        Component[] fields = {typeDemander,
                addressRegistration,addressActual,addressEquals,
                powerDemand, powerCurrent, powerMaximum, voltage, safety,
                specification};
        for(Component field : fields){
            field.setVisible(true);
        }

        //accordionExpiration.add("Этапы выполнения работ",this.expirationsLayout);
        add(formDemand,filesLayout,notesLayout,buttonBar,accordionHistory,space);
    }

    @Override
    public void populateForm(Demand value) {
        super.populateForm(value);
        if(value != null) {
            if(pointService.findAllByDemand(demand).isEmpty()) {
                point = new Point();
            } else {
                point = pointService.findAllByDemand(demand).get(0);
            }
        }
        pointBinder.readBean(this.point);
        safety.setReadOnly(true);
    }

    public boolean save() {
        if(!super.save() || (pointBinder.validate().getValidationErrors().size() > 0)) return false;
        pointBinder.writeBeanIfValid(point);
        point.setDemand(demand);
        historyExists |= historyService.saveHistory(client, demand,point,Point.class);
        pointService.update(this.point);
        demand.setChange(demand.isChange() || historyExists);
        demandService.update(demand);
        return true;
    }

    @Override
    protected Boolean verifyField() {
        return super.verifyField();
    }

    @Override
    protected void settingTemporalReasons() {
        powerCurrent.setEnabled(true);
        if(reason.getValue().getId() == 1){
            powerCurrent.setValue(0.0);
            powerCurrent.setEnabled(false);
        }
        if(reason.getValue().getId() == 5){
            contract.setVisible(true);
            period.setVisible(false);
            this.MaxPower = 1000000.0;
            powerMaximum.setHelperText("");
            period.setHelperText("");
        }
        if(reason.getValue().getId() == 6){
            contract.setVisible(false);
            period.setVisible(true);
            this.MaxPower = 150.0;
            powerMaximum.setHelperText("Для передвижных объектов максимальная мощность не более 150 кВт");
            period.setHelperText("Для передвижных объектов срок подключения не должен превышать 12 месяцев");
        }
    }
}
