package angela.example.indoorgardenapp;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.squareup.picasso.Picasso;

public class PlantsAdapter extends FirestoreRecyclerAdapter<PlantModel, PlantsAdapter.PlantsHolder> {

    Context context;
    Dialog myDialog;
    public PlantsAdapter(@NonNull FirestoreRecyclerOptions<PlantModel> options, Context context) {
        super(options);

        this.context = context;
    }

    @Override
    protected void onBindViewHolder(@NonNull PlantsHolder holder, int position, @NonNull PlantModel model) {
        holder.name.setText(model.getName());
        holder.description.setText(model.getDescription());
        holder.price.setText(String.valueOf(model.getPrice()));

        Picasso.get().load(model.getImage1()).resize(1000, 1000).into(holder.image);

        holder.wishlist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                holder.wishlist.setBackgroundResource(R.drawable.ic_full_green_heart);
                Toast.makeText(context, "Added to Wishlist", Toast.LENGTH_SHORT).show();
                holder.wishlist.setEnabled(false);
            }
        });



        holder.plant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(context, "Test Click" + String.valueOf(holder.getAdapterPosition()), Toast.LENGTH_SHORT).show();
               Intent intent = new Intent(context, PlantInformationActivity.class);
//                intent.putExtra("name", model.getName());
//                intent.putExtra("image1", model.getImage1());
//                intent.putExtra("image2", model.getImage2());
//                intent.putExtra("image3", model.getImage3());
//                intent.putExtra("price", model.getPrice());
//                intent.putExtra("description", model.getDescription());
                intent.putExtra("plantInfo", model);
                context.startActivity(intent);

            }
        });

    }

    @NonNull
    @Override
    public PlantsHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.plants_row, parent, false);
        return new PlantsHolder(view);
    }

    class PlantsHolder extends RecyclerView.ViewHolder {

        TextView name, description, price;
        ImageView image;
        Button shopping_cart;
        Button wishlist;
        ConstraintLayout plant;
        public PlantsHolder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.p_name);
            description = itemView.findViewById(R.id.p_desc);
            price = itemView.findViewById(R.id.p_price);
            image = itemView.findViewById(R.id.p_picture);
            wishlist = itemView.findViewById(R.id.fav_btn);
            shopping_cart = itemView.findViewById(R.id.cart_btn);
            plant = itemView.findViewById(R.id.plant_info);
        }
    }

}