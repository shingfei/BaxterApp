package com.example.backend.login;

import java.util.Scanner;
import com.example.backend.api.MockDatabase;
import com.example.backend.main.Logic;

/**
 * Created by PascalRoose on 11-Jan-18.
 */

public class UserLogin
{
    public static boolean loginScreen(){
        @SuppressWarnings("resource")
        Scanner keyboard = new Scanner(System.in);
        System.out.println("Voer uw verzorgernummer in:");
        String verzorgerNummer = keyboard.nextLine();
        System.out.println("Voer uw wachtwoord in:");
        String wachtwoord = keyboard.nextLine();

        if(MockDatabase.checkLogin(verzorgerNummer, wachtwoord)){
            return true;
        }
        return false;
    }

    public static void logout() {
        Logic.setVerzorger(null);
    }

    public static void newPin() {
        if(Pincode.check()) {
            Logic.setPincode(null);
            Pincode.setup();
        }

    }
}
