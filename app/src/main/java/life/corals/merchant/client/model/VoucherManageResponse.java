package life.corals.merchant.client.model;
import java.util.Objects;
import com.google.gson.annotations.SerializedName;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * VoucherManageResponse
 */
@javax.annotation.Generated(value = "io.swagger.codegen.languages.JavaClientCodegen", date = "2020-03-11T04:44:18.800Z")
public class VoucherManageResponse {
  @SerializedName("status_code")
  private String statusCode = null;

  @SerializedName("status_message")
  private String statusMessage = null;

  @SerializedName("cust_name")
  private String custName = null;

  @SerializedName("redeem_point_balance")
  private String redeemPointBalance = null;

  @SerializedName("redeem_point_exp_date")
  private String redeemPointExpDate = null;

  @SerializedName("wallet_balance_exp_date")
  private String walletBalanceExpDate = null;

  @SerializedName("cust_wallet_balance")
  private String custWalletBalance = null;

  @SerializedName("redeem_deposit_amount")
  private String redeemDepositAmount = null;

  @SerializedName("cb_ledger_id")
  private String cbLedgerId = null;

  @SerializedName("cust_id")
  private String custId = null;

  @SerializedName("redeem_message")
  private String redeemMessage = null;

  @SerializedName("referral_reward")
  private String referralReward = null;

  public VoucherManageResponse statusCode(String statusCode) {
    this.statusCode = statusCode;
    return this;
  }

  /**
   * Get statusCode
   * @return statusCode
   **/
  @Schema(description = "")
  public String getStatusCode() {
    return statusCode;
  }

  public void setStatusCode(String statusCode) {
    this.statusCode = statusCode;
  }

  public VoucherManageResponse statusMessage(String statusMessage) {
    this.statusMessage = statusMessage;
    return this;
  }

  /**
   * Get statusMessage
   * @return statusMessage
   **/
  @Schema(description = "")
  public String getStatusMessage() {
    return statusMessage;
  }

  public void setStatusMessage(String statusMessage) {
    this.statusMessage = statusMessage;
  }

  public VoucherManageResponse custName(String custName) {
    this.custName = custName;
    return this;
  }

  /**
   * Get custName
   * @return custName
   **/
  @Schema(description = "")
  public String getCustName() {
    return custName;
  }

  public void setCustName(String custName) {
    this.custName = custName;
  }

  public VoucherManageResponse redeemPointBalance(String redeemPointBalance) {
    this.redeemPointBalance = redeemPointBalance;
    return this;
  }

  /**
   * Get redeemPointBalance
   * @return redeemPointBalance
   **/
  @Schema(description = "")
  public String getRedeemPointBalance() {
    return redeemPointBalance;
  }

  public void setRedeemPointBalance(String redeemPointBalance) {
    this.redeemPointBalance = redeemPointBalance;
  }

  public VoucherManageResponse redeemPointExpDate(String redeemPointExpDate) {
    this.redeemPointExpDate = redeemPointExpDate;
    return this;
  }

  /**
   * Get redeemPointExpDate
   * @return redeemPointExpDate
   **/
  @Schema(description = "")
  public String getRedeemPointExpDate() {
    return redeemPointExpDate;
  }

  public void setRedeemPointExpDate(String redeemPointExpDate) {
    this.redeemPointExpDate = redeemPointExpDate;
  }

  public VoucherManageResponse walletBalanceExpDate(String walletBalanceExpDate) {
    this.walletBalanceExpDate = walletBalanceExpDate;
    return this;
  }

  /**
   * Get walletBalanceExpDate
   * @return walletBalanceExpDate
   **/
  @Schema(description = "")
  public String getWalletBalanceExpDate() {
    return walletBalanceExpDate;
  }

  public void setWalletBalanceExpDate(String walletBalanceExpDate) {
    this.walletBalanceExpDate = walletBalanceExpDate;
  }

  public VoucherManageResponse custWalletBalance(String custWalletBalance) {
    this.custWalletBalance = custWalletBalance;
    return this;
  }

  /**
   * Get custWalletBalance
   * @return custWalletBalance
   **/
  @Schema(description = "")
  public String getCustWalletBalance() {
    return custWalletBalance;
  }

  public void setCustWalletBalance(String custWalletBalance) {
    this.custWalletBalance = custWalletBalance;
  }

  public VoucherManageResponse redeemDepositAmount(String redeemDepositAmount) {
    this.redeemDepositAmount = redeemDepositAmount;
    return this;
  }

  /**
   * Get redeemDepositAmount
   * @return redeemDepositAmount
   **/
  @Schema(description = "")
  public String getRedeemDepositAmount() {
    return redeemDepositAmount;
  }

  public void setRedeemDepositAmount(String redeemDepositAmount) {
    this.redeemDepositAmount = redeemDepositAmount;
  }

  public VoucherManageResponse cbLedgerId(String cbLedgerId) {
    this.cbLedgerId = cbLedgerId;
    return this;
  }

  /**
   * Get cbLedgerId
   * @return cbLedgerId
   **/
  @Schema(description = "")
  public String getCbLedgerId() {
    return cbLedgerId;
  }

  public void setCbLedgerId(String cbLedgerId) {
    this.cbLedgerId = cbLedgerId;
  }

  public VoucherManageResponse custId(String custId) {
    this.custId = custId;
    return this;
  }

  /**
   * Get custId
   * @return custId
   **/
  @Schema(description = "")
  public String getCustId() {
    return custId;
  }

  public void setCustId(String custId) {
    this.custId = custId;
  }

  public VoucherManageResponse redeemMessage(String redeemMessage) {
    this.redeemMessage = redeemMessage;
    return this;
  }

  /**
   * Get redeemMessage
   * @return redeemMessage
   **/
  @Schema(description = "")
  public String getRedeemMessage() {
    return redeemMessage;
  }

  public void setRedeemMessage(String redeemMessage) {
    this.redeemMessage = redeemMessage;
  }

  public VoucherManageResponse referralReward(String referralReward) {
    this.referralReward = referralReward;
    return this;
  }

  /**
   * Get referralReward
   * @return referralReward
   **/
  @Schema(description = "")
  public String getReferralReward() {
    return referralReward;
  }

  public void setReferralReward(String referralReward) {
    this.referralReward = referralReward;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    VoucherManageResponse voucherManageResponse = (VoucherManageResponse) o;
    return Objects.equals(this.statusCode, voucherManageResponse.statusCode) &&
            Objects.equals(this.statusMessage, voucherManageResponse.statusMessage) &&
            Objects.equals(this.custName, voucherManageResponse.custName) &&
            Objects.equals(this.redeemPointBalance, voucherManageResponse.redeemPointBalance) &&
            Objects.equals(this.redeemPointExpDate, voucherManageResponse.redeemPointExpDate) &&
            Objects.equals(this.walletBalanceExpDate, voucherManageResponse.walletBalanceExpDate) &&
            Objects.equals(this.custWalletBalance, voucherManageResponse.custWalletBalance) &&
            Objects.equals(this.redeemDepositAmount, voucherManageResponse.redeemDepositAmount) &&
            Objects.equals(this.cbLedgerId, voucherManageResponse.cbLedgerId) &&
            Objects.equals(this.custId, voucherManageResponse.custId) &&
            Objects.equals(this.redeemMessage, voucherManageResponse.redeemMessage) &&
            Objects.equals(this.referralReward, voucherManageResponse.referralReward);
  }

  @Override
  public int hashCode() {
    return Objects.hash(statusCode, statusMessage, custName, redeemPointBalance, redeemPointExpDate, walletBalanceExpDate, custWalletBalance, redeemDepositAmount, cbLedgerId, custId, redeemMessage, referralReward);
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class VoucherManageResponse {\n");

    sb.append("    statusCode: ").append(toIndentedString(statusCode)).append("\n");
    sb.append("    statusMessage: ").append(toIndentedString(statusMessage)).append("\n");
    sb.append("    custName: ").append(toIndentedString(custName)).append("\n");
    sb.append("    redeemPointBalance: ").append(toIndentedString(redeemPointBalance)).append("\n");
    sb.append("    redeemPointExpDate: ").append(toIndentedString(redeemPointExpDate)).append("\n");
    sb.append("    walletBalanceExpDate: ").append(toIndentedString(walletBalanceExpDate)).append("\n");
    sb.append("    custWalletBalance: ").append(toIndentedString(custWalletBalance)).append("\n");
    sb.append("    redeemDepositAmount: ").append(toIndentedString(redeemDepositAmount)).append("\n");
    sb.append("    cbLedgerId: ").append(toIndentedString(cbLedgerId)).append("\n");
    sb.append("    custId: ").append(toIndentedString(custId)).append("\n");
    sb.append("    redeemMessage: ").append(toIndentedString(redeemMessage)).append("\n");
    sb.append("    referralReward: ").append(toIndentedString(referralReward)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces
   * (except the first line).
   */
  private String toIndentedString(Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }

}

