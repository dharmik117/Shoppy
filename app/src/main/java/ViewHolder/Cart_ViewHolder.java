package ViewHolder;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shoppy.R;

import Interfaces.ItemClickListner;

public class Cart_ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
{
    public TextView txtproductname,txtproductprice,txtproductque;
    private ItemClickListner itemClickListner;

    public Cart_ViewHolder(@NonNull View itemView)
    {
        super(itemView);

        txtproductname = itemView.findViewById(R.id.clproductname);
        txtproductprice = itemView.findViewById(R.id.clproductprice);
        txtproductque = itemView.findViewById(R.id.clproductque);
    }

    @Override
    public void onClick(View v)
    {
        itemClickListner.onClick(v,getAdapterPosition(),false);
    }

    public void setItemClickListner(ItemClickListner itemClickListner) {
        this.itemClickListner = itemClickListner;
    }
}
