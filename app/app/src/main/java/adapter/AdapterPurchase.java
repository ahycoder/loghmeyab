package adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import ir.yousefi.restaurant.R;
import model.StructFood;
import utility.Stepper;

public class AdapterPurchase extends RecyclerView.Adapter<AdapterPurchase.ViewHolder>  {
  private ArrayList<StructFood> list;

  public interface OnSelectedPurchaseListener{
    void onSelectedPurchase(StructFood food, int number,int position);
  }
  private OnSelectedPurchaseListener listener;
  public AdapterPurchase(ArrayList<StructFood> list) {
    this.list = list;
  }

  public void setListener(OnSelectedPurchaseListener listener) {
    this.listener = listener;
  }

  @NonNull
  @Override
  public AdapterPurchase.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_purchase, parent, false);
    return new ViewHolder(itemView);
  }

  @Override
  public void onBindViewHolder(@NonNull AdapterPurchase.ViewHolder holder,final int position) {
       final StructFood food =list.get(position);
       holder.txtItemFoodName.setText(food.getName());
       holder.txtItemFoodPrice.setText(food.getPrice()+" تومان ");
       holder.stepperItemFood.setCount(food.getCountBuyed());
       holder.stepperItemFood.setListener(new Stepper.OnTouchStepperListener() {
          @Override
          public void onTouch(int count) {
            if (listener != null){
              listener.onSelectedPurchase(food,count,position);
            }
          }

        });

  }

  @Override
  public int getItemCount() {
    return list.size();
  }

  public class ViewHolder extends RecyclerView.ViewHolder {
    ImageView imgItemFood;
    TextView txtItemFoodName;
    TextView txtItemFoodPrice;
    Stepper stepperItemFood;

    public ViewHolder(@NonNull View itemView) {
      super(itemView);
      imgItemFood =itemView.findViewById(R.id.imgItemFood);
      txtItemFoodName =itemView.findViewById(R.id.txtItemFoodName);
      txtItemFoodPrice =itemView.findViewById(R.id.txtItemFoodPrice);
      stepperItemFood =itemView.findViewById(R.id.stepperItemFood);
    }
  }
}
