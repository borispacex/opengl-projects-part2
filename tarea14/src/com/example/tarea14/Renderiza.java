package com.example.tarea14;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;
import android.opengl.GLSurfaceView.Renderer;

public class Renderiza implements Renderer {
	/* Objeto */
	Esfera esfera1;
	Esfera esfera2;

	float luz_ambiente[] = { 1, 1, 1, 1 }; // I
	float luz_difusa[] = { 1, 1, 1, 1 };
	float luz_especular[] = { 1, 1, 1, 1 };
	float luz_posicion[] = { 2, 0, 0, 0 }; // L w == 0 (direccional) w == 1
											// (posicional)

	float blanco[] = { 1, 1, 1, 1 }; // I
	float negro[] = { 0, 0, 0, 1 }; // I
	float angulo = 0;

	// @Override
	public void onSurfaceCreated(GL10 gl, EGLConfig arg1) {

		esfera1 = new Esfera(1, 48, 48);
		esfera2 = new Esfera(0.1f, 48, 48);

		gl.glLightfv(GL10.GL_LIGHT0, GL10.GL_AMBIENT, luz_ambiente, 0);
		gl.glLightfv(GL10.GL_LIGHT0, GL10.GL_DIFFUSE, luz_difusa, 0);
		gl.glLightfv(GL10.GL_LIGHT0, GL10.GL_SPECULAR, luz_especular, 0);

		/* Habilita la interpolación del sombreado */
		gl.glShadeModel(GL10.GL_SMOOTH);
		/* Habilita el ocultamiento de superficies */
		gl.glEnable(GL10.GL_DEPTH_TEST);
		/* Habilita la iluminación */
		gl.glEnable(GL10.GL_LIGHTING);
		/* Habilita la luz 0 */
		gl.glEnable(GL10.GL_LIGHT0);
		/* Habilita la normalización */
		gl.glEnable(GL10.GL_NORMALIZE);
		gl.glClearColor(0, 0, 0, 1);
	}

	// @Override
	public void onDrawFrame(GL10 gl) {
		gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);
		gl.glLoadIdentity();
		
		/** SOMBREADO GIRANDO **/
		angulo = 155;
		gl.glPushMatrix();
			gl.glRotatef(angulo, 0, 0, 1);
			gl.glTranslatef(1.3f, 0, -15);
			gl.glMaterialfv(GL10.GL_FRONT_AND_BACK, GL10.GL_EMISSION, blanco, 0);
			// esfera2.dibuja(gl);
			gl.glMaterialfv(GL10.GL_FRONT_AND_BACK, GL10.GL_EMISSION, negro, 0);
			gl.glLightfv(GL10.GL_LIGHT0, GL10.GL_POSITION, luz_posicion, 0);
		gl.glPopMatrix();

		/* Definición del color del material - Valores por defecto */
		// rojo
		float mat_ambiente[] = { 0.1715f, 0, 0, 1 }; // K
		float mat_difuso[] = { 0.6757f, 0, 0, 1 }; // mas blanco cuando se
													// acerca a 1.
		float mat_especular[] = { 0.7333f, 0, 0, 1 };
		float mat_brillo = 128f;
		gl.glMaterialfv(GL10.GL_FRONT_AND_BACK, GL10.GL_AMBIENT, mat_ambiente,
				0);
		gl.glMaterialfv(GL10.GL_FRONT_AND_BACK, GL10.GL_DIFFUSE, mat_difuso, 0);
		gl.glMaterialfv(GL10.GL_FRONT_AND_BACK, GL10.GL_SPECULAR,
				mat_especular, 0);
		gl.glMaterialf(GL10.GL_FRONT_AND_BACK, GL10.GL_SHININESS, mat_brillo);
		
		gl.glPushMatrix();
			gl.glTranslatef(0, 0, -15);
			esfera1.dibuja(gl);
		gl.glPopMatrix();
		
		// amarillo
		/* Definición del color del material - Valores por defecto */
		float mat_ambiente2[] = { 0.1715f, 0.1715f, 0, 1 }; // K
		float mat_difuso2[] = { 0.6757f, 0.6757f, 0, 1 }; // mas blanco cuando
															// se acerca a 1.
		float mat_especular2[] = { 0.7333f, 0.7333f, 0, 1 };
		float mat_brillo2 = 128f;
		gl.glMaterialfv(GL10.GL_FRONT_AND_BACK, GL10.GL_AMBIENT, mat_ambiente2,
				0);
		gl.glMaterialfv(GL10.GL_FRONT_AND_BACK, GL10.GL_DIFFUSE, mat_difuso2, 0);
		gl.glMaterialfv(GL10.GL_FRONT_AND_BACK, GL10.GL_SPECULAR,
				mat_especular2, 0);
		gl.glMaterialf(GL10.GL_FRONT_AND_BACK, GL10.GL_SHININESS, mat_brillo2);
		
		gl.glPushMatrix();
			gl.glTranslatef(0, 2.1f, -15);
			esfera1.dibuja(gl);
		gl.glPopMatrix();

		// plateado
		float mat_ambiente3[] = { 0.1715f, 0.1715f, 0.1715f, 1 }; // K
		float mat_difuso3[] = { 0.6757f, 0.6757f, 0.6757f, 1 }; // mas blanco
																// cuando se
																// acerca a 1.
		float mat_especular3[] = { 0.7333f, 0.7333f, 0.7333f, 1 };
		float mat_brillo3 = 128f;
		gl.glMaterialfv(GL10.GL_FRONT_AND_BACK, GL10.GL_AMBIENT, mat_ambiente3,
				0);
		gl.glMaterialfv(GL10.GL_FRONT_AND_BACK, GL10.GL_DIFFUSE, mat_difuso3, 0);
		gl.glMaterialfv(GL10.GL_FRONT_AND_BACK, GL10.GL_SPECULAR,
				mat_especular3, 0);
		gl.glMaterialf(GL10.GL_FRONT_AND_BACK, GL10.GL_SHININESS, mat_brillo3);
		
		gl.glPushMatrix();
			gl.glTranslatef(0, -2.1f, -15);
			esfera1.dibuja(gl);
		gl.glPopMatrix();

		gl.glFlush();
		
		/*
		angulo = angulo + 2;
		if (angulo > 360 - 2){angulo = 0;}
		*/
	}

	// @Override
	public void onSurfaceChanged(GL10 gl, int w, int h) {
		gl.glViewport(0, 0, w, h);
		gl.glMatrixMode(GL10.GL_PROJECTION);
		gl.glLoadIdentity();
		if (w <= h)
			gl.glOrthof(-2, 2, -2 * h / (float) w, 2 * h / (float) w, 3, 50);
		else
			gl.glOrthof(-2 * w / (float) h, 2 * w / (float) h, -2, 2, 3, 50);
		gl.glMatrixMode(GL10.GL_MODELVIEW);
		gl.glLoadIdentity();
	}
}
