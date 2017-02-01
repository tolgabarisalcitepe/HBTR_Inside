package tr.hbtrinside.hbtrinside.extended;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;



public class ExtendedFragment extends Fragment
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
        m_App = ((ExtendedApplication) this.getActivity().getApplication());
        m_Act = (ExtendedAppCompatActivity)this.getActivity();
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View p_view = super.onCreateView(inflater, container, savedInstanceState);
        return p_view;
    };

    @SuppressLint("ValidFragment")
    public ExtendedFragment(Activity p_Act)
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


    public ExtendedFragment()
    {
        super();
    }




}
