package adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import ir.yousefi.restaurant.R;
import model.StructTypeFood;

public class AdapterTypeFoods extends RecyclerView.Adapter<AdapterTypeFoods.ViewHolder> {
  private List<StructTypeFood>list;

  public AdapterTypeFoods(List<StructTypeFood> list) {
    this.list = list;
  }

  @NonNull
  @Override
  public AdapterTypeFoods.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_type_food, parent, false);
    AdapterTypeFoods.ViewHolder viewHolder = new AdapterTypeFoods.ViewHolder(itemView);
    return viewHolder;
  }

  @Override
  public void onBindViewHolder(@NonNull AdapterTypeFoods.ViewHolder holder, int position) {
     StructTypeFood typeFood = list.get(position);
     Picasso.get().load(typeFood.getImageUrl()).into(holder.imgItemTypeFood);
  }

  @Override
  public int getItemCount() {
    return list.size();
  }

  public class ViewHolder extends RecyclerView.ViewHolder {
    ImageView imgItemTypeFood;
    public ViewHolder(@NonNull View itemView) {
      super(itemView);
      imgItemTypeFood =itemView.findViewById(R.id.imgItemTypeFood);
    }
  }
}
