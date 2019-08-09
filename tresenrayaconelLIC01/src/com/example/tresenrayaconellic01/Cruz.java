package com.example.tresenrayaconellic01;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.opengles.GL10;

public class Cruz {
	private float vertices[]={
			-1,1,
			1,-1,
			1,1,
			-1,-1
			
			
	};
	
	
	FloatBuffer bufVertices;

	public Cruz() {
		ByteBuffer bufByte = ByteBuffer.allocateDirect(vertices.length * 4);
		bufByte.order(ByteOrder.nativeOrder());
		bufVertices = bufByte.asFloatBuffer();
		bufVertices.put(vertices);
		bufVertices.rewind();
		
	}
	
	public void dibuja(GL10 gl){
		gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
		gl.glVertexPointer(2, GL10.GL_FLOAT, 0, bufVertices);
		gl.glColor4f(1, 0, 0, 0);
		gl.glLineWidthx(5);
		//gl.glDrawArrays(GL10.GL_TRIANGLES, 0, 6);    // se aumenta si quieres mas lineas multiples a la vez
		gl.glDrawArrays(GL10.GL_LINES, 0, 4);    
		gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
		
	}

	
}
