package com.example.tarea8;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import javax.microedition.khronos.opengles.GL10;

public class Triangulo {
	 /*		 2
	 *      / \ 
	 *     /   \ 
	 * 	  /     \ 
	 *   /       \ 
	 * 	/________ \ 
	 * 0 			1
	 */
	/*
	 * Las coordenadas cartesianas (x, y)
	 */
	FloatBuffer bufVertices;
	public Triangulo(float n) {
		float vertices[] = new float[] { 
				-n, -n, // 0
				n, -n, // 1
				0, n // 2
		};
		/* Lee los vértices */
		ByteBuffer bufByte = ByteBuffer.allocateDirect(vertices.length * 4);
		bufByte.order(ByteOrder.nativeOrder()); 	// Utiliza el ordendel byte nativo
		bufVertices = bufByte.asFloatBuffer();		// Convierte de byte a float
		bufVertices.put(vertices);
		bufVertices.rewind();	// puntero al principio del buffer
	}

	public void dibuja(GL10 gl) {
		gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);	// Se activa el arreglo de vértices
		gl.glVertexPointer(2, GL10.GL_FLOAT, 0, bufVertices);	// Se especifica los datos del arreglo de vértices
		gl.glColor4f(1, 0, 0, 0);	// Se establece el color en (r,g,b,a)
		gl.glDrawArrays(GL10.GL_TRIANGLES, 0, 3);	// Dibuja el triángulo	
		gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);	// Se desactiva el arreglo de vértices	
	}
}