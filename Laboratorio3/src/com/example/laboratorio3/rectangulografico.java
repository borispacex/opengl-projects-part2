package com.example.laboratorio3;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.opengles.GL10;

public class rectangulografico {
	
	private float vertices[]= new float[]{	
			112,50,    //v0
			142,50,	 //v3
			142,80,	 //v1	
			112,80,	 //v2
		
	};
	
	FloatBuffer bufVertices;
	public rectangulografico() {
		ByteBuffer bufByte = ByteBuffer.allocateDirect(vertices.length * 4);
		bufByte.order(ByteOrder.nativeOrder());
		bufVertices = bufByte.asFloatBuffer();
		bufVertices.put(vertices);
		bufVertices.rewind();
		
	}
	public void dibuja(GL10 gl){
		gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
		gl.glVertexPointer(2, GL10.GL_FLOAT, 0, bufVertices);
		//gl.glDrawArrays(GL10.GL_TRIANGLES, 0, 6);    // se aumenta si quieres mas lineas multiples a la vez
		gl.glDrawArrays(GL10.GL_TRIANGLE_FAN, 0, 4);    
		gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
		
	}

	
	
}
