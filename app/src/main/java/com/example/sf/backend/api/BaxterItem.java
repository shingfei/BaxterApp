package com.example.sf.backend.api;

/**
 * Created by Lars Fikkers on 15-1-2018.
 */

public class BaxterItem {
    private String patientNaam, medicijnNaam, toediening, barcode, dosis;
    private int afspraakNummer;
    private boolean check;

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

    public String getToediening() {
        return toediening;
    }

    public void setToediening(String toediening) {
        this.toediening = toediening;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public String getDosis() {
        return dosis;
    }

    public void setDosis(String dosis) {
        this.dosis = dosis;
    }

    public int getAfspraakNummer() {
        return afspraakNummer;
    }

    public void setAfspraakNummer(int afspraakNummer) {
        this.afspraakNummer = afspraakNummer;
    }

    public boolean getCheck() {
        return check;
    }

    public void setCheck(boolean b) {
        check = b;
    }
}
