package com.abelsalcedo.mgworldv2.activities.colaborador;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.abelsalcedo.mgworldv2.R;
import com.abelsalcedo.mgworldv2.adapter.HistoryBookingColaboradorAdapter;
import com.abelsalcedo.mgworldv2.includes.MyToolbar;
import com.abelsalcedo.mgworldv2.models.HistoryBooking;
import com.abelsalcedo.mgworldv2.providers.AuthProvider;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

public class HistoryBookingColaboradorActivity extends AppCompatActivity {
    private RecyclerView mReciclerView;
    private HistoryBookingColaboradorAdapter mAdapter;
    private AuthProvider mAuthProvider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_booking_colaborador);
        MyToolbar.show(this, "Historial de viajes", true);

        mReciclerView = findViewById(R.id.recyclerViewHistoryBooking);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        mReciclerView.setLayoutManager(linearLayoutManager);
    }

    @Override
    protected void onStart() {
        super.onStart();
        mAuthProvider = new AuthProvider();
        Query query = FirebaseDatabase.getInstance().getReference()
                .child("HistoryBooking")
                .orderByChild("idColaborador")
                .equalTo(mAuthProvider.getId());
        FirebaseRecyclerOptions<HistoryBooking> options = new FirebaseRecyclerOptions.Builder<HistoryBooking>()
                .setQuery(query, HistoryBooking.class)
                .build();
        mAdapter = new HistoryBookingColaboradorAdapter(options, HistoryBookingColaboradorActivity.this);

        mReciclerView.setAdapter(mAdapter);
        mAdapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mAdapter.stopListening();
    }
}