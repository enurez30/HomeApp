package sera.com.plasticmobilehomeapp;

import android.animation.ObjectAnimator;
import android.app.FragmentManager;
import android.content.ClipData;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.design.widget.BottomSheetBehavior;
import android.support.v7.app.AppCompatActivity;
import android.view.DragEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements View.OnDragListener, View.OnLongClickListener {
    private BottomSheetBehavior bottomSheetBehavior;
    private boolean showBehavior;
    private FragmentManager mFragmentManager;
    @BindView(R.id.layout3)
    LinearLayout layout3;
    @BindView(R.id.layout1)
    LinearLayout layout1;
    @BindView(R.id.rectangle)
    FrameLayout rectangle;
    @BindView(R.id.bottom_sheet)
    LinearLayout llBottomSheet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.main);
        ButterKnife.bind(this);
        mFragmentManager = getFragmentManager();
        replaceFragment();


        bottomSheetBehavior = BottomSheetBehavior.from(llBottomSheet);
        bottomSheetBehavior.setHideable(false);

        rectangle.setTag("DRAGGABLE");
        rectangle.setOnLongClickListener(this);
        layout3.setOnDragListener(this);
        layout1.setOnDragListener(this);
        execute(rectangle);
    }

    private void execute(View view) {
        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(view, View.ROTATION, 0.0f, 360.0f);
        objectAnimator.setDuration(1000);
        objectAnimator.setRepeatCount(Animation.INFINITE);
        objectAnimator.setInterpolator(new LinearInterpolator());
        objectAnimator.setAutoCancel(false);
        objectAnimator.start();
    }

    private void replaceFragment() {
        mFragmentManager.beginTransaction().replace(R.id.rectangle, new RectangleFragment()).commit();
    }

    @Override
    public boolean onLongClick(View v) {

        ClipData data = ClipData.newPlainText("simple_text", "text");
        View.DragShadowBuilder sb = new View.DragShadowBuilder(v);
        v.startDrag(data, sb, v, 0);
        return true;
    }

    @Override
    public boolean onDrag(View v, DragEvent event) {

        int action = event.getAction();
        switch (action) {
            case DragEvent.ACTION_DRAG_STARTED:
                bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                return true;//event.getClipDescription().hasMimeType(ClipDescription.MIMETYPE_TEXT_PLAIN);

            case DragEvent.ACTION_DRAG_ENTERED:
                v.getBackground().setColorFilter(Color.GRAY, PorterDuff.Mode.SRC_IN);
                v.invalidate();
                return true;

            case DragEvent.ACTION_DRAG_LOCATION:

                return true;

            case DragEvent.ACTION_DRAG_EXITED:
                v.getBackground().clearColorFilter();
                v.invalidate();
                return true;

            case DragEvent.ACTION_DROP:
                v.getBackground().clearColorFilter();
                v.invalidate();

                View vw = (View) event.getLocalState();
                ViewGroup owner = (ViewGroup) vw.getParent();
                owner.removeView(vw);
                LinearLayout container = (LinearLayout) v;
                container.addView(vw);
                vw.setVisibility(View.VISIBLE);
                showBehavior = v.getId() == R.id.layout3;
                return true;

            case DragEvent.ACTION_DRAG_ENDED:
                v.getBackground().clearColorFilter();
                v.invalidate();

                if (showBehavior) {
                    bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                } else {
                    bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                }

                return true;

        }
        return false;
    }

}

