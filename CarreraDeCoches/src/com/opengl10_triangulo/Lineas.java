package com.opengl10_triangulo;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;

import javax.microedition.khronos.opengles.GL10;

public class Lineas {
	
	FloatBuffer bufVertices;
	ShortBuffer bufIndices;

	public Lineas() {
		ByteBuffer bufByte = ByteBuffer.allocateDirect(9 * 8 * 4);
		bufByte.order(ByteOrder.nativeOrder());
		bufVertices = bufByte.asFloatBuffer();
		for (int i = 0; i < 9; i++) {
			float d = i * 60;
			bufVertices.put(157); bufVertices.put(0 + d);
			bufVertices.put(163); bufVertices.put(0 + d);
			bufVertices.put(163); bufVertices.put(35 + d);
			bufVertices.put(157); bufVertices.put(35 + d);
		}
		bufVertices.rewind();
		
		bufByte = ByteBuffer.allocateDirect(9 * 6 * 2);
		bufByte.order(ByteOrder.nativeOrder());
		bufIndices = bufByte.asShortBuffer();
		for (int i = 0; i < 4 * 9; i += 4){
			bufIndices.put((byte) (0 + i));
			bufIndices.put((byte) (1 + i));
			bufIndices.put((byte) (2 + i));
			bufIndices.put((byte) (0 + i));
			bufIndices.put((byte) (2 + i));
			bufIndices.put((byte) (3 + i));
		}
		bufIndices.rewind();
	}
	
	public void dibuja(GL10 gl) {
		gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
		gl.glVertexPointer(2, GL10.GL_FLOAT, 0, bufVertices);
		gl.glColor4f(1, 1, 1, 0);
		gl.glDrawElements(GL10.GL_TRIANGLES, 9 * 6,
				GL10.GL_UNSIGNED_SHORT, bufIndices);
		gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
	}
}
