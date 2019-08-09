package com.example.tarea13;

import javax.microedition.khronos.opengles.GL10;

public class Camara {
	
	private float m[] = new float[16];
	
	/**
	 *                   u x v = Producto vectorial o producto cruz
	 * u = (u0, u1, u2) ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	 * v = (v0, v1, v2)  u x v = (u1v2 - u2v1, u2v0 - u0v2, u0v1 - u1v0)  
	 *                                          
	 */
	public void producto_vectorial(float u[], float v[], float n[]) {
		n[0] = (u[1] * v[2]) - (u[2] * v[1]);
		n[1] = (u[2] * v[0]) - (u[0] * v[2]);
		n[2] = (u[0] * v[1]) - (u[1] * v[0]);
	}
	
	/**
	 *                     |v| = Longitud de un vector o magnitud
	 * v = (x, y, z)    ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	 *                     |v| = raiz_cuadrada (x^2 + y^2 + z^2) 
	 *                                          
	 */
	public float longitud(float v[]) {
		return (float)Math.sqrt(v[0] * v[0] + v[1] * v[1] + v[2] * v[2]);
	}
	
	/**
	 *        v            u = Vector unitario o de longitud 1
	 *  u  = ---        ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	 *       |v|           u = Vector normalizado
	 *                     
	 */
	public void normaliza(float v[]) {
		float longitud = longitud(v);
		if (longitud == 0 )
			return;
		v[0] = v[0] / longitud;
		v[1] = v[1] / longitud;
		v[2] = v[2] / longitud;
	}

	void LookAt(GL10 gl, float vistaX, float vistaY, float vistaZ, float centroX,
		float centroY, float centroZ, float arribaX, float arribaY, float arribaZ) {
		
		float u[] = new float[3];
		float v[] = new float[3];
		float n[] = new float[3];

		n[0] = vistaX - centroX;
		n[1] = vistaY - centroY;
		n[2] = vistaZ - centroZ;

		normaliza(n);
		
		v[0] = arribaX;
		v[1] = arribaY;
		v[2] = arribaZ;
		
		//normaliza(v); // No es necesario!
		
		/* u = v x n */
		producto_vectorial(v, n, u);
		
		normaliza(u);
		
		/* Recalcula v: v = n x u */
		producto_vectorial(n, u, v);

		// En uno
		
//		m[0] = u[0]; m[4] = u[1]; m[ 8] = u[2];
//			m[12] = -(vistaX * u[0] + vistaY * u[1] + vistaZ * u[2]); 
//		m[1] = v[0]; m[5] = v[1]; m[ 9] = v[2]; 
//			m[13] = -(vistaX * v[0] + vistaY * v[1] + vistaZ * v[2]);
//		m[2] = n[0]; m[6] = n[1]; m[10] = n[2]; 
//			m[14] = -(vistaX * n[0] + vistaY * n[1] + vistaZ * n[2]);
//		m[3] =    0; m[7] =    0; m[11] =    0;
//			m[15] = 1;
//		gl.glMultMatrixf(m, 0);		

		// En dos pasos
		
		m[0] = u[0]; m[4] = u[1]; m[ 8] = u[2]; m[12] = 0; 
		m[1] = v[0]; m[5] = v[1]; m[ 9] = v[2]; m[13] = 0;
		m[2] = n[0]; m[6] = n[1]; m[10] = n[2]; m[14] = 0;
		m[3] = 0;    m[7] = 0;    m[11] = 0;    m[15] = 1;
		
		gl.glMultMatrixf(m, 0);
		
		gl.glTranslatef(-vistaX, -vistaY, -vistaZ);
	}
}
