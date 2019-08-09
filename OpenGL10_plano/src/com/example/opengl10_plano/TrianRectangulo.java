package com.example.opengl10_plano;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;
import javax.microedition.khronos.opengles.GL10;

public class TrianRectangulo
{
	/**
	 * 3 --------- 2
	 * /| /|
	 * / | / |
	 * 7 --------- 6 |
	 * | | | |
	 * | 0 ------|-- 1
	 * | / | /
	 * |/ |/
	 * 4 --------- 5
	 */
	private float vertices[] = new float[]
			{
			// Frente
			1, -1, 1, //  1 //0
			-1, -1, 1, //  2 //1
			-1, 1, 1, //  3 //2
			1, 1, 1, //  4 //3
			// Atr�s

			// Izquierda
			0, -1, 0, // 0 //4
			-1, -1, 1, //  2 //5
			-1, 1, 1, //  3 //6
			0, 1, 0, //5 //7
			// Derecha
			0, -1, 0, // 0 //8
			1, -1, 1, //  1 //9
			1, 1, 1, //  4 //10
			0, 1, 0, //5 //11
			// Abajo
			0, -1, 0, // 0 //12
			1, -1, 1, //  1 //13
			-1, -1, 1, //  2 //14

			// Arriba
			-1, 1, 1, //  3 //15
			1, 1, 1, //  4 //16
			0, 1, 0, //5 //17

			};
	byte maxColor = (byte)255;

	private byte colores[] = new byte[]
			{
			// Frente - lila
			0,maxColor, maxColor, maxColor, // 4 0
			0,maxColor, maxColor, maxColor, // 5 1
			0,maxColor, maxColor, maxColor, // 6 2
			0,maxColor, maxColor, maxColor, // 7 3

			// Izquierda - celeste
			maxColor,0, maxColor, maxColor, // 0 4
			maxColor,0, maxColor, maxColor, // 4 5
			maxColor,0, maxColor, maxColor, // 7 6
			maxColor,0, maxColor, maxColor, // 3 7
			// Derecha - rojo
			maxColor, 0, 0, maxColor, // 5 8
			maxColor, 0, 0, maxColor, // 1 9
			maxColor, 0, 0, maxColor, // 2 10
			maxColor, 0, 0, maxColor, // 6 11

			// Arriba - verde
			0, maxColor, 0, maxColor, // 7 12
			0, maxColor, 0, maxColor, // 6 13
			0, maxColor, 0, maxColor, // 2 14

			// Abajo - azul
			0, 0, maxColor, maxColor, // 0 15
			0, 0, maxColor, maxColor, // 1 16
			0, 0, maxColor, maxColor, // 5 17

			};
	private short indices[] = new short []
			{
			0,1,2,0,2,3, // Frente
			4,5,6,4,6,7, // Izquierda
			8,9,10,8,10,11, // Derecha
			12,13,14,// Abajo
			15,16,17 // Arriba
			};
	private FloatBuffer bufVertices;
	private ByteBuffer bufColores;
	private ShortBuffer bufIndices;

	public TrianRectangulo()
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

		/* Lee los indices */
		bufByte = ByteBuffer.allocateDirect(indices.length * 2);
		bufByte.order(ByteOrder.nativeOrder()); // Utiliza el orden de byte nativo
		bufIndices = bufByte.asShortBuffer(); // Convierte de byte a short
		bufIndices.put(indices);
		bufIndices.rewind(); // puntero al principio del buffer
	}

	public void dibuja(GL10 gl)
	{
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
		gl.glDrawElements(GL10.GL_TRIANGLES, indices.length,
				GL10.GL_UNSIGNED_SHORT, bufIndices);
		/* Desactiva el arreglo de v�rtices */
		gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
		/* Desactiva el arreglo de colores */
		gl.glDisableClientState(GL10.GL_COLOR_ARRAY);
	}
}