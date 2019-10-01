package model;

import com.google.gson.annotations.SerializedName;

public class StructTypeFood {
  @SerializedName("type_id")
  private int id;
  @SerializedName("type_name")
  private String name;
  @SerializedName("type_image_url")
  private String ImageUrl;

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

  public String getImageUrl() {
    return ImageUrl;
  }

  public void setImageUrl(String imageUrl) {
    ImageUrl = imageUrl;
  }
}
