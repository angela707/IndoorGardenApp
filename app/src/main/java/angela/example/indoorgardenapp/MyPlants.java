package angela.example.indoorgardenapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.google.firebase.auth.FirebaseAuth;

public class MyPlants extends AppCompatActivity {


    // za vtor kolokvium da se nadgradi so notifikacii
    RecyclerView myRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_plants);

        Toolbar myToolbar = findViewById(R.id.myToolbar);
        setSupportActionBar(myToolbar);

        myRecyclerView = findViewById(R.id.recyclerMyPlants);
        MyPlantsAdapter adapter = new MyPlantsAdapter(this);
        myRecyclerView.setAdapter(adapter);
        myRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_my_plants, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.myWishlist){

            Intent intent = new Intent (this, WishlistActivity.class);
            startActivity(intent);
        }

        if (id == R.id.contact_information)
        {
            Intent intent = new Intent (this, ContactActivity.class);
            startActivity(intent);
        }

        if (id == R.id.shopping_cart){
            Intent intent = new Intent (this, ShoppingCartActivity.class);
            startActivity(intent);
        }

        if (id == R.id.log_out){
            FirebaseAuth.getInstance().signOut();
            Intent intent = new Intent(MyPlants.this, MainActivity.class);
            startActivity(intent);
            finish();

        }
        return true;

    }
}