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

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class WishlistActivity extends AppCompatActivity {

    RecyclerView myRecyclerView;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    FirebaseAuth fAuth = FirebaseAuth.getInstance();
    private CollectionReference myWish = db.collection("Wishlist_Collection");
    private WishlistAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wishlist);
        Toolbar myToolbar = findViewById(R.id.myToolbar);
        setSupportActionBar(myToolbar);
        myRecyclerView = findViewById(R.id.recyclerWishlist);

       setUpWishlistAdapter();
    }

    private void setUpWishlistAdapter() {

        myRecyclerView = findViewById(R.id.recyclerWishlist);
        String user_uid = " ";
        if (fAuth.getCurrentUser()!= null)
        {
            user_uid = fAuth.getCurrentUser().getUid();
        }


        Query query = myWish.whereEqualTo("user_uid", user_uid);

        FirestoreRecyclerOptions<WishlistModel> options = new FirestoreRecyclerOptions.Builder<WishlistModel>().setQuery(query, WishlistModel.class).build();
        adapter = new WishlistAdapter(options, this);
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

        /*if (id == R.id.myPlants){
            Intent intent = new Intent (this, MyPlants.class);
            startActivity(intent);
        }*/
        if (id == R.id.log_out){
            FirebaseAuth.getInstance().signOut();
            Intent intent = new Intent(WishlistActivity.this, MainActivity.class);
            startActivity(intent);
            finish();

        }
        return true;

    }
}