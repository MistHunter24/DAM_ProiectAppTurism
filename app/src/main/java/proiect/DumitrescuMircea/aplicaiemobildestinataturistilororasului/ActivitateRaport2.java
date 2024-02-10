package proiect.DumitrescuMircea.aplicaiemobildestinataturistilororasului;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;



public class ActivitateRaport2 extends AppCompatActivity {

    List<Turist> listaTurists = new ArrayList<>();
    List<Restaurant> listaRestaurant = new ArrayList<>();

    TextView tvData;
    TextView tvNoteRestaurant;
    TextView tvNoteActivitati;
    TextView tvBilant;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activitate_raport2);

        initComponents();
    }

    private void initComponents() {
        tvData = findViewById(R.id.raport_2_tv_data);
        tvNoteRestaurant = findViewById(R.id.raport_2_tv_calorii_mese);
        tvNoteActivitati = findViewById(R.id.raport_2_tv_calorii_fitness);
        tvBilant = findViewById(R.id.raport_2_tv_bilant);
        Button btnBack = findViewById(R.id.raport_2_btn_back);
        Button btnGrafic = findViewById(R.id.raport_2_btn_grafic);

        SharedPreferences sharedPref = getSharedPreferences(ActivitateRapoarte.SHARED_P, Context.MODE_PRIVATE);
        String dataAleasa = sharedPref.getString(ActivitateRapoarte.SP_DATA_RAPORT, "");

        DatabaseController controller = DatabaseController.getInstance(getApplicationContext());
        DataRepository  dataRepository = new DataRepository(controller);

        dataRepository.open();
        listaTurists = dataRepository.bilantActivitati(dataAleasa);
        listaRestaurant = dataRepository.bilantRestaurant(dataAleasa);
        dataRepository.close();

        Float NoteRestaurant = calculeazaNoteRestaurant(listaRestaurant);
        int NoteActivitati = calculeazaNoteActivitati(listaTurists);



        Float bilant = NoteRestaurant + NoteActivitati;
        afiseazaRaport(dataAleasa, NoteRestaurant, NoteActivitati, bilant);

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ActivitateRaport2.this, ActivitateRapoarte.class);
                startActivity(intent);
            }
        });

        btnGrafic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ActivitateRaport2.this, ActivitateGrafic.class);
                startActivity(intent);
            }
        });
    }

    private void afiseazaRaport(String data, Float NoteRestaurant, int NoteActivitati, Float bilant) {

        if(NoteRestaurant == 0 && NoteActivitati == 0) {
            int color = getResources().getColor(R.color.colorRed);
            tvData.setTextColor(color);
            tvData.setAllCaps(true);
            String dataString = String.format(getString(R.string.raport_2_activitati_inexistente), data);
            tvData.setText(dataString);
        } else {
            String dataString = String.format(getString(R.string.raport_2_afisare_data), data);
            String noteMString = String.format(getString(R.string.raport_2_afisare_note_restaurant), NoteRestaurant.intValue());
            String noteFString = String.format(getString(R.string.raport_2_afisare_note_activitati), NoteActivitati);

            tvData.setText(dataString);
            tvNoteRestaurant.setText(noteMString);
            tvNoteActivitati.setText(noteFString);

            if(bilant > 2) {
                String bilantStringA = String.format(getString(R.string.raport_2_obiective_vizitate), bilant.intValue());
                int color = getResources().getColor(R.color.colorGreen);
                tvBilant.setTextColor(color);
                tvBilant.setText(bilantStringA);
            } else {
                bilant *= -1;
                String bilantStringB = String.format(getString(R.string.raport_2_obiective_nevizitate), bilant.intValue());
                int color = getResources().getColor(R.color.colorRed);
                tvBilant.setTextColor(color);
                tvBilant.setAllCaps(true);
                tvBilant.setText(bilantStringB);
            }
        }
    }

    private int calculeazaNoteActivitati(List<Turist> listaTurists) {
        int output = 0;
        if(listaTurists != null) {
            for(int i = 0; i < listaTurists.size(); i++) {
                output += listaTurists.get(i).getNotaActivitate();
            }
        }
        return output;
    }

    private float calculeazaNoteRestaurant(List<Restaurant> listaRestaurant) {
        float output = 0;
        if(listaRestaurant != null) {
            for(int i = 0; i < listaRestaurant.size(); i++) {
                float nrNote = listaRestaurant.get(i).getNotaRestaurant();
                String recenzii = listaRestaurant.get(i).getRecenzie();
                float calculNote = nrNote ;
              output += calculNote;
            }
        }
        return output;
    }

}
