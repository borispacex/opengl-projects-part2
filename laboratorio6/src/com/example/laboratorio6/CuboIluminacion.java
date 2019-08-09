package com.example.laboratorio6;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;
import javax.microedition.khronos.opengles.GL10;

/**
 * Clase Cubo (OpenGL 1.x)
 * 
 * @author Jhonny Felipez
 * @version 2.0 02/04/2014
 * 
 */
public class CuboIluminacion {

	/**
	 *       3 --------- 2
	 *       /|        /|   
	 *      / |       / |
	 *    7 --------- 6 |
	 *     |  |      |  |
	 *     | 0 ------|-- 1 
	 *     | /       | /
	 *     |/        |/
	 *    4 --------- 5  
	 */
	
    /* Las coordenadas cartesianas (x, y, z) */	
	private float vertices[] = new float[] {
		// Frente
		-0.5f, -0.5f,  0.5f, // 4   0
		 0.5f, -0.5f,  0.5f, // 5   1
		 0.5f,  0.5f,  0.5f, // 6   2
		-0.5f,  0.5f,  0.5f, // 7   3
		// Atrás
		-0.5f,  0.5f, -0.5f, // 3   4
		 0.5f,  0.5f, -0.5f, // 2   5
		 0.5f, -0.5f, -0.5f, // 1   6
		-0.5f, -0.5f, -0.5f, // 0   7
		// Izquierda
		-0.5f, -0.5f, -0.5f, // 0   8
		-0.5f, -0.5f,  0.5f, // 4   9
		-0.5f,  0.5f,  0.5f, // 7  10 
		-0.5f,  0.5f, -0.5f, // 3  11
		// Derecha
		 0.5f, -0.5f,  0.5f, // 5  12 
		 0.5f, -0.5f, -0.5f, // 1  13
		 0.5f,  0.5f, -0.5f, // 2  14
		 0.5f,  0.5f,  0.5f, // 6  15
		 // Abajo
		-0.5f, -0.5f, -0.5f, // 0  16
		 0.5f, -0.5f, -0.5f, // 1  17
		 0.5f, -0.5f,  0.5f, // 5  18
		-0.5f, -0.5f,  0.5f, // 4  19
		 // Arriba
		-0.5f,  0.5f,  0.5f, // 7  20
		 0.5f,  0.5f,  0.5f, // 6  21
		 0.5f,  0.5f, -0.5f, // 2  22
		-0.5f,  0.5f, -0.5f  // 3  23
	};

	/* Las normales x c/cara (x,y,z) */
	private float normales[] = new float[] {
		// Frente
		 0,  0,  1, // 4   0
		 0,  0,  1, // 5   1
		 0,  0,  1, // 6   2
		 0,  0,  1, // 7   3	
		// Atrás
		 0,  0, -1, // 3   4	
		 0,  0, -1, // 2   5
		 0,  0, -1, // 1   6	
		 0,  0, -1, // 0   7	
		// Izquierda
		-1,  0,  0, // 0   8
		-1,  0,  0, // 4   9
		-1,  0,  0, // 7  10
		-1,  0,  0, // 3  11
		// Derecha
		 1,  0,  0, // 5  12
		 1,  0,  0, // 1  13
		 1,  0,  0, // 2  14
		 1,  0,  0, // 6  15
		// Abajo
		 0, -1,  0, // 0  16
		 0, -1,  0, // 1  17
		 0, -1,  0, // 5  18
		 0, -1,  0, // 4  19
		// Arriba
		 0,  1,  0, // 7  20
		 0,  1,  0, // 6  21
		 0,  1,  0, // 2  22
		 0,  1,  0  // 3  23
	};
	
	/* Indices */
	private short indices[] = new short [] { 
		 0,  1,  2,  0,  2,  3, // Frente
		 4,  5,  6,  4,  6,  7, // Atrás
		 8,  9, 10,  8, 10, 11, // Izquierda 
		12, 13, 14, 12, 14, 15, // Derecha
		16, 17, 18, 16, 18, 19, // Abajo
		20, 21, 22, 20, 22, 23  // Arriba
	};
	
	private FloatBuffer bufVertices;
	private FloatBuffer bufNormales;
	private ShortBuffer bufIndices;
	
	public CuboIluminacion() {
		/* Lee los vértices */
		ByteBuffer bufByte = ByteBuffer.allocateDirect(vertices.length * 4);
		bufByte.order(ByteOrder.nativeOrder()); // Utiliza el orden de byte nativo
		bufVertices = bufByte.asFloatBuffer(); // Convierte de byte a float
		bufVertices.put(vertices).rewind();

		/* Lee las normales */
		bufByte = ByteBuffer.allocateDirect(normales.length * 4);
		bufByte.order(ByteOrder.nativeOrder()); // Utiliza el orden de byte nativo
		bufNormales = bufByte.asFloatBuffer(); // Convierte de byte a float
		bufNormales.put(normales).rewind();
		
		/* Lee los indices */
		bufByte = ByteBuffer.allocateDirect(indices.length * 2);
		bufByte.order(ByteOrder.nativeOrder()); // Utiliza el orden de byte nativo
		bufIndices = bufByte.asShortBuffer(); // Convierte de byte a short
		bufIndices.put(indices);
		bufIndices.rewind(); // puntero al principio del buffer
	}
	
	public void dibuja(GL10 gl) {

		/* Se habilita el acceso al arreglo de vértices */
		gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
		
		/* Se habilita el acceso al arreglo de colores */
		gl.glEnableClientState(GL10.GL_NORMAL_ARRAY);
		
		/* Se especifica los datos del arreglo de vértices */
		gl.glVertexPointer(3, GL10.GL_FLOAT, 0, bufVertices);		
		
		/* Se especifica los datos del arreglo de las normales */
		gl.glNormalPointer(GL10.GL_FLOAT, 0, bufNormales);
		
		/* Se dibuja el cubo */
		gl.glDrawElements(GL10.GL_TRIANGLES, indices.length,
				GL10.GL_UNSIGNED_SHORT, bufIndices);

		/* Se deshabilita el acceso a los arreglos */
		gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
		gl.glDisableClientState(GL10.GL_NORMAL_ARRAY);
	}
}
