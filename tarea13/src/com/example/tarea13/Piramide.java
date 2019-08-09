package com.example.tarea13;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;
import javax.microedition.khronos.opengles.GL10;

public class Piramide {

	private float vertices[] = new float[] {
			// Frente
			1, -1, 1, // 2 //0
			-1, -1, 1, // 3 //1
			0, 1, 0, // 4 //2
			// Atrás
			-1, -1, -1, // 0 //3
			1, -1, -1, // 1 //4
			0, 1, 0,// 4 //5
			// Izquierda
			-1, -1, -1, // 0 //6
			-1, -1, 1, // 3 //7
			0, 1, 0,// 4 // 8
			// Derecha
			1, -1, -1, // 1 //9
			1, -1, 1, // 2 //10
			0, 1, 0,// 4 // 11
			// Abajo
			-1, -1, -1, // 0 //12
			1, -1, -1, // 1 //13
			1, -1, 1, // 2 //14
			-1, -1, 1, // 3 //15

	};
	byte maxColor = (byte) 255;
	private byte colores[] = new byte[] {

			// Frente - lila
			maxColor, 0, maxColor, maxColor, // 4 0
			maxColor, 0, maxColor, maxColor, // 5 1
			maxColor, 0, maxColor, maxColor, // 6 2

			// Atrás - amarillo
			maxColor, maxColor, 0, maxColor, // 3 4
			maxColor, maxColor, 0, maxColor, // 2 5
			maxColor, maxColor, 0, maxColor, // 1 6

			// Izquierda - celeste
			0, maxColor, maxColor, maxColor, // 0 8
			0, maxColor, maxColor, maxColor, // 4 9
			0, maxColor, maxColor, maxColor, // 7 10

			// Derecha - rojo
			maxColor, 0, 0, maxColor, // 5 12
			maxColor, 0, 0, maxColor, // 1 13
			maxColor, 0, 0, maxColor, // 2 14

			// Abajo - azul
			0, 0, maxColor, maxColor, // 0 16
			0, 0, maxColor, maxColor, // 1 17
			0, 0, maxColor, maxColor, // 5 18
			0, 0, maxColor, maxColor, // 4 19

	};
	private short indices[] = new short[] { 0, 1, 2, // Frente
			3, 4, 5, // Atrás
			6, 7, 8, // Izquierda
			9, 10, 11, // Derecha
			12, 13, 14, 12, 14, 15, // Abajo

	};
	private FloatBuffer bufVertices;
	private ByteBuffer bufColores;
	private ShortBuffer bufIndices;

	public Piramide() {
		/* Lee los vértices */
		ByteBuffer bufByte = ByteBuffer.allocateDirect(vertices.length * 4);
		bufByte.order(ByteOrder.nativeOrder()); // Utiliza el orden de byte
												// nativo
		bufVertices = bufByte.asFloatBuffer(); // Convierte de byte a float
		bufVertices.put(vertices);
		bufVertices.rewind(); // puntero al principio del buffer
		/* Lee los colores */
		bufColores = ByteBuffer.allocateDirect(colores.length);
		bufColores.put(colores);
		bufColores.position(0); // puntero al principio del buffer
		/* Lee los indices */
		bufByte = ByteBuffer.allocateDirect(indices.length * 2);
		bufByte.order(ByteOrder.nativeOrder()); // Utiliza el orden de byte
												// nativo
		bufIndices = bufByte.asShortBuffer(); // Convierte de byte a short
		bufIndices.put(indices);
		bufIndices.rewind(); // puntero al principio del buffer
	}

	public void dibuja(GL10 gl) {
		/* Activa el arreglo de vértices */
		gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
		/* Activa el arreglo de colores */
		gl.glEnableClientState(GL10.GL_COLOR_ARRAY);
		/* Apunta a los datos del arreglo de vértices */
		gl.glVertexPointer(3, GL10.GL_FLOAT, 0, bufVertices);
		/* Apunta a los datos del arreglo de colores */
		gl.glColorPointer(4, GL10.GL_UNSIGNED_BYTE, 0, bufColores);
		/*
		 * Renderiza las primitivas desde los datos de los arreglos (vertices,
		 * colores e indices)
		 */
		gl.glDrawElements(GL10.GL_TRIANGLES, indices.length,
				GL10.GL_UNSIGNED_SHORT, bufIndices);
		/* Desactiva el arreglo de vértices */
		gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
		/* Desactiva el arreglo de colores */
		gl.glDisableClientState(GL10.GL_COLOR_ARRAY);
	}
}