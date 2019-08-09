package com.opengl10_triangulo;

import java.util.Random;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.opengl.GLSurfaceView.Renderer;
import android.opengl.GLU;
import android.view.MotionEvent;

/**
 * Clase Renderiza (OpenGL 1.x)
 * 
 * @author Jhonny Felipez
 * @version 1.0 01/01/2015
 *
 */
public class Renderiza extends GLSurfaceView implements Renderer {

	/* Objeto */
	private Carretera carretera;
	private Lineas lineas;
	private rectangulografico rectangulografico;
	private float despLineasY;
	private float velocidadCoche1;
	private float despCoche2x;
	private float despCoche1x;
	private float despCoche2y;
	private Rectangulo rectangulocoche1;
	private Rectangulo rectangulocoche2;
	
	
	Random ran  = new Random();
	
	public Renderiza(Context contexto) {
		super(contexto);
		this.setRenderer(this);
		this.requestFocus();
		this.setFocusableInTouchMode(true);
		this.setRenderMode(GLSurfaceView.RENDERMODE_CONTINUOUSLY);
	}
	
	@Override
	public void onSurfaceCreated(GL10 gl, EGLConfig arg1) {
	 
		carretera = new Carretera();
		lineas = new Lineas();
		rectangulografico =  new rectangulografico();
		
		rectangulocoche1 =  new Rectangulo(112, 50, 30, 30);
		
		despCoche1x = 0;
		despLineasY = 0;
		velocidadCoche1 = 10;
		
		
		
		if (ran.nextInt(2) == 0) {
			despCoche2x = 0;
		}
		else {
			despCoche2x = 64;
		}
		
		
		despCoche2y = 840;
		
		rectangulocoche2 =  new Rectangulo(112+despCoche2x, 50+despCoche2y, 30, 30);
		/* Color de fondo */
		gl.glClearColor(0.24f, 0.874f, 0.11f, 0);
		
	}
	public void dibujaCarretera(GL10 gl){
		gl.glLoadIdentity(); 
		carretera.dibuja(gl);
		
	}
	
	public void dibujaLineas(GL10 gl){
		gl.glLoadIdentity();
		gl.glTranslatef(0, despLineasY, 0);
		lineas.dibuja(gl);
		
	}
	
	public void dibujaCoche1(GL10 gl){
		gl.glLoadIdentity(); 
		gl.glColor4f(1, 1, 0, 0);
		rectangulocoche1.x = 112+despCoche1x;
		gl.glTranslatef(despCoche1x, 0, 0);
		rectangulografico.dibuja(gl);
	}
	
	public void dibujaCoche2(GL10 gl){
		gl.glLoadIdentity(); 
		gl.glColor4f(0, 1, 1, 0);

		rectangulocoche2.x = 112+despCoche2x;
		rectangulocoche2.y = 50+despCoche2y;
		
		gl.glTranslatef(despCoche2x, despCoche2y, 0);
		rectangulografico.dibuja(gl);
	}
	
	@Override
	public void onDrawFrame(GL10 gl) {
		gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		dibujaCarretera(gl);
		dibujaLineas(gl);
		dibujaCoche1(gl);
		dibujaCoche2(gl);
		if (!seSobrePonen(rectangulocoche1, rectangulocoche2)) {

			despLineasY = despLineasY - velocidadCoche1;
			despCoche2y =  despCoche2y - 15;
			if (despLineasY < -60)
				despLineasY = 0;
			if (despCoche2y < -60){
				
				despCoche2y = 840;
				
				if (ran.nextInt(2) == 0) {
					despCoche2x = 0;
				}		
				else {
					despCoche2x = 64;
				}
			}
		}
		
			
	}
	
	@Override
	public void onSurfaceChanged(GL10 gl, int width, int height) {
	 
		/* Ventana de despliegue */
		gl.glViewport(0, 0, width, height);
	 
		/* Matriz de Proyección */
		gl.glMatrixMode(GL10.GL_PROJECTION);
	 
		/* Inicializa la Matriz de Proyección */
		gl.glLoadIdentity();
	 
		/* Proyección paralela */
		GLU.gluOrtho2D(gl, 0, 320, 0, 480);
	 
		/* Matriz del Modelo-Vista */
		gl.glMatrixMode(GL10.GL_MODELVIEW);
	 
		/* Inicializa la Matriz del Modelo-Vista */
		gl.glLoadIdentity();
	}
	
	public boolean onTouchEvent (MotionEvent e){
		if (e.getAction() == MotionEvent.ACTION_UP) {
			if (despCoche1x == 0) {
				despCoche1x = 64;
			}
			else
				despCoche1x = 0;
			requestRender();
		}
		return true;
	}
	
	public boolean seSobrePonen (Rectangulo r1, Rectangulo r2){
		return (r1.x < r2.x + r2.ancho && r2.x < r1.x + r1.ancho &&  
				r1.y < r2.y + r2.alto && r2.y < r1.y + r1.alto );
	}
}
