package com.hbtrinside.hbtrinside.activity;


import android.app.FragmentTransaction;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.hbtrinside.hbtrinside.fragment.MyListFragment;
import com.hbtrinside.hbtrinside.R;
import com.hbtrinside.hbtrinside.fragment.WelcomeFragment;
import com.hbtrinside.hbtrinside.core.Core;
import com.hbtrinside.hbtrinside.core.Sonuc;
import com.hbtrinside.hbtrinside.extended.ExtendedAppCompatActivity;
import com.hbtrinside.hbtrinside.model.ParameterObjects.gen_personel;
import com.hbtrinside.hbtrinside.model.initialobject.PersonelBilgisiGetirInitParameter;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends ExtendedAppCompatActivity {

    private Toolbar toolbar;
    private Boolean exit = false;
    private SharedPreferences prefs;
    private String m_Token;
    private String m_SicilKod;
    private String m_MesajKod;

    //deneme
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        m_Token = m_App.getm_OrtAlan().getString("Token", "");
        m_SicilKod = m_App.getm_OrtAlan().getString("SicilKod", "");
        m_MesajKod = m_App.getm_OrtAlan().getString("MesajKod","");
        if (m_Token.isEmpty()) {
            displayLogin();
            ActionBar actionBar = getSupportActionBar();
            actionBar.hide();
            ImageView imgFoto = (ImageView) findViewById(R.id.imageView);
            ImageView imgProf = (ImageView) findViewById(R.id.profile_image_kisi);
            imgFoto.setVisibility(View.INVISIBLE);
            imgProf.setVisibility(View.INVISIBLE);
        } else {
            Login();
        }
    }

    public void Login()
    {
        String Token= "";
        try {
            Token = m_App.TokenWebServis(getResources().getString(R.string.BaseURL).concat(getResources().getString(R.string.TokenURL)),m_SicilKod,m_MesajKod);
        }catch (Exception e)
        {
            Toast t = Toast.makeText(getApplicationContext(),
                    "Token'a giderken Hata oluştu",
                    Toast.LENGTH_SHORT);
            t.show();
        }
        PersonelBilgisiGetirInitParameter personelBilgisiGetirInitParameter = new PersonelBilgisiGetirInitParameter();
        personelBilgisiGetirInitParameter.SicilKod = Integer.parseInt(m_SicilKod);
        if(Token.isEmpty())
        {
            //TODO ALERT
            Toast t = Toast.makeText(getApplicationContext(),
                    "Girdiğiniz Bilgilerde Biri Bulunamadı.",
                    Toast.LENGTH_SHORT);
            t.show();
        }
        else
        {
            Toast t = Toast.makeText(getApplicationContext(),
                    "Giriş Başarılı....",
                    Toast.LENGTH_SHORT);
            t.show();
            m_App.getm_OrtAlanEditor().putString("Token",Token);
            m_App.getm_OrtAlanEditor().putString("SicilKod",m_SicilKod);
            m_App.getm_OrtAlanEditor().commit();
            Sonuc sonuc = m_App.WebServis(getResources().getString(R.string.BaseURL).concat(
                    getResources().getString(R.string.MobilURL)).concat(
                    getResources().getString(R.string.PersonelURL)),personelBilgisiGetirInitParameter.Form().toString());
            if(sonuc.sonucKod == 0)
            {
                PersonelGetir(sonuc);

            }
            else
            {
                //TODO ALERT
            }
            displayList();
        }
    }

    public void onBackPressed() {
        if (exit) {
            finish(); // finish activity
        } else {
            Toast.makeText(this, "Press Back again to Exit.",
                    Toast.LENGTH_SHORT).show();
            exit = true;
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    exit = false;
                }
            }, 3 * 1000);
        }

    }



    private void displayList() {
        ImageView imgProf = (ImageView) findViewById(R.id.profile_image_kisi);
        TextView tvAdSoyad = (TextView) findViewById(R.id.tv_Ad_Soyad);
        TextView tvOrgAciklama = (TextView) findViewById(R.id.tv_Sap_Org_Aciklama);
        Picasso.with(this).load("http://www.hbtrinside.com/images/" + m_Act.genPersonel.SICIL_KOD + ".jpg").into(imgProf);
        tvAdSoyad.setText(m_Act.genPersonel.AD + " " + m_Act.genPersonel.SOYAD);
        tvOrgAciklama.setText(m_Act.genPersonel.SAP_ORG_ACIKLAMA + "\n" + m_Act.genPersonel.POZISYON_ACIKLAMA);
        MyListFragment listFR = new MyListFragment();
        android.app.FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.fragment_container, listFR);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    private void displayLogin() {
        WelcomeFragment listFR = new WelcomeFragment();
        android.app.FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.fragment_container, listFR);
        transaction.addToBackStack(null);
        transaction.commit();
    }


    protected void PersonelGetir(Sonuc p_Sonuc) {
        try {
            if (p_Sonuc.sonucKod == 0) {
                JSONObject JObject = null;
                JSONArray jsArray = null;
                JSONObject jArray = null;
                try {
                    JObject = new JSONObject(p_Sonuc.sonucBilgisi);
                    jsArray = JObject.getJSONArray("Bilgi");
                    jArray = jsArray.getJSONObject(0);
                    gen_personel genPersonel = new gen_personel();
                    genPersonel.SICIL_KOD = jArray.optInt("SICIL_KOD", -1);
                    genPersonel.AD = jArray.getString("AD");
                    genPersonel.SOYAD = jArray.getString("SOYAD");
                    genPersonel.ORG_NO = jArray.optInt("ORG_NO", -1);
                    genPersonel.SW_BORDRO_ALT_BIRIM = jArray.optInt("SW_BORDRO_ALT_BIRIM", -1);
                    genPersonel.CINSIYET = jArray.getString("CINSIYET");
                    genPersonel.DOGUM_TARIHI = Core.StringToDate(jArray.getString("DOGUM_TARIHI"));
                    genPersonel.MEDENI_DURUM = jArray.getString("MEDENI_DURUM");
                    genPersonel.KAN_GRUBU = jArray.getString("KAN_GRUBU");
                    genPersonel.ISE_GIRIS_TARIHI = Core.StringToDate(jArray.getString("ISE_GIRIS_TARIHI"));
                    genPersonel.ISTEN_CIKIS_TARIHI = Core.StringToDate(jArray.getString("ISTEN_CIKIS_TARIHI"));
                    genPersonel.TC_KIMLIK_NO = jArray.getString("TC_KIMLIK_NO");
                    genPersonel.ADRES = jArray.getString("ADRES");
                    genPersonel.IL = jArray.getString("IL");
                    genPersonel.ILCE = jArray.getString("ILCE");
                    genPersonel.TEL1 = jArray.getString("TEL1");
                    genPersonel.TEL2 = jArray.getString("TEL2");
                    genPersonel.EMAIL = jArray.getString("EMAIL");
                    genPersonel.EGITIM = jArray.getString("EGITIM");
                    genPersonel.SW_AKTIF = jArray.optInt("SW_AKTIF", -1);
                    genPersonel.POZISYON_ACIKLAMA = jArray.getString("POZISYON_ACIKLAMA");
                    genPersonel.SAP_ORG_ACIKLAMA = jArray.getString("SAP_ORG_ACIKLAMA");
                    genPersonel.BMS_FABRIKA_KOD = jArray.optInt("BMS_FABRIKA_KOD", -1);
                    genPersonel.BMS_URUN_GRUP_KOD = jArray.optInt("BMS_URUN_GRUP_KOD", -1);
                    genPersonel.BMS_HAT_KOD = jArray.optInt("BMS_HAT_KOD", -1);
                    genPersonel.BMS_GRUP_KOD = jArray.optInt("BMS_GRUP_KOD", -1);
                    genPersonel.BMS_EKIP_KOD = jArray.optInt("BMS_EKIP_KOD", -1);
                    m_Act.genPersonel = genPersonel;
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } else {//TODO ALERT
                //  m_App.Alert("Hata",sonuc.sonucMesaj);
            }

        } catch (Resources.NotFoundException e) {
            //TODO ALERT
            // m_App.Alert("Login","NotFoundException");
            //e.printStackTrace();
        }

        //TODO: DOĞRU YERE TAŞINMASI GEREKİYOR
//        Intent intent = new Intent(LoginActivity.this, AnaEkranActivity.class);
//        LoginActivity.this.startActivity(intent);
//        LoginActivity.this.finish();
    }

}
