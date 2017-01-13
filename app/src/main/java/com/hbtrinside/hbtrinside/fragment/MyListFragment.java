package com.hbtrinside.hbtrinside.fragment;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;
import android.support.annotation.NonNull;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.getbase.floatingactionbutton.FloatingActionButton;
import com.hbtrinside.hbtrinside.R;
import com.hbtrinside.hbtrinside.activity.MainActivity;
import com.hbtrinside.hbtrinside.adapter.OzelAdapter;
import com.hbtrinside.hbtrinside.core.Core;
import com.hbtrinside.hbtrinside.core.Sonuc;
import com.hbtrinside.hbtrinside.extended.ExtendedListFragment;
import com.hbtrinside.hbtrinside.model.ParameterObjects.Kisi;
import com.hbtrinside.hbtrinside.model.ParameterObjects.mesaj;
import com.hbtrinside.hbtrinside.model.initialobject.MesajGetirInitParameter;
import com.hbtrinside.hbtrinside.model.initialobject.MesajGonderInitParameter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

//
public class MyListFragment extends ExtendedListFragment implements OnItemClickListener,SwipeRefreshLayout.OnRefreshListener,View.OnClickListener {

    final List<Kisi> kisiler = new ArrayList<Kisi>();
    public List<mesaj> m_Mesaj = new ArrayList<mesaj>();
    private SwipeRefreshLayout swipeRefreshLayout;
    private Context context;
    private MyListFragment ref;
    private MaterialDialog dialog;
    private MaterialDialog dialog2;
    private Toolbar toolbar;
    private MainActivity m_MainAct;
    private View positiveAction;
    private Toast toast;
    //TextToSpeech t1;
    View rootView;
    String[] deneme = null;
    protected static final int RESULT_SPEECH = 1;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = getActivity().getApplicationContext();
        setHasOptionsMenu(true);
        ref = this;
    }

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("");
        rootView = inflater.inflate(R.layout.fragment_list, container, false);
        swipeRefreshLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.swipe_container);
        swipeRefreshLayout.setOnRefreshListener(this);
        m_MainAct= (MainActivity)this.getActivity();
        mesajGetir(0,m_Act.genPersonel.SICIL_KOD,1);

        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        OzelAdapter adaptorumuz = new OzelAdapter(getActivity(), m_Mesaj,this);
        getListView().setAdapter(adaptorumuz);
        getListView().setOnItemClickListener(this);
        getListView().setBackgroundColor(Color.WHITE);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Toast.makeText(getActivity(), "Item: " + position, Toast.LENGTH_SHORT).show();
        String selected = ((TextView) view.findViewById(R.id.tweet)).getText().toString();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        getActivity().getMenuInflater().inflate(R.menu.menu_main, menu);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.action_tweet:
                break;
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_tweet:
                showCustomView();
                break;
            default:
                break;
        }
        return true;
    }

    private void showToast(String message) {
        if (toast != null) {
            toast.cancel();
            toast = null;
        }
        toast = Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT);
        toast.show();
    }

    public void showCustomView() {
        dialog2 = new MaterialDialog.Builder(getActivity())
                .title("Tweet")
                .customView(R.layout.dialog_add_custom, true)
                .positiveText("Ok")
                .negativeText(android.R.string.cancel)
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        String dialogCountText = ((EditText) dialog.findViewById(R.id.dialog_add_what)).getText().toString();
                        //showToast(dialogCountText);
                        mesajGonder(dialogCountText);
                        onRefresh();
                    }
                }).build();
        dialog2.show();

        positiveAction = dialog2.getActionButton(DialogAction.POSITIVE);
        //noinspection ConstantConditions
        FloatingActionButton passwordInput = (FloatingActionButton) dialog2.getCustomView().findViewById(R.id.button);
        passwordInput.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(
                        RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, "tr-TR");
                try {
                    startActivityForResult(intent, RESULT_SPEECH);
                } catch (ActivityNotFoundException a) {
                    Toast t = Toast.makeText(getActivity().getApplicationContext(),
                            "Opps! Your device doesn't support Speech to Text",
                            Toast.LENGTH_SHORT);
                    t.show();
                }
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case RESULT_SPEECH: {
                if (resultCode == Activity.RESULT_OK && null != data) {

                    ArrayList<String> text = data
                            .getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    deneme = text.get(0).toString().split(" ");
                    AutoCompleteTextView dialogText = (AutoCompleteTextView) dialog2.findViewById(R.id.dialog_add_what);
                    dialogText.setText(text.get(0).toString());
                }
                break;
            }

        }
    }

    public void mesajGonder(String Mesaj) {
        MesajGonderInitParameter mesajGonderInitParameter = new MesajGonderInitParameter();
        mesajGonderInitParameter.Mesaj = Mesaj;
        mesajGonderInitParameter.OrgNo = m_Act.genPersonel.ORG_NO;
        mesajGonderInitParameter.GonderenSicilKod = m_Act.genPersonel.SICIL_KOD;
        mesajGonderInitParameter.AliciSicilKod = 0;

        Sonuc sonuc = m_App.WebServis(getResources().getString(R.string.BaseURL).concat(
                getResources().getString(R.string.MobilURL)).concat(
                getResources().getString(R.string.MesajGonderURL)), mesajGonderInitParameter.Form().toString());
        if (sonuc.sonucKod == 0) {
            showToast("Mesaj Başarıyla Gönderildi.");

        } else {
            //TODO ALERT
            showToast("Mesaj Başarıyla Gönderilemedi.");
        }
    }

    public void mesajGetir(int pMesajId,int pSicilKod,int pMesajGuncellemeYon) {
        MesajGetirInitParameter mesajGetirInitParameter = new MesajGetirInitParameter();
        mesajGetirInitParameter.MesajId = pMesajId;
        mesajGetirInitParameter.SicilKod = pSicilKod;//genPersonel.SICIL_KOD;
        mesajGetirInitParameter.MesajGuncellemeYon = pMesajGuncellemeYon;

        Sonuc sonuc = m_App.WebServis(getResources().getString(R.string.BaseURL).concat(
                getResources().getString(R.string.MobilURL)).concat(
                getResources().getString(R.string.MesajGetirURL)), mesajGetirInitParameter.Form().toString());
        m_Mesaj = new ArrayList<mesaj>();
        if (sonuc.sonucKod == 0) {
            MesajGetir(sonuc);
        } else {
            //TODO ALERT
       //     showToast("Mesaj Başarıyla Gönderilemedi.");
        }
    }

    protected void MesajGetir(Sonuc p_Sonuc) {
        try {

            if (p_Sonuc.sonucKod == 0) {
                JSONObject JObject = null;
                JSONArray jsArray = null;
                JSONObject jArray = null;
                try {
                    JObject = new JSONObject(p_Sonuc.sonucBilgisi);
                    jsArray = JObject.getJSONArray("Bilgi");

                    for (int i = 0; i < jsArray.length(); i++) {
                        jArray = jsArray.getJSONObject(i);
                        mesaj Mesaj = new mesaj();
                        Mesaj.ALICI_SICIL_KOD = jArray.optInt("ALICI_SICIL_KOD", -1);
                        Mesaj.EKLENEN_TARIH = Core.StringToDate(jArray.getString("EKLENEN_TARIH"));
                        Mesaj.GONDEREN_SICIL_KOD = jArray.optInt("GONDEREN_SICIL_KOD", -1);
                        Mesaj.ID = jArray.optInt("ID", -1);
                        Mesaj.MESAJ1 = jArray.getString("MESAJ1");
                        Mesaj.MESAJ_ID = jArray.optInt("MESAJ_ID", -1);
                        Mesaj.GONDEREN_AD_SOYAD = jArray.getString("GONDEREN_AD_SOYAD");
                        Mesaj.ALICI_AD_SOYAD = jArray.getString("ALICI_AD_SOYAD");
                        m_Mesaj.add(Mesaj);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } else {//TODO ALERT
            }

        } catch (Resources.NotFoundException e) {
            //TODO ALERT
        }

    }

    public void MesajGetir2() {
        mesajGetir(0,m_Act.genPersonel.SICIL_KOD,1);
        OzelAdapter adaptorumuz = new OzelAdapter(getActivity(), m_Mesaj,this);
        getListView().setAdapter(adaptorumuz);
        getListView().setOnItemClickListener(this);
        getListView().setBackgroundColor(Color.WHITE);
    }

    @Override
    public void onRefresh() {
        MesajGetir2();
        swipeRefreshLayout.setRefreshing(false);
    }

}


