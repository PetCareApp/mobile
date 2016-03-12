package cap7.com.br.petcare.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import cap7.com.br.petcare.R;
import cap7.com.br.petcare.Util.Contrato;
import cap7.com.br.petcare.dao.ProprietarioDao;
import cap7.com.br.petcare.model.Proprietario;
import cap7.com.br.petcare.Util.ScriptDB;

public class ConsultaPerfilActivity extends AppCompatActivity {
   //private ListView lista;
    int idProprietario;

    ProprietarioDao proprietarioDao;
    Proprietario proprietario;

    EditText edtNome;
    EditText edtEmail;
    Button btnAlterar;

    Cursor cursor;
    SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consulta_perfil);

      /*  ProprietarioDao proprietarioDao = new ProprietarioDao(getBaseContext());
        Cursor cursor = proprietarioDao.carregarPerfil();

        String[] nomeCampos = new String[] {ScriptDB.PROPRIETARIO_NOME};
        int[] idViews = new int[] {R.id.textViewNomeProprietario};

        SimpleCursorAdapter adaptador = new SimpleCursorAdapter(getBaseContext(), R.layout.item_perfil, cursor,
                nomeCampos,idViews,0);
        lista = (ListView)findViewById(R.id.listViewProprietario);
        lista.setAdapter(adaptador); */

        idProprietario = this.getIntent().getIntExtra("id", -1);
        proprietarioDao = new ProprietarioDao(getBaseContext());


        edtNome = (EditText) findViewById(R.id.editTextNomeProprietarioAlterar);
        edtEmail = (EditText) findViewById(R.id.editTextEmailProprietarioAlterar);
        btnAlterar = (Button) findViewById(R.id.btnSalvarProprietarioAlterar);

        cursor = proprietarioDao.retornarProprietario(idProprietario);
        edtNome.setText(cursor.getString(cursor.getColumnIndexOrThrow(ScriptDB.PROPRIETARIO_NOME)));
        edtEmail.setText(cursor.getString(cursor.getColumnIndexOrThrow(ScriptDB.PROPRIETARIO_EMAIL)));

        btnAlterar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                proprietarioDao.update(idProprietario, edtNome.getText().toString() ,edtEmail.getText().toString());

                proprietario = new Proprietario();
                proprietario.setId(idProprietario);
                proprietario.setNome(edtNome.getText().toString());
                proprietario.setEmail(edtEmail.getText().toString());

                preferences = getSharedPreferences(Contrato.PREF_SETTINGS, 0);
                SharedPreferences.Editor editor = preferences.edit();
                editor.putInt(Contrato.ID_PROPRIETARIO_PREF, proprietario.getId());
                editor.putString(Contrato.NOME_PROPRIETARIO_PREF, proprietario.getNome());
                editor.putString(Contrato.EMAIL_PROPRIETARIO_PREF, proprietario.getEmail());
                editor.commit();

                Log.d("JR", proprietario.getId() + "");
                Log.d("JR", proprietario.getNome());

                Intent it = new Intent(ConsultaPerfilActivity.this, MainActivity.class);
                startActivity(it);
                finish();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
    }
}
