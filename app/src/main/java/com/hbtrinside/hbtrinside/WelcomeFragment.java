package com.hbtrinside.hbtrinside;

import android.app.FragmentTransaction;
import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.hbtrinside.hbtrinside.core.Core;
import com.hbtrinside.hbtrinside.core.Sonuc;
import com.hbtrinside.hbtrinside.extended.ExtendedApplication;
import com.hbtrinside.hbtrinside.extended.ExtendedFragment;
import com.hbtrinside.hbtrinside.model.gen_personel;
import com.hbtrinside.hbtrinside.model.initialobject.PersonelBilgisiGetirInitParameter;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class WelcomeFragment extends ExtendedFragment implements View.OnClickListener {
    private Context context;
    private WelcomeFragment ref;
    private Button LoginBtn ;
    View rootView;
    EditText EtMobilNo;
    EditText EtSicilKod;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = getActivity().getApplicationContext();
        ref = this;
    }

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_login, container, false);
        Button LoginBtn = (Button) rootView.findViewById(R.id.loginbtn);
        EtMobilNo  =(EditText) rootView.findViewById(R.id.edt_phone);
        EtSicilKod =(EditText) rootView.findViewById(R.id.edt_sicil_kod) ;
        LoginBtn.setOnClickListener(this);
        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    String Token ;
    @Override
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.loginbtn:
                String StrMobilNo = EtMobilNo.getText().toString();
                String StrSicilKod = EtSicilKod.getText().toString();
                Login( StrSicilKod, StrMobilNo);
                break;
        }
    }
    public void Login(String StrSicilKod,String StrMobilNo)
    {
        String Token= "";
        try {
            Token = m_App.TokenWebServis(getResources().getString(R.string.BaseURL).concat(getResources().getString(R.string.TokenURL)), StrMobilNo);
        }catch (Exception e)
        {
            Toast t = Toast.makeText(getActivity().getApplicationContext(),
                    "Token'a giderken Hata oluştu",
                    Toast.LENGTH_SHORT);
            t.show();
        }
        PersonelBilgisiGetirInitParameter personelBilgisiGetirInitParameter = new PersonelBilgisiGetirInitParameter();
        personelBilgisiGetirInitParameter.SicilKod = Integer.parseInt(StrSicilKod);
        personelBilgisiGetirInitParameter.TelNo = StrMobilNo;
        if(Token.isEmpty())
        {
            //TODO ALERT
            Toast t = Toast.makeText(getActivity().getApplicationContext(),
                    "Girdiğiniz Bilgilerde Biri Bulunamadı.",
                    Toast.LENGTH_SHORT);
            t.show();
        }
        else
        {
            Toast t = Toast.makeText(getActivity().getApplicationContext(),
                    "Giriş Başarılı....",
                    Toast.LENGTH_SHORT);
            t.show();
            m_App.getm_OrtAlanEditor().putString("Token",Token);
            m_App.getm_OrtAlanEditor().putString("SicilKod",StrSicilKod);
            m_App.getm_OrtAlanEditor().putString("MobilNo",StrMobilNo);
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
                    genPersonel.SIFRE = jArray.getString("SIFRE");
                    genPersonel.ULKE_KOD = jArray.getString("ULKE_KOD");
                    genPersonel.ORG_NO = jArray.optInt("ORG_NO", -1);
                    genPersonel.SW_BORDRO_ALT_BIRIM = jArray.optInt("SW_BORDRO_ALT_BIRIM", -1);
                    genPersonel.POZISYON = jArray.getString("POZISYON");
                    genPersonel.CINSIYET = jArray.getString("CINSIYET");
                    genPersonel.DOGUM_TARIHI = Core.StringToDate(jArray.getString("DOGUM_TARIHI"));
                    genPersonel.MEDENI_DURUM = jArray.getString("MEDENI_DURUM");
                    genPersonel.KAN_GRUBU = jArray.getString("KAN_GRUBU");
                    genPersonel.ISE_GIRIS_TARIHI = Core.StringToDate(jArray.getString("ISE_GIRIS_TARIHI"));
                    genPersonel.ISTEN_CIKIS_TARIHI = Core.StringToDate(jArray.getString("ISTEN_CIKIS_TARIHI"));
                    genPersonel.SIRKET_NO = jArray.optInt("SIRKET_NO", -1);
                    genPersonel.TC_KIMLIK_NO = jArray.getString("TC_KIMLIK_NO");
                    genPersonel.SSK_NO = jArray.getString("SSK_NO");
                    genPersonel.ADRES = jArray.getString("ADRES");
                    genPersonel.IL = jArray.getString("IL");
                    genPersonel.ILCE = jArray.getString("ILCE");
                    genPersonel.TEL1 = jArray.getString("TEL1");
                    genPersonel.TEL2 = jArray.getString("TEL2");
                    genPersonel.EMAIL = jArray.getString("EMAIL");
                    genPersonel.EGITIM = jArray.getString("EGITIM");
                    genPersonel.MUHASEBE_KODU = jArray.optInt("MUHASEBE_KODU", -1);
                    genPersonel.SW_USTALIK = jArray.optInt("SW_USTALIK", -1);
                    genPersonel.NAR_HESABI_GRUBU = jArray.getString("NAR_HESABI_GRUBU");
                    genPersonel.SW_MERKEZ_URETIM = jArray.optInt("SW_MERKEZ_URETIM", -1);
                    genPersonel.POSTA_KOD = jArray.optInt("POSTA_KOD", -1);
                    genPersonel.SICIL_KAYNAK = jArray.getString("SICIL_KAYNAK");
                    genPersonel.YONETICI_1 = jArray.getString("YONETICI_1");
                    genPersonel.YONETICI_2 = jArray.getString("YONETICI_2");
                    genPersonel.YONETICI_3 = jArray.getString("YONETICI_3");
                    genPersonel.SORUMLU_ORG_1 = jArray.optInt("SORUMLU_ORG_1", -1);
                    genPersonel.SORUMLU_ORG_2 = jArray.optInt("SORUMLU_ORG_2", -1);
                    genPersonel.SORUMLU_ORG_3 = jArray.optInt("SORUMLU_ORG_3", -1);
                    genPersonel.CALISMA_GRUBU = jArray.getString("CALISMA_GRUBU");
                    genPersonel.EKLEYEN_KULLANICI = jArray.optInt("EKLEYEN_KULLANICI", -1);
                    genPersonel.EKLENEN_TARIH = Core.StringToDate(jArray.getString("EKLENEN_TARIH"));
                    genPersonel.GUNCELLEYEN_KULLANICI = jArray.optInt("GUNCELLEYEN_KULLANICI", -1);
                    genPersonel.GUNCELLENEN_TARIH = Core.StringToDate(jArray.getString("GUNCELLENEN_TARIH"));
                    genPersonel.SW_AKTIF = jArray.optInt("SW_AKTIF", -1);
                    genPersonel.POZISYON_ACIKLAMA = jArray.getString("POZISYON_ACIKLAMA");
                    genPersonel.SAP_ORG_KOD = jArray.optInt("SAP_ORG_KOD", -1);
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



    private void displayList() {
        android.support.v7.app.ActionBar actionBar = ((AppCompatActivity)getActivity()).getSupportActionBar();
        actionBar.show();
        ImageView imgFoto = (ImageView) getActivity().findViewById(R.id.imageView);
        ImageView imgProf = (ImageView) getActivity().findViewById(R.id.profile_image_kisi);
        imgFoto.setVisibility(View.VISIBLE);
        imgProf.setVisibility(View.VISIBLE);
        MyListFragment listFR = new MyListFragment();
        android.app.FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.fragment_container, listFR);
        TextView tvAdSoyad = (TextView) getActivity().findViewById(R.id.tv_Ad_Soyad);
        TextView tvOrgAciklama = (TextView) getActivity().findViewById(R.id.tv_Sap_Org_Aciklama);
        Picasso.with(context).load("http://www.hbtrinside.com/images/"+ m_Act.genPersonel.SICIL_KOD +".jpg").into(imgProf);
        tvAdSoyad.setText(m_Act.genPersonel.AD +" "+ m_Act.genPersonel.SOYAD);
        tvOrgAciklama.setText(m_Act.genPersonel.SAP_ORG_ACIKLAMA+"\n"+m_Act.genPersonel.POZISYON_ACIKLAMA);
        transaction.addToBackStack(null);
        transaction.commit();
    }



}
