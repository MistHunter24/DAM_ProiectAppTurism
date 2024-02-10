package proiect.DumitrescuMircea.aplicaiemobildestinataturistilororasului;

import android.os.Parcel;
import android.os.Parcelable;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;


public class Turist implements Parcelable {

    private String denumireActivitate;
    private Integer notaActivitate;
    private Integer durataActivitate;
    private Date dataActivitatii;

    private Long idActivitate;

    public Turist(String denumireActivitate, Integer notaActivitate, Integer durataActivitate,
                  Date dataActivitatii) {
        this.denumireActivitate = denumireActivitate;
        this.notaActivitate = notaActivitate;
        this.durataActivitate = durataActivitate;
        this.dataActivitatii = dataActivitatii;
    }

    public Turist(String denumireActivitate, Integer notaActivitate, Integer durataActivitate,
                  Date dataActivitatii, Long idActivitate) {
        this.denumireActivitate = denumireActivitate;
        this.notaActivitate = notaActivitate;
        this.durataActivitate = durataActivitate;
        this.dataActivitatii = dataActivitatii;
        this.idActivitate = idActivitate;
    }

    protected Turist(Parcel in) {
        denumireActivitate = in.readString();
        if (in.readByte() == 0) {
            notaActivitate = null;
        } else {
            notaActivitate = in.readInt();
        }
        if (in.readByte() == 0) {
            durataActivitate = null;
        } else {
            durataActivitate = in.readInt();
        }
        if (in.readByte() == 0) {
            idActivitate = null;
        } else {
            idActivitate = in.readLong();
        }
        try {
            this.dataActivitatii = new SimpleDateFormat
                    (ActivitateTuristicaAdd.DATE_FORMAT, Locale.US).parse(in.readString());
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public static final Creator<Turist> CREATOR = new Creator<Turist>() {
        @Override
        public Turist createFromParcel(Parcel in) {
            return new Turist(in);
        }

        @Override
        public Turist[] newArray(int size) {
            return new Turist[size];
        }
    };

    public String getDenumireActivitate() {
        return denumireActivitate;
    }

    public void setDenumireActivitate(String denumireActivitate) {
        this.denumireActivitate = denumireActivitate;
    }

    public Integer getNotaActivitate() {
        return notaActivitate;
    }

    public void setNotaActivitate(Integer notaActivitate) {
        this.notaActivitate = notaActivitate;
    }

    public Integer getDurataActivitate() {
        return durataActivitate;
    }

    public void setDurataActivitate(Integer durataActivitate) {
        this.durataActivitate = durataActivitate;
    }

    public Date getDataActivitatii() {
        return dataActivitatii;
    }

    public void setDataActivitatii(Date dataActivitatii) {
        this.dataActivitatii = dataActivitatii;
    }

    public Long getIdTurist() {
        return idActivitate;
    }

    public void setIdActivitate(Long idActivitate) {
        this.idActivitate = idActivitate;
    }

    @Override
    public String toString() {
        return "Turist{" +
                "denumireActivitate='" + denumireActivitate + '\'' +
                ", nota=" + notaActivitate +
                ", durataActivitate=" + durataActivitate +
                ", dataActivitatii=" + dataActivitatii +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(denumireActivitate);
        if (notaActivitate == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(notaActivitate);
        }
        if (durataActivitate == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(durataActivitate);
        }
        if (idActivitate == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeLong(idActivitate);
        }
        String dateStr = this.dataActivitatii != null ?
                new SimpleDateFormat(ActivitateTuristicaAdd.DATE_FORMAT, Locale.US)
                        .format(this.dataActivitatii) :
                null;
        dest.writeString(dateStr);
    }
}
