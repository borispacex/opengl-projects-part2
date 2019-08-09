package com.example.laboratorio3_prueba;

import android.app.Activity;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

/**
 * Carrera de Coches en OpneGL ES 1.0
 * 
 * @author Jhonny Felipez
 * @version 1.0 01/01/2015
 * 
 */
public class MainAcelerometro extends Activity implements SensorEventListener {
	private SensorManager sensorManager;
	MiGLSurfaceView superficie;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);

		/*
		 * Orientación de la pantalla vertical (PORTRAIT) u
		 * horizontal(LANDSCAPE)
		 */
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

		superficie = new MiGLSurfaceView(this);
		setContentView(superficie);

		sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
		sensorManager.registerListener(this,
				sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
				SensorManager.SENSOR_DELAY_NORMAL);
	}

	// @Override
	public void onSensorChanged(SensorEvent e) {
		if (e.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
			superficie.renderiza.acelerometroX = e.values[0];
		}
	}

	// @Override
	public void onAccuracyChanged(Sensor sensor, int arg1) {

	}

	@Override
	protected void onResume() {
		super.onResume();
		superficie.onResume();
		sensorManager.registerListener(this,
				sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
				SensorManager.SENSOR_DELAY_NORMAL);
	}

	@Override
	protected void onPause() {
		super.onPause();
		superficie.onPause();
		sensorManager.unregisterListener(this);
	}

	public class MiGLSurfaceView extends GLSurfaceView {

		public Renderiza renderiza;

		public MiGLSurfaceView(Context contexto) {
			super(contexto);
			renderiza = new Renderiza(contexto);
			setRenderer(renderiza);
			requestFocus();
			setFocusableInTouchMode(true);
			setRenderMode(GLSurfaceView.RENDERMODE_CONTINUOUSLY);
		}
	}
}
