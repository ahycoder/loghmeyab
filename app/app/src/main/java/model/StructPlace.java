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

  @SerializedName("place_courier_km")
  private int courierKm;

  @SerializedName("place_courier_price")
  private int courierPrice;

  @SerializedName("place_time_work")
  private String timeWork;

  @SerializedName("place_address")
  private String address;

  @SerializedName("place_location_lat")
  private double lat;

  @SerializedName("place_location_lng")
  private double lng;

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
    courierKm = in.readInt();
    courierPrice = in.readInt();
    timeWork = in.readString();
    address = in.readString();
    lat = in.readDouble();
    lng = in.readDouble();
    isActive = in.readInt();
    numberOfComments = in.readInt();
    points = in.readInt();
    minOrder = in.readInt();
    timeReady = in.readString();
    date = in.readString();
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

  public int getCourierKm() { return courierKm; }

  public void setCourierKm(int courierKm) { this.courierKm = courierKm; }

  public int getCourierPrice() { return courierPrice; }

  public void setCourierPrice(int courierPrice) { this.courierPrice = courierPrice; }

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

  public double getLat() {
    return lat;
  }

  public void setLat(double lat) {
    this.lat = lat;
  }

  public double getLng() {
    return lng;
  }

  public void setLng(double lng) {
    this.lng = lng;
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

  public void setNumberOfComments(int numberOfComments) { this.numberOfComments = numberOfComments; }

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

  @Override
  public int describeContents() {
    return hashCode();
  }

  @Override
  public void writeToParcel(Parcel dest, int flags) {
    dest.writeInt(id);
    dest.writeInt(kindId);
    dest.writeString(name);
    dest.writeString(kindName);
    dest.writeString(logoUrl);
    dest.writeInt(courierKm);
    dest.writeInt(courierPrice);
    dest.writeString(timeWork);
    dest.writeString(address);
    dest.writeDouble(lat);
    dest.writeDouble(lng);
    dest.writeInt(isActive);
    dest.writeInt(numberOfComments);
    dest.writeInt(points);
    dest.writeInt(minOrder);
    dest.writeString(timeReady);
    dest.writeString(date);
  }
}
