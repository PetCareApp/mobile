package cap7.com.br.petcare.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import cap7.com.br.petcare.R;
import cap7.com.br.petcare.Util.Contrato;
import cap7.com.br.petcare.Util.ScriptDB;
import cap7.com.br.petcare.dao.AnimalDao;
import cap7.com.br.petcare.model.AnimalCursorAdapter;

public class ConsultaAnimalActivity extends AppCompatActivity  {
    private AnimalDao animalDao;
    private ListView lista;
    private SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consulta_animal);
        preferences = getSharedPreferences(Contrato.PREF_SETTINGS, 0);
        animalDao = new AnimalDao(getBaseContext());
        final Cursor cursor = animalDao.carregarAnimais();

        String[] nomeCampos = new String[] {ScriptDB.ANIMAL_NOME};
        int[] idViews = new int[] {R.id.txtItemAnimal};

        lista = (ListView)findViewById(R.id.listViewAnimal);

         final SimpleCursorAdapter adaptador = new SimpleCursorAdapter(getBaseContext(), R.layout.item_animal, cursor,
                nomeCampos,idViews, 0);

       //
        AnimalCursorAdapter adapter = new AnimalCursorAdapter(getBaseContext(),cursor,0);

        lista.setAdapter(adapter);
        adaptador.notifyDataSetChanged();
        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                cursor.moveToPosition(position);
                int codigo = cursor.getInt(cursor.getColumnIndexOrThrow(ScriptDB.ANIMAL_ID));
                String nome = cursor.getString(cursor.getColumnIndexOrThrow(ScriptDB.ANIMAL_NOME));

                //armazenar no prefs
                SharedPreferences.Editor editor = preferences.edit();
                editor.putString(Contrato.ID_ANIMAL_PREF, String.valueOf(codigo));
                editor.putString(Contrato.NOME_ANIMAL_PREF, nome);
                editor.commit();

                Intent itAnimal = new Intent(ConsultaAnimalActivity.this, DetalhesAnimalActivity.class);
                itAnimal.putExtra("idAnimal", Integer.valueOf(codigo));
                itAnimal.putExtra("nomeAnimal", nome);
                startActivity(itAnimal);
                finish();
            }
        });
    }

}
