package com.example.zece.healthtracker.View;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.zece.healthtracker.Database.Patient;
import com.example.zece.healthtracker.R;
import com.example.zece.healthtracker.UI.FilesPage;
import com.example.zece.healthtracker.UI.PatientDataEdit;

import java.util.List;

public class PatientListAdaptor extends RecyclerView.Adapter<PatientListAdaptor.PatientViewHolder> {

    public interface OnDeleteClickListener{
        void OnDeleteClickListener(Patient mPatient);
    }

    private final LayoutInflater layoutInflater;
    private Context mContext;
    private List<Patient> list;
    private OnDeleteClickListener onDeleteClickListener;

    public PatientListAdaptor(Context context, OnDeleteClickListener listener) {
        layoutInflater = LayoutInflater.from(context);
        this.list = list;
        mContext = context;
        this.onDeleteClickListener = listener;
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

        if (list != null) {
            Patient patient = list.get(position);
            holder.setData(patient.getLast_name(), position);
            holder.setData(patient.getFirst_name(), position);
            holder.setData(patient.getNote(), position);
            holder.setListeners();
        } else {
            holder.patientItemView.setText(R.string.no_patient);
        }

    }

    @Override
    public int getItemCount() {
        if (list != null)
            return list.size();
        else return 0;
    }

    public void setPatients(List<Patient> patients) {
        list = patients;
        notifyDataSetChanged();
    }

    public class PatientViewHolder extends RecyclerView.ViewHolder{

        private TextView patientItemView;
        private int mPosition;
        private ImageView imgDelete, imgEdit;

        public PatientViewHolder(View itemView) {
            super(itemView);
            patientItemView = itemView.findViewById(R.id.file_name);
            imgDelete = itemView.findViewById(R.id.ivRowDelete);
            imgEdit = itemView.findViewById(R.id.ivRowEdit);
        }

        public void setData(String patient, int position) {
            patientItemView.setText(patient);
            mPosition=position;
        }

        public void setListeners() {
            imgEdit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent intent = new Intent(mContext, PatientDataEdit.class);
                    intent.putExtra("patient_id", list.get(mPosition).getPatient_id());
                    ((Activity)mContext).startActivityForResult(intent, FilesPage.UPDATE_PATIENT_DATA_ACTIVITY_REQUEST_CODE);


                }
            });

            imgDelete.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    if (onDeleteClickListener != null){
                        onDeleteClickListener.OnDeleteClickListener(list.get(mPosition));
                    }

                }
            });
        }
    }
}
