package angela.example.indoorgardenapp;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.util.Log;
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
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.squareup.picasso.Picasso;

public class PlantsAdapter extends FirestoreRecyclerAdapter<PlantModel, PlantsAdapter.PlantsHolder> {

    Context context;
    private static final String TAG = "PlantsAdapter";


    public PlantsAdapter(@NonNull FirestoreRecyclerOptions<PlantModel> options, Context context) {
        super(options);

        this.context = context;
    }

    @Override
    protected void onBindViewHolder(@NonNull PlantsHolder holder, int position, @NonNull PlantModel model) {
        FirebaseAuth fAuth = FirebaseAuth.getInstance();
        FirebaseFirestore fs = FirebaseFirestore.getInstance();
        holder.name.setText(model.getName());
        holder.description.setText(model.getDescription());
        holder.price.setText(String.valueOf(model.getPrice()));

        Uri picUrl = Uri.parse(model.getImage1());
        Picasso.get().load(picUrl).resize(500, 500).noFade().into(holder.image);

        String user_uid = " ";

        if (fAuth.getCurrentUser()!= null)
        {
            user_uid = fAuth.getCurrentUser().getUid();
        }

        DocumentSnapshot snapshot = getSnapshots().getSnapshot(holder.getAdapterPosition());
        String plant_uid = snapshot.getId();
        CollectionReference wishRef = fs.collection("Wishlist_Collection");


        //check if plant is in wishlist




        // add to wishlist
        holder.wishlist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                holder.wishlist.setBackgroundResource(R.drawable.ic_full_green_heart);
                String user_uid = " ";

                if (fAuth.getCurrentUser()!= null) {
                    user_uid = fAuth.getCurrentUser().getUid();
                }


                DocumentSnapshot snapshot = getSnapshots().getSnapshot(holder.getAdapterPosition());
                String plant_uid = snapshot.getId();

                String wishlist_item_uid = user_uid + model.getName();

                CollectionReference wishlist = fs.collection("Wishlist_Collection");
                WishlistModel myWishlist = new WishlistModel(user_uid, plant_uid);


                wishlist.document(wishlist_item_uid).set(myWishlist).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()){
                            Toast.makeText(context, "Added to Wishlist", Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(context, "Error adding to wishlist!", Toast.LENGTH_SHORT).show();
                        }
                    }
                });



                holder.wishlist.setEnabled(false);
            }
        });


        holder.shopping_cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String user_uid = " ";
                if (fAuth.getCurrentUser()!= null) {
                    user_uid = fAuth.getCurrentUser().getUid();
                }

                DocumentSnapshot snapshot = getSnapshots().getSnapshot(holder.getAdapterPosition());
                String plant_uid = snapshot.getId();

                String item_uid = user_uid + model.getName();

                DocumentReference ref = fs.collection("Shopping_Cart_Collection").document(item_uid);

                fs.collection("Shopping_Cart_Collection").document(item_uid).get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.getResult().exists()){ //item exist, update it
                            ref.update("amount", FieldValue.increment(1)).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Toast.makeText(context, "Updated Shopping Cart!", Toast.LENGTH_SHORT).show();
                                    Log.d(TAG, "Updated Shopping Cart!");
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Log.w(TAG, "Error updating document", e);
                                }
                            });
                        }
                        else{ //item doesn't exist, create it

                            String user_uid = fAuth.getCurrentUser().getUid();
                            DocumentSnapshot snapshot = getSnapshots().getSnapshot(holder.getAdapterPosition());
                            String plant_uid = snapshot.getId();
                            ShoppingCartModel myItem = new ShoppingCartModel(user_uid, plant_uid, 1);

                            String cart_item_uid = user_uid + model.getName();
                            fs.collection("Shopping_Cart_Collection").document(cart_item_uid).set(myItem)
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()){
                                        Toast.makeText(context, "Added to Shopping Cart", Toast.LENGTH_SHORT).show();
                                    }else{
                                        Toast.makeText(context, "Error adding to Shopping Cart!", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });

                        }

                    }
                });


            }
        });







        holder.plant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(context, "Test Click" + String.valueOf(holder.getAdapterPosition()), Toast.LENGTH_SHORT).show();
               Intent intent = new Intent(context, PlantInformationActivity.class);
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
        Button wishlist, shopping_btn;
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
            shopping_btn = itemView.findViewById(R.id.cart_btn);


        }
    }

}