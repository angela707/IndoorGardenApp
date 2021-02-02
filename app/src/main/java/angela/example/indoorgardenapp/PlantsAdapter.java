package angela.example.indoorgardenapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class PlantsAdapter extends RecyclerView.Adapter<PlantsAdapter.PlantsViewHolder> {
    Context context;

    public PlantsAdapter (Context context){

        this.context = context;
    }

    @NonNull
    @Override
    public PlantsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.plants_row, parent, false);
        PlantsViewHolder holder = new PlantsViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull PlantsAdapter.PlantsViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 10;
    }


    public class PlantsViewHolder extends RecyclerView.ViewHolder{

        public PlantsViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}


