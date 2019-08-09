package com.opengl10_triangulo;

import java.util.Random;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.opengl.GLSurfaceView.Renderer;
import android.opengl.GLU;

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
	private RectanguloGrafico rectanguloGrafico;
	private Rectangulo rectangulocoche1;
	private Rectangulo rectangulocoche2;
	private float desplLineasY;
	private float velocidadCoche1;
	private float desplcoche2X;
	private float desplcoche2Y;
	
	Random rand =new Random();
	
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
		rectanguloGrafico=new RectanguloGrafico();
		
		rectangulocoche1 = new Rectangulo(112,50,30,30);
		//rectangulocoche2 = new Rectangulo(112,50,30,30);
		
		
		desplLineasY=0; //velocidad
		velocidadCoche1= -10;
		
		if(rand.nextInt(2)==0)//0 o1
			desplcoche2X=0;
		else
			desplcoche2X=64;
		
		desplcoche2Y=840;
		rectangulocoche2 = new Rectangulo(112+desplcoche2X, 50+desplcoche2Y, 30, 30);
		/* Color de fondo */
		
		gl.glClearColor(0, 1, 1, 0);
		
		//ty_lineas = 0;
		
	}
	public void dibujaCarretera(GL10 gl)
	{
		gl.glLoadIdentity(); 
		carretera.dibuja(gl);
	}
	public void dibujalineas(GL10 gl)
	{
		gl.glLoadIdentity(); 
		gl.glTranslatef(0, desplLineasY, 0);
		lineas.dibuja(gl);
	}
	public void dibujacoche1(GL10 gl)
	{
		gl.glLoadIdentity();
		gl.glColor4f(0,1,1, 0);
		rectanguloGrafico.dibuja(gl);
	}
	
	public void dibujacoche2(GL10 gl)
	{	
		gl.glLoadIdentity();
		gl.glColor4f(1,0,0, 0);
		rectangulocoche2.x=112+desplcoche2X;
		rectangulocoche2.y=50+desplcoche2Y;
		gl.glTranslatef(desplcoche2X,desplcoche2Y,0);
		rectanguloGrafico.dibuja(gl);
	}
	
	@Override
	public void onDrawFrame(GL10 gl) {
		gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		dibujaCarretera(gl);
		dibujalineas(gl);
		dibujacoche1(gl);
		dibujacoche2(gl);
		
		if(!seSobrepone(rectangulocoche1,rectangulocoche2))
		desplLineasY=desplLineasY + velocidadCoche1;
		desplcoche2Y = desplcoche2Y-8;
		if (desplLineasY< -60)
			desplLineasY = 0;
		if(desplcoche2Y < -60){
			desplcoche2Y =840;
			if(rand.nextInt(2)==0)
				desplcoche2X=0;
			else
				desplcoche2X=64;
		}
	}
	
	@Override
	public void onSurfaceChanged(GL10 gl, int width, int height) {
	 
		gl.glViewport(0, 0, width, height);
		gl.glMatrixMode(GL10.GL_PROJECTION);
		gl.glLoadIdentity();
		GLU.gluOrtho2D(gl, 0, 320, 0, 480);
		gl.glMatrixMode(GL10.GL_MODELVIEW);
	 	gl.glLoadIdentity();
	}
	
	public boolean seSobrepone(Rectangulo r1, Rectangulo r2)
	{
		return(r1.x< r2.x+r2.ancho &&  r2.x < r1.x + r1.ancho &&
			r1.y< r2.y+r2.alto &&  r2.y < r1.y + r1.alto);
	}
}
