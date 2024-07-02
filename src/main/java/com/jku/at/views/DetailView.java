package com.jku.at.views;

import com.jku.at.model.Software;
import com.jku.at.service.SoftwareService;
import jakarta.enterprise.context.RequestScoped;
import jakarta.faces.annotation.View;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@View
@RequestScoped
public class DetailView {

    @Autowired
    SoftwareService softwareService;

    @Getter
    @Setter
    private String name;

    @Getter
    @Setter
    private Software software;

    public void init() {
        software=softwareService.findByName(name);
    }
}