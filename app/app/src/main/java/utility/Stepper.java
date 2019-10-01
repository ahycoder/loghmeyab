package utility;

import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import ir.yousefi.restaurant.R;

public class Stepper extends LinearLayout {


  public interface OnTouchStepperListener{
    void onTouch(int count);
  }
  private OnTouchStepperListener listener;

  public void setListener(OnTouchStepperListener listener) {
    this.listener = listener;
  }

  private ImageView imgMinesStepper,imgPlusStepper;
  private TextView txtCountStepper;
  public Stepper(Context context) {
    super(context);
    init(context);
  }

  public Stepper(Context context, @Nullable AttributeSet attrs) {
    super(context, attrs);
    init(context);
  }

  public Stepper(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
    init(context);
  }

  @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
  public Stepper(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
    super(context, attrs, defStyleAttr, defStyleRes);
    init(context);
  }

  private void init(final Context context){
    LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    View layout =inflater.inflate(R.layout.stepper,this);
    imgMinesStepper=layout.findViewById(R.id.imgMinesStepper);
    imgPlusStepper=layout.findViewById(R.id.imgPlusStepper);
    txtCountStepper=layout.findViewById(R.id.txtCountStepper);
    imgMinesStepper.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View v) {
        int count=Integer.parseInt(txtCountStepper.getText().toString());
        if (count!=0){
          count--;
          txtCountStepper.setText(count+"");
          if (listener != null){
            listener.onTouch(count);
          }
        }

      }
    });
    imgPlusStepper.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View v) {
        int count=Integer.parseInt(txtCountStepper.getText().toString());
        count++;
        txtCountStepper.setText(count+"");
        if (listener != null){
          listener.onTouch(count);
        }
      }
    });
  }
  public void setCount(int count) {
    txtCountStepper.setText(count+"");
  }
  public int getCount(){
    return Integer.parseInt(txtCountStepper.getText().toString());
  }
}
