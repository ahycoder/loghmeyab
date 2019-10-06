package webservice;


import com.google.gson.JsonObject;

import org.json.JSONArray;

import java.util.List;

import model.StructComment;
import model.StructFood;
import model.StructPlace;
import model.StructTypeFood;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface Api {



  @POST("first")
  @FormUrlEncoded
  Call<List<StructFood>> getPlace(@Field("Key")String key, @Field("place_id")String id);

  @POST("first")
  @FormUrlEncoded
  Call<List<StructPlace>> getPlaces(@Field("Key")String key);

  @POST("first")
  @FormUrlEncoded
  Call<List<StructPlace>> getPlaces(@Field("Key")String key,@Field("Kind")String kind);

  @POST("first")
  @FormUrlEncoded
  Call<List<StructTypeFood>> getTypesFood(@Field("Key")String key);

  @POST("first")
  @FormUrlEncoded
  Call<List<StructComment>> getCommentsOfPlace(@Field("Key")String key, @Field("place_id")String id);

  @POST("first")
  @FormUrlEncoded
  Call<JsonObject> goToPay(@Field("Key")String key, @Field("place_id")int placeId, @Field("json_purchase") JSONArray jsonArray);


}
