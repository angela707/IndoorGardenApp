package angela.example.indoorgardenapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;

import java.util.ArrayList;
import java.util.List;

public class PlantInformationActivity extends AppCompatActivity {

    PlantModel p;
    String image1, image2, image3;
    TextView pname, pdescription, pprice;
    SliderView sliderView;
    String []images;
    SliderAdapter sliderAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plant_information);

        sliderView = findViewById(R.id.image_slider);

        pname = findViewById(R.id.pname);
        pdescription = findViewById(R.id.pdesc);
        pprice = findViewById(R.id.pprice);


        getData();
        setData();

        images = new String[]{image1, image2, image3};

        sliderAdapter = new SliderAdapter(this, images);
        sliderView.setSliderAdapter(sliderAdapter);
        sliderView.setIndicatorAnimation(IndicatorAnimationType.WORM);
        sliderView.setSliderTransformAnimation(SliderAnimations.DEPTHTRANSFORMATION);
        sliderView.startAutoCycle();



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
        if (getIntent().hasExtra("plantInfo"))
        {

            p = (PlantModel) getIntent().getSerializableExtra("plantInfo");

        }
        else {
            Toast.makeText(this, "No data", Toast.LENGTH_SHORT).show();
        }

    }
}