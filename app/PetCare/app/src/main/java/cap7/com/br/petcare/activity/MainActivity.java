package cap7.com.br.petcare.activity;

import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Intent;
import android.content.IntentSender;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentActivity;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.BaseAdapter;
import android.widget.HeaderViewListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import cap7.com.br.petcare.R;
import cap7.com.br.petcare.Util.Contrato;
import cap7.com.br.petcare.Util.ScriptDB;
import cap7.com.br.petcare.dao.AnimalDao;
import cap7.com.br.petcare.model.Animal;

public class MainActivity extends AppCompatActivity implements
        GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    private static final int REQUEST_ERRO_PLAY_SERVICES = 1;
    private static final int REQUEST_CHECAR_GPS = 2;
    private static final String EXTRA_DIALOG = "dialog";

    Handler mHandler;
    boolean mDeveExibirDialog;
    int mTentativas;


    GoogleApiClient mGoogleApiClient;
    private SharedPreferences preferences;
    private TextView txtNomeProprietario;
    private TextView txtEmailProprietario;
    String nome;
    String email;
    int id;
    private AnimalDao animalDao;
    private Animal animal;
    //#profiile
    private Toolbar toolbar;
    private NavigationView navigationView;
    private DrawerLayout drawerLayout;
    private Menu menu;

    //#google maps API
    GoogleMap mGoogleMap;
    LatLng mOrigem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //#preferences
        preferences = getSharedPreferences(Contrato.PREF_SETTINGS, 0);
        nome = preferences.getString(Contrato.NOME_PROPRIETARIO_PREF, null);
        id = preferences.getInt(Contrato.ID_PROPRIETARIO_PREF, -1);
        email = preferences.getString(Contrato.EMAIL_PROPRIETARIO_PREF, null);
        //#end-preferences

        mHandler = new Handler();
        mDeveExibirDialog = savedInstanceState == null;

        //startando de uma localização pre-definida com API do gmaps.
        SupportMapFragment fragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mGoogleMap = fragment.getMap();
        mGoogleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        //mOrigem = new LatLng(-23.561706, -46.655981);
        mGoogleMap.setMyLocationEnabled(true);
        mGoogleApiClient = new GoogleApiClient.Builder(this).addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
       // atualizarMapa();

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //atualiza o menu lateral
        atualizarMenu();

        //Initializing NavigationView
        navigationView = (NavigationView) findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            // This method will trigger on item Click of navigation menu
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                //Checking if the item is in checked state or not, if not make it in checked state
                if (menuItem.isChecked()) menuItem.setChecked(false);
                else menuItem.setChecked(true);

                drawerLayout.closeDrawers();

                switch (menuItem.getItemId()) {
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
                        try {
                            animal = animalDao.recuperarAnimal(menuItem.getItemId());
                            Intent itAnimal = new Intent(MainActivity.this, DetalhesAnimalActivity.class);
                            itAnimal.putExtra("idAnimal", Integer.valueOf(animal.getId()));
                            itAnimal.putExtra("nomeAnimal", animal.getNome());
                            startActivity(itAnimal);
                        } catch (Exception e) {
                            Toast.makeText(getApplicationContext(), "Erro ao selecionar o menu.", Toast.LENGTH_SHORT).show();
                        } finally {
                            return true;
                        }
                }
            }
        });

        drawerLayout = (DrawerLayout) findViewById(R.id.drawer);
        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.openDrawer, R.string.closeDrawer){

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                txtNomeProprietario = (TextView) findViewById(R.id.lblProprietarioNomePerfil);
                txtNomeProprietario.setText(nome);
                txtNomeProprietario = (TextView) findViewById(R.id.lblProprietarioEmailPerfil);
                txtNomeProprietario.setText(email);
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                //#Perfil nome e email
                txtNomeProprietario = (TextView) findViewById(R.id.lblProprietarioNomePerfil);
                txtNomeProprietario.setText(nome);
                txtNomeProprietario = (TextView) findViewById(R.id.lblProprietarioEmailPerfil);
                txtNomeProprietario.setText(email);
                for (int i = 0, count = navigationView.getChildCount(); i < count; i++) {
                    final View child = navigationView.getChildAt(i);
                    if (child != null && child instanceof ListView) {
                        final ListView menuView = (ListView) child;
                        final HeaderViewListAdapter mAdapter = (HeaderViewListAdapter) menuView.getAdapter();
                        final BaseAdapter wrapped = (BaseAdapter) mAdapter.getWrappedAdapter();
                        wrapped.notifyDataSetChanged();
                    }
                }
                //#end-perfilnome e email
                //#Ao abrir o navigation fazer algo, se não quiser nada, deixar em branco.
                super.onDrawerOpened(drawerView);
            }
        };
        drawerLayout.setDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
     }

     @Override
     protected void onResume() {
         super.onResume();
         nome = preferences.getString(Contrato.NOME_PROPRIETARIO_PREF, null);
         id = preferences.getInt(Contrato.ID_PROPRIETARIO_PREF, -1);
         atualizarMenu();
     }

     @Override
     protected void onStart(){
         super.onStart();
         mGoogleApiClient.connect();
         atualizarMenu();
     }

     @Override
     protected void onRestart() {
         super.onRestart();
         nome = preferences.getString(Contrato.NOME_PROPRIETARIO_PREF, null);
         id = preferences.getInt(Contrato.ID_PROPRIETARIO_PREF, -1);
         atualizarMenu();
     }

     @Override
     protected void onStop(){
         if(mGoogleApiClient != null && mGoogleApiClient.isConnected()){
             mGoogleApiClient.disconnect();
         }
         mHandler.removeCallbacksAndMessages(null);
         super.onStop();
     }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    //Metodo que vai receber as coordenadas de onde ficarao os petshops.
    private void atualizarMapa(){
        //vai surmir quando for pegar pela localização do usuário.
        mGoogleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(mOrigem, 17.0f));
        mGoogleMap.clear();
        mGoogleMap.addMarker(new MarkerOptions()
                .position(mOrigem)
                .title("petshop x")
                .snippet("Local Atual"));
    }

    private void obterUltimaLocalizacao() {
        Location location = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
        if (location != null){
            mTentativas = 0;
            mOrigem = new LatLng(location.getLatitude(), location.getLongitude());
            atualizarMapa();
        } else if (mTentativas < 10){
            mTentativas++;
            mHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    obterUltimaLocalizacao();
                }
            }, 2000);
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState){
        super.onSaveInstanceState(outState);
        outState.putBoolean(EXTRA_DIALOG, mDeveExibirDialog);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState){
        super.onRestoreInstanceState(savedInstanceState);
        mDeveExibirDialog = savedInstanceState.getBoolean(EXTRA_DIALOG, true);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_ERRO_PLAY_SERVICES
                && resultCode == RESULT_OK){
            mGoogleApiClient.connect();
        } else if (requestCode == REQUEST_CHECAR_GPS){
            if (resultCode == RESULT_OK){
                mTentativas = 0;
                mHandler.removeCallbacksAndMessages(null);
                obterUltimaLocalizacao();
            } else {
                Toast.makeText(this, "Habilitar GPS para te localizarmos!", Toast.LENGTH_SHORT).show();
                //finish();
                //fecha a aplicacao se nao tiver o gps (remover isso)
            }
        }
    }


    @Override
    public void onConnected(Bundle bundle) {
            verificarStatusGPS();
    }

    @Override
    public void onConnectionSuspended(int i) {
        mGoogleApiClient.connect();
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        if (connectionResult.hasResolution()){
            try{
                connectionResult.startResolutionForResult(this,
                        REQUEST_ERRO_PLAY_SERVICES);
            } catch (IntentSender.SendIntentException e){
                e.printStackTrace();
            }
        } else {
            exibirMensagemDeErro(this, connectionResult.getErrorCode());
        }
    }

    private void exibirMensagemDeErro(FragmentActivity activity, final int codigoErro) {
        final String TAG = "DIALOG_ERRO_PLAY_SERVICES";
        if (getSupportFragmentManager().findFragmentByTag(TAG) == null){
            DialogFragment errorFragment = new DialogFragment(){
                @Override
                public Dialog onCreateDialog(Bundle savedInstance){
                    return GooglePlayServicesUtil.getErrorDialog(codigoErro, getActivity(), REQUEST_ERRO_PLAY_SERVICES);
                }
            };
            errorFragment.show(activity.getFragmentManager(), TAG);
        }
    }

    private void verificarStatusGPS(){
        final LocationRequest locationRequest = LocationRequest.create();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        LocationSettingsRequest.Builder locationSettingsRequest = new LocationSettingsRequest.Builder();
        locationSettingsRequest.setAlwaysShow(true);
        locationSettingsRequest.addLocationRequest(locationRequest);

        PendingResult<LocationSettingsResult> result = LocationServices.SettingsApi.checkLocationSettings(
                mGoogleApiClient, locationSettingsRequest.build()
        );
        result.setResultCallback(new ResultCallback<LocationSettingsResult>() {
            @Override
            public void onResult(LocationSettingsResult locationSettingsResult) {
                final Status status = locationSettingsResult.getStatus();
                switch (status.getStatusCode()){
                    case LocationSettingsStatusCodes.SUCCESS:
                        obterUltimaLocalizacao();
                        break;
                    case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                        if (mDeveExibirDialog){
                            try {
                                status.startResolutionForResult(MainActivity.this, REQUEST_CHECAR_GPS);
                                mDeveExibirDialog = false;
                            } catch (IntentSender.SendIntentException e){
                                e.printStackTrace();
                            }
                        }
                        break;
                    case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                        Log.wtf("ASSJ", "Erro");
                        break;
                }
            }
        });
    }

    public void atualizarMenu(){
        //Initializing NavigationView
        navigationView = (NavigationView) findViewById(R.id.navigation_view);

        //adicionando menu dinamico com os animais carregados da base.
        menu = navigationView.getMenu();

        animalDao = new AnimalDao(getBaseContext());
        final Cursor cursor = animalDao.carregarAnimais();
        menu.removeGroup(1);
        if (cursor.getCount() > 0){
            do {
                String idString =  cursor.getString(cursor.getColumnIndexOrThrow(ScriptDB.ANIMAL_ID));
                int idAnimal = (Integer.valueOf(idString));
                String nomeAnimal = cursor.getString(cursor.getColumnIndexOrThrow(ScriptDB.ANIMAL_NOME));
                menu.add(1, idAnimal, Menu.NONE, nomeAnimal).setIcon(R.drawable.ic_pets_black_24dp);
            } while (cursor.moveToNext());
        }

        for (int i = 0, count = navigationView.getChildCount(); i < count; i++) {
            final View child = navigationView.getChildAt(i);
            if (child != null && child instanceof ListView) {
                final ListView menuView = (ListView) child;
                final HeaderViewListAdapter mAdapter = (HeaderViewListAdapter) menuView.getAdapter();
                final BaseAdapter wrapped = (BaseAdapter) mAdapter.getWrappedAdapter();
                wrapped.notifyDataSetChanged();
            }
        }
    }

}
