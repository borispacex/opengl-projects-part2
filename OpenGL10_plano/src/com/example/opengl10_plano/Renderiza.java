package com.example.opengl10_plano;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;
import android.content.Context;
import android.opengl.GLSurfaceView;
import android.opengl.GLSurfaceView.Renderer;
import android.opengl.GLU;
import android.opengl.Matrix;
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
	private Cubo cubo;
	private Piso piso;
	private Piramide piramide;
	private TrianRectangulo trian;

	/* Texturas */
	Textura texturaBotonArr;
	Textura texturaBotonAba;
	
	/* Inicializa ubicación de la vista del observador */
	private final float[] vectorEntrada = { 0, 0, -1, 1 };
	private static float posicion[] = { 0, 1, 3 };
	private final float[] direccion = new float[4];
	
	final float[] matriz = new float[16];

	/* Tamaño de la ventana en pixeles */
	private int alto;
	private int ancho;
	
	/* Para la rotación y traslación */
	private float rotY;
	private	float antX;
	
	/* Contexto */
	Context contexto;
	
	public Renderiza(Context contexto) {
		super(contexto);
		this.contexto = contexto;
		this.setRenderer(this);
		this.requestFocus();
		this.setFocusableInTouchMode(true);
		this.setRenderMode(GLSurfaceView.RENDERMODE_CONTINUOUSLY);
	}
	
	// @Override
	public void onSurfaceCreated(GL10 gl, EGLConfig arg1) {
		
		cubo = new Cubo();
		piso = new Piso();
		piramide = new Piramide();
		trian  = new TrianRectangulo();
		
		/* Lee las texturas */
		texturaBotonArr = new Textura(gl, contexto, "botonarr.png");
		texturaBotonArr.setVertices(0, -4f, 1, 1);
		
		texturaBotonAba = new Textura(gl, contexto, "botonaba.png");
		texturaBotonAba.setVertices(0, -5.5f, 1, 1);
		
		antX = -1;
		
		gl.glClearColor(176/255f, 196/255f, 222/256f, 0);
		
	}
	
	// @Override
	public void onDrawFrame(GL10 gl) {

		/* Borra el buffer de la ventana y del z-buffer */
		gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);
		
		gl.glEnable(GL10.GL_DEPTH_TEST);
		gl.glMatrixMode(GL10.GL_PROJECTION);
		gl.glLoadIdentity();
		GLU.gluPerspective(gl, 67, ancho / (float)alto, 1f, 100f);
		gl.glMatrixMode(GL10.GL_MODELVIEW);
		gl.glLoadIdentity();
		gl.glRotatef(-rotY, 0, 1, 0);
		gl.glTranslatef(-posicion[0], -posicion[1], -posicion[2]);
		
		// Piso
		gl.glPushMatrix();
		gl.glTranslatef(0, 0, -6);
		piso.dibuja(gl);
		gl.glPopMatrix();
		
		// Cubos
				gl.glPushMatrix();
				gl.glTranslatef(2, 0, -2);
				gl.glScalef(0.5f, 0.5f, 0.5f);
				cubo.dibuja(gl);
				gl.glPopMatrix();
				
				gl.glPushMatrix();
				gl.glTranslatef(-2, 0, -4);
				gl.glScalef(0.5f, 0.5f, 0.5f);
				piramide.dibuja(gl);
				gl.glPopMatrix();
				
				gl.glPushMatrix();
				gl.glTranslatef(0, 0, -6);
				gl.glScalef(0.5f, 0.5f, 0.5f);
				trian.dibuja(gl);
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
		alto = h;
		gl.glViewport(0, 0, w, h);
		GLU.gluLookAt(gl, 0, 0, 0, 0, 0, -1, 0, 1, 0);
	}
	
	/**
	 * Maneja los eventos del movimiento en la pantalla táctil. 
	 */
	// @Override
	public boolean onTouchEvent(MotionEvent e) {
		
		/* Obtiene la coordenada de la pantalla */
		float x = e.getX();
		float y = e.getY();
		
		/* Se considera cuando se levanta el dedo de la pantalla. */ 
		if (e.getAction() == MotionEvent.ACTION_DOWN || e.getAction() == MotionEvent.ACTION_MOVE) {
			
			/* En coordenadas del OpenGL */
			float posx = ((x / (float) ancho) * 8) - 4;
			float posy = ((1 - y / (float) alto) * 12) - 6;

			/* Verifica área elegida */
			if (puntoEstaDentroDelRectangulo(posx, posy, 0, -4f, 1, 1)) { // Arr
				Matrix.setIdentityM(matriz, 0);
				Matrix.rotateM(matriz, 0, rotY, 0, 1, 0);
				Matrix.multiplyMV(direccion, 0, matriz, 0, vectorEntrada, 0);
				
				posicion[0] = posicion[0] + direccion[0] * 0.1f;
				posicion[1] = posicion[1] + direccion[1] * 0.1f;
				posicion[2] = posicion[2] + direccion[2] * 0.1f;
				
			} else if (puntoEstaDentroDelRectangulo(posx, posy, 0, -5.5f, 1, 1)) { // Abj
				Matrix.setIdentityM(matriz, 0);
				Matrix.rotateM(matriz, 0, rotY, 0, 1, 0);
				Matrix.multiplyMV(direccion, 0, matriz, 0, vectorEntrada, 0);
				
				posicion[0] = posicion[0] - direccion[0] * 0.1f;
				posicion[1] = posicion[1] - direccion[1] * 0.1f;
				posicion[2] = posicion[2] - direccion[2] * 0.1f;
			} else {
				if(antX == -1) {
					antX = x;
				} else {							
					rotY = rotY + (x - antX) / 10;
					antX = x;
				}
			}
			
			//requestRender();
		} else { 
			antX = -1;
		}	
		return true;
	}
	
	private boolean puntoEstaDentroDelRectangulo(float posx, float posy,
			float x, float y, float ancho, float alto) {
		return (x < posx && posx < x + ancho && y < posy && posy < y + alto);
	}
}
