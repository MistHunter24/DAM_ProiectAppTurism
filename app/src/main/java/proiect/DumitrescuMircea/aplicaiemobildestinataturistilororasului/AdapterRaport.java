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



public class AdapterRaport extends ArrayAdapter<Turist> {

    private Context context;
    private int resource;
    private List<Turist> turistList;
    private LayoutInflater layoutInflater;

    public AdapterRaport(@NonNull Context context, int resource,
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
            addDenumire(view, activitateTurist.getDenumireActivitate());
            addNota(view, activitateTurist.getNotaActivitate());
            addData(view, activitateTurist.getDataActivitatii());
        }
        return view;
    }

    private void addData(View view, Date dataActivitatii) {
        TextView textView = view.findViewById(R.id.lv_raport_data);
        if(dataActivitatii != null) {
            textView.setText(new SimpleDateFormat(ActivitateTuristicaAdd.DATE_FORMAT, Locale.US).
                    format(dataActivitatii));
        } else {
            textView.setText(R.string.adapter_no_content);
        }
    }


    private void addNota(View view, Integer nota) {
        TextView textView = view.findViewById(R.id.lv_raport_note_activitate);
        if (nota != null) {
            textView.setText("Nota acordata: " + String.valueOf(nota));
        } else {
            textView.setText(R.string.adapter_no_content);
        }
    }

    private void addDenumire(View view, String denumireActivitate) {
        TextView textView = view.findViewById(R.id.lv_raport_nume);
        if(denumireActivitate != null) {
            textView.setText(denumireActivitate);
        } else {
            textView.setText(R.string.adapter_no_content);
        }
    }
}
