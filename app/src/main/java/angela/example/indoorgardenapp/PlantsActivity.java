package angela.example.indoorgardenapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class PlantsActivity extends AppCompatActivity {

    RecyclerView myRecyclerView;

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference plantsRef = db.collection("Plant_Collection");
    private PlantsAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plants);

        Toolbar myToolbar = findViewById(R.id.myToolbar);
        setSupportActionBar(myToolbar);
        FloatingActionButton fab = findViewById(R.id.shopping_btn);


        setUpRecyclerView();






        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(PlantsActivity.this, "KLIKNA NA SHOPPING", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(PlantsActivity.this, ShoppingCartActivity.class);

                startActivity(intent);
            }
        });

    }

    private void setUpRecyclerView() {
        myRecyclerView = findViewById(R.id.recyclerPlants);

        Query query = plantsRef.orderBy("price", Query.Direction.DESCENDING);

        FirestoreRecyclerOptions<PlantModel> options = new FirestoreRecyclerOptions.Builder<PlantModel>().setQuery(query, PlantModel.class).build();
        adapter = new PlantsAdapter(options, this);
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
        getMenuInflater().inflate(R.menu.menu, menu);
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

       /* if (id == R.id.myPlants){
            Intent intent = new Intent (this, MyPlants.class);
            startActivity(intent);
        }*/

        if (id == R.id.log_out){
            FirebaseAuth.getInstance().signOut();
            Intent intent = new Intent(PlantsActivity.this, MainActivity.class);
            startActivity(intent);
            finish();

        }

        return true;

    }

}