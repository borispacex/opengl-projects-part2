package com.example.laboratorio6;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;
import javax.microedition.khronos.opengles.GL10;
/**
 * Clase Rectangulo (OpenGL 1.x)
 *  
 * @author Jhonny Felipez
 * @version 1.0 21/08/2014
 *
 */
public class Rectangulo {
	
	/**
	 *    3 ---------- 2
	 *     |         /| 
	 *     |      /   | 
	 *     |   /      |
	 *     |/         |
	 *    0 ---------- 1  
	 */
	
	/* Las coordenadas cartesianas (x, y) */
	private float vertices[] = new float[] {
		-10, -0.6f, -10, // 0
		 10, -0.6f, -10, // 1
		 10, -0.6f,  10, // 2
		-10, -0.6f,  10, // 3
	};
	
    /* Las normales (x,y,z) */
	private float normales[] = new float[] {
		 0,  1,  0, // 0
		 0,  1,  0, // 1
		 0,  1,  0, // 2
		 0,  1,  0, // 3
	};
	
	/* Indices */
	private short indices[] = new short [] { 
		 0, 1, 2, 0, 2, 3
	};
	
	private FloatBuffer bufVertices;
	private FloatBuffer bufNormales;	
	private ShortBuffer bufIndices;
	
	public Rectangulo(){
		
		/* Lee los vértices */
		ByteBuffer bufByte = ByteBuffer.allocateDirect(vertices.length * 4);
		bufByte.order(ByteOrder.nativeOrder()); // Utiliza el orden de byte nativo
		bufVertices = bufByte.asFloatBuffer(); // Convierte de byte a float
		bufVertices.put(vertices).rewind(); // puntero al principio del buffer

		/* Lee las normales */
		bufByte = ByteBuffer.allocateDirect(normales.length * 4);
		bufByte.order(ByteOrder.nativeOrder()); // Utiliza el orden de byte nativo
		bufNormales = bufByte.asFloatBuffer(); // Convierte de byte a float
		bufNormales.put(normales).rewind();		

		/* Lee los indices */
		bufByte = ByteBuffer.allocateDirect(indices.length * 2);
		bufByte.order(ByteOrder.nativeOrder()); // Utiliza el orden de byte nativo
		bufIndices = bufByte.asShortBuffer(); // Convierte de byte a short
		bufIndices.put(indices).rewind(); // puntero al principio del buffer
		
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
