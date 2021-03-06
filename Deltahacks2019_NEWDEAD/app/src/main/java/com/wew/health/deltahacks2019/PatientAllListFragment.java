package com.wew.health.deltahacks2019;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

public class PatientAllListFragment extends Fragment {
    private static final String ORDER_LIST = "new_food_order";
    private static final String DASHBOARD_LIST = "patient_dashboard";

    private RecyclerView mPatientsRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private RecyclerView.Adapter mAdapter;

    private String destination;//the destination of the user after clicking on a patient

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstaceState){
        View view = inflater.inflate(R.layout.fragment_patient_list,container,false);
        mPatientsRecyclerView = view.findViewById(R.id.patients_alerts_recycler_view);
        mLayoutManager = new LinearLayoutManager(getActivity());
        mPatientsRecyclerView.setLayoutManager(mLayoutManager);

        return view;
    }

    @Override
    public void onResume(){
        super.onResume();
        updateList();
    }

    public void updateList(){
        List<Patient> patientList = testPatients();
        if (mAdapter == null){
            mAdapter = new PatientAdapter(patientList,1,destination);
            mPatientsRecyclerView.setAdapter(mAdapter);
        }
    }

    public void setDestination(String dest){
        this.destination = dest;
    }

    public List<Patient> testPatients(){
        List<Patient> patients = new ArrayList<>();

        Patient patient1 = new Patient();
        patient1.setFirstName("Peter");
        patient1.setLastName("Parker");
        patient1.setAlertMessage("I have spiders on me");
        patient1.setSeat("A123");
        patient1.setAlertType(2);
        patient1.setLastTimeServed(System.currentTimeMillis());
        Long lastTime = patient1.getLastTimeServed();
        Log.i("BORBOT last time",""+lastTime);
        patient1.setNextTimeServed(lastTime + 3);
        Log.i("BORBOT last time again",""+lastTime);
        Log.i("BORBOT next time",""+patient1.getNextTimeServed());
        patients.add(patient1);

        Patient patient2 = new Patient();
        patient2.setFirstName("Jack");
        patient2.setLastName("Pearson");
        patient2.setAlertMessage("sober");
        patient2.setSeat("B456");
        patient2.setAlertType(1);
        patient2.setLastTimeServed(System.currentTimeMillis());
        Long lastTime2 = patient2.getLastTimeServed();
        patient2.setNextTimeServed(lastTime2 + 4);
        patients.add(patient2);

        Patient patient3 = new Patient();
        patient3.setFirstName("William");
        patient3.setLastName("Randall's dad");
        patient3.setSeat("A673");
        patient3.setAlertType(0);
        patient3.setLastTimeServed(System.currentTimeMillis());
        Long lastTime3 = patient3.getLastTimeServed();
        patient3.setNextTimeServed(lastTime3 + 5);
        patients.add(patient3);

        return patients;
    }
}
