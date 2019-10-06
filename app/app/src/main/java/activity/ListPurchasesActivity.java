package activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import adapter.AdapterPurchase;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import ir.yousefi.restaurant.G;
import ir.yousefi.restaurant.R;
import model.StructFood;
import model.StructPlace;
import utility.Constant;
import utility.utilityPurchase;

public class ListPurchasesActivity extends EnhancedActivity {

  private TextView txtPurchasesWohleCount,txtPurchasesWohlePriceOrder,txtPurchasesWholeOffPrice,txtPurchasesFactorePrice,txtPurchaseComtinuOrMinOrder;
  private LinearLayout layPurchaseComtinuOrMinOrder;
  private ArrayList<StructFood> list = new ArrayList<>();
  private StructPlace place;
  private int wohleCount;
  private RecyclerView recyclerPurchase;
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_purchases);
    Bundle bundle =getIntent().getExtras();
    if (bundle !=null){
      Parcelable[] parcelables =bundle.getParcelableArray(Constant.KEY_INTENT_LIST_PURCACE);
      for (Parcelable parcelable : parcelables) {
        list.add((StructFood) parcelable);
      }
      place =bundle.getParcelable(Constant.KEY_INTENT_PLACE);
      wohleCount=bundle.getInt(Constant.KEY_INTENT_WOHLE_COUNT_LIST_FOOD);
    }
    initView();
    txtPurchasesFactorePrice.setText(utilityPurchase.getFactorPrice(list)+getResources().getString(R.string.tooman));
    txtPurchasesWohlePriceOrder.setText(utilityPurchase.getWholePrice(list)+getResources().getString(R.string.tooman));
    txtPurchasesWholeOffPrice.setText(utilityPurchase.getOffPrice(list)+getResources().getString(R.string.tooman));
    txtPurchasesWohleCount.setText(utilityPurchase.getWohleCount(list)+"");

    final AdapterPurchase adapterPurchase= new AdapterPurchase(list);
    recyclerPurchase.setAdapter(adapterPurchase);
    adapterPurchase.setListener(new AdapterPurchase.OnSelectedPurchaseListener() {
        @Override
        public void onSelectedPurchase(StructFood food, int count, int position) {
          list =utilityPurchase.updateListPurchase(list,food,count);
          adapterPurchase.notifyDataSetChanged();

          if (list.size()==0){
            try {
              Thread.sleep(1000);
            } catch (InterruptedException e) {
              e.printStackTrace();
            }
            Intent intent = new Intent(G.currentActivity, PlaceActivity.class);
            intent.putExtra(Constant.KEY_INTENT_PLACE,place);
            G.currentActivity.startActivity(intent);
            finish();
          }else {
            if (utilityPurchase.getFactorPrice(list)<place.getMinOrder()){
              txtPurchaseComtinuOrMinOrder.setText(getResources().getString(R.string.order_is_lowwer_min));
              layPurchaseComtinuOrMinOrder.setClickable(false);
            }else{
              txtPurchaseComtinuOrMinOrder.setText(getResources().getString(R.string.save_info_continue_pay));
              layPurchaseComtinuOrMinOrder.setClickable(true);
            }

            txtPurchasesFactorePrice.setText(utilityPurchase.getFactorPrice(list)+getResources().getString(R.string.tooman));
            txtPurchasesWohlePriceOrder.setText(utilityPurchase.getWholePrice(list)+getResources().getString(R.string.tooman));
            txtPurchasesWholeOffPrice.setText(utilityPurchase.getOffPrice(list)+getResources().getString(R.string.tooman));
            txtPurchasesWohleCount.setText(utilityPurchase.getWohleCount(list)+"");
          }

        }
      });
      adapterPurchase.notifyDataSetChanged();


    layPurchaseComtinuOrMinOrder.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        Intent intent= new Intent(G.currentActivity, InfoPurchaserActivity.class);
        intent.putExtra(Constant.KEY_INTENT_PLACE,place);
        intent.putExtra(Constant.KEY_INTENT_LIST_PURCACE,utilityPurchase.getPurchases(list));
        G.currentActivity.startActivity(intent);
      }
    });

  }




  private void initView(){
    txtPurchasesWohleCount=findViewById(R.id.txtPurchasesWohleCount);
    txtPurchasesWohlePriceOrder=findViewById(R.id.txtPurchasesWohlePriceOrder);
    txtPurchasesWholeOffPrice=findViewById(R.id.txtPurchasesWholeOffPrice);
    txtPurchasesFactorePrice=findViewById(R.id.txtPurchasesFactorePrice);
    txtPurchaseComtinuOrMinOrder=findViewById(R.id.txtPurchaseComtinuOrMinOrder);
    layPurchaseComtinuOrMinOrder=findViewById(R.id.layPurchaseComtinuOrMinOrder);
    recyclerPurchase=findViewById(R.id.recyclerPurchase);
    recyclerPurchase.setLayoutManager(new LinearLayoutManager(this));
  }

  @Override
  public void onBackPressed() {
    Intent intent = new Intent(G.currentActivity, PlaceActivity.class);
    intent.putExtra(Constant.KEY_INTENT_PLACE,place);
    intent.putExtra(Constant.KEY_INTENT_LIST_PURCACE,list);
    G.currentActivity.startActivity(intent);
    finish();
  }
}
