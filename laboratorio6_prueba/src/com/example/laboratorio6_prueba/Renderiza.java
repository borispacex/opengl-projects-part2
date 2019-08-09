package com.example.laboratorio6_prueba;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.opengl.GLU;
import android.opengl.Matrix;
import android.opengl.GLSurfaceView.Renderer;
import android.view.MotionEvent;

public class Renderiza extends GLSurfaceView implements Renderer {
	
	/* Objeto */
	private CuboIluminacion cubo;
	private Piso plano;
	
	/* Objetos labo 5 */
	private Cubo cubo1;
	private Cubo cubo2;
	private Cubo muro;
	private Cubo suelo;
	private Cilindro cilindro;
	private Esfera esfera;
	private TrianRectangulo trianRectangulo;
	private Piramide piramide;
	private EsferaMitad esferaMitad;
	private Elipsoide elipsoide;
	
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
	
	// luz persona
	float luz_ambiente[]  = { 0, 0, 0, 1}; // I 
	float luz_difusa[]    = { 1, 1, 1, 1 }; 
	float luz_especular[] = { 1, 1, 1, 1 }; 
	float luz_posicion[]  = { 0, 0, 1, 0};
	
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
		
		cubo = new CuboIluminacion();
		plano = new Piso();
		
		// labo 5
		cubo1 = new Cubo(1.3f, 1, 1.3f);	// RGB
		cubo2 = new Cubo(1, 3.5f, 1);
		muro = new Cubo(8, 3, 0.3f);
		suelo = new Cubo(10f, 0.2f, 6f);
		esfera = new Esfera(1,4,4,0,255,0);
		cilindro = new Cilindro(48, 1);
		piramide = new Piramide(57, 52, 52);
		trianRectangulo = new TrianRectangulo(0.2f, 1.15f, 160, 20, 10);
		esferaMitad = new EsferaMitad(2,20,20,0,255,0);
		elipsoide = new Elipsoide(1,40,40);
		
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
		
		/* Definición del color de la luz - Valores por defecto */
		
		float luz_ambiente1[]  = { 0, 0, 0, 1}; // I 
		float luz_difusa1[]    = { 1, 1, 1, 1 }; 
		float luz_especular1[] = { 1, 1, 1, 1 }; 
		float luz_posicion1[]  = { 0, 1, 0, 0};
		
		
		
		gl.glLightfv(GL10.GL_LIGHT0, GL10.GL_AMBIENT, luz_ambiente1, 0);
		gl.glLightfv(GL10.GL_LIGHT0, GL10.GL_DIFFUSE, luz_difusa1, 0);
		gl.glLightfv(GL10.GL_LIGHT0, GL10.GL_SPECULAR, luz_especular1, 0);
		gl.glLightfv(GL10.GL_LIGHT0, GL10.GL_POSITION, luz_posicion1, 0);
		gl.glEnable(GL10.GL_LIGHT0);
		
		/* Se habilita la interpolación del sombreado */
		gl.glShadeModel(GL10.GL_SMOOTH); 
		
		/* Se habilita el ocultamiento de superficies */
		gl.glEnable(GL10.GL_DEPTH_TEST);
		
		/* Se habilita la iluminación */
		gl.glEnable(GL10.GL_LIGHTING); 
		
		/* Se habilita la luz 0 */
		gl.glEnable(GL10.GL_LIGHT0); 
		
		/* Se habilita la normalización */
		gl.glEnable(GL10.GL_NORMALIZE); 
		
		/* Fondo negro */
		gl.glClearColor(15/255f, 15/255f, 15/255f, 0);
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
		//gl.glFrustumf(-4, 4, -6, 6, 1, 50);
		//gl.glOrthof(-4, 4, -6, 6, 1, 50); // proyeccion en paralelo
		gl.glMatrixMode(GL10.GL_MODELVIEW);
		gl.glLoadIdentity();
		gl.glRotatef(-rotX, 1, 0, 0); 	// cambiando
		gl.glRotatef(-rotY, 0, 1, 0);	// cambiando
		gl.glTranslatef(-posicion[0], -posicion[1], -posicion[2]);	// cambiando
		// pruebas
		
		luz_posicion[0] = -posicion[0]/10f;
		luz_posicion[1] = -posicion[1]/10f;
		luz_posicion[2] = -posicion[2]/10f;
		
		gl.glLightfv(GL10.GL_LIGHT0, GL10.GL_AMBIENT, luz_ambiente, 0);
		gl.glLightfv(GL10.GL_LIGHT0, GL10.GL_DIFFUSE, luz_difusa, 0);
		gl.glLightfv(GL10.GL_LIGHT0, GL10.GL_SPECULAR, luz_especular, 0);
		gl.glLightfv(GL10.GL_LIGHT0, GL10.GL_POSITION, luz_posicion, 0);
		
		
		
		/*************************************** PLANO ******************************************/
		/* Definición del color del material */
		
		float mat_ambiente []  = {0.5020f, 0.2510f, 0f, 1}; // K
		float mat_difuso []    = {0.5020f, 0.2510f, 0f, 1};
		float mat_especular [] = {0.5020f, 0.2510f, 0f, 1};
		float mat_brillo = 255f;
		
		gl.glMaterialfv(GL10.GL_FRONT_AND_BACK, GL10.GL_AMBIENT, mat_ambiente, 0);
		gl.glMaterialfv(GL10.GL_FRONT_AND_BACK, GL10.GL_DIFFUSE, mat_difuso, 0);
		gl.glMaterialfv(GL10.GL_FRONT_AND_BACK, GL10.GL_SPECULAR, mat_especular, 0);
		gl.glMaterialf(GL10.GL_FRONT_AND_BACK, GL10.GL_SHININESS, mat_brillo);
		
		
		// Plano
		gl.glPushMatrix();
			gl.glTranslatef(0, 0.5f, -1);
			plano.dibuja(gl);
		gl.glPopMatrix();
		
		/*************************************** CUBO ROJO ******************************************/
		/* Definición del color del material */
		float mat_ambiente1 []  = {0f   , 0f, 0f, 1}; // K
		float mat_difuso1 []    = {0.95f, 0f, 0f, 1};
		float mat_especular1 [] = {0.95f, 0f, 0f, 1};
		float mat_brillo1       = 255f;
		
		gl.glMaterialfv(GL10.GL_FRONT_AND_BACK, GL10.GL_AMBIENT, mat_ambiente1, 0);
		gl.glMaterialfv(GL10.GL_FRONT_AND_BACK, GL10.GL_DIFFUSE, mat_difuso1, 0);
		gl.glMaterialfv(GL10.GL_FRONT_AND_BACK, GL10.GL_SPECULAR, mat_especular1, 0);
		gl.glMaterialf(GL10.GL_FRONT_AND_BACK, GL10.GL_SHININESS, mat_brillo1);

		// Cubo Rojo
		gl.glPushMatrix();
			gl.glTranslatef(-1.7f, 0, 0);
			cubo.dibuja(gl);
		gl.glPopMatrix();
		/*************************************** CUBO AZUL ******************************************/
		/* Definición del color del material - Valores por defecto */
		float mat_ambiente2 []  = {0f, 0f,    0f, 1}; // K
		float mat_difuso2 []    = {0f, 0f, 0.95f, 1};
		float mat_especular2 [] = {0f, 0f, 0.95f, 1};
		float mat_brillo2       = 255f;
		
		gl.glMaterialfv(GL10.GL_FRONT_AND_BACK, GL10.GL_AMBIENT, mat_ambiente2, 0);
		gl.glMaterialfv(GL10.GL_FRONT_AND_BACK, GL10.GL_DIFFUSE, mat_difuso2, 0);
		gl.glMaterialfv(GL10.GL_FRONT_AND_BACK, GL10.GL_SPECULAR, mat_especular2, 0);
		gl.glMaterialf(GL10.GL_FRONT_AND_BACK, GL10.GL_SHININESS, mat_brillo2);

		// Cubo Azul
		gl.glPushMatrix();
			gl.glTranslatef(1.7f, 0, 0);
			cubo.dibuja(gl);
		gl.glPopMatrix();
		
		/********************* CODIGO LABO 5********************/
		// Arboles
		/* Definición del color del material - Valores por defecto */
		float mat_ambiente3 []  = {0f, 0f, 0f, 1}; // K
		float mat_difuso3 [] = {0f, 0.55f, 0.55f, 1};
		float mat_especular3 [] = {0f, 0.55f, 0.55f, 1};
		float mat_brillo3 = 255f;
		
		gl.glMaterialfv(GL10.GL_FRONT_AND_BACK, GL10.GL_AMBIENT, mat_ambiente3, 0);
		gl.glMaterialfv(GL10.GL_FRONT_AND_BACK, GL10.GL_DIFFUSE, mat_difuso3, 0);
		gl.glMaterialfv(GL10.GL_FRONT_AND_BACK, GL10.GL_SPECULAR, mat_especular3, 0);
		gl.glMaterialf(GL10.GL_FRONT_AND_BACK, GL10.GL_SHININESS, mat_brillo3);
		for (int z = -4; z >= -10; z = z - 2) {
			for (int x = -6; x <= 6; x = x + 2) {
				gl.glPushMatrix();
				gl.glTranslatef(x, 0, z); // traslada
				gl.glScalef(0.5f, 0.5f, 0.5f); // tamaño
				trianRectangulo.dibuja(gl);
				gl.glPushMatrix();
				gl.glTranslatef(0, 2, 0);
				esfera.dibuja(gl);
				gl.glPopMatrix();
				gl.glPopMatrix();
			}
		}
		int v = -6;
		for (int z = -2; z <= 4; z = z + 2) {
			gl.glPushMatrix();
			gl.glTranslatef(v, 0, z); // traslada
			gl.glScalef(0.5f, 0.5f, 0.5f); // tamaño
			trianRectangulo.dibuja(gl);
			gl.glPushMatrix();
			gl.glTranslatef(0, 2, 0);
			esfera.dibuja(gl);
			gl.glPopMatrix();
			gl.glPopMatrix();
			if (z == 4 && v == -6) {
				v = 6;
				z = -4;
			}
		}
		// suelo
		float mat_ambiente4 []  = {0f, 0.1f, 0.55f, 1}; // K
		float mat_difuso4 []    = {0f, 0.1f, 0.55f, 1};
		float mat_especular4 [] = {0f, 0.1f, 0.55f, 1};
		float mat_brillo4 = 255f;
		
		gl.glMaterialfv(GL10.GL_FRONT_AND_BACK, GL10.GL_AMBIENT, mat_ambiente4, 0);
		gl.glMaterialfv(GL10.GL_FRONT_AND_BACK, GL10.GL_DIFFUSE, mat_difuso4, 0);
		gl.glMaterialfv(GL10.GL_FRONT_AND_BACK, GL10.GL_SPECULAR, mat_especular4, 0);
		gl.glMaterialf(GL10.GL_FRONT_AND_BACK, GL10.GL_SHININESS, mat_brillo4);
		
		gl.glPushMatrix();
		gl.glTranslatef(0, -0.5f, 1); // traslada
		gl.glScalef(0.5f, 0.5f, 0.5f); // tamaño
		suelo.dibuja(gl);
		gl.glPopMatrix();
		
		

		// bloques
		/* Definición del color del material - Valores por defecto */
		float mat_ambiente5 []  = {0f, 0f, 0, 1}; // K
		float mat_difuso5 []    = {0.15f, 0f, 0.85f, 1};
		float mat_especular5 [] = {0.15f, 0f, 0.85f, 1};
		float mat_brillo5 = 255f;
		
		gl.glMaterialfv(GL10.GL_FRONT_AND_BACK, GL10.GL_AMBIENT, mat_ambiente5, 0);
		gl.glMaterialfv(GL10.GL_FRONT_AND_BACK, GL10.GL_DIFFUSE, mat_difuso5, 0);
		gl.glMaterialfv(GL10.GL_FRONT_AND_BACK, GL10.GL_SPECULAR, mat_especular5, 0);
		gl.glMaterialf(GL10.GL_FRONT_AND_BACK, GL10.GL_SHININESS, mat_brillo5);
		
		for (int z = -1; z <= 3; z = z + 4) {
			for (int x = -4; x <= 4; x = x + 8) {
				gl.glPushMatrix();
				gl.glTranslatef(x, 1.2f, z); // traslada
				gl.glScalef(0.5f, 0.5f, 0.5f); // tamaño
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
		/* Definición del color del material - Valores por defecto */
		float mat_ambiente6 []  = {0f, 0f,    0f, 1}; // K
		float mat_difuso6 []    = {0.85f, 0.15f, 0.05f, 1};
		float mat_especular6 [] = {0.85f, 0.15f, 0.05f, 1};
		float mat_brillo6 = 255f;
		
		gl.glMaterialfv(GL10.GL_FRONT_AND_BACK, GL10.GL_AMBIENT, mat_ambiente6, 0);
		gl.glMaterialfv(GL10.GL_FRONT_AND_BACK, GL10.GL_DIFFUSE, mat_difuso6, 0);
		gl.glMaterialfv(GL10.GL_FRONT_AND_BACK, GL10.GL_SPECULAR, mat_especular6, 0);
		gl.glMaterialf(GL10.GL_FRONT_AND_BACK, GL10.GL_SHININESS, mat_brillo6);
		
		for (int z = -2; z <= 6; z = z + 8) {
			gl.glPushMatrix();
			gl.glScalef(0.5f, 0.5f, 0.5f); // tamaño
			gl.glTranslatef(0, 1.5f, z);
			muro.dibuja(gl);
			gl.glPopMatrix();
		}
		for (int x = -8; x <= 8; x = x + 16) {
			gl.glPushMatrix();
			gl.glRotatef(90, 0, 1, 0); // rotamos
			gl.glScalef(0.2f, 0.5f, 0.5f); // tamaño
			gl.glTranslatef(-4, 1.5f, x);
			muro.dibuja(gl);
			gl.glPopMatrix();
		}

		/* ROMBO */
		/* Definición del color del material - Valores por defecto */
		float mat_ambiente7 []  = {0f, 0f,    0f, 1}; // K
		float mat_difuso7 []    = {0.15f, 0.5f, 0.85f, 1};
		float mat_especular7 [] = {0.15f, 0.5f, 0.85f, 1};
		float mat_brillo7 = 255f;
		
		gl.glMaterialfv(GL10.GL_FRONT_AND_BACK, GL10.GL_AMBIENT, mat_ambiente7, 0);
		gl.glMaterialfv(GL10.GL_FRONT_AND_BACK, GL10.GL_DIFFUSE, mat_difuso7, 0);
		gl.glMaterialfv(GL10.GL_FRONT_AND_BACK, GL10.GL_SPECULAR, mat_especular7, 0);
		gl.glMaterialf(GL10.GL_FRONT_AND_BACK, GL10.GL_SHININESS, mat_brillo7);
		
		gl.glPushMatrix();
		gl.glScalef(1f, 1f, 1f); // tamaño
		gl.glTranslatef(-6, 8, 3);
		piramide1.dibuja(gl);
		gl.glPushMatrix();
		gl.glRotatef(180, 0, 0, 1); // rotamos
		gl.glTranslatef(0, 2, 0);
		piramide1.dibuja(gl);
		gl.glPopMatrix();
		gl.glPopMatrix();

		// rombo 2
		/* Definición del color del material */
		float mat_ambiente8 []  = {0.0f, 0f , 0f, 1}; // K
		float mat_difuso8 []    = {0.5f, 0.15f, 0.15f, 1};
		float mat_especular8 [] = {0.5f, 0.15f, 0.15f, 1};
		float mat_brillo8       = 255f;
		
		gl.glMaterialfv(GL10.GL_FRONT_AND_BACK, GL10.GL_AMBIENT, mat_ambiente8, 0);
		gl.glMaterialfv(GL10.GL_FRONT_AND_BACK, GL10.GL_DIFFUSE, mat_difuso8, 0);
		gl.glMaterialfv(GL10.GL_FRONT_AND_BACK, GL10.GL_SPECULAR, mat_especular8, 0);
		gl.glMaterialf(GL10.GL_FRONT_AND_BACK, GL10.GL_SHININESS, mat_brillo8);
		gl.glPushMatrix();
		gl.glTranslatef(-10, 8, 3);
		esfera1.dibuja(gl);
		gl.glPopMatrix();

		// esfera
		/* Definición del color del material */
		float mat_ambiente9 [] = {0.0f, 0f , 0.0f, 1}; // K
		float mat_difuso9 [] = {0.35f, 0.35f, 0.15f, 1};
		float mat_especular9 [] = {0.35f, 0.35f, 0.15f, 1};
		float mat_brillo9 = 255f;
		
		gl.glMaterialfv(GL10.GL_FRONT_AND_BACK, GL10.GL_AMBIENT, mat_ambiente9, 0);
		gl.glMaterialfv(GL10.GL_FRONT_AND_BACK, GL10.GL_DIFFUSE, mat_difuso9, 0);
		gl.glMaterialfv(GL10.GL_FRONT_AND_BACK, GL10.GL_SPECULAR, mat_especular9, 0);
		gl.glMaterialf(GL10.GL_FRONT_AND_BACK, GL10.GL_SHININESS, mat_brillo9);
		gl.glPushMatrix();
		gl.glTranslatef(-14, 8, 3);
		esfera2.dibuja(gl);
		gl.glPopMatrix();

		// trianRectangulo
		/* Definición del color del material */
		float mat_ambiente10 [] = {0.0f, 0f , 0.0f, 1}; // K
		float mat_difuso10 [] = {0.35f, 0.55f, 0.35f, 1};
		float mat_especular10 [] = {0.35f, 0.55f, 0.35f, 1};
		float mat_brillo10 = 255f;
		
		gl.glMaterialfv(GL10.GL_FRONT_AND_BACK, GL10.GL_AMBIENT, mat_ambiente10, 0);
		gl.glMaterialfv(GL10.GL_FRONT_AND_BACK, GL10.GL_DIFFUSE, mat_difuso10, 0);
		gl.glMaterialfv(GL10.GL_FRONT_AND_BACK, GL10.GL_SPECULAR, mat_especular10, 0);
		gl.glMaterialf(GL10.GL_FRONT_AND_BACK, GL10.GL_SHININESS, mat_brillo10);
		gl.glPushMatrix();
		gl.glTranslatef(-15, 4, 3);
		gl.glRotatef(-75, 0, 1, 0);
		gl.glRotatef(-20, 1, 0, 0);
		trianRectangulo1.dibuja(gl);
		gl.glPopMatrix();

		// esferaMitad
		/* Definición del color del material */
		float mat_ambiente11 [] = {0.0f, 0f , 0.0f, 1}; // K
		float mat_difuso11 [] = {0.25f, 0.45f, 0.05f, 1};
		float mat_especular11 [] = {0.25f, 0.45f, 0.05f, 1};
		float mat_brillo11 = 255f;
		
		gl.glMaterialfv(GL10.GL_FRONT_AND_BACK, GL10.GL_AMBIENT, mat_ambiente11, 0);
		gl.glMaterialfv(GL10.GL_FRONT_AND_BACK, GL10.GL_DIFFUSE, mat_difuso11, 0);
		gl.glMaterialfv(GL10.GL_FRONT_AND_BACK, GL10.GL_SPECULAR, mat_especular11, 0);
		gl.glMaterialf(GL10.GL_FRONT_AND_BACK, GL10.GL_SHININESS, mat_brillo11);
		gl.glPushMatrix();
		gl.glTranslatef(-11, 3, 3);
		gl.glRotatef(90, 0, 0, 1);
		esferaMitad.dibuja(gl);
		gl.glPopMatrix();

		// cilindro
		/* Definición del color del material */
		float mat_ambiente12 [] = {0.0f, 0f , 0.0f, 1}; // K
		float mat_difuso12 [] = {0.45f, 0.75f, 0.35f, 1};
		float mat_especular12 [] = {0.45f, 0.75f, 0.35f, 1};
		float mat_brillo12 = 255f;
		
		gl.glMaterialfv(GL10.GL_FRONT_AND_BACK, GL10.GL_AMBIENT, mat_ambiente12, 0);
		gl.glMaterialfv(GL10.GL_FRONT_AND_BACK, GL10.GL_DIFFUSE, mat_difuso12, 0);
		gl.glMaterialfv(GL10.GL_FRONT_AND_BACK, GL10.GL_SPECULAR, mat_especular12, 0);
		gl.glMaterialf(GL10.GL_FRONT_AND_BACK, GL10.GL_SHININESS, mat_brillo12);
		gl.glPushMatrix();
		gl.glTranslatef(-8, 2, 4);
		cilindro.dibuja(gl);
		gl.glPopMatrix();
		
		// elipsoide
		/* Definición del color del material */
		float mat_ambiente13 [] = {0.0f, 0f , 0.0f, 1}; // K
		float mat_difuso13 [] = {0.65f, 0.25f, 0.15f, 1};
		float mat_especular13 [] = {0.65f, 0.25f, 0.15f, 1};
		float mat_brillo13 = 255f;
		
		gl.glMaterialfv(GL10.GL_FRONT_AND_BACK, GL10.GL_AMBIENT, mat_ambiente13, 0);
		gl.glMaterialfv(GL10.GL_FRONT_AND_BACK, GL10.GL_DIFFUSE, mat_difuso13, 0);
		gl.glMaterialfv(GL10.GL_FRONT_AND_BACK, GL10.GL_SPECULAR, mat_especular13, 0);
		gl.glMaterialf(GL10.GL_FRONT_AND_BACK, GL10.GL_SHININESS, mat_brillo13);
		gl.glPushMatrix();
		gl.glTranslatef(-16, 1, 4);
		elipsoide.dibuja(gl);
		gl.glPopMatrix();
		/********************** FIN CODIGO LABO 5 ******************/
		
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
		gl.glViewport(0, 0, w, h);
		/* Matriz de Proyección */
		gl.glMatrixMode(GL10.GL_PROJECTION);
	 
		/* Inicializa la Matriz de Proyección */
		gl.glLoadIdentity();
	 
		/* Proyección paralela */
		if (w <= h){
			gl.glOrthof(-2, 2, -2 * (float) h / (float) w, 2 * (float) h / (float) w, 3, 50);
		}else{
			gl.glOrthof(-2 * (float) w / (float) h, 2 * (float) w / (float) h, -2, 2, 3, 50);
		}
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
				
			}else if (puntoEstaDentroDelRectangulo(posx, posy, -3.5f, -4.75f, 1, 1)) { // 1
				Matrix.setIdentityM(matriz, 0);
				Matrix.rotateM(matriz, 0, rotY, 0, 1, 0);
				Matrix.multiplyMV(direccion, 0, matriz, 0, vectorEntrada, 0);
				
				posicion[0] = 3;
				posicion[1] = 4;
				posicion[2] = 0;
				rotX = 15;
				rotY = 100;
				
			} else if (puntoEstaDentroDelRectangulo(posx, posy, 2.5f, -4.75f, 1, 1)) { // 2
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
