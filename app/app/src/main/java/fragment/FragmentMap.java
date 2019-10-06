package fragment;

import android.content.Context;
import android.graphics.Color;
import android.location.Location;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import ir.yousefi.restaurant.G;
import ir.yousefi.restaurant.R;

public class FragmentMap extends Fragment {
  private OnFragmentMapListener mListener;
  private static final String ARG_PLACE_LAT = "param1";
  private static final String ARG_PLACE_LNG = "param2";
  private static final String ARG_PLACE_ORDER_RANG = "param3";
  private static final String ARG_PLACE_NAME = "param4";
  private Circle circle;
  private double latPlace;
  private double lngPlace;
  private String placeName;
  private CircleOptions circleOptions;
  private double radius=4000;
  private MarkerOptions markerOptionsPlace;
  private LatLng centerLocation;
  public FragmentMap() { }
  public static FragmentMap newInstance(double latPlace, double lngPlace,double orderRange,String placeName) {
    FragmentMap fragment = new FragmentMap();
    Bundle args = new Bundle();
    args.putDouble(ARG_PLACE_LAT,latPlace);
    args.putDouble(ARG_PLACE_LNG,lngPlace);
    args.putDouble(ARG_PLACE_ORDER_RANG,orderRange);
    args.putString(ARG_PLACE_NAME,placeName);
    fragment.setArguments(args);
    return fragment;
  }
  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    if (getArguments() != null) {
      latPlace=getArguments().getDouble(ARG_PLACE_LAT);
      lngPlace=getArguments().getDouble(ARG_PLACE_LNG);
      double orderRange =getArguments().getDouble(ARG_PLACE_ORDER_RANG)*1000;
      if (orderRange !=0){
        radius=orderRange;
        centerLocation= new LatLng(latPlace, lngPlace);
      }else {
        centerLocation= new LatLng(32.65, 51.66);
      }
      placeName=getArguments().getString(ARG_PLACE_NAME);
    }

    circleOptions=new CircleOptions()
      .center(centerLocation).radius(radius).
      strokeColor(getResources().getColor(R.color.colorPrimary))
      .fillColor(Color.TRANSPARENT);



  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    View root = inflater.inflate(R.layout.fragment_map, container, false);
    SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.frg);  //use SuppoprtMapFragment for using in fragment instead of activity  MapFragment = activity   SupportMapFragment = fragment
    mapFragment.getMapAsync(new OnMapReadyCallback() {
      @Override
      public void onMapReady(final GoogleMap googleMap) {
        circle = googleMap.addCircle(circleOptions);
        markerOptionsPlace=new MarkerOptions()
          .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE))
          .position(new LatLng(latPlace,lngPlace)).title(placeName);
        googleMap.addMarker(markerOptionsPlace).showInfoWindow();
        CameraPosition position= CameraPosition.builder()
          .target(centerLocation)
          .zoom(12)
          .build();

        googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(position));
        googleMap.getUiSettings().setZoomControlsEnabled(true);
        googleMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
          @Override
          public void onMapClick(LatLng point) {
            googleMap.clear();
            googleMap.addCircle(circleOptions);
            googleMap.addMarker(markerOptionsPlace).showInfoWindow();
            int distance =distanceTwoPoint(centerLocation,point);
            int radius =(int)circle.getRadius();
            double result =distance-radius;
            if (result>0){
              G.showToast(getResources().getString(R.string.select_location_in_range_place));
            }else{
              googleMap.addMarker(new MarkerOptions().position(point).title(getResources().getString(R.string.i_am_here)));
              if (mListener != null) {
                mListener.onMap(point);
              }
            }
          }
        });
      }
    });

    return root;
  }
  @Override
  public void onAttach(@NonNull Context context) {
    super.onAttach(context);
    if (context instanceof OnFragmentMapListener) {
      mListener = (OnFragmentMapListener) context;
    } else {
      throw new RuntimeException(context.toString()
        + " must implement OnFragmentInteractionListener");
    }
  }
  @Override
  public void onDetach() {
    super.onDetach();
    mListener = null;
  }

  public interface OnFragmentMapListener {
    void onMap(LatLng point);
  }
  private int distanceTwoPoint(LatLng point1 , LatLng point2){
    float[] results = new float[1];
    Location.distanceBetween(point1.latitude, point1.longitude, point2.latitude, point2.longitude, results);
    return (int) results[0];
  }
}
