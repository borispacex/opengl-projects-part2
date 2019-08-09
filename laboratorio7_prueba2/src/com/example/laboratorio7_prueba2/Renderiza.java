package com.example.laboratorio7_prueba2;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.opengl.GLU;
import android.opengl.Matrix;
import android.opengl.GLSurfaceView.Renderer;
import android.view.MotionEvent;

/**
 * Clase Renderiza (OpenGL 1.x)
 * 
 * @author Jhonny Felipez
 * @version 1.0 02/04/2014
 *
 */
public class Renderiza extends GLSurfaceView implements Renderer {
	
	/* Objeto */
	Objeto obj;
	Objeto obj1;
	Objeto obj2;
	
	private Piso piso;
	
	/* Texturas */
	Textura texturaBotonArr;
	Textura texturaBotonAba;

	
	/* Inicializa ubicación de la vista del observador */
	private final float[] vectorEntrada = { 0, 0, -1, 1 };
	private static float posicion[] = { 0, 1, 3 };
	private final float[] direccion = new float[4];
	final float[] matriz = new float[16];
	
	/* Para la rotación */
	private float rotX;
	private float rotY;
	private float antX;
	private float antY;
	
	private int alto;
	private int ancho;
	
	/* Contexto */
	Context contexto;
	
	public Renderiza(Context contexto){
		super(contexto);
		this.contexto = contexto;
		this.setRenderer(this);
		this.requestFocus();
		this.setFocusableInTouchMode(true);
		this.setRenderMode(GLSurfaceView.RENDERMODE_CONTINUOUSLY);
	}
	
	// @Override
	public void onSurfaceCreated(GL10 gl, EGLConfig arg1) {
		obj = new Objeto(contexto, "porsche.obj");
		obj1 = new Objeto(contexto, "dog.obj");
		obj2 = new Objeto(contexto, "yamaha-yzr-r6.obj");
		piso = new Piso();

		
		/* Lee las texturas */
		texturaBotonArr = new Textura(gl, contexto, "botonarr.png");
		texturaBotonArr.setVertices(-0.5f, -4f, 1, 1);
		
		texturaBotonAba = new Textura(gl, contexto, "botonaba.png");
		texturaBotonAba.setVertices(-0.5f, -5.5f, 1, 1);
		
		antX = antY = -1;
		
		
		/* Definición del color de la luz - Valores por defecto */
		float [] luz_ambiente = { 0, 0, 0, 1}; // I 
		float [] luz_difusa = { 1, 1, 1, 1 }; 
		float [] luz_especular = { 1, 1, 1, 1 }; 
		float [] luz_posicion = { 0, 0, 1, 0}; // L
		
		gl.glLightfv(GL10.GL_LIGHT0, GL10.GL_AMBIENT, luz_ambiente, 0);
		gl.glLightfv(GL10.GL_LIGHT0, GL10.GL_DIFFUSE, luz_difusa, 0);
		gl.glLightfv(GL10.GL_LIGHT0, GL10.GL_SPECULAR, luz_especular, 0);
		gl.glLightfv(GL10.GL_LIGHT0, GL10.GL_POSITION, luz_posicion, 0);
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
		/* Fondo negro */
		gl.glClearColor(12/255f, 183/255f, 242/256f, 0);
	}
	
	// @Override
	public void onDrawFrame(GL10 gl) {
		/* Inicializa el buffer de color y de profundidad */
		gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);
		/* Inicializa la Matriz del Modelo-Vista */
		gl.glEnable(GL10.GL_DEPTH_TEST);
		
		/* Matriz de Proyección */
		gl.glMatrixMode(GL10.GL_PROJECTION);
		gl.glLoadIdentity();
		
		GLU.gluPerspective(gl, 67, ancho / (float)alto, 1f, 50f);
		//gl.glFrustumf(-4, 4, -6, 6, 1, 50);
		//gl.glOrthof(-4, 4, -6, 6, 1, 50); // proyeccion en paralelo
		gl.glMatrixMode(GL10.GL_MODELVIEW);
		gl.glLoadIdentity();
		gl.glRotatef(-rotX, 1, 0, 0);
		gl.glRotatef(-rotY, 0, 1, 0);
		gl.glTranslatef(-posicion[0], -posicion[1], -posicion[2]);
		
		
		
		
		
		//gl.glLoadIdentity();
		/* Traslada */
		//gl.glTranslatef(0, 0, -15);
		
		/* Rota el cubo */
		//gl.glRotatef(rotX, 0.0f, 1.0f, 0.0f);
		//gl.glRotatef(rotY, 1.0f, 0.0f, 0.0f);
		
		gl.glPushMatrix();
		gl.glTranslatef(0, 0, -6);
		piso.dibuja(gl);
		gl.glPopMatrix();
		
		
		
		
		float [] luz_ambiente = { 0, 0, 0, 1}; // I 
		float [] luz_difusa = { 1, 1, 1, 1 }; 
		float [] luz_especular = { 1, 1, 1, 1 }; 
		float [] luz_posicion = { 0, 0, 1, 0}; // L
		
		gl.glLightfv(GL10.GL_LIGHT0, GL10.GL_AMBIENT, luz_ambiente, 0);
		gl.glLightfv(GL10.GL_LIGHT0, GL10.GL_DIFFUSE, luz_difusa, 0);
		gl.glLightfv(GL10.GL_LIGHT0, GL10.GL_SPECULAR, luz_especular, 0);
		gl.glLightfv(GL10.GL_LIGHT0, GL10.GL_POSITION, luz_posicion, 0);
		
		
		
		gl.glPushMatrix();
		gl.glTranslatef(0, 0, 0);
		gl.glScalef(0.5f, 0.5f, 0.5f);
		obj.dibuja(gl);
		gl.glPopMatrix();
		
		gl.glPushMatrix();
		gl.glTranslatef(2, 0, -2);
		gl.glScalef(0.5f, 0.5f, 0.5f);
		obj1.dibuja(gl);
		gl.glPopMatrix();
		
		gl.glPushMatrix();
		gl.glTranslatef(-2, 0, -2);
		gl.glScalef(0.5f, 0.5f, 0.5f);
		obj2.dibuja(gl);
		gl.glPopMatrix();
		
		/* Botones de las opciones */
		gl.glDisable(GL10.GL_DEPTH_TEST);
		gl.glMatrixMode(GL10.GL_PROJECTION);
		gl.glLoadIdentity();
		gl.glOrthof(-4, 4, -6, 6, 1, -1);
		gl.glMatrixMode(GL10.GL_MODELVIEW);
		gl.glLoadIdentity();
		
		gl.glEnable(GL10.GL_TEXTURE_2D);
		gl.glEnable(GL10.GL_BLEND);
		gl.glBlendFunc(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);

		texturaBotonArr.muestra(gl);
		texturaBotonAba.muestra(gl);
		
		gl.glDisable(GL10.GL_BLEND);
		gl.glDisable(GL10.GL_TEXTURE_2D);
		
		gl.glFlush();
		
		
	}
	
	// @Override
	public void onSurfaceChanged(GL10 gl, int w, int h) {
		ancho = w;
		/* Ventana de despliegue */
		gl.glViewport(0, 0, w, h);
	 
		/* Matriz de Proyección */
		gl.glMatrixMode(GL10.GL_PROJECTION);
	 
		/* Inicializa la Matriz de Proyección */
		gl.glLoadIdentity();
	 
		/* Proyección paralela */
		if (w <= h)
			gl.glOrthof(-2, 2, -2 * (float) h / (float) w, 2 * (float) h / (float) w, 3, 50);
		else
			gl.glOrthof(-2 * (float) w / (float) h, 2 * (float) w / (float) h, -2, 2, 3, 50);
	 
		/* Matriz del Modelo-Vista */
		gl.glMatrixMode(GL10.GL_MODELVIEW);
	 
		/* Inicializa la Matriz del Modelo-Vista */
		gl.glLoadIdentity();
		
		ancho = w;
		alto = h;
		gl.glViewport(0, 0, w, h);
		GLU.gluLookAt(gl, 0, 0, 0, 0, 0, -1, 0, 1, 0);
	}
	/**
	 * Maneja los eventos del movimiento en la pantalla táctil. 
	 */
	@Override
	public boolean onTouchEvent(MotionEvent e) {
		float x = e.getX();
        float y = e.getY();
        
		/* Se considera cuando se levanta el dedo de la pantalla. */ 
		if (e.getAction() == MotionEvent.ACTION_DOWN || e.getAction() == MotionEvent.ACTION_MOVE) {
			
			/* En coordenadas del OpenGL */
			float posx = ((x / (float) ancho) * 8) - 4;
			float posy = ((1 - y / (float) alto) * 12) - 6;

			/* Verifica área elegida */
			if (puntoEstaDentroDelRectangulo(posx, posy, -0.5f, -4f, 1, 1)) { // Arr
				Matrix.setIdentityM(matriz, 0);
				Matrix.rotateM(matriz, 0, rotY, 0, 1, 0);
				Matrix.rotateM(matriz, 0, rotX, 1, 0, 0);
				Matrix.multiplyMV(direccion, 0, matriz, 0, vectorEntrada, 0);
				
				posicion[0] = posicion[0] + direccion[0] * 0.1f;
				posicion[1] = posicion[1] + direccion[1] * 0.1f;
				posicion[2] = posicion[2] + direccion[2] * 0.1f;
				
			} else if (puntoEstaDentroDelRectangulo(posx, posy, -0.5f, -5.5f, 1, 1)) { // Abj
				Matrix.setIdentityM(matriz, 0);
				Matrix.rotateM(matriz, 0, rotY, 0, 1, 0);
				Matrix.multiplyMV(direccion, 0, matriz, 0, vectorEntrada, 0);
				
				posicion[0] = posicion[0] - direccion[0] * 0.1f;
				posicion[1] = posicion[1] - direccion[1] * 0.1f;
				posicion[2] = posicion[2] - direccion[2] * 0.1f;
			} else {
				if(antX == -1) {
					antX = x;
					antY = y;
				} else {
					rotX = rotX + (y - antY) / 10;
					rotY = rotY + (x - antX) / 10;
					antX = x;
					antY = y;
				}
			}
			
			//requestRender();
		} else { 
			antX = -1;
			antY = -1;
		}	
		return true;
	}
	
	private boolean puntoEstaDentroDelRectangulo(float posx, float posy,
			float x, float y, float ancho, float alto) {
		return (x < posx && posx < x + ancho && y < posy && posy < y + alto);
	}

	
}