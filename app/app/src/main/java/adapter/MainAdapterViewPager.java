package adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import androidx.viewpager.widget.PagerAdapter;
import ir.yousefi.restaurant.R;

public class MainAdapterViewPager extends PagerAdapter {
  private Context context;
  private ArrayList<String> listUrlImage;

  public MainAdapterViewPager(Context context, ArrayList<String> listUrlImage) {
    this.context = context;
    this.listUrlImage = listUrlImage;
  }

    Bitmap bitmap ;

  @Override
  public Object instantiateItem(ViewGroup collection, final int position) {
    LayoutInflater inflater = LayoutInflater.from(context);
    ViewGroup layout = (ViewGroup) inflater.inflate(R.layout.item_main_view_pager, collection, false);
    ImageView imageView=layout.findViewById(R.id.imgMainViewPager);
    String url =listUrlImage.get(position);
    Picasso.get().load(listUrlImage.get(position)).into(imageView);
    collection.addView(layout);
    return layout;
  }

  @Override
  public void destroyItem(ViewGroup collection, int position, Object view) {
    collection.removeView((View) view);
  }

  @Override
  public int getCount() {
    return listUrlImage.size();
  }

  @Override
  public boolean isViewFromObject(View view, Object object) {
    return view == object;
  }

}
