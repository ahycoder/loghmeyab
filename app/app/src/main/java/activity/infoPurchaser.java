package activity;

import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatEditText;
import ir.yousefi.restaurant.G;
import ir.yousefi.restaurant.R;
import model.StructPlace;

public class infoPurchaser extends AppCompatActivity {
  private LinearLayout layContinue,layInfoTwo,layInfoOne;
  private AppCompatEditText edtInfoiAddress,edtInfoSubject,edtInfiDesc,edtInfoName,edtInfoPhoneNumber,edtInfoOffCode;
  private TextView txtInfoCheckOffCode;
  private Parcelable[] parcelables;
  private StructPlace place;
  private double lat,lan;
  private boolean isInLayTwo=false;

  private void initView(){
    layContinue=findViewById(R.id.layContinue);
    layInfoTwo=findViewById(R.id.layInfoTwo);
    layInfoOne=findViewById(R.id.layInfoOne);
    edtInfoiAddress=findViewById(R.id.edtInfoiAddress);
    edtInfoSubject=findViewById(R.id.edtInfoSubject);
    edtInfiDesc=findViewById(R.id.edtInfiDesc);
    edtInfoName=findViewById(R.id.edtInfoName);
    edtInfoPhoneNumber=findViewById(R.id.edtInfoPhoneNumber);
    edtInfoOffCode=findViewById(R.id.edtInfoOffCode);
    txtInfoCheckOffCode=findViewById(R.id.txtInfoCheckOffCode);
  }


  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_info_purchaser);
    initView();
    Bundle bundle =getIntent().getExtras();
    parcelables = bundle.getParcelableArray("Purchases");
    place =bundle.getParcelable("place");
    lat=bundle.getDouble("lat");
    lan =bundle.getDouble("lan");

    layContinue.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        if (!isInLayTwo){
          String name=edtInfoName.getText().toString();
          String phone=edtInfoPhoneNumber.getText().toString();
          String desc=edtInfoName.getText().toString();
          if (name.length()>0){
            if (iscorrectPhonenumber(phone)){
              layInfoOne.setVisibility(View.GONE);
              layInfoTwo.setVisibility(View.VISIBLE);
              isInLayTwo=true;
            }else{
              G.showToast(getResources().getString(R.string.select_phone_number));
            }
          }else{
            G.showToast(getResources().getString(R.string.select_name));
          }
        }else {
          String subject=edtInfoSubject.getText().toString();
          String adress=edtInfoiAddress.getText().toString();
          if (subject.length()>0){
            if (adress.length()>0){
              ///////////////////////////////////////////startActivity next
            }else{
              G.showToast(getResources().getString(R.string.select_address));
            }
          }else{
            G.showToast(getResources().getString(R.string.select_subject));
          }
        }

      }
    });

  }

  @Override
  public void onBackPressed() {
    if (isInLayTwo){
      layInfoOne.setVisibility(View.VISIBLE);
      layInfoTwo.setVisibility(View.GONE);
      isInLayTwo=false;
    }else{
       super.onBackPressed();
    }

  }

  private boolean iscorrectPhonenumber(String phone) {
    return true;
  }
}
