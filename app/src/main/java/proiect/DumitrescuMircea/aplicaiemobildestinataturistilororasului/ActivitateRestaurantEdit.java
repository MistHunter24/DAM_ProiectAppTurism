package proiect.DumitrescuMircea.aplicaiemobildestinataturistilororasului;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
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

import static proiect.DumitrescuMircea.aplicaiemobildestinataturistilororasului.R.id.et_restaurant_edit_denumire;



public class ActivitateRestaurantEdit extends AppCompatActivity {

    public final static String DATE_FORMAT = "dd-MM-yyyy";
    public final static String KEY_EDIT_RESTAURANT = "keyEditRestaurant";
    public final static String KEY_DELETE_RESTAURANT = "keyDeleteRestaurant";

    private Intent intent;

    private EditText etDenumireRestaurant;
    private RadioGroup rgPerioada;
    private EditText etRecenzie;
    private Spinner spnNota;
    private EditText etDataMesei;
    private long id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activitate_restaurant_edit);

        initComponents();
        intent = getIntent();

        if(intent.hasExtra(ActivitateRestaurant.KEY_TO_EDIT_RESTAURANT)) {
            Restaurant restaurant = intent.getParcelableExtra(ActivitateRestaurant.KEY_TO_EDIT_RESTAURANT);
            if(restaurant != null) {
                updateUI(restaurant);
                id = restaurant.getIdRestaurant();
            }
        }
    }

    private void initComponents() {
        etDenumireRestaurant = findViewById(et_restaurant_edit_denumire);
        rgPerioada = findViewById(R.id.rg_restaurant_edit_perioada);
        etRecenzie = findViewById(R.id.et_restaurant_edit_recenzie);
        spnNota = findViewById(R.id.spn_restaurant_edit_nota);
        etDataMesei = findViewById(R.id.et_restaurant_edit_data);
        Button btnSalveaza = findViewById(R.id.btn_restaurant_edit_save);
        Button btnAnuleaza = findViewById(R.id.btn_restaurant_edit_cancel);
        Button btnSterge = findViewById(R.id.btn_restaurant_edit_sterge);

        ArrayAdapter<CharSequence> spnNotaAdapter =
                ArrayAdapter.createFromResource(
                        getApplicationContext(),
                        R.array.spn_restaurant_add_nota,
                        R.layout.support_simple_spinner_dropdown_item);
        spnNota.setAdapter(spnNotaAdapter);


        btnSalveaza.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(validareInput()) {
                    Restaurant restaurant = creareObiectRestaurant();
                    intent.putExtra(KEY_EDIT_RESTAURANT, restaurant);
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

        btnSterge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog optiuneStergere = dialogStergere();
                optiuneStergere.show();
            }
        });
    }

    // crearea dialogului pentru confirmarea stergerii
    private AlertDialog dialogStergere()
    {
        AlertDialog alertDialog = new AlertDialog.Builder(this)
                .setTitle(getString(R.string.btn_sterge))
                .setMessage(getString(R.string.alerta_stergere_dialog))

                .setPositiveButton(getString(R.string.btn_sterge), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        if(validareInput()) {
                            Restaurant restaurant = creareObiectRestaurant();
                            intent.putExtra(KEY_DELETE_RESTAURANT, restaurant);
                            setResult(RESULT_FIRST_USER, intent);
                            finish();
                        }
                    }
                })

                .setNegativeButton(getString(R.string.btn_anuleaza), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        dialog.dismiss();
                    }
                })
                .create();

        return alertDialog;
    }

    // functie pentru update al widgeturilor cu datele intrarii
    private void updateUI(Restaurant restaurant) {
        if(restaurant.getNumeRestaurant() != null) {
            etDenumireRestaurant.setText(restaurant.getNumeRestaurant());
        }
        if(restaurant.getDataMesei() != null) {
            etDataMesei.setText(new SimpleDateFormat(
                    DATE_FORMAT, Locale.US).format(restaurant.getDataMesei()));
        }
        if(restaurant.getRecenzie() != null) {
            etRecenzie.setText(String.valueOf(restaurant.getRecenzie()));
        }
        if(restaurant.getPerioadaMesei() != null) {
            addPerioada(restaurant);
        }
        if(restaurant.getNotaRestaurant() != null) {
            addNota(restaurant);
        }
    }

    private void addNota(Restaurant restaurant) {
        String[] spnArr = getResources().getStringArray(R.array.spn_values);
        String value = restaurant.getNotaRestaurant().toString();
        String searchValue = prelucrareNota(value);
        for (int i = 0; i < spnArr.length; i++) {
            if (spnArr[i].equalsIgnoreCase(searchValue)) {
                spnNota.setSelection(i);
            }
        }
    }

    private String prelucrareNota(String value) {
        if(Character.toString(value.charAt(2)).equals("0")) {
            return value.substring(0, 1);
        }
        else if(Character.toString(value.charAt(2)).equals("5")) {
            return value;
        }
        return "1";
    }

    private void addPerioada(Restaurant restaurant) {
        switch (restaurant.getPerioadaMesei()) {
            case "Mic dejun" : {
                rgPerioada.check(R.id.perioada_edit_micdejun);
                break;
            }
            case "Pranz" : {
                rgPerioada.check(R.id.perioada_edit_pranz);
                break;
            }
            case "Cina" : {
                rgPerioada.check(R.id.perioada_edit_cina);
                break;
            }
            case "Gustare" : {
                rgPerioada.check(R.id.perioada_edit_gustare);
                break;
            }
        }
    }

    private Restaurant creareObiectRestaurant() {
        String numeMancare = etDenumireRestaurant.getText().toString();
        RadioButton optiuneRB = findViewById(rgPerioada.getCheckedRadioButtonId());
        String perioadaMesei = optiuneRB.getText().toString();
        String recenzie = etRecenzie.getText().toString();
        Float nota = Float.parseFloat(spnNota.getSelectedItem().toString());
        Date dataMesei = null;
        try {
            dataMesei = new SimpleDateFormat
                    (DATE_FORMAT, Locale.US).parse(etDataMesei.getText().toString());
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return new Restaurant(numeMancare, perioadaMesei, recenzie, nota, dataMesei, id);
    }

    private boolean validareInput() {
        int anulCurent = Calendar.getInstance().get(Calendar.YEAR);
        String dataIntrodusa = etDataMesei.getText().toString();
        String recenzieIntrodusa = etRecenzie.getText().toString();

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
        else if(Integer.parseInt(recenzieIntrodusa) > 5) {
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
