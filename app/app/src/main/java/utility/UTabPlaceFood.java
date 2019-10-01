package utility;

import android.view.View;

import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

import activity.PlacefoodFragment;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;
import model.StructFood;

  public class UTabPlaceFood {

    private ViewPager viewPager;
    private TabLayout tabLayout;
    private Adapter adapter;

    public UTabPlaceFood(AppCompatActivity activity, int viewPagerId, int tabLayoutId) {
      View view = activity.getWindow().getDecorView();
      viewPager = (ViewPager) view.findViewById(viewPagerId);
      tabLayout = (TabLayout) view.findViewById(tabLayoutId);
      adapter = new Adapter(activity.getSupportFragmentManager());
      viewPager.setAdapter(adapter);
      tabLayout.setupWithViewPager(viewPager);
    }
    public void add(String title,ArrayList<StructFood> list,boolean isActive,ArrayList<StructFood> purchaseFoods) {
      Item item = new Item(list, title,isActive,purchaseFoods);
      adapter.add(item);
      adapter.notifyDataSetChanged();

    }

    public void setTitle(int index, String title) {
      adapter.getRawItem(index).setTitle(title);
      adapter.notifyDataSetChanged();
    }




    private class Item {
    private ArrayList<StructFood> list;
    private ArrayList<StructFood> purchaseFoods;
    private String title;
    private int icon;
    private boolean isActive;

    public Item(ArrayList<StructFood> list,String title,boolean isActive,ArrayList<StructFood> purchaseFoods) {
        this.title = title;
        this.isActive = isActive;
        this.list = list;
        this.purchaseFoods = purchaseFoods;
    }

      public boolean isActive() { return isActive; }

      public void setActive(boolean active) { isActive = active; }

      public ArrayList<StructFood> getList() {
      return list;
    }

    public void setList(ArrayList<StructFood> list) {
      this.list = list;
    }

    public String getTitle() {
      return title;
    }

    public void setTitle(String title) {
      this.title = title;
    }
  }






  private class Adapter extends FragmentPagerAdapter {
    private List<Item> items = new ArrayList<>();

    public Adapter(FragmentManager fragmentManager) {
      super(fragmentManager);
    }

    @Override
    public Fragment getItem(int position) {

      return PlacefoodFragment.newInstance(items.get(position).list,items.get(position).title,items.get(position).isActive,items.get(position).purchaseFoods);
    }

    public Item getRawItem(int position) {
      return items.get(position);
    }

    @Override
    public int getCount() {
      return items.size();
    }

    public void add(Item item) {
      items.add(item);
    }

    @Override
    public CharSequence getPageTitle(int position) {
      return items.get(position).getTitle();
    }
  }


}
