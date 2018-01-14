package com.example.sf.backend.api;

import org.json.JSONObject;

public class StringBaxterItemParser {
    public BaxterItem jsonParser(String jsonString) {
        BaxterItem baxterItem = new BaxterItem();
        try {
            JSONObject jsonObject = new JSONObject(jsonString);

            baxterItem.setPatientNaam(jsonObject.getString("patientNaam"));
            baxterItem.setAfspraakNummer(jsonObject.getString("afspraakNummer"));
            baxterItem.setDosis(jsonObject.getString("dosis"));
            baxterItem.setMedicijnNaam(jsonObject.getString("medicijnNaam"));
            baxterItem.setToediening(jsonObject.getString("Toediening"));
        } catch (Exception e) {
            System.out.println(e);
        }
        return baxterItem;
    }
}
