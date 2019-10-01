package adapter;

import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import activity.PlaceActivity;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import ir.yousefi.restaurant.G;
import ir.yousefi.restaurant.R;
import model.StructPlace;
import utility.RatingBar;

public class AdapterPlaces extends RecyclerView.Adapter<AdapterPlaces.ViewHolder>  {
  private List<StructPlace> list;
  private int layoutItem;

  public AdapterPlaces(List<StructPlace> list,int layoutItem) {

    this.list = list;
    this.layoutItem = layoutItem;
  }

  @NonNull
  @Override
  public AdapterPlaces.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    View itemView = LayoutInflater.from(parent.getContext()).inflate(layoutItem, parent, false);

    ViewHolder viewHolder = new ViewHolder(itemView);
    return viewHolder;
  }

  @Override
  public void onBindViewHolder(@NonNull AdapterPlaces.ViewHolder holder, int position) {
       final StructPlace place =list.get(position);
       holder.itemPlaceTxtName.setText(place.getName());
       holder.itemPlaceTxtNumberOfComments.setText(place.getNumberOfComments()+"");
       holder.itemPlaceTxtRate.setText((place.getPoints()/(float)(place.getNumberOfComments()))+"");
       holder.itemPlaceTxtWorkTime.setText(place.getTimeWork());
       holder.itemPlaceTxtCourier.setText(place.getCourier());
       holder.itemPlaceTxtAddress.setText(place.getAddress());
       holder.itemPlaceRatingBar.setRating(place.getPoints()/place.getNumberOfComments());
       holder.itemPlaceRatingBar.setEnable(false);
       Log.i("TAG",place.getName()+" ->isActive:"+place.getActive());
       holder.cardViewItemPlace.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {
           Intent intent= new Intent(G.currentActivity, PlaceActivity.class);
           intent.putExtra("place",place);
           G.currentActivity.startActivity(intent);
         }
       });
  }

  @Override
  public int getItemCount() {
    return list.size();
  }

  public class ViewHolder extends RecyclerView.ViewHolder {
    ImageView itemPlaceImgLogo;
    TextView itemPlaceTxtName;
    TextView itemPlaceTxtNumberOfComments;
    RatingBar itemPlaceRatingBar;
    TextView itemPlaceTxtRate;
    TextView itemPlaceTxtWorkTime;
    TextView itemPlaceTxtCourier;
    TextView itemPlaceTxtAddress;
    CardView cardViewItemPlace;

    public ViewHolder(@NonNull View itemView) {
      super(itemView);
      itemPlaceImgLogo =itemView.findViewById(R.id.itemPlaceImgLogo);
      itemPlaceTxtName =itemView.findViewById(R.id.itemPlaceTxtName);
      itemPlaceTxtNumberOfComments =itemView.findViewById(R.id.itemPlaceTxtNumberOfComments);
      itemPlaceRatingBar =itemView.findViewById(R.id.itemPlaceRatingBar);
      itemPlaceTxtRate =itemView.findViewById(R.id.itemPlaceTxtRate);
      itemPlaceTxtWorkTime =itemView.findViewById(R.id.itemPlaceTxtWorkTime);
      itemPlaceTxtCourier =itemView.findViewById(R.id.itemPlaceTxtCourier);
      itemPlaceTxtAddress =itemView.findViewById(R.id.itemPlaceTxtAddress);
      cardViewItemPlace =itemView.findViewById(R.id.cardViewItemPlace);
    }
  }
}
