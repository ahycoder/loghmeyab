package activity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.util.List;

import adapter.AdapterPlaces;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import ir.yousefi.restaurant.R;
import model.StructPlace;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import webservice.Api;
import webservice.ApiClient;

public class ListPlaceActivity extends AppCompatActivity {

  private RecyclerView recyclerPlace;
  private AdapterPlaces adapterPlaces;
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_list_place);
        Bundle bundle =getIntent().getExtras();
        String kind =bundle.getString("kind");
        recyclerPlace =findViewById(R.id.recyclerPlace);
        recyclerPlace.setLayoutManager(new LinearLayoutManager(this));
        ((TextView)findViewById(R.id.txtToolbarTitle)).setText(getResources().getString(R.string.select_whit_you));
        findViewById(R.id.imgToolbarBack).setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
            finish();
          }
        });

        ApiClient.getClient().create(Api.class).getPlaces("Places_Kinds", kind).enqueue(new Callback<List<StructPlace>>() {
          @Override
          public void onResponse(Call<List<StructPlace>> call, Response<List<StructPlace>> response) {
            if (response.isSuccessful()) {
              if (response.body() != null && response.body().size() > 0) {
                adapterPlaces = new AdapterPlaces(response.body(),R.layout.item_place);
                recyclerPlace.setAdapter(adapterPlaces);
                adapterPlaces.notifyDataSetChanged();
              }
            }
          }

          @Override
          public void onFailure(Call<List<StructPlace>> call, Throwable t) {
          }
        });
  }
}
