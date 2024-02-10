package proiect.DumitrescuMircea.aplicaiemobildestinataturistilororasului;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.github.mikephil.charting.data.BarEntry;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/*
*
* clasa include metode privind lucrul cu baza de date
*
* */

public class DataRepository implements Constante {

    private SQLiteDatabase database;
    private DatabaseController databaseController;

    public DataRepository(DatabaseController databaseController) {
        this.databaseController = databaseController;
    }

    public void open() {
        try {
            database = databaseController.getWritableDatabase();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void close() {
        try {
            database.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // metode INSERT
    public long insertRestaurant(Restaurant restaurant) {
        if(restaurant == null) {
            return -1;
        }
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_RESTAURANT_DENUMIRE, restaurant.getNumeRestaurant());
        contentValues.put(COLUMN_MASA_PERIOADA, restaurant.getPerioadaMesei());
        contentValues.put(COLUMN_MASA_RECENZIE, restaurant.getRecenzie());
        contentValues.put(COLUMN_MASA_NOTA, restaurant.getNotaRestaurant());

        String dataMesei = new SimpleDateFormat
                (ActivitateRestaurantAdd.DATE_FORMAT, Locale.US).format(restaurant.getDataMesei());
        String dataReversed = dataReverse(dataMesei);
        contentValues.put(COLUMN_MASA_DATA, dataReversed);

        return database.insert(
                TABLE_NAME_RESTAURANT,
                null,
                contentValues);
    }

    public long insertTurist(Turist turist) {
        if(turist == null) {
            return -1;
        }
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_ACTIVITATE_DENUMIRE, turist.getDenumireActivitate());
        contentValues.put(COLUMN_ACTIVITATE_NOTA, turist.getNotaActivitate());
        contentValues.put(COLUMN_ACTIVITATE_MINUTE, turist.getDurataActivitate());

        String dataActivitate = new SimpleDateFormat
                (ActivitateTuristicaAdd.DATE_FORMAT, Locale.US).format(turist.getDataActivitatii());
        String dataReversed = dataReverse(dataActivitate);
        contentValues.put(COLUMN_ACTIVITATE_DATA, dataReversed);

        return database.insert(
                TABLE_NAME_ACTIVITATI,
                null,
                contentValues);
    }

    // metode UPDATE
    public int updateRestaurant(Restaurant restaurant) {
        if(restaurant == null) {
            return -1;
        }

        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_RESTAURANT_DENUMIRE, restaurant.getNumeRestaurant());
        contentValues.put(COLUMN_MASA_PERIOADA, restaurant.getPerioadaMesei());
        contentValues.put(COLUMN_MASA_RECENZIE, restaurant.getRecenzie());
        contentValues.put(COLUMN_MASA_NOTA, restaurant.getNotaRestaurant());

        String dataMesei = new SimpleDateFormat
                (ActivitateRestaurantAdd.DATE_FORMAT, Locale.US).format(restaurant.getDataMesei());
        String dataReversed = dataReverse(dataMesei);
        contentValues.put(COLUMN_MASA_DATA, dataReversed);
        Long id = restaurant.getIdRestaurant();

        return database.update(
                TABLE_NAME_RESTAURANT,
                contentValues,
                COLUMN_RESTAURANT_ID + " = ?",
                new String[]{String.valueOf(id)});
    }

    public int updateTurist(Turist turist) {
        if(turist == null) {
            return -1;
        }

        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_ACTIVITATE_DENUMIRE, turist.getDenumireActivitate());
        contentValues.put(COLUMN_ACTIVITATE_NOTA, turist.getNotaActivitate());
        contentValues.put(COLUMN_ACTIVITATE_MINUTE, turist.getDurataActivitate());

        String dataActivitate = new SimpleDateFormat
                (ActivitateTuristicaAdd.DATE_FORMAT, Locale.US).format(turist.getDataActivitatii());
        String dataReversed = dataReverse(dataActivitate);
        contentValues.put(COLUMN_ACTIVITATE_DATA, dataReversed);
        Long id = turist.getIdTurist();

        return database.update(
                TABLE_NAME_ACTIVITATI,
                contentValues,
                COLUMN_ACTIVITATE_ID + " = ?",
                new String[]{String.valueOf(id)});
    }

    // metode DELETE
    public int deleteRestaurant(Long id) {
        return database.delete(
                TABLE_NAME_RESTAURANT,
                COLUMN_RESTAURANT_ID + " = ?",
                new String[]{String.valueOf(id)});
    }

    public  int deleteTurist(Long id) {
        return database.delete(
                TABLE_NAME_ACTIVITATI,
                COLUMN_ACTIVITATE_ID + " = ?",
                new String[]{String.valueOf(id)});
    }

    // metode SELECT *
    public List<Restaurant> findAllRestaurant() {

        List<Restaurant> lista = new ArrayList<>();
        Cursor cursor = database
                .query(TABLE_NAME_RESTAURANT,
                        null,
                        null,
                        null,
                        null,
                        null,
                        COLUMN_MASA_DATA);

        while (cursor.moveToNext()) {
            Long id = cursor.getLong(cursor.getColumnIndex(COLUMN_RESTAURANT_ID));
            String numeRestaurant = cursor.getString(cursor.getColumnIndex(COLUMN_RESTAURANT_DENUMIRE));
            String perioadaMesei = cursor.getString(cursor.getColumnIndex(COLUMN_MASA_PERIOADA));
            String recenzie = cursor.getString(cursor.getColumnIndex(COLUMN_MASA_RECENZIE));
            Float nota = cursor.getFloat(cursor.getColumnIndex(COLUMN_MASA_NOTA));

            Date dataMesei = null;
            String extractedDate = cursor.getString(cursor.getColumnIndex(COLUMN_MASA_DATA));
            String correctDate = dataCorrect(extractedDate);
            try {
                dataMesei = new SimpleDateFormat(ActivitateRestaurantAdd.DATE_FORMAT, Locale.US)
                        .parse(correctDate);
            } catch (ParseException e) {
                e.printStackTrace();
            }

            Restaurant restaurant = new Restaurant(numeRestaurant, perioadaMesei, recenzie, nota, dataMesei, id);
            lista.add(restaurant);
        }
        cursor.close();
        return lista;
    }

    public List<Turist> findAllTurist() {

        List<Turist> lista = new ArrayList<>();
        Cursor cursor = database
                .query(TABLE_NAME_ACTIVITATI,
                        null,
                        null,
                        null,
                        null,
                        null,
                        COLUMN_ACTIVITATE_DATA);

        while (cursor.moveToNext()) {
            Long id = cursor.getLong(cursor.getColumnIndex(COLUMN_ACTIVITATE_ID));
            String denumireActivitate = cursor.getString(cursor.getColumnIndex(COLUMN_ACTIVITATE_DENUMIRE));
            Integer notaActivitate = cursor.getInt(cursor.getColumnIndex(COLUMN_ACTIVITATE_NOTA));
            Integer durata = cursor.getInt(cursor.getColumnIndex(COLUMN_ACTIVITATE_MINUTE));

            Date dataActivitatii = null;
            String extractedDate = cursor.getString(cursor.getColumnIndex(COLUMN_ACTIVITATE_DATA));
            String correctDate = dataCorrect(extractedDate);
            try {
                dataActivitatii = new SimpleDateFormat(ActivitateTuristicaAdd.DATE_FORMAT, Locale.US)
                        .parse(correctDate);
            } catch (ParseException e) {
                e.printStackTrace();
            }

            Turist turist = new Turist(denumireActivitate, notaActivitate, durata, dataActivitatii, id);
            lista.add(turist);
        }
        cursor.close();
        return lista;
    }


    public List<Turist> raportLunaCurenta(String searchString) {

        List<Turist> lista = new ArrayList<>();

        String reversedSearchString = dataReverse(searchString);

        searchString = reversedSearchString.substring(0, 7);

        String sqlString =
                "SELECT * FROM " +
                        TABLE_NAME_ACTIVITATI +
                " WHERE " +
                        COLUMN_ACTIVITATE_DATA +
                " LIKE '" +
                searchString +
                "%'";

        Cursor cursor = database.rawQuery(sqlString, null);

        while (cursor.moveToNext()) {
            Long id = cursor.getLong(cursor.getColumnIndex(COLUMN_ACTIVITATE_ID));
            String denumireActivitate = cursor.getString(cursor.getColumnIndex(COLUMN_ACTIVITATE_DENUMIRE));
            Integer notaActivitate = cursor.getInt(cursor.getColumnIndex(COLUMN_ACTIVITATE_NOTA));
            Integer durata = cursor.getInt(cursor.getColumnIndex(COLUMN_ACTIVITATE_MINUTE));

            Date dataActivitatii = null;
            String extractedDate = cursor.getString(cursor.getColumnIndex(COLUMN_ACTIVITATE_DATA));
            String correctDate = dataCorrect(extractedDate);
            try {
                dataActivitatii = new SimpleDateFormat(ActivitateTuristicaAdd.DATE_FORMAT, Locale.US)
                        .parse(correctDate);
            } catch (ParseException e) {
                e.printStackTrace();
            }

            Turist turist = new Turist(denumireActivitate, notaActivitate, durata, dataActivitatii, id);
            lista.add(turist);
        }
        cursor.close();
        return lista;
    }
//                                                    aici

    public List<Restaurant> bilantRestaurant(String searchString) {
        List<Restaurant> lista = new ArrayList<>();

        String reversedSearchString = dataReverse(searchString);

        String sqlString =
                "SELECT * FROM " +
                        TABLE_NAME_RESTAURANT +
                        " WHERE " +
                        COLUMN_MASA_DATA +
                        " = '" +
                        reversedSearchString +
                        "'";

        Cursor cursor = database.rawQuery(sqlString, null);

        while (cursor.moveToNext()) {
            Long id = cursor.getLong(cursor.getColumnIndex(COLUMN_RESTAURANT_ID));
            String numeMancare = cursor.getString(cursor.getColumnIndex(COLUMN_RESTAURANT_DENUMIRE));
            String recenzie = cursor.getString(cursor.getColumnIndex(COLUMN_MASA_RECENZIE));
            Float nota = cursor.getFloat(cursor.getColumnIndex(COLUMN_MASA_NOTA));
            String perioadaMesei = cursor.getString(cursor.getColumnIndex(COLUMN_MASA_PERIOADA));

            Date dataMesei = null;
            String extractedDate = cursor.getString(cursor.getColumnIndex(COLUMN_MASA_DATA));
            String correctDate = dataCorrect(extractedDate);
            try {
                dataMesei = new SimpleDateFormat(ActivitateTuristicaAdd.DATE_FORMAT, Locale.US)
                        .parse(correctDate);
            } catch (ParseException e) {
                e.printStackTrace();
            }

            Restaurant restaurant = new Restaurant(numeMancare, perioadaMesei, recenzie, nota, dataMesei, id);
            lista.add(restaurant);
        }
        cursor.close();
        return lista;
    }

    public List<Turist> bilantActivitati(String searchString) {
        List<Turist> lista = new ArrayList<>();

        String reversedSearchString = dataReverse(searchString);

        String sqlString =
                "SELECT * FROM " +
                        TABLE_NAME_ACTIVITATI +
                        " WHERE " +
                        COLUMN_ACTIVITATE_DATA +
                        " = '" +
                        reversedSearchString +
                        "'";

        Cursor cursor = database.rawQuery(sqlString, null);

        while (cursor.moveToNext()) {
            Long id = cursor.getLong(cursor.getColumnIndex(COLUMN_ACTIVITATE_ID));
            String denumireActivitate = cursor.getString(cursor.getColumnIndex(COLUMN_ACTIVITATE_DENUMIRE));
            Integer notaActivitate = cursor.getInt(cursor.getColumnIndex(COLUMN_ACTIVITATE_NOTA));
            Integer durata = cursor.getInt(cursor.getColumnIndex(COLUMN_ACTIVITATE_MINUTE));

            Date dataActivitatii = null;
            String extractedDate = cursor.getString(cursor.getColumnIndex(COLUMN_ACTIVITATE_DATA));
            String correctDate = dataCorrect(extractedDate);
            try {
                dataActivitatii = new SimpleDateFormat(ActivitateTuristicaAdd.DATE_FORMAT, Locale.US)
                        .parse(correctDate);
            } catch (ParseException e) {
                e.printStackTrace();
            }

            Turist turist = new Turist(denumireActivitate, notaActivitate, durata, dataActivitatii, id);
            lista.add(turist);
        }
        cursor.close();
        return lista;
    }

    // metoda care selecteaza si proceseaza datele pentru BarChart
    public ArrayList<BarEntry> barChart(String dataCautata) {
        float[] calculMese = new float[4];

        String reversedSearchString = dataReverse(dataCautata);
        String sqlString =
                "SELECT * FROM " +
                        TABLE_NAME_RESTAURANT +
                        " WHERE " +
                        COLUMN_MASA_DATA +
                        " = '" +
                        reversedSearchString +
                        "'";
        Cursor cursor = database.rawQuery(sqlString, null);

        while (cursor.moveToNext()) {
            Integer recenzieRestaurant = cursor.getInt(cursor.getColumnIndex(COLUMN_MASA_RECENZIE));
            Float notaRestaurant = cursor.getFloat(cursor.getColumnIndex(COLUMN_MASA_NOTA));
            String perioadaMesei = cursor.getString(cursor.getColumnIndex(COLUMN_MASA_PERIOADA));
            Float calcul = notaRestaurant * recenzieRestaurant;

            switch (perioadaMesei) {
                case "Mic dejun": {
                    calculMese[0] += calcul;
                    break;
                }
                case "Pranz": {
                    calculMese[1] += calcul;
                    break;
                }
                case "Cina": {
                    calculMese[2] += calcul;
                    break;
                }
                case "Gustare": {
                    calculMese[3] += calcul;
                    break;
                }
            }
        }
        cursor.close();
        ArrayList<BarEntry> valori = new ArrayList<>();
        valori.add(new BarEntry(0, calculMese[0]));
        valori.add(new BarEntry(1, calculMese[1]));
        valori.add(new BarEntry(2, calculMese[2] ));
        valori.add(new BarEntry(3, calculMese[3]));
        return valori;
    }


    public String dataReverse(String input) {
        if (input == null) {
            return input;
        }
        String output = "";
        output += input.substring(6);
        output += input.substring(2, 6);
        output += input.substring(0, 2);

        return output;
    }

    public String dataCorrect(String input) {
        if (input == null) {
            return input;
        }
        String output = "";
        output += input.substring(8);
        output += input.substring(4, 8);
        output += input.substring(0, 4);

        return output;
    }
}
