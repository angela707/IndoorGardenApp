package angela.example.indoorgardenapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;

public class CheckoutActivity extends AppCompatActivity {

    private static final String TAG = "CheckoutActivity";
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private FirebaseAuth fAuth = FirebaseAuth.getInstance();
    String user_uid = fAuth.getCurrentUser().getUid();
    TextView full_price;
    EditText first_name, last_name, email, number, address, city, country;
    Button confirm;
    Map<String, Integer> items = new HashMap<>();

    int full_priceVal, current_order;
    String first_nameVal, last_nameVal, emailVal, numberVal, addressVal, cityVal, countryVal, order_id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);

        confirm = findViewById(R.id.confirmation_btn);
        full_price = findViewById(R.id.register_amount);
        first_name = findViewById(R.id.first_name);
        last_name = findViewById(R.id.last_name);
        email = findViewById(R.id.register_email);
        number = findViewById(R.id.register_number);
        address = findViewById(R.id.register_address);
        city = findViewById(R.id.register_city);
        country = findViewById(R.id.register_country);




        db.collection("User_Collection").document(fAuth.getCurrentUser().getUid()).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                UserProfile user = documentSnapshot.toObject(UserProfile.class);

                full_price.setText(String.valueOf(user.getCurrent_total()));
                full_priceVal = user.getCurrent_total();
                current_order = user.getNum_orders();

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.e(TAG, "Couldn't get user: ", e);
            }
        });


        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                first_nameVal = first_name.getText().toString().trim();
                last_nameVal = last_name.getText().toString().trim();
                emailVal = email.getText().toString().trim();
                numberVal = number.getText().toString().trim();
                addressVal = address.getText().toString().trim();
                cityVal = city.getText().toString().trim();
                countryVal = country.getText().toString().trim();


                if (TextUtils.isEmpty(first_nameVal)) {
                    first_name.setError("Name is required.");
                    return;
                }
                if (TextUtils.isEmpty(last_nameVal)) {
                    last_name.setError("Last name is required.");
                    return;
                }
                if (TextUtils.isEmpty(emailVal)) {
                    email.setError("Email is required.");
                    return;
                }
                if (TextUtils.isEmpty(numberVal)) {
                    number.setError("Number is required.");
                    return;
                }
                if (TextUtils.isEmpty(addressVal)) {
                    address.setError("Address is required.");
                    return;
                }
                if (TextUtils.isEmpty(cityVal)) {
                    city.setError("City is required.");
                    return;
                }
                if (TextUtils.isEmpty(countryVal)) {
                    country.setError("Country is required.");
                    return;
                }





                db.collection("Shopping_Cart_Collection").whereEqualTo("user_uid", user_uid).get().
                        addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                for (QueryDocumentSnapshot documentSnapshot : task.getResult()) {
                                    ShoppingCartModel item = documentSnapshot.toObject(ShoppingCartModel.class);  // dodadi gi rabotite vo tags;
                                    items.put(item.getPlant_uid(), item.getAmount());
                                }
                                OrderModel order = new OrderModel(user_uid, first_nameVal, last_nameVal, emailVal, numberVal, addressVal, cityVal, countryVal, full_priceVal, items);
                                order_id = user_uid + current_order;
                                db.collection("Orders_Collection").document(order_id).set(order).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) { // dodadena order vo collection
                                        //Toast.makeText(CheckoutActivity.this, "Added to orders", Toast.LENGTH_SHORT).show();
                                        db.collection("User_Collection").document(fAuth.getCurrentUser().getUid())
                                                .update("num_orders", FieldValue.increment(1)).addOnSuccessListener(new OnSuccessListener<Void>() {
                                                    @Override
                                                    public void onSuccess(Void aVoid) { // smeneta number_order vo user
                                                        //Toast.makeText(CheckoutActivity.this, "Updated in user!", Toast.LENGTH_SHORT).show();
                                                        db.collection("User_Collection").document(fAuth.getCurrentUser().getUid())
                                                                .update("current_total", 0)
                                                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                            @Override
                                                            public void onSuccess(Void aVoid) { // resetiran total
                                                                //Toast.makeText(CheckoutActivity.this, "Resetiran total", Toast.LENGTH_SHORT).show();
                                                                db.collection("Shopping_Cart_Collection").whereEqualTo("user_uid", user_uid).get()
                                                                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                                                            @Override
                                                                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                                                                for (QueryDocumentSnapshot documentSnapshot : task.getResult()) {
                                                                                    ShoppingCartModel myItem = documentSnapshot.toObject(ShoppingCartModel.class);

                                                                                    db.collection("Plant_Collection").document(myItem.getPlant_uid()).get()
                                                                                            .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                                                                                @Override
                                                                                                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                                                                                    PlantModel plantModel = task.getResult().toObject(PlantModel.class);
                                                                                                    String deleteItem_id = user_uid + plantModel.getName();

                                                                                                    db.collection("Shopping_Cart_Collection").document(deleteItem_id)
                                                                                                            .delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                                                                                                        @Override
                                                                                                        public void onSuccess(Void aVoid) {
                                                                                                            Log.d(TAG, "DocumentSnapshot successfully deleted!");
                                                                                                        }
                                                                                                    });
                                                                                                }
                                                                                            });

                                                                                }
                                                                            }
                                                                        });



                                                            }
                                                        });




                                                    }
                                                });
                                    }
                                });
                            }
                        });




                Intent intent = new Intent(CheckoutActivity.this, ConfirmationPageActivity.class);
                intent.putExtra("order_id", order_id);
                startActivity(intent);
            }
        });
    }
}