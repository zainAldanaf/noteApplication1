package com.example.mynoteapplication;

import android.nfc.Tag;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class AddNote extends AppCompatActivity {

    FirebaseFirestore db = FirebaseFirestore.getInstance();

    EditText et_note;
    Button save;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);

        et_note = findViewById(R.id.newNote);
        save = findViewById(R.id.saveNote);

        save.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String note = et_note.getText().toString();

                Map<String, Object> notes = new HashMap<>();
                if (!note.isEmpty()) {
                    notes.put("note", note);

                    db.collection("Note")
                            .add(notes)
                            .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                @Override
                                public void onSuccess(DocumentReference documentReference) {
                                    Log.e("TAG", "Data added successfully to database" + documentReference.getId());
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Log.e("TAG", "Failed to add database",e);

                                }
                            });
                } else {
                  //  Toast.makeText(this, "Please Fill fields", Toast.LENGTH_SHORT).show();
                    Log.e("TAG", "Failed");
                }
            }
        });

    }
/*
    public void saveToFirebase() {

        String note = et_note.getText().toString();

        Map<String, Object> notes = new HashMap<>();
        if (!note.isEmpty()) {
            notes.put("note", note);

            db.collection("Notes")
                    .add(notes)
                    .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                         @Override
                         public void onSuccess(DocumentReference documentReference) {
                           Log.e("TAG", "Data added successfully to database" + documentReference.getId());
                         }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.e("TAG", "Failed to add database",e);

                        }
                    });

        } else {
            Toast.makeText(this, "Please Fill fields", Toast.LENGTH_SHORT).show();
            Log.e("TAG", "Failed");
        }

    }

*/

}

