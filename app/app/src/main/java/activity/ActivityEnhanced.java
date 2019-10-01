package activity;

import android.content.Context;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import ir.yousefi.restaurant.G;
import utility.font.FontContextWrapper;


public class ActivityEnhanced extends AppCompatActivity {


  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    G.currentActivity = this;


  }
  @Override
  protected void attachBaseContext(Context newBase) {
    super.attachBaseContext(FontContextWrapper.wrap(newBase));
  }
}
