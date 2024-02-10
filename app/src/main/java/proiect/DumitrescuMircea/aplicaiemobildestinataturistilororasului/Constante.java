package proiect.DumitrescuMircea.aplicaiemobildestinataturistilororasului;

public interface Constante {

    // cosntante pentru JSON:

    String HTTP_TAG_IMAGINE = "imagine";
    String HTTP_TAG_NUME = "nume";
    String HTTP_TAG_NOTA = "nota";
    String HTTP_TAG_RECENZIE = "recenzie";

    // constante pentru baza de date:

    String DATABASE_NAME = "turist.db";
    Integer DATABASE_VERSION = 1;

    String TABLE_NAME_RESTAURANT = "restaurant";
    String TABLE_NAME_ACTIVITATI = "activitati";

    String COLUMN_RESTAURANT_ID = "id_r";
    String COLUMN_RESTAURANT_DENUMIRE = "nume_r";
    String COLUMN_MASA_PERIOADA = "perioada_m";
    String COLUMN_MASA_RECENZIE = "recenzie_m";
    String COLUMN_MASA_NOTA = "nota";
    String COLUMN_MASA_DATA = "data_m";
    String COLUMN_ACTIVITATE_ID = "id_a";
    String COLUMN_ACTIVITATE_DENUMIRE = "nume_a";
    String COLUMN_ACTIVITATE_NOTA = "nota_a";
    String COLUMN_ACTIVITATE_MINUTE = "minute_a";
    String COLUMN_ACTIVITATE_DATA = "data_a";

    String CREATE_TABLE_RESTAURANT =
            "CREATE TABLE IF NOT EXISTS " + TABLE_NAME_RESTAURANT + " ( " +
                    COLUMN_RESTAURANT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
                    COLUMN_RESTAURANT_DENUMIRE + " TEXT, " +
                    COLUMN_MASA_PERIOADA + " TEXT, " +
                    COLUMN_MASA_RECENZIE + " TEXT, " +
                    COLUMN_MASA_NOTA + " REAL, " +
                    COLUMN_MASA_DATA + " TEXT);";

    String CREATE_TABLE_ACTIVITATE =
            "CREATE TABLE IF NOT EXISTS " + TABLE_NAME_ACTIVITATI + " ( " +
                    COLUMN_ACTIVITATE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
                    COLUMN_ACTIVITATE_DENUMIRE + " TEXT, " +
                    COLUMN_ACTIVITATE_NOTA + " INTEGER, " +
                    COLUMN_ACTIVITATE_MINUTE + " INTEGER, " +
                    COLUMN_ACTIVITATE_DATA + " TEXT);";

    String DROP_TABLE_RESTAURANT = "DROP TABLE IF EXISTS " + TABLE_NAME_RESTAURANT + ";";
    String DROP_TABLE_ACTIVITATI = "DROP TABLE IF EXISTS " + TABLE_NAME_ACTIVITATI + ";";
}
