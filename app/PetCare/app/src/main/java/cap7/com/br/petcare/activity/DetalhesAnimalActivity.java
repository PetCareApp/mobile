package cap7.com.br.petcare.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import cap7.com.br.petcare.R;
import cap7.com.br.petcare.dao.AnimalDao;
import cap7.com.br.petcare.model.Animal;

public class DetalhesAnimalActivity extends AppCompatActivity {


    private Animal animal;
    private AnimalDao animalDao;

    private TextView txtNomeAnimal;
    private TextView txtRacaAnimal;
    private TextView txtCorAnimal;
    private Button btnAlterarPet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalhes_animal);

        btnAlterarPet = (Button) findViewById(R.id.btnPetAlterar);

        animalDao = new AnimalDao(getBaseContext());
        animal = new Animal();

        Intent i = getIntent();
        String nomeAnimal = i.getStringExtra("nomeAnimal");
        int idAnimal = i.getIntExtra("idAnimal", -1);
        animal.setId(idAnimal);
        animal = animalDao.recuperarAnimal(animal.getId());

        txtNomeAnimal = (TextView) findViewById(R.id.txtDetalhePetNome);
        txtCorAnimal = (TextView) findViewById(R.id.txtDetalhePetCor);
        txtRacaAnimal = (TextView) findViewById(R.id.txtDetalhePetRaca);

        txtNomeAnimal.setText(animal.getNome());
        txtCorAnimal.setText(animal.getCor());
        txtRacaAnimal.setText(animal.getRaca());

        btnAlterarPet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent itPet = new Intent(DetalhesAnimalActivity.this, AlterarAnimalActivity.class);
                itPet.putExtra("animal", animal);
                startActivity(itPet);
                finish();
            }
        });
    }
}
