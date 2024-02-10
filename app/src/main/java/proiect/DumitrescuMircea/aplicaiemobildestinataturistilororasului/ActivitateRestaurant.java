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


public class ActivitateRestaurant extends AppCompatActivity {

    public static final int REQUEST_CODE_ADD_RESTAURANT = 100;
    public static final int REQUEST_CODE_EDIT_RESTAURANT = 101;
    public final static String KEY_TO_EDIT_RESTAURANT = "keyRestaurantToBeEdited";

    public final static String OPKEY_INSERT_RESTAURANT = "opkeyInsertRestaurant";
    public final static String OPKEY_UPDATE_RESTAURANT = "opkeyUpdateRestaurant";
    public final static String OPKEY_DELETE_RESTAURANT = "opkeyDeleteRestaurant";

    LayoutInflater layoutInflater;
    private ListView lvRestaurant;
    private List<Restaurant> listaRestaurant = new ArrayList<>();

    private DataRepository dataRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_mese);

        DatabaseController controller = DatabaseController.getInstance(getApplicationContext());
        dataRepository = new DataRepository(controller);

        dataRepository.open();
        listaRestaurant = dataRepository.findAllRestaurant();
        dataRepository.close();

        initComponents();
    }

    private void initComponents() {
        FloatingActionButton fabAddRestaurant = findViewById(R.id.fab_add_restaurant);
        lvRestaurant = findViewById(R.id.lv_mese);

        fabAddRestaurant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent
                        (ActivitateRestaurant.this, ActivitateRestaurantAdd.class);
                startActivityForResult(intent, REQUEST_CODE_ADD_RESTAURANT);
            }
        });

        lvRestaurant.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent
                        (ActivitateRestaurant.this, ActivitateRestaurantEdit.class);
                intent.putExtra(KEY_TO_EDIT_RESTAURANT, listaRestaurant.get(position));
                startActivityForResult(intent, REQUEST_CODE_EDIT_RESTAURANT);
            }
        });

        layoutInflater = getLayoutInflater();
        refreshList(listaRestaurant);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_ADD_RESTAURANT
                && resultCode == RESULT_OK && data != null) {

            Restaurant restaurant = data.getParcelableExtra(ActivitateRestaurantAdd.KEY_ADD_RESTAURANT);
            dbOps(OPKEY_INSERT_RESTAURANT, restaurant);
        } else if (requestCode == REQUEST_CODE_EDIT_RESTAURANT
                && resultCode == RESULT_OK && data != null) {

            Restaurant restaurant = data.getParcelableExtra(ActivitateRestaurantEdit.KEY_EDIT_RESTAURANT);
            dbOps(OPKEY_UPDATE_RESTAURANT, restaurant);
        } else if (requestCode == REQUEST_CODE_EDIT_RESTAURANT
                && resultCode == RESULT_FIRST_USER && data != null) {

            Restaurant restaurant = data.getParcelableExtra(ActivitateRestaurantEdit.KEY_DELETE_RESTAURANT);
            dbOps(OPKEY_DELETE_RESTAURANT, restaurant);
        }
    }

    public void refreshList(List<Restaurant> listaMese) {
        AdapterRestaurant customAdapter = new AdapterRestaurant
                (ActivitateRestaurant.this,
                        R.layout.lv_restaurant,
                        listaRestaurant,
                        layoutInflater);
        lvRestaurant.setAdapter(customAdapter);
        customAdapter.notifyDataSetChanged();
    }

    public void dbOps(String opKey, Restaurant restaurant) {
        if (restaurant != null) {
            listaRestaurant.clear();
            dataRepository.open();
            switch (opKey) {
                case OPKEY_INSERT_RESTAURANT:
                    dataRepository.insertRestaurant(restaurant);
                    break;
                case OPKEY_UPDATE_RESTAURANT:
                    dataRepository.updateRestaurant(restaurant);
                    break;
                case OPKEY_DELETE_RESTAURANT:
                    dataRepository.deleteRestaurant(restaurant.getIdRestaurant());
                    break;
            }
            listaRestaurant = dataRepository.findAllRestaurant();
            dataRepository.close();
            refreshList(listaRestaurant);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.app_add_restaurant_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.add_restaurant_menu_principal: {
                if (item.isEnabled()) {
                    Intent intent = new Intent(ActivitateRestaurant.this, MainActivity.class);
                    startActivity(intent);
                }
                return true;
            }
            case R.id.add_restaurant_menu_lista_fitness: {
                if (item.isEnabled()) {
                    Intent intent = new Intent(ActivitateRestaurant.this, ActivitateTuristica.class);
                    startActivity(intent);
                }
                return true;
            }
            case R.id.add_restaurant_menu_rapoarte: {
                if (item.isEnabled()) {
                    Intent intent = new Intent(ActivitateRestaurant.this, ActivitateRapoarte.class);
                    startActivity(intent);
                }
                return true;
            }
            case R.id.add_restaurant_menu_utilizator: {
                if (item.isEnabled()) {
                    Intent intent = new Intent(ActivitateRestaurant.this, ActivitateUtilizator.class);
                    startActivity(intent);
                }
                return true;
            }
            case R.id.add_restaurant_menu_despre: {
                if (item.isEnabled()) {
                    Intent intent = new Intent(ActivitateRestaurant.this, ActivitateDespre.class);
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
