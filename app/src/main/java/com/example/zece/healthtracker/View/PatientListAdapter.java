package com.example.zece.healthtracker.View;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
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

import java.io.File;
import java.util.List;

public class PatientListAdapter extends RecyclerView.Adapter<PatientListAdapter.PatientViewHolder> {

    public interface OnDeleteClickListener{
        void OnDeleteClickListener(Patient mPatient);
        void OnDeleteClickListener2(Record mRecord);
    }

    private final LayoutInflater layoutInflater;
    private Context mContext;
    private List<Patient> list;
    private List<Record> recordList;
    private OnDeleteClickListener onDeleteClickListener;

    public PatientListAdapter(Context context, OnDeleteClickListener listener , OnDeleteClickListener listener2) {
        layoutInflater = LayoutInflater.from(context);
        this.list = list;
        mContext = context;
        this.onDeleteClickListener = listener;
    }

    //Create new views

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

        public void setDataRec(String record, int position) {
            patientItemView.setText(record);
            mPosition=position;
        }

        public void setListeners() {

            imgEdit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent intent = new Intent(mContext, PatientDataEdit.class);
                    intent.putExtra("patient_id", list.get(mPosition).getPatient_id());
                    intent.putExtra("patient_firstName", list.get(mPosition).getFirst_name());
                    intent.putExtra("patient_lastName", list.get(mPosition).getLast_name());
                    //intent.putExtra("record_date", recordList.get(mPosition).getDate());

                    ((Activity)mContext).startActivityForResult(intent,
                            FilesPage.UPDATE_PATIENT_DATA_ACTIVITY_REQUEST_CODE);



                }
            });

            imgDelete.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    AlertDialog.Builder altDial = new AlertDialog.Builder(mContext);
                    altDial.setMessage("Do you want to delete the data?").setCancelable(false)
                            .setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                    if (onDeleteClickListener != null){
                                        onDeleteClickListener.OnDeleteClickListener(list.get(mPosition));

                                        File file = new File(Environment.getExternalStorageDirectory() + "/Health_tracker/"
                                                + list.get(mPosition).getLast_name() + "_"
                                                + list.get(mPosition).getFirst_name() + " " +recordList.get(mPosition).getDate()+ ".wav");

                                            boolean deleted = file.delete();


                                    }

                                }
                            })
                            .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.cancel();
                                }
                            });

                    AlertDialog alert = altDial.create();
                    alert.setTitle("Delete");
                    alert.show();



                }
            });
        }
    }
}
