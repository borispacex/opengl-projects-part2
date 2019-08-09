package com.example.laboratorio5;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;
import javax.microedition.khronos.opengles.GL10;

public class Cubo {
	private float vertices[];

	private byte colores[];
	private short indices[] = new short[] { 0, 1, 2, 0, 2, 3, // Frente
			4, 5, 6, 4, 6, 7, // Atr�s
			8, 9, 10, 8, 10, 11, // Izquierda
			12, 13, 14, 12, 14, 15, // Derecha
			16, 17, 18, 16, 18, 19, // Abajo
			20, 21, 22, 20, 22, 23 // Arriba
	};
	private FloatBuffer bufVertices;
	private ByteBuffer bufColores;
	private ShortBuffer bufIndices;
	private byte maxColor1;
	private byte maxColor2;
	private byte maxColor3;
	private byte maxColor4;
	public Cubo(float a, float b, float c, int color1, int color2, int color3) {
		maxColor1 = (byte) color1;
		maxColor2 = (byte) color2;
		maxColor3 = (byte) color3;
		maxColor4 = (byte) 0;
		colores = new byte[] {
				// Frente - lila
				maxColor1, maxColor2, maxColor3, maxColor4, // 4 0
				maxColor1, maxColor2, maxColor3, maxColor4, // 5 1
				maxColor1, maxColor2, maxColor3, maxColor4, // 6 2
				maxColor1, maxColor2, maxColor3, maxColor4, // 7 3
				// Atr�s - amarillo
				maxColor1, maxColor2, maxColor3, maxColor4, // 3 4
				maxColor1, maxColor2, maxColor3, maxColor4, // 2 5
				maxColor1, maxColor2, maxColor3, maxColor4, // 1 6
				maxColor1, maxColor2, maxColor3, maxColor4, // 0 7
				// Izquierda - celeste
				maxColor1, maxColor2, maxColor3, maxColor4, // 0 8
				maxColor1, maxColor2, maxColor3, maxColor4, // 4 9
				maxColor1, maxColor2, maxColor3, maxColor4, // 7 10
				maxColor1, maxColor2, maxColor3, maxColor4, // 3 11
				// Derecha - rojo
				maxColor1, maxColor2, maxColor3, maxColor4, // 5 12
				maxColor1, maxColor2, maxColor3, maxColor4, // 1 13
				maxColor1, maxColor2, maxColor3, maxColor4, // 2 14
				maxColor1, maxColor2, maxColor3, maxColor4, // 6 15
				// Abajo - azul
				maxColor1, maxColor2, maxColor3, maxColor4, // 0 16
				maxColor1, maxColor2, maxColor3, maxColor4, // 1 17
				maxColor1, maxColor2, maxColor3, maxColor4, // 5 18
				maxColor1, maxColor2, maxColor3, maxColor4, // 4 19
				// Arriba - verde
				maxColor1, maxColor2, maxColor3, maxColor4, // 7 20
				maxColor1, maxColor2, maxColor3, maxColor4, // 6 21
				maxColor1, maxColor2, maxColor3, maxColor4, // 2 22
				maxColor1, maxColor2, maxColor3, maxColor4 // 3 23
		};
		vertices = new float[] {
				// Frente
				-1*a, -1*b, 1*c, // 4 0
				1*a, -1*b, 1*c, // 5 1
				1*a, 1*b, 1*c, // 6 2
				-1*a, 1*b, 1*c, // 7 3
				// Atr�s
				-1*a, 1*b, -1*c, // 3 4
				1*a, 1*b, -1*c, // 2 5
				1*a, -1*b, -1*c, // 1 6
				-1*a, -1*b, -1*c, // 0 7
				// Izquierda
				-1*a, -1*b, -1*c, // 0 8
				-1*a, -1*b, 1*c, // 4 9
				-1*a, 1*b, 1*c, // 7 10
				-1*a, 1*b, -1*c, // 3 11
				// Derecha
				1*a, -1*b, 1*c, // 5 12
				1*a, -1*b, -1*c, // 1 13
				1*a, 1*b, -1*c, // 2 14
				1*a, 1*b, 1*c, // 6 15
				// Abajo
				-1*a, -1*b, -1*c, // 0 16
				1*a, -1*b, -1*c, // 1 17
				1*a, -1*b, 1*c, // 5 18
				-1*a, -1*b, 1*c, // 4 19
				// Arriba
				-1*a, 1*b, 1*c, // 7 20
				1*a, 1*b, 1*c, // 6 21
				1*a, 1*b, -1*c, // 2 22
				-1*a, 1*b, -1*c // 3 23
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