package com.example.laboratorio5;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.opengl.GLU;
import android.opengl.Matrix;
import android.opengl.GLSurfaceView.Renderer;
import android.view.MotionEvent;

public class Renderiza extends GLSurfaceView implements Renderer {
	
	/* Objetos */
	private Cubo cubo1;
	private Cubo cubo2;
	private Cubo muro;
	private Cubo suelo;
	private Piso piso;
	private Cilindro cilindro;
	private Esfera esfera;
	private TrianRectangulo trianRectangulo;
	private Piramide piramide;
	private EsferaMitad esferaMitad;
	private Elipsoide elipsoide;
	
	// extras
	private Piramide piramide1;
	private Esfera esfera1;
	private Esfera esfera2;
	private TrianRectangulo trianRectangulo1;
	
	/* Texturas */
	Textura texturaBotonArr;
	Textura texturaBotonAba;
	Textura texturaBotonIzq;
	Textura texturaBotonDer;
	Textura texturaBoton1;
	Textura texturaBoton2;
	
	/* Inicializa ubicación de la vista del observador */
	private final float[] vectorEntrada = { 0, 0, -1, 1 };
	private static float posicion[] = { 0, 7, -16 };
	private final float[] direccion = new float[4];
	
	final float[] matriz = new float[16];
	
	/* Para la rotación y traslación */
	private float rotX = -25, rotY = 180;
	private	float antX, antY;

	/* Tamaño de la ventana en pixeles */
	private int alto;
	private int ancho;
	
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
		
		cubo1 = new Cubo(1.3f, 1, 1.3f, 110, 108, 108);	// RGB
		cubo2 = new Cubo(1, 3.5f, 1, 72, 54, 54);
		muro = new Cubo(8, 3, 0.3f, 124, 111, 111);
		suelo = new Cubo(10f, 0.2f, 6f, 57, 21, 14);
		piso = new Piso();
		esfera = new Esfera(1,4,4,0,255,0);
		cilindro = new Cilindro(48, 1);
		piramide = new Piramide(57, 52, 52);
		trianRectangulo = new TrianRectangulo(0.2f, 1.15f, 160, 20, 10);
		esferaMitad = new EsferaMitad(2,20,20,0,255,0);
		elipsoide = new Elipsoide(1,40,40);
		
		// extras
		piramide1 = new Piramide(20,20,20);
		esfera1 = new Esfera(2,8,2,0,0,0);
		esfera2 = new Esfera(1,30,30,0,0,0);
		trianRectangulo1 = new TrianRectangulo(1f, 1f, 160, 20, 100);
		
		/* Lee las texturas */
		texturaBotonArr = new Textura(gl, this.contexto, "botonarr.png");
		texturaBotonArr.setVertices(-0.5f, -4f, 1, 1);
		
		texturaBotonAba = new Textura(gl, this.contexto, "botonaba.png");
		texturaBotonAba.setVertices(-0.5f, -5.5f, 1, 1);
		
		texturaBotonIzq = new Textura(gl, this.contexto, "botonizq.png");
		texturaBotonIzq.setVertices(-2f, -4.75f, 1, 1);
		
		texturaBotonDer = new Textura(gl, this.contexto, "botonder.png");
		texturaBotonDer.setVertices(1f, -4.75f, 1, 1);
		
		texturaBoton1 = new Textura(gl, this.contexto, "numero1.png");
		texturaBoton1.setVertices(-3.5f, -4.75f, 1, 1);
		
		texturaBoton2 = new Textura(gl, this.contexto, "numero2.png");
		texturaBoton2.setVertices(2.5f, -4.75f, 1, 1);
		
		antX = antY = -1;
		
		/* Se habilita el modo de sombreado plano */
		gl.glShadeModel(GL10.GL_FLAT);
		/* Se habilita el ocultamiento de superficies */
		gl.glEnable(GL10.GL_DEPTH_TEST);
		
		gl.glClearColor(176/255f, 196/255f, 222/255f, 0);
	
	}
	
	// @Override
	public void onDrawFrame(GL10 gl) {

		/* Borra el buffer de la ventana y del z-buffer */
		gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);
		
		gl.glEnable(GL10.GL_DEPTH_TEST);
		
		/* Matriz de Proyección */
		gl.glMatrixMode(GL10.GL_PROJECTION);
		gl.glLoadIdentity();
		
//		GLU.gluPerspective(gl, 67, ancho / (float)alto, 1f, 50f);
		GLU.gluPerspective(gl, 67, ancho / (float)alto, 1f, 100f);
//		gl.glFrustumf(-4, 4, -6, 6, 1, 50);
//		gl.glOrthof(-4, 4, -6, 6, 1, 50);
		gl.glMatrixMode(GL10.GL_MODELVIEW);
		gl.glLoadIdentity();
		gl.glRotatef(-rotX, 1, 0, 0);
		gl.glRotatef(-rotY, 0, 1, 0);
		gl.glTranslatef(-posicion[0], -posicion[1], -posicion[2]);
		
		// Piso
		gl.glPushMatrix();
			gl.glTranslatef(0, 0, -6);
			piso.dibuja(gl);
		gl.glPopMatrix();
		
		// Arboles
		for (int z = -4; z >= -10; z = z - 2) {
			for (int x = -6; x <= 6; x = x + 2) {
				gl.glPushMatrix();
					gl.glTranslatef(x, 0, z);	// traslada
					gl.glScalef(0.5f, 0.5f, 0.5f);	// tamaño
					trianRectangulo.dibuja(gl);
					gl.glPushMatrix();
						gl.glTranslatef(0, 2, 0);
						esfera.dibuja(gl);	
					gl.glPopMatrix();
				gl.glPopMatrix();
				
			}
		}
		// suelo
		gl.glPushMatrix();
			gl.glTranslatef(0, -0.5f, 1);	// traslada
			gl.glScalef(0.5f, 0.5f, 0.5f);	// tamaño
			suelo.dibuja(gl);
		gl.glPopMatrix();
		
		// bloques
		for (int z = -1; z <= 3; z = z + 4) {
			for (int x = -4; x <= 4; x = x + 8) {
				gl.glPushMatrix();
					gl.glTranslatef(x, 1.5f, z);	// traslada
					gl.glScalef(0.5f, 0.5f, 0.5f);	// tamaño
					cubo2.dibuja(gl);
					gl.glPushMatrix();
						gl.glTranslatef(0, 3.5f, 0);
						cubo1.dibuja(gl);
					gl.glPopMatrix();
					gl.glPushMatrix();
						gl.glTranslatef(0, 5.5f, 0);
						piramide.dibuja(gl);
					gl.glPopMatrix();
				gl.glPopMatrix();
			}
		}
		// muros 
		for (int z = -2; z <= 6; z = z + 8) {
			gl.glPushMatrix();
				gl.glScalef(0.5f, 0.5f, 0.5f);	// tamaño
				gl.glTranslatef(0, 1.5f, z);
				muro.dibuja(gl);
			gl.glPopMatrix();
		}
		for (int x = -8; x <= 8; x = x +16) {
			gl.glPushMatrix();
				gl.glRotatef(90, 0, 1, 0);		// rotamos
				gl.glScalef(0.2f, 0.5f, 0.5f);	// tamaño
				gl.glTranslatef(-4, 1.5f, x);
				muro.dibuja(gl);
			gl.glPopMatrix();
		}
		// mas arboles
		int x = -6;
		for (int z = -2; z <= 4 ; z = z + 2) {
			gl.glPushMatrix();
				gl.glTranslatef(x, 0, z);	// traslada
				gl.glScalef(0.5f, 0.5f, 0.5f);	// tamaño
				trianRectangulo.dibuja(gl);
				gl.glPushMatrix();
					gl.glTranslatef(0, 2, 0);
					esfera.dibuja(gl);	
				gl.glPopMatrix();
			gl.glPopMatrix();
			if (z == 4 && x == -6) {
				x = 6;
				z = -4;
			}
		}
		
		/* ROMBO */
		gl.glPushMatrix();
			gl.glScalef(1f, 1f, 1f);	// tamaño
			gl.glTranslatef(-6, 8, 3);
			piramide1.dibuja(gl);
			gl.glPushMatrix();
				gl.glRotatef(180, 0, 0, 1);		// rotamos
				gl.glTranslatef(0, 2, 0);
				piramide1.dibuja(gl);
			gl.glPopMatrix();
		gl.glPopMatrix();
		
		// rombo 2
		gl.glPushMatrix();
			gl.glTranslatef(-10, 8, 3);
			esfera1.dibuja(gl);
		gl.glPopMatrix();
		
		// esfera
		gl.glPushMatrix();
			gl.glTranslatef(-14, 8, 3);
			esfera2.dibuja(gl);
		gl.glPopMatrix();
		
		// trianRectangulo
		gl.glPushMatrix();
			gl.glTranslatef(-15, 4, 3);
			gl.glRotatef(-75, 0, 1, 0);
			gl.glRotatef(-20, 1, 0, 0);
			trianRectangulo1.dibuja(gl);
		gl.glPopMatrix();
		
		// esferaMitad
		gl.glPushMatrix();
			gl.glTranslatef(-11, 3, 3);
			gl.glRotatef(90, 0, 0, 1);
			esferaMitad.dibuja(gl);
		gl.glPopMatrix();
		
		// cilindro
		gl.glPushMatrix();
			gl.glTranslatef(-8, 2, 4);
			cilindro.dibuja(gl);
		gl.glPopMatrix();
		
		// elipsoide
		gl.glPushMatrix();
			gl.glTranslatef(-16, 1, 4);
			elipsoide.dibuja(gl);
		gl.glPopMatrix();
		
		/* Botones de las opciones */
		gl.glDisable(GL10.GL_DEPTH_TEST);
		gl.glMatrixMode(GL10.GL_PROJECTION);
		gl.glLoadIdentity();
		gl.glOrthof(-4, 4, -6, 6, 1, -1);
		gl.glMatrixMode(GL10.GL_MODELVIEW);
		gl.glLoadIdentity();
		
		
		gl.glEnable(GL10.GL_BLEND);
		gl.glEnable(GL10.GL_TEXTURE_2D);
		gl.glBlendFunc(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);
		
		texturaBotonArr.muestra(gl);
		texturaBotonAba.muestra(gl);
		texturaBotonIzq.muestra(gl);
		texturaBotonDer.muestra(gl);
		texturaBoton1.muestra(gl);
		texturaBoton2.muestra(gl);
		
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

			/* Verifica área elegida */
			if (puntoEstaDentroDelRectangulo(posx, posy, -0.5f, -4f, 1, 1)) { // Adelante
				Matrix.setIdentityM(matriz, 0);
				Matrix.rotateM(matriz, 0, rotY, 0, 1, 0);
				Matrix.rotateM(matriz, 0, rotX, 1, 0, 0);
				Matrix.multiplyMV(direccion, 0, matriz, 0, vectorEntrada, 0);
				
				posicion[0] = posicion[0] + direccion[0] * 0.3f;
				posicion[1] = posicion[1] + direccion[1] * 0.3f;
				posicion[2] = posicion[2] + direccion[2] * 0.3f;
				
			} else if (puntoEstaDentroDelRectangulo(posx, posy, -0.5f, -5.5f, 1, 1)) { // Atras
				Matrix.setIdentityM(matriz, 0);
				Matrix.rotateM(matriz, 0, rotY, 0, 1, 0);
				Matrix.rotateM(matriz, 0, rotX, 1, 0, 0);
				Matrix.multiplyMV(direccion, 0, matriz, 0, vectorEntrada, 0);
				
				posicion[0] = posicion[0] - direccion[0] * 0.3f;
				posicion[1] = posicion[1] - direccion[1] * 0.3f;
				posicion[2] = posicion[2] - direccion[2] * 0.3f;
			}
			else if (puntoEstaDentroDelRectangulo(posx, posy, -2f, -4.75f, 1, 1)) { // Izquierda
				Matrix.setIdentityM(matriz, 0);
				Matrix.rotateM(matriz, 0, rotY, 0, 1, 0);
				Matrix.multiplyMV(direccion, 0, matriz, 0, vectorEntrada, 0);
				
				// rotX = 0;
				rotY = rotY + 10f;
				// posicion[0] = posicion[0] + direccion[0] * 0.3f;
				
			} else if (puntoEstaDentroDelRectangulo(posx, posy, 1f, -4.75f, 1, 1)) { // Derecha
				Matrix.setIdentityM(matriz, 0);
				Matrix.rotateM(matriz, 0, rotY, 0, 1, 0);
				Matrix.multiplyMV(direccion, 0, matriz, 0, vectorEntrada, 0);
				
				// rotX = 0;
				rotY = rotY - 10f;
				// posicion[0] = posicion[0] - direccion[0] * 0.3f;
				
			}else if (puntoEstaDentroDelRectangulo(posx, posy, -3.5f, -4.75f, 1, 1)) { // Arriba
				Matrix.setIdentityM(matriz, 0);
				Matrix.rotateM(matriz, 0, rotY, 0, 1, 0);
				Matrix.multiplyMV(direccion, 0, matriz, 0, vectorEntrada, 0);
				
				posicion[0] = 0;
				posicion[1] = 7;
				posicion[2] = -16;
				rotX = -25;
				rotY = 180;
				
			} else if (puntoEstaDentroDelRectangulo(posx, posy, 2.5f, -4.75f, 1, 1)) { // Abajo
				Matrix.setIdentityM(matriz, 0);
				Matrix.rotateM(matriz, 0, rotY, 0, 1, 0);
				Matrix.multiplyMV(direccion, 0, matriz, 0, vectorEntrada, 0);
				
				posicion[0] = 0;
				posicion[1] = 10;
				posicion[2] = -25;
				rotX = -25;
				rotY = 180;
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
