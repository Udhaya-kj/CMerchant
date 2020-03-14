package life.corals.merchant.adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import life.corals.merchant.R;
import life.corals.merchant.activity.VoucherSetupHome;
import life.corals.merchant.activity.VoucherSetupPreview;
import life.corals.merchant.client.ApiCallback;
import life.corals.merchant.client.ApiException;
import life.corals.merchant.client.OkHttpApiClient;
import life.corals.merchant.client.api.MerchantApisApi;
import life.corals.merchant.client.model.SetUpRedemptionList;
import life.corals.merchant.client.model.SetUpVoucherList;
import life.corals.merchant.client.model.SetUpVoucherResponse;
import life.corals.merchant.utils.AlertDialogFailure;
import life.corals.merchant.utils.AlertDialogYesNo;
import life.corals.merchant.utils.IntermediateAlertDialog;

import static android.content.Context.MODE_PRIVATE;
import static life.corals.merchant.utils.Constants.DEVICE_ID;
import static life.corals.merchant.utils.Constants.MERCHANT_DETAILS_PREFERENCE;
import static life.corals.merchant.utils.Constants.MERCHANT_ID;

public class Vouchers_RecyclerviewAdapter extends RecyclerView.Adapter<Vouchers_RecyclerviewAdapter.ViewHolder> {
    private SharedPreferences preferences;
    Context context;
    ArrayList<SetUpRedemptionList> setUpRedemptionLists;
    private IntermediateAlertDialog intermediateAlertDialog;
    private List<SetUpRedemptionList> voucher_list = new ArrayList<>();
    public Vouchers_RecyclerviewAdapter(Context context, ArrayList<SetUpRedemptionList> setUpRedemptionLists) {
        this.context = context;
        this.setUpRedemptionLists = setUpRedemptionLists;
    }

    @Override
    public Vouchers_RecyclerviewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem = layoutInflater.inflate(R.layout.redeem_voucher, parent, false);
        Vouchers_RecyclerviewAdapter.ViewHolder viewHolder = new Vouchers_RecyclerviewAdapter.ViewHolder(listItem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(Vouchers_RecyclerviewAdapter.ViewHolder holder, final int position) {

        final SetUpRedemptionList setUpRedemptionList = setUpRedemptionLists.get(position);
        holder.tl.setText(setUpRedemptionList.getRedeemTitle());
        String date = setUpRedemptionList.getRedeemExpdt();
        Log.d("IsActive---", "onBindViewHolder: " + setUpRedemptionList.isIsActive());
        if (setUpRedemptionList.isIsActive().toString().equals("true")) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date strDate = null;
            try {
                strDate = sdf.parse(date);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            if (new Date().after(strDate)) {
                Log.d("Date---", "catalog outdated");
                holder.textView_expired.setVisibility(View.VISIBLE);
            } else {
                Log.d("Date---", "catalog not outdated");
                holder.textView_expired.setVisibility(View.GONE);
            }
        } else {
            holder.textView_expired.setVisibility(View.VISIBLE);
            holder.textView_expired.setText("Inactive");
            //holder.cardView.setAlpha(0.6f);
        }

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (setUpRedemptionList.isIsActive().toString().equals("true")) {
                    String act_time = setUpRedemptionList.getRedeemActtime(), exp_time = setUpRedemptionList.getRedeemEndtime();
                    Log.d("Time_Len---", "onClick: " + act_time.length() + "," + exp_time.length());
                    if (act_time.length() == 3) {
                        act_time = act_time.substring(0, 1) + ":" + act_time.substring(1, act_time.length());
                    } else if (act_time.length() == 4) {
                        act_time = act_time.substring(0, 2) + ":" + act_time.substring(2, act_time.length());
                    } else if (act_time.length() == 2) {
                        act_time = "00:" + act_time;
                    } else {
                        act_time = "00:0" + act_time;
                    }

                    if (exp_time.length() == 3) {
                        exp_time = exp_time.substring(0, 1) + ":" + exp_time.substring(1, exp_time.length());
                    } else if (exp_time.length() == 4) {
                        exp_time = exp_time.substring(0, 2) + ":" + exp_time.substring(2, exp_time.length());
                    } else if (exp_time.length() == 2) {
                        exp_time = "00:" + exp_time;
                    } else {
                        exp_time = "00:0" + exp_time;
                    }

                    String sharable = null;
                    String get_share = setUpRedemptionList.isIsCustSharable().toString();
                    if (get_share.equals("false")) {
                        sharable = "0";
                    } else {
                        sharable = "1";
                    }

                    Intent in = new Intent(context, VoucherSetupPreview.class);
                    in.putExtra("type_code", "0");
                    in.putExtra("back_press_code", "0");
                    in.putExtra("redeem_type", setUpRedemptionList.getRedeemType());
                    in.putExtra("title", setUpRedemptionList.getRedeemTitle());
                    in.putExtra("desc", setUpRedemptionList.getRedeemDescription());
                    in.putExtra("lead_title", setUpRedemptionList.getLeadTitle());
                    in.putExtra("lead_desc", setUpRedemptionList.getLeadDescription());
                    in.putExtra("pur_amount", setUpRedemptionList.getVoucherPurchaseAmount());
                    in.putExtra("points", setUpRedemptionList.getRedeemPoints());
                    in.putExtra("s_date", setUpRedemptionList.getRedeemActivedt());
                    in.putExtra("s_time", act_time);
                    in.putExtra("e_date", setUpRedemptionList.getRedeemExpdt());
                    in.putExtra("e_time", exp_time);
                    in.putExtra("act_days", setUpRedemptionList.getRedeemActivedays());
                    in.putExtra("bg_color", setUpRedemptionList.getVoucherBg());
                    in.putExtra("sharable", sharable);
                    in.putExtra("wallet", setUpRedemptionList.getRedeemDepositAmt());
                    in.putExtra("v_id", setUpRedemptionList.getAssignedVoucherId());
                    in.putExtra("v_count", setUpRedemptionList.getAssignedVoucherCount());
                    in.putExtra("mer_cd_redeem_id", setUpRedemptionList.getMerCbRedeemId());
                    in.putExtra("create_update_code", "0");
                    in.putExtra("isActive", setUpRedemptionList.isIsActive());
                    in.putExtra("terms_conditions",setUpRedemptionList.getTermsConditions());
                    in.putExtra("ref_reward_points",setUpRedemptionList.getReferralRewardPoints());
                    Log.d("Adapter---->", " Rectc : "+position + "," + setUpRedemptionList.getRedeemType() + "," + setUpRedemptionList.getRedeemTitle() + " " + setUpRedemptionList.getRedeemDescription() + " " + setUpRedemptionList.getVoucherBg() + " " + setUpRedemptionList.getLeadTitle() + " " + setUpRedemptionList.getLeadDescription() + " " + setUpRedemptionList.getAssignedVoucherCount()+","+setUpRedemptionList.getVoucherPurchaseAmount());
                    //e_date_list.get(position) + " " + e_time_list.get(position) + "," + act_dys_list.get(position) + "," + sharable_list.get(position)+","+sharable+","+voucher_bg_list.get(position));
                    context.startActivity(in);
                    ((Activity) context).finish();
                    ((Activity) context).overridePendingTransition(R.anim.swipe_in_right, R.anim.swipe_in_right);
                }
                else {
                    ((Activity) context).runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                           new AlertDialogYesNo(context, "Activate Voucher?", "Are you sure want to activate this voucher?", "OK", "CANCEL") {
                                @Override
                                public void onOKButtonClick() {
                                    callActivateAPI(setUpRedemptionList.getRedeemType(),setUpRedemptionList.getMerCbRedeemId().toString(),setUpRedemptionList.isIsCustSharable());
                                }

                                @Override
                                public void onCancelButtonClick() {

                                }
                            };
                        }
                    });
                }

            }
        });
    }

    @Override
    public int getItemCount() {
        return setUpRedemptionLists.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView tl, textView_expired;
        public CardView cardView;

        public ViewHolder(View rowView) {
            super(rowView);
            cardView = (CardView) rowView.findViewById(R.id.voucher_cardview);
            tl = (TextView) rowView.findViewById(R.id.text_title);
            textView_expired = (TextView) rowView.findViewById(R.id.text_expired);
        }
    }

    private void callActivateAPI(String v_type,String merId,boolean isSharable) {
        preferences = context.getSharedPreferences(MERCHANT_DETAILS_PREFERENCE, MODE_PRIVATE);
        intermediateAlertDialog = new IntermediateAlertDialog(context);
        SetUpRedemptionList setUpRedemptionList = new SetUpRedemptionList();
        setUpRedemptionList.setMerCbRedeemId(merId);
        setUpRedemptionList.setRedeemType(v_type);
        setUpRedemptionList.setIsActive(true);
        setUpRedemptionList.setIsCustSharable(isSharable);
        voucher_list.add(setUpRedemptionList);
        SetUpVoucherList setUpVoucherList = new SetUpVoucherList();
        setUpVoucherList.setMerId(preferences.getString(MERCHANT_ID, ""));
        setUpVoucherList.setMerDeviceId(preferences.getString(DEVICE_ID, ""));
        setUpVoucherList.setVoucherList(voucher_list);
        setUpVoucherList.setRequestCode("U");
        Log.d("Voucher--->", "onClick: " + voucher_list+" , "+v_type+","+merId+","+isSharable);
        try {
            activateVoucher(setUpVoucherList);
        } catch (ApiException e) {
            e.printStackTrace();
            Log.d("Voucher--->", "onClick: " + e.getMessage());
        }
    }

    private void activateVoucher(SetUpVoucherList requestBody) throws ApiException {
        Log.d("Voucher---", "deleteVoucher: " + requestBody);
        OkHttpApiClient okHttpApiClient = new OkHttpApiClient(Objects.requireNonNull(context));
        MerchantApisApi webMerchantApisApi = new MerchantApisApi();
        webMerchantApisApi.setApiClient(okHttpApiClient.getApiClient());

        webMerchantApisApi.merchantVoucherSetupAsync(requestBody, new ApiCallback<SetUpVoucherResponse>() {
            @Override
            public void onFailure(final ApiException e, int statusCode, Map<String, List<String>> responseHeaders) {
                if (intermediateAlertDialog != null) {
                    intermediateAlertDialog.dismissAlertDialog();
                }
                Log.d("Failedddd--->", "Code : 1 " + e.toString() + "," + statusCode);
                if (statusCode == 404) {
                    ((Activity) context).runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            new AlertDialogFailure(context, "Voucher not found. Please Try Again!", "OK", "Failed") {
                                @Override
                                public void onButtonClick() {

                                }
                            };
                        }
                    });

                } else {
                    ((Activity) context).runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            new AlertDialogFailure(context, "Please try again later!", "OK", "Something went wrong") {
                                @Override
                                public void onButtonClick() {

                                }
                            };
                        }
                    });

                }

                Log.d("onFailure--->", "" + e.getMessage());
                //Toast.makeText(Review_Activity.this, ""+e.getMessage().toString()+","+statusCode, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onSuccess(SetUpVoucherResponse result, int statusCode, Map<String, List<String>> responseHeaders) {
                Log.d("Failed--->", "Code :" + result.getStatusCode() + "," + result);
                if (intermediateAlertDialog != null) {
                    intermediateAlertDialog.dismissAlertDialog();
                }
                if (Integer.parseInt(result.getStatusCode()) == 200) {
                    Log.d("delete---", "onSuccess: " + Integer.parseInt(result.getStatusCode()));

                    ((Activity) context).runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            LayoutInflater factory = LayoutInflater.from(context);
                            final View deleteDialogView = factory.inflate(R.layout.success_dialog, null);
                            final AlertDialog deleteDialog = new AlertDialog.Builder(context).create();
                            TextView textView_delete = (TextView) deleteDialogView.findViewById(R.id.text_dialog);
                            textView_delete.setText("Voucher activated successfully");
                            deleteDialog.setView(deleteDialogView);
                            deleteDialog.show();

                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    deleteDialog.dismiss();
                                    ((Activity)context).startActivity(new Intent(context,VoucherSetupHome.class));
                                    ((Activity)context).finish();

                                }
                            }, 3000);

                        }
                    });

                } else {
                    Log.d("Failed--->", "Code : 3");
                    ((Activity) context).runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            new AlertDialogFailure(context, "Please try again later!", "OK", "Something went wrong") {
                                @Override
                                public void onButtonClick() {

                                }
                            };
                        }
                    });
                }

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


