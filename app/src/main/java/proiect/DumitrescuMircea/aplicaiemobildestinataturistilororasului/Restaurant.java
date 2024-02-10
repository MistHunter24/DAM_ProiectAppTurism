package proiect.DumitrescuMircea.aplicaiemobildestinataturistilororasului;

import android.os.Parcel;
import android.os.Parcelable;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;


public class Restaurant implements Parcelable {

    private String numeRestaurant;
    private String perioadaMesei;
    private String recenzie;
    private Float notaRestaurant;
    private Date dataMesei;

    private Long idRestaurant;

    public Restaurant(String numeRestaurant, String perioadaMesei, String recenzie,
                      Float notaRestaurant, Date dataMesei) {
        this.numeRestaurant = numeRestaurant;
        this.perioadaMesei = perioadaMesei;
        this.recenzie = recenzie;
        this.notaRestaurant = notaRestaurant;
        this.dataMesei = dataMesei;
    }

    public Restaurant(String numeRestaurant, String perioadaMesei, String recenzie,
                      Float notaRestaurant, Date dataMesei, Long idRestaurant) {
        this.numeRestaurant = numeRestaurant;
        this.perioadaMesei = perioadaMesei;
        this.recenzie = recenzie;
        this.notaRestaurant = notaRestaurant;
        this.dataMesei = dataMesei;
        this.idRestaurant = idRestaurant;
    }

    protected Restaurant(Parcel in) {
        numeRestaurant = in.readString();
        perioadaMesei = in.readString();
        if (in.readByte() == 0) {
            recenzie = null;
        } else {
            recenzie = in.readString();
        }
        if (in.readByte() == 0) {
            notaRestaurant = null;
        } else {
            notaRestaurant = in.readFloat();
        }
        if (in.readByte() == 0) {
            idRestaurant = null;
        } else {
            idRestaurant = in.readLong();
        }
        try {
            this.dataMesei = new SimpleDateFormat
                    (ActivitateRestaurantAdd.DATE_FORMAT, Locale.US)
                    .parse(in.readString());
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public static final Creator<Restaurant> CREATOR = new Creator<Restaurant>() {
        @Override
        public Restaurant createFromParcel(Parcel in) {
            return new Restaurant(in);
        }

        @Override
        public Restaurant[] newArray(int size) {
            return new Restaurant[size];
        }
    };

    public String getNumeRestaurant() {
        return numeRestaurant;
    }

    public void setNumeRestaurant(String numeRestaurant) {
        this.numeRestaurant = numeRestaurant;
    }

    public String getPerioadaMesei() {
        return perioadaMesei;
    }

    public void setPerioadaMesei(String perioadaMesei) {
        this.perioadaMesei = perioadaMesei;
    }

    public String getRecenzie() {
        return recenzie;
    }

    public void setRecenzie(String recenzie) {
        this.recenzie = recenzie;
    }

    public Float getNotaRestaurant() {
        return notaRestaurant;
    }

    public void setNotaRestaurant(Float notaRestaurant) {
        this.notaRestaurant = notaRestaurant;
    }

    public Date getDataMesei() {
        return dataMesei;
    }

    public void setDataMesei(Date dataMesei) {
        this.dataMesei = dataMesei;
    }

    public Long getIdRestaurant() {
        return idRestaurant;
    }

    public void setIdRestaurant(Long idRestaurant) {
        this.idRestaurant = idRestaurant;
    }

    @Override
    public String toString() {
        return "Restaurant{" +
                "numeMancare='" + numeRestaurant + '\'' +
                ", perioadaMesei='" + perioadaMesei + '\'' +
                ", Recenzie=" + recenzie +
                ", Nota=" + notaRestaurant +
                ", dataMesei=" + dataMesei +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

        dest.writeString(numeRestaurant);
        dest.writeString(perioadaMesei);
        if (recenzie == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeString(recenzie);
        }
        if (notaRestaurant == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeFloat(notaRestaurant);
        }
        if (idRestaurant == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeLong(idRestaurant);
        }
        String dateStr = this.dataMesei != null ?
                new SimpleDateFormat
                        (ActivitateRestaurantAdd.DATE_FORMAT, Locale.US)
                        .format(this.dataMesei) :
                null;
        dest.writeString(dateStr);
    }
}
