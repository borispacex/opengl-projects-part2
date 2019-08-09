package com.example.tarea8;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import javax.microedition.khronos.opengles.GL10;

public class Cuadrado {
	
	FloatBuffer bufVertices;
	public Cuadrado(float n, float m){
		float vertices[] = {
				-n, -m, // 0
				-n, m, // 1
				n, m, // 2
				n, -m //3
		};
		// BUFFER 1
		//en aca se multiplica por 4 torque es float
		ByteBuffer bufByte = ByteBuffer.allocateDirect(vertices.length * 4);
		bufByte.order(ByteOrder.nativeOrder());	// Utiliza el orden del byte nativo
		bufVertices = bufByte.asFloatBuffer();	// Convierte de byte a float
		bufVertices.put(vertices);
		bufVertices.rewind();					// puntero al principio del buffer
	}
	public void dibuja(GL10 gl){
		/* Se activa el arreglo de vértices */
		gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
		// BUFFER 1
		/* Se especifica los datos del arreglo de vértices */
		gl.glVertexPointer(2, GL10.GL_FLOAT, 0, bufVertices);
		/* Dibuja el triángulo */
		gl.glDrawArrays(GL10.GL_TRIANGLE_FAN, 0, 4);
		/* Se desactiva el arreglo de vértices */
		gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
	}
	// metodo para dar color al fondo
	
}
