package com.example.backend.api;

/**
 * Created by PascalRoose on 11-Jan-18.
 */
import android.annotation.TargetApi;

import java.time.LocalDate;
import java.time.LocalTime;

import com.example.backend.entities.*;
import com.example.backend.main.Logic;

public class MockDatabase
{
    @TargetApi(26)
    public static Agenda getAgenda(){
        Medicijn paracetemol = new Medicijn(1, "Paracetemol", "slikken kut");
        Medicijn zetpil = new Medicijn(2, "Zetpil", "in uw reet douwen");
        Medicijn wiet = new Medicijn(3, "Medicinale wiet", "blaze it faggot");
        Patient gerard = new Patient(1, "Gerard van Gezond- Boerenverstand", "http://test.test/1.jpg");
        Patient peter = new Patient(2, "Peter Allemaal Onvoldoende", "http://test.test/2.jpg");
        Baxter baxter1 = new Baxter("3AEE75EC", gerard, paracetemol, 2);
        Baxter baxter2 = new Baxter("DFE5746B", gerard, zetpil, 69);
        Baxter baxter3 = new Baxter("FE5876EE", peter, wiet, 420);
        Afspraak afspraak1 = new Afspraak(1, LocalDate.of(2017, 12, 13), LocalTime.of(10, 0), baxter1);
        Afspraak afspraak2 = new Afspraak(2, LocalDate.of(2017, 12, 14), LocalTime.of(10, 0), baxter2);
        Afspraak afspraak3 = new Afspraak(3, LocalDate.of(2017, 12, 14), LocalTime.of(11, 0), baxter3);
        Afspraak[] afspraken = {afspraak1, afspraak2, afspraak3};
        Agenda agenda = new Agenda(afspraken);
        return agenda;
    }

    public static boolean checkLogin(String verzorgerNummer, String wachtwoord){
        if(verzorgerNummer.equals("18041998") && wachtwoord.equals("geheim")){
            Logic.setVerzorger(new Verzorger(verzorgerNummer, "Pascal Roose", "ae3c7ffe"));
            return true;
        }
        return false;
    }
}
