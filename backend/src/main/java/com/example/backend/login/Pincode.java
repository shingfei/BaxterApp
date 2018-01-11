package com.example.backend.login;
import java.util.Scanner;
import java.util.regex.Pattern;
import com.example.backend.main.Logic;
/**
 * Created by PascalRoose on 11-Jan-18.
 */

public class Pincode
{
    public static void setup() {
        while (true) {
            if(newPincode()){
                break;
            }
        }
    }

    @SuppressWarnings("resource")
    public static boolean check(){
        for(int i = 0; i<3; i++){
            Scanner keyboard = new Scanner(System.in);
            System.out.println("Voer uw pincode in:");
            String pincode = keyboard.nextLine();
            if(pincode.equals(Logic.getPincode())){
                System.out.println("Pincode is correct!");
                return true;
            }
            System.out.println("Pincode incorrect");
        }
        Logic.setVerzorger(null);
        Logic.setPincode(null);
        System.out.println("Pincode 3x fout ingevoerd. Log opnieuw in");
        return false;
    }

    private static boolean newPincode() {
        @SuppressWarnings("resource")
        Scanner keyboard = new Scanner(System.in);
        System.out.println("Voer uw nieuwe pincode in:");
        String pincode = keyboard.nextLine();
        if (Pattern.matches("[0-9]+", pincode) && pincode.length() == 4) {
            System.out.println("Herhaal uw pincode:");
            String repeatPincode = keyboard.nextLine();
            if (repeatPincode.equals(pincode)) {
                Logic.setPincode(pincode);
                System.out.println("Pincode ingesteld");
                return true;
            } else {
                System.out.println("Pincode komt niet overeen met uw vorige pincode");
            }
        } else {
            System.out.println("Pincode moet uit 4 getallen bestaan");
        }
        return false;
    }
}
