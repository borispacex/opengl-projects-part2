package com.example.tresenrayaconellic01;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.opengles.GL10;

public class Circulo {
	/**
	 * 3 2
	 *
	 * 4 1
	 *
	 * 5 0
	 *
	 * 6 9
	 *
	 * 7 8
	 */
	private FloatBuffer bufVertices;
	private int segmentos;
	private boolean llenado;
	public Circulo(float radio, int segmentos, boolean llenado) {
		this.segmentos = segmentos;
		this.llenado = llenado;
		/* Reserva espacio para los v�rtices */
		ByteBuffer bufByte = ByteBuffer.allocateDirect(360 * 2 * 4);
		bufByte.order(ByteOrder.nativeOrder()); // Utiliza el orden del byte nativo
		bufVertices = bufByte.asFloatBuffer(); // Convierte de byte a float
		/* Lee los v�rtices */
		int j = 0;
		for (float i = 0; i < 360.0f; i = i + 360.0f/segmentos) {
			//for (float i = 0; i < 360.0f; i = i + 360.0f/segmentos) {
			//bufVerticesCabeza.put(j++, -0.25f + (float) Math.cos(Math.toRadians(i)) * radio);
	 		//bufVerticesCabeza.put(j++, 0.5f + (float) Math.sin(Math.toRadians(i)) * radio);
			
			
			
			bufVertices.put(j++, +0+(float) Math.cos(Math.toRadians(i)) * radio);
			bufVertices.put(j++, +0+(float) Math.sin(Math.toRadians(i)) * radio);
		}
	}
	public void dibuja(GL10 gl) {
		/*
		gl.glVertexPointer(2, GL10.GL_FLOAT, 0, bufVerticesCabeza);
		gl.glColor4f(0, 127 / 255f, 76 / 255f, 1);
		gl.glDrawArrays(GL10.GL_TRIANGLE_FAN, 0, segmentos);
		gl.glDisableClientState(GL10.GL_VERTEX_ARRAY); 
		 */
		
		/* Activa el arreglo de v�rtices */
		gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
		/* Apunta a los datos del arreglo de v�rtices */
		gl.glVertexPointer(2, GL10.GL_FLOAT, 0, bufVertices);
		/* Se establece el color en (r,g,b,a) */
		gl.glColor4f(1, 0, 1, 0);
		/* Renderiza las primitivas desde los datos del arreglo de v�rtices */
		gl.glDrawArrays((llenado) ? GL10.GL_TRIANGLE_FAN : GL10.GL_LINE_LOOP, 0, segmentos);
		/* Desactiva el arreglo de v�rtices */
		gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
	}
}