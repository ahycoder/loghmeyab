package adapter;

import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import co.lujun.androidtagview.TagContainerLayout;
import ir.yousefi.restaurant.R;
import model.StructComment;
import utility.RatingBar;

public class AdapterComment extends RecyclerView.Adapter<AdapterComment.ViewHolder>  {
  private List<StructComment> list;

  public AdapterComment(List<StructComment> list) {
    this.list = list;

  }

  @NonNull
  @Override
  public AdapterComment.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_comment, parent, false);
    return new ViewHolder(itemView);
  }

  @Override
  public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
       final StructComment comment =list.get(position);
       holder.txtCommentUserName.setText(comment.getUserName());
       holder.txtCommentRate.setText(comment.getRate()+"");
       holder.txtCommentDate.setText(comment.getOrderDate());
       holder.txtMultiComment.setText(comment.getComment()+" ");
       holder.ratingBarComment.setRating(comment.getRate());

    try {
      JSONArray array = new JSONArray(comment.getOrderList());
      ArrayList<String> tags= new ArrayList<>();
     for (int i =0;i<array.length();i++){
       tags.add(array.get(i).toString());
     }
      holder.tagsView.setTags(tags);
      if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
        holder.tagsView.setTagTextDirection(View.TEXT_DIRECTION_RTL);
      }

    } catch (JSONException e) {
      e.printStackTrace();
    }


  }

  @Override
  public int getItemCount() {
    return list.size();
  }

  public class ViewHolder extends RecyclerView.ViewHolder {
    TextView txtCommentUserName;
    RatingBar ratingBarComment;
    TextView txtCommentRate;
    TextView txtCommentDate;
    TextView txtMultiComment;
    LinearLayout layCommentList;
    TagContainerLayout tagsView;


    public ViewHolder(@NonNull View itemView) {
      super(itemView);
      txtCommentUserName =itemView.findViewById(R.id.txtCommentUserName);
      ratingBarComment =itemView.findViewById(R.id.ratingBarComment);
      txtCommentRate =itemView.findViewById(R.id.txtCommentRate);
      txtCommentDate =itemView.findViewById(R.id.txtCommentDate);
      txtMultiComment =itemView.findViewById(R.id.txtMultiComment);
      layCommentList =itemView.findViewById(R.id.layCommentList);
      tagsView =itemView.findViewById(R.id.tagsView);
    }
  }
}
