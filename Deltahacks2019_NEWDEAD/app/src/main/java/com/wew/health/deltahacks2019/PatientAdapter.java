package com.wew.health.deltahacks2019;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class PatientAdapter extends RecyclerView.Adapter<PatientAdapter.PatientHolder> {

    private static final int ALERT_NONE = 0;
    private static final int ALERT_MODERATE = 1;
    private static final int ALERT_SERIOUS = 2;
    private static final String ORDER_LIST = "new_food_order";
    private static final String DASHBOARD_LIST = "patient_dashboard";

    private List<Patient> mPatients;
    private int listType;//0 if you only want alerts, 1 if you want all patients
    private static String listLocation;//onClick is different depending on which activity the list is in

    PatientAdapter(List<Patient> pl, int tab, String location){
        mPatients = pl;
        listType = tab;
        listLocation = location;
    }

    public static class PatientHolder extends RecyclerView.ViewHolder{
        CardView cardView;
        TextView patientName;
        TextView patientSeat;
        TextView lastServed;
        TextView nextServed;
        ImageView alertSymbol;
        TextView alertMessage;

        PatientHolder(final View itemView){
            super(itemView);
            cardView = itemView.findViewById(R.id.patientView);
            patientName = itemView.findViewById(R.id.PatientName);
            patientSeat = itemView.findViewById(R.id.PatientSeat);
            lastServed = itemView.findViewById(R.id.lastTimeServed);
            nextServed = itemView.findViewById(R.id.nextTimeServed);
        }

        private void bindData(Patient patient){
            if (listLocation.equals(DASHBOARD_LIST)){
                cardView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        //get the activity that the cardView resides in
                        Activity mContext = (Activity) view.getContext();
                        //display patient menu
                        Dialog dialog = new Dialog(mContext);
                        dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
                        //dialog.setContentView(mContext.getLayoutInflater().inflate(R.layout.patient_popup_menu, null));
                        dialog.show();
                    }
                });
            }else if (listLocation.equals(ORDER_LIST)){
                cardView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        //open up the... FRUIT MENU
                        Intent intent = new Intent(view.getContext(),NewOrderActivity.class);
                        view.getContext().startActivity(intent);
                    }
                });
            }

        }
    }

    @Override
    public PatientHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.patient_list_item, parent, false);
        PatientHolder srHolder = new PatientHolder(v);
        return srHolder;
    }

    @Override
    public void onBindViewHolder(PatientHolder holder, int position){
        Patient patient = mPatients.get(position);

        //if we are on alerts tab, only display patients that have an alert set
        if (listType == 0){
            if (patient.isAlertType() != ALERT_NONE){
                bindPatientData(holder,patient);
            }
        }else{//display all patients if on the all tab
            bindPatientData(holder,patient);
        }

        Log.i("BORBOT",""+getItemCount());
    }

    //binds all patient data to the view holder
    private void bindPatientData(PatientHolder holder, Patient patient){
        String patientName = patient.getFirstName() + " " + patient.getLastName();
        String lastTime = "" + patient.getLastTimeServed();
        String nextTime = ""+patient.getNextTimeServed();

        holder.patientName.setText(patientName);
        holder.patientSeat.setText(patient.getSeat());
        holder.lastServed.setText(lastTime);
        holder.nextServed.setText(nextTime);
        if (patient.isAlertType() != ALERT_NONE){
            //TODO set the alert on the view
        }

        holder.bindData(patient);
    }

    @Override
    public int getItemCount(){
        return mPatients.size();
    }
}