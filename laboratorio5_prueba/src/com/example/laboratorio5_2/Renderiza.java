package com.example.laboratorio5_2;

import java.io.PushbackInputStream;

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
 * @version 1.0 02/04/2016
 *
 */
public class Renderiza extends GLSurfaceView implements Renderer {
	
	/* Objetos */
	private Cubo cubo;
	private Piso piso;
	private Esfera esfera;
	private Esfera manzana;
	private Piramide piramide;
	private Piramide piramide1;
	private Piramide piramide2;
	private Piramide piramide3;
	private Piramide piramide4;
	private Triangulo piramide5;
	private Triangulo piramide6;
	private Triangulo piramide7;
	private Triangulo piramide8;
	private Triangulo piramide9;
	private TrianRectangulo triangulorec;
	private Cilindro cilindro;
	private Esfera1 esfera1;
	private Esfera3 foco;
	/* Texturas */
	Textura texturaBotonArr;
	Textura texturaBotonAba;
	Textura texturaBotonDerr;
	Textura texturaBotonIzq;
	
	/* Inicializa ubicación de la vista del observador */
	private final float[] vectorEntrada = { 0, 0, -1, 1 };
	private static float posicion[] = { 0, 1, 3 };
	private final float[] direccion = new float[4];
	
	final float[] matriz = new float[16];
	
	/* Para la rotación y traslación */
	private float rotX, rotY;
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
		
		cubo = new Cubo();
		piso = new Piso();
		piramide = new Piramide();
		piramide1 = new Piramide();
		piramide2 = new Piramide();
		piramide3 = new Piramide();
		piramide4 = new Piramide();
		
		piramide5= new Triangulo();
		piramide6 = new Triangulo();
		piramide7 = new Triangulo();
		
		piramide8 = new Triangulo();
		piramide9 = new Triangulo();
		triangulorec = new TrianRectangulo();
		cilindro = new Cilindro(45,1);
		esfera = new Esfera(1,48,48);
		esfera1 = new Esfera1(3,50,50);
		foco = new Esfera3(0.5f,48,48);
		manzana = new Esfera(1, 20, 20); 
		
		
		/* Lee las texturas */
		texturaBotonArr = new Textura(gl, contexto, "botonarr.png");
		texturaBotonArr.setVertices(-0.5f, -4f, 1, 1);
		
		texturaBotonAba = new Textura(gl, contexto, "botonaba.png");
		texturaBotonAba.setVertices(-0.5f, -5.5f, 1, 1);
		
		texturaBotonDerr= new Textura(gl, contexto, "botonder.png");
		texturaBotonDerr.setVertices( 0.5f, -4.7f, 1, 1);
		
		texturaBotonIzq = new Textura(gl, contexto, "botonizq.png");
		texturaBotonIzq.setVertices(-1.3f, -4.7f, 1, 1);
		
		antX = antY = -1;
		
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
		GLU.gluPerspective(gl, 67, ancho / (float)alto, 1f, 50f);
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
		gl.glScalef(4, 4, 4);
		piso.dibuja(gl);
		gl.glPopMatrix();
		
		// Cubos
		/*
		for (int z = 0; z >= -8; z = z - 2) {
			for (int x = -4; x <= 4; x = x + 2) {
				gl.glPushMatrix();
				gl.glTranslatef(x, 0, z);
				gl.glScalef(0.5f, 0.5f, 0.5f);
				cubo.dibuja(gl);
				gl.glPopMatrix();
			}
		}*/
		 gl.glPushMatrix();
		 gl.glTranslatef(11, 0, 1);
		 esfera.dibuja(gl);
		 gl.glPopMatrix();
		 
		 gl.glPushMatrix();
		 gl.glTranslatef(9, 0, 1);
		 cilindro.dibuja(gl);
		 gl.glPopMatrix();
		 
		 //cilindro arbol 2
		 gl.glPushMatrix();
		 gl.glTranslatef(9,0,-5);
		 gl.glScalef(0.2f,1,0.2f);
		 cilindro.dibuja(gl);
		 gl.glPopMatrix();
		 
		 gl.glPushMatrix();
		 gl.glTranslatef(9, 2,-5);
		 esfera.dibuja(gl);
		 gl.glPopMatrix();
		 
		 gl.glPushMatrix();
		 gl.glColor4f(1, 0, 0, 0);
		 gl.glScalef(0.2f, 0.2f, 0.2f);
		 gl.glTranslatef(9, 0,-5);
		 manzana.dibuja(gl);
		 gl.glPopMatrix();
		 
		 
		 gl.glPushMatrix();
		 gl.glTranslatef(3, 0, -10);
		 gl.glScalef(2, 3, 2);
		 piramide.dibuja(gl);
		 gl.glPopMatrix();
		 
		 
		/*esfera cilindro triangulorec piramide cubo*/
		
	        //arbol
			gl.glPushMatrix();
			gl.glTranslatef(-4,0,0);
			//tallo
			gl.glPushMatrix();
			gl.glScalef(0.2f,0.5f,0.2f);
			cubo.dibuja(gl);
			gl.glPopMatrix();
			
			//arbusto
			gl.glPushMatrix();
			gl.glRotatef(180,0,0,1);
			gl.glTranslatef(0,-1.5f,0);
			piramide5.dibuja(gl);
			gl.glPopMatrix();
	        //arbusto
			gl.glPushMatrix();
			gl.glRotatef(180,0,0,1);
			gl.glTranslatef(0,-2.5f,0);
            piramide6.dibuja(gl);
			gl.glPopMatrix();
			//arbusto
			gl.glPushMatrix();
			gl.glRotatef(180,0,0,1);
			gl.glTranslatef(0,-3.5f,0);
			gl.glScalef(0.6f,0.6f,0.6f);
			piramide7.dibuja(gl);
			gl.glPopMatrix();

			gl.glPopMatrix();

		 
			 gl.glPushMatrix();
			 gl.glTranslatef(5, 30,-8);
			 esfera1.dibuja(gl);
			 gl.glPopMatrix();
		 
		   //iglecia 
			 //alto
			 gl.glPushMatrix();
			 gl.glTranslatef(-15, 0,-40);
			 gl.glScalef(2,4,2);
			 cubo.dibuja(gl);
			 gl.glPopMatrix();
			 
			 //largox
			 gl.glPushMatrix();
			 gl.glTranslatef(-10, -2,-40);
			 gl.glScalef(4,2,2);
			 cubo.dibuja(gl);
			 gl.glPopMatrix();
			 
			 //largoz
			 gl.glPushMatrix();
			 gl.glTranslatef(-15,-2,-34);
			 gl.glScalef(2,2,4);
			 cubo.dibuja(gl);
			 gl.glPopMatrix();
			 
			 //techo
			 gl.glPushMatrix();
				gl.glRotatef(180,0,0,1);
				gl.glTranslatef(15,-6,-40);
				gl.glScalef(2f,2f,2f);
				piramide7.dibuja(gl);
				gl.glPopMatrix();
			gl.glPopMatrix();
			 
			 
			//casa
			 gl.glPushMatrix();
			 gl.glTranslatef(-5, 0,20);
			 gl.glScalef(4,3,2);
			 cubo.dibuja(gl);
			 gl.glPopMatrix();
			 
			 gl.glPushMatrix();
			 gl.glRotatef(180,0,0,1);
			 gl.glTranslatef(0, 10, 0);
			 triangulorec.dibuja(gl);
			 gl.glPopMatrix();
			 //piramide
			 gl.glPushMatrix();
			gl.glRotatef(180,0,0,1);
			gl.glTranslatef(20,-8,14);
			gl.glScalef(5, 10, 5);
			piramide8.dibuja(gl);
			gl.glPopMatrix();
		 // edificio
			gl.glPushMatrix();
			 gl.glTranslatef(-20,8,0);
			 gl.glScalef(6, 10, 6);
			 gl.glColor4f(0.36f ,0.63f ,0.38f ,0);
			 cilindro.dibuja(gl);
			 gl.glPopMatrix();
		 
		 //limunarias
			 gl.glPushMatrix();
			 gl.glPushMatrix();
				gl.glScalef(0.1f,3,0.1f);
				gl.glTranslatef(-4,0,-3);
				cubo.dibuja(gl);
				
		       gl.glPopMatrix();
		       gl.glPushMatrix();
				gl.glTranslatef(-0.3f,3,0);
				foco.dibuja(gl);
		       gl.glPopMatrix();
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
		texturaBotonDerr.muestra(gl);
		texturaBotonIzq.muestra(gl);
		
		
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
			if (puntoEstaDentroDelRectangulo(posx, posy, -0.5f, -4f, 1, 1)) { // Arr
				Matrix.setIdentityM(matriz, 0);
				Matrix.rotateM(matriz, 0, rotY, 0, 1, 0);
				Matrix.rotateM(matriz, 0, rotX, 1, 0, 0);
				Matrix.multiplyMV(direccion, 0, matriz, 0, vectorEntrada, 0);
				
				posicion[0] = posicion[0] + direccion[0] * 0.1f;
				posicion[1] = posicion[1] + direccion[1] * 0.1f;
				posicion[2] = posicion[2] + direccion[2] * 0.1f;
				
			} else if (puntoEstaDentroDelRectangulo(posx, posy,-0.5f,-5.5f, 1, 1)) { // Abj
				Matrix.setIdentityM(matriz, 0);
				Matrix.rotateM(matriz, 0, rotY, 0, 1, 0);
				Matrix.multiplyMV(direccion, 0, matriz, 0, vectorEntrada, 0);
				
				posicion[0] = posicion[0] - direccion[0] * 0.1f;
				posicion[1] = posicion[1] - direccion[1] * 0.1f;
				posicion[2] = posicion[2] - direccion[2] * 0.1f;
				
			}else if (puntoEstaDentroDelRectangulo(posx, posy,0.5f,-4.7f, 1, 1)) { // Abjdereha
				Matrix.setIdentityM(matriz, 0);
				Matrix.rotateM(matriz, 0, rotY, 0, 1, 0);
				Matrix.multiplyMV(direccion,0, matriz, 0, vectorEntrada, 0);
				
				posicion[0] = posicion[0] - direccion[0] * 0.1f;
				posicion[1] = posicion[1] - direccion[1] * 0.1f;
				posicion[2] = posicion[2] - direccion[2] * 0.1f;
				
			}
			else if (puntoEstaDentroDelRectangulo(posx, posy, -1.3f, -4.7f, 1, 1)) { // Abjizq
				Matrix.setIdentityM(matriz, 0);
				Matrix.rotateM(matriz, 0, rotY, 0, 1, 0);
				Matrix.multiplyMV(direccion, 0, matriz, 0, vectorEntrada, 0);
				
				posicion[0] = posicion[0] - direccion[0] * 0.1f;
				posicion[1] = posicion[1] - direccion[1] * 0.1f;
				posicion[2] = posicion[2] - direccion[2] * 0.1f;
				
			}else {
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
