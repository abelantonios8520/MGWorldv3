package com.abelsalcedo.mgworldv2.providers;

import com.abelsalcedo.mgworldv2.models.Cliente;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class ClienteProvider {
    DatabaseReference mDatabase;

    public ClienteProvider() {
        mDatabase = FirebaseDatabase.getInstance().getReference().child("Users").child("Clientes");
    }

    public Task<Void> create(Cliente cliente) {
        Map<String, Object> map = new HashMap<>();
        map.put("name", cliente.getName());
        map.put("ape", cliente.getApe());
        map.put("telef", cliente.getTelf());
        map.put("email", cliente.getEmail());
        return mDatabase.child(cliente.getId()).setValue(map);
    }

    public Task<Void> update(Cliente cliente) {
        Map<String, Object> map = new HashMap<>();
        map.put("name", cliente.getName());
        map.put("image", cliente.getImage());
        map.put("gustos", cliente.getGustos());
        return mDatabase.child(cliente.getId()).updateChildren(map);
    }

    public DatabaseReference getCliente(String idCliente) {
        return mDatabase.child(idCliente);
    }
}
