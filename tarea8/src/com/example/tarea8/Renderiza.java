package com.example.tarea8;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;
import android.content.Context;
import android.opengl.GLSurfaceView;
import android.opengl.GLSurfaceView.Renderer;
import android.opengl.GLU;
import android.view.MotionEvent;

public class Renderiza extends GLSurfaceView implements Renderer {
	private float angulo = 65;		//	Se almacena el desplazamiento del triángulo
	private float angulo1 = -65;	// cuadrado
	private boolean animacion = true;
	private boolean animacion1 = false;
	/* Objeto */
	//private Triangulo triangulo;	//	Incremento del ángulo de la animación
	//private Cuadrado cuadrado;
	private Circulo cabeza;
	private Cuadrado brazoIzq;
	private Cuadrado brazoDer;
	private Cuadrado manoIzq;
	private Cuadrado manoDer;
	private Triangulo cuerpo;
	private Cuadrado pieIzq;
	private Cuadrado pieDer;
	public Renderiza(Context contexto) {
		super(contexto);
		this.setRenderer(this);	//	inicia el renderizado
		this.requestFocus();	// La ventana solicita recibir una entrada
		this.setFocusableInTouchMode(true);	//	Establece que la ventana pueda detectar el modo táctil .
		this.setRenderMode(GLSurfaceView.RENDERMODE_CONTINUOUSLY);	// El renderizado se llama continuamente para renderizar la escena .
	}

	// @Override
	public void onSurfaceCreated(GL10 gl, EGLConfig arg1) {
		// triangulo = new Triangulo(1f);
		// cuadrado = new Cuadrado(0.5f, 1.5f);
		cabeza = new Circulo(1.1f, 360, true);
		brazoIzq = new Cuadrado(0.3f, 1f);
		brazoDer = new Cuadrado(0.3f, 1f);
		manoIzq = new Cuadrado(0.35f, 0.35f);
		manoDer = new Cuadrado(0.35f, 0.35f);
		cuerpo = new Triangulo(2f);
		pieIzq = new Cuadrado(0.4f, 1.3f);
		pieDer = new Cuadrado(0.4f, 1.3f);
		gl.glClearColor(0, 0, 0, 0);	// Color de fondo
	}

	// @Override
	public void onDrawFrame(GL10 gl) {
		gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);	// Inicializa el buffer de color y de profundidad 
		gl.glMatrixMode(GL10.GL_MODELVIEW);	// Inicializa la Matriz del Modelo - Vista
		
		
		gl.glLoadIdentity();
		gl.glTranslatef(0, 3.1f, 0);
		gl.glRotatef(0, 0, 0, 1);
		gl.glColor4f(0, 1, 1, 0);
		cabeza.dibuja(gl);
		
		gl.glLoadIdentity();
		gl.glTranslatef(0, 0, 0);
		gl.glRotatef(0, 0, 0, 1);
		gl.glColor4f(0, 1, 0, 0);
		cuerpo.dibuja(gl);
	
		/*
		gl.glLoadIdentity();
		gl.glTranslatef(-1.7f,1f , 0);
		gl.glRotatef(65, 0, 0, 1);
		gl.glColor4f(0, 0, 1, 0);
		brazoIzq.dibuja(gl);
		
		gl.glLoadIdentity();
		gl.glTranslatef(-3.1f,1.65f , 0);
		gl.glRotatef(25, 0, 0, 1);
		gl.glColor4f(1, 0, 1, 0);
		manoIzq.dibuja(gl);
		*/
		// *****
		gl.glPushMatrix();
		gl.glTranslatef(-1.7f,1f , 0);
		gl.glRotatef(angulo, 0, 0, 1);
		gl.glColor4f(0, 0, 1, 0);
		brazoIzq.dibuja(gl);
			// ---- OBJETO 1
			gl.glPushMatrix();			// guardamos la matriz identidad
			gl.glTranslatef(0.05f,1.5f , 0);
			gl.glRotatef(25, 0, 0, 1);
			gl.glColor4f(1, 0, 1, 0);
			manoIzq.dibuja(gl);
			gl.glPopMatrix();
		gl.glPopMatrix();
		
		if(angulo > 80){
			animacion = false;
		}
		if(animacion){
			angulo = angulo + 1;
		}else{
			angulo = angulo - 1;
		}
		if(angulo < 50){
			animacion = true;
		}
		// *****
		/*
		gl.glLoadIdentity();
		gl.glTranslatef(1.7f,1f , 0);
		gl.glRotatef(-65, 0, 0, 1);
		gl.glColor4f(0, 0, 1, 0);
		brazoDer.dibuja(gl);
		
		gl.glLoadIdentity();
		gl.glTranslatef(3.1f,1.65f , 0);
		gl.glRotatef(-25, 0, 0, 1);
		gl.glColor4f(1, 0, 1, 0);
		manoDer.dibuja(gl);
		*/
		// *****
		gl.glPushMatrix();
		gl.glTranslatef(1.7f,1f , 0);
		gl.glRotatef(angulo1, 0, 0, 1);
		gl.glColor4f(0, 0, 1, 0);
		brazoDer.dibuja(gl);
			// ---- OBJETO 1
			gl.glPushMatrix();			// guardamos la matriz identidad
			gl.glTranslatef(0.05f,1.5f , 0);
			gl.glRotatef(25, 0, 0, 1);
			gl.glColor4f(1, 0, 1, 0);
			manoDer.dibuja(gl);
			gl.glPopMatrix();
		gl.glPopMatrix();
		
		if(angulo1 > -50){
			animacion1 = false;
		}
		if(animacion1){
			angulo1 = angulo1 + 1;
		}else{
			angulo1 = angulo1 - 1;
		}
		if(angulo1 < -80){
			animacion1 = true;
		}
		// *****
		gl.glLoadIdentity();
		gl.glTranslatef(-0.95f,-3.35f , 0);
		gl.glRotatef(180, 0, 0, 1);
		gl.glColor4f(0, 0, 1, 0);
		pieIzq.dibuja(gl);
		
		gl.glLoadIdentity();
		gl.glTranslatef(0.95f,-3.35f , 0);
		gl.glRotatef(180, 0, 0, 1);
		gl.glColor4f(0, 0, 1, 0);
		pieDer.dibuja(gl);
		
		/* 
		// ---- OBJETO 1
		// gl.glLoadIdentity();
		gl.glPushMatrix();			// guardamos la matriz identidad
		gl.glTranslatef(0, 1, 0);	// t=1
		//gl.glRotatef(angulo, 0, 0, 1);
		triangulo.dibuja(gl);
		gl.glPopMatrix();
		
		// ---- OBJETO 2
		//gl.glLoadIdentity();
		gl.glPushMatrix();
		gl.glTranslatef(0, -1, 0);	// T=T*T(tx,ty)
		//gl.glRotatef(angulo1, 0, 0, 1);
		rectangulo.dibuja(gl);
		gl.glPopMatrix();
		
		angulo = angulo + 5;
		angulo1 = angulo1 - 5;
		*/		
		/* GIRO DE DOS OBJETOS ALREDEDOR DE EL CUADRADO*/
		// ---- OBJETO 2
		
		
		/*
		gl.glPushMatrix();
		gl.glTranslatef(0, -1, 0);	// T=T*T(tx,ty)
		gl.glRotatef(angulo, 0, 0, 1);
		gl.glColor4f(0, 1, 0, 1);
		cuadrado.dibuja(gl);
			// ---- OBJETO 1
			gl.glPushMatrix();			// guardamos la matriz identidad
			gl.glTranslatef(0, 1, 0);
			gl.glTranslatef(0, 0.5f, 0);	
			triangulo.dibuja(gl);
			gl.glPopMatrix();
		gl.glPopMatrix();
				
		angulo = angulo + 5;
		*/
	}

	// @Override
	public void onSurfaceChanged(GL10 gl, int w, int h) {
		gl.glViewport(0, 0, w, h);	// Ventana de despliegue
		gl.glMatrixMode(GL10.GL_PROJECTION);	// Matriz de Proyección
		gl.glLoadIdentity();	// Inicializa la Matriz de Proyección
		GLU.gluOrtho2D(gl, -4, 4, -6, 6);	// Proyección paralela
		gl.glMatrixMode(GL10.GL_MODELVIEW);	// Matriz del Modelo - Vista
		gl.glLoadIdentity();	// Inicializa la Matriz del Modelo - Vista
	}

	/*Maneja los eventos de la pantalla táctil. */
	@Override
	public boolean onTouchEvent(MotionEvent e) {
		/* Se considera cuando el dedo toca la pantalla. */
		if (e.getAction() == MotionEvent.ACTION_DOWN) {
			animacion = !animacion;
		}
		return true;
	}
}