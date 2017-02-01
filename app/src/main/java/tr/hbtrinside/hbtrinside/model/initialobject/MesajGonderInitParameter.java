package tr.hbtrinside.hbtrinside.model.initialobject;

import android.content.res.Resources;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by DoGan on 11.01.2017.
 */

public class MesajGonderInitParameter {
    public int GonderenSicilKod;
    public String Mesaj;
    public int OrgNo;
    public int AliciSicilKod;
    public JSONObject Form()
    {
        JSONObject Bilgiler = new JSONObject();
        try {
            Bilgiler.put("GonderenSicilKod", GonderenSicilKod);
            Bilgiler.put("Mesaj", Mesaj);
            Bilgiler.put("OrgNo", OrgNo);
            Bilgiler.put("AliciSicilKod", AliciSicilKod);
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
