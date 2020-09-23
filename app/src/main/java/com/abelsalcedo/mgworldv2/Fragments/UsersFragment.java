package com.abelsalcedo.mgworldv2.Fragments;

import android.graphics.Typeface;
import android.os.Bundle;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.abelsalcedo.mgworldv2.Adapter.OnItemClick;
import com.abelsalcedo.mgworldv2.Adapter.UserAdapter;
import com.abelsalcedo.mgworldv2.Model.Cliente;
import com.abelsalcedo.mgworldv2.Model.User;
import com.abelsalcedo.mgworldv2.Notifications.Client;
import com.abelsalcedo.mgworldv2.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class UsersFragment extends Fragment {

    private RecyclerView recyclerView;

    Typeface MR, MRR;
    FrameLayout frameLayout;
    TextView es_descp, es_title;

    private UserAdapter userAdapter;
    private List<Cliente> mUsers;
    static OnItemClick onItemClick;

    EditText search_users;

    public static UsersFragment newInstance(OnItemClick click) {
        onItemClick = click;
        Bundle args = new Bundle();

        UsersFragment fragment = new UsersFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_users, container, false);


        MRR = Typeface.createFromAsset(getContext().getAssets(), "fonts/myriadregular.ttf");
        MR = Typeface.createFromAsset(getContext().getAssets(), "fonts/myriad.ttf");

        recyclerView = view.findViewById(R.id.recycler_view);
        frameLayout = view.findViewById(R.id.es_layout);
        es_descp = view.findViewById(R.id.es_descp);
        es_title = view.findViewById(R.id.es_title);

        es_descp.setTypeface(MR);
        es_title.setTypeface(MRR);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(), DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(dividerItemDecoration);

        mUsers = new ArrayList<>();

        readUsers();

        search_users = view.findViewById(R.id.search_users);
        search_users.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                searchUsers(charSequence.toString().toLowerCase());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        return view;
    }

    private void searchUsers(String s) {

        final FirebaseUser fuser = FirebaseAuth.getInstance().getCurrentUser();
        Query query = FirebaseDatabase.getInstance().getReference("Users").orderByChild("search")
                .startAt(s)
                .endAt(s+"\uf8ff");

        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                mUsers.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                    Cliente cliente = snapshot.getValue(Cliente.class);

                    assert cliente != null;
                    assert fuser != null;
                    if (!cliente.getId().equals(fuser.getUid())){
                        mUsers.add(cliente);
                    }
                }

                userAdapter = new UserAdapter(getContext(),onItemClick, mUsers, false);
                recyclerView.setAdapter(userAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    private void readUsers() {

        final FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users");

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (search_users.getText().toString().equals("")) {
                    mUsers.clear();
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        Client cliente = snapshot.getValue(Cliente.class);

                        if (cliente!= null && cliente.getId()!=null && firebaseUser!=null && !cliente.getId().equals(firebaseUser.getUid())) {
                            mUsers.add(cliente);
                        }
                    }

                    if(mUsers.size()==0){
                        frameLayout.setVisibility(View.VISIBLE);
                    }
                    else{
                        frameLayout.setVisibility(View.GONE);
                    }

                    userAdapter = new UserAdapter(getContext(), onItemClick,mUsers, false);
                    recyclerView.setAdapter(userAdapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}

