package com.example.tresenrayaconellic02;

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

	/*public Circulo(float radio, int segmentos, boolean llenado, float xx, float yy) {
		this.segmentos = segmentos;
		this.llenado = llenado;
		/* Reserva espacio para los vértices 
		ByteBuffer bufByte = ByteBuffer.allocateDirect(360 * 2 * 4);
		bufByte.order(ByteOrder.nativeOrder()); // Utiliza el orden del byte
												// nativo
		bufVertices = bufByte.asFloatBuffer(); // Convierte de byte a float
		/* Lee los vértices 
		int j = 0;
		for (float i = 0; i < 360.0f; i = i + 360.0f / segmentos) {
			bufVertices.put(j++, xx+(float) Math.cos(Math.toRadians(i)) * radio);
			bufVertices.put(j++, yy+(float) Math.sin(Math.toRadians(i)) * radio);
		}
	}*/

	public Circulo(float radio, int segmentos, boolean llenado) {
		this.segmentos = segmentos;
		this.llenado = llenado;
		
		ByteBuffer bufByte = ByteBuffer.allocateDirect(3240 * 2 * 4);
		bufByte.order(ByteOrder.nativeOrder()); // Utiliza el orden del byte
												// nativo
		bufVertices = bufByte.asFloatBuffer(); // Convierte de byte a float
		/* Lee los vértices */
		int j = 0;
		//c0
		for (float i = 0; i < 360.0f; i = i + 360.0f / segmentos) {
			bufVertices.put(j++, -2+(float) Math.cos(Math.toRadians(i)) * radio);
			bufVertices.put(j++, 2+(float) Math.sin(Math.toRadians(i)) * radio);
		}
		//c1
		for (float i = 0; i < 360.0f; i = i + 360.0f / segmentos) {	
			bufVertices.put(j++, 0+(float) Math.cos(Math.toRadians(i)) * radio);
			bufVertices.put(j++, 2+(float) Math.sin(Math.toRadians(i)) * radio);		
		}
		//c2
		for (float i = 0; i < 360.0f; i = i + 360.0f / segmentos) {	
			bufVertices.put(j++, 2+(float) Math.cos(Math.toRadians(i)) * radio);
			bufVertices.put(j++, 2+(float) Math.sin(Math.toRadians(i)) * radio);		
		}
		//c3
		for (float i = 0; i < 360.0f; i = i + 360.0f / segmentos) {	
			bufVertices.put(j++, -2+(float) Math.cos(Math.toRadians(i)) * radio);
			bufVertices.put(j++, 0+(float) Math.sin(Math.toRadians(i)) * radio);		
		}
		//c4
		for (float i = 0; i < 360.0f; i = i + 360.0f / segmentos) {	
			bufVertices.put(j++, 0+(float) Math.cos(Math.toRadians(i)) * radio);
			bufVertices.put(j++, 0+(float) Math.sin(Math.toRadians(i)) * radio);		
		}
		//c5
		for (float i = 0; i < 360.0f; i = i + 360.0f / segmentos) {	
			bufVertices.put(j++, 2+(float) Math.cos(Math.toRadians(i)) * radio);
			bufVertices.put(j++, 0+(float) Math.sin(Math.toRadians(i)) * radio);		
		}
		//c6
		for (float i = 0; i < 360.0f; i = i + 360.0f / segmentos) {	
			bufVertices.put(j++, -2+(float) Math.cos(Math.toRadians(i)) * radio);
			bufVertices.put(j++, -2+(float) Math.sin(Math.toRadians(i)) * radio);		
		}
		//c7
		for (float i = 0; i < 360.0f; i = i + 360.0f / segmentos) {	
			bufVertices.put(j++, 0+(float) Math.cos(Math.toRadians(i)) * radio);
			bufVertices.put(j++, -2+(float) Math.sin(Math.toRadians(i)) * radio);		
		}
		//c8
		for (float i = 0; i < 360.0f; i = i + 360.0f / segmentos) {	
			bufVertices.put(j++, 2+(float) Math.cos(Math.toRadians(i)) * radio);
			bufVertices.put(j++, -2+(float) Math.sin(Math.toRadians(i)) * radio);		
		}
		
	}
	
	
	public void dibuja(GL10 gl, int m) {
		
		gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
		
		gl.glVertexPointer(2, GL10.GL_FLOAT, 0, bufVertices);
		
		gl.glColor4f(0, 0, 0, 0);
	
		gl.glDrawArrays((llenado) ? GL10.GL_TRIANGLE_FAN : GL10.GL_LINE_LOOP,
				360*m, segmentos);
		
		/*gl.glDrawArrays((llenado) ? GL10.GL_TRIANGLE_FAN : GL10.GL_LINE_LOOP,
				360*1, segmentos);
		
		gl.glDrawArrays((llenado) ? GL10.GL_TRIANGLE_FAN : GL10.GL_LINE_LOOP,
				360*2, segmentos);
		
		gl.glDrawArrays((llenado) ? GL10.GL_TRIANGLE_FAN : GL10.GL_LINE_LOOP,
				360*3, segmentos);
		
		gl.glDrawArrays((llenado) ? GL10.GL_TRIANGLE_FAN : GL10.GL_LINE_LOOP,
				360*4, segmentos);
		
		gl.glDrawArrays((llenado) ? GL10.GL_TRIANGLE_FAN : GL10.GL_LINE_LOOP,
				360*5, segmentos);
		
		gl.glDrawArrays((llenado) ? GL10.GL_TRIANGLE_FAN : GL10.GL_LINE_LOOP,
				360*6, segmentos);
		
		gl.glDrawArrays((llenado) ? GL10.GL_TRIANGLE_FAN : GL10.GL_LINE_LOOP,
				360*7, segmentos);
		
		gl.glDrawArrays((llenado) ? GL10.GL_TRIANGLE_FAN : GL10.GL_LINE_LOOP,
				360*8, segmentos);*/
		
		gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
	}
}