package activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.allenliu.badgeview.BadgeFactory;
import com.allenliu.badgeview.BadgeView;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import adapter.AdapterComment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import ir.yousefi.restaurant.G;
import ir.yousefi.restaurant.R;
import model.StructComment;
import model.StructFood;
import model.StructPlace;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import utility.RatingBar;
import utility.UTabPlaceFood;
import utility.utilityPurchase;
import webservice.Api;
import webservice.ApiClient;


public class PlaceActivity extends ActivityEnhanced implements PlacefoodFragment.onFragmentFoodListener {
  private UTabPlaceFood uTab;
  private boolean isActive;
  private TextView plcaeTxtTimeReady, plcaeTxtMinOrder,plcaeTxtCourier,plcaeTxtNumberOfComments,plcaeTxtIsActive,plcaeTxtRate,txtStartBuyWholePrice;
  private ImageView placeImgIsActive,plcaeImgLogo,imgStartBuyCart;
  private RatingBar plcaeRatingBar;
  private LinearLayout layCommentInComment,layMenuInComment,layCommentInMenu,layMenu,layMenuInMenu,layComment,layMenuCommentTop,layStartBuyed;
  private RecyclerView recyclerComment;
  private ArrayList<StructFood> foodsPurchase= new ArrayList<>();
  private BadgeView badgeView;
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_place);
    initViewes();


    Bundle bundle =getIntent().getExtras();
    final StructPlace place =bundle.getParcelable("place");
    if (bundle.getParcelableArrayList("purchaseFoods") != null){
        foodsPurchase =bundle.getParcelableArrayList("purchaseFoods");
        handelListPurchaseAndShowInLayStartBuyed(foodsPurchase);
    }else{
      foodsPurchase=new ArrayList<>();
    }
    plcaeTxtTimeReady.setText(place.getTimeReady());
    plcaeTxtMinOrder.setText(place.getMinOrder()+"");
    plcaeTxtCourier.setText(place.getCourier());
    plcaeTxtNumberOfComments.setText(place.getNumberOfComments()+" نظر ");

    if (place.getActive() == 0){
      isActive=false;
      plcaeTxtIsActive.setText(getResources().getString(R.string.place_is_closed));
      placeImgIsActive.setImageDrawable(getResources().getDrawable(R.drawable.circle_red_rounded));
    }
    else{
      isActive=true;
      plcaeTxtIsActive.setText(getResources().getString(R.string.place_is_opened));
      placeImgIsActive.setImageDrawable(getResources().getDrawable(R.drawable.circle_green_rounded));
    }

    //Picasso.get().load(place.getLogoUrl()).error(R.drawable.logo).into(plcaeImgLogo);
    plcaeRatingBar.setRating(place.getPoints()/place.getNumberOfComments());
    plcaeRatingBar.setEnable(false);

    layMenuInComment.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        layComment.setVisibility(View.GONE);
        layMenuCommentTop.setVisibility(View.GONE);
        layMenu.setVisibility(View.VISIBLE);
      }
    });
    layCommentInMenu.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        layComment.setVisibility(View.VISIBLE);
        layMenuCommentTop.setVisibility(View.VISIBLE);
        layMenu.setVisibility(View.GONE);
      }
    });

    ((TextView)findViewById(R.id.txtToolbarTitle)).setText(place.getName()+"");
    findViewById(R.id.imgToolbarBack).setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        finish();
      }
    });

    uTab = new UTabPlaceFood(this, R.id.viewPagerPlaceFoods, R.id.tabLayout);
    ApiClient.getClient().create(Api.class).getPlace("Place_Foods",place.getId()+"").enqueue(new Callback<List<StructFood>>() {
      @Override
      public void onResponse(Call<List<StructFood>> call, Response<List<StructFood>> response) {
        if (response.body() != null && response.body().size() > 0) {
          Set<String> menuNames = new HashSet<>();
          for (StructFood food :response.body()){
            menuNames.add(food.getMenuName());
          }
          for (String menuName :menuNames){
              ArrayList<StructFood> foods= new ArrayList<>();
             for (StructFood food :response.body()){
               if (food.getMenuName().equals(menuName))foods.add(food);
             }
             uTab.add(menuName,foods,isActive,foodsPurchase);
          }
        }
      }

      @Override
      public void onFailure(Call<List<StructFood>> call, Throwable t) {

      }
    });



    ApiClient.getClient().create(Api.class).getCommentsOfPlace("Place_Comments",place.getId()+"").enqueue(new Callback<List<StructComment>>() {
      @Override
      public void onResponse(Call<List<StructComment>> call, Response<List<StructComment>> response) {
        AdapterComment adapterComment = new AdapterComment(response.body());
        recyclerComment.setAdapter(adapterComment);
        adapterComment.notifyDataSetChanged();
      }

      @Override
      public void onFailure(Call<List<StructComment>> call, Throwable t) {

      }
    });

    layStartBuyed.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        Log.i("TAG","FactorPrice:"+utilityPurchase.getFactorPrice(foodsPurchase)+"    min:"+place.getMinOrder());
        if(utilityPurchase.getFactorPrice(foodsPurchase)<place.getMinOrder()){
          Toast.makeText(G.currentActivity,getResources().getString(R.string.order_is_lowwer_min)+"",Toast.LENGTH_SHORT).show();
        }else{
          Intent intent= new Intent(G.currentActivity, PurchasesActivity.class);
          intent.putExtra("Purchases",utilityPurchase.getPurchases(foodsPurchase));
          intent.putExtra("place",place);
          intent.putExtra("WohleCount",utilityPurchase.getWohleCount(foodsPurchase));
          G.currentActivity.startActivity(intent);
          finish();
        }

      }
    });

  }

  private void initViewes() {
    plcaeTxtTimeReady=findViewById(R.id.plcaeTxtTimeReady);
    plcaeTxtMinOrder =findViewById(R.id.plcaeTxtMinOrder);
    plcaeTxtCourier=findViewById(R.id.plcaeTxtCourier);
    plcaeTxtNumberOfComments=findViewById(R.id.plcaeTxtNumberOfComments);
    plcaeTxtIsActive=findViewById(R.id.plcaeTxtIsActive);
    plcaeTxtRate=findViewById(R.id.plcaeTxtRate);
    txtStartBuyWholePrice=findViewById(R.id.txtStartBuyWholePrice);
    placeImgIsActive=findViewById(R.id.placeImgIsActive);
    plcaeImgLogo=findViewById(R.id.plcaeImgLogo);
    imgStartBuyCart=findViewById(R.id.imgStartBuyCart);
    plcaeRatingBar=findViewById(R.id.plcaeRatingBar);
    layCommentInComment=findViewById(R.id.layCommentInComment);
    layMenuInComment=findViewById(R.id.layMenuInComment);
    layCommentInMenu=findViewById(R.id.layCommentInMenu);
    layMenuInMenu=findViewById(R.id.layMenuInMenu);
    layMenuCommentTop=findViewById(R.id.layMenuCommentTop);
    layMenu=findViewById(R.id.layMenu);
    layComment=findViewById(R.id.layComment);
    layStartBuyed=findViewById(R.id.layStartBuyed);
    recyclerComment=findViewById(R.id.recyclerComment);
    recyclerComment.setLayoutManager(new LinearLayoutManager(this));
    badgeView = BadgeFactory.createCircle(this);
  }




  @Override
  public void onFoodListener(StructFood food, int count) {
    handelListPurchaseAndShowInLayStartBuyed(utilityPurchase.updateListPurchase(foodsPurchase,food,count));
  }

  private void handelListPurchaseAndShowInLayStartBuyed(ArrayList<StructFood> list){
    if (list.size()>0){
      layStartBuyed.setVisibility(View.VISIBLE);
      txtStartBuyWholePrice.setText(utilityPurchase.getFactorPrice(list)+getResources().getString(R.string.tooman));
      badgeView.setBadgeCount(utilityPurchase.getWohleCount(list))
        .setTextColor(getResources().getColor(R.color.colorWhite))
        .setWidthAndHeight(15,15)
        .setBadgeBackground(getResources().getColor(R.color.colorPrimary))
        .setTextSize(10)
        .setBadgeGravity(Gravity.LEFT|Gravity.TOP)
        .bind(imgStartBuyCart);
    }else {
      layStartBuyed.setVisibility(View.GONE);
    }
  }
}
