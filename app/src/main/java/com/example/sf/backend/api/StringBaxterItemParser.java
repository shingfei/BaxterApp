package com.example.sf.backend.api;

import org.json.JSONArray;
import org.json.JSONObject;

public class StringBaxterItemParser {
    public BaxterItem parseBaxterItem(String jsonString, int index) {
        BaxterItem tempBaxterItem = new BaxterItem();

        try {
            JSONObject jsonObject = new JSONObject(jsonString);

            tempBaxterItem.setPatientNaam(jsonObject.getJSONArray("patientNamen").getString(index));
            tempBaxterItem.setMedicijnNaam(jsonObject.getJSONArray("medicijnNamen").getString(index));
            tempBaxterItem.setToediening(jsonObject.getJSONArray("toediening").getString(index));
            tempBaxterItem.setDosis(jsonObject.getJSONArray("dosis").getString(index));
            tempBaxterItem.setBarcode(jsonObject.getJSONArray("barcode").getString(index));
            tempBaxterItem.setAfspraakNummer(jsonObject.getJSONArray("afspraakNummer").getInt(index));
        } catch (Exception e) {
            System.out.println(e);
        }

        return tempBaxterItem;
    }

    public int getLengthArray(String inputString) {
        int length = 0;
        try {
            JSONObject jsonObject = new JSONObject(inputString);

            length = jsonObject.getJSONArray("patientNamen").length();
        } catch (Exception e) {
            System.out.println(e);
        }
        return length;
    }

    public String updateMessage(Round round) {
        JSONObject jsonObject = new JSONObject();
        JSONArray array1 = new JSONArray(), array2 = new JSONArray();

        for (int i = 0; i < round.roundArray.length; i++) {
            if (round.roundArray[i].getCheck()) {
                try {
                    array1.put(round.roundArray[i].getAfspraakNummer());
                    array2.put(2);

                } catch (Exception e) {
                    System.out.println(e);
                }
            }
        }
        try {
            jsonObject.put("afspraakNummer", array1);
            jsonObject.put("verzorgerNummer", array2);
        }
        catch (Exception e) {
            System.out.println(e);
        }

        return jsonObject.toString();
    }
}
