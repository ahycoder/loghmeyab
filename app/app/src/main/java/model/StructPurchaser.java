package model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.android.gms.maps.model.LatLng;
import com.google.gson.annotations.SerializedName;

public class StructPurchaser implements Parcelable {
  @SerializedName("subject")
  private String subject;
  @SerializedName("Address")
  private String Address;
  @SerializedName("point")
  private LatLng point;
  @SerializedName("name")
  private String name;
  @SerializedName("phoneNumber")
  private String phoneNumber;
  @SerializedName("description")
  private String description;

  public StructPurchaser(String subject, String address, LatLng point) {
    this.subject = subject;
    Address = address;
    this.point = point;
  }

  protected StructPurchaser(Parcel in) {
    subject = in.readString();
    Address = in.readString();
    point = in.readParcelable(LatLng.class.getClassLoader());
    name = in.readString();
    phoneNumber = in.readString();
    description = in.readString();
  }

  public static final Creator<StructPurchaser> CREATOR = new Creator<StructPurchaser>() {
    @Override
    public StructPurchaser createFromParcel(Parcel in) {
      return new StructPurchaser(in);
    }

    @Override
    public StructPurchaser[] newArray(int size) {
      return new StructPurchaser[size];
    }
  };

  public String getSubject() {
    return subject;
  }

  public void setSubject(String subject) {
    this.subject = subject;
  }

  public String getAddress() {
    return Address;
  }

  public void setAddress(String address) {
    Address = address;
  }

  public LatLng getPoint() {
    return point;
  }

  public void setPoint(LatLng point) {
    this.point = point;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getPhoneNumber() {
    return phoneNumber;
  }

  public void setPhoneNumber(String phoneNumber) {
    this.phoneNumber = phoneNumber;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  @Override
  public int describeContents() {
    return hashCode();
  }

  @Override
  public void writeToParcel(Parcel dest, int flags) {
    dest.writeString(subject);
    dest.writeString(Address);
    dest.writeParcelable(point, flags);
    dest.writeString(name);
    dest.writeString(phoneNumber);
    dest.writeString(description);
  }
}
