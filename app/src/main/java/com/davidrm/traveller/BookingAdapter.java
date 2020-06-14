package com.davidrm.traveller;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.DocumentSnapshot;

public class BookingAdapter extends FirestoreRecyclerAdapter<Actividad,BookingAdapter.ViewHolder> {

    Activity activity;


    /**
     * Create a new RecyclerView adapter that listens to a Firestore Query.  See {@link
     * FirestoreRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public BookingAdapter(@NonNull FirestoreRecyclerOptions<Actividad> options, Activity activity) {
        super(options);
        this.activity = activity;
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, int position, @NonNull Actividad actividad) {

        DocumentSnapshot actividadDocument = getSnapshots().getSnapshot(holder.getAdapterPosition());

        final String id = actividadDocument.getId();

        holder.textViewActividad.setText(actividad.getActividad());
        holder.textViewFecha.setText(actividad.getFecha());
        holder.textViewHora.setText(actividad.getHora());

        holder.btnEditar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity, EditarActivity.class);
                intent.putExtra("actividadId", id);
                activity.startActivity(intent);

            }
        });




    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.view_bookings,viewGroup,false);
        return new ViewHolder(view);
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView textViewActividad;
        TextView textViewFecha;
        TextView textViewHora;
        ImageButton btnEditar;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            textViewActividad = itemView.findViewById(R.id.textViewActividad);
            textViewFecha = itemView.findViewById(R.id.textViewFecha);
            textViewHora = itemView.findViewById(R.id.textViewHora);
            btnEditar = itemView.findViewById(R.id.imageButtonEditar);




        }
    }




}
