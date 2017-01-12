package com.hbtrinside.hbtrinside.core;

import android.util.Base64;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Crypto
{
    public static String EncryptUserPassword(String password)
    {
        String rethash = "";
        try
        {
            byte[] hash = byteSha1(password);
            rethash = Base64.encodeToString(hash, Base64.DEFAULT);
            rethash =rethash.substring(0,rethash.length()-1);
        } catch (Exception ex)
        {
            ex.printStackTrace();
            // String strerr = "Error in HashCode : " + ex.toString();
        }
        return rethash;
    }  
    static byte[] byteSha1(String input) throws NoSuchAlgorithmException
    {
        MessageDigest mDigest = MessageDigest.getInstance("SHA1");
        byte[] asciibyte = new byte[0];
        try {
            asciibyte = input.getBytes("UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        byte[] result = mDigest.digest(asciibyte);
        for (int i = 0; i < result.length; i++)
        {
            try
            {
                result[i] = (byte) unsignedShortToInt(result[i]);
            } catch (NumberFormatException e)
            {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (Exception e)
            {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        return result;
    }
    
    public static final int unsignedShortToInt(byte b)
    {
        return (b & 0xff);
    }
}
