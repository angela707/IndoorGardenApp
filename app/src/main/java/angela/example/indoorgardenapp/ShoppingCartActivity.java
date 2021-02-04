package angela.example.indoorgardenapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ShoppingCartActivity extends AppCompatActivity {
    RecyclerView myRecyclerView;
    Button checkout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_cart);

        myRecyclerView = findViewById(R.id.recyclerShoppingCart);
        ShoppingCartAdapter adapter = new ShoppingCartAdapter(this);
        myRecyclerView.setAdapter(adapter);
        myRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        checkout = findViewById(R.id.checkout_btn);


        checkout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent (ShoppingCartActivity.this, CheckoutActivity.class);
                startActivity(intent);
            }
        });
    }
}