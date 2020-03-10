package life.corals.merchant.adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.HashMap;
import java.util.List;

import life.corals.merchant.R;
import life.corals.merchant.client.model.InlineResponse20013;
import life.corals.merchant.client.model.InlineResponse20013Customersrec;
import life.corals.merchant.client.model.InlineResponse200Cashcampaigns;
import life.corals.merchant.utils.MySharedPreference;

import static android.content.Context.MODE_PRIVATE;
import static life.corals.merchant.utils.Constants.CURRENCY_SYMBOL;
import static life.corals.merchant.utils.Constants.MERCHANT_DETAILS_PREFERENCE;

public class CustomerListAdapter extends RecyclerView.Adapter<CustomerListAdapter.MyViewHolder> {
    private List<InlineResponse20013Customersrec> customerList;

    private Context mCtx;
    private SharedPreferences preferences;


    public CustomerListAdapter(Context mCtx, List<InlineResponse20013Customersrec> customerList) {
        this.mCtx = mCtx;
        this.customerList = customerList;
        preferences = mCtx.getSharedPreferences(MERCHANT_DETAILS_PREFERENCE, MODE_PRIVATE);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView custId, custMble, custWalletbal,custCurr,cbpoints, custName;
        public LinearLayout CustNameLayout;

        private MyViewHolder(View view) {
            super(view);
            cbpoints = (TextView) view.findViewById(R.id.cb_points_bal);
            custId = (TextView) view.findViewById(R.id.cust_id);
            custMble = (TextView) view.findViewById(R.id.cust_mobile);
            custWalletbal = (TextView) view.findViewById(R.id.cust_wall);
            custCurr = (TextView) view.findViewById(R.id.cust_curr);
            CustNameLayout = (LinearLayout) view.findViewById(R.id.cust_name_layout);
            custName = (TextView) view.findViewById(R.id.cust_name);
        }
    }

    @NonNull
    @Override
    public CustomerListAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_customer_list, parent, false);
        return new CustomerListAdapter.MyViewHolder(itemView);
    }


    @Override
    public void onBindViewHolder(@NonNull CustomerListAdapter.MyViewHolder holder, int position) {
        InlineResponse20013Customersrec customerlist = customerList.get(position);
        Log.d("onBindViewHolder", "onBindViewHolder: "+customerlist);
        if (customerlist.getCustName()!=null){
            holder.custName.setText(customerlist.getCustName());
            holder.CustNameLayout.setVisibility(View.VISIBLE);
        }else {
            holder.CustNameLayout.setVisibility(View.GONE);
        }
        holder.custId.setText(customerlist.getCustId());
        holder.custMble.setText(customerlist.getCustMobile());
        holder.custWalletbal.setText(customerlist.getCustWalletBal());
        holder.custCurr.setText(preferences.getString(CURRENCY_SYMBOL,""));
        holder.cbpoints.setText(customerlist.getCustCbPointsBalance()+" points");
    }

    @Override
    public int getItemCount() {
        return customerList.size();
    }

    public List<InlineResponse20013Customersrec> getCustomerList() {
        return customerList;
    }
}