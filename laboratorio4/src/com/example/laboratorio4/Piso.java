package com.example.laboratorio4;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;
import javax.microedition.khronos.opengles.GL10;

public class Piso {
	private float vertices[] = new float[] 
	{
		-5,-1f,3,
		5,-1f,3,
		5,-1f,-3,
		-5,-1f,-3,
	};
	byte maxColor = (byte)255;
	
	private byte colores[] = new byte[]
	{
			(byte)188, (byte)188, (byte)188,0,
			(byte)188, (byte)188, (byte)188,0,
			(byte)188, (byte)188, (byte)188,0,
			(byte)188, (byte)188, (byte)188,0,
	};
	
	private FloatBuffer bufVertices;
	private ByteBuffer bufColores;
	private ShortBuffer bufIndices;
	
	public Piso()
	{
		/* Lee los v�rtices */
		ByteBuffer bufByte = ByteBuffer.allocateDirect(vertices.length * 4);
		bufByte.order(ByteOrder.nativeOrder()); // Utiliza el orden de byte nativo
		bufVertices = bufByte.asFloatBuffer(); // Convierte de byte a float
		bufVertices.put(vertices);
		bufVertices.rewind(); // puntero al principio del buffer

		/* Lee los colores */
		bufColores = ByteBuffer.allocateDirect(colores.length);
		bufColores.put(colores);
		bufColores.position(0); // puntero al principio del buffer
	}
	
	public void dibuja(GL10 gl)
	{
		gl.glCullFace(GL10.GL_BACK);
		gl.glEnable(GL10.GL_CULL_FACE);
		/* Activa el arreglo de v�rtices */
		gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
		/* Activa el arreglo de colores */
		gl.glEnableClientState(GL10.GL_COLOR_ARRAY);
		/* Apunta a los datos del arreglo de v�rtices */
		gl.glVertexPointer(3, GL10.GL_FLOAT, 0, bufVertices);
		/* Apunta a los datos del arreglo de colores */
		gl.glColorPointer(4, GL10.GL_UNSIGNED_BYTE, 0, bufColores);
		/* Renderiza las primitivas desde los datos de los arreglos (vertices,
		 * colores e indices) */
		gl.glPushMatrix();
			gl.glDrawArrays(GL10.GL_TRIANGLE_FAN, 0,4);
		gl.glPopMatrix();
		/* Desactiva el arreglo de v�rtices */
		gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
		/* Desactiva el arreglo de colores */
		gl.glDisableClientState(GL10.GL_COLOR_ARRAY);
	}
}