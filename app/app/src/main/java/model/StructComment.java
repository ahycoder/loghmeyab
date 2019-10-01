package model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class StructComment implements Parcelable {
  @SerializedName("order_list")
  private String orderList;
  @SerializedName("order_date")
  private String orderDate;
  @SerializedName("comments_comment")
  private String comment;
  @SerializedName("user_name")
  private String userName;
  @SerializedName("comments_rate")
  private int rate;

  protected StructComment(Parcel in) {
    orderList = in.readString();
    orderDate = in.readString();
    comment = in.readString();
    userName = in.readString();
    rate = in.readInt();
  }

  public static final Creator<StructComment> CREATOR = new Creator<StructComment>() {
    @Override
    public StructComment createFromParcel(Parcel in) {
      return new StructComment(in);
    }

    @Override
    public StructComment[] newArray(int size) {
      return new StructComment[size];
    }
  };

  public String getOrderList() {
    return orderList;
  }

  public void setOrderList(String orderList) {
    this.orderList = orderList;
  }

  public String getOrderDate() {
    return orderDate;
  }

  public void setOrderDate(String orderDate) {
    this.orderDate = orderDate;
  }

  public String getComment() {
    return comment;
  }

  public void setComment(String comment) {
    this.comment = comment;
  }

  public String getUserName() {
    return userName;
  }

  public void setUserName(String userName) {
    this.userName = userName;
  }

  public int getRate() {
    return rate;
  }

  public void setRate(int rate) {
    this.rate = rate;
  }

  @Override
  public int describeContents() {
    return hashCode();
  }

  @Override
  public void writeToParcel(Parcel dest, int flags) {
    dest.writeString(orderList);
    dest.writeString(orderDate);
    dest.writeString(comment);
    dest.writeString(userName);
    dest.writeInt(rate);
  }
}
