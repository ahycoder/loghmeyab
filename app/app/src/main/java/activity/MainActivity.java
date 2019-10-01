package activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import adapter.AdapterPlaces;
import adapter.AdapterTypeFoods;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;
import adapter.MainAdapterViewPager;
import ir.yousefi.restaurant.R;
import model.StructPlace;
import model.StructTypeFood;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import webservice.Api;
import webservice.ApiClient;

public class MainActivity extends ActivityEnhanced {
  private RecyclerView recyclerMainPlacesOffer,recyclerMainPlacesNew,recyclerMainPlacesOff,recyclerMainPlacesProgressSuccessful,recyclerMainTypesFood;
  private AdapterPlaces adapterPlaces;
  private AdapterTypeFoods adapterTypeFoods;


  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    recyclerMainPlacesOffer =findViewById(R.id.recyclerMainPlacesOffer);
    recyclerMainPlacesNew =findViewById(R.id.recyclerMainPlacesNew);
    recyclerMainPlacesOff =findViewById(R.id.recyclerMainPlacesOff);
    recyclerMainPlacesProgressSuccessful =findViewById(R.id.recyclerMainPlacesProgressSuccessful);
    recyclerMainTypesFood =findViewById(R.id.recyclerMainTypesFood);
    recyclerMainPlacesOffer.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, true));
    recyclerMainPlacesNew.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, true));
    recyclerMainPlacesOff.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, true));
    recyclerMainPlacesProgressSuccessful.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, true));
    recyclerMainTypesFood.setLayoutManager(new GridLayoutManager(this, 2,RecyclerView.HORIZONTAL, true));




      findViewById(R.id.imgToolbarMenu).setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
          ((DrawerLayout) findViewById(R.id.drawer_layout)).openDrawer(Gravity.RIGHT);
        }
      });


    ViewPager viewPager =findViewById(R.id.mainViewPager);
    ArrayList<String> listUrlImage = new ArrayList<>();
    listUrlImage.add("http://192.168.1.103/loghmeyab/image/1.jpg");
    listUrlImage.add("http://192.168.1.103/loghmeyab/image/2.jpg");
    viewPager.setAdapter(new MainAdapterViewPager(this,listUrlImage));



    View.OnClickListener listener = new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        view.getTag();
        Intent intent= new Intent(MainActivity.this,ListPlaceActivity.class);
        intent.putExtra("kind",view.getTag().toString());
        MainActivity.this.startActivity(intent);
      }
    };
    findViewById(R.id.imgRestaurant).setOnClickListener(listener);
    findViewById(R.id.imgFastfood).setOnClickListener(listener);
    findViewById(R.id.imgSweet).setOnClickListener(listener);
    findViewById(R.id.imgCafe).setOnClickListener(listener);
    findViewById(R.id.imgBreakfast).setOnClickListener(listener);




    initiliazeRecycler("Places_New",recyclerMainPlacesNew);
    initiliazeRecycler("Places_Offer",recyclerMainPlacesOffer);
    initiliazeRecycler("Foods_Off",recyclerMainPlacesOff);
    initiliazeRecycler("Places",recyclerMainPlacesProgressSuccessful);



    ApiClient.getClient().create(Api.class).getTypesFood("Types").enqueue(new Callback<List<StructTypeFood>>() {
      @Override
      public void onResponse(Call<List<StructTypeFood>> call, Response<List<StructTypeFood>> response) {
        if (response.body() != null && response.body().size() > 0) {
        adapterTypeFoods = new AdapterTypeFoods(response.body());
        recyclerMainTypesFood.setAdapter(adapterTypeFoods);
        adapterTypeFoods.notifyDataSetChanged();
        }
      }

      @Override
      public void onFailure(Call<List<StructTypeFood>> call, Throwable t) {

      }
    });

  }

  private void initiliazeRecycler(String key,final RecyclerView recyclerView){
    ApiClient.getClient().create(Api.class).getPlaces(key).enqueue(new Callback<List<StructPlace>>() {
      @Override
      public void onResponse(Call<List<StructPlace>> call, Response<List<StructPlace>> response) {
        if (response.isSuccessful()){
          if (response.body() != null && response.body().size() > 0) {
            adapterPlaces = new AdapterPlaces(response.body(),R.layout.item_place_2);
            recyclerView.setAdapter(adapterPlaces);
            adapterPlaces.notifyDataSetChanged();
          }
        }
      }
      @Override
      public void onFailure(Call<List<StructPlace>> call, Throwable t) {}
    });
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    menu.add(Menu.NONE, 1, Menu.NONE, "Item name");
    return true;
  }
}
