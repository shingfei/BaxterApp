package com.example.sf.backend.api;

/**
 * Created by User on 1/15/2018.
 */

public class Round {
    public BaxterItem[] roundArray;

    public BaxterItem[] getRoundArray() {
        return roundArray;
    }

    public void setRoundArray(BaxterItem[] b) {
        roundArray = b;
    }

    public BaxterItem getBaxter(String barcode) {
        for (int i = 0; i < roundArray.length; i++) {
            if (roundArray[i].getBarcode().equals(barcode)) {
                return roundArray[i];
            }
        }
        return null;
    }
}
