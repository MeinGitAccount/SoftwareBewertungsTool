package com.jku.at.DTO;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class AnswerSetDTO {
    public Boolean einAusRechnung;
    public Boolean guvRechnung;
    public Boolean uvAnmeldung;
    public Boolean lugVerrechnung;
    public Boolean rumWesen;
    public Boolean belegerfassung;
    public Boolean elekRechnung;
    public Boolean auswertungen;
    public Boolean impExport;
    public Boolean kassabuch;
    public Integer autoBelegerfassung;
    public Integer eingabenkontrolle;
    public Integer kiSupport;
    public Boolean apiBank;
    public Boolean apiDatev;
    public Boolean apiFinOn;
    public Boolean apiKunde;
    public Integer kundFreundSupp;
    public Integer kundFreundSchulungen;
    public Integer kundFreundUI;
    public Integer kundFreundHandyNutzung;
    public Integer kosten;
}