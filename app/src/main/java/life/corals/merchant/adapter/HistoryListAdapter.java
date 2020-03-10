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
import life.corals.merchant.client.model.InlineResponse20012Transactions;
import life.corals.merchant.utils.MySharedPreference;

import static android.content.Context.MODE_PRIVATE;
import static life.corals.merchant.utils.Constants.CURRENCY_SYMBOL;
import static life.corals.merchant.utils.Constants.MERCHANT_DETAILS_PREFERENCE;

public class HistoryListAdapter extends RecyclerView.Adapter<HistoryListAdapter.MyViewHolder> {

    private List<InlineResponse20012Transactions> historyList;
    private HashMap<String, String> outletNames = new HashMap<>();
    private MySharedPreference sharedPreference;
    private Context mCtx;
    private SharedPreferences preferences;


    public HistoryListAdapter(Context mCtx, List<InlineResponse20012Transactions> historyList, HashMap<String, String> outletNames) {
        this.mCtx = mCtx;
        this.historyList = historyList;
        this.outletNames = outletNames;
        sharedPreference = new MySharedPreference(mCtx);
        preferences =mCtx.getSharedPreferences(MERCHANT_DETAILS_PREFERENCE, MODE_PRIVATE);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView transactionType, payment,paymentCurrency, wallBal,wallBalCurrency, clientId, paymentDate, txnref, outletName;
        private  LinearLayout outletLayout;
        private MyViewHolder(View view) {
            super(view);

            transactionType = (TextView) view.findViewById(R.id.history_txn_type);
            payment = (TextView) view.findViewById(R.id.history_amount);
            wallBal = (TextView) view.findViewById(R.id.history_wallet_bal);
            clientId = (TextView) view.findViewById(R.id.history_client_id);
            paymentDate = (TextView) view.findViewById(R.id.history_payment_time);
            txnref = (TextView) view.findViewById(R.id.history_txn_ref);
            outletName = (TextView) view.findViewById(R.id.history_outlet_name);
            paymentCurrency =  (TextView) view.findViewById(R.id.history_amount_currency);
            wallBalCurrency =  (TextView) view.findViewById(R.id.history_wallet_bal_currency);


            outletLayout = view.findViewById(R.id.outlet_layout);


        }
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_history, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        InlineResponse20012Transactions todayHistory = historyList.get(position);
        String transactionType = sharedPreference.getTransactionType(todayHistory.getTransactionType());

        if (transactionType==null || transactionType.isEmpty()){
            holder.transactionType.setText(todayHistory.getTransactionType());
        }else{
            holder.transactionType.setText(transactionType);
        }

        holder.payment.setText(todayHistory.getPaymentAmt());
        holder.wallBal.setText(todayHistory.getWalletbalance());
        holder.clientId.setText(String.valueOf(todayHistory.getCustomerid()));
        holder.paymentDate.setText(todayHistory.getDatetime());
        holder.txnref.setText(todayHistory.getTxnrefno());

        Log.d("paymentDate ", "onBindViewHolder: "+outletNames.get(todayHistory.getOutletid()));
        if(todayHistory.getOutletid()!=null){
            if (outletNames.get(todayHistory.getOutletid())!=null){
                holder.outletName.setText(outletNames.get(todayHistory.getOutletid()));
            }else {
                holder.outletLayout.setVisibility(View.GONE);
            }
        }else{
           // holder.outletName.setText("Not applicable");
            holder.outletLayout.setVisibility(View.GONE);
        }

        holder.paymentCurrency.setText(preferences.getString(CURRENCY_SYMBOL,""));
        holder.wallBalCurrency.setText(preferences.getString(CURRENCY_SYMBOL,""));
    }

    @Override
    public int getItemCount() {
        return historyList.size();
    }
}