package com.example.ejemplo4calculadora;


import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {
	
	EditText edit1;
	EditText edit2;
	TextView texto1;
	TextView texto2;
	Button boton1;
	Button boton2;
	Button boton3;
	Button boton4;
	
	public void resetear() {
    	edit1.setText("");
    	edit2.setText("");
    }
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        edit1 = (EditText) findViewById(R.id.editText1);
        edit2 = (EditText) findViewById(R.id.editText2);
        
        texto1 = (TextView) findViewById(R.id.textView1);
        texto2 = (TextView) findViewById(R.id.textView2);
        
        boton1 = (Button) findViewById(R.id.suma);
        boton2 = (Button) findViewById(R.id.resta);
        boton3 = (Button) findViewById(R.id.multiplicacion);
        boton4 = (Button) findViewById(R.id.division);
        
        
        
        boton1.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				int res = 0;
				String a = edit1.getText().toString();
				String b = edit2.getText().toString();
				
				int num1 = Integer.parseInt(a);
				int num2 = Integer.parseInt(b);
				res = num1 + num2;
				String resultado = Integer.toString(res);
				
				texto1.setText(resultado);
				resetear();
			}
			
		});
        
        boton2.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				int res = 0;
				String a = edit1.getText().toString();
				String b = edit2.getText().toString();
				
				int num1 = Integer.parseInt(a);
				int num2 = Integer.parseInt(b);
				res = num1 - num2;
				String resultado = Integer.toString(res);
				
				texto1.setText(resultado);
				
				resetear();
			}
		});
        
        boton3.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				int res = 0;
				String a = edit1.getText().toString();
				String b = edit2.getText().toString();
				
				int num1 = Integer.parseInt(a);
				int num2 = Integer.parseInt(b);
				res = num1 * num2;
				String resultado = Integer.toString(res);
				
				texto1.setText(resultado);
				
				resetear();
			}
		});
        
        boton4.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				float resultado = 0;
				String a = edit1.getText().toString();
				String b = edit2.getText().toString();
				
				float num1 = Float.parseFloat(a);
				float num2 = Float.parseFloat(b);
//				int num1 = Integer.parseInt(a);
//				int num2 = Integer.parseInt(b);
//				int resultado =
//				float res = Float.parseFloat(resultado)
				
				if (num2!=0) {
					resultado = num1/num2;
					String cadena = Float.toString(resultado);
					if(cadena.substring(cadena.length()-2, cadena.length()).equals(".0")){
						cadena = cadena.substring(0, cadena.length()-2);
					}
					texto1.setText(cadena);
					
				}
				else{
					Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_LONG).show();
				}
				
				resetear();
			}
		});
    }

    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    
}
