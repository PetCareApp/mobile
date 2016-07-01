package cap7.com.br.petcare.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.telephony.PhoneNumberFormattingTextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import cap7.com.br.petcare.R;
import cap7.com.br.petcare.Util.Contrato;
import cap7.com.br.petcare.Util.ScriptDB;
import cap7.com.br.petcare.dao.ProprietarioDao;
import cap7.com.br.petcare.model.Proprietario;

public class ProprietarioActivity extends Activity {

    private ProprietarioDao proprietarioDao;

    private EditText editEmail;
    private EditText editTelefone;
    private EditText editNome;
    private Proprietario proprietario;
    private Button btnLogar;
    //private ImageView imgSalvar;
    private SharedPreferences preferences;
    private Cursor cursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        preferences = getSharedPreferences(Contrato.PREF_SETTINGS, 0);
        String nome = preferences.getString(Contrato.NOME_PROPRIETARIO_PREF, null);
        String email = preferences.getString(Contrato.EMAIL_PROPRIETARIO_PREF, null);
        String telefone = preferences.getString(Contrato.TELEFONE_PROPRIETARIO_PREF, null);
        int id = preferences.getInt(Contrato.ID_PROPRIETARIO_PREF, -1);

        if (nome != null && email != null){
            Intent it = new Intent(ProprietarioActivity.this, MainActivity.class);
            startActivity(it);
            finish();
        }

        proprietarioDao = new ProprietarioDao(getBaseContext());
        btnLogar = (Button)findViewById(R.id.btnLogar);
        //imgSalvar = (ImageView) findViewById(R.id.imgCadastrarProprietario);

        btnLogar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editNome = (EditText) findViewById(R.id.edtNomeProprietario);
                editEmail = (EditText) findViewById(R.id.edtEmailProprietario);
                editTelefone = (EditText) findViewById(R.id.edtTelProprietario);

                String resultado;
                proprietario = new Proprietario();
                proprietario.setNome(editNome.getText().toString());
                proprietario.setEmail(editEmail.getText().toString());
                proprietario.setTelefone(editTelefone.getText().toString());
                editTelefone.addTextChangedListener(new PhoneNumberFormattingTextWatcher());

                resultado = proprietarioDao.save(proprietario);
                cursor = proprietarioDao.carregarPerfil();
                proprietario.setId(cursor.getInt(cursor.getColumnIndexOrThrow(ScriptDB.PROPRIETARIO_ID_CURSOR)));

                SharedPreferences.Editor editor = preferences.edit();
                editor.putString(Contrato.NOME_PROPRIETARIO_PREF, proprietario.getNome());
                editor.putString(Contrato.EMAIL_PROPRIETARIO_PREF, proprietario.getEmail());
                editor.putString(Contrato.TELEFONE_PROPRIETARIO_PREF, proprietario.getTelefone());
                editor.putInt(Contrato.ID_PROPRIETARIO_PREF, proprietario.getId());
                editor.commit();

                Toast.makeText(getApplicationContext(), resultado, Toast.LENGTH_LONG).show();

                Intent it = new Intent(ProprietarioActivity.this, MainActivity.class);
                startActivity(it);
                finish();
            }
        });

    }
}
