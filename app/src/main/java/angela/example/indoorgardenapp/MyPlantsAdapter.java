package angela.example.indoorgardenapp;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MyPlantsAdapter extends RecyclerView.Adapter<MyPlantsAdapter.MyPlantsViewHolder>{

    Context context;

    public MyPlantsAdapter (Context context){

        this.context = context;
    }
    @NonNull
    @Override
    public MyPlantsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.plants_row, parent, false);
        MyPlantsAdapter.MyPlantsViewHolder holder = new MyPlantsAdapter.MyPlantsViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyPlantsAdapter.MyPlantsViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }


    public class MyPlantsViewHolder extends RecyclerView.ViewHolder{

        public MyPlantsViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
