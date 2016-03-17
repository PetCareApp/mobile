package cap7.com.br.petcare.activity;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import cap7.com.br.petcare.R;
import cap7.com.br.petcare.Util.ScriptDB;
import cap7.com.br.petcare.dao.AnimalDao;

public class ConsultaAnimalActivity extends AppCompatActivity {
    private AnimalDao animalDao;
    private ListView lista;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consulta_animal);

        animalDao = new AnimalDao(getBaseContext());
        Cursor cursor = animalDao.carregarAnimais();

        String[] nomeCampos = new String[] {ScriptDB.ANIMAL_NOME};
        int[] idViews = new int[] {R.id.txtItemAnimal};

        SimpleCursorAdapter adaptador = new SimpleCursorAdapter(getBaseContext(), R.layout.item_animal, cursor,
                nomeCampos,idViews,0);
        lista = (ListView)findViewById(R.id.listViewAnimais);
        lista.setAdapter(adaptador);

    }
}
