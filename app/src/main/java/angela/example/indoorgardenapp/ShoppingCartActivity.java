package angela.example.indoorgardenapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.List;

public class ShoppingCartActivity extends AppCompatActivity {


    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private FirebaseAuth fAuth = FirebaseAuth.getInstance();
    private CollectionReference cartRef = db.collection("Shopping_Cart_Collection");
    private static final String TAG = "ShoppingCartActivity";
    private ShoppingCartAdapter adapter;
    List<ShoppingCartModel> lista;
    PlantModel plant;
    private int price;


    RecyclerView myRecyclerView;
    Button checkout;
    TextView total_price;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_cart);

        Toolbar myToolbar = findViewById(R.id.myToolbar);
        setSupportActionBar(myToolbar);


        setUpRecyclerView();

        checkout = findViewById(R.id.checkout_btn);



        checkout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent (ShoppingCartActivity.this, CheckoutActivity.class);
                startActivity(intent);
            }
        });

        String user_uid = " ";

        if (fAuth.getCurrentUser()!= null)
        {
            user_uid = fAuth.getCurrentUser().getUid();
        }



        calculateFullPrice();

        //total_price.setText(String.valueOf(price));


    }

    private void setUpRecyclerView() {
        myRecyclerView = findViewById(R.id.recyclerShoppingCart);
        String user_uid = " ";
        if (fAuth.getCurrentUser()!= null)
        {
            user_uid = fAuth.getCurrentUser().getUid();
        }

        Query query = cartRef.whereEqualTo("user_uid", user_uid);


        FirestoreRecyclerOptions<ShoppingCartModel> options = new FirestoreRecyclerOptions.Builder<ShoppingCartModel>().setQuery(query, ShoppingCartModel.class).build();
        adapter = new ShoppingCartAdapter(options, this);
        //myRecyclerView.setHasFixedSize(true);
        myRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        myRecyclerView.setAdapter(adapter);
    }

    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }

    private void calculateFullPrice(){

        db.collection("User_Collection").document(fAuth.getCurrentUser().getUid()).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                UserProfile user = documentSnapshot.toObject(UserProfile.class);
                total_price = findViewById(R.id.total_price);
                total_price.setText(String.valueOf(user.getCurrent_total()));

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.e(TAG, "Couldn't get user: ", e);
            }
        });
    }


    public void reloadActivity(){
        recreate();
    }
}