package adapter;

import android.util.Log;
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

public class AdapterFood extends RecyclerView.Adapter<AdapterFood.ViewHolder>  {
  private ArrayList<StructFood> list;
  private ArrayList<StructFood> purchaseFoods;
  private boolean isActivePlace;

  public interface OnSelectedFoodListener{
    void onSelectedFood(StructFood food,int number);
  }
  private OnSelectedFoodListener listener;
  public AdapterFood(ArrayList<StructFood> list,boolean isActivePlace,OnSelectedFoodListener listener,ArrayList<StructFood> purchaseFoods) {
    this.list = list;
    this.purchaseFoods = purchaseFoods;
    this.isActivePlace = isActivePlace;
    this.listener = listener;
  }

  @NonNull
  @Override
  public AdapterFood.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_food, parent, false);
    return new ViewHolder(itemView);
  }

  @Override
  public void onBindViewHolder(@NonNull AdapterFood.ViewHolder holder, int position) {
       final StructFood food =list.get(position);
       holder.txtItemFoodName.setText(food.getName());
       holder.txtItemFoodDesc.setText(food.getDesc());
       holder.txtItemFoodPrice.setText(food.getPrice()+" تومان ");

       if (isActivePlace){
       holder.txtItemFoodClosed.setVisibility(View.GONE);
       holder.stepperItemFood.setVisibility(View.VISIBLE);
       }else{
         holder.txtItemFoodClosed.setVisibility(View.VISIBLE);
         holder.stepperItemFood.setVisibility(View.GONE);
       }

       if (purchaseFoods != null){
         for (int i=0;i<purchaseFoods.size();i++){
           if (purchaseFoods.get(i).getId()==food.getId()){
           Log.i("TAG","purchaseFoods id:"+purchaseFoods.get(i).getId()+"      food.getId:"+food.getId()+"      count:"+purchaseFoods.get(i).getCountBuyed());
             holder.stepperItemFood.setCount(purchaseFoods.get(i).getCountBuyed());
           }
         }
       }
        holder.stepperItemFood.setListener(new Stepper.OnTouchStepperListener() {
          @Override
          public void onTouch(int count) {
            if (listener != null){
              listener.onSelectedFood(food,count);
            }
            if (count>0){

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
    TextView txtItemFoodDesc;
    TextView txtItemFoodPrice;
    TextView txtItemFoodClosed;
    Stepper stepperItemFood;

    public ViewHolder(@NonNull View itemView) {
      super(itemView);
      imgItemFood =itemView.findViewById(R.id.imgItemFood);
      txtItemFoodName =itemView.findViewById(R.id.txtItemFoodName);
      txtItemFoodDesc =itemView.findViewById(R.id.txtItemFoodDesc);
      txtItemFoodPrice =itemView.findViewById(R.id.txtItemFoodPrice);
      txtItemFoodClosed =itemView.findViewById(R.id.txtItemFoodClosed);
      stepperItemFood =itemView.findViewById(R.id.stepperItemFood);
    }
  }
}
