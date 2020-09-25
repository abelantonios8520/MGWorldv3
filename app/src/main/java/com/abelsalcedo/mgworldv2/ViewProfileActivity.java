package com.abelsalcedo.mgworldv2;

import android.app.AlertDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.Preference;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.abelsalcedo.mgworldv2.Model.Cliente;
import com.abelsalcedo.mgworldv2.Model.Colaborador;
import com.abelsalcedo.mgworldv2.providers.AuthProvider;
import com.abelsalcedo.mgworldv2.providers.ClienteProvider;
import com.bumptech.glide.Glide;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import static android.content.Context.MODE_PRIVATE;

public class ViewProfileActivity extends BottomSheetDialogFragment {

    String uid;
    //DatabaseReference reference;
    TextView username, bio_et;
    ImageView profile_img;
    static Context mContext;

    FirebaseAuth mAuth;
    DatabaseReference mDatabase;

    SharedPreferences mPref;
    private ClienteProvider mClienteProvider;
    private AuthProvider mAuthProvider;

    public ViewProfileActivity() {

    }

    public static ViewProfileActivity newInstance(String uid, Context context) {

        Bundle args = new Bundle();
        args.putString("uid",uid);
        mContext = context;
        ViewProfileActivity fragment = new ViewProfileActivity();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mPref = mContext.getApplicationContext().getSharedPreferences("typeUser", MODE_PRIVATE);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstance){
        View view = inflater.inflate(R.layout.activity_view_profile, container,false);

        if(getArguments()!=null){
            uid = getArguments().getString("id");
            profile_img = view.findViewById(R.id.view_profile_image);
            username = view.findViewById(R.id.view_username);
            bio_et = view.findViewById(R.id.view_bio_et);

            String user = mPref.getString("user", "");
            if (user.equals("cliente")) {
                mDatabase.child("Users").child("Clientes").child(mAuth.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        Cliente cliente = new Cliente();
                        cliente.setUsername(cliente.getUsername());
                        cliente.setBio(cliente.getBio());
                        if (cliente.getImageURL().equals("default")){
                        profile_img.setImageResource(R.drawable.profile_img);
                    } else {
                        //change this
                        Glide.with(mContext.getApplicationContext()).load(cliente.getImageURL()).into(profile_img);
                    }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }else{
                    mDatabase.child("Users").child("Colaboradores").child(mAuth.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            Colaborador colaborador = new Colaborador();
                            colaborador.setUsername(colaborador.getUsername());
                            colaborador.setBio(colaborador.getBio());
                            if (colaborador.getImageURL().equals("default")){
                                profile_img.setImageResource(R.drawable.profile_img);
                            } else {
                                //change this
                                Glide.with(mContext.getApplicationContext()).load(colaborador.getImageURL()).into(profile_img);
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
            }

        }

        return view;
    }

//    @Nullable
//    @Override
//    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        View view = inflater.inflate(R.layout.activity_view_profile,container,false);
//        if(getArguments()!=null){
//            uid = getArguments().getString("uid");
//            profile_img =  view.findViewById(R.id.view_profile_image);
//            username = view.findViewById(R.id.view_username);
//            bio_et = view.findViewById(R.id.view_bio_et);
//
//            reference = FirebaseDatabase.getInstance().getReference("Users").child(uid);
//            reference.addValueEventListener(new ValueEventListener() {
//                @Override
//                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                    Cliente cliente = dataSnapshot.getValue(Cliente.class);
//                    username.setText(cliente.getUsername());
//                    bio_et.setText((CharSequence) cliente.getBio());
//                    if (cliente.getImageURL().equals("default")){
//                        profile_img.setImageResource(R.drawable.profile_img);
//                    } else {
//                        //change this
//                        Glide.with(mContext.getApplicationContext()).load(cliente.getImageURL()).into(profile_img);
//                    }
//                }
//
//                @Override
//                public void onCancelled(@NonNull DatabaseError databaseError) {
//
//                }
//            });
//        }
//
//        return view;
//    }
}
