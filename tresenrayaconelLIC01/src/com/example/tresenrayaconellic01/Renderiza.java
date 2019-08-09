package com.example.tresenrayaconellic01;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.opengl.GLSurfaceView.Renderer;
import android.opengl.GLU;
import android.view.MotionEvent;
import android.widget.Toast;

public class Renderiza extends GLSurfaceView  implements Renderer {
	private Cruz cruz;
	private Circulo circulo;
	private ray rr;
	int ancho;
	int alto;
	Context context;
	boolean sw =  true;
	
	public Renderiza(Context context) {
		super(context);
		this.context =  context;
		this.setRenderer(this);
		this.requestFocus();
		this.setFocusableInTouchMode(true);
		this.setRenderMode(GLSurfaceView.RENDERMODE_WHEN_DIRTY);
	}
	
	public void onSurfaceCreated(GL10 gl, EGLConfig arg1) {
		gl.glClearColor(0, 1, 1, 0);//colores
		circulo =  new Circulo(1, 360, false);
		cruz = new Cruz();
		rr = new ray();
		
		
	}
	public void onDrawFrame(GL10 gl) {
		gl.glLineWidth(3);
		gl.glEnable(GL10.GL_POINT_SMOOTH);
		rr.dibuja(gl);
		/* Inicializa el buffer de color */
		gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		
		if (sw) {
			cruz.dibuja(gl);
		}
		else{
			circulo.dibuja(gl);
		}
		
	}

	/*@Override
	public void onSurfaceChanged(GL10 gl, int w, int h) {
		//vamos a utilizar toda nuestra area
		/* Ventana de despliegue 
		gl.glViewport(0, 0, w, h);
		/* Matriz de Proyección 
		gl.glMatrixMode(GL10.GL_PROJECTION);
		/* Inicializa la Matriz de Proyección 
		gl.glLoadIdentity();
		/* Proyección paralela 
		GLU.gluOrtho2D(gl, -4, 4, -6, 6);
		/* Matriz del Modelo-Vista 
		gl.glMatrixMode(GL10.GL_MODELVIEW);
		/* Inicializa la Matriz del Modelo-Vista 
		gl.glLoadIdentity();
		
		
	}
*/
	public void onSurfaceChanged(GL10 gl, int w, int h) {
		ancho = w;
		alto = h;
		gl.glViewport(0, 0, ancho, alto);
		gl.glMatrixMode(GL10.GL_PROJECTION);
		gl.glLoadIdentity();
		GLU.gluOrtho2D(gl, -4, 4, -6, 6);
		gl.glMatrixMode(GL10.GL_MODELVIEW);
		gl.glLoadIdentity();
	}

	@Override
	public boolean onTouchEvent(MotionEvent e){
		float posx = e.getX();
		float posy = e.getY();
		
		
		
		if (e.getAction() == MotionEvent.ACTION_UP) {		// UP es cuado el dedo se lñevanta de la pantalla  DOWN cuado el dedo se pone en la pantalla 
			
			//Toast.makeText(context.getApplicationContext(), "holaaa", Toast.LENGTH_SHORT).show();
			posx = ((posx / (float) ancho) * 8) - 4;
			posy = ((1 - posy / (float) alto) * 12) - 6;
			
			if (puntoEstaDentroDelRectangulo(posx, posy, -1, -1, 2, 2)) {
				Toast.makeText(context.getApplicationContext(), "DENTRO", Toast.LENGTH_SHORT).show();
				sw=!sw;
				requestRender();
				
			}
			
		}
		
		
		return true;
	}
	
	
	public boolean puntoEstaDentroDelRectangulo(float posx, float posy, int x, int y, int ancho, int alto){
		return x < posx  &&  posx < x+ancho && y<posy  &&  posy < y+alto; 
	}
	
	

}
