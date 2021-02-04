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

public class WishlistActivity extends AppCompatActivity {

    RecyclerView myRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wishlist);
        Toolbar myToolbar = findViewById(R.id.myToolbar);
        setSupportActionBar(myToolbar);
        myRecyclerView = findViewById(R.id.recyclerWishlist);

        WishlistAdapter adapter = new WishlistAdapter(this);
        myRecyclerView.setAdapter(adapter);
        myRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_wishlist, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.shopping_cart){

            Intent intent = new Intent (this, ShoppingCartActivity.class);
            startActivity(intent);
        }

        if (id == R.id.contact_information)
        {
            Intent intent = new Intent (this, ContactActivity.class);
            startActivity(intent);
        }

        if (id == R.id.myPlants){
            Intent intent = new Intent (this, MyPlants.class);
            startActivity(intent);
        }
        return true;

    }
}