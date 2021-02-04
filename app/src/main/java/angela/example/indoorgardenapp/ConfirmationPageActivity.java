package angela.example.indoorgardenapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

public class ConfirmationPageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirmation_page);
        Toolbar myToolbar = findViewById(R.id.myToolbar);
        setSupportActionBar(myToolbar);
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

        if (id == R.id.myPlants){
            Intent intent = new Intent (this, MyPlants.class);
            startActivity(intent);
        }
        return true;

    }
}