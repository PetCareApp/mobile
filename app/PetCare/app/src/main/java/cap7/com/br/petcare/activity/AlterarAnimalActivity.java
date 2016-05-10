package cap7.com.br.petcare.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import cap7.com.br.petcare.R;
import cap7.com.br.petcare.Util.Contrato;
import cap7.com.br.petcare.dao.AnimalDao;
import cap7.com.br.petcare.model.Animal;

public class AlterarAnimalActivity extends AppCompatActivity {

    private EditText editNome;
    private EditText editRaca;
    private EditText editCor;

    private Button btnAlterarPet;

    private AnimalDao mAnimalDao;
    private Animal mAnimal;

    int idAnimal;

    private SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animal_alterar);

        mAnimal = new Animal();
        mAnimalDao = new AnimalDao(getBaseContext());

        Intent itPet = getIntent();
        mAnimal = (Animal) itPet.getSerializableExtra("animal");

        idAnimal = mAnimal.getId();
        editNome = (EditText) findViewById(R.id.editPetAltNome);
        editRaca = (EditText) findViewById(R.id.editPetAltRaca);
        editCor = (EditText) findViewById(R.id.editPetAltCor);

        editNome.setText(mAnimal.getNome());
        editCor.setText(mAnimal.getCor());
        editRaca.setText(mAnimal.getRaca());

        btnAlterarPet = (Button) findViewById(R.id.btnPetAltSalvar);
        btnAlterarPet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAnimalDao.update(mAnimal.getId(), editNome.getText().toString(), editCor.getText().toString(),
                        editRaca.getText().toString());

                mAnimal = new Animal();
                mAnimal.setId(idAnimal);
                mAnimal.setNome(editNome.getText().toString());
                mAnimal.setCor(editCor.getText().toString());
                mAnimal.setRaca(editRaca.getText().toString());

                preferences = getSharedPreferences(Contrato.PREF_SETTINGS, 0);
                SharedPreferences.Editor editor = preferences.edit();
                editor.putInt(Contrato.ID_ANIMAL_PREF, mAnimal.getId());
                editor.putString(Contrato.NOME_ANIMAL_PREF, mAnimal.getNome());
                editor.putString(Contrato.COR_ANIMAL_PREF, mAnimal.getCor());
                editor.putString(Contrato.RACA_ANIMAL_PREF, mAnimal.getRaca());
                editor.commit();

                Intent it = new Intent(AlterarAnimalActivity.this, DetalhesAnimalActivity.class);
                it.putExtra("idAnimal", mAnimal.getId());
                startActivity(it);
                finish();

            }
        });


    }
}
