package activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatRadioButton;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import fragment.FragmentMap;
import ir.yousefi.restaurant.G;
import ir.yousefi.restaurant.R;
import model.StructPlace;
import model.StructPurchaser;
import utility.Constant;

public class InfoPurchaserActivity extends EnhancedActivity implements FragmentMap.OnFragmentMapListener {
  private static final String KEY_ADDRESS="KEY_ADDRESS";
  private LinearLayout layContinue,layInfoView1,layInfoView2,layInfoView3,layInfoView4,layInnerView4;
  private AppCompatEditText edtInfoAddress,edtInfoSubject,edtInfoDesc,edtInfoName,edtInfoPhoneNumber,edtInfoOffCode;
  private TextView txtInfoCheckOffCode;
  private RadioGroup radioGroupInfoPurchases;
  private Parcelable[] parcelables;
  private StructPlace place;
  private double lat,lan;
  private boolean isInLayTwo=false;
  private FloatingActionButton fabInfoAddAddress;
  private int currentLayout=1;
  private  Gson gson;
  private LatLng point;



  private void initView(){
    radioGroupInfoPurchases=findViewById(R.id.radioGroupInfoPurchases);
    layContinue=findViewById(R.id.layContinue);
    layInfoView1=findViewById(R.id.layInfoView1);
    layInfoView2=findViewById(R.id.layInfoView2);
    layInfoView3=findViewById(R.id.layInfoView3);
    layInfoView4=findViewById(R.id.layInfoView4);
    layInnerView4=findViewById(R.id.layInnerView4);
    fabInfoAddAddress=findViewById(R.id.fabInfoAddAddress);
    edtInfoAddress =findViewById(R.id.edtInfoAddress);
    edtInfoSubject=findViewById(R.id.edtInfoSubject);
    edtInfoDesc=findViewById(R.id.edtInfoDesc);
    edtInfoName=findViewById(R.id.edtInfoName);
    edtInfoPhoneNumber=findViewById(R.id.edtInfoPhoneNumber);
    edtInfoOffCode=findViewById(R.id.edtInfoOffCode);
    txtInfoCheckOffCode=findViewById(R.id.txtInfoCheckOffCode);
  }



  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_info_purchaser);
    Bundle bundle =getIntent().getExtras();
    parcelables = bundle.getParcelableArray(Constant.KEY_INTENT_LIST_PURCACE);
    Log.i("TAG","InfoPurchaser : parcelables.length:"+parcelables.length);
    place =bundle.getParcelable(Constant.KEY_INTENT_PLACE);
    gson = new Gson();
    initView();
    ((TextView)findViewById(R.id.txtToolbarTitle)).setText(getResources().getString(R.string.select_location_tollbar)+"");
    findViewById(R.id.imgToolbarBack).setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        finish();
      }
    });

    FragmentMap fragmentMap=FragmentMap.newInstance(place.getLat(),place.getLng(),place.getCourierKm(),place.getName());
    FragmentManager manager = getSupportFragmentManager();
    FragmentTransaction ft = manager.beginTransaction();
    ft.replace(R.id.layInfoView3, fragmentMap, "tag");
    ft.commitAllowingStateLoss();

    readyView1();




    layContinue.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {

        switch (currentLayout){
          case 1:
          {
            String name=edtInfoName.getText().toString();
            String phone=edtInfoPhoneNumber.getText().toString();
            String desc=edtInfoName.getText().toString();
            if (name.length()>0){
              if (iscorrectPhonenumber(phone)){
                if (loadListAdressFromPrefrence().size()>0){
                  readyView4();
                }else {
                  readyView2();
                }
              }else{
                G.showToast(getResources().getString(R.string.select_phone_number));
              }
            }else{
              G.showToast(getResources().getString(R.string.select_name));
            }
          }
            break;
          case 2:
          {
            if (edtInfoSubject.getText().toString().length()>0){
              if (edtInfoAddress.getText().toString().length()>0){
                readyView3();
              }else{
                G.showToast(getResources().getString(R.string.select_address));
              }
            }else{
              G.showToast(getResources().getString(R.string.select_subject));
            }
          }
            break;
          case 3:
          {
            if (point !=null){
                List<StructPurchaser> list = loadListAdressFromPrefrence();
                list.add(new StructPurchaser(edtInfoSubject.getText().toString(), edtInfoAddress.getText().toString(),point));
                saveListAdressInPrefrence(list);
                setListAddressInRadioGroup(loadListAdressFromPrefrence());
                readyView4();
            }else{
              G.showToast(getResources().getString(R.string.select_location));
            }
          }
            break;
          case 4:
          {
            int id =radioGroupInfoPurchases.getCheckedRadioButtonId();
            if (id == -1){
              G.showToast(getResources().getString(R.string.select_one_case));
            }else{
              List<StructPurchaser> list =loadListAdressFromPrefrence();
              for(int i=0; i<list.size(); i++){
                if (id==(i+100)){
                  View radioButton = radioGroupInfoPurchases.findViewById(id);
                  StructPurchaser purchaser =(StructPurchaser)radioButton.getTag();
                  purchaser.setName(edtInfoName.getText().toString());
                  purchaser.setPhoneNumber(edtInfoPhoneNumber.getText().toString());
                  purchaser.setDescription(edtInfoDesc.getText().toString());
                  Intent intent= new Intent(G.currentActivity, FactorActivity.class);
                  intent.putExtra(Constant.KEY_INTENT_PLACE,place);
                  intent.putExtra(Constant.KEY_INTENT_LIST_PURCACE,parcelables);
                  intent.putExtra(Constant.KEY_INTENT_PURCHASER,purchaser);
                  G.currentActivity.startActivity(intent);
                }
              }
            }

          }
            break;
        }
      }
    });
    fabInfoAddAddress.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        readyView2();
      }
    });

  }



  private boolean iscorrectPhonenumber(String phone) {
    return true;
  }

  @Override
  public void onMap(LatLng point)
  {
    this.point=point;
    Log.i("TAG","point:"+point.toString());
  }

  private void showLayoutAndGoneAnotherLayout(LinearLayout showLayout){
    currentLayout= Integer.parseInt(showLayout.getTag().toString());
    layInfoView1.setVisibility(View.GONE);
    layInfoView2.setVisibility(View.GONE);
    layInfoView3.setVisibility(View.GONE);
    layInfoView4.setVisibility(View.GONE);
    showLayout.setVisibility(View.VISIBLE);
  }


  public static List<StructPurchaser> loadListAdressFromPrefrence() {
    List<StructPurchaser> list;
    Gson gson = new Gson();
    String json = G.preferences.getString(KEY_ADDRESS, "");
    if (json.isEmpty()) {
      list = new ArrayList<>();
    } else {
      Type type = new TypeToken<List<StructPurchaser>>() {}.getType();
      list = gson.fromJson(json, type);
    }
    return list;
  }

  public static void saveListAdressInPrefrence(List<StructPurchaser> list) {
    SharedPreferences.Editor prefsEditor = G.preferences.edit();
    Gson gson = new Gson();
    String json = gson.toJson(list);
    prefsEditor.putString(KEY_ADDRESS, json);
    prefsEditor.apply();
  }


  private void setListAddressInRadioGroup(List<StructPurchaser> list){
    radioGroupInfoPurchases.removeAllViews();
    final AppCompatRadioButton[] rb = new AppCompatRadioButton[list.size()];
    radioGroupInfoPurchases.setOrientation(RadioGroup.HORIZONTAL);//or RadioGroup.VERTICAL
    for(int i=0; i<list.size(); i++){
      rb[i]  = new AppCompatRadioButton(this);
      rb[i].setText(" " + list.get(i).getSubject() +"\n"+list.get(i).getAddress());
      rb[i].setTag(list.get(i));
      rb[i].setId(i + 100);
      RadioGroup.LayoutParams params = new RadioGroup.LayoutParams(RadioGroup.LayoutParams.WRAP_CONTENT, RadioGroup.LayoutParams.WRAP_CONTENT);
      params.setMargins(5, 30, 45, 30);
      rb[i].setLayoutParams(params);
      rb[i].setGravity(Gravity.RIGHT | Gravity.CENTER_VERTICAL);
      if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
        rb[i].setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
      }
      radioGroupInfoPurchases.addView(rb[i]);
      radioGroupInfoPurchases.setOrientation(LinearLayout.VERTICAL);
      radioGroupInfoPurchases.setGravity(Gravity.RIGHT);
    }
  }

  private void readyView1(){
    showLayoutAndGoneAnotherLayout(layInfoView1);
    edtInfoName.setText("");
    edtInfoPhoneNumber.setText("");
    edtInfoOffCode.setText("");
    edtInfoDesc.setText("");

  }
  private void readyView2(){
    showLayoutAndGoneAnotherLayout(layInfoView2);
    edtInfoSubject.setText("");
    edtInfoAddress.setText("");

  }
  private void readyView3(){
    showLayoutAndGoneAnotherLayout(layInfoView3);
  }
  private void readyView4(){
    showLayoutAndGoneAnotherLayout(layInfoView4);
    setListAddressInRadioGroup(loadListAdressFromPrefrence());

  }

  @Override
  public void onBackPressed() {
    switch (currentLayout){
      case 1:
        super.onBackPressed();
        break;
      case 2:
        if (loadListAdressFromPrefrence().size()>0){
          readyView4();
        }else {
          readyView1();
        }
        break;
      case 3:
        readyView2();
        break;
      case 4:
        readyView1();
        break;
    }

  }
}
