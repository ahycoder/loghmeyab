package utility;

import android.os.Parcelable;
import android.util.Log;

import java.util.ArrayList;

import model.StructFood;

public class utilityPurchase {
  public static int getFactorPrice(ArrayList<StructFood> list){
    int factorPrice=0;
    for (StructFood food : list) {
      int price = 0;
      if (food.getOff() > 0) {
        price = food.getCountBuyed() * food.getOff();
      } else {
        price = food.getCountBuyed() * food.getPrice();
      }
      factorPrice += price;
    }
    return factorPrice;
  }
  public static int getWholePrice(ArrayList<StructFood> list){
    int wholePrice=0;
    for (StructFood food : list) {
      wholePrice += food.getCountBuyed() * food.getPrice();
    }
    return wholePrice;
  }
  public static int getOffPrice(ArrayList<StructFood> list){
    int offPrice=0;
    for (StructFood food : list) {
      offPrice += food.getCountBuyed() * (food.getPrice()-food.getOff());
    }
    return offPrice;
  }
  public static int getWohleCount(ArrayList<StructFood> list){
    int wholeCount=0;
    for (StructFood food : list) {
      wholeCount += food.getCountBuyed();
    }
    return wholeCount;
  }
  public static Parcelable[] getPurchases(ArrayList<StructFood> list){
    Parcelable[] parcelables= new Parcelable[list.size()];
    for (int i=0;i<list.size();i++){
      parcelables[i]=list.get(i);
    }
    return parcelables;
  }

  public static ArrayList<StructFood> updateListPurchase(ArrayList<StructFood> list, StructFood food, int count) {
    for (int i=0;i<list.size();i++){
      Log.i("TAG","before:"+list.get(i).getName()+"=>"+list.get(i).getCountBuyed());
    }
    if (list.contains(food)){
      for (int i=list.size()-1;i>=0;i--) {
        if (list.get(i).equals(food)) {
          if (count == 0) {
            list.remove(i);
          }else {
            list.get(i).setCountBuyed(count);
          }
        }
      }
    }else {
      food.setCountBuyed(count);
      list.add(food);
    }
    for (int i=0;i<list.size();i++){
      Log.i("TAG","after:"+list.get(i).getName()+"=>"+list.get(i).getCountBuyed());
    }
    return list;
  }
}
