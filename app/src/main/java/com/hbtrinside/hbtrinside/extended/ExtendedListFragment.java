package com.hbtrinside.hbtrinside.extended;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Fragment;
import android.app.ListFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public class ExtendedListFragment extends ListFragment
{

    protected ExtendedApplication m_App;
    protected ExtendedAppCompatActivity m_Act;
    @Override
    public void onResume()
    {
        super.onResume();

    }

    public void onStart()
    {
        super.onStart();

    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        m_App = ((ExtendedApplication) this.getActivity().getApplication());
        m_Act = (ExtendedAppCompatActivity)this.getActivity();

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View p_view = super.onCreateView(inflater, container, savedInstanceState);
        return p_view;
    };

    @SuppressLint("ValidFragment")
    public ExtendedListFragment(Activity p_Act)
    {
        super();
        try
        {
            this.m_Act = (ExtendedAppCompatActivity) p_Act;
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }


    public ExtendedListFragment()
    {
        super();
    }




}
