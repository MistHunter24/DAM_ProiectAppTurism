package proiect.DumitrescuMircea.aplicaiemobildestinataturistilororasului;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;


public class ActivitateTuristica extends AppCompatActivity {

    public final static int REQUEST_CODE_ADD_ACTIVITATE_TURISTICA = 200;
    public final static int REQUEST_CODE_EDIT_ACTIVITATE_TURISTICA = 201;
    public final static String KEY_TO_EDIT_ACTIVITATE_TURISTICA = "keyActivitateTuristicaToBeEdited";

    public final static String OPKEY_INSERT_ACTIVITATE_TURISTICA = "opkeyInsertActivitateTuristica";
    public final static String OPKEY_UPDATE_ACTIVITATE_TURISTICA = "opkeyUpdateActivitateTuristica";
    public final static String OPKEY_DELETE_ACTIVITATE_TURISTICA = "opkeyDeleteActivitateTuristica";

    LayoutInflater layoutInflater;
    private ListView lvActivitateTuristica;
    private List<Turist> listaActivitateTuristica = new ArrayList<>();

    private DataRepository dataRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_activitate_turistica);

        DatabaseController controller = DatabaseController.getInstance(getApplicationContext());
        dataRepository = new DataRepository(controller);
        dataRepository.open();
        listaActivitateTuristica = dataRepository.findAllTurist();
        dataRepository.close();

        initComponents();
    }

    private void initComponents() {
        FloatingActionButton fabAddTurist = findViewById(R.id.fab_add_activitate_turistica);
        lvActivitateTuristica = findViewById(R.id.lv_activitate_turistica);

        fabAddTurist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent
                        (ActivitateTuristica.this, ActivitateTuristicaAdd.class);
                startActivityForResult(intent, REQUEST_CODE_ADD_ACTIVITATE_TURISTICA);
            }
        });

        lvActivitateTuristica.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent
                        (ActivitateTuristica.this, ActivitateTuristicaEdit.class);
                intent.putExtra(KEY_TO_EDIT_ACTIVITATE_TURISTICA, listaActivitateTuristica.get(position));
                startActivityForResult(intent, REQUEST_CODE_EDIT_ACTIVITATE_TURISTICA);
            }
        });

        layoutInflater = getLayoutInflater();
        refreshList(listaActivitateTuristica);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_ADD_ACTIVITATE_TURISTICA
                && resultCode == RESULT_OK && data != null) {

            Turist turist = data.getParcelableExtra(ActivitateTuristicaAdd.KEY_ADD_Activity);
            dbOps(OPKEY_INSERT_ACTIVITATE_TURISTICA, turist);
        } else if (requestCode == REQUEST_CODE_EDIT_ACTIVITATE_TURISTICA
                && resultCode == RESULT_OK && data != null) {

            Turist turist = data.getParcelableExtra(ActivitateTuristicaEdit.KEY_EDIT_ACTIVITATE);
            dbOps(OPKEY_UPDATE_ACTIVITATE_TURISTICA, turist);
        } else if (requestCode == REQUEST_CODE_EDIT_ACTIVITATE_TURISTICA
                && resultCode == RESULT_FIRST_USER && data != null) {

            Turist turist = data.getParcelableExtra(ActivitateTuristicaEdit.KEY_DELETE_ACTIVITATE);
            dbOps(OPKEY_DELETE_ACTIVITATE_TURISTICA, turist);
        }
    }

    public void refreshList(List<Turist> listaTurists) {
        AdapterActivitateTuristica customAdapter = new AdapterActivitateTuristica
                (ActivitateTuristica.this,
                        R.layout.lv_activitate_turistica,
                        listaActivitateTuristica,
                        layoutInflater);
        lvActivitateTuristica.setAdapter(customAdapter);
        customAdapter.notifyDataSetChanged();
    }

    public void dbOps(String opKey, Turist turist) {
        if (turist != null) {
            listaActivitateTuristica.clear();
            dataRepository.open();
            switch (opKey) {
                case OPKEY_INSERT_ACTIVITATE_TURISTICA:
                    dataRepository.insertTurist(turist);
                    break;
                case OPKEY_UPDATE_ACTIVITATE_TURISTICA:
                    dataRepository.updateTurist(turist);
                    break;
                case OPKEY_DELETE_ACTIVITATE_TURISTICA:
                    dataRepository.deleteTurist(turist.getIdTurist());
                    break;
            }
            listaActivitateTuristica = dataRepository.findAllTurist();
            dataRepository.close();
            refreshList(listaActivitateTuristica);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.app_add_activitate_turistica_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.add_activitate_turistica_menu_principal: {
                if (item.isEnabled()) {
                    Intent intent = new Intent(ActivitateTuristica.this, MainActivity.class);
                    startActivity(intent);
                }
                return true;
            }
            case R.id.add_activitate_turistica_menu_lista_mese: {
                if (item.isEnabled()) {
                    Intent intent = new Intent(ActivitateTuristica.this, ActivitateRestaurant.class);
                    startActivity(intent);
                }
                return true;
            }
            case R.id.add_activitate_turistica_menu_rapoarte: {
                if (item.isEnabled()) {
                    Intent intent = new Intent(ActivitateTuristica.this, ActivitateRapoarte.class);
                    startActivity(intent);
                }
                return true;
            }
            case R.id.add_activitate_turistica_menu_utilizator: {
                if (item.isEnabled()) {
                    Intent intent = new Intent(ActivitateTuristica.this, ActivitateUtilizator.class);
                    startActivity(intent);
                }
                return true;
            }
            case R.id.add_activitate_turistica_menu_despre: {
                if (item.isEnabled()) {
                    Intent intent = new Intent(ActivitateTuristica.this, ActivitateDespre.class);
                    startActivity(intent);
                }
                return true;
            }
            default: {
                return super.onOptionsItemSelected(item);
            }
        }
    }
}
