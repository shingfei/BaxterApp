package com.example.sf.backend.api;


public class BaxterItem {
    private String patientNaam;
    private String medicijnNaam;
    private String dosis;
    private String toediening;
    private String afspraakNummer;

    public String getPatientNaam() {
        return patientNaam;
    }

    public void setPatientNaam(String patientNaam) {
        this.patientNaam = patientNaam;
    }

    public String getMedicijnNaam() {
        return medicijnNaam;
    }

    public void setMedicijnNaam(String medicijnNaam) {
        this.medicijnNaam = medicijnNaam;
    }

    public String getDosis() {
        return dosis;
    }

    public void setDosis(String dosis) {
        this.dosis = dosis;
    }

    public String getToediening() {
        return toediening;
    }

    public void setToediening(String toediening) {
        this.toediening = toediening;
    }

    public String getAfspraakNummer() {
        return afspraakNummer;
    }

    public void setAfspraakNummer(String afspraakNummer) { this.afspraakNummer = afspraakNummer; }
}
