package model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class StructPlace implements Parcelable {
  @SerializedName("place_id")
  private int id;

  @SerializedName("kind_id")
  private  int kindId;

  @SerializedName("place_name")
  private String name;

  @SerializedName("kind_name")
  private String kindName;

  @SerializedName("place_logo_url")
  private String logoUrl;

  @SerializedName("place_courier")
  private String courier;

  @SerializedName("place_time_work")
  private String timeWork;

  @SerializedName("place_address")
  private String address;

  @SerializedName("place_geo")
  private String geo;

  @SerializedName("place_is_active")
  private int isActive;

  @SerializedName("place_number_of_comments")
  private int numberOfComments;

  @SerializedName("place_points")
  private int points;

  @SerializedName("place_min_order")
  private int minOrder;

  @SerializedName("place_time_ready")
  private String timeReady;

  @SerializedName("place_date")
  private String date;


  protected StructPlace(Parcel in) {
    id = in.readInt();
    kindId = in.readInt();
    name = in.readString();
    kindName = in.readString();
    logoUrl = in.readString();
    courier = in.readString();
    timeWork = in.readString();
    address = in.readString();
    geo = in.readString();
    isActive = in.readInt();
    numberOfComments = in.readInt();
    points = in.readInt();
    minOrder = in.readInt();
    timeReady = in.readString();
    date = in.readString();
  }

  @Override
  public void writeToParcel(Parcel dest, int flags) {
    dest.writeInt(id);
    dest.writeInt(kindId);
    dest.writeString(name);
    dest.writeString(kindName);
    dest.writeString(logoUrl);
    dest.writeString(courier);
    dest.writeString(timeWork);
    dest.writeString(address);
    dest.writeString(geo);
    dest.writeInt(isActive);
    dest.writeInt(numberOfComments);
    dest.writeInt(points);
    dest.writeInt(minOrder);
    dest.writeString(timeReady);
    dest.writeString(date);
  }

  @Override
  public int describeContents() {
    return hashCode();
  }

  public static final Creator<StructPlace> CREATOR = new Creator<StructPlace>() {
    @Override
    public StructPlace createFromParcel(Parcel in) {
      return new StructPlace(in);
    }

    @Override
    public StructPlace[] newArray(int size) {
      return new StructPlace[size];
    }
  };

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public int getKindId() {
    return kindId;
  }

  public void setKindId(int kindId) {
    this.kindId = kindId;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getKindName() {
    return kindName;
  }

  public void setKindName(String kindName) {
    this.kindName = kindName;
  }

  public String getLogoUrl() {
    return logoUrl;
  }

  public void setLogoUrl(String logoUrl) {
    this.logoUrl = logoUrl;
  }

  public String getCourier() {
    return courier;
  }

  public void setCourier(String courier) {
    this.courier = courier;
  }

  public String getTimeWork() {
    return timeWork;
  }

  public void setTimeWork(String timeWork) {
    this.timeWork = timeWork;
  }

  public String getAddress() {
    return address;
  }

  public void setAddress(String address) {
    this.address = address;
  }

  public String getGeo() {
    return geo;
  }

  public void setGeo(String geo) {
    this.geo = geo;
  }

  public int getActive() {
    return isActive;
  }

  public void setActive(int active) {
    isActive = active;
  }

  public int getNumberOfComments() {
    return numberOfComments;
  }

  public void setNumberOfComments(int numberOfComments) {
    this.numberOfComments = numberOfComments;
  }

  public int getPoints() {
    return points;
  }

  public void setPoints(int points) {
    this.points = points;
  }

  public int getMinOrder() {
    return minOrder;
  }

  public void setMinOrder(int minOrder) {
    this.minOrder = minOrder;
  }

  public String getTimeReady() {
    return timeReady;
  }

  public void setTimeReady(String timeReady) {
    this.timeReady = timeReady;
  }

  public String getDate() {
    return date;
  }

  public void setDate(String date) {
    this.date = date;
  }
}
