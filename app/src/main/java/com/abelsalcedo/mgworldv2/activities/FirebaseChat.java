package com.abelsalcedo.mgworldv2.activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.TextViewRichContentReceiverCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.abelsalcedo.mgworldv2.R;
import com.abelsalcedo.mgworldv2.activities.cliente.HistoryBookingDetailClienteActivity;
import com.abelsalcedo.mgworldv2.activities.cliente.MapClienteActivity;
import com.abelsalcedo.mgworldv2.activities.cliente.MapClienteBookingActivity;
import com.abelsalcedo.mgworldv2.activities.colaborador.HistoryBookingDetailColaboradorActivity;
import com.abelsalcedo.mgworldv2.activities.colaborador.MapColaboradorActivity;
import com.abelsalcedo.mgworldv2.adapter.AdapterMensajes;
import com.abelsalcedo.mgworldv2.models.HistoryBooking;
import com.abelsalcedo.mgworldv2.models.Mensaje;
import com.abelsalcedo.mgworldv2.models.User;
import com.abelsalcedo.mgworldv2.providers.AuthProvider;
import com.abelsalcedo.mgworldv2.providers.ClienteBookingProvider;
import com.abelsalcedo.mgworldv2.providers.ClienteProvider;
import com.abelsalcedo.mgworldv2.providers.ColaboradorProvider;
import com.abelsalcedo.mgworldv2.providers.GeofireProvider;
import com.abelsalcedo.mgworldv2.providers.HistoryBookingProvider;
import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;
import dmax.dialog.SpotsDialog;

public class FirebaseChat extends AppCompatActivity {
    private TextView mTextViewName;
    private CircleImageView mfotoPerfil;
    private RecyclerView mrecyclerView;
    private EditText mtxtMensajes;
    private Button mEnviar;
    private ImageButton btnEnviarFoto;
    private AuthProvider mAuthProvider;
    private AdapterMensajes adapter;
    private FirebaseDatabase database;
    private DatabaseReference databaseReference;
    private FirebaseStorage storage;
    private StorageReference storageReference;
    private static final int PHOTO_SEND = 1;
    private static final int PHOTO_PERFIL = 2;
    private String fotoPerfilCadena;
    private FirebaseAuth mAuth;
    private String NOMBRE_USUARIO;
    DatabaseReference mDatabase;
    SharedPreferences mPref;
    AlertDialog mDialog;
    private ValueEventListener mListener;
    private GeofireProvider mGeofireProvider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_firebase_chat);

        mfotoPerfil = findViewById(R.id.fotoPerfil);
        mTextViewName = findViewById(R.id.textViewNombre);
        mrecyclerView = findViewById(R.id.rvMensajes);
        mtxtMensajes = findViewById(R.id.txtMensaje);
        mEnviar = findViewById(R.id.btnEnviar);
        btnEnviarFoto = findViewById(R.id.btnEnviarfoto);
        fotoPerfilCadena = "";
        mDatabase = FirebaseDatabase.getInstance().getReference();
        database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference("chat");//sala de chat
        storage = FirebaseStorage.getInstance();
        mAuth = FirebaseAuth.getInstance();
        mAuthProvider = new AuthProvider();

        adapter = new AdapterMensajes(this);
        LinearLayoutManager lm = new LinearLayoutManager(this);
        mrecyclerView.setLayoutManager(lm);
        mrecyclerView.setAdapter(adapter);
        mDialog = new SpotsDialog.Builder().setContext(FirebaseChat.this).setMessage("Espere un momento").build();
        mPref = getApplicationContext().getSharedPreferences("typeUser", MODE_PRIVATE);

        mEnviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //adapter.addMensaje(new Mensaje(mtxtMensajes.getText().toString(), mnombre.getText().toString(), "","1",ServerValue.TIMESTAMP));
                databaseReference.push().setValue(new MensajeEnviar(mtxtMensajes.getText().toString(),NOMBRE_USUARIO , fotoPerfilCadena, "1", ServerValue.TIMESTAMP));
                mtxtMensajes.setText("");
            }
        });

        btnEnviarFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_GET_CONTENT);
                i.setType("image/jpeg");
                i.putExtra(Intent.EXTRA_LOCAL_ONLY, true);
                startActivityForResult(Intent.createChooser(i, "Selecciona una foto"), PHOTO_SEND);
            }
        });

        mfotoPerfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_GET_CONTENT);
                i.setType("image/jpeg");
                i.putExtra(Intent.EXTRA_LOCAL_ONLY, true);
                startActivityForResult(Intent.createChooser(i, "Selecciona una foto"), PHOTO_PERFIL);
            }
        });

        adapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
            @Override
            public void onItemRangeInserted(int positionStart, int itemCount) {
                super.onItemRangeInserted(positionStart, itemCount);

                setScrollbar();
            }
        });

        databaseReference.addChildEventListener(new ChildEventListener() {



            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                MensajeRecibir m = dataSnapshot.getValue(MensajeRecibir.class);
                adapter.addMensaje(m);
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void setScrollbar() {
        mrecyclerView.scrollToPosition(adapter.getItemCount() - 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PHOTO_SEND && resultCode == RESULT_OK) {
            Uri u = data.getData();
            storageReference = storage.getReference("imagenes_chat");//imagenes_chat
            final StorageReference fotoReferencia = storageReference.child(u.getLastPathSegment());

            fotoReferencia.putFile(u).addOnSuccessListener(this, new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Task<Uri> urlTask = taskSnapshot.getStorage().getDownloadUrl();
                    MensajeEnviar m = new MensajeEnviar( NOMBRE_USUARIO+" te ha enviado una foto", u.toString(), NOMBRE_USUARIO, fotoPerfilCadena, "2", ServerValue.TIMESTAMP);
                    databaseReference.push().setValue(m);
                }
            });
        } else if (requestCode == PHOTO_PERFIL && resultCode == RESULT_OK) {
            Uri u = data.getData();
            storageReference = storage.getReference("foto_perfil");//imagenes_chat
            final StorageReference fotoReferencia = storageReference.child(u.getLastPathSegment());

            fotoReferencia.putFile(u).addOnSuccessListener(this, new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Task<Uri> urlTask = taskSnapshot.getStorage().getDownloadUrl();
                    fotoPerfilCadena = u.toString();
                    MensajeEnviar m = new MensajeEnviar(NOMBRE_USUARIO+" ha actualizado su foto de perfil", u.toString(), NOMBRE_USUARIO, fotoPerfilCadena, "2", ServerValue.TIMESTAMP);
                    databaseReference.push().setValue(m);
                    Glide.with(FirebaseChat.this).load(u.toString()).into(mfotoPerfil);
                }
            });
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        mEnviar.setEnabled(false);
        String user = mPref.getString("user", "");
        if (user.equals("cliente")) {
            mDatabase.child("Users").child("Clientes").child(mAuth.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    User user = dataSnapshot.getValue(User.class);
                    NOMBRE_USUARIO = user.getName();
                    mTextViewName.setText(NOMBRE_USUARIO);
                    mEnviar.setEnabled(true);
                }


                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }

       else{ mDatabase.child("Users").child("Colaboradores").child(mAuth.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                User user = dataSnapshot.getValue(User.class);
                NOMBRE_USUARIO = user.getName();
                mTextViewName.setText(NOMBRE_USUARIO);
                mEnviar.setEnabled(true);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
  }


}
