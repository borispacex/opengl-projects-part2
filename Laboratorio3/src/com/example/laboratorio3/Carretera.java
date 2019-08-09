package com.example.laboratorio3;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import javax.microedition.khronos.opengles.GL10;

public class Carretera {
	
	private float vertices[] = new float [] {
		 96, 0,		 // 0
		224, 0,		 // 1
		224, 480,	 // 2
		 96, 480,  	 // 3
	};
	
	FloatBuffer bufVertices;

	public Carretera() {
		ByteBuffer bufByte = ByteBuffer.allocateDirect(vertices.length * 4);
		bufByte.order(ByteOrder.nativeOrder());
		bufVertices = bufByte.asFloatBuffer();
		bufVertices.put(vertices);
		bufVertices.rewind();

	}
	
	public void dibuja(GL10 gl) {
		gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
		gl.glVertexPointer(2, GL10.GL_FLOAT, 0, bufVertices);
		gl.glColor4f(0.11f, 0.11f, 0.11f, 0);
		gl.glDrawArrays(GL10.GL_TRIANGLE_FAN, 0, 4);
		gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
	}
}
