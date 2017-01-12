package com.hbtrinside.hbtrinside.model.initialobject;

import android.content.res.Resources;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by DoGan on 11.01.2017.
 */

public class MesajGetirInitParameter {
    public int MesajId;
    //1 Pozitif 10 tane // -1 Negatif 10 tane
    public int MesajGuncellemeYon;
    public int SicilKod;
    public JSONObject Form()
    {
        JSONObject Bilgiler = new JSONObject();
        try {
            Bilgiler.put("MesajId", MesajId);
            Bilgiler.put("MesajGuncellemeYon", MesajGuncellemeYon);
            Bilgiler.put("SicilKod", SicilKod);
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
