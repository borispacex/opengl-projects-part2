package com.example.laboratorio5;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;
import javax.microedition.khronos.opengles.GL10;

public class TrianRectangulo {
	private float vertices[];
	byte maxColor1;
	byte maxColor2;
	byte maxColor3;

	private byte colores[];
	private short indices[] = new short[] { 0, 1, 2, 0, 2, 3, // Frente
			4, 5, 6, 4, 6, 7, // Izquierda
			8, 9, 10, 8, 10, 11, // Derecha
			12, 13, 14,// Abajo
			15, 16, 17 // Arriba
	};
	private FloatBuffer bufVertices;
	private ByteBuffer bufColores;
	private ShortBuffer bufIndices;

	public TrianRectangulo(float n, float m, int color1, int color2, int color3) {
		maxColor1 = (byte) color1;
		maxColor2 = (byte) color2;
		maxColor3 = (byte) color3;
		colores = new byte[] {
				// Frente - lila
				maxColor1, maxColor2, maxColor3, 0, // 4 0
				maxColor1, maxColor2, maxColor3, 0, // 5 1
				maxColor1, maxColor2, maxColor3, 0, // 6 2
				maxColor1, maxColor2, maxColor3, 0, // 7 3

				// Izquierda - celeste
				maxColor1, maxColor2, maxColor3, 0, // 0 4
				maxColor1, maxColor2, maxColor3, 0, // 4 5
				maxColor1, maxColor2, maxColor3, 0, // 7 6
				maxColor1, maxColor2, maxColor3, 0, // 3 7
				// Derecha - rojo
				maxColor1, maxColor2, maxColor3, 0, // 5 8
				maxColor1, maxColor2, maxColor3, 0, // 1 9
				maxColor1, maxColor2, maxColor3, 0, // 2 10
				maxColor1, maxColor2, maxColor3, 0, // 6 11

				// Arriba - verde
				maxColor1, maxColor2, 0, 0, // 7 12
				maxColor1, maxColor2, 0, 0, // 6 13
				maxColor1, maxColor2, 0, 0, // 2 14

				// Abajo - azul
				0, maxColor2, maxColor3, 0, // 0 15
				0, maxColor2, maxColor3, 0, // 1 16
				0, maxColor2, maxColor3, 0, // 5 17

		};
		vertices = new float[] {
				// Frente
				1*n, -1*m, 1*n, // 1 //0
				-1*n, -1*m, 1*n, // 2 //1
				-1*n, 1*m, 1*n, // 3 //2
				1*n, 1*m, 1*n, // 4 //3
				// Atras

				// Izquierda
				0*n, -1*m, 0*n, // 0 //4
				-1*n, -1*m, 1*n, // 2 //5
				-1*n, 1*m, 1*n, // 3 //6
				0*n, 1*m, 0*n, // 5 //7
				// Derecha
				0*n, -1*m, 0*n, // 0 //8
				1*n, -1*m, 1*n, // 1 //9
				1*n, 1*m, 1*n, // 4 //10
				0*n, 1*m, 0*n, // 5 //11
				// Abajo
				0*n, -1*m, 0*n, // 0 //12
				1*n, -1*m, 1*n, // 1 //13
				-1*n, -1*m, 1*n, // 2 //14

				// Arriba
				-1*n, 1*m, 1*n, // 3 //15
				1*n, 1*m, 1*n, // 4 //16
				0*n, 1*m, 0*n, // 5 //17

		};
		/* Lee los v�rtices */
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
		/* Activa el arreglo de v�rtices */
		gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
		/* Activa el arreglo de colores */
		gl.glEnableClientState(GL10.GL_COLOR_ARRAY);
		/* Apunta a los datos del arreglo de v�rtices */
		gl.glVertexPointer(3, GL10.GL_FLOAT, 0, bufVertices);
		/* Apunta a los datos del arreglo de colores */
		gl.glColorPointer(4, GL10.GL_UNSIGNED_BYTE, 0, bufColores);
		/*
		 * Renderiza las primitivas desde los datos de los arreglos (vertices,
		 * colores e indices)
		 */
		gl.glDrawElements(GL10.GL_TRIANGLES, indices.length,
				GL10.GL_UNSIGNED_SHORT, bufIndices);
		/* Desactiva el arreglo de v�rtices */
		gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
		/* Desactiva el arreglo de colores */
		gl.glDisableClientState(GL10.GL_COLOR_ARRAY);
	}
}