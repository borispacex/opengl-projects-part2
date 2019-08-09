package com.example.tarea11;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;
import android.content.Context;
import android.opengl.GLSurfaceView;
import android.opengl.GLSurfaceView.Renderer;
import android.view.MotionEvent;

public class Renderiza extends GLSurfaceView implements Renderer {
	/* Objeto */
	private Esfera esfera;
	private Cilindro cilindro;
	private Piso piso;
	/* Para la rotación */
	private float rotX;
	private float rotY;
	private float antX;
	private float antY;
	// para la traslacion
	private float tx1,tz1;
	private float angulo = 0;

	public Renderiza(Context contexto) {
		super(contexto);
		/* Se inicia el renderizado */
		this.setRenderer(this);
		/* La ventana solicita recibir una entrada */
		this.requestFocus();
		/* Se establece que la ventana detecte el modo táctil */
		this.setFocusableInTouchMode(true);
		/* Se renderizará al inicio o cuando se llame a requestRender() */
		//this.setRenderMode(GLSurfaceView.RENDERMODE_WHEN_DIRTY);
		this.setRenderMode(GLSurfaceView.RENDERMODE_CONTINUOUSLY);	// El renderizado se llama continuamente para renderizar la escena .
	}

	// @Override
	public void onSurfaceCreated(GL10 gl, EGLConfig arg1) {
		esfera = new Esfera(1,48,48);
		cilindro = new Cilindro(48, 0.3f);
		piso = new Piso();
		/* Se habilita el modo de sombreado plano */
		gl.glShadeModel(GL10.GL_FLAT);
		/* Se habilita el ocultamiento de superficies */
		gl.glEnable(GL10.GL_DEPTH_TEST);
		/* Color de fondo */
		gl.glClearColor(0, 0, 0, 0);
	}

	// @Override
	public void onDrawFrame(GL10 gl) {
		/* Se inicializa el buffer de color y de profundidad */
		gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);
		gl.glMatrixMode(GL10.GL_MODELVIEW);	// Inicializa la Matriz del Modelo - Vista
		/* Se inicializa la Matriz del Modelo-Vista */
		gl.glLoadIdentity();

		/* Rotar */
		gl.glRotatef(rotX, 0.0f, 1.0f, 0.0f);
		gl.glRotatef(rotY, 1.0f, 0.0f, 0.0f);
		
		// Calcula el desplazamiento
		float theta = (float) Math.toRadians(angulo);
		tx1 = (float) (Math.cos(theta) * 8f);
		tz1 = (float) (Math.sin(theta) * 8f);
		angulo = angulo + 5;
		if (angulo > 360) {
			angulo = 0;
		}
		
		gl.glPushMatrix();
			gl.glTranslatef(0, 0, 0);
			piso.dibuja(gl);
		gl.glPopMatrix();
		
		gl.glPushMatrix();
			// traslada
			gl.glTranslatef(tx1, 0, tz1);
			cilindro.dibuja(gl);
			
			gl.glPushMatrix();
				gl.glTranslatef(0, 2, 0);
				esfera.dibuja(gl);
			gl.glPopMatrix();
			
		gl.glPopMatrix();
		
		/* Se asegura que se ejecute las anteriores instrucciones */
		gl.glFlush();
	}

	// @Override
	public void onSurfaceChanged(GL10 gl, int w, int h) {
		// w = w - 400;
		// h = h - 400;
		/* Ventana de despliegue */
		gl.glViewport(0, 0, w, h);
		/* Matriz de Proyección */
		gl.glMatrixMode(GL10.GL_PROJECTION);
		gl.glLoadIdentity();
		/* Proyección paralela */
		if (w <= h)
			gl.glOrthof(-10f, 10f, -10f * (float) h / (float) w, 10f * (float) h
					/ (float) w, -10, 10);
		else
			gl.glOrthof(-10f * (float) w / (float) h, 10f * (float) w / (float) h,
					-10f, 10f, -10, 10);
		/* Matriz del Modelo-Vista */
		gl.glMatrixMode(GL10.GL_MODELVIEW);
		/* Se inicializa la Matriz del Modelo-Vista */
		gl.glLoadIdentity();
	}

	/**
	 * Maneja los eventos del movimiento en la pantalla táctil.
	 */
	@Override
	public boolean onTouchEvent(MotionEvent e) {
		float x = e.getX();
		float y = e.getY();
		switch (e.getAction()) {
		case MotionEvent.ACTION_MOVE:
			float dx = x - antX;
			float dy = y - antY;
			rotX = rotX + dx * 0.5625f; // 180 / 320 = 0.5625
			rotY = rotY + dy * 0.5625f;
			requestRender();
		}
		antX = x;
		antY = y;
		return true;
	}
}
