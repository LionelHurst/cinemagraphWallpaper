package com.dagtech.cinemagraphwallpaper;

import java.util.ArrayList;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.preference.ListPreference;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.RadioButton;
import android.widget.TextView;


public class CustomListPreference extends ListPreference
{   
	
    //CustomListPreferenceAdapter customListPreferenceAdapter = null;
    Context mContext;
    CharSequence[] entries;
    CharSequence[] entryValues;
    ArrayList<Bitmap> icons;
    ArrayList<RadioButton> rButtonList;
    SharedPreferences prefs;
    SharedPreferences.Editor editor;

    public CustomListPreference(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        mContext = context;
    }

    @Override
    protected void onPrepareDialogBuilder(AlertDialog.Builder builder)
    {
    	int index = findIndexOfValue(mContext.getSharedPreferences(
				"cinemagraph", Context.MODE_PRIVATE).getString(
    			"pref_picture", "river"));
    	//Log.w("TEST", mContext.getSharedPreferences("cinemagraph", Context.MODE_PRIVATE).getAll().toString());

		ListAdapter listAdapter = (ListAdapter) new CustomListAdapterTest(getContext(),
			R.layout.list_view_row, this.getEntryValues(), this.getEntries(),index, this);

		builder.setAdapter(listAdapter, this);
		super.onPrepareDialogBuilder(builder);
		

    }
    
    private class CustomListAdapterTest extends ArrayAdapter<CharSequence> implements OnClickListener {
   	
    	int index;
    	CharSequence[] objects, entries;
    	
    	public CustomListAdapterTest(Context context, int textViewResourceId, CharSequence[] entries, CharSequence[] objects, int selected, CustomListPreference ts) {
    		super(context, textViewResourceId, objects);
    		rButtonList = new ArrayList<RadioButton>();
    		icons = new ArrayList<Bitmap>();
    		index = selected;
    		this.objects = objects;
    		this.entries = entries;
    		icons.add(BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_barbor));
    		icons.add(BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_campfire));
    		icons.add(BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_candles));
    		icons.add(BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_cars));
    		icons.add(BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_chicago));
    		icons.add(BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_dashboard));
    		icons.add(BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_desert));
    		icons.add(BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_forest));
    		icons.add(BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_horse));
    		icons.add(BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_lightning));
    		icons.add(BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_ocean));
    		icons.add(BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_owl));
    		icons.add(BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_river));
    		icons.add(BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_shootingstar));
    		icons.add(BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_skyrim));
    		icons.add(BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_sunset));
    		icons.add(BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_theatre));
    		icons.add(BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_tornado));
    		icons.add(BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_turbine));
    	}
    	
    	@Override
    	public View getView(final int position, View convertView, ViewGroup parent) {

    		//get colors
    		/*ThemeManager tm = new ThemeManager();
    		int[] colors = tm.getColors(themeId.toString(), this.getContext().getResources());*/

    		//inflate layout
    		LayoutInflater inflater = ((Activity)getContext()).getLayoutInflater();
    		View row = inflater.inflate(R.layout.list_view_row, parent, false);
    		//row.setId(Integer.parseInt(themeId.toString()));

    		ImageView imageView = (ImageView) row.findViewById(R.id.icon);
    		imageView.setImageBitmap(icons.get(position));
    		
    		/*if (entries[position].equals("ocean") || 
    				entries[position].equals("chicago") || 
    				entries[position].equals("horse") || 
    				entries[position].equals("sunset") || 
    				entries[position].equals("dashboard") || 
    				entries[position].equals("desert") || 
    				entries[position].equals("tornado") || 
    					entries[position].equals("barbor") || 
    					entries[position].equals("candles")) {
    			row.setEnabled(false);
    			TextView tv = (TextView) row.findViewById(R.id.summary);
        		tv.setText("Only available in full version");
        		
        		RadioButton tb = (RadioButton) row.findViewById(R.id.ckbox);
        		tb.setEnabled(false);
        		
    		}else {*/
    		
    		//set on click listener for row
    		row.setOnClickListener(this);
    		row.setClickable(true);
    		//set checkbox
    		RadioButton tb = (RadioButton) row.findViewById(R.id.ckbox);
    		if (position == index) {
    			tb.setChecked(true);
    		}else {
    		tb.setClickable(false);
    		
            row.setOnClickListener(new View.OnClickListener()
            {
                public void onClick(View v)	
                {

                	Editor edit = mContext.getSharedPreferences("cinemagraph", 0).edit();
        			edit.putString("pref_picture", getEntryValues()[position].toString());
        			edit.commit();

                    
                    Dialog mDialog = getDialog();
                    mDialog.dismiss();
                }
            });
    		}
    		
    		//tv.setTextColor(0xff000000);

    		
    		
    		
    		//set name
    		TextView tv = (TextView) row.findViewById(R.id.themename);
    		tv.setText(objects[position]);
    		
    		return row;
    	}

    	@Override
    	public void onClick(View v) {
    		
    	}

    }

}