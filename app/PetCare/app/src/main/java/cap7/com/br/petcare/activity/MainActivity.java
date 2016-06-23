package cap7.com.br.petcare.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import cap7.com.br.petcare.R;
import cap7.com.br.petcare.Util.Contrato;

public class MainActivity extends AppCompatActivity {

    private SharedPreferences preferences;
    private TextView txtNomeProprietario;
    private TextView txtEmailProprietario;
    String nome;
    String email;
    int id;

    //profile
    private Toolbar toolbar;
    private NavigationView navigationView;
    private DrawerLayout drawerLayout;

    //google maps API
    GoogleMap mGoogleMap;
    LatLng mOrigem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //preferences
        preferences = getSharedPreferences(Contrato.PREF_SETTINGS, 0);
        nome = preferences.getString(Contrato.NOME_PROPRIETARIO_PREF, null);
        id = preferences.getInt(Contrato.ID_PROPRIETARIO_PREF, -1);
        email = preferences.getString(Contrato.EMAIL_PROPRIETARIO_PREF, null);
        //end-preferences

        //startando de uma localização pre-definida com API do gmaps.
        SupportMapFragment fragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mGoogleMap = fragment.getMap();
        mGoogleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        mOrigem = new LatLng(-23.561706, -46.655981);
        atualizarMapa();
        // Initializing Toolbar and setting it as the actionbar
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //Initializing NavigationView
        navigationView = (NavigationView) findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            // This method will trigger on item Click of navigation menu
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                //Checking if the item is in checked state or not, if not make it in checked state
                if (menuItem.isChecked()) menuItem.setChecked(false);
                else menuItem.setChecked(true);

                //Closing drawer on item click
                drawerLayout.closeDrawers();

                //Check to see which item was being clicked and perform appropriate action
                switch (menuItem.getItemId()) {
                    //Replacing the main content with ContentFragment Which is our Inbox View;
                    case R.id.profile_proprietario:
                        Toast.makeText(getApplicationContext(), "PERFIL SELECIONADO :)", Toast.LENGTH_SHORT).show();
                        Intent itPerfil = new Intent(MainActivity.this, ConsultaPerfilActivity.class);
                        itPerfil.putExtra("id", id);
                        startActivity(itPerfil);
                        finish();
                        return true;
                    case R.id.profile_pets:
                        Toast.makeText(getApplicationContext(), "PETS SELECIONADO :)", Toast.LENGTH_SHORT).show();
                        Intent itMeusPets = new Intent(MainActivity.this, ConsultaAnimalActivity.class);
                        itMeusPets.putExtra("id", id);
                        startActivity(itMeusPets);
                        finish();
                        return true;
                    case R.id.profile_add_pets:
                        Toast.makeText(getApplicationContext(), "ADD PETS SELECIONADO :)", Toast.LENGTH_SHORT).show();
                        Intent itAddPets = new Intent(MainActivity.this, AnimalActivity.class);
                        itAddPets.putExtra("id", id);
                        startActivity(itAddPets);
                        finish();
                        return true;
                    default:
                        Toast.makeText(getApplicationContext(), "Erro na seleção do menu.", Toast.LENGTH_SHORT).show();
                        return true;
                }
            }
        });

        drawerLayout = (DrawerLayout) findViewById(R.id.drawer);
        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.openDrawer, R.string.closeDrawer){

            @Override
            public void onDrawerClosed(View drawerView) {
                // Code here will be triggered once the drawer closes as we dont want anything to happen so we leave this blank
                super.onDrawerClosed(drawerView);
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                //Perfil nome e email
                txtNomeProprietario = (TextView) findViewById(R.id.lblProprietarioNomePerfil);
                txtNomeProprietario.setText(nome);
                txtNomeProprietario = (TextView) findViewById(R.id.lblProprietarioEmailPerfil);
                txtNomeProprietario.setText(email);
                //end-perfilnome e email
                //Ao abrir o navigation fazer algo, se não quiser nada, deixar em branco.
                super.onDrawerOpened(drawerView);
            }
        };
        //Setting the actionbarToggle to drawer layout
        drawerLayout.setDrawerListener(actionBarDrawerToggle);
        //calling sync state is necessay or else your icon wont show up
        actionBarDrawerToggle.syncState();

         // ACABA AQUI COMECA AQUI
      /*   txtNomeProprietario = (TextView) findViewById(R.id.lblProprietarioNomePerfil);
         txtNomeProprietario.setText(nome);

         txtNomeProprietario.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 Intent it = new Intent(MainActivity.this, ConsultaPerfilActivity.class);
                 it.putExtra("id", id);
                 startActivity(it);
                 finish();
             }
         }); */

     }

     @Override
     protected void onResume() {
         super.onResume();
         nome = preferences.getString(Contrato.NOME_PROPRIETARIO_PREF, null);
         id = preferences.getInt(Contrato.ID_PROPRIETARIO_PREF, -1);
     }

     @Override
     protected void onRestart() {
         super.onRestart();
         nome = preferences.getString(Contrato.NOME_PROPRIETARIO_PREF, null);
         id = preferences.getInt(Contrato.ID_PROPRIETARIO_PREF, -1);
     }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    //Metodo que vai receber as coordenadas de onde ficarao os petshops.
    private void atualizarMapa(){
        mGoogleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(mOrigem, 17.0f));
        mGoogleMap.addMarker(new MarkerOptions()
        .position(mOrigem)
        .title("petshop av. paulista")
        .snippet("São Paulo"));
    }



 }
