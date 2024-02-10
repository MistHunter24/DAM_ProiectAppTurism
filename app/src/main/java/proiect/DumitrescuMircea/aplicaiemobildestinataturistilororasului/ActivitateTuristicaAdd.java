package proiect.DumitrescuMircea.aplicaiemobildestinataturistilororasului;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;



public class ActivitateTuristicaAdd extends AppCompatActivity {

    public final static String DATE_FORMAT = "dd-MM-yyyy";
    public final static String KEY_ADD_Activity = "keyAddActivity";
    private Intent intent;

    private EditText etDenumireActivitate;
    private EditText etDurataActivitate;
    private EditText etNotaActivitate;
    private EditText etDataActivitate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activitate_add);

        initComponents();
        intent = getIntent();
    }

    private void initComponents() {
        etDenumireActivitate = findViewById(R.id.et_add_denumire);
        etDurataActivitate = findViewById(R.id.et_add_timp);
        etNotaActivitate = findViewById(R.id.et_add_nota);
        etDataActivitate = findViewById(R.id.et_add_data);
        Button btnSalveaza = findViewById(R.id.btn_add_save);
        Button btnAnuleaza = findViewById(R.id.btn_add_cancel);

        btnSalveaza.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(validareInput()) {
                    Turist turist = creareObiectActivitateTuristica();
                    intent.putExtra(KEY_ADD_Activity, turist);
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

    private Turist creareObiectActivitateTuristica() {
        String denumireActivitate = etDenumireActivitate.getText().toString();
        Integer minuteActivitate = Integer.parseInt(etDurataActivitate.getText().toString());
        Integer notaActivitate = Integer.parseInt(etNotaActivitate.getText().toString());
        Date dataActivitate = null;
        try {
            dataActivitate = new SimpleDateFormat(DATE_FORMAT, Locale.US).parse(etDataActivitate.getText().toString());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return new Turist(denumireActivitate, notaActivitate, minuteActivitate, dataActivitate);
    }

    private boolean validareInput() {
        int anulCurent = Calendar.getInstance().get(Calendar.YEAR);
        String dataIntrodusa = etDataActivitate.getText().toString();
        String notaIntrodusa = etNotaActivitate.getText().toString();

        if(etDenumireActivitate.getText().toString().isEmpty()) {
            Toast.makeText(getApplicationContext(),
                    getString(R.string.validare_nume_activitate), Toast.LENGTH_LONG).show();
        }

        if(notaIntrodusa.trim().isEmpty()) {
            Toast.makeText(getApplicationContext(),
                    getString(R.string.validare_nota_activitate), Toast.LENGTH_LONG).show();
            return false;
        }
        else if(Integer.parseInt(notaIntrodusa) > 5) {
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
