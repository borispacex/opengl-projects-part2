package com.example.laboratorio7_prueba1;

import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.app.Activity;

/**
 * Iluminación 3D en OpenGL ES 1.x.
 * 
 * @author Jhonny Felipez
 * @version 1.0 02/04/2014
 *
 */

public class MainActivity extends Activity {
	private GLSurfaceView superficie;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		/* Ventana sin título */
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		
		/* Establece las banderas de la ventana de esta Actividad */
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		
		/* Se crea el objeto Renderiza */
		superficie = new Renderiza(this);

		/*
		 * Activity <- GLSurfaceView  : Coloca la Vista de la Superficie del
		 * OpenGL como un Contexto de ésta Actividad.
		 */
		setContentView(superficie);
		// setContentView(R.layout.activity_main);
	}

	/**
	 * Recuerda que debe reanudar superficie
	 */
	@Override
	protected void onResume() {
		super.onResume();
		superficie.onResume();
	}

	/**
	 * También pausa la superficie
	 */
	@Override
	protected void onPause() {
		super.onPause();
		superficie.onPause();
	}
}
