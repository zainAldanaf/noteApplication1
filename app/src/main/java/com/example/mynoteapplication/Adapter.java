package com.example.mynoteapplication;

import static android.media.CamcorderProfile.get;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.FirebaseFirestore;
import java.util.ArrayList;

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {
    private LayoutInflater layoutInflater;
    private ArrayList<Notes> data;
    ImageView delete;

    FirebaseFirestore db = FirebaseFirestore.getInstance();

    Adapter(Context context, ArrayList<Notes> data){
        this.layoutInflater = LayoutInflater.from(context);
        this.data = data;

    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.custom_view,parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
     holder.note.setText(data.get(position).getUsername());
    /* holder.delete.setOnClickListener(new View.OnClickListener() {

        });*/
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView note;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            note = itemView.findViewById(R.id.note);
            delete = itemView.findViewById(R.id.delete);

        }
    }

    private void deleteItem(int position) {
        Notes item = data.get(position);
        db.collection("my_collection").document(item.getId())
                .delete()
                .addOnSuccessListener(aVoid -> {
                    // remove the item from your list and update your RecyclerView
                    data.remove(position);
                    notifyItemRemoved(position);
                    notifyItemRangeChanged(position, data.size());
                })
                .addOnFailureListener(e -> {
                    // handle the failure here
                });
    }

}
