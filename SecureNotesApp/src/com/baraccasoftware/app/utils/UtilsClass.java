package com.baraccasoftware.app.utils;

import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.widget.TextView;

public class UtilsClass {

	public static void setCustomFontToTextView(TextView textView, AssetManager assets){
		 // Font path
        String fontPath = "fonts/SecureNotes_Special_font.otf";
       
        // Loading Font Face
        Typeface tf = Typeface.createFromAsset(assets, fontPath);
 
        // Applying font
        textView.setTypeface(tf);
	}
}
