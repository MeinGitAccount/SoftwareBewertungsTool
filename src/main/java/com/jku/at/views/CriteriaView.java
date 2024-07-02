package com.jku.at.views;

import com.jku.at.model.Software;
import com.jku.at.service.SoftwareService;
import jakarta.annotation.PostConstruct;
import jakarta.faces.annotation.View;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import lombok.Getter;
import lombok.Setter;
import org.primefaces.event.RowEditEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@View
public class CriteriaView {

    @Autowired
    SoftwareService softwareService;


    @Getter
    @Setter
    private List<Software> softwareList;


    @PostConstruct
    public void init() {
        initSoftware();
    }

    private void initSoftware() {
        softwareList = softwareService.findAll();
    }

    public void deleteSoftware(Software software) {
        softwareService.delete(software);
        initSoftware();
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Software " + software.getName() + " gelöscht.", ""));
    }

    public void onRowEditSoftware(RowEditEvent<Software> event) {
        initSoftware(); //necessary to be sure the software isn't in the list anymore before adding the new managed entity to the list
        if(softwareService.findAll().stream().anyMatch(s -> s.getName().equals(event.getObject().getName()))) {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Speichern von " + event.getObject().getName() + " fehlgeschlagen.", "Der Name existiert bereits.");
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
        else{
            softwareList.add(softwareService.save(event.getObject()));
            FacesMessage msg = new FacesMessage("Software " + event.getObject().getName() + " bearbeitet.", "");
            FacesContext.getCurrentInstance().addMessage(null, msg);
            initSoftware();
        }
    }

    public void onRowCancelSoftware(RowEditEvent<Software> event) {
        FacesMessage msg = new FacesMessage("Bearbeitung abgebrochen", "");
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public void addNewSoftware() {
        softwareList.add(new Software());
    }

    public void saveSoftware(Software soft) {
        softwareService.save(soft);
        initSoftware();
    }
}