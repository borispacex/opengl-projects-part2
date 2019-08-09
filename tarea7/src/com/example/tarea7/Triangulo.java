package com.example.tarea7;

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
	/*
	private float vertices[] = new float[] { 
			0.5f, 0.5f,	// 0
			2.5f, 0.5f,	// 1
			1.5f, 2.5f	// 2
	};
	*/
	private float vertices[] = new float[] { 
			-1, -1,	// 0
			1, -1,	// 1
			0, 1	// 2
	};
	FloatBuffer bufVertices;

	public Triangulo() {
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
		gl.glColor4f(0, 0, 1, 0);	// Se establece el color en (r,g,b,a)
		gl.glDrawArrays(GL10.GL_TRIANGLES, 0, 3);	// Dibuja el triángulo	
		gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);	// Se desactiva el arreglo de vértices	
	}
}