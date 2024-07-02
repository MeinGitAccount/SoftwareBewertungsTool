package com.jku.at.model;

import com.jku.at.DTO.AnswerSetDTO;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

@Entity
public class Software {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Getter
    private Integer id;

    @Column(length = 50, unique = true, nullable = false)
    @Getter
    @Setter
    private String name;

    @Getter
    @Setter
    private Boolean einAusRechnung;

    @Getter
    @Setter
    private Boolean guvRechnung;

    @Getter
    @Setter
    private Boolean uvAnmeldung;

    @Getter
    @Setter
    private Boolean lugVerrechnung;

    @Getter
    @Setter
    private Boolean rumWesen;

    @Getter
    @Setter
    private Boolean belegerfassung;

    @Getter
    @Setter
    private Boolean elekRechnung;

    @Getter
    @Setter
    private Boolean auswertungen;

    @Getter
    @Setter
    private Boolean impExport;

    @Getter
    @Setter
    private Boolean kassabuch;

    @Getter
    @Setter
    private Boolean autoBelegerfassung;

    @Getter
    @Setter
    private Boolean eingabenkontrolle;

    @Getter
    @Setter
    private Boolean kiSupport;

    @Getter
    @Setter
    private Boolean apiBank;

    @Getter
    @Setter
    private Boolean apiDatev;

    @Getter
    @Setter
    private Boolean apiFinOn;

    @Getter
    @Setter
    private Boolean apiKunde;

    @Getter
    @Setter
    private Boolean kundFreundSupp;

    @Getter
    @Setter
    private Boolean kundFreundSchulungen;

    @Getter
    @Setter
    private Boolean kundFreundUI;

    @Getter
    @Setter
    private Boolean kundFreundHandyNutzung;

    @Getter
    @Setter
    private Boolean kosten;

    @Getter
    @Setter
    private int apiCount;

    @Getter
    @Setter
    //want rich text but can't display it :,( it's always just normal fkin text.. tilting
    @Lob
    @Column(columnDefinition = "CLOB")
    private String beschreibung;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @Getter
    @Setter
    private List<CriteriaRating> criteriaRatings = new ArrayList<>();

    public Software() {
        criteriaRatings.add(new CriteriaRating("Funktionsumfang", this));
        criteriaRatings.add(new CriteriaRating("Bedienbarkeit", this));
        criteriaRatings.add(new CriteriaRating("Automatisierbarkeit", this));
        criteriaRatings.add(new CriteriaRating("Schnittstellen", this));
        criteriaRatings.add(new CriteriaRating("Kosten", this));
    }
    @Override
    public int hashCode() {
        return name.hashCode();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        return name.equalsIgnoreCase(((Software) o).getName());
    }

    @Override
    public String toString() {
        return name;
    }


    //IMPORTANT: the below method works on the premise, that Booleans (in the AnswerSetDTO) are ALL knockout criteria
    public double getScore(AnswerSetDTO answerSet) {
        double score = 0;

        //below are 2 additional possible ways to achieve the knockout: Firstly here's a row of if-statements

        /*
        if(answerSet.getEinAusRechnung() && !this.getEinAusRechnung() || answerSet.getGuvRechnung() && !this.getGuvRechnung() || answerSet.getUvAnmeldung() && !this.getUvAnmeldung() ||
                answerSet.getLugVerrechnung() && !this.getLugVerrechnung() || answerSet.getRumWesen() && !this.getRumWesen() || answerSet.getBelegerfassung() && !this.getBelegerfassung() ||
                answerSet.getElekRechnung() && !this.getElekRechnung() || answerSet.getAuswertungen() && !this.getAuswertungen() || answerSet.getImpExport() && !this.getImpExport() ||
                answerSet.getKassabuch() && !this.getKassabuch() || answerSet.getApiBank() && !this.getApiBank() || answerSet.getApiDatev() && !this.getApiDatev() ||
                answerSet.getApiFinOn() && !this.getApiFinOn() || answerSet.getApiKunde() && !this.getApiKunde()) {
            return score;
        }
        */

        //secondly, here is the knockout via Fields (must be public fields in order to work) rather than Methods

        /*
        for (Field f : this.getClass().getDeclaredFields()) {
            if(f.getType().equals(boolean.class) || f.getType().equals(Boolean.class)) {
                Optional<Field> aStField = Arrays.stream(answerSet.getClass().getDeclaredFields()).filter(ff -> f.getName().equals(ff.getName())).findFirst();
                if(aStField.isEmpty()) throw new IllegalStateException("didn't work smh, Optional empty?! Software.java, line 156");
                else {
                    try {
                        Boolean boolAnsSet = (Boolean) aStField.get().get(answerSet);
                        Boolean boolSoft = (Boolean) f.get(this);
                        if (boolAnsSet && !boolSoft) return score;
                    } catch (IllegalAccessException e) {
                        System.out.println("Field " + f.getName() + " couldn't be accessed.");
                    }
                }
            }
        }
        */

        for(Method m : answerSet.getClass().getDeclaredMethods()) {
            if(m.getName().startsWith("get")) {
                if(m.getReturnType().equals(Boolean.class)) {
                    Optional<Method> thisMethod = Arrays.stream(this.getClass().getDeclaredMethods()).filter(ff -> m.getName().equals(ff.getName())).findFirst();
                    if (thisMethod.isEmpty())
                        //indicator when something goes wrong - thisMethod should NOT be empty (usually)
                        throw new IllegalStateException("didn't work smh, Optional empty?! Software.java, line 156");
                    else {
                        try {
                            Boolean boolSoft = (Boolean) thisMethod.get().invoke(this);
                            Boolean boolAnsSet = (Boolean) m.invoke(answerSet);
                            if (boolAnsSet && !boolSoft) return 0.0;
                        } catch (IllegalAccessException e) {
                            System.out.println("Method " + m.getName() + " couldn't be accessed. Error");
                        } catch (InvocationTargetException e) {
                            System.out.println("Method " + m.getName() + " was invoked improperly. Error");
                        }
                    }
                }

                else if(m.getReturnType().equals(Integer.class)) {
                    Optional<Method> thisMethod = Arrays.stream(this.getClass().getDeclaredMethods()).filter(ff -> m.getName().equals(ff.getName())).findFirst();
                    if (thisMethod.isEmpty())
                        //funnily enough this also catches the additional field "apicount" from being inacurately calculated into the final score
                        throw new IllegalStateException("didn't work smh, Optional empty?! Software.java, line 156");
                    else {
                        try {
                            if((Boolean) thisMethod.get().invoke(this)) score += ((Integer) m.invoke(answerSet)).doubleValue() / 8.0;
                        } catch (IllegalAccessException e) {
                            System.out.println("Method " + m.getName() + " couldn't be accessed. Error");
                        } catch (InvocationTargetException e) {
                            System.out.println("Method " + m.getName() + " was invoked improperly. Error");
                        }
                    }
                }
            }
        }

        //Here's also a way to calculate score with adding the properties one by one rather than going over java.reflect

        /*
        score += this.getAutoBelegerfassung() ? (double) answerSet.getAutoBelegerfassung() / 8.0 : 0.0;
        score += this.getEingabenkontrolle() ? (double) answerSet.getEingabenkontrolle() / 8.0 : 0.0;
        score += this.getKiSupport() ? (double) answerSet.getKiSupport() / 8.0 : 0.0;
        score += this.getKundFreundSupp() ? (double) answerSet.getKundFreundSupp() / 8.0 : 0.0;
        score += this.getAutoBelegerfassung() ? (double) answerSet.getKundFreundSchulungen() / 8.0 : 0.0;
        score += this.getKundFreundSupp() ? (double) answerSet.getKundFreundUI() / 8.0 : 0.0;
        score += this.getKundFreundHandyNutzung() ? (double) answerSet.getKundFreundHandyNutzung() / 8.0 : 0.0;
        score += this.getKosten() ? (double) answerSet.getKosten() / 8.0 : 0.0;
        */
        return score;
    }
}