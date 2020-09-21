package ViewHolder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shoppy.R;

import Interfaces.ItemClickListner;

public class OnClick_ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
{
    public TextView txtproductname,txtproductdes,txtproductprice;
    public ImageView pdimageview;
    public ItemClickListner listner;

    public OnClick_ViewHolder(@NonNull View itemView)
    {
        super(itemView);

        pdimageview = (ImageView) itemView.findViewById(R.id.ocproductimage);
        txtproductname = (TextView) itemView.findViewById(R.id.ocproductname);
        txtproductdes = (TextView) itemView.findViewById(R.id.ocproductdes);
        txtproductprice = (TextView) itemView.findViewById(R.id.ocprice);
    }

    public void setitemclicklistner (ItemClickListner listner)
    {
        this.listner = listner;
    }

    @Override
    public void onClick(View v)
    {
        listner.onClick(v,getAdapterPosition(),false);
    }
}
