package life.corals.merchant.adapter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import life.corals.merchant.R;
import life.corals.merchant.activity.CashCampaigns;
import life.corals.merchant.activity.Homenew;
import life.corals.merchant.client.ApiCallback;
import life.corals.merchant.client.ApiException;
import life.corals.merchant.client.OkHttpApiClient;
import life.corals.merchant.client.api.MerchantApisApi;
import life.corals.merchant.client.model.Body13;
import life.corals.merchant.client.model.InlineResponse20010Cashcampaigns;
import life.corals.merchant.client.model.MerchantcampaignppcupdatemCashcampaigns;
import life.corals.merchant.utils.AlertDialogFailure;
import life.corals.merchant.utils.AlertDialogYesNo;
import life.corals.merchant.utils.AlertSuccess;
import life.corals.merchant.utils.IntermediateAlertDialog;
import life.corals.merchant.utils.Util;

import static android.content.Context.MODE_PRIVATE;
import static life.corals.merchant.utils.Constants.CURRENCY_SYMBOL;
import static life.corals.merchant.utils.Constants.DEVICE_ID;
import static life.corals.merchant.utils.Constants.MERCHANT_DETAILS_PREFERENCE;
import static life.corals.merchant.utils.Constants.MERCHANT_ID;

public class getCampaignlistAdapter extends RecyclerView.Adapter<getCampaignlistAdapter.MyViewHolder> {

    private List<InlineResponse20010Cashcampaigns> campaignList;
    private Context mCtx;
    private SharedPreferences preferences;
    private List<MerchantcampaignppcupdatemCashcampaigns> cashcampaignsList;
    private Body13 body13 = new Body13();
    private AlertDialogFailure failureAlertDialog;
    private String campaignId;
    private IntermediateAlertDialog intermediateAlertDialog;
    private Handler mHandler;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView topupAmount, bonus, amountSymbol, bonusSymbol;
        private Switch active;

        private MyViewHolder(View view) {
            super(view);
            mHandler = new Handler();
            topupAmount = (TextView) view.findViewById(R.id.topup);
            bonus = (TextView) view.findViewById(R.id.bonus);
            amountSymbol = (TextView) view.findViewById(R.id.amount_symbol);
            bonusSymbol = (TextView) view.findViewById(R.id.bonus_symbol);
            active = (Switch) view.findViewById(R.id.switch_active);
        }
    }

    public getCampaignlistAdapter(List<InlineResponse20010Cashcampaigns> inlineResponse200Cashcampaigns, Context mCtx) {
        this.campaignList = inlineResponse200Cashcampaigns;
        this.mCtx = mCtx;

        preferences = mCtx.getSharedPreferences(MERCHANT_DETAILS_PREFERENCE, MODE_PRIVATE);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_topup, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        InlineResponse20010Cashcampaigns campaignLst = campaignList.get(position);
        holder.topupAmount.setText(campaignLst.getTopupamt());
        holder.bonus.setText(campaignLst.getTopupbonus());
        holder.amountSymbol.setText(preferences.getString(CURRENCY_SYMBOL, ""));
        holder.bonusSymbol.setText(preferences.getString(CURRENCY_SYMBOL, ""));
        holder.active.setOnCheckedChangeListener(null);
        if (campaignLst.isIsActive()) {
            holder.active.setChecked(true);
           // holder.active.setBackgroundColor(Color.GREEN);
        } else {
           // holder.active.setBackgroundColor(Color.RED);
            holder.active.setChecked(false);
        }

        holder.active.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                campaignId = campaignLst.getCampaignid();

                if (isChecked) {
                    new AlertDialogYesNo(mCtx, "Confirmation", "Are you sure want activate Campaign?", "YES", "NO") {
                        @Override
                        public void onOKButtonClick() {
                            intermediateAlertDialog = new IntermediateAlertDialog(mCtx);
                            updatecallApi(campaignId, true);
                        }

                        @Override
                        public void onCancelButtonClick() {
                            mCtx.startActivity(new Intent(mCtx, CashCampaigns.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK));

                        }
                    };

                } else {
                    new AlertDialogYesNo(mCtx, "Confirmation", "Are you sure want to Deactivate campaign?", "YES", "NO") {

                        @Override
                        public void onOKButtonClick() {
                            intermediateAlertDialog = new IntermediateAlertDialog(mCtx);
                            updatecallApi(campaignId,false);
                        }

                        @Override
                        public void onCancelButtonClick() {
                            mCtx.startActivity(new Intent(mCtx, CashCampaigns.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK));

                        }
                    };
                }

            }
        });


    }

    @Override
    public int getItemCount() {
        return campaignList.size();
    }

    private void updatecallApi(String id, boolean isActive) {
        if (Util.getConnectivityStatusString(mCtx)) {
            try {
                getRequestBody13(id, isActive);
                updateCampaigns(body13, isActive);
            } catch (ApiException e) {
                intermediateAlertDialog.dismissAlertDialog();
                e.printStackTrace();
            }
        } else {
            intermediateAlertDialog.dismissAlertDialog();
            new AlertDialogFailure(mCtx, "Please turn ON your data connection", "Retry", "No internet connection !") {
                @Override
                public void onButtonClick() {
                    updatecallApi(campaignId, isActive);
                }
            };
        }
    }

    private void getRequestBody13(String id, boolean active) {
        cashcampaignsList = new ArrayList<>();
        MerchantcampaignppcupdatemCashcampaigns response200Cashcampaigns = new MerchantcampaignppcupdatemCashcampaigns();
        response200Cashcampaigns.setIsActive(active);
        response200Cashcampaigns.setCampaignid(id);
        response200Cashcampaigns.setTopupamt("");
        response200Cashcampaigns.setTopupbonus("");
        response200Cashcampaigns.setExpiryDate("");
        response200Cashcampaigns.setActiveDate("");
        cashcampaignsList.add(response200Cashcampaigns);
        body13.setUpdate(true);
        body13.setCashcampaigns(cashcampaignsList);
        body13.setDeviceid(preferences.getString(DEVICE_ID, ""));
        body13.setMerId(preferences.getString(MERCHANT_ID, ""));
        body13.setSessiontoken("notoken");

    }

    private void updateCampaigns(Body13 requestBody, boolean active) throws ApiException {
        OkHttpApiClient okHttpApiClient = new OkHttpApiClient(mCtx);
        MerchantApisApi merchantApisApi = new MerchantApisApi();
        merchantApisApi.setApiClient(okHttpApiClient.getApiClient());

        merchantApisApi.merchantCampaignPpcUpdateMPostAsync(requestBody, new ApiCallback<Void>() {
            @Override
            public void onFailure(ApiException e, int statusCode, Map<String, List<String>> responseHeaders) {

                intermediateAlertDialog.dismissAlertDialog();
                new Thread(() -> mHandler.post(() -> new AlertDialogFailure(mCtx, "Please try again later", "OK", "Something went wrong!") {
                    @Override
                    public void onButtonClick() {
                        mCtx.startActivity(new Intent(mCtx, Homenew.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK));
                    }
                })).start();

            }

            @Override
            public void onSuccess(Void result, int statusCode, Map<String, List<String>> responseHeaders) {
                intermediateAlertDialog.dismissAlertDialog();

                new Thread(() -> mHandler.post(() -> {
                    if (statusCode == 200) {
                        if (active) {
                            new AlertSuccess(mCtx, "Done !", "Successfully Activated") {
                                @Override
                                public void onButtonClick() {
                                    mCtx.startActivity(new Intent(mCtx, CashCampaigns.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK));
                                }
                            };
                        } else {
                            new AlertSuccess(mCtx, "Done !", "Successfully deactivated") {
                                @Override
                                public void onButtonClick() {
                                    mCtx.startActivity(new Intent(mCtx, CashCampaigns.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK));
                                }
                            };
                        }

                    } else {
                        failureAlertDialog = new AlertDialogFailure(mCtx, "Please try again later", "OK", "Something went wrong!") {
                            @Override
                            public void onButtonClick() {
                                mCtx.startActivity(new Intent(mCtx, Homenew.class));
                            }
                        };
                    }

                })).start();
            }

            @Override
            public void onUploadProgress(long bytesWritten, long contentLength, boolean done) {

            }

            @Override
            public void onDownloadProgress(long bytesRead, long contentLength, boolean done) {

            }
        });

    }
}