package com.example.tresenrayaconellic02;

import java.util.Arrays;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import android.R.array;
import android.content.Context;
import android.opengl.GLSurfaceView;
import android.opengl.GLSurfaceView.Renderer;
import android.opengl.GLU;
import android.view.MotionEvent;
import android.widget.Toast;

public class Renderiza extends GLSurfaceView implements Renderer {


	int [][]m = new int[3][3];
	int fin = 0;
	int ancho;
	int alto;
	//private Triangulo triangulo;
	private cruz cruz;
	private ray ray;
	private Circulo circulo;
	Context context;
	boolean sw=false;
	//                    0
	// sw false cruz      2		// cpu
	// sw true circulos   1   	// humano


	public Renderiza(Context context) {
		super(context);
		this.context = context;
		this.setRenderer(this);

		// estas 3 lineas detecta los elementos de la pantalla
		this.requestFocus();
		this.setFocusableInTouchMode(true);
		this.setRenderMode(GLSurfaceView.RENDERMODE_WHEN_DIRTY); // modo de
		// renderizado

	}

	@Override
	public void onSurfaceCreated(GL10 gl, EGLConfig arg1) {
		// TODO Auto-generated method stub
		circulo = new Circulo(0.8f, 360, false);
		cruz = new cruz();
		ray = new ray();
		gl.glClearColor(235f / 255, 124f / 255, 20f / 255, 0); // color de fondo
	}

	@Override
	public void onSurfaceChanged(GL10 gl, int w, int h) {

		ancho = w;
		alto = h;
		// TODO Auto-generated method stub
		gl.glViewport(0, 0, w, h); // utiliza toda nuestra area
		gl.glMatrixMode(GL10.GL_PROJECTION); // realiza una proyeccion
		gl.glLoadIdentity();
		GLU.gluOrtho2D(gl, -4, 4, -6, 6); // definimos el rango o area
		gl.glMatrixMode(GL10.GL_MODELVIEW);
		gl.glLoadIdentity();
	}

	@Override
	public boolean onTouchEvent(MotionEvent e) {
		float posx = e.getX();
		float posy = e.getY();
		if (e.getAction() == MotionEvent.ACTION_UP) {
			posx = ((posx / (float) ancho) * 8) - 4;
			posy = ((1 - posy / (float) alto) * 12) - 6;
				if (ptoStaDentroCuadra(posx, posy, -3, 1, 2, 2)) {
					if (m[0][0]==0) {		
						m[0][0]=1;	
						Toast.makeText(context.getApplicationContext(), "1 - UNO", Toast.LENGTH_SHORT).show();
						//requestRender();
					}



				}
				//1
				if (ptoStaDentroCuadra(posx, posy, -1, 1, 2, 2)) {
					if (m[0][1]==0) {
						m[0][1]=1;
						Toast.makeText(context.getApplicationContext(), "2 - DOS", Toast.LENGTH_SHORT).show();
						//requestRender();
					}


				}
				//2
				if (ptoStaDentroCuadra(posx, posy, 1, 1, 2, 2)) {
					if (m[0][2]==0) {
						m[0][2]=1;
						Toast.makeText(context.getApplicationContext(), "3 - TRES", Toast.LENGTH_SHORT).show();
						//requestRender();
					}


				}
				//3
				if (ptoStaDentroCuadra(posx, posy, -3, -1, 2, 2)) {
					if (m[1][0]==0) {
						m[1][0]=1;
						Toast.makeText(context.getApplicationContext(), "4 - CUATRO", Toast.LENGTH_SHORT).show();
						//requestRender();
					}


				}
				if (ptoStaDentroCuadra(posx, posy, -1, -1, 2, 2)) {
					if (m[1][1]==0) {
						m[1][1]=1;
						Toast.makeText(context.getApplicationContext(), "5 - CINCO", Toast.LENGTH_SHORT).show();
						//requestRender();
					}


				}

				//5
				if (ptoStaDentroCuadra(posx, posy, 1, -1, 2, 2)) {
					if (m[1][2]==0) {
						m[1][2]=1;
						Toast.makeText(context.getApplicationContext(), "6 - SEIS", Toast.LENGTH_SHORT).show();
						//requestRender();
					}


				}
				//6
				if (ptoStaDentroCuadra(posx, posy, -3, -3, 2, 2)) {
					if (m[2][0]==0) {
						m[2][0]=1;
						Toast.makeText(context.getApplicationContext(), "7 - SIETE", Toast.LENGTH_SHORT).show();
						//requestRender();
					}


				}
				//7
				if (ptoStaDentroCuadra(posx, posy, -1, -3, 2, 2)) {
					if (m[2][1]==0) {
						m[2][1]=1;
						Toast.makeText(context.getApplicationContext(), "8 - OCHO", Toast.LENGTH_SHORT).show();
						//requestRender();
					}


				}//8
				if (ptoStaDentroCuadra(posx, posy, 1, -3, 2, 2)) {
					if (m[2][2]==0) {
						m[2][2]=1;
						Toast.makeText(context.getApplicationContext(), "9 - NUEVE", Toast.LENGTH_SHORT).show();
						//requestRender();
					}


				}
		}
		return true;
	}
	//donde entra la inteligencia artificial y como se puede optimizar el algorritmo. minmmax
	//para 2 jugadores
	private boolean ptoStaDentroCuadra(float posx, float posy, int x, int y, int ancho,
			int alto) {
		return (x < posx && posx < x + ancho && y < posy && posy < y + alto);

	}
	@Override
	public void onDrawFrame(GL10 gl) {
		// TODO Auto-generated method stub

		gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		gl.glLineWidth(3);
		// gl.glPointSize(10);
		gl.glEnable(GL10.GL_POINT_SMOOTH);
		ray.dibuja(gl);

		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				if (m[i][j] != 0) {
					if (m[i][j]==1) {
						if(i==0 && j==0)
							circulo.dibuja(gl,0);
						if(i==0 && j==1)
							circulo.dibuja(gl,1);
						if(i==0 && j==2)
							circulo.dibuja(gl,2);
						if(i==1 && j==0)
							circulo.dibuja(gl,3);
						if(i==1 && j==1)
							circulo.dibuja(gl,4);
						if(i==1 && j==2)
							circulo.dibuja(gl,5);
						if(i==2 && j==0)
							circulo.dibuja(gl,6);
						if(i==2 && j==1)
							circulo.dibuja(gl,7);
						if(i==2 && j==2)
							circulo.dibuja(gl,8);
					}
					else {
						if(i==0 && j==0)
							cruz.dibuja(gl,0);
						if(i==0 && j==1)
							cruz.dibuja(gl,1);
						if(i==0 && j==2)
							cruz.dibuja(gl,2);
						if(i==1 && j==0)
							cruz.dibuja(gl,3);
						if(i==1 && j==1)
							cruz.dibuja(gl,4);
						if(i==1 && j==2)
							cruz.dibuja(gl,5);
						if(i==2 && j==0)
							cruz.dibuja(gl,6);
						if(i==2 && j==1)
							cruz.dibuja(gl,7);
						if(i==2 && j==2)
							cruz.dibuja(gl,8);
					}


					/*if(sw){

					}else{
						cruz.dibuja(gl);
					}*/
				}
			}
		}



	}
}
