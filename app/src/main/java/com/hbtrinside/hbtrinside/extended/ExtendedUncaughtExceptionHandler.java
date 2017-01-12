package com.hbtrinside.hbtrinside.extended;
import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources.NotFoundException;
import android.os.Build;

import com.hbtrinside.hbtrinside.R;
import com.hbtrinside.hbtrinside.MainActivity;
import com.hbtrinside.hbtrinside.core.HataRapor;

import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * Created by Tolga Baris on 4.1.2017.
 */

public class ExtendedUncaughtExceptionHandler implements Thread.UncaughtExceptionHandler
{
    private final Activity myContext;
    private final String LINE_SEPARATOR = "\n";

    public ExtendedUncaughtExceptionHandler(Activity context)
    {
        myContext = context;
    }

    public void uncaughtException(Thread thread, Throwable exception)
    {
        StringWriter stackTrace = new StringWriter();
        exception.printStackTrace(new PrintWriter(stackTrace));
        StringBuilder errorReport = new StringBuilder();
        errorReport.append("************ CAUSE OF ERROR ************\n\n");
        errorReport.append(stackTrace.toString());

        errorReport.append("\n************ DEVICE INFORMATION ***********\n");
        errorReport.append("Brand: ");
        errorReport.append(Build.BRAND);
        errorReport.append(LINE_SEPARATOR);
        errorReport.append("Device: ");
        errorReport.append(Build.DEVICE);
        errorReport.append(LINE_SEPARATOR);
        errorReport.append("Model: ");
        errorReport.append(Build.MODEL);
        errorReport.append(LINE_SEPARATOR);
        errorReport.append("Id: ");
        errorReport.append(Build.ID);
        errorReport.append(LINE_SEPARATOR);
        errorReport.append("Product: ");
        errorReport.append(Build.PRODUCT);
        errorReport.append(LINE_SEPARATOR);
        errorReport.append("\n************ FIRMWARE ************\n");
        errorReport.append("SDK: ");
        errorReport.append(Build.VERSION.SDK);
        errorReport.append(LINE_SEPARATOR);
        errorReport.append("Release: ");
        errorReport.append(Build.VERSION.RELEASE);
        errorReport.append(LINE_SEPARATOR);
        errorReport.append("Incremental: ");
        errorReport.append(Build.VERSION.INCREMENTAL);
        errorReport.append(LINE_SEPARATOR);
        exception.printStackTrace();


         HataRapor hatarapor = new HataRapor();
        hatarapor.logturu =1;
        hatarapor.logdata =errorReport.toString();
        hatarapor.uygulamaturu ="HB_Inside";
        try
        {
            ((ExtendedApplication)myContext.getApplication()).WebServis(myContext.getResources().getString(R.string.BaseURL).concat(myContext.getResources().getString(R.string.MobilURL)), "HataRapor", hatarapor.Form().toString());
        }
        catch (NotFoundException e)
        {
            e.printStackTrace();
        }

        Intent intent = new Intent(myContext, MainActivity.class);
        intent.putExtra("ERROR_ID", "1");
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK |   Intent.FLAG_ACTIVITY_CLEAR_TOP);
        myContext.startActivity(intent);

        System.exit(2);
//				android.os.Process.killProcess(android.os.Process.myPid());
//				System.exit(2);
    }

}