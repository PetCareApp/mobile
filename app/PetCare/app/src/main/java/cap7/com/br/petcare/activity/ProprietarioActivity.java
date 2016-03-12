package cap7.com.br.petcare.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import cap7.com.br.petcare.R;
import cap7.com.br.petcare.Util.Contrato;
import cap7.com.br.petcare.dao.ProprietarioDao;
import cap7.com.br.petcare.model.Proprietario;
import cap7.com.br.petcare.Util.ScriptDB;

public class ProprietarioActivity extends AppCompatActivity {

    private ProprietarioDao proprietarioDao;

    private EditText editEmail;
    //private EditText editSenha;
    private EditText editNome;
    private Proprietario proprietario;
    private Button btnSalvar;
    private SharedPreferences preferences;
    private Cursor cursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_proprietario);

        preferences = getSharedPreferences(Contrato.PREF_SETTINGS, 0);
        String nome = preferences.getString("nomeProprietario", null);
        String email = preferences.getString("emailProprietario", null);
        int id = preferences.getInt("idProprietario", -1);

        if (nome != null && email != null){
            Intent it = new Intent(ProprietarioActivity.this, MainActivity.class);
            startActivity(it);
            finish();
        }

        proprietarioDao = new ProprietarioDao(getBaseContext());
        btnSalvar = (Button)findViewById(R.id.btnSalvarProprietario);
      btnSalvar.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {

              editNome = (EditText) findViewById(R.id.editTextNomeProprietario);
              editEmail = (EditText) findViewById(R.id.editTextEmailProprietario);
              //editSenha = (EditText) findViewById(R.id.editTxtSenhaProprietario);
              String resultado;
              proprietario = new Proprietario();
              proprietario.setNome(editNome.getText().toString());
              proprietario.setEmail(editEmail.getText().toString());
              //proprietario.setSenha(editSenha.getText().toString());

              resultado = proprietarioDao.save(proprietario);
              cursor = proprietarioDao.carregarPerfil();
              proprietario.setId(cursor.getInt(cursor.getColumnIndexOrThrow(ScriptDB.PROPRIETARIO_ID_CURSOR)));

              SharedPreferences.Editor editor = preferences.edit();
              editor.putString("nomeProprietario", proprietario.getNome());
              editor.putString("emailProprietario", proprietario.getEmail());
              editor.putInt("idProprietario", proprietario.getId());
              editor.commit();

              Toast.makeText(getApplicationContext(), resultado, Toast.LENGTH_LONG).show();

              Intent it = new Intent(ProprietarioActivity.this, MainActivity.class);
              startActivity(it);
              finish();
          }
      });



    }


}
