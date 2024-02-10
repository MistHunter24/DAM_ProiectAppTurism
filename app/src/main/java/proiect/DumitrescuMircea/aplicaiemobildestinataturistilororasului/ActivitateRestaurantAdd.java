package proiect.DumitrescuMircea.aplicaiemobildestinataturistilororasului;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class ActivitateRestaurantAdd extends AppCompatActivity {

    public final static String DATE_FORMAT = "dd-MM-yyyy";
    public final static String KEY_ADD_RESTAURANT = "keyAddRestaurant";
    private Intent intent;

    private EditText etDenumireRestaurant;
    private RadioGroup rgPerioada;
    private EditText etRecenzie;
    private Spinner spnNota;
    private EditText etDataVizitei;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activitate_restaurant_add);

        initComponents();
        intent = getIntent();
    }

    private void initComponents() {
        etDenumireRestaurant = findViewById(R.id.et_restaurant_add_denumire);
        rgPerioada = findViewById(R.id.rg_restaurant_add_perioada);
        etRecenzie = findViewById(R.id.et_restaurant_add_activitate_turistica);
        spnNota = findViewById(R.id.spn_restaurant_add_nota);
        etDataVizitei = findViewById(R.id.et_restaurant_add_data);
        Button btnSalveaza = findViewById(R.id.btn_restaurant_add_save);
        Button btnAnuleaza = findViewById(R.id.btn_restaurant_add_cancel);

        ArrayAdapter<CharSequence> spnNotaAdapter =
                ArrayAdapter.createFromResource(
                        ActivitateRestaurantAdd.this,
                        R.array.spn_restaurant_add_nota,
                        R.layout.support_simple_spinner_dropdown_item);
        spnNota.setAdapter(spnNotaAdapter);

        btnSalveaza.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(validareInput()) {
                    Restaurant restaurant = creareObiectRestaurant();
                    intent.putExtra(KEY_ADD_RESTAURANT, restaurant);
                    setResult(RESULT_OK, intent);
                    finish();
                }
            }
        });

        btnAnuleaza.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(RESULT_CANCELED, intent);
                finish();
            }
        });
    }

    private Restaurant creareObiectRestaurant() {
        String numeRestaurant = etDenumireRestaurant.getText().toString();
        RadioButton optiuneRB = findViewById(rgPerioada.getCheckedRadioButtonId());
        String perioadaMesei = optiuneRB.getText().toString();
        String recenzieRestaurant = etRecenzie.getText().toString();
        Float notaRestaurant = Float.parseFloat(spnNota.getSelectedItem().toString());
        Date dataMesei = null;
        try {
            dataMesei = new SimpleDateFormat
                    (DATE_FORMAT, Locale.US).parse(etDataVizitei.getText().toString());
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return new Restaurant(numeRestaurant, perioadaMesei, recenzieRestaurant, notaRestaurant, dataMesei);
    }

    private boolean validareInput() {
        int anulCurent = Calendar.getInstance().get(Calendar.YEAR);
        String dataIntrodusa = etDataVizitei.getText().toString();
        String recenzieIntrodusa = etRecenzie.getText().toString();
        Float notaRestaurant = Float.parseFloat(spnNota.getSelectedItem().toString());

        if(etDenumireRestaurant.getText().toString().trim().isEmpty()) {
            Toast.makeText(getApplicationContext(),
                    getString(R.string.validare_nume_activitate), Toast.LENGTH_LONG).show();
            return false;
        }

        if(recenzieIntrodusa.trim().isEmpty()) {
            Toast.makeText(getApplicationContext(),
                    getString(R.string.validare_nota_activitate), Toast.LENGTH_LONG).show();
            return false;
        }
        else if(notaRestaurant > 5 ) {
            Toast.makeText(getApplicationContext(),
                    getString(R.string.validare_numar_pozitiv_nota), Toast.LENGTH_LONG).show();
            return false;
        }

        if(dataIntrodusa.trim().isEmpty() || !validareData(dataIntrodusa) ||
                dataIntrodusa.trim().length() != 10) {
            Toast.makeText(getApplicationContext(),
                    getString(R.string.validare_data_activitate), Toast.LENGTH_LONG).show();
            return false;
        }
        else {
            int anIntrodus = Integer.parseInt(dataIntrodusa.substring(6));
            int lunaIntrodusa = Integer.parseInt(dataIntrodusa.substring(3, 5));
            int ziIntrodusa = Integer.parseInt(dataIntrodusa.substring(0, 2));

            if(anIntrodus > anulCurent || (anIntrodus + 1) < anulCurent) {
                Toast.makeText(getApplicationContext(),
                        getString(R.string.validare_an_curent), Toast.LENGTH_LONG).show();
                return false;
            }
            else if(ziIntrodusa < 1 || ziIntrodusa > 31 || lunaIntrodusa < 1 || lunaIntrodusa > 12 ) {
                Toast.makeText(getApplicationContext(),
                        getString(R.string.validare_zi_luna), Toast.LENGTH_LONG).show();
                return false;
            }
        }

        return true;
    }

    private boolean validareData(String inputData) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DATE_FORMAT, Locale.US);
        try {
            simpleDateFormat.parse(inputData);
        } catch (ParseException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
}
