package com.jku.at.views;

import com.jku.at.model.Criteria;
import com.jku.at.model.CriteriaRating;
import com.jku.at.model.Software;
import com.jku.at.service.CriteriaRatingService;
import com.jku.at.service.CriteriaService;
import com.jku.at.service.SoftwareService;
import jakarta.annotation.PostConstruct;
import jakarta.faces.annotation.View;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import lombok.Getter;
import lombok.Setter;
import org.primefaces.event.RowEditEvent;
import org.primefaces.event.ToggleEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@View
public class CriteriaView {

    @Autowired
    CriteriaService criteriaService;

    @Autowired
    SoftwareService softwareService;

    @Autowired
    private CriteriaRatingService criteriaRatingService;

    @Getter
    @Setter
    private List<Criteria> criteriaList;

    @Getter
    @Setter
    private List<Software> softwareList;

    @Getter
    @Setter
    private Criteria newCriteria;

    @Getter
    @Setter
    private Software newSoftware;


    @PostConstruct
    public void init() {
        initCriteria();
        initSoftware();
    }

    private void initCriteria() {
        criteriaList = criteriaService.findAll();
        newCriteria = new Criteria();
    }

    private void initSoftware() {
        softwareList = softwareService.findAll();
        newSoftware = new Software();
    }

    public void deleteCriteria(Criteria criterium) {
        criteriaService.delete(criterium);
        initCriteria();
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Kriterium " + criterium.getName() + " gelöscht.", ""));
    }

    public void onRowEditCriterium(RowEditEvent<Criteria> event) {
        criteriaService.save(event.getObject());
        FacesMessage msg = new FacesMessage("Kriterium " + event.getObject().getName() + " bearbeitet.", "");
        FacesContext.getCurrentInstance().addMessage(null, msg);
        initCriteria();
    }
    public void onRowCancelCriterium(RowEditEvent<Criteria> event) {
        FacesMessage msg = new FacesMessage("Bearbeitung abgebrochen", "");
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }
    public void addNewCriterium() {
        criteriaList.add(new Criteria());
    }

    public void deleteSoftware(Software software) {
        softwareService.delete(software);
        initSoftware();
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Software " + software.getName() + " gelöscht.", ""));
    }

    public void onRowEditSoftware(RowEditEvent<Software> event) {
        softwareService.save(event.getObject());
        FacesMessage msg = new FacesMessage("Software " + event.getObject().getName() + " bearbeitet.", "");
        FacesContext.getCurrentInstance().addMessage(null, msg);
        initSoftware();
    }
    public void onRowCancelSoftware(RowEditEvent<Criteria> event) {
        FacesMessage msg = new FacesMessage("Bearbeitung abgebrochen", "");
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }
    public void addNewSoftware() {
        softwareList.add(new Software());
    }

    public void onRowExpansion(ToggleEvent event) throws RuntimeException {
        if(event.getData().getClass() != Software.class) throw new RuntimeException("Event triggered wrongly.");

        Software soft = (Software) event.getData();
        boolean changed = false;

        for(Criteria c : criteriaList) {
            if(soft.getCriteriaRatings().stream().noneMatch(cR -> cR.getCriteria().getName().equals(c.getName()))) {
                System.out.println(c + " " + soft);
                soft.getCriteriaRatings().add(new CriteriaRating(c, soft));
                changed = true;
            }
        }
        if(changed) {
            softwareService.save(soft);
            for(CriteriaRating cR : soft.getCriteriaRatings()) {
                System.out.println(cR);
            }

        }
    }

    public void saveAllCritRatingsForSoftware(Software software) {
        softwareService.save(software);
    }
}