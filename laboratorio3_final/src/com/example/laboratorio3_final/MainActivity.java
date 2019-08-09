package com.example.laboratorio3_final;

import android.app.Activity;
import android.app.ActionBar;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.os.Build;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	}

	public void btnAce(View vista) {
		// Crea intent
		Intent intent = new Intent(this, MainAcelerometro.class);

		// Inicia la otra actividad
		this.startActivity(intent);
	}

	public void btnTouch(View vista) {
		// Crea intent
		Intent intent = new Intent(this, MainTouch.class);

		// Inicia la otra actividad
		this.startActivity(intent);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
}
