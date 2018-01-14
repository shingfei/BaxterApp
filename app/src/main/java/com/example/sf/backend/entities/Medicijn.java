package com.example.sf.backend.entities;

/**
 * Created by PascalRoose on 11-Jan-18.
 */

public class Medicijn
{
    private int medicijnNummer;
    private String naam;
    private String toediening;

    public Medicijn(int medicijnNummer, String naam, String toediening) {
        this.medicijnNummer = medicijnNummer;
        this.naam = naam;
        this.toediening = toediening;
    }

    public void print() {
        System.out.println("        - MedicijnNummer: " + medicijnNummer + ", naam: " + naam + ", toediening: " + toediening);
    }

    public int getMedicijnNummer() {
        return medicijnNummer;
    }
    public String getNaam() {
        return naam;
    }
    public String getToediening() {
        return toediening;
    }

}
