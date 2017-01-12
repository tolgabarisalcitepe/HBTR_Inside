package com.hbtrinside.hbtrinside.core;

import android.content.res.Resources.NotFoundException;

import org.json.JSONException;
import org.json.JSONObject;

public class HataRapor
	{
		public String logdata;
		public int logturu;
        public String uygulamaturu;

		 public JSONObject Form() 
			{
				JSONObject Bilgiler = new JSONObject();
				try {
					Bilgiler.put("logdata", logdata);
					Bilgiler.put("logturu", logturu);
                    Bilgiler.put("uygulamaturu", uygulamaturu);
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				catch (NotFoundException e) {

					// TODO Auto-generated catch block
					e.printStackTrace();
				} 
				return Bilgiler;
				
			}
	}
