package angela.example.indoorgardenapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ConfirmationPageActivity extends AppCompatActivity {

    TextView order_num;
    Button plants;
    String order_id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirmation_page);
        Toolbar myToolbar = findViewById(R.id.myToolbar);
        setSupportActionBar(myToolbar);

        order_num = findViewById(R.id.order_num);

        getData();
        setData();


        plants = findViewById(R.id.plans_btn);

        plants.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ConfirmationPageActivity.this, PlantsActivity.class);
                startActivity(intent);
            }
        });
    }

    private void setData() {
        order_num.setText(order_id);
    }

    private void getData() {
        Intent intent = getIntent();
        if(intent.hasExtra("order_id")){
            order_id = intent.getStringExtra("order_id");

        }
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

        /*if (id == R.id.myPlants){
            Intent intent = new Intent (this, MyPlants.class);
            startActivity(intent);
        }*/
        return true;

    }
}