package com.example.sf.backend.entities;

/**
 * Created by PascalRoose on 11-Jan-18.
 */

public class Verzorger
{
    private static String verzorgerNummer;
    private static String naam;
    private static String token;

    public Verzorger(String _verzorgerNummer, String _naam, String _token) {
        verzorgerNummer = _verzorgerNummer;
        naam = _naam;
        token = _token;
    }

    public String getVerzorgerNummer() {
        return verzorgerNummer;
    }
    public String getNaam() {
        return naam;
    }
    public String getToken() {
        return token;
    }
}
