package com.hbtrinside.hbtrinside.extended;


import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Build;
import android.preference.PreferenceManager;
import android.util.Log;

import com.google.gson.Gson;
import com.hbtrinside.hbtrinside.activity.MainActivity;
import com.hbtrinside.hbtrinside.model.ParameterObjects.ResponseHelper;
import com.hbtrinside.hbtrinside.core.RunAsyncTask;
import com.hbtrinside.hbtrinside.core.Sonuc;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

/**
 * Created by Tolga Baris on 4.1.2017.
 */

public class ExtendedApplication extends Application {
    DataDownloadListener dataDownloadListener;
    private static Context m_Context;
    private SharedPreferences m_OrtAlan;
    private SharedPreferences.Editor m_OrtAlanEditor;
    private Intent m_Intent;
    private float m_density;

    public Intent getIntent() {
        return m_Intent;
    }

    public void setIntent(Intent p_intent) {
        m_Intent = p_intent;
    }

    public static interface DataDownloadListener {
        void dataDownloadedSuccessfully(String data);

    }

    public void onCreate() {

        super.onCreate();
        setm_OrtAlan(PreferenceManager.getDefaultSharedPreferences(getApplicationContext()));
        setm_OrtAlanEditor(getm_OrtAlan().edit());
        m_Context = this;
        androidDefaultUEH =
                Thread.getDefaultUncaughtExceptionHandler();
        Thread.setDefaultUncaughtExceptionHandler(exceptionHandler);
        getm_OrtAlanEditor().putString("IMAGE_DIRECTORY", getFilesDir().toString());
        getm_OrtAlanEditor().commit();
        setM_density();
    }


    @SuppressWarnings("unused")
    private Thread.UncaughtExceptionHandler androidDefaultUEH;

    private Thread.UncaughtExceptionHandler exceptionHandler = new Thread.UncaughtExceptionHandler() {
        public void uncaughtException(Thread thread, Throwable e) {
            StackTraceElement[] arr = e.getStackTrace();
            String report = e.toString() + "\n\n";
            report += "--------- Stack trace ---------\n\n";
            for (int i = 0; i < arr.length; i++) {
                report += "    " + arr[i].toString() + "\n";
            }
            report += "-------------------------------\n\n";
            report += "--------- Cause ---------\n\n";
            Throwable cause = e.getCause();
            if (cause != null) {
                report += cause.toString() + "\n\n";
                arr = cause.getStackTrace();
                for (int i = 0; i < arr.length; i++) {
                    report += "    " + arr[i].toString() + "\n";
                }
            }
            report += "-------------------------------\n\n";
            report += "-------------------------------\n\n";
            report += "--------- Device ---------\n\n";
            report += "Brand: " + Build.BRAND + "\n";
            report += "Device: " + Build.DEVICE + "\n";
            report += "Model: " + Build.MODEL + "\n";
            report += "Id: " + Build.ID + "\n";
            report += "Product: " + Build.PRODUCT + "\n";
            report += "-------------------------------\n\n";
            report += "--------- Firmware ---------\n\n";
            report += "SDK: " + Build.VERSION.SDK + "\n";
            report += "Release: " + Build.VERSION.RELEASE + "\n";
            report += "Incremental: " + Build.VERSION.INCREMENTAL + "\n";
            report += "-------------------------------\n\n";
            Log.e("Report ::", report);

            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            intent.putExtra("ERROR_ID", "1");
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);

            System.exit(0);// If you want to restart activity and
            // want to kill after crash.s
        }
    };
    Sonuc m_WebServisSonuc;

    public Sonuc WebServis(final String... Url) {
        m_WebServisSonuc = null;
        m_WebServisSonuc = new Sonuc();

        Thread thread = new Thread(new Runnable() {
            @SuppressWarnings("deprecation")
            @Override
            public void run() {
                String line;
                try {
                    String StrURL = Url[0];
                    URL url = new URL(StrURL); //Enter URL here
                    HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                    httpURLConnection.setDoOutput(true);
                    httpURLConnection.setRequestMethod("POST"); // here you are telling that it is a POST request, which can be changed into "PUT", "GET", "DELETE" etc.
                    httpURLConnection.setRequestProperty("Content-Type", "application/json"); // here you are setting the `Content-Type` for the data you are sending which is `application/json`
                    httpURLConnection.setRequestProperty("Authorization", "bearer ".concat(m_OrtAlan.getString("Token", "")));
                    httpURLConnection.connect();


                    DataOutputStream wr = new DataOutputStream(httpURLConnection.getOutputStream());
                    wr.writeBytes(Url[1]);
                    wr.flush();
                    wr.close();
                    BufferedReader br = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream(), "utf-8"));
                    line = null;
                    sb = new StringBuilder();
                    while ((line = br.readLine()) != null) {
                        sb.append(line + "\n");
                    }
                    br.close();
                    System.out.println("" + sb.toString());


                } catch (Exception e) {
                    Log.e("ERROR", "Server'a Bağlanılamadı ");
                    m_WebServisSonuc.sonucMesaj = e.getMessage();

                }
                if (sb.toString().contains("Sonuc")) {
                    JSONObject JObject = null;
                    try {
                        Log.e("INIT", Url.toString());
                        Log.e("RESULT", sb.toString());
                        JObject = new JSONObject(sb.toString());
                        JSONObject SonucJSONObject = JObject.getJSONObject("Sonuc");
                        m_WebServisSonuc.sonucKod = SonucJSONObject.getInt("SonucKod");
                        m_WebServisSonuc.sonucMesaj = SonucJSONObject.getString("SonucMesaj");
                        m_WebServisSonuc.ekran = SonucJSONObject.getInt("Ekran") == 0 ? false : true;
                        m_WebServisSonuc.sonucBilgisi = sb.toString();
                        Log.e("WEBSERVIS", m_WebServisSonuc.sonucBilgisi);
                        // return
                        // m_WebServisSonuc;
                    } catch (Exception e) {
                        m_WebServisSonuc = new Sonuc();
                    }

                } else {
                    m_WebServisSonuc = new Sonuc();
                }


            }
        });

        thread.start();
        try {
            thread.join();
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return m_WebServisSonuc;
    }


    StringBuilder sb;
    JSONObject JObject = null;
    public String TokenWebServis(final String... Url) {
        m_WebServisSonuc = null;
        m_WebServisSonuc = new Sonuc();


        Thread thread = new Thread(new Runnable() {
            @SuppressWarnings("deprecation")
            @Override
            public void run() {
                String line = "";
                try {

                    String URL = Url[0];
                    Map<String, Object> params = new LinkedHashMap<>();
                    params.put("password", Url[1]);
                    params.put("grant_type", "password");

                    StringBuilder postData = new StringBuilder();
                    for (Map.Entry<String, Object> param : params.entrySet()) {
                        if (postData.length() != 0) postData.append('&');
                        postData.append(URLEncoder.encode(param.getKey(), "UTF-8"));
                        postData.append('=');
                        postData.append(URLEncoder.encode(String.valueOf(param.getValue()), "UTF-8"));
                    }
                    byte[] postDataBytes = postData.toString().getBytes("UTF-8");

//                        JSONObject jsonParam = new JSONObject();
//                        jsonParam.put("username", "5554787457");
//                        //TODO Token gelecek
//                        jsonParam.put("password", "");
//                        jsonParam.put("grant_type", "password");
//                        byte[] postData       = jsonParam.toString().getBytes();
                    int postDataLength = postDataBytes.length;
                    URL obj = new URL(URL);
                    HttpURLConnection con = (HttpURLConnection) obj.openConnection();// must be set
                    try {


                        // must be set
                        con.setDoOutput(true);
                        con.setInstanceFollowRedirects(false);
                        con.setRequestMethod("POST");
                        con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                        con.setRequestProperty("charset", "utf-8");
                        con.setRequestProperty("Content-Length", Integer.toString(postDataLength));
                        con.setUseCaches(false);
                        try {
                            DataOutputStream wr = new DataOutputStream(con.getOutputStream());
                            wr.write(postDataBytes);
                            BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream(), "UTF-8"));

                            sb = new StringBuilder();
                            while ((line = br.readLine()) != null) {
                                sb.append(line + "\n");
                            }
                            br.close();
                            m_WebServisSonuc.sonucBilgisi = sb.toString();

                        } catch (Exception e) {
                            Log.e("ERROR", e.getMessage());
                            m_WebServisSonuc.sonucMesaj = e.getMessage();

                        }
                        Log.e("ERROR", "");

                    } catch (Exception e) {
                        Log.e("ERROR", e.getMessage());
                        m_WebServisSonuc.sonucMesaj = e.getMessage();

                    }
                } catch (Exception e) {
                    Log.e("ERROR", "Server'a Bağlanılamadı ");
                    m_WebServisSonuc.sonucMesaj = e.getMessage();

                }

                if (m_WebServisSonuc.sonucBilgisi.contains("access_token")) {

                    try {
                        Log.e("INIT", Url.toString());
                        Log.e("RESULT", m_WebServisSonuc.sonucBilgisi);
                        JObject = new JSONObject(m_WebServisSonuc.sonucBilgisi);
                        m_WebServisSonuc.sonucBilgisi = JObject.getString("access_token");
                        Log.e("WEBSERVIS", m_WebServisSonuc.sonucBilgisi);

                        // return
                        // m_WebServisSonuc;
                    } catch (Exception e) {
                        m_WebServisSonuc = new Sonuc();
                    }

                } else {
                    m_WebServisSonuc = new Sonuc();
                }


            }
        });

        thread.start();
        try {
            thread.join();
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }


        try {
            return JObject.getString("access_token");
        } catch (JSONException e) {
            e.printStackTrace();
            return null;

        }
    }

    public boolean isConnectedToServer(String url, int timeout) {
        try {
            URL myUrl = new URL(url.replace("api/", "") + "help");
            URLConnection connection = myUrl.openConnection();
            connection.setConnectTimeout(timeout);
            connection.connect();
            return true;
        } catch (Exception e) {
            // Handle your exceptions
            return false;
        }
    }

    public class WebServisKontrol extends AsyncTask<String, Void, String> {
        boolean m_WebServisKontrol = false;
        DataDownloadListener dataDownloadListener;

        public void setDataDownloadListener(DataDownloadListener dataDownloadListener) {
            this.dataDownloadListener = dataDownloadListener;
        }

        @Override
        protected String doInBackground(String... Url) {
            try {
                URL url = new URL(Url[0]);
                URLConnection connection = url.openConnection();

                connection.setConnectTimeout(Integer.parseInt(Url[1]));
                connection.connect();
                m_WebServisKontrol = true;
            } catch (Exception e) {
                e.printStackTrace();
                m_WebServisKontrol = false;
            }
            return null;
        }

        protected void onPostExecute(final String restore) {

        }

    }

    private void parseResponse(String response) {
        ResponseHelper responseHelper;
        Gson gson = new Gson();
        try {
/*            JSONObject reader = new JSONObject(response);
            String weather = reader.getString("access_token");
            String deneme = reader.getString("access_token");*/
            responseHelper = gson.fromJson(response, ResponseHelper.class);
            System.out.println(responseHelper.getAccess_token());

        } catch (Exception e) {
            System.out.print(e.toString());
        }

    }

    public boolean WSKontrol(String p_URL, String p_TimeOut) {
        WebServisKontrol wSKontrol = new WebServisKontrol();
        String str = "";
        try {
            str = wSKontrol.execute(p_URL, p_TimeOut).get();
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (ExecutionException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return wSKontrol.m_WebServisKontrol;
    }

    public boolean WSKontrolDAsync(String p_URL, String p_TimeOut) {

        try {
            URL url = new URL(p_URL);
            URLConnection connection = url.openConnection();
            connection.setConnectTimeout(Integer.parseInt(p_TimeOut));
            connection.getContent();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }


    public boolean AgBaglantisiKontrol() {
        boolean wifiBaglantisi = false;
        boolean g3Baglantisi = false;

        ConnectivityManager baglantiYonetici = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo[] agBilgiListesi = baglantiYonetici.getAllNetworkInfo();
        for (NetworkInfo agBilgisi : agBilgiListesi) {
            if (agBilgisi.getTypeName().equalsIgnoreCase("WIFI"))
                if (agBilgisi.isConnected())
                    wifiBaglantisi = true;
            if (agBilgisi.getTypeName().equalsIgnoreCase("MOBILE"))
                if (agBilgisi.isConnected())
                    g3Baglantisi = true;
        }
        return wifiBaglantisi || g3Baglantisi;
    }

    public boolean VeritabaniKontrol() {
        SQLiteDatabase veritabani = null;
        try {
            veritabani = SQLiteDatabase.openDatabase(getApplicationContext().getDatabasePath("PROBBYS").toString(), null, SQLiteDatabase.OPEN_READONLY);
            veritabani.close();
            return true;
        } catch (Exception e) {
            // Log an info message stating database doesn't exist.
        }

        return false;
    }


    public HashMap<String, String> StringToHashMap(String p_SharedStore) {
        Date date = new Date();
        long time = date.getTime() / 60000;
        HashMap<String, String> guncellemelistesi = new HashMap<String, String>();

        try {
            if (!(p_SharedStore.isEmpty()) && !(p_SharedStore.equals(""))) {
                p_SharedStore = p_SharedStore.substring(1, p_SharedStore.length() - 1);
                String[] keyValuePairs = p_SharedStore.split(",");

                for (String pair : keyValuePairs) {
                    String[] entry = pair.split("=");
                    if (time - Long.valueOf(entry[1].trim()) < 30) {
                        guncellemelistesi.put(entry[0].trim(), entry[1].trim());
                    }
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            guncellemelistesi = new HashMap<String, String>();
        }
        return guncellemelistesi;
    }


    public SharedPreferences getm_OrtAlan() {
        return m_OrtAlan;
    }

    public void setm_OrtAlan(SharedPreferences m_OrtAlan) {
        this.m_OrtAlan = m_OrtAlan;
    }


    public SharedPreferences.Editor getm_OrtAlanEditor() {
        return m_OrtAlanEditor;
    }

    public void setm_OrtAlanEditor(SharedPreferences.Editor m_OrtAlanEditor) {
        this.m_OrtAlanEditor = m_OrtAlanEditor;
    }


    public void setm_Context(Context p_Context) {
        this.m_Context = p_Context;
    }

    public Context getm_Context() {
        return m_Context;
    }


    public float getM_density() {
        return m_density;
    }

    public void setM_density() {
        this.m_density = getResources().getDisplayMetrics().density;
    }


    public class StackObject {
        int ID;
        char Type;

    }

    protected class RunAsync extends RunAsyncTask {
        Activity m_Act;

        public RunAsync(Activity p_Act) {
            super(p_Act);
            this.m_Act = p_Act;
        }
    }

    public void runAsync(Activity m_Act, Runnable... runnables) {
        RunAsyncTask runner = new RunAsyncTask(m_Act);
        // try
        // {
        runner.execute(runnables);
        // }
        // catch (InterruptedException e)
        // {
        // // TODO Auto-generated catch block
        // e.printStackTrace();
        // }
        // catch (ExecutionException e)
        // {
        // // TODO Auto-generated catch block
        // e.printStackTrace();
        // }

    }


}