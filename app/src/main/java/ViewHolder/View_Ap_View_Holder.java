package ViewHolder;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shoppy.R;

import Interfaces.ItemClickListner;


public class View_Ap_View_Holder extends RecyclerView.ViewHolder implements View.OnClickListener
{
    public TextView textviewpname,textviewquentity,textviewprice;
    public ItemClickListner listner;

    public View_Ap_View_Holder(@NonNull View itemView)
    {
        super(itemView);

        textviewpname = (itemView).findViewById(R.id.appname);
        textviewquentity = (itemView).findViewById(R.id.approductquentity);
        textviewprice = (itemView).findViewById(R.id.apprice);
    }

    public void setItemclickListner(ItemClickListner listner)
    {
        this.listner = listner;
    }

    @Override
    public void onClick(View v)
    {
        listner.onClick(v, getAdapterPosition(),false);
    }
}
