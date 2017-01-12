package com.hbtrinside.hbtrinside.core;

import android.content.ContentValues;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.text.TextUtils;
import android.webkit.WebSettings;
import android.webkit.WebView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class Core
{

    // PARAMETRE NULL MI
    public static <T> boolean IsNvl(T p_arg0)
    {
        try
        {
            return (p_arg0 == null) ? true : false;
        } catch (Exception e)
        {
            return false;
        }
    }
    
    // PARAMETRE NULL �SE 2. PARAMETREY� D�ND�R ORACLE NVL �LE AYNI MANTIK
    public static String CursorToString(Cursor p_Cursor, String p_Params)
    {
        List<Integer> paramsList = new ArrayList<Integer>();
        if (p_Cursor.moveToFirst())
        {
            do
            {
                paramsList.add(Integer.parseInt(p_Cursor.getString(p_Cursor
                        .getColumnIndex("ID"))));
            } while (p_Cursor.moveToNext());
        }
        String toParams = TextUtils.join(",", paramsList);
        return toParams;
    }
    
    public static <T> T Nvl(T p_arg0, T p_arg1)
    {
        return (p_arg0 == null) ? p_arg1 : p_arg0;
    }
    
    // PARAMETRE .NETTEN GELEN TAR�H B�LG�S�N� TEM�ZLER
    public static String DateTime(String str)
    {
        if (str == null)
            return "";
        str = str.replace(".", ",");
        String[] sss = str.split("T");
        if (sss.length < 2)
        {
            sss[1] = sss[1].split(",")[0];
            str = sss[0].concat(" ").concat(sss[1]);
            return str;
        } else
        {
            return sss[0];
        }
    }
    
    // WEB SERV�STEN D�NEN JSON YAPIDA GELEN VER�Y� CONTENT VALUE'YA
    // D�ND�R�R
    public static ContentValues ContentValuesfromJSON(final String m_Json)
    {
        JSONObject JObject = null;
        Map<String, Object> StrMap = null;
        try
        {
            JObject = new JSONObject(m_Json);
            StrMap = Core.toMap(JObject);
        } catch (JSONException e1)
        {
            e1.printStackTrace();
        }
        ContentValues values = new ContentValues();
        for (Map.Entry<String, Object> mapEntry : StrMap.entrySet())
        {
            try
            {
                if (!(mapEntry.getKey().toString().substring(0, 2).equals("FK")))
                {
                    if (mapEntry.getValue() != null)
                        values.put(mapEntry.getKey().toString(), mapEntry
                                .getValue().toString());
                }
            } catch (Exception e)
            {
                e.printStackTrace();
            }
        }
        return values;
    }
    
    // JsonObjectToMap ContentValuesfromJSON FONKS�YONUNUN ALT
    // FONKS�YONLARIDIR BU NEDENLE DI�ARI A�IK DE��LD�R.
    // toMap ContentValuesfromJSON FONKS�YONUNUN ALT FONKS�YONLARIDIR BU
    // NEDENLE DI�ARI A�IK DE��LD�R.
    // toList ContentValuesfromJSON FONKS�YONUNUN ALT FONKS�YONLARIDIR BU
    // NEDENLE DI�ARI A�IK DE��LD�R.
    // fromJson ContentValuesfromJSON FONKS�YONUNUN ALT FONKS�YONLARIDIR BU
    // NEDENLE DI�ARI A�IK DE��LD�R.
    private static Map<String, Object> JsonObjectToMap(
            final JSONObject p_object, final String p_key) throws JSONException
    {
        return toMap(p_object.getJSONObject(p_key));
    }
    
    private static Map<String, Object> toMap(JSONObject p_object)
            throws JSONException
    {
        Map<String, Object> map = new HashMap<String, Object>();
        Iterator<?> keys = p_object.keys();
        while (keys.hasNext())
        {
            String key = (String) keys.next();
            map.put(key, fromJson(p_object.get(key)));
        }
        return map;
    }
    
    private static List<Object> toList(JSONArray p_array) throws JSONException
    {
        List<Object> list = new ArrayList<Object>();
        for (int i = 0; i < p_array.length(); i++)
        {
            list.add(fromJson(p_array.get(i)));
        }
        return list;
    }
    
    public static WebView WebViewLoad(WebView vw, String str)
    {
        WebSettings webViewAyar = vw.getSettings();
        webViewAyar.setDefaultTextEncodingName("utf-8");
        vw.loadData(str, "text/html; charset=utf-8", null);
        return vw;
    }
    
    private static Object fromJson(Object p_json) throws JSONException
    {
        if (p_json == JSONObject.NULL)
        {
            return null;
        } else if (p_json instanceof JSONObject)
        {
            return toMap((JSONObject) p_json);
        } else if (p_json instanceof JSONArray)
        {
            return toList((JSONArray) p_json);
        } else
        {
            return p_json;
        }
    }
    
    public static Calendar StringToDate(String p_DateTime)
    {
        // Date date = null;
        // SimpleDateFormat format = new
        // SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        // try
        // {
        // date = format.parse(p_DateTime);
        // System.out.println(date);
        // }
        // catch (ParseException e)
        // {
        // // TODO Auto-generated catch block
        // e.printStackTrace();
        // }
        // return date;
        if(p_DateTime.equals("null"))
        {
            return null;
        }
            Calendar cal = Calendar.getInstance();
        try
        {
            SimpleDateFormat sdf = new SimpleDateFormat(
                    "yyyy-MM-dd'T'HH:mm:ss", new Locale("tr", "TR"));
            Date d = sdf.parse(p_DateTime);
            cal = Calendar.getInstance();
            cal.setTime(d);
            return cal;
        } catch (Exception e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return cal;
    }

    public static final String DateToString(Calendar cal)// , String dateformat)
    {
        if (cal != null)
        {
            StringBuffer ret = new StringBuffer();
            // if (dateformat.equals("dd/MM/yyyy"))
            {
                String date = null;
                int dt = cal.get(Calendar.DATE);
                if (dt < 10)
                {
                    date = "0" + dt;
                } else
                {
                    date = "" + dt;
                }
                ret.append(date);
                ret.append(".");
                String month = null;
                int mo = cal.get(Calendar.MONTH)+1 ; /*
                                                       * Calendar month is zero
                                                       * indexed, string months
                                                       * are not
                                                       */
                if (mo < 10)
                {
                    month = "0" + mo;
                } else
                {
                    month = "" + mo;
                }
                ret.append(month);
                ret.append(".");
                ret.append(cal.get(Calendar.YEAR));
                ret.append(" ");
                ret.append(DayOfWeek(cal.get(Calendar.DAY_OF_WEEK)));
            }
            return ret.toString();
        }
        return "";
    }
    
     public static final String DateToShortString(Calendar cal)// , String dateformat)
     {
         if (cal != null)
         {
             StringBuffer ret = new StringBuffer();
             // if (dateformat.equals("dd/MM/yyyy"))
             {
                 String date = null;
                 int dt = cal.get(Calendar.DATE);
                 if (dt < 10)
                 {
                     date = "0" + dt;
                 } else
                 {
                     date = "" + dt;
                 }
                 ret.append(date);
                 ret.append("/");
                 String month = null;
                 int mo = cal.get(Calendar.MONTH)+1 ; /*
                                                        * Calendar month is zero
                                                        * indexed, string months
                                                        * are not
                                                        */
                 if (mo < 10)
                 {
                     month = "0" + mo;
                 } else
                 {
                     month = "" + mo;
                 }
                 ret.append(month);
                 ret.append("/");
                 ret.append(cal.get(Calendar.YEAR));
             }
             return ret.toString();
         }
         return "";
     }
    public static final String _Net_DateToShortString(Calendar cal)// , String dateformat)
    {
        if (cal != null)
        {
            StringBuffer ret = new StringBuffer();
            // if (dateformat.equals("dd/MM/yyyy"))
            {
                ret.append(cal.get(Calendar.YEAR));
                ret.append("-");
                String month = null;
                int mo = cal.get(Calendar.MONTH)+1 ; 
                 if (mo < 10)
                {
                    month = "0" + mo;
                } else
                {
                    month = "" + mo;
                }
                ret.append(month);
                ret.append("-");
                String date = null;
                int dt = cal.get(Calendar.DATE);
                if (dt < 10)
                {
                    date = "0" + dt;
                } else
                {
                    date = "" + dt;
                }
                ret.append(date);
            }
            return ret.toString();
        }
        return "";
    }
    
    public static final String TimeToString(Calendar cal)// , String dateformat)
    {
        if (cal != null)
        {
            String ret ="";
            int sayi = cal.get(Calendar.HOUR_OF_DAY); 
            ret =  String.format("%02d", sayi);
            ret+=":";
            sayi = cal.get(Calendar.MINUTE); 
            ret += String.format("%02d", sayi);
            return ret; 
        }
        return "";
    }
    
    public static Calendar SetCalendar(int p_Year,int p_Month,int p_Day,int p_Hour,int p_Minute)
    {
        String year  = String.valueOf(p_Year);
        String month = String.format("%02d", p_Month+1);
        String day   = String.format("%02d", p_Day);
        String hour  = String.format("%02d", p_Hour);
        String minute= String.format("%02d", p_Minute);
       
        String str = year+"-"+month+"-"+day+"T"+hour+":"+minute+":00";

        return StringToDate(str);
    }
    
    public static String DayOfWeek(int day)
    {
    switch (day) {
        case Calendar.MONDAY:
            return "Pazartesi";
        case Calendar.TUESDAY:
            return "Salı";
        case Calendar.WEDNESDAY:
            return "Çarşamba";
        case Calendar.THURSDAY:
            return "Perşembe";
        case Calendar.FRIDAY:
            return "Cuma";
        case Calendar.SATURDAY:
            return "Cumartesi";
        case Calendar.SUNDAY:  
            return "Pazar";
    }
    return "";
    }
    
    public static boolean VeriKontrol(String p_Directory, String p_KeyWord)
    {
        if (!(IsNvl(p_KeyWord)))
        {
            if (!(p_KeyWord.equals("")))
            {
                File sdImageStorageDir = new File(p_Directory);
                File[] FileList = sdImageStorageDir.listFiles();
                for (int i = 0; i < FileList.length; i++)
                {
                    File file = FileList[i];
                    String filePath = file.getPath();
                    if (filePath.equals(p_Directory + "/" + p_KeyWord))
                    {
                        if (file.length() == 0)
                        {
                            file.delete();
                            return false;
                        }
                        return true;
                    }
                }
            }
        }
        return false;
    }
    
    public static int VeriTemizle(String p_Directory,
            ArrayList<String> p_KeyWord)
    {
        int deleted = 0;
        if (p_KeyWord != null)
        {
            if (p_KeyWord.size() > 0)
            {
                for (int i = 0; i < p_KeyWord.size(); i++)
                {
                    File dir = new File(p_Directory);
                    File file = new File(dir, p_KeyWord.get(i));
                    if (file.delete())
                    {
                        deleted++;
                    }
                }
            }
        }
        return deleted;
    }

    public static Bitmap ResimBoyutlandir(Bitmap p_bm, int p_newHeight,
            int p_newWidth)
    {
        int width = p_bm.getWidth();
        int height = p_bm.getHeight();
        float scaleWidth = ((float) p_newWidth) / width;
        float scaleHeight = ((float) p_newHeight) / height;
        // CREATE A MATRIX FOR THE MANIPULATION
        Matrix matrix = new Matrix();
        // RESIZE THE BIT MAP
        matrix.postScale(scaleWidth, scaleHeight);
        // "RECREATE" THE NEW BITMAP
        Bitmap resizedBitmap = Bitmap.createBitmap(p_bm, 0, 0, width, height,
                matrix, false);
        return resizedBitmap;
    }
    
    public static Bitmap ResimYukle(String p_IMAGE_DIRECTORY, String p_RESIM_ADI)
    {
        if ((VeriKontrol(p_IMAGE_DIRECTORY, p_RESIM_ADI)))
        {
            Bitmap bm = BitmapFactory.decodeFile(p_IMAGE_DIRECTORY + "/"
                    + p_RESIM_ADI);
            if ((!IsNvl(bm)))
            {
                return bm;
            } else
            {
                return null;
            }
        } else
        {
            return null;
        }
    }

}
