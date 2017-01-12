package com.hbtrinside.hbtrinside.adapter;

import android.app.Activity;
import android.content.Context;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.hbtrinside.hbtrinside.R;
import com.hbtrinside.hbtrinside.fragment.MyListFragment;
import com.hbtrinside.hbtrinside.model.ParameterObjects.mesaj;
import com.squareup.picasso.Picasso;

import java.util.Calendar;
import java.util.List;

public class OzelAdapter extends BaseAdapter {

    private LayoutInflater mInflater;
    private List<mesaj> mMesajListesi;
    private Context context;
    private MyListFragment m_Fragment;

    public OzelAdapter(Activity activity, List<mesaj> Mesajlar, MyListFragment pFragment) {
        //XML'i alıp View'a çevirecek inflater'ı örnekleyelim
        mInflater = (LayoutInflater) activity.getSystemService(
                Context.LAYOUT_INFLATER_SERVICE);
        //gösterilecek listeyi de alalım
        mMesajListesi = Mesajlar;
        m_Fragment =pFragment ;
        this.context = activity;
    }

    @Override
    public int getCount() {
        return mMesajListesi.size();
    }

    @Override
    public mesaj getItem(int position) {
        //şöyle de olabilir: public Object getItem(int position)
        return mMesajListesi.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View satirView;
        mesaj Mesaj = mMesajListesi.get(position);
        if(mMesajListesi.size()-1 == position)
        {
            m_Fragment.mesajGetir(Mesaj.ID,m_Fragment.m_Act.genPersonel.SICIL_KOD,-1);
            mMesajListesi.addAll(mMesajListesi.size(),m_Fragment.m_Mesaj);
        }
        satirView = mInflater.inflate(R.layout.list_row2, null);
        TextView textView = (TextView) satirView.findViewById(R.id.namesurname);
        TextView textView1 = (TextView) satirView.findViewById(R.id.minute);
        TextView textView2 = (TextView) satirView.findViewById(R.id.tweet);
        ImageView imageView = (ImageView) satirView.findViewById(R.id.profile_image);
        Picasso.with(context).load("http://www.hbtrinside.com/images/"+ Mesaj.GONDEREN_SICIL_KOD +".jpg").into(imageView);

        textView.setText(String.valueOf(Mesaj.GONDEREN_AD_SOYAD));


        textView1.setText( DateUtils.getRelativeTimeSpanString( Mesaj.EKLENEN_TARIH.getTimeInMillis(),  Calendar.getInstance().getTimeInMillis(),DateUtils.MINUTE_IN_MILLIS).toString());
        textView2.setText(Mesaj.MESAJ1);
        this.notifyDataSetChanged();
        //imageView.setImageResource(R.drawable.icon);

        
        return satirView;
    }


}