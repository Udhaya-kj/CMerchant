package life.corals.merchant.adapter;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import life.corals.merchant.R;


public class ViewSlider_RecyclerAdapter extends RecyclerView.Adapter<ViewSlider_RecyclerAdapter.ViewHolder> {

    private ArrayList<String> title_list,desc_list,validity_list,colors_list,sharable_list;

    // RecyclerView recyclerView;
    public ViewSlider_RecyclerAdapter(ArrayList<String> title_list, ArrayList<String> desc_list, ArrayList<String> validity_list, ArrayList<String> colors_list, ArrayList<String> sharable_list) {
        this.title_list = title_list;
        this.desc_list = desc_list;
        this.validity_list = validity_list;
        this.colors_list = colors_list;
        this.sharable_list = sharable_list;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem= layoutInflater.inflate(R.layout.voucher_layout_view, parent, false);
        ViewHolder viewHolder = new ViewHolder(listItem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        holder.tv_title.setText(title_list.get(position));
        holder.tv_desc.setText(desc_list.get(position));
        holder.tv_val .setText(validity_list.get(position));
        holder.cardView.setCardBackgroundColor(Color.parseColor(colors_list.get(position)));

        if(!sharable_list.get(position).equals("1")){
            holder.view.setVisibility(View.VISIBLE);
            holder.layout_share.setVisibility(View.GONE);
        }
    }


    @Override
    public int getItemCount() {
        return title_list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView tv_title,tv_desc,tv_val;
        CardView cardView;
        View view;
        LinearLayout layout_share;
        public ViewHolder(View itemView) {
            super(itemView);

            this.layout_share = (LinearLayout) itemView.findViewById(R.id.layout_share);
            this.view = (View) itemView.findViewById(R.id.view_top);
            this.tv_title = (TextView) itemView.findViewById(R.id.text_title);
            this.tv_desc = (TextView) itemView.findViewById(R.id.text_desc);
            this.tv_val = (TextView) itemView.findViewById(R.id.text_terms_conditions);
            this.cardView = (CardView) itemView.findViewById(R.id.cardview_voucher);

        }
    }


}
