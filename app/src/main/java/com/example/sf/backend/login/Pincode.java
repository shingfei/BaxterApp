package com.example.sf.backend.login;

import java.util.Scanner;
import com.example.sf.backend.main.Logic;

public class Pincode {

    public static boolean check(String pincode){
        if(pincode.equals(Logic.getPincode())){
            return true;
        }
        return false;
    }

    public static void newPincode(String pincode) {
        Logic.setPincode(pincode);
    }
}
