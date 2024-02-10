package proiect.DumitrescuMircea.aplicaiemobildestinataturistilororasului;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;



public class AdapterRestaurant extends ArrayAdapter<Restaurant> {

    private Context context;
    private int resource;
    private List<Restaurant> mese;
    private LayoutInflater layoutInflater;

    public AdapterRestaurant(@NonNull Context context, int resource,
                             @NonNull List<Restaurant> mese, LayoutInflater layoutInflater) {
//        super(context, resource, mese);
        super(context, resource, mese);
        this.context = context;
        this.resource = resource;
        this.mese = mese;
        this.layoutInflater = layoutInflater;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = layoutInflater.inflate(resource, parent, false);
        Restaurant restaurant = mese.get(position);
        if(restaurant != null) {
            addNumeRestaurant(view, restaurant.getNumeRestaurant());
            addPerioada(view, restaurant.getPerioadaMesei());
           // addCalorii(view, restaurant.getRecenzie());
            addNotaRestaurant(view, restaurant.getNotaRestaurant());
            addData(view, restaurant.getDataMesei());
        }
        return view;
    }

    private void addData(View view, Date dataMesei) {
        TextView textView = view.findViewById(R.id.lv_masa_data);
        if(dataMesei != null) {
            textView.setText(new SimpleDateFormat(ActivitateRestaurantAdd.DATE_FORMAT, Locale.US)
            .format(dataMesei));
        } else {
            textView.setText(R.string.adapter_no_content);
        }
    }


    private void addNotaRestaurant(View view, Float notaRestaurant) {
        TextView textView = view.findViewById(R.id.lv_masa_portii);
        if(notaRestaurant != null) {
            textView.setText("Nota Restaurant = " + String.valueOf(notaRestaurant));
        } else {
            textView.setText(R.string.adapter_no_content);
        }
    }



    private void addPerioada(View view, String perioadaMesei) {
        TextView textView = view.findViewById(R.id.lv_masa_perioada);
        if(perioadaMesei != null && !perioadaMesei.trim().isEmpty()) {
            textView.setText(perioadaMesei);
        } else {
            textView.setText(R.string.adapter_no_content);
        }
    }

    private void addNumeRestaurant(View view, String numeRestaurant) {
        TextView textView = view.findViewById(R.id.lv_masa_nume);
        if(numeRestaurant != null && !numeRestaurant.trim().isEmpty()) {
            textView.setText(numeRestaurant);
        } else {
            textView.setText(R.string.adapter_no_content);
        }
    }
}
