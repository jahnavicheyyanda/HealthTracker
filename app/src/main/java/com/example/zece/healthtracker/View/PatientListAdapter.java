package com.example.zece.healthtracker.View;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.zece.healthtracker.Database.Patient;
import com.example.zece.healthtracker.Database.Record;
import com.example.zece.healthtracker.R;
import com.example.zece.healthtracker.UI.FilesPage;
import com.example.zece.healthtracker.UI.PatientDataEdit;
import com.example.zece.healthtracker.UI.RecordedWave;

import java.io.File;
import java.util.List;

public class PatientListAdapter extends RecyclerView.Adapter<PatientListAdapter.PatientViewHolder> {

    public interface OnDeleteClickListener{
        void onDeleteClickListener(Patient mPatient);
    }

    private final LayoutInflater layoutInflater;
    private Context mContext;
    private List<Patient> list;
    private List<Record> recordList;
    private OnDeleteClickListener onDeleteClickListener;

    public PatientListAdapter(Context context, OnDeleteClickListener listener , OnDeleteClickListener listener2) {
        layoutInflater = LayoutInflater.from(context);
        mContext = context;
        this.onDeleteClickListener = listener;
    }

    @NonNull
    @Override
    public PatientViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = layoutInflater.inflate(R.layout.patient_list_item, parent, false);
        PatientViewHolder viewHolder = new PatientViewHolder(itemView);
        return viewHolder;
    }

    //binding the view holders to their data with assigning them to position
    @Override
    public void onBindViewHolder(@NonNull PatientViewHolder holder, int position) {

        if (list != null) {

            // In the record list, last names are added as titles

            Patient patientLastName = list.get(position);
            holder.setData(patientLastName.getLast_name(), position);
            /*holder.setData(patient.getNote(), position);
            holder.setData(patient.getLast_name(), position);*/

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

    //notification methods are for updates, to rebind the affected view holders to their data updated.
    public void setPatients(List<Patient> patients) {
        list = patients;
        notifyDataSetChanged();
    }

    public void setRecords(List<Record> records) {
        recordList = records;
        notifyDataSetChanged();
    }

    public class PatientViewHolder extends RecyclerView.ViewHolder{

        private TextView patientItemView;
        private int mPosition;
        private ImageView imgDelete, imgEdit, imgPlay;

        PatientViewHolder(View itemView) {
            super(itemView);
            patientItemView = itemView.findViewById(R.id.file_name);
            imgDelete = itemView.findViewById(R.id.iv_row_delete);
            imgEdit = itemView.findViewById(R.id.iv_row_edit);
            imgPlay = itemView.findViewById(R.id.iv_row_play);
        }


        void setData(String patient, int position) {
            patientItemView.setText(patient);
            mPosition=position;
        }

        void setListeners() {

            imgEdit.setOnClickListener(v -> {

                Intent intent = new Intent(mContext, PatientDataEdit.class);
                intent.putExtra("patient_id", list.get(mPosition).getPatient_id());
                intent.putExtra("patient_firstName", list.get(mPosition).getFirst_name());
                intent.putExtra("patient_lastName", list.get(mPosition).getLast_name());

                ((Activity)mContext).startActivityForResult(intent,
                        FilesPage.UPDATE_PATIENT_DATA_ACTIVITY_REQUEST_CODE);

            });

            imgDelete.setOnClickListener(v -> {
                AlertDialog.Builder altDial = new AlertDialog.Builder(mContext);
                altDial.setMessage("Do you want to delete the data?").setCancelable(false)
                        .setPositiveButton("Delete", (dialog, which) -> {

                            if (onDeleteClickListener != null){
                                onDeleteClickListener.onDeleteClickListener(list.get(mPosition));

                                File file = new File(Environment.getExternalStorageDirectory() + "/Health_tracker/"
                                        + list.get(mPosition).getLast_name() + "_"
                                        + list.get(mPosition).getFirst_name() + " "
                                        + recordList.get(mPosition).getDate()+ ".wav");

                                    boolean deleted = file.delete();
                            }
                        })
                        .setNegativeButton("Cancel", (dialog, which) -> dialog.cancel());

                AlertDialog alert = altDial.create();
                alert.setTitle("Delete");
                alert.show();

            });

            imgPlay.setOnClickListener(v -> {

                Intent intentPlay = new Intent(mContext, RecordedWave.class);
                intentPlay.putExtra("patient_id", list.get(mPosition).getPatient_id());
                intentPlay.putExtra("patient_firstName", list.get(mPosition).getFirst_name());
                intentPlay.putExtra("patient_lastName", list.get(mPosition).getLast_name());
                intentPlay.putExtra("record_date", recordList.get(mPosition).getDate());

                ((Activity)mContext).startActivityForResult(intentPlay,
                        FilesPage.UPDATE_PATIENT_DATA_ACTIVITY_REQUEST_CODE);


            });

        }
    }
}
