package com.dagtech.cinemagraphwallpaper;

import android.app.WallpaperManager;
import android.content.Intent;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.Preference.OnPreferenceClickListener;

public class Settings extends PreferenceActivity {
	@SuppressWarnings("deprecation")
	@Override
	protected void onCreate(Bundle icicle) {
		super.onCreate(icicle);
		addPreferencesFromResource(R.xml.preferences);
		getPreferenceManager().setSharedPreferencesName("cinemagraph");

		
		getPreferenceManager().findPreference("test").setOnPreferenceClickListener(new OnPreferenceClickListener()
		{
			@Override
		    public boolean onPreferenceClick(Preference preference) {
				Intent intent = new Intent();
				intent.setAction(WallpaperManager.ACTION_LIVE_WALLPAPER_CHOOSER);
				startActivity(intent);
				return true;
			}
		});
		
	}
}
