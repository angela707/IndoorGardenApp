package angela.example.indoorgardenapp;


import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

public class ShoppingCartAdapter extends FirestoreRecyclerAdapter<ShoppingCartModel, ShoppingCartAdapter.CartHolder>{

    FirebaseAuth fAuth = FirebaseAuth.getInstance();
    FirebaseFirestore fs = FirebaseFirestore.getInstance();
    String user_uid  = fAuth.getCurrentUser().getUid();
    String current_uid;

    private static final String TAG = "ShoppingCartAdapter";
    Context context;
    ShoppingCartActivity activity;
    public ShoppingCartAdapter(@NonNull FirestoreRecyclerOptions<ShoppingCartModel> options, Context context ) {
        super(options);
        this.context = context;
        this.activity = (ShoppingCartActivity) context;
    }

    @Override
    protected void onBindViewHolder(@NonNull CartHolder holder, int position, @NonNull ShoppingCartModel model) {


        holder.number.setText(String.valueOf(model.getAmount()));

        DocumentReference doc = fs.collection("Plant_Collection").document(model.getPlant_uid());
        doc.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                PlantModel plant = documentSnapshot.toObject(PlantModel.class);

                holder.name.setText(plant.getName());
                //holder.description.setText(plant.getDescription());
                holder.price.setText(String.valueOf(plant.getPrice()));
                Uri picUrl = Uri.parse(plant.getImage1());
                Picasso.get().load(picUrl).resize(500, 500).noFade().into(holder.image);


            }
        });


        holder.addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DocumentReference doc = fs.collection("Plant_Collection").document(model.getPlant_uid());
                doc.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        PlantModel plant = documentSnapshot.toObject(PlantModel.class);
                        int maxPlant = plant.getAmount();



                        current_uid = model.getUser_uid() + plant.getName();

                        if (model.getAmount() < maxPlant){
                            DocumentReference doc = fs.collection("Shopping_Cart_Collection").document(current_uid);

                            doc.update("amount", FieldValue.increment(1)).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    fs.collection("User_Collection").document(fAuth.getCurrentUser().getUid())
                                            .update("current_total", FieldValue.increment(plant.getPrice())).addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                            //Toast.makeText(context, "Updated in user!", Toast.LENGTH_SHORT).show();
                                            activity.reloadActivity();
                                        }
                                    }).addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Toast.makeText(context, "Couldn't add to user", Toast.LENGTH_SHORT).show();
                                        }
                                    });


                                    Log.d(TAG, "DocumentSnapshot successfully updated!");
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Log.w(TAG, "Error updating document", e);
                                }
                            });
                        }
                        else{
                            Toast.makeText(context, "Out of stock!", Toast.LENGTH_SHORT).show();
                        }


                    }
                });
            }
        });


        holder.delBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DocumentReference doc = fs.collection("Plant_Collection").document(model.getPlant_uid());
                doc.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        PlantModel plant = documentSnapshot.toObject(PlantModel.class);



                        current_uid = model.getUser_uid() + plant.getName();


                        DocumentReference doc = fs.collection("Shopping_Cart_Collection").document(current_uid);

                        if (model.getAmount() == 1){ // delete from shopping cart
                            doc.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {

                                    fs.collection("User_Collection").document(fAuth.getCurrentUser().getUid())
                                            .update("current_total", FieldValue.increment(-( plant.getPrice()))).addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                            //Toast.makeText(context, "Updated in user!", Toast.LENGTH_SHORT).show();
                                            activity.reloadActivity();
                                        }
                                    }).addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Toast.makeText(context, "Couldn't add to user", Toast.LENGTH_SHORT).show();
                                        }
                                    });
                                    Log.w(TAG, "Document successfully deleted");
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Log.w(TAG, "Error deleting document", e);
                                }
                            });

                        } else{ //update
                            doc.update("amount", FieldValue.increment(-1)).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    fs.collection("User_Collection").document(fAuth.getCurrentUser().getUid())
                                            .update("current_total", FieldValue.increment(-( plant.getPrice()))).addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                            //Toast.makeText(context, "Updated in user!", Toast.LENGTH_SHORT).show();
                                            activity.reloadActivity();
                                        }
                                    }).addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Toast.makeText(context, "Couldn't add to user", Toast.LENGTH_SHORT).show();
                                        }
                                    });
                                    Log.d(TAG, "DocumentSnapshot successfully updated!");
                                    activity.reloadActivity();
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Log.w(TAG, "Error updating document", e);
                                }
                            });

                        }


                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error accessing document", e);
                    }
                });

            }
        });



    }

    @NonNull
    @Override
    public CartHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.my_shopping_cart_row, parent, false);
        return new CartHolder(view);
    }

    public class CartHolder extends RecyclerView.ViewHolder {
        TextView name, price, number;
        ImageView image;
        Button addBtn, delBtn;
        public CartHolder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.p_name);
            price = itemView.findViewById(R.id.p_price);
            number = itemView.findViewById(R.id.plant_number);
            image = itemView.findViewById(R.id.p_picture);
            addBtn = itemView.findViewById(R.id.add_btn);
            delBtn = itemView.findViewById(R.id.delete_btn);
        }
    }
}