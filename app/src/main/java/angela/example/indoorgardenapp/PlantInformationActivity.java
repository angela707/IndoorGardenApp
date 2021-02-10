package angela.example.indoorgardenapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

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
import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;

import java.util.ArrayList;
import java.util.List;

public class PlantInformationActivity extends AppCompatActivity {

    FirebaseAuth fAuth = FirebaseAuth.getInstance();
    FirebaseFirestore fs = FirebaseFirestore.getInstance();
    String user_uid  = fAuth.getCurrentUser().getUid();
    private static final String TAG = "PlantInformationActivit";
    PlantModel p;
    String image1, image2, image3, plant_uid;
    TextView pname, pdescription, pprice;
    SliderView sliderView;
    String []images;
    SliderAdapter sliderAdapter;
    Button fav, cart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plant_information);

        sliderView = findViewById(R.id.image_slider);

        pname = findViewById(R.id.pname);
        pdescription = findViewById(R.id.pdesc);
        pprice = findViewById(R.id.pprice);

        fav = findViewById(R.id.fav_btn);
        cart = findViewById(R.id.cart_btn);


        getData();
        setData();

        images = new String[]{image1, image2, image3};

        sliderAdapter = new SliderAdapter(this, images);
        sliderView.setSliderAdapter(sliderAdapter);
        sliderView.setIndicatorAnimation(IndicatorAnimationType.WORM);
        sliderView.setSliderTransformAnimation(SliderAnimations.DEPTHTRANSFORMATION);
        sliderView.startAutoCycle();


        fav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fav.setBackgroundResource(R.drawable.ic_full_green_heart);
                String user_uid = " ";

                if (fAuth.getCurrentUser()!= null) {
                    user_uid = fAuth.getCurrentUser().getUid();
                }


                String wishlist_item_uid = user_uid + p.getName();

                CollectionReference wishlist = fs.collection("Wishlist_Collection");
                WishlistModel myWishlist = new WishlistModel(user_uid, plant_uid);


                wishlist.document(wishlist_item_uid).set(myWishlist).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()){
                            Toast.makeText(PlantInformationActivity.this, "Added to Wishlist", Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(PlantInformationActivity.this, "Error adding to wishlist!", Toast.LENGTH_SHORT).show();
                        }
                    }
                });



                fav.setEnabled(false);
            }
        });




        cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                String item_uid = user_uid + p.getName();

                DocumentReference ref = fs.collection("Shopping_Cart_Collection").document(item_uid);

                fs.collection("Shopping_Cart_Collection").document(item_uid).get()
                        .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                if (task.getResult().exists()){ //item exist, update it
                                    ref.update("amount", FieldValue.increment(1)).addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {

                                            fs.collection("User_Collection").document(fAuth.getCurrentUser().getUid())
                                                    .update("current_total", FieldValue.increment(p.getPrice())).addOnSuccessListener(new OnSuccessListener<Void>() {
                                                @Override
                                                public void onSuccess(Void aVoid) {
                                                    Toast.makeText(PlantInformationActivity.this, "Updated in user!", Toast.LENGTH_SHORT).show();
                                                }
                                            }).addOnFailureListener(new OnFailureListener() {
                                                @Override
                                                public void onFailure(@NonNull Exception e) {
                                                    Toast.makeText(PlantInformationActivity.this, "Couldn't add to user", Toast.LENGTH_SHORT).show();
                                                }
                                            });


                                            Toast.makeText(PlantInformationActivity.this, "Updated Shopping Cart!", Toast.LENGTH_SHORT).show();
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


                                    ShoppingCartModel myItem = new ShoppingCartModel(user_uid, plant_uid, 1);

                                    String cart_item_uid = user_uid + p.getName();
                                    fs.collection("Shopping_Cart_Collection").document(cart_item_uid).set(myItem)
                                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> task) {
                                                    if (task.isSuccessful()){

                                                        fs.collection("User_Collection").document(fAuth.getCurrentUser().getUid())
                                                                .update("current_total", FieldValue.increment(p.getPrice())).addOnSuccessListener(new OnSuccessListener<Void>() {
                                                            @Override
                                                            public void onSuccess(Void aVoid) {
                                                                Log.d(TAG, "Updated in user!");
                                                                //Toast.makeText(context, "Updated in user!", Toast.LENGTH_SHORT).show();
                                                            }
                                                        }).addOnFailureListener(new OnFailureListener() {
                                                            @Override
                                                            public void onFailure(@NonNull Exception e) {
                                                                Log.e(TAG, "Couldn't add to user: ", e);
                                                                //Toast.makeText(context, "Couldn't add to user", Toast.LENGTH_SHORT).show();
                                                            }
                                                        });
                                                        Toast.makeText(PlantInformationActivity.this, "Added to Shopping Cart", Toast.LENGTH_SHORT).show();
                                                    }else{
                                                        Toast.makeText(PlantInformationActivity.this, "Error adding to Shopping Cart!", Toast.LENGTH_SHORT).show();
                                                    }
                                                }
                                            });

                                }

                            }
                        });



            }
        });



    }

    private void setData() {
        pname.setText(p.getName());
        pdescription.setText(p.getDescription());
        pprice.setText(String.valueOf(p.getPrice()));
        image1 = p.getImage1();
        image2 = p.getImage2();
        image3 = p.getImage3();
    }

    private void getData() {
        if (getIntent().hasExtra("plantInfo") && getIntent().hasExtra("plant_uid"))
        {

            p = (PlantModel) getIntent().getSerializableExtra("plantInfo");
            plant_uid = getIntent().getStringExtra("plant_uid");

        }
        else {
            Toast.makeText(this, "No data", Toast.LENGTH_SHORT).show();
        }

    }
}