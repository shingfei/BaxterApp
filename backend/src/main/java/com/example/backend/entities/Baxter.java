package com.example.backend.entities;

/**
 * Created by PascalRoose on 11-Jan-18.
 */

public class Baxter
{
    private String barcode;
    private Patient patient;
    private Medicijn medicijn;
    private int dosis;

    public Baxter(String barcode, Patient patient, Medicijn medicijn, int dosis) {
        this.barcode = barcode;
        this.patient = patient;
        this.medicijn = medicijn;
        this.dosis = dosis;
    }

    public void print() {
        System.out.println("    - Barcode: " + barcode + ", dosis: " + dosis);
        medicijn.print();
        patient.print();
    }

    public String getBarcode() {
        return barcode;
    }
    public Patient getPatient() {
        return patient;
    }
    public Medicijn getMedicijn() {
        return medicijn;
    }
    public int getDosis() {
        return dosis;
    }

}
