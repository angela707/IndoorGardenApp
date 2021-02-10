package angela.example.indoorgardenapp;

import android.content.Context;
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
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Picasso;

public class WishlistAdapter extends FirestoreRecyclerAdapter<WishlistModel, WishlistAdapter.WishlistHolder> {

    private static final String TAG = "WishlistAdapter";
    FirebaseAuth fAuth = FirebaseAuth.getInstance();
    FirebaseFirestore fs = FirebaseFirestore.getInstance();
    Context context;
    String user_uid= fAuth.getCurrentUser().getUid();;
    public WishlistAdapter(@NonNull FirestoreRecyclerOptions<WishlistModel> options, Context context) {
        super(options);
        this.context = context;
    }

    @Override
    protected void onBindViewHolder(@NonNull WishlistHolder holder, int position, @NonNull WishlistModel model) {


        if (fAuth.getCurrentUser()!= null)
        {
            user_uid = fAuth.getCurrentUser().getUid();
        }


        PlantModel myPlant;
        DocumentReference doc = fs.collection("Plant_Collection").document(model.getPlant_uid());
        doc.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                PlantModel plant = documentSnapshot.toObject(PlantModel.class);

                holder.name.setText(plant.getName());
                //holder.description.setText(plant.getDescription());
                holder.price.setText(String.valueOf(plant.getPrice()));
                Picasso.get().load(plant.getImage1()).resize(500, 500).noFade().into(holder.img);

            }
        });

        holder.fav_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


            }
        });

        holder.cart_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });






    }

    @NonNull
    @Override
    public WishlistHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.my_wishlist_row, parent, false);
        return new WishlistHolder(view);
    }

    public class WishlistHolder extends RecyclerView.ViewHolder {
        TextView name, price; //,description;
        ImageView img;
        Button fav_btn, cart_btn;

        public WishlistHolder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.p_name);
            //description = itemView.findViewById(R.id.p_desc);
            price = itemView.findViewById(R.id.p_price);
            img = itemView.findViewById(R.id.p_picture);
            fav_btn = itemView.findViewById(R.id.fav_btn);
            cart_btn = itemView.findViewById(R.id.cart_btn);

        }
    }
}