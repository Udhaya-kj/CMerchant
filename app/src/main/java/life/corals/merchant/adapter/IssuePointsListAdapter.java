package life.corals.merchant.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.List;

import life.corals.merchant.R;
import life.corals.merchant.client.model.InlineResponse20018Customers;

public class IssuePointsListAdapter extends RecyclerView.Adapter<IssuePointsListAdapter.MyViewHolder> {

    private List<InlineResponse20018Customers> issueHistry;
    private Context mCtx;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView customerId, customerName,issuepoints,time,type,mobilenumber, pointsAdded, pointsBalance, issueDate, expiryDate, activeCustomer;
   private LinearLayout nameLayout;
        private MyViewHolder(View view) {
            super(view);
            customerId = (TextView) view.findViewById(R.id.issue_customer_id);
            customerName = (TextView) view.findViewById(R.id.issue_customer_name);
            mobilenumber = (TextView) view.findViewById(R.id.issue_mobile_number);
            pointsAdded = (TextView) view.findViewById(R.id.issue_points_added);
            pointsBalance = (TextView) view.findViewById(R.id.issue_points_balance);
            issueDate = (TextView) view.findViewById(R.id.issue_issued_time);
            expiryDate = (TextView) view.findViewById(R.id.issue_expiry_date);
            activeCustomer = (TextView) view.findViewById(R.id.customer_active);
            type = (TextView) view.findViewById(R.id.points_type);

            nameLayout = view.findViewById(R.id.name_layout);

            issuepoints = (TextView) view.findViewById(R.id.points);
            time = (TextView) view.findViewById(R.id.time);
        }
    }

    public IssuePointsListAdapter(List<InlineResponse20018Customers> inlineResponse20018Customers, Context mCtx) {
        this.issueHistry = inlineResponse20018Customers;
        this.mCtx = mCtx;
    }

    @Override
    public IssuePointsListAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_issue_points_history, parent, false);

        return new IssuePointsListAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(IssuePointsListAdapter.MyViewHolder holder, int position) {
        InlineResponse20018Customers issueHistory = issueHistry.get(position);

        holder.customerId.setText(issueHistory.getCustId());
        holder.mobilenumber.setText(issueHistory.getMobileNo());
        holder.pointsBalance.setText(issueHistory.getPointsBal() + " points");
        Timestamp issueTime = Timestamp.valueOf(issueHistory.getIssuedDate());
        Timestamp expTime = Timestamp.valueOf(issueHistory.getExpiryDate()+" 00:00:00");

        SimpleDateFormat sdfDate = new SimpleDateFormat("dd MMM yyyy hh:mm a");
        SimpleDateFormat sdfDate1 = new SimpleDateFormat("dd MMM yyyy");

        holder.issueDate.setText(sdfDate.format(issueTime));
        holder.expiryDate.setText(sdfDate1.format(expTime));
        Log.d("issue__History", "onBindViewHolder:  "+issueHistory.getTxnType());
        if (issueHistory.getTxnType()!=null){

            if (issueHistory.getTxnType().equals("R")) {
                holder.issuepoints.setText("Points redeemed ");
                holder.time.setText("Redeemed time");
                holder.pointsAdded.setText(issueHistory.getPointsRedeemed() + " points");
                holder.type.setText("Redeem Points");
            }else {
                holder.issuepoints.setText("Points issued");
                holder.time.setText("Issued time");
                holder.pointsAdded.setText(issueHistory.getPointsIssued() + " points");
                holder.type.setText("Issue Points");
            }
        }else {
            holder.issuepoints.setText("nill");
            holder.time.setText("nill");
            holder.pointsAdded.setText("nill");
            holder.type.setText("nill");
        }


        if(issueHistory.getCustName()!=null){
            if (!issueHistory.getCustName().equals("") ){
                holder.customerName.setText(issueHistory.getCustName());
                holder.nameLayout.setVisibility(View.VISIBLE);
            }else {
                holder.nameLayout.setVisibility(View.GONE);
            }
        }else{
            holder.nameLayout.setVisibility(View.GONE);
        }

        if (issueHistory.isIsCustActive()) {
            holder.activeCustomer.setVisibility(View.GONE);
        } else {
            holder.activeCustomer.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public int getItemCount() {
        return issueHistry.size();
    }

    public List<InlineResponse20018Customers> getTopupList() {
        return issueHistry;
    }

}