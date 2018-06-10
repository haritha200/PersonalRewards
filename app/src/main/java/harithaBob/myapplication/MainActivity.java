package harithaBob.myapplication;

import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends AppCompatActivity
    implements AppBarLayout.OnOffsetChangedListener {

    private static final float PERCENTAGE_TO_SHOW_TITLE_AT_TOOLBAR  = 0.9f;
    private static final float PERCENTAGE_TO_HIDE_TITLE_DETAILS     = 0.3f;
    private static final int ALPHA_ANIMATIONS_DURATION              = 200;
    private boolean mIsTheTitleVisible          = false;
    private boolean mIsTheTitleContainerVisible = true;
    private LinearLayout mTitleContainer;
    public static TextView mTotalPoints;
    private TextView mTitle;
    private AppBarLayout mAppBarLayout;
    private Toolbar mToolbar;
    private RecyclerView mRecyclerView;
    private GridLayoutManager mGrid;
    private ProductAdapter mAdapter;
    private CircleImageView mCircleImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bindActivity();
        mAppBarLayout.addOnOffsetChangedListener(this);
        mToolbar.inflateMenu(R.menu.menu_main);
        mToolbar.setNavigationIcon(R.drawable.ic_redeem);
        startAlphaAnimation(mTitle, 0, View.INVISIBLE);
        mRecyclerView.setLayoutManager(mGrid);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setNestedScrollingEnabled(false);
        mRecyclerView.setAdapter(mAdapter);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ProductLab plab = ProductLab.get(MainActivity.this);
                ArrayList<ProductObject> products = plab.getProducts();
                for (int i =0; i< products.size(); i++){
                    ProductObject p = products.get(i);
                    p.setPointSum(0);
                    plab.updateProduct(p);
                }
                mRecyclerView.setAdapter(new ProductAdapter(MainActivity.this, products));
                mRecyclerView.getAdapter().notifyDataSetChanged();
                mTotalPoints.setText("0\nPOINTS");

                Snackbar.make(view, "Redeemed all points. ", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        updateMainUI();

    }

    private void bindActivity() {
        mTotalPoints = (TextView) findViewById(R.id.total_points);
        mToolbar        = (Toolbar) findViewById(R.id.main_toolbar);
        mTitle          = (TextView) findViewById(R.id.main_textview_title);
        mTitleContainer = (LinearLayout) findViewById(R.id.main_linearlayout_title);
        mAppBarLayout   = (AppBarLayout) findViewById(R.id.main_appbar);
        mRecyclerView = (RecyclerView)findViewById(R.id.product_list);
        mCircleImageView = (CircleImageView)findViewById(R.id.redeem);
        mGrid = new GridLayoutManager(this, 3);
        mAdapter = new ProductAdapter(MainActivity.this, getProductTestData());
    }

    private List<ProductObject> getProductTestData() {
        ProductLab productlab = ProductLab.get(this);
        if(productlab.getProducts().size()<1){
            productlab.addProduct(new ProductObject("Sugar drink | ", "sugar_drink", 20, 0));
            productlab.addProduct(new ProductObject("Ice cream | ", "icecream", 20, 0));
            productlab.addProduct(new ProductObject("Pizza | ", "pizza", 30,0));
            productlab.addProduct(new ProductObject("Fried | ", "fries", 20, 0));
        }
        return productlab.getProducts();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public void onOffsetChanged(AppBarLayout appBarLayout, int offset) {
        int maxScroll = appBarLayout.getTotalScrollRange();
        float percentage = (float) Math.abs(offset) / (float) maxScroll;

        handleAlphaOnTitle(percentage);
        handleToolbarTitleVisibility(percentage);
    }

    private void handleToolbarTitleVisibility(float percentage) {
            if (percentage >= PERCENTAGE_TO_SHOW_TITLE_AT_TOOLBAR) {

                if(!mIsTheTitleVisible) {
                    startAlphaAnimation(mTitle, ALPHA_ANIMATIONS_DURATION, View.VISIBLE);
                    mIsTheTitleVisible = true;
                }

            } else {

                if (mIsTheTitleVisible) {
                    startAlphaAnimation(mTitle, ALPHA_ANIMATIONS_DURATION, View.INVISIBLE);
                    mIsTheTitleVisible = false;
                }
            }
    }

    private void handleAlphaOnTitle(float percentage) {
        if (percentage >= PERCENTAGE_TO_HIDE_TITLE_DETAILS) {
            if(mIsTheTitleContainerVisible) {
                startAlphaAnimation(mTitleContainer, ALPHA_ANIMATIONS_DURATION, View.INVISIBLE);
                mIsTheTitleContainerVisible = false;
            }

        } else {

            if (!mIsTheTitleContainerVisible) {
                startAlphaAnimation(mTitleContainer, ALPHA_ANIMATIONS_DURATION, View.VISIBLE);
                mIsTheTitleContainerVisible = true;
                Log.i("TITLEVIS", "VISIBLE??");
            }
        }
    }

    private void updateMainUI(){
        int tot = 0;
        for(int i =0; i <mAdapter.productList.size(); i++){
            tot+=mAdapter.productList.get(i).getPointSum();
        }
        mTotalPoints.setText(Integer.toString(tot) + "\n POINTS");
    }

    public static void startAlphaAnimation (View v, long duration, int visibility) {
        AlphaAnimation alphaAnimation = (visibility == View.VISIBLE)
            ? new AlphaAnimation(0f, 1f)
            : new AlphaAnimation(1f, 0f);

        alphaAnimation.setDuration(duration);
        alphaAnimation.setFillAfter(true);
        v.startAnimation(alphaAnimation);
    }
}
