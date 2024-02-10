package proiect.DumitrescuMircea.aplicaiemobildestinataturistilororasului;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
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



public class ActivitateTuristicaEdit extends AppCompatActivity {

    public final static String DATE_FORMAT = "dd-MM-yyyy";
    public final static String KEY_EDIT_ACTIVITATE = "keyEditActivitate";
    public final static String KEY_DELETE_ACTIVITATE = "keyDeleteActivitate";

    private Intent intent;

    private EditText etDenumireActivitate;
    private EditText etMinuteActivitate;
    private EditText etNotaActivitate;
    private EditText etDataActivitate;
    private long id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activitate_activitate_edit);

        initComponents();
        intent = getIntent();

        if(intent.hasExtra(ActivitateTuristica.KEY_TO_EDIT_ACTIVITATE_TURISTICA)) {
            Turist turist = intent.getParcelableExtra(ActivitateTuristica.KEY_TO_EDIT_ACTIVITATE_TURISTICA);
            if(turist != null) {
                updateUI(turist);
                id = turist.getIdTurist();
            }
        }
    }

    private void initComponents() {
        etDenumireActivitate = findViewById(R.id.et_activitate_edit_denumire);
        etMinuteActivitate = findViewById(R.id.et_activitate_edit_timp);
        etNotaActivitate = findViewById(R.id.et_activitate_edit_nota);
        etDataActivitate = findViewById(R.id.et_activitate_edit_data);
        Button btnSalveaza = findViewById(R.id.btn_activitate_edit_save);
        Button btnAnuleaza = findViewById(R.id.btn_activitate_edit_cancel);
        Button btnSterge = findViewById(R.id.btn_activitate_edit_sterge);

        btnSalveaza.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(validareInput()) {
                    Turist turist = creareObiectActivitate();
                    intent.putExtra(KEY_EDIT_ACTIVITATE, turist);
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

    private AlertDialog dialogStergere()
    {
        AlertDialog alertDialog = new AlertDialog.Builder(this)
                .setTitle(getString(R.string.btn_sterge))
                .setMessage(getString(R.string.alerta_stergere_dialog))

                .setPositiveButton
                        (getString(R.string.btn_sterge), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        if(validareInput()) {
                            Turist turist = creareObiectActivitate();
                            intent.putExtra(KEY_DELETE_ACTIVITATE, turist);
                            setResult(RESULT_FIRST_USER, intent);
                            finish();
                        }
                    }
                })

                .setNegativeButton
                        (getString(R.string.btn_anuleaza), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        dialog.dismiss();
                    }
                })
                .create();

        return alertDialog;
    }

    private Turist creareObiectActivitate() {
        String denumireActivitate = etDenumireActivitate.getText().toString();
        Integer minuteActivitate = Integer.parseInt(etMinuteActivitate.getText().toString());
        Integer notaActivitate = Integer.parseInt(etNotaActivitate.getText().toString());
        Date dataActivitate = null;
        try {
            dataActivitate = new SimpleDateFormat
                    (DATE_FORMAT, Locale.US).parse(etDataActivitate.getText().toString());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return new Turist(denumireActivitate, notaActivitate, minuteActivitate, dataActivitate, id);
    }

    private void updateUI(Turist turist) {
        if(turist.getDenumireActivitate() != null) {
            etDenumireActivitate.setText(turist.getDenumireActivitate());
        }
        if(turist.getDataActivitatii() != null) {
            etDataActivitate.setText
                    (new SimpleDateFormat(DATE_FORMAT, Locale.US).format(turist.getDataActivitatii()));
        }
        if(turist.getNotaActivitate() != null) {
            etNotaActivitate.setText(String.valueOf(turist.getNotaActivitate()));
        }
        if(turist.getDurataActivitate() != null) {
            etMinuteActivitate.setText(String.valueOf(turist.getDurataActivitate()));
        }
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
        else if(Integer.parseInt(notaIntrodusa) < 1) {
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
