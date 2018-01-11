package com.example.backend.main;
import java.util.Scanner;


import com.example.backend.api.MockDatabase;
import com.example.backend.entities.Afspraak;
import com.example.backend.entities.Agenda;
import com.example.backend.entities.Verzorger;
import com.example.backend.login.Pincode;
import com.example.backend.login.UserLogin;

/**
 * Created by PascalRoose on 11-Jan-18.
 */

public class Logic
{

        private static Verzorger verzorger;
        private static String pincode;
        private static Agenda agenda;

        public static void login() {
            while (true) {
                if (verzorger == null) {
                    if (UserLogin.loginScreen()) {
                        System.out.println("Login succesvol!");
                    } else {
                        System.out.println("Verzorgernummer en/of Wachtwoord incorrect");
                    }
                } else {
                    if (pincode == null) {
                        Pincode.setup();
                        break;
                    } else {
                        if (Pincode.check()) {
                            break;
                        }
                    }
                }
            }
            System.out.println("Succesvol ingelogd! Welkom " + Logic.getVerzorger().getNaam() + "!");
            dashboard();
        }

        private static void dashboard() {
            @SuppressWarnings("resource")
            Scanner keyboard = new Scanner(System.in);

            System.out.println("Welkom " + verzorger.getNaam() + "!");

            agenda = MockDatabase.getAgenda();
            agenda.print();

            System.out.println("1. Scanner | 2. Instellingen");
            int keuze = keyboard.nextInt();

            switch (keuze) {
                case 1:
                    barcodeScanner();
                    break;
                case 2:
                    settings();
                    break;
            }
            dashboard();
        }

        private static void barcodeScanner(){
            @SuppressWarnings("resource")
            Scanner keyboard = new Scanner(System.in);

            System.out.println("1 | 2 | 3");
            int keuze = keyboard.nextInt();

            switch (keuze) {
                case 1:
                    scan("3AEE75EC");
                    break;
                case 2:
                    scan("DFE5746B");
                    break;
                case 3:
                    scan("FE5876EE");
                    break;
            }
        }

        private static void scan(String barcode) {
            Afspraak afspraken[] = agenda.getAfspraken();
            Afspraak scanAfspraak = null;

            for(int i = 0; i < afspraken.length; i++) {
                if(barcode == afspraken[i].getBaxter().getBarcode()) {
                    scanAfspraak = afspraken[i];
                }
            }

            if(scanAfspraak != null) {
                scanAfspraak.print();
            }


        }

        private static void settings() {
            @SuppressWarnings("resource")
            Scanner keyboard = new Scanner(System.in);

            System.out.println("1. Uitloggen | 2. Account ontkoppelen | 3. Nieuwe pincode | 4. Terug");
            int keuze = keyboard.nextInt();

            switch(keuze) {
                case 1:
                    Logic.login();
                case 2:
                    UserLogin.logout();
                    Logic.login();
                case 3:
                    UserLogin.newPin();
                    Logic.settings();
                case 4:
                    Logic.dashboard();
            }
        }

        public static Verzorger getVerzorger() {
            return verzorger;
        }

        public static void setVerzorger(Verzorger verzorger) {
            Logic.verzorger = verzorger;
        }

        public static String getPincode() {
            return pincode;
        }

        public static void setPincode(String pincode) {
            Logic.pincode = pincode;
        }

}
