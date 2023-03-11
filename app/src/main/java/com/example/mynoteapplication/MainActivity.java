package com.example.mynoteapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    Adapter adapter;
    ArrayList<Notes> items;
    LinearLayoutManager layoutManager = new LinearLayoutManager(this);
    ImageView delete;
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ImageView add = findViewById(R.id.add);
        delete = findViewById(R.id.delete);

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,AddNote.class));
            }
        });
        items = new ArrayList<Notes>();

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new Adapter(this, items);
        recyclerView.setAdapter(adapter);

        GetAllNotes();

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }
    private void GetAllNotes() {

        db.collection("Note").get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot documentSnapshots) {
                        if (documentSnapshots.isEmpty()) {
                            Log.e("zzz", "onSuccess: LIST EMPTY");
                            return;
                        } else {
                            for (DocumentSnapshot documentSnapshot : documentSnapshots) {
                                if (documentSnapshot.exists()) {
                                    String id = documentSnapshot.getId();
                                    String myNote = documentSnapshot.getString("note");
                                    Notes notes = new Notes(id, myNote);
                                    items.add(notes);

                                    recyclerView.setLayoutManager(layoutManager);
                                    recyclerView.setHasFixedSize(true);
                                    recyclerView.setAdapter(adapter);
                                    adapter.notifyDataSetChanged();
                                    Log.e("zzz", items.toString());
                                }
                            }
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.e("LogDATA", "get failed with ");


                    }
                });
    }
    public void Delete(final Notes notes) {
        db.collection("Users").document(notes.getId())
                .delete()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Log.e("zzz", "deleted");
                        items.remove(notes);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.e("zzz", "fail");
                    }
                });
    }


}