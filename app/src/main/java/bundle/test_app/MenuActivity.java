package bundle.test_app;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import com.startapp.android.publish.adsCommon.StartAppSDK;




public class MenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        StartAppSDK.init(this, "201337339", true);

        setContentView(R.layout.activity_menu);
    }

    public void GuessCity (View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void GuessCountry (View view) {
        Intent intent = new Intent(this, CountriesActivity.class);
        startActivity(intent);
    }

    public void Quit (View view) {
        finish();
        System.exit(0);
    }

}
