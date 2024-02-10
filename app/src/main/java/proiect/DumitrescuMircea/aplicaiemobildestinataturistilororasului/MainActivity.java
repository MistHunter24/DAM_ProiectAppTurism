package proiect.DumitrescuMircea.aplicaiemobildestinataturistilororasului;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;


public class MainActivity extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initComponents();
    }

    private void initComponents() {
        Button lRestaurant = findViewById(R.id.btn_add_masa);
        Button lActivitateTuristica = findViewById(R.id.btn_add_activitate_turistica);
        Button lJson = findViewById(R.id.btn_acces_json);
        Button rapoarte = findViewById(R.id.btn_rapoarte);

        lRestaurant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ActivitateRestaurant.class);
                startActivity(intent);
            }
        });

        lActivitateTuristica.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ActivitateTuristica.class);
                startActivity(intent);
            }
        });



        rapoarte.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ActivitateRapoarte.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.app_main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        super.onOptionsItemSelected(item);
        if (item.getItemId() == R.id.main_menu_utilizator) {
            Intent intent = new Intent(getApplicationContext(), ActivitateUtilizator.class);
            startActivity(intent);
        }
        else if(item.getItemId() == R.id.main_menu_despre) {
            Intent intent = new Intent(getApplicationContext(), ActivitateDespre.class);
            startActivity(intent);
        }

        return true;
    }
}
