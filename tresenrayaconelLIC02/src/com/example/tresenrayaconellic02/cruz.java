package com.example.tresenrayaconellic02;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.opengles.GL10;
//realiza una cruz
public class cruz {
	/*private float vertices[]={
			-3,2,     // v1
			3,2 		// v2
	};*/
	
	private float vertices[]={
			//primera estrella

			//p0
			-2.8f,1.2f, 
			-1.2f,2.8f, 
			
			-1.2f,1.2f, 
			-2.8f,2.8f,
			
			
			 //p1
			-0.8f,1.2f, 
			0.8f,2.8f, 
			
			0.8f,1.2f, 
			-0.8f,2.8f,
			
			
			//p2
			1.2f,1.2f, 
			2.8f,2.8f, 
			
			2.8f,1.2f, 
			1.2f,2.8f,
			
			
			//p3
			-2.8f,-0.8f, 
			-1.2f,0.8f, 
			
			-1.2f,-0.8f, 
			-2.8f,0.8f,
			
			//p4			
			-0.8f,0.8f, 
			0.8f,-0.8f, 
			
			0.8f,0.8f, 
			-0.8f,-0.8f, 
			
			//p5
			1.2f,-0.8f, 
			2.8f,0.8f, 
			
			2.8f,-0.8f, 
			1.2f,0.8f,
			
			
			//p6
			-2.8f,-2.8f, 
			-1.2f,-1.2f, 
			
			-1.2f,-2.8f, 
			-2.8f,-1.2f,
			
			//p7
			-0.8f,-2.8f, 
			0.8f,-1.2f, 
			
			0.8f,-2.8f, 
			-0.8f,-1.2f,
			
			
			// p8
			1.2f,-2.8f, 
			2.8f,-1.2f, 
			
			2.8f,-2.8f, 
			1.2f,-1.2f,
			
			
		
	};
	
	
	FloatBuffer bufVertices;
	//FloatBuffer bufVertices1;
	public cruz() {
		ByteBuffer bufByte = ByteBuffer.allocateDirect(vertices.length * 4);
		bufByte.order(ByteOrder.nativeOrder());
		bufVertices = bufByte.asFloatBuffer();
		bufVertices.put(vertices);
		bufVertices.rewind();//puntero del buffer
		
	}
	public void dibuja(GL10 gl,int m){
		gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
		gl.glVertexPointer(2, GL10.GL_FLOAT, 0, bufVertices);
		gl.glLineWidth(6);
		gl.glColor4f(0, 0, 0, 0);
		gl.glDrawArrays(GL10.GL_LINES, 4*m, 4);    // se aumenta si quieres mas lineas multiples a la vez
		gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
		
	}
	
}
