package com.hbtrinside.hbtrinside.extended;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;
import android.widget.Toast;

import com.hbtrinside.hbtrinside.model.ParameterObjects.gen_personel;

public class ExtendedAppCompatActivity extends AppCompatActivity
{
    public gen_personel genPersonel;
    protected ExtendedApplication m_App= ((ExtendedApplication) this.getApplication());
    protected ExtendedAppCompatActivity    m_Act=((ExtendedAppCompatActivity)this);
    // region Standart Methodlar
    public void load(Context p_ctx, Class<?> p_c)
    {
        m_App.setIntent(new Intent(p_ctx, p_c));
        startActivityForResult(m_App.getIntent(), 0);
    }

    public void loadAndClear(Context p_ctx, Class<?> p_c)
    {
        m_App.setIntent(new Intent(p_ctx, p_c));
        m_App.getIntent().setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivityForResult(m_App.getIntent(), 0);
    }

    public void toast(String p_m)
    {
        Toast.makeText(getApplicationContext(), p_m, Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        m_App.setm_Context(ExtendedAppCompatActivity.this);
    }

    @Override
    protected void onStart()
    {
        super.onStart();
        m_App= ((ExtendedApplication) this.getApplication());
        m_Act=((ExtendedAppCompatActivity)this);
        Thread.setDefaultUncaughtExceptionHandler(new ExtendedUncaughtExceptionHandler(this));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        m_App= ((ExtendedApplication) this.getApplication());
        m_Act=((ExtendedAppCompatActivity)this);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

    }

    @Override
    protected void onRestart()
    {
        super.onRestart();

    }

    public void reload()
    {
        Intent intent = getIntent();
        overridePendingTransition(0, 0);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        finish();
        overridePendingTransition(0, 0);
        startActivity(intent);
    }

}




