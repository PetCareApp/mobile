package cap7.com.br.petcare.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import cap7.com.br.petcare.R;
import cap7.com.br.petcare.Util.Contrato;

public class MainActivity extends AppCompatActivity {

    private SharedPreferences preferences;
    private TextView txtNomeProprietario;
    String nome;
    int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        preferences = getSharedPreferences(Contrato.PREF_SETTINGS, 0);
         nome = preferences.getString("nomeProprietario", null);
         id = preferences.getInt("idProprietario", -1);

        txtNomeProprietario = (TextView)findViewById(R.id.proprietarioNomePerfil);
        txtNomeProprietario.setText(nome);

        txtNomeProprietario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(MainActivity.this, ConsultaPerfilActivity.class);
                it.putExtra("id", id);
                startActivity(it);
                finish();
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        nome = preferences.getString("nomeProprietario", null);
        id = preferences.getInt("idProprietario", -1);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        nome = preferences.getString("nomeProprietario", null);
        id = preferences.getInt("idProprietario", -1);
    }
}
