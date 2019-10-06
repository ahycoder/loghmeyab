package ir.yousefi.restaurant;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Handler;
import android.view.LayoutInflater;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import co.ronash.pushe.Pushe;
import utility.font.FontConfig;

public class G extends Application {
  public  static Context context;
  public  static SharedPreferences preferences;
  public  static Handler handler;
  public static LayoutInflater layoutInflater;
  public static AppCompatActivity currentActivity;


  @Override
  public void onCreate() {
    super.onCreate();
    Pushe.initialize(this,true);
    context=getApplicationContext();
    preferences = getSharedPreferences("preferences", Context.MODE_PRIVATE);
    handler= new Handler();
    layoutInflater = LayoutInflater.from(context);
    FontConfig.initDefault(new FontConfig.Builder()
      .setDefaultFontPath("fonts/IRANSansMobile.ttf")
      .setFontAttrId(R.attr.fontPath)
      .build());
  }
  public static void showToast(String message){
    Toast.makeText(context,message+"",Toast.LENGTH_SHORT).show();
  }

}
