package activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import androidx.fragment.app.FragmentActivity;
import ir.yousefi.restaurant.G;
import ir.yousefi.restaurant.R;
import model.StructPlace;
import utility.font.FontContextWrapper;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

  private GoogleMap mMap;
  private TextView txtContinue;
  private LinearLayout layContinue;
  private Parcelable[] parcelables;
  private StructPlace place;
  private LatLng pointSelected;

  @Override
  protected void attachBaseContext(Context newBase) {
    super.attachBaseContext(FontContextWrapper.wrap(newBase));
  }

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_maps);
    Bundle bundle =getIntent().getExtras();
    parcelables = bundle.getParcelableArray("Purchases");
    place =bundle.getParcelable("place");

    SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
    mapFragment.getMapAsync(this);

    ((TextView)findViewById(R.id.txtToolbarTitle)).setText(getResources().getString(R.string.select_location));
    findViewById(R.id.imgToolbarBack).setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        finish();
      }
    });

     txtContinue=findViewById(R.id.txtContinue);
     layContinue=findViewById(R.id.layContinue);

    layContinue.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        Intent intent= new Intent(G.currentActivity, infoPurchaser.class);
        intent.putExtra("place",place);
        intent.putExtra("purchaseFoods",parcelables);
        intent.putExtra("purchaseFoods",parcelables);
        intent.putExtra("lat",pointSelected.latitude);
        intent.putExtra("lan",pointSelected.longitude);
        G.currentActivity.startActivity(intent);
      }
    });
  }

  @Override
  public void onMapReady(GoogleMap googleMap) {
    mMap = googleMap;

    LatLng myLocation = new LatLng(32.65, 51.66);
    CameraPosition position= CameraPosition.builder()
      .target(myLocation)
      .zoom(14)
      .build();
    mMap.animateCamera(CameraUpdateFactory.newCameraPosition(position));
    mMap.getUiSettings().setZoomControlsEnabled(true);
    mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
      @Override
      public void onMapClick(LatLng point) {
        layContinue.setVisibility(View.VISIBLE);
        mMap.clear();
        mMap.addMarker(new MarkerOptions().position(point).title(getResources().getString(R.string.i_am_here)));
        pointSelected=point;
      }
    });
  }


}
