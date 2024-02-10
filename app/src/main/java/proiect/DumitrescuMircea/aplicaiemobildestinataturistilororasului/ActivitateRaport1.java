package proiect.DumitrescuMircea.aplicaiemobildestinataturistilororasului;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;



public class ActivitateRaport1 extends AppCompatActivity {

    List<Turist> listaTurists = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activitate_raport1);

        DatabaseController controller = DatabaseController.getInstance(getApplicationContext());
        DataRepository  dataRepository = new DataRepository(controller);

        dataRepository.open();
        listaTurists = dataRepository.raportLunaCurenta(getDataCurenta());
        dataRepository.close();

        initComponents();
    }

    private void initComponents() {
        ListView lvFitness = findViewById(R.id.raport_1_lv);
        Button btnBack = findViewById(R.id.raport_1_btn);

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ActivitateRaport1.this, ActivitateRapoarte.class);
                startActivity(intent);
            }
        });

        LayoutInflater layoutInflater = getLayoutInflater();
        AdapterRaport  customAdapter = new AdapterRaport
                (ActivitateRaport1.this, R.layout.lv_raport, listaTurists, layoutInflater);
        lvFitness.setAdapter(customAdapter);
        customAdapter.notifyDataSetChanged();
    }

    // functie pentru citirea si formatarea datei curente
    private String getDataCurenta() {
        Date currentTime = Calendar.getInstance().getTime();
        return new SimpleDateFormat(ActivitateRapoarte.DATE_FORMAT, Locale.getDefault()).format(currentTime);
    }
}
