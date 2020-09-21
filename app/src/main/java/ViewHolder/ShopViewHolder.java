package ViewHolder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shoppy.R;

import Interfaces.ItemClickListner;

public class ShopViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
{
    public TextView txtshopname,txtshopdes;
    public ImageView imageview;
    public ItemClickListner listner;
    public CardView cardviewhomepage;

    public ShopViewHolder(@NonNull View itemView)
    {
        super(itemView);

        imageview = (ImageView) itemView.findViewById(R.id.shopimage);
        txtshopname = (TextView) itemView.findViewById(R.id.txtshopname);
        txtshopdes = (TextView) itemView.findViewById(R.id.txtshopdes);
        cardviewhomepage = (CardView) itemView.findViewById(R.id.homepagedesign);
    }

    public void setItemClickListner(ItemClickListner listner)
    {
        this.listner = listner;
    }

    @Override
    public void onClick(View v)
    {
        listner.onClick(v,getAdapterPosition(),false );
    }
}
