package cap7.com.br.petcare.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;

import cap7.com.br.petcare.R;
import cap7.com.br.petcare.dao.AnimalDao;
import cap7.com.br.petcare.model.Animal;

/**
 * Created by Virginia on 28/02/2016.
 */
public class AnimalActivity extends AppCompatActivity {

    private AnimalDao animalDao;

    private EditText editNome;
    private Spinner spinSexo;
    private Spinner spinEspecie;
    private EditText editRaca;
    private EditText editCor;
    private Button btnSalvar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animal_cadastrar);

        animalDao = new AnimalDao(getBaseContext());

        editNome = (EditText) findViewById(R.id.editPetNome);
        spinSexo = (Spinner) findViewById(R.id.spinPetSexo);
        spinEspecie = (Spinner) findViewById(R.id.spinPetEspecie);
        editRaca = (EditText) findViewById(R.id.editPetRaca);
        editCor = (EditText) findViewById(R.id.editPetCor);
        btnSalvar = (Button) findViewById(R.id.btnPetSalvar);

        List<String> especieList = new ArrayList<String>();
        especieList.add(Animal.Especie.CACHORRO.toString());
        especieList.add(Animal.Especie.GATO.toString());

        List<String> sexoList = new ArrayList<String>();
        sexoList.add(Animal.Sexo.FEMEA.toString());
        sexoList.add(Animal.Sexo.MACHO.toString());

        ArrayAdapter<String> especieAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, especieList);
        especieAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinEspecie.setAdapter(especieAdapter);

        ArrayAdapter<String> sexoAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, sexoList);
        sexoAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinSexo.setAdapter(sexoAdapter);

    }

    public void salvarAnimal(View view) {
        Animal animal = new Animal();
        animal.setNome(editNome.getText().toString());
        //animal.setEspecie(Animal.Especie.valueOf(spinEspecie.getSelectedItem().toString()));
        //animal.setSexo(Animal.Sexo.valueOf(spinSexo.getSelectedItem().toString()));
        animal.setRaca(editRaca.getText().toString());
        animal.setCor(editCor.getText().toString());

        animalDao.save(animal);
    }

}
