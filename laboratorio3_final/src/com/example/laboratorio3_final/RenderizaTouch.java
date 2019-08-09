package com.example.laboratorio3_final;

import java.util.Random;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;
import android.content.Context;
import android.opengl.GLSurfaceView;
import android.opengl.GLSurfaceView.Renderer;
import android.opengl.GLU;
import android.view.MotionEvent;

public class RenderizaTouch extends GLSurfaceView implements Renderer {

	private Context contexto;

	private float despCarreteraY;
	private float despCoche1x;
	private float despCoche2x;
	private float despCoche2y;
	private float x1;
	private float x2;
	private float y2;

	public TexturaTouch texturaCoche1;
	public TexturaTouch texturaCarretera;

	public TexturaTouch texturaCoche2;
	Sonido sonido1;

	Random ran = new Random();

	public RenderizaTouch(Context contexto) {
		super(contexto);
		this.contexto = contexto;
		sonido1 = new Sonido(contexto, "sonidocc.ogg");

		this.setRenderer(this);
		this.setRenderMode(GLSurfaceView.RENDERMODE_CONTINUOUSLY);
	}

	// @Override
	public void onSurfaceCreated(GL10 gl, EGLConfig arg1) {

		despCarreteraY = 0;
		despCoche1x = 0;

		texturaCarretera = new TexturaTouch(gl, contexto, "carretera.png");
		texturaCarretera.setVertices(0, 0, 320, 480);

		texturaCoche1 = new TexturaTouch(gl, contexto, "autoRojoC1.png");
		texturaCoche1.setVertices(112, 50, 30, 40);

		texturaCoche2 = new TexturaTouch(gl, contexto, "autoYellowC1.png");
		texturaCoche2.setVertices(112 + despCoche2x, 50 + despCoche2y, 30, 40);
		sonido1.play();
		gl.glEnable(GL10.GL_TEXTURE_2D);
		gl.glBlendFunc(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);
		gl.glEnable(GL10.GL_BLEND);
		if (ran.nextInt(2) == 0) {
			despCoche2x = 0;
		} else {
			despCoche2x = 64;
		}
		despCoche2y = 840;

		gl.glClearColor(0.24f, 0.874f, 0.11f, 0);

	}

	public void dibujaCarretera(GL10 gl) {
		gl.glMatrixMode(GL10.GL_MODELVIEW);
		gl.glLoadIdentity();
		gl.glMatrixMode(GL10.GL_TEXTURE);
		gl.glLoadIdentity();
		gl.glTranslatef(0, 112 + despCarreteraY, 0);
		gl.glBindTexture(GL10.GL_TEXTURE_2D,
				texturaCarretera.getCodigoTextura());
		texturaCarretera.muestra(gl);

	}

	public void dibujaCoche1(GL10 gl) {

		gl.glMatrixMode(GL10.GL_MODELVIEW);

		gl.glLoadIdentity();

		texturaCoche1.setVertices(112 + despCoche1x, 50, 30, 40);
		x1 = 112 + despCoche1x;
		gl.glTranslatef(despCoche1x, 0, 0);
		gl.glMatrixMode(GL10.GL_TEXTURE);
		gl.glLoadIdentity();

		gl.glBindTexture(GL10.GL_TEXTURE_2D, texturaCoche1.getCodigoTextura());
		texturaCoche1.muestra(gl);

	}

	public void dibujaCoche2(GL10 gl) {
		gl.glMatrixMode(GL10.GL_MODELVIEW);
		gl.glLoadIdentity();
		x2 = 112 + despCoche2x;
		y2 = 50 + despCoche2y;
		gl.glTranslatef(despCoche2x, despCoche2y, 0);
		gl.glMatrixMode(GL10.GL_TEXTURE);
		gl.glLoadIdentity();
		gl.glBindTexture(GL10.GL_TEXTURE_2D, texturaCoche2.getCodigoTextura());
		texturaCoche2.muestra(gl);

	}

	// @Override
	public void onDrawFrame(GL10 gl) {
		gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);
		dibujaCarretera(gl);
		dibujaCoche1(gl);
		dibujaCoche2(gl);

		if (!seSobrePonen(texturaCoche1, texturaCoche2)) {

			despCarreteraY = despCarreteraY - 0.01f;
			despCoche2y = despCoche2y - 10;
			if (despCarreteraY < -60) {
				despCarreteraY = 0;
			}
			if (despCoche2y < -60) {

				despCoche2y = 840;

				if (ran.nextInt(2) == 0) {
					despCoche2x = 0;
				} else {
					despCoche2x = 64;
				}
			}
		}

	}

	// @Override
	public void onSurfaceChanged(GL10 gl, int width, int height) {

		gl.glViewport(0, 0, width, height);

		gl.glMatrixMode(GL10.GL_PROJECTION);

		gl.glLoadIdentity();

		GLU.gluOrtho2D(gl, 0, 320, 0, 480);

		gl.glMatrixMode(GL10.GL_MODELVIEW);

		gl.glLoadIdentity();
	}

	public boolean onTouchEvent(MotionEvent e) {

		if (e.getAction() == MotionEvent.ACTION_UP) {
			if (despCoche1x == 0) {
				despCoche1x = 32;
			} else
				despCoche1x = 0;
			requestRender();
		}
		return true;
	}

	public boolean seSobrePonen(TexturaTouch r1, TexturaTouch r2) {
		return (x1 < x2 + r2.getAncho() && x2 - 32 < x1 + r1.getAncho()
				&& 50 < y2 + r2.getAlto() && y2 < 50 + r1.getAlto());
	}
}
