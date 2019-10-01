package model;

import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import androidx.annotation.RequiresApi;

public class StructFood implements Parcelable {
  @SerializedName("food_id")
  private int id;
  @SerializedName("food_name")
  private String name;
  @SerializedName("food_desc")
  private String desc;
  @SerializedName("food_price")
  private int price;
  @SerializedName("food_off")
  private int off;
  @SerializedName("food_stock")
  private int stock;
  @SerializedName("menu_name")
  private String menuName;
  @SerializedName("countBuyed")
  private int countBuyed;


  protected StructFood(Parcel in) {
    id = in.readInt();
    name = in.readString();
    desc = in.readString();
    price = in.readInt();
    off = in.readInt();
    stock = in.readInt();
    menuName = in.readString();
    countBuyed = in.readInt();
  }

  @Override
  public void writeToParcel(Parcel dest, int flags) {
    dest.writeInt(id);
    dest.writeString(name);
    dest.writeString(desc);
    dest.writeInt(price);
    dest.writeInt(off);
    dest.writeInt(stock);
    dest.writeString(menuName);
    dest.writeInt(countBuyed);
  }

  @RequiresApi(api = Build.VERSION_CODES.KITKAT)
  @Override
  public int describeContents() {
    return hashCode();
  }

  public static final Creator<StructFood> CREATOR = new Creator<StructFood>() {
    @Override
    public StructFood createFromParcel(Parcel in) {
      return new StructFood(in);
    }

    @Override
    public StructFood[] newArray(int size) {
      return new StructFood[size];
    }
  };

  public int getCountBuyed() { return countBuyed; }

  public void setCountBuyed(int countBuyed) { this.countBuyed = countBuyed; }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getDesc() {
    return desc;
  }

  public void setDesc(String desc) {
    this.desc = desc;
  }

  public int getPrice() {
    return price;
  }

  public void setPrice(int price) {
    this.price = price;
  }

  public int getOff() {
    return off;
  }

  public void setOff(int off) {
    this.off = off;
  }

  public int getStock() {
    return stock;
  }

  public void setStock(int stock) {
    this.stock = stock;
  }

  public String getMenuName() {
    return menuName;
  }

  public void setMenuName(String menuName) {
    this.menuName = menuName;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof StructFood)) return false;
    StructFood that = (StructFood) o;
    return getId() == that.getId();
  }

}
