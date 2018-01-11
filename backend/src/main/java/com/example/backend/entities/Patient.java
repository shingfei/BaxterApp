package com.example.backend.entities;

/**
 * Created by PascalRoose on 11-Jan-18.
 */

public class Patient
{
    private int patientNummer;
    private String naam;
    private String fotoUrl;

    public Patient(int patientNummer, String naam, String fotoUrl) {
        this.patientNummer = patientNummer;
        this.naam = naam;
        this.fotoUrl = fotoUrl;
    }

    public void print() {
        System.out.println("        - PatientNummer: " + patientNummer + ", naam: " + naam + ", fotoURL: " + fotoUrl);
    }

    public int getPatientNummer() {
        return patientNummer;
    }
    public String getNaam() {
        return naam;
    }
    public String getFotoUrl() {
        return fotoUrl;
    }
}
