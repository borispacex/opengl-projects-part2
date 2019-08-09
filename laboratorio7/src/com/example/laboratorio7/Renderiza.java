package com.example.laboratorio7;

import java.io.IOException;

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
	private Terreno terreno;
	
	/* Texturas */
	Textura texturaBotonArr;
	Textura texturaBotonAba;
	Textura texturaBotonIzq;
	Textura texturaBotonDer;
	Textura texturaBoton1;
	Textura texturaBoton2;

	
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
	
	/* Animacion */
	MD2 md2 = new MD2();
	boolean estaBienElModelo;
	String nombreCuadro = "stand";
	String cuadros[] = {"stand", "run", "attack", "paina", "painb", "painc", "jump", "flip", "salute", "bumflop",
			"wavealt", "sniffsniff", "cstand", "cwalk", "crattack", "crpain", "crdeath", "datha", "dathb", "dathc", "boom"};
	int iCuadro = 0;
	
	public Renderiza(Context contexto){
		super(contexto);
		this.contexto = contexto;
		this.setRenderer(this);
		this.requestFocus();
		this.setFocusableInTouchMode(true);
		this.setRenderMode(GLSurfaceView.RENDERMODE_CONTINUOUSLY);
	}
	public void setNombreCuadro(String nombreCuadro){
		this.nombreCuadro = nombreCuadro;
	}
	
	// @Override
	public void onSurfaceCreated(GL10 gl, EGLConfig arg1) {
		/*
		obj = new Objeto(contexto, "porsche.obj");
		obj1 = new Objeto(contexto, "dog.obj");
		*/
		obj = new Objeto(contexto, "yamaha-yzr-r6.obj");
		obj1 = new Objeto(contexto, "pianoAQueue.obj");
		obj2 = new Objeto(contexto, "teddyBear.obj");
		/*
		obj = new Objeto(contexto, "table_football.obj");
		obj1 = new Objeto(contexto, "pianoAQueue.obj");
		obj2 = new Objeto(contexto, "teddyBear.obj");
		*/
		
		
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
		
		// piso = new Piso();
		terreno = new Terreno(gl, contexto, "heightmap4_128.raw", "texture.png");
		// terreno = new Terreno(gl, contexto, "heightmap3_65.raw", "texture.png");
		
		
		/* Definición del color de la luz - Valores por defecto */
		float luz_ambiente[]  = { 0, 0, 0, 1}; // I 
		float luz_difusa[]    = { 1, 1, 1, 1 }; 
		float luz_especular[] = { 1, 1, 1, 1 }; 
		float luz_posicion[]  = { 0, 0, 5, 0}; // L 	w == 0 (direccional) w == 1 (posicional)
		
		gl.glLightfv(GL10.GL_LIGHT0, GL10.GL_AMBIENT, luz_ambiente, 0);
		gl.glLightfv(GL10.GL_LIGHT0, GL10.GL_DIFFUSE, luz_difusa, 0);
		gl.glLightfv(GL10.GL_LIGHT0, GL10.GL_SPECULAR, luz_especular, 0);
		gl.glLightfv(GL10.GL_LIGHT0, GL10.GL_POSITION, luz_posicion, 0);
		
		/* Animacion de Objeto movimiento */
		try {
			// estaBienElModelo = md2.leeArchivoMD2(contexto, gl, "Ogros.md2", "igdosh.png");
			estaBienElModelo = md2.leeArchivoMD2(contexto, gl, "ogro2.md2", "skin.jpg");
		} catch (IOException e) {
			System.err.println("Error parsing :");
			e.printStackTrace();
		}
		gl.glEnable(GL10.GL_COLOR_MATERIAL);
		
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
		
		/* Habilita la textura */
		gl.glEnable(GL10.GL_TEXTURE_2D);
		/* Ocultamiento de caras */
		gl.glEnable(GL10.GL_CULL_FACE);
		/* Cara de Atrás (GL_BACK) (por defecto) */
		/* Cara de Adelante (GL_FRONT) */
		gl.glCullFace(GL10.GL_BACK);
		/* Antihorario (GL_CCW) (por defecto) */
		/* Horario (GL_CW) */
		gl.glFrontFace(GL10.GL_CCW);
		
		/* Fondo negro */
		gl.glClearColor(12/255f, 183/255f, 242/256f, 0);
	}
	
	// @Override
	public void onDrawFrame(GL10 gl) {
		if (estaBienElModelo)
			md2.animacion(nombreCuadro);
		
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
		
		/* Definición del color del material - Valores por defecto */
		float mat_ambiente []  = {0.2f, 0.2f, 0.2f, 1}; // K
		float mat_difuso []    = {0.8f, 0.8f, 0.8f, 1};
		float mat_especular [] = {0f, 0f, 0f, 1};
		float mat_brillo       = 0f;
		
		gl.glMaterialfv(GL10.GL_FRONT_AND_BACK, GL10.GL_AMBIENT, mat_ambiente, 0);
		gl.glMaterialfv(GL10.GL_FRONT_AND_BACK, GL10.GL_DIFFUSE, mat_difuso, 0);
		gl.glMaterialfv(GL10.GL_FRONT_AND_BACK, GL10.GL_SPECULAR, mat_especular, 0);
		gl.glMaterialf(GL10.GL_FRONT_AND_BACK, GL10.GL_SHININESS, mat_brillo);
		
		float escala = 5f / terreno.getAncho();
		
		gl.glPushMatrix();
			// gl.glTranslatef(0, 0, -6);
			// piso.dibuja(gl);
			gl.glEnable(GL10.GL_TEXTURE_2D);
			gl.glScalef(escala, escala, escala);
			gl.glTranslatef(-terreno.getAncho()/2, -18, -terreno.getAncho()/2);
			gl.glBindTexture(GL10.GL_TEXTURE_2D, terreno.getCodigoTextura());
			terreno.dibuja(gl);
			gl.glDisable(GL10.GL_TEXTURE_2D);
		gl.glPopMatrix();
		
		gl.glPushMatrix();
			gl.glTranslatef(0, 0, 0);
			gl.glScalef(0.5f, 0.5f, 0.5f);
			obj.dibuja(gl);
		gl.glPopMatrix();
		
		gl.glPushMatrix();
			gl.glTranslatef(1, 0, -1);
			gl.glScalef(0.5f, 0.5f, 0.5f);
			obj1.dibuja(gl);
		gl.glPopMatrix();
		
		gl.glPushMatrix();
			gl.glTranslatef(-1.5f, 0, -1);
			gl.glScalef(0.5f, 0.5f, 0.5f);
			obj2.dibuja(gl);
		gl.glPopMatrix();
		
		/******************* ANIMACION  *******************/
		/* Definición del color de la luz */
		float luz_ambiente[] = {0.3f, 0.3f, 0.3f, 1.0f};
		float luz_difusa[] = {0.7f, 0.7f, 0.7f, 1.0f};
		float luz_posicion[] = {-0.2f, 0.3f, 1, 0.0f};
		gl.glLightModelfv(GL10.GL_LIGHT_MODEL_AMBIENT, luz_ambiente, 0);
		gl.glLightfv(GL10.GL_LIGHT0, GL10.GL_DIFFUSE, luz_difusa, 0);
		gl.glLightfv(GL10.GL_LIGHT0, GL10.GL_POSITION, luz_posicion, 0);
		
		if (estaBienElModelo) {
			gl.glPushMatrix();
				gl.glEnable(GL10.GL_COLOR_MATERIAL);
				gl.glTranslatef(-0.25f, 0.05f, -1.5f);
				gl.glRotatef(-90.0f, 0.0f, 1.0f, 0.0f);
				gl.glRotatef(-90.0f, 1.0f, 0.0f, 0.0f);
				gl.glScalef(0.02f, 0.02f, 0.02f);
				md2.dibuja(gl);
				gl.glDisable(GL10.GL_COLOR_MATERIAL);
			gl.glPopMatrix();
		}
		// Avanza la animacion
		if (estaBienElModelo)
			md2.avanza(0.025f);
		/****************** FIN ANIMACION *******************/
		
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

		gl.glPushMatrix();
			texturaBotonArr.muestra(gl);
			texturaBotonAba.muestra(gl);
			texturaBotonIzq.muestra(gl);
			texturaBotonDer.muestra(gl);
			texturaBoton1.muestra(gl);
			texturaBoton2.muestra(gl);
		gl.glPopMatrix();
		
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
		// GLU.gluPerspective(gl, 45, (float)w / h, 1f, 200f);
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
			if (puntoEstaDentroDelRectangulo(posx, posy, -0.5f, -4f, 1, 1)) { // Adelante
				Matrix.setIdentityM(matriz, 0);
				Matrix.rotateM(matriz, 0, rotY, 0, 1, 0);
				Matrix.rotateM(matriz, 0, rotX, 1, 0, 0);
				Matrix.multiplyMV(direccion, 0, matriz, 0, vectorEntrada, 0);
				
				posicion[0] = posicion[0] + direccion[0] * 0.2f;
				posicion[1] = posicion[1] + direccion[1] * 0.2f;
				posicion[2] = posicion[2] + direccion[2] * 0.2f;
				
			} else if (puntoEstaDentroDelRectangulo(posx, posy, -0.5f, -5.5f, 1, 1)) { // Atras
				Matrix.setIdentityM(matriz, 0);
				Matrix.rotateM(matriz, 0, rotY, 0, 1, 0);
				Matrix.rotateM(matriz, 0, rotX, 1, 0, 0);
				Matrix.multiplyMV(direccion, 0, matriz, 0, vectorEntrada, 0);
				
				posicion[0] = posicion[0] - direccion[0] * 0.2f;
				posicion[1] = posicion[1] - direccion[1] * 0.2f;
				posicion[2] = posicion[2] - direccion[2] * 0.2f;
			}
			else if (puntoEstaDentroDelRectangulo(posx, posy, -2f, -4.75f, 1, 1)) { // Izquierda
				Matrix.setIdentityM(matriz, 0);
				Matrix.rotateM(matriz, 0, rotY, 0, 1, 0);
				Matrix.multiplyMV(direccion, 0, matriz, 0, vectorEntrada, 0);
				
				// rotX = 0;
				rotY = rotY + 5f;
				// posicion[0] = posicion[0] + direccion[0] * 0.3f;
				
			} else if (puntoEstaDentroDelRectangulo(posx, posy, 1f, -4.75f, 1, 1)) { // Derecha
				Matrix.setIdentityM(matriz, 0);
				Matrix.rotateM(matriz, 0, rotY, 0, 1, 0);
				Matrix.multiplyMV(direccion, 0, matriz, 0, vectorEntrada, 0);
				
				// rotX = 0;
				rotY = rotY - 5f;
				// posicion[0] = posicion[0] - direccion[0] * 0.3f;
				
			}else if (puntoEstaDentroDelRectangulo(posx, posy, -3.5f, -4.75f, 1, 1)) { // 1
				Matrix.setIdentityM(matriz, 0);
				Matrix.rotateM(matriz, 0, rotY, 0, 1, 0);
				Matrix.multiplyMV(direccion, 0, matriz, 0, vectorEntrada, 0);
				
				posicion[0] = 3;
				posicion[1] = 4;
				posicion[2] = 0;
				rotX = -45;
				rotY = 80;
				
			} else if (puntoEstaDentroDelRectangulo(posx, posy, 2.5f, -4.75f, 1, 1)) { // 2
				// cambiaremos de imagen
				iCuadro = iCuadro + 1;
				if (iCuadro == cuadros.length)
					iCuadro = 0;
				this.nombreCuadro = cuadros[iCuadro];
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