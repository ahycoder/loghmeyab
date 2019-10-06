package fragment;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import adapter.AdapterFood;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import ir.yousefi.restaurant.G;
import ir.yousefi.restaurant.R;
import model.StructFood;


public class FragmentPlacefood extends Fragment {

  public interface onFragmentFoodListener{
    void onFoodListener(StructFood food,int number);
  }
  private onFragmentFoodListener mListener;
  private static final String ARG_PARAM1 = "param1";
  private static final String ARG_PARAM2 = "param2";
  private static final String ARG_PARAM3 = "param3";
  private static final String ARG_PARAM4 = "param4";
  private ArrayList<StructFood> list;
  private ArrayList<StructFood> purchaseFoods;
  private String title;
  private RecyclerView recyclerFragmentPlaceFood;
  private AdapterFood adapterFood;
  private boolean isActivePlace;
  public FragmentPlacefood() { }

  public static FragmentPlacefood newInstance(ArrayList<StructFood> list, String title, boolean isActivePlace, ArrayList<StructFood> purchaseFoods) {
    FragmentPlacefood fragment = new FragmentPlacefood();
    Bundle args = new Bundle();
    args.putParcelableArrayList(ARG_PARAM1,list);
    args.putString(ARG_PARAM2,title);
    args.putBoolean(ARG_PARAM3,isActivePlace);
    args.putParcelableArrayList(ARG_PARAM4,purchaseFoods);
    fragment.setArguments(args);
    return fragment;
  }

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    if (getArguments() != null) {
      list = getArguments().getParcelableArrayList(ARG_PARAM1);
      purchaseFoods = getArguments().getParcelableArrayList(ARG_PARAM4);
      title = getArguments().getString(ARG_PARAM2);
      isActivePlace =getArguments().getBoolean(ARG_PARAM3);
    }
  }

  @Override
  public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    if (purchaseFoods != null) Log.i("TAG","fragment purchaseFoods size:"+purchaseFoods.size());
    View root =inflater.inflate(R.layout.fragment_placefood, container, false);
     recyclerFragmentPlaceFood=root.findViewById(R.id.recyclerFragmentPlaceFood);
    recyclerFragmentPlaceFood.setLayoutManager(new LinearLayoutManager(G.currentActivity));
    adapterFood = new AdapterFood(list, isActivePlace, new AdapterFood.OnSelectedFoodListener() {
      @Override
      public void onSelectedFood(StructFood food, int number) {
        if (mListener != null) {
             mListener.onFoodListener(food,number);
         }
      }
    },purchaseFoods);
    recyclerFragmentPlaceFood.setAdapter(adapterFood);
    adapterFood.notifyDataSetChanged();


//    if (mListener != null) {
//      mListener.onFoodListener(uri);
//    }

    return root;
  }
  @Override
  public void onAttach(@NonNull Context context) {
    super.onAttach(context);
    if (context instanceof onFragmentFoodListener) {
      mListener = (onFragmentFoodListener) context;
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
}
