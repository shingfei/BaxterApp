package com.example.sf.backend.entities;

/**
 * Created by PascalRoose on 11-Jan-18.
 */

public class Agenda {

    private static Afspraak[] afspraken;

    public Agenda(Afspraak[] _afspraken) {
        afspraken = _afspraken;
    }
    public void print() {
        System.out.println("Afspraken:");
        for(int i=0; i< afspraken.length; i++) {
            afspraken[i].print();
        }
    }
    public void refresh() {
        //TODO: Afspraken ophalen en opnieuw opslaan
    }
    public Afspraak[] getAfspraken() {
        return afspraken;
    }

}

