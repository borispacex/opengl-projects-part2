package com.example.laboratorio5;



import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import javax.microedition.khronos.opengles.GL10;

public class Cilindro {

	public Cilindro(int n, float radious) {

		this.numOfVertex = n;

		float[] vertex = new float[3 * (n + 1) * 2];
		byte[] baseIndex = new byte[n];
		byte[] topIndex = new byte[n];
		byte[] edgeIndex = new byte[n * 2 + 2];

		double perAngle = 2 * Math.PI / n;

		for (int i = 0; i < n; i++) {
			double angle = i * perAngle;
			int offset = 6 * i;

			vertex[offset + 0] = (float) (Math.cos(angle) * radious) + cx;
			vertex[offset + 1] = -height;
			vertex[offset + 2] = (float) (Math.sin(angle) * radious) + cy;

			vertex[offset + 3] = (float) (Math.cos(angle) * radious) + cx;
			vertex[offset + 4] = height;
			vertex[offset + 5] = (float) (Math.sin(angle) * radious) + cy;

			topIndex[i] = (byte) (2 * i);

			baseIndex[i] = (byte) (2 * i + 1);

			edgeIndex[2 * i + 1] = baseIndex[i];
			edgeIndex[2 * i] = topIndex[i];

		}

		edgeIndex[2 * n] = topIndex[0];
		edgeIndex[2 * n + 1] = baseIndex[0];

		ByteBuffer vbb = ByteBuffer.allocateDirect(vertex.length * 4).order(
				ByteOrder.nativeOrder());

		mFVertexBuffer = vbb.asFloatBuffer();
		mFVertexBuffer.put(vertex);
		mFVertexBuffer.position(0);

		normalBuffer = mFVertexBuffer;

		mCircleBottom = ByteBuffer.allocateDirect(baseIndex.length);
		mCircleBottom.put(baseIndex);
		mCircleBottom.position(0);

		mCircleTop = ByteBuffer.allocateDirect(topIndex.length);
		mCircleTop.put(topIndex);
		mCircleTop.position(0);

		mEdge = ByteBuffer.allocateDirect(edgeIndex.length);
		mEdge.put(edgeIndex);
		mEdge.position(0);
	}

	public void dibuja(GL10 gl) {
		gl.glCullFace(GL10.GL_BACK);
		gl.glEnable(GL10.GL_CULL_FACE);
		gl.glVertexPointer(3, GL10.GL_FLOAT, 0, mFVertexBuffer);
		gl.glNormalPointer(GL10.GL_FLOAT, 0, normalBuffer);
		gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);

		gl.glPushMatrix();

		// gl.glColor4f(0.5f, 0.35f, 0.05f, 0);		// color cafe
		gl.glDrawElements(GL10.GL_TRIANGLE_STRIP, numOfVertex * 2 + 2,
				GL10.GL_UNSIGNED_BYTE, mEdge);
		gl.glPopMatrix();
		gl.glPushMatrix();

		// gl.glColor4f((byte)188, (byte)188, (byte)188, 0);	// color plomo
		gl.glDrawElements(GL10.GL_TRIANGLE_FAN, numOfVertex,
				GL10.GL_UNSIGNED_BYTE, mCircleTop);
		gl.glPopMatrix();

		gl.glPushMatrix();

		gl.glTranslatef(0, 2 * height, 0);
		gl.glRotatef(-180, 1, 0, 0);

		// gl.glColor4f(0, 1f, 0, 0);	// color verde
		gl.glDrawElements(GL10.GL_TRIANGLE_FAN, numOfVertex,
				GL10.GL_UNSIGNED_BYTE, mCircleBottom);
		gl.glPopMatrix();

	}

	private FloatBuffer mFVertexBuffer;
	private FloatBuffer normalBuffer;
	private ByteBuffer mCircleBottom;
	private ByteBuffer mCircleTop;
	private ByteBuffer mEdge;
	private int numOfVertex;
	private int cx = 0;
	private int cy = 0;
	private int height = 1;
}