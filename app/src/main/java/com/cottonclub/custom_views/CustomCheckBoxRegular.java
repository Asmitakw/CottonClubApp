package com.cottonclub.custom_views;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatCheckBox;
import androidx.appcompat.widget.AppCompatEditText;

import com.cottonclub.R;


public class CustomCheckBoxRegular extends AppCompatCheckBox
{
	private Typeface tf;
	public  final String DEFAULT_FONT = "fonts/calibri.ttf";
	public CustomCheckBoxRegular(Context context)
	{
		super(context);
		if (!isInEditMode())
			init(null);
	}
	public CustomCheckBoxRegular(Context context, AttributeSet attrs)
	{
		super(context, attrs);
		if (!isInEditMode())
			init(attrs);
	}

	public CustomCheckBoxRegular(Context context, AttributeSet attrs, int defStyle)
	{
		super(context, attrs, defStyle);
		if (!isInEditMode())
			init(attrs);
	}

	private void init(AttributeSet attrs)
	{
		if(attrs!=null)
		{
			TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.CustomTextViewRegular);
			String fontName = a.getString(R.styleable.CustomTextViewRegular_fontfam);
				if (fontName != null)
					tf = Typeface.createFromAsset(getContext().getAssets(),"fonts/"+fontName+".OTF");
				else
					tf = Typeface.createFromAsset(getContext().getAssets(),DEFAULT_FONT);  //default font for app
				
			a.recycle();
			if(tf!=null)
				setTypeface(tf);
		}
	}
}
