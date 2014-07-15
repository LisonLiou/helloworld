package com.example.helloworld;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class DialogActivity extends Activity{

	private TextView textview1;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		super.setContentView(R.layout.dialog);
		
		Intent intent=getIntent();
		textview1=(TextView)super.findViewById(R.id.textview1);
		textview1.setText(intent.getStringExtra("testdialog"));
	}

}
