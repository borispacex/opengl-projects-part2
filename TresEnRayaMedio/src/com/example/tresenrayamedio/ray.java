package com.example.tresenrayamedio;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.opengles.GL10;
//realiza una cruz
public class ray {
	/*private float vertices[]={
			-3,2,     // v1
			3,2 		// v2
	};*/
	
	private float vertices[]={
			//primera estrella
			3,1 ,-3,1,
			3,-1, -3,-1,
			1,3, 1,-3,
			-1,3, -1,-3
			
			
			
	};
	private float vertices1[]={
			//primera estrella
			
			
	};
	
	FloatBuffer bufVertices;
	FloatBuffer bufVertices1;
	public ray() {
		ByteBuffer bufByte = ByteBuffer.allocateDirect(vertices.length * 4);
		bufByte.order(ByteOrder.nativeOrder());
		bufVertices = bufByte.asFloatBuffer();
		bufVertices.put(vertices);
		bufVertices.rewind();//puntero del buffer
		
	}
	public void dibuja(GL10 gl){
		gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
		gl.glVertexPointer(2, GL10.GL_FLOAT, 0, bufVertices);
		gl.glLineWidthx(7);
		gl.glColor4f(1, 0, 0, 0);//rojo
		gl.glDrawArrays(GL10.GL_LINES, 0, 8);    // se aumenta si quieres mas lineas multiples a la vez
		gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
		
	}
	
}
