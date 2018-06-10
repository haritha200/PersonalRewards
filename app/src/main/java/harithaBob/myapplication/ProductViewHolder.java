package harithaBob.myapplication;

import android.content.Context;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import static harithaBob.myapplication.MainActivity.mTotalPoints;

public class ProductViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    public TextView productPoint;
    public ImageView productImage;
    public TextView productName;
    public TextView productAccu;
    public ProductObject mProduct;
    public Context mContext;

    public void bindProductToHolder(ProductObject productObject, int imageRes, Context context){
        mProduct = productObject;
        productImage.setImageResource(imageRes);
        productName.setText(productObject.getName());
        productAccu.setText(Integer.toString(productObject.getPointSum()));
        productPoint.setText(Integer.toString(productObject.getPoint()));
        mContext = context;
    }

    public ProductViewHolder(View itemView) {
        super(itemView);
        productImage = (ImageView)itemView.findViewById(R.id.product_image);
        productName = (TextView)itemView.findViewById(R.id.product_name);
        productPoint = (TextView)itemView.findViewById(R.id.product_point);
        productAccu = (TextView)itemView.findViewById(R.id.product_accumulate);

        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        ProductLab plab = ProductLab.get(mContext);
        plab.addToUndoTable(mProduct);

        mProduct.setPointSum(mProduct.getPoint()+ mProduct.getPointSum());
        plab.updateProduct(mProduct);
        productAccu.setText(Integer.toString(mProduct.getPointSum()));

        ArrayList<ProductObject> products = plab.getProducts();
        int tot = 0;
        for (int i=0; i<products.size(); i++){
            tot+=products.get(i).getPointSum();
        }
        mTotalPoints.setText(tot+"\nPOINTS");
        Snackbar.make(view, "Woohoo! Didn't eat "+ mProduct.getName()  + "Earned " + mProduct.getPoint()+"points", Snackbar.LENGTH_SHORT)
                .setAction("Action", null).show();
    }
}