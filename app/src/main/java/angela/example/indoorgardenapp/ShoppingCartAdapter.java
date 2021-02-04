package angela.example.indoorgardenapp;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ShoppingCartAdapter extends RecyclerView.Adapter<ShoppingCartAdapter.ShoppingCartViewHolder>{

    Context context;

    public ShoppingCartAdapter (Context context){

        this.context = context;
    }
    @NonNull
    @Override
    public ShoppingCartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.my_shopping_cart_row, parent, false);
        ShoppingCartAdapter.ShoppingCartViewHolder holder = new ShoppingCartAdapter.ShoppingCartViewHolder(view);
        return holder;

    }

    @Override
    public void onBindViewHolder(@NonNull ShoppingCartAdapter.ShoppingCartViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 4;
    }

    public class ShoppingCartViewHolder extends RecyclerView.ViewHolder{

        public ShoppingCartViewHolder(@NonNull View itemView)
        {
            super(itemView);
        }
    }
}
