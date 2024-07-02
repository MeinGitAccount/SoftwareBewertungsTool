package com.jku.at.views;

import com.jku.at.DTO.AnswerSetDTO;
import com.jku.at.model.CriteriaRating;
import com.jku.at.model.Software;
import com.jku.at.service.SoftwareService;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.annotation.View;
import jakarta.faces.context.FacesContext;
import jakarta.faces.model.SelectItem;
import jakarta.inject.Named;
import lombok.Getter;
import lombok.Setter;
import org.primefaces.PrimeFaces;
import org.primefaces.event.ItemSelectEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import software.xdev.chartjs.model.charts.BarChart;
import software.xdev.chartjs.model.charts.RadarChart;
import software.xdev.chartjs.model.color.Color;
import software.xdev.chartjs.model.data.BarData;
import software.xdev.chartjs.model.data.RadarData;
import software.xdev.chartjs.model.dataset.BarDataset;
import software.xdev.chartjs.model.dataset.RadarDataset;
import software.xdev.chartjs.model.options.*;
import software.xdev.chartjs.model.options.scale.Scales;
import software.xdev.chartjs.model.options.scale.cartesian.linear.LinearScaleOptions;
import software.xdev.chartjs.model.options.scale.cartesian.linear.LinearTickOptions;
import software.xdev.chartjs.model.options.scale.radial.PointLabels;
import software.xdev.chartjs.model.options.scale.radial.RadialLinearScaleOptions;
import software.xdev.chartjs.model.options.scale.radial.RadialTickOptions;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@View
@SessionScoped
@Named
public class FragebogenView implements Serializable {

    static final long serialVersionUID = 42L;

    @Autowired
    SoftwareService softwareService;

    @Getter
    @Setter
    private List<Software> softwareList;

    @Getter
    @Setter
    private AnswerSetDTO answerSet = new AnswerSetDTO();

    @Getter
    @Setter
    Map<Software, Double> resultMap = new HashMap<>();

    @Getter
    @Setter
    BarChart barModel;

    @Getter
    @Setter
    List<SelectItem> selectItems = new ArrayList<>();

    @PostConstruct
    public void init() {
        initSoftware();
        initSelectItems();
    }

    private void initSoftware() {
        softwareList = softwareService.findAll();
    }

    private void initSelectItems() {
        selectItems.add(new SelectItem(5, "Sehr wichtig"));
        selectItems.add(new SelectItem(4, "Eher wichtig"));
        selectItems.add(new SelectItem(3, "Mittelmäßig wichtig"));
        selectItems.add(new SelectItem(2, "Wenig wichtig"));
        selectItems.add(new SelectItem(1, "Nicht wichtig"));
    }

    public String getRadarChartForSoftware(Software s) {
        if(s == null) return "";
        RadarDataset dataSet = new RadarDataset()
                .setLabel(s.getName())
                .setBackgroundColor(new Color(255, 99, 132, 0.2))
                .setBorderColor(new Color(255, 99, 132))
                .setBorderWidth(1);
        RadarData radarData = new RadarData().addDataset(dataSet);

        RadarOptions options = new RadarOptions()
                .setMaintainAspectRatio(false)
                .setPlugins(new Plugins().setLegend(new Legend().setDisplay(false)))
                .setScales(new Scales().addScale(Scales.ScaleAxis.R,
                        new RadialLinearScaleOptions().setBeginAtZero(Boolean.TRUE)
                                .setTicks(new RadialTickOptions().setStepSize(1).setFont(new Font().setSize(18)))
                                .setPointLabels(new PointLabels().setFont(new Font().setSize(20)))
                ));

        for(CriteriaRating e : s.getCriteriaRatings()) {
            radarData.addLabel(e.getCriteriaName());
            dataSet.addData(e.getRating());
        }

        return new RadarChart().setData(radarData).setOptions(options).toJson();
    }


    public void initErgebnis() {
        for(Software s : softwareList) {
            resultMap.put(s, s.getScore(answerSet));
        }
        createBarModel();
        PrimeFaces.current().executeScript("PF('AbschlussDialog').show()");
    }

    private void createBarModel() {
        BarDataset dataSet = new BarDataset()
                .setLabel("Ergebnis")
                .setBackgroundColor(new Color(255, 99, 132, 0.2))
                .setBorderColor(new Color(255, 99, 132))
                .setBorderWidth(1);
        BarData barData = new BarData().addDataset(dataSet);
        BarChart barChartObj = new BarChart().setData(barData);

        for(Software soft : softwareList) {
            barData.addLabel(soft.getName());
            dataSet.addData(resultMap.get(soft));
        }

        barModel = barChartObj.setOptions(new BarOptions().setPlugins(new Plugins().setLegend(new Legend().setDisplay(false)))
                        .setScales(new Scales().addScale(Scales.ScaleAxis.Y, new LinearScaleOptions().setBeginAtZero(true).setMax(5).setTicks(new LinearTickOptions().setStepSize(1))))
                        .setResponsive(true)
                        .setMaintainAspectRatio(false)
                        .setIndexAxis(BarOptions.IndexAxis.X)
        );
    }

    public String getIntRatingAsString(Integer i) {
        if(i == null) return "";
        return switch (i) {
            case 1 -> "Nicht wichtig";
            case 2 -> "Wenig wichtig";
            case 3 -> "Mittelmäßig wichtig";
            case 4 -> "Eher Wichtig";
            case 5 -> "Sehr wichtig";
            default -> throw new IllegalStateException();
        };
    }

    public void softSelect(ItemSelectEvent event) throws IOException {
        FacesContext.getCurrentInstance().getExternalContext().redirect("detail.xhtml?softName=" + softwareList.stream().filter(s -> s.getName().equals(barModel.getData().getLabels().get(event.getItemIndex()))).findFirst().get().getName());
    }
}