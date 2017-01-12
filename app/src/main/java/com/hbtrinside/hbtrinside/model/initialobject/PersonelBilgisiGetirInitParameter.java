package com.hbtrinside.hbtrinside.model.initialobject;

import android.content.res.Resources;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by DoGan on 11.01.2017.
 */

public class PersonelBilgisiGetirInitParameter {

    public int getSicilKod() {
        return SicilKod;
    }

    public void setSicilKod(int sicilKod) {
        SicilKod = sicilKod;
    }
    public int SicilKod;
    public String TelNo;
    public JSONObject Form()
    {
        JSONObject Bilgiler = new JSONObject();
        try {
            Bilgiler.put("SicilKod", SicilKod);
            Bilgiler.put("TelNo", TelNo);
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        catch (Resources.NotFoundException e) {

            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return Bilgiler;

    }
}
