package com.example.carrera_coches;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.opengles.GL10;

public class RectanguloGrafico {
	private float vertices[] = new float [] {
			 112, 50,		 // 0
			142, 50,		 // 1
			142, 80,	 // 2
			 112, 80,  	 // 3
		};
		
		FloatBuffer bufVertices;

		public RectanguloGrafico() {
			ByteBuffer bufByte = ByteBuffer.allocateDirect(vertices.length * 4);
			bufByte.order(ByteOrder.nativeOrder());
			bufVertices = bufByte.asFloatBuffer();
			bufVertices.put(vertices);
			bufVertices.rewind();

		}
		
		public void dibuja(GL10 gl) {
			gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
			gl.glVertexPointer(2, GL10.GL_FLOAT, 0, bufVertices);
			//gl.glColor4f(0.35f, 0.35f, 0.35f, 0);
			gl.glDrawArrays(GL10.GL_TRIANGLE_FAN, 0, 4);
			gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
		}

}
