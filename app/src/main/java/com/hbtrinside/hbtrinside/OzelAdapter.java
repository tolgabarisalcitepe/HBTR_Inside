package com.hbtrinside.hbtrinside;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.hbtrinside.hbtrinside.model.mesaj;
import com.squareup.picasso.Picasso;

import java.util.List;

public class OzelAdapter extends BaseAdapter {

    private LayoutInflater mInflater;
    private List<mesaj> mMesajListesi;
    private Context context;

    public OzelAdapter(Activity activity, List<mesaj> Mesajlar) {
        //XML'i alıp View'a çevirecek inflater'ı örnekleyelim
        mInflater = (LayoutInflater) activity.getSystemService(
                Context.LAYOUT_INFLATER_SERVICE);
        //gösterilecek listeyi de alalım
        mMesajListesi = Mesajlar;
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
        satirView = mInflater.inflate(R.layout.list_row2, null);
        TextView textView = (TextView) satirView.findViewById(R.id.namesurname);
        TextView textView1 = (TextView) satirView.findViewById(R.id.minute);
        TextView textView2 = (TextView) satirView.findViewById(R.id.tweet);
        ImageView imageView = (ImageView) satirView.findViewById(R.id.profile_image);
        Picasso.with(context).load("http://www.hbtrinside.com/images/"+ Mesaj.GONDEREN_SICIL_KOD +".jpg").into(imageView);

        textView.setText(String.valueOf(Mesaj.GONDEREN_AD_SOYAD));
        textView1.setText("15 min ago");
        textView2.setText(Mesaj.MESAJ1);
        //imageView.setImageResource(R.drawable.icon);

        
        return satirView;
    }
}