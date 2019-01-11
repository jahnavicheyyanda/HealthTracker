package com.example.zece.healthtracker;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class PatientListAdaptor extends RecyclerView.Adapter<PatientListAdaptor.PatientViewHolder> {

    private final LayoutInflater layoutInflater;
    private Context mContext;
    private List<Patient> last_name;

    public PatientListAdaptor(Context context) {
        layoutInflater = LayoutInflater.from(context);
        mContext = context;
    }

    @NonNull
    @Override
    public PatientViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = layoutInflater.inflate(R.layout.list_item, parent, false);
        PatientViewHolder viewHolder = new PatientViewHolder(itemView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull PatientViewHolder holder, int position) {

        if (last_name != null) {
            Patient patient = last_name.get(position);
            holder.setData(patient.getLast_name(), position);
        } else {
            holder.patientItemView.setText(R.string.no_patient);
        }

    }

    @Override
    public int getItemCount() {
        if (last_name != null)
            return last_name.size();
        else return 0;
    }

    public void setPatients(List<Patient> patients) {
        last_name = patients;
        notifyDataSetChanged();
    }

    public class PatientViewHolder extends RecyclerView.ViewHolder{

        private TextView patientItemView;
        private int mPosition;

        public PatientViewHolder(View itemView) {
            super(itemView);
            patientItemView = itemView.findViewById(R.id.file_name);
        }

        public void setData(String patient, int position) {
            patientItemView.setText(patient);
            mPosition=position;
        }
    }
}
