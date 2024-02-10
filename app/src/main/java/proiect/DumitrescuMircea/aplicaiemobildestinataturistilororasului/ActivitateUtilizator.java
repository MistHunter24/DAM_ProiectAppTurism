package proiect.DumitrescuMircea.aplicaiemobildestinataturistilororasului;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.net.URI;



public class ActivitateUtilizator extends AppCompatActivity {

    private static final Integer RESULT_LOAD_IMG = 300;
    public final static String SP_NUME = "user_nume";
    public final static String SP_VARSTA = "user_varsta";
    public final static String SP_SEX = "user_sex";
//    public final static String SP_IMAGE = "user_image";

    private SharedPreferences sharedPref;

    private ImageView userImage;
    private EditText etNume;
    private EditText etVarsta;
    private EditText etSex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activitate_utilizator);

        initComponents();
        loadSharedPref();
    }

    @Override
    protected void onPause() {
        super.onPause();
        saveSharedPref();
    }

    private void initComponents() {
        ImageButton ibMain = findViewById(R.id.user_ib);
        userImage = findViewById(R.id.user_iv);
        Button loadImage = findViewById(R.id.user_btn);
        etVarsta = findViewById(R.id.user_et_varsta);
        etNume = findViewById(R.id.user_et_nume);
        etSex = findViewById(R.id.user_et_sex);

        loadImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
                photoPickerIntent.setType("image/*");
                startActivityForResult(photoPickerIntent, RESULT_LOAD_IMG);
            }
        });

        ibMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });
    }
// TODO
    private void loadSharedPref() {
        sharedPref = getSharedPreferences(ActivitateDespre.SHARED_P, Context.MODE_PRIVATE);
        etNume.setText(sharedPref.getString(SP_NUME, ""));
        etVarsta.setText(sharedPref.getString(SP_VARSTA, ""));
        etSex.setText(sharedPref.getString(SP_SEX, ""));
    }

    private void saveSharedPref() {
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(SP_NUME, etNume.getText().toString());
        editor.putString(SP_VARSTA, etVarsta.getText().toString());
        editor.putString(SP_SEX, etSex.getText().toString());
        editor.apply();
    }

    @Override
    protected void onActivityResult(int reqCode, int resultCode, Intent data) {
        super.onActivityResult(reqCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            try {
                final Uri imageUri = data.getData();
                final InputStream imageStream = getContentResolver().openInputStream(imageUri);
                final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
                userImage.setImageBitmap(selectedImage);


            } catch (FileNotFoundException e) {
                e.printStackTrace();
                Toast.makeText(getApplicationContext(),
                        getString(R.string.eroare_incarcarea_imaginii), Toast.LENGTH_LONG).show();
            }
        } else {
            Toast.makeText(getApplicationContext(),
                    getString( R.string.eroare_alegere_imagine),Toast.LENGTH_LONG).show();
        }
    }
}
