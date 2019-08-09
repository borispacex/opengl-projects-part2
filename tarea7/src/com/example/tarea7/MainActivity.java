package com.example.tarea7;

import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.app.Activity;

public class MainActivity extends Activity {
	private GLSurfaceView superficie;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);	// Ventana sin título
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN); // Establece las banderas de la ventana de esta Actividad
		superficie = new Renderiza(this);	// Se crea el objeto Renderiza
		/*
		 * Activity < - GLSurfaceView : Coloca la Vista de la Superficie del
		 * OpenGL como un Contexto de ésta Actividad.
		 */
		setContentView(superficie);
		// setContentView(R.layout.activity_main);
	}
}
