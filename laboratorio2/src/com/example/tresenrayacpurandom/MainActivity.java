package com.example.tresenrayacpurandom;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {
	
	private Button boton;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// todo el codigo que estaba aqui era para lanzar a pantalla el programa
		// pero como aveces vamos a querer reiniciar el juego entonces lo ponemos en un metodo
		// al que se llamara iniciar que iniciara el juego ahora y cuando presionemos el boton
		iniciar();
		
		
		
	}
	
	public void iniciar(){
		GLSurfaceView s = new Renderiza(this);	
		RelativeLayout pantalla = (RelativeLayout) getLayoutInflater().inflate(R.layout.activity_main, null);		
		pantalla.addView(s);
		
		boton = (Button) pantalla.findViewById(R.id.btnAccion);
		boton.bringToFront();
		boton.setText("Reiniciar");
		setContentView(pantalla);
		
	}
	
	public void btnAccion(View v){
		iniciar();
	}
	

}
