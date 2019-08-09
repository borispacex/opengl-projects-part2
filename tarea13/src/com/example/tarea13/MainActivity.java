package com.example.tarea13;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		/* Ventana sin título */
		requestWindowFeature(Window.FEATURE_NO_TITLE);

		/* Establece las banderas de la ventana de esta Actividad */
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);

		/*
		 * Orientación de la pantalla vertical (PORTRAIT) u
		 * horizontal(LANDSCAPE)
		 */
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

		/* Se crea el objeto Renderiza */
		GLSurfaceView superficie = new Renderiza(this);

		/*
		 * Activity <- GLSurfaceView : Coloca la Vista de la Superficie del
		 * OpenGL como un Contexto de ésta Actividad.
		 */
		setContentView(superficie);
		// setContentView(R.layout.activity_main);

	}

}
