package com.example.sf.backend.login;

import java.util.Scanner;
import com.example.sf.backend.api.MockDatabase;
import com.example.sf.backend.main.Logic;

/**
 * Created by PascalRoose on 11-Jan-18.
 */

public class UserLogin
{
    public static boolean login(String verzorgerNummer, String wachtwoord){
        if(MockDatabase.checkLogin(verzorgerNummer, wachtwoord)){
            return true;
        }
        return false;
    }

    public static void logout() {
        Logic.setVerzorger(null);
    }

}
