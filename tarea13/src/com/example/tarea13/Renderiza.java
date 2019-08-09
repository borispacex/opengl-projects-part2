package com.example.tarea13;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;
import android.content.Context;
import android.opengl.GLSurfaceView;
import android.opengl.GLSurfaceView.Renderer;
import android.opengl.GLU;
import android.opengl.Matrix;
import android.view.MotionEvent;

public class Renderiza extends GLSurfaceView implements Renderer {

	/* Objeto */
	private Cubo cubo1;
	private Cubo cubo2;
	private Piso piso;
	private Piramide piramide;
	private TrianRectangulo trian;
	private Cilindro cilindro;

	/* Texturas */
	Textura texturabtn1;
	Textura texturabtn2;
	Textura texturabtn3;

	/* Inicializa ubicaci�n de la vista del observador */
	private final float[] vectorEntrada = { 0, 0, -1, 1 };
	private static float posicion[] = { 0, 1, 3 };
	private final float[] direccion = new float[4];

	final float[] matriz = new float[16];

	/* Tama�o de la ventana en pixeles */
	private int alto;
	private int ancho;

	/* Para la rotacion y traslaci�n */
	private float rotX;
	private float rotY;
	private float antY;
	private float antX;

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

		cubo1 = new Cubo(255);
		cubo2 = new Cubo(125);
		piso = new Piso();
		piramide = new Piramide();
		trian = new TrianRectangulo();
		cilindro = new Cilindro(45,1);

		/* Lee las texturas */
		texturabtn1 = new Textura(gl, contexto, "numero1.png");
		texturabtn1.setVertices(-2, -5.5f, 1, 1);

		texturabtn2 = new Textura(gl, contexto, "numero2.png");
		texturabtn2.setVertices(0, -5.5f, 1, 1);
		
		texturabtn3 = new Textura(gl, contexto, "numero3.png");
		texturabtn3.setVertices(2, -5.5f, 1, 1);

		antY = -1;
		antX = -1;
		/* Habilita el ocultamiento de superficies */
		gl.glEnable(GL10.GL_DEPTH_TEST);
		/* Color de fondo */
		gl.glClearColor(176 / 255f, 196 / 255f, 222 / 256f, 0);

	}

	// @Override
	public void onDrawFrame(GL10 gl) {

		/* Borra el buffer de la ventana y del z-buffer */
		gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);

		gl.glEnable(GL10.GL_DEPTH_TEST);
		gl.glMatrixMode(GL10.GL_PROJECTION);
		gl.glLoadIdentity();
		
		GLU.gluPerspective(gl, 67, ancho / (float) alto, 1f, 100f);
		gl.glMatrixMode(GL10.GL_MODELVIEW);
		gl.glLoadIdentity();
		gl.glRotatef(-rotY, 1, 0, 0);
		gl.glRotatef(-rotX, 0, 1, 0);
		gl.glTranslatef(-posicion[0], -posicion[1], -posicion[2]);

		// Piso
		gl.glPushMatrix();
			gl.glTranslatef(0, 0, -6);
			piso.dibuja(gl);
		gl.glPopMatrix();
		// Cubo 1
		gl.glPushMatrix();
			gl.glTranslatef(-4, 0, -4);
			gl.glScalef(0.5f, 0.5f, 0.5f);
			cubo1.dibuja(gl);
		gl.glPopMatrix();
		// Cubo 2
		gl.glPushMatrix();
			gl.glTranslatef(4, 0, -4);
			gl.glScalef(0.5f, 0.5f, 0.5f);
			cubo2.dibuja(gl);
		gl.glPopMatrix();
		// Piramide
		gl.glPushMatrix();
			gl.glTranslatef(0, 0, -3);
			gl.glScalef(0.3f, 0.3f, 0.3f);
			piramide.dibuja(gl);
		gl.glPopMatrix();
		// Triangulo-Rectangulo
		gl.glPushMatrix();
			gl.glTranslatef(0, 0, -5);
			gl.glScalef(0.3f, 0.3f, 0.3f);
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

		texturabtn1.muestra(gl);
		texturabtn2.muestra(gl);
		texturabtn3.muestra(gl);

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
	 * Maneja los eventos del movimiento en la pantalla t�ctil.
	 */
	@Override
	public boolean onTouchEvent(MotionEvent e) {

		/* Obtiene la coordenada de la pantalla */
		float x = e.getX();
		float y = e.getY();

		/* Se considera cuando se levanta el dedo de la pantalla. */
		if (e.getAction() == MotionEvent.ACTION_DOWN || e.getAction() == MotionEvent.ACTION_MOVE) {

			/* En coordenadas del OpenGL */
			float posx = ((x / (float) ancho) * 8) - 4;
			float posy = ((1 - y / (float) alto) * 12) - 6;

			/* Verifica boton elegido */
			if (puntoEstaDentroDelRectangulo(posx, posy, -2, -5.5f, 1, 1)) { // 1
				Matrix.setIdentityM(matriz, 0);
				Matrix.rotateM(matriz, 0, rotY, 0, 1, 0);
				Matrix.multiplyMV(direccion, 0, matriz, 0, vectorEntrada, 0);
				
				posicion[0] = 0;
				posicion[1] = 1;
				posicion[2] = 6.5f;
				rotX = 0;
				rotY = 0;
				
			} else if (puntoEstaDentroDelRectangulo(posx, posy, 0, -5.5f, 1, 1)) { // 2
				Matrix.setIdentityM(matriz, 0);
				Matrix.rotateM(matriz, 0, rotY, 0, 1, 0);
				Matrix.multiplyMV(direccion, 0, matriz, 0, vectorEntrada, 0);

				posicion[0] = -2.5f;
				posicion[1] = 1;
				posicion[2] = -4;
				rotX = -90;
				rotY = 0;
				
			} else if (puntoEstaDentroDelRectangulo(posx, posy, 2, -5.5f, 1, 1)) { // 3
				Matrix.setIdentityM(matriz, 0);
				Matrix.rotateM(matriz, 0, rotY, 0, 1, 0);
				Matrix.multiplyMV(direccion, 0, matriz, 0, vectorEntrada, 0);

				posicion[0] = 2.5f;
				posicion[1] = 1;
				posicion[2] = -4;
				rotX = 90;
				rotY = 0;
				
			}
			else {
				if (antY == -1) {
					antY = y;
				} else {
					rotY = rotY + (y - antY) * 0.5625f;	// 180 / 320 = 0.5625
					antY = y;
				}
				if (antX == -1) {
					antX = x;
				} else {
					rotX = rotX + (x - antX) * 0.5625f;	// 180 / 320 = 0.5625
					antX = x;
				}
			}
			// requestRender();
		} else {
			antY = -1;
			antX = -1;
		}
		return true;
	}

	private boolean puntoEstaDentroDelRectangulo(float posx, float posy,
			float x, float y, float ancho, float alto) {
		return (x < posx && posx < x + ancho && y < posy && posy < y + alto);
	}
}
