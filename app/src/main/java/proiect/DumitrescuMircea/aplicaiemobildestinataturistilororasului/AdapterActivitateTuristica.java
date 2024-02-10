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



public class AdapterActivitateTuristica extends ArrayAdapter<Turist> {

    private Context context;
    private int resource;
    private List<Turist> turistList;
    private LayoutInflater layoutInflater;

    public AdapterActivitateTuristica(@NonNull Context context, int resource,
                                      @NonNull List<Turist> turistList, LayoutInflater layoutInflater) {
        super(context, resource, turistList);
        this.context = context;
        this.resource = resource;
        this.turistList = turistList;
        this.layoutInflater = layoutInflater;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = layoutInflater.inflate(resource, parent, false);
        Turist activitateTurist = turistList.get(position);
        if(activitateTurist != null) {
            addActivitate(view, activitateTurist.getDenumireActivitate());
            addNota(view, activitateTurist.getNotaActivitate());
            addDurata(view, activitateTurist.getDurataActivitate());
            addData(view, activitateTurist.getDataActivitatii());
        }
        return view;
    }

    private void addData(View view, Date dataActivitatii) {
        TextView textView = view.findViewById(R.id.lv_fitness_data);
        if(dataActivitatii != null) {
            textView.setText(new SimpleDateFormat(ActivitateTuristicaAdd.DATE_FORMAT, Locale.US).
                    format(dataActivitatii));
        } else {
          textView.setText(R.string.adapter_no_content);
        }
    }


    private void addDurata(View view, Integer nrMinute) {
        TextView textView = view.findViewById(R.id.lv_fitness_timp);
        if(nrMinute != null) {
            textView.setText("Durata activitate: " + String.valueOf(nrMinute));

        } else {
            textView.setText(R.string.adapter_no_content);
        }
    }

    private void addNota(View view, Integer nota) {
        TextView textView = view.findViewById(R.id.lv_fitness_calorii);
        if (nota != null) {
            textView.setText("Nota acordata: " + String.valueOf(nota));
        } else {
            textView.setText(R.string.adapter_no_content);
        }
    }

    private void addActivitate(View view, String denumireActivitate) {
        TextView textView = view.findViewById(R.id.lv_activitate_nume);
        if(denumireActivitate != null) {
            textView.setText(denumireActivitate);
        } else {
            textView.setText(R.string.adapter_no_content);
        }
    }
}
