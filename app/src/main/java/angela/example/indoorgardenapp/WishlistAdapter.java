package angela.example.indoorgardenapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
public class WishlistAdapter extends RecyclerView.Adapter<WishlistAdapter.WishlistViewHolder> {

    Context context;
    WishlistAdapter(Context context){
        this.context = context;
    }

    @NonNull
    @Override
    public WishlistViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.plants_row, parent, false);
        WishlistAdapter.WishlistViewHolder holder = new WishlistAdapter.WishlistViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull WishlistAdapter.WishlistViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 10;
    }



    public class WishlistViewHolder extends RecyclerView.ViewHolder{

        public WishlistViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
