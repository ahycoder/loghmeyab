package activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

import ir.yousefi.restaurant.G;
import ir.yousefi.restaurant.R;
import model.StructFood;
import model.StructPlace;
import model.StructPurchaser;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import utility.Constant;
import utility.utilityPurchase;
import webservice.Api;
import webservice.ApiClient;

public class FactorActivity extends EnhancedActivity {
  private Parcelable[] parcelables;
  private StructPlace place;
  private StructPurchaser purchaser;
  private TextView txtFactoreUserPhone,txtFactoreReciverPhone,txtFactoreAddress,txtFactoreName,txtFactorePrice,txtFactoreOff,txtFactoreCostCourier,txtFactoreFinalPrice;
  private LinearLayout layFactorePay;
  private JSONArray jsonPurchase;
  private static final int RESULT_BACK_BROWSER=100;
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_factore);
    Bundle bundle =getIntent().getExtras();
    parcelables = bundle.getParcelableArray(Constant.KEY_INTENT_LIST_PURCACE);
    place =bundle.getParcelable(Constant.KEY_INTENT_PLACE);
    purchaser =bundle.getParcelable(Constant.KEY_INTENT_PURCHASER);
   ArrayList<StructFood> list = new ArrayList<>();
    Parcelable[] array=parcelables;
    for (Parcelable parcelable : array) {
      list.add((StructFood) parcelable);
    }
    initViews();
    txtFactoreName.setText(purchaser.getName()+"");
    txtFactoreUserPhone.setText("from login");
    txtFactoreReciverPhone.setText(purchaser.getPhoneNumber()+"");
    txtFactoreAddress.setText(purchaser.getAddress()+"");
    txtFactorePrice.setText(utilityPurchase.getWholePrice(list)+"");
    txtFactoreOff.setText(utilityPurchase.getOffPrice(list)+"");
    txtFactoreCostCourier.setText(place.getCourierPrice()+"");
    txtFactoreFinalPrice.setText(utilityPurchase.getFactorPrice(list)+"");


    Gson gson = new Gson();
    String listString = gson.toJson(list, new TypeToken<ArrayList<StructFood>>() {}.getType());
    try {
      jsonPurchase = new JSONArray(listString);
    } catch (JSONException e) {
      e.printStackTrace();
    }
    Log.i("TAG", "jsonPurchase:" + jsonPurchase);
    layFactorePay.setOnClickListener(new View.OnClickListener() {
       @Override
       public void onClick(View v) {
         ApiClient.getClient().create(Api.class).goToPay("Go_To_Pay_Purchase", place.getId(), jsonPurchase).enqueue(new Callback<JsonObject>() {
           @Override
           public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
             int codeResponse = response.body().get("code").getAsInt();
             switch (codeResponse) {
               case Constant.CODE_PLACE_IS_ACTIVE_FALSE:
                 G.showToast(place.getName() + " " + getResources().getString(R.string.closed));
                 Intent intentMain = new Intent(G.currentActivity, MainActivity.class);
                 G.currentActivity.startActivity(intentMain);
                 break;
               case Constant.CODE_LIST_PURCHASE_CHANGE:
                 G.showToast(" " + getResources().getString(R.string.change_your_list_purchase));
                 G.showToast(place.getName() + " " + getResources().getString(R.string.closed));
                 Intent intentListPurchases = new Intent(G.currentActivity, ListPurchasesActivity.class);
                 G.currentActivity.startActivity(intentListPurchases);
                 break;
               case Constant.CODE_ERROR_FOR_PAY:
                 G.showToast(" " + getResources().getString(R.string.error_for_pay));
                 break;
               case Constant.CODE_CONTINUE_FOR_PAY:
                 Intent openUrlIntent = new Intent(Intent.ACTION_VIEW);
                 openUrlIntent.addFlags(Intent.FLAG_ACTIVITY_PREVIOUS_IS_TOP);
                 openUrlIntent.setData(Uri.parse(response.body().get("result").getAsString()));
                 startActivity(openUrlIntent);
                 break;
             }
           }

           @Override
           public void onFailure(Call<JsonObject> call, Throwable t) {
             Log.i("TAG", "onFailure:" + t.getMessage());
           }
         });

       }
     });

    Intent in = getIntent();
    Uri data = in.getData();
    if (data != null) {
        Toast.makeText(getBaseContext(), "موفقیت", Toast.LENGTH_LONG).show();
    }
  }


  private void initViews(){
    txtFactoreUserPhone =findViewById(R.id.txtFactoreUserPhone);
    txtFactoreReciverPhone =findViewById(R.id.txtFactoreReciverPhone);
    txtFactoreAddress =findViewById(R.id.txtFactoreAddress);
    txtFactoreName =findViewById(R.id.txtFactoreName);
    txtFactorePrice =findViewById(R.id.txtFactorePrice);
    txtFactoreOff =findViewById(R.id.txtFactoreOff);
    txtFactoreCostCourier =findViewById(R.id.txtFactoreCostCourier);
    txtFactoreFinalPrice =findViewById(R.id.txtFactoreFinalPrice);
    layFactorePay =findViewById(R.id.layFactorePay);
  }
}
