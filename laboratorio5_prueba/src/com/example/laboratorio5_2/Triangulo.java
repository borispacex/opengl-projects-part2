package com.example.laboratorio5_2;



import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;

import javax.microedition.khronos.opengles.GL10;

public class Triangulo {
    /**
     * 3 ---------
     2
     * /| /|
     * / | / |
     * 7 --------- 6 |
     * | | | |
     * | 0 ------|-- 1
     * | / | /
     * |/ |/
     * 4 --------- 5
     */
    private float vertices[] = new float[] {
// Frente

// Arriba`	
            -1, 1, 1, // 1 0
            1, 1, 1, // 2 1
            1, 1, -1, // 3 2
            -1, 1, -1, // 4 3

            1,1,1, //5  4
            -1,1,1, //6  5
            0,-1,0,  //7  6

            -1,1,1, // 8  7
            -1,1,-1, //9  8
            0,-1,0,  //10  9

            1,1,1,  //11  10
            1,1,-1, //12  11
            0,-1,0, //13   12

            -1,1,-1,  //13
            1,1,-1,  //14
            0,-1,0  //15



    };
    byte maxColor = (byte)255;
    private byte colores[] = new byte[] {
            // Frente- lila


// Arriba - verde
            0, maxColor, 0, maxColor, // 1 0
            0, maxColor, 0, maxColor, // 2 1
            0, maxColor, 0, maxColor, // 3 2
            0, maxColor, 0, maxColor, // 4 3

            maxColor, maxColor, 0, maxColor, // 5 4
            maxColor, maxColor, 0, maxColor, // 6 5
            maxColor, maxColor, 0, maxColor, // 7 6

            0, maxColor, maxColor, maxColor, // 0 8
            0, maxColor, maxColor, maxColor, // 4 9
            0, maxColor, maxColor, maxColor, // 7 10

            maxColor, 0, 0, maxColor, // 5 12
            maxColor, 0, 0, maxColor, // 1 13
            maxColor, 0, 0, maxColor, // 2 14

            0, 0, maxColor, maxColor, // 0 16
            0, 0, maxColor, maxColor, // 1 17
            0, 0, maxColor, maxColor // 5 18



    };
    private short indices[] = new short [] {
            0, 1, 2, 0, 2, 3, // Frente
            4,5,6,
            7,8,9,
            10,11,12,
            13,14,15


    };
    private FloatBuffer bufVertices;
    private ByteBuffer bufColores;
    private ShortBuffer bufIndices;
    public Triangulo() {
        /* Lee los vértices */
        ByteBuffer bufByte = ByteBuffer.allocateDirect(vertices.length * 4);
        bufByte.order(ByteOrder.nativeOrder()); // Utiliza el orden de byte nativo
        bufVertices = bufByte.asFloatBuffer(); // Convierte de byte a float
        bufVertices.put(vertices);
        bufVertices.rewind(); // puntero al principio del buffer
        /* Lee los colores */
        bufColores = ByteBuffer.allocateDirect(colores.length);
        bufColores.put(colores);
        bufColores.position(0); // puntero al principio del buffer
        /* Lee los indices */
        bufByte = ByteBuffer.allocateDirect(indices.length * 2);
        bufByte.order(ByteOrder.nativeOrder()); // Utiliza el orden de byte nativo
        bufIndices = bufByte.asShortBuffer(); // Convierte de byte a short
        bufIndices.put(indices);
        bufIndices.rewind(); // puntero al principio del buffer
    }
    public void dibuja(GL10 gl) {
        /* Se activa el arreglo de vértices */
        gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
        /* Se activa el arreglo de colores */
        gl.glEnableClientState(GL10.GL_COLOR_ARRAY);
        /* Se especifica los datos del arreglo de vértices */
        gl.glVertexPointer(3, GL10.GL_FLOAT, 0, bufVertices);
        /* Se especifica los datos del arreglo de colores */
        gl.glColorPointer(4, GL10.GL_UNSIGNED_BYTE, 0, bufColores);
        /* Se dibuja el cubo */
        gl.glDrawElements(GL10.GL_TRIANGLES, indices.length,
                GL10.GL_UNSIGNED_SHORT, bufIndices);
        /* Se desactiva el arreglo de vértices */
        gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
        /* Se desactiva el arreglo de colores */
        gl.glDisableClientState(GL10.GL_COLOR_ARRAY);
    }
}
