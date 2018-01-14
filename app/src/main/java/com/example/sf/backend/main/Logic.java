package com.example.sf.backend.main;

import com.example.sf.backend.entities.Agenda;
import com.example.sf.backend.entities.Verzorger;

public class Logic
{

        private static Verzorger verzorger;
        private static String pincode;
        private static Agenda agenda;

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
