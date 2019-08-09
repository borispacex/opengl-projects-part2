package com.example.laboratorio7_prueba2;

/**
 * Vector3
 * 
 * Clase que realiza las operaciones vectoriales.
 * 
 * @author Jhonny Felipez
 * @version 1.0 13/05/2014
 * 
 */
class Vector3 {
	double x, y, z;
	
	/**
	* Construye una nuevo Vector.
	*/
	public Vector3() {
		x = y = z = 0;
	}

	/**
	* Construye una nuevo Vector.
	*/
	public Vector3(double x, double y, double z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	/**
	 * Retorna la coordenada x del Vector.
	 * 
	 * @return x
	 */
	public double getX() {
		return x;
	}

	/**
	 * Retorna la coordenada y del Vector.
	 * 
	 * @return y
	 */
	public double getY() {
		return y;
	}

	/**
	 * Retorna la coordenada z del Vector.
	 * 
	 * @return z
	 */
	public double getZ() {
		return z;
	}
	
    /**
     *                   u = Suma de vectores
     *  u = v1 + v2     ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
     *                   u = (v1.x + v2.x, v1.y + v2.y, v1.z + v2.z)
     */
    public Vector3 mas(Vector3 v2) {
    	return (new Vector3(this.x + v2.x, this.y + v2.y, this.z + v2.z));
    }
	
	/**
     *                   u = Resta de vectores
     *  u = v1 - v2     ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
     *                   u = (v1.x - v2.x, v1.y - v2.y, v1.z - v2.z)
     */
	public Vector3 menos(Vector3 v2) {
        return (new Vector3(this.x - v2.x, this.y - v2.y, this.z - v2.z));
    }

	/**
	 *                      u x v = Producto vectorial o producto cruz
	 * u = (u.x, u.y, u.z) ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	 * v = (v.x, v.y, v.z)  u x v = (u.y * v.z - u.z * v.y,   
	 *                               u.z * v.x - u.x * v.z,
	 *                               u.x * v.y - u.y * v.x)                  
	 */
	public Vector3 producto_vectorial(Vector3 v2) {
		Vector3 r = new Vector3();
        r.x = (this.y * v2.z) - (this.z * v2.y);
        r.y = (this.z * v2.x) - (this.x * v2.z);
        r.z = (this.x * v2.y) - (this.y * v2.x);
        return r;
    }

	/**
	 *                      u . v = Producto escalar o producto punto
	 * u = (u.x, u.y, u.z) ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	 * v = (v.x, v.y, v.z)  u . v = u.x v.x + u.y v.y + u.z v.z
	 *                                          
	 */
    public double producto_escalar(Vector3 v2) {
        return (this.x * v2.x) + (this.y * v2.y) + (this.z + v2.z);
    }

	/**
	 *                     |v| = Longitud de un vector o magnitud
	 * v = (x, y, z)    ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	 *                     |v| = raiz_cuadrada (x^2 + y^2 + z^2) 
	 *                                          
	 */
    public double longitud() {
        return Math.sqrt(x * x + y * y + z * z);
    }
    
    /**
     *        v            u = Vector unitario o de longitud 1
     *  u  = ---        ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
     *       |v|           u = Vector normalizado
     *                     
     */
    public void normaliza() {
    	double longitud = longitud();
    	if (longitud > 0 ) {
	    	x = x / longitud;
	    	y = y / longitud;
	    	z = z / longitud;
    	}
    }
    
	/**
	 *        3
     *       /\
     *      /  \
     *   v /    \
     *    /      \
     *   /________\
     *  1    u     2
     */
 	public static Vector3 normal(Vector3 v1, Vector3 v2, Vector3 v3) {
 		Vector3 u = new Vector3(); // vector u
 		Vector3 v = new Vector3(); // vector v
 		Vector3 n = new Vector3(); // vector n

 	    /* Calcula los vectores u y v */
 		u = v2.menos(v1);
 		v = v3.menos(v1);

 	    /* n = u x v */
 	    n = u.producto_vectorial(v);
 	    
 	    /* Normaliza */
 	    n.normaliza();
 	    
 	    return n;
 	}

	public String toString() {
		return "Vector3 [x=" + x + ", y=" + y + ", z=" + z + "]";
	}
}