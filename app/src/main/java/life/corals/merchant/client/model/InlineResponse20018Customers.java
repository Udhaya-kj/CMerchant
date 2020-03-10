package life.corals.merchant.client.model;

import com.google.gson.annotations.SerializedName;

import java.util.Objects;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * InlineResponse20018Customers
 */
@javax.annotation.Generated(value = "io.swagger.codegen.languages.JavaClientCodegen", date = "2020-02-28T12:58:34.011Z")
public class InlineResponse20018Customers {
  @SerializedName("cust_name")
  private String custName = null;

  @SerializedName("cust_id")
  private String custId = null;

  @SerializedName("expiry_date")
  private String expiryDate = null;

  @SerializedName("is_cust_active")
  private Boolean isCustActive = null;

  @SerializedName("issued_date")
  private String issuedDate = null;

  @SerializedName("mobile_no")
  private String mobileNo = null;

  @SerializedName("points_bal")
  private String pointsBal = null;

  @SerializedName("points_issued")
  private String pointsIssued = null;

  @SerializedName("points_redeemed")
  private String pointsRedeemed = null;

  @SerializedName("redeem_title")
  private String redeemTitle = null;

  @SerializedName("redeem_type")
  private String redeemType = null;

  @SerializedName("txn_type")
  private String txnType = null;

  @SerializedName("txn_ref_no")
  private String txnRefNo = null;

  public InlineResponse20018Customers custName(String custName) {
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

  public InlineResponse20018Customers custId(String custId) {
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

  public InlineResponse20018Customers expiryDate(String expiryDate) {
    this.expiryDate = expiryDate;
    return this;
  }

  /**
   * Get expiryDate
   * @return expiryDate
   **/
  @Schema(description = "")
  public String getExpiryDate() {
    return expiryDate;
  }

  public void setExpiryDate(String expiryDate) {
    this.expiryDate = expiryDate;
  }

  public InlineResponse20018Customers isCustActive(Boolean isCustActive) {
    this.isCustActive = isCustActive;
    return this;
  }

  /**
   * Get isCustActive
   * @return isCustActive
   **/
  @Schema(description = "")
  public Boolean isIsCustActive() {
    return isCustActive;
  }

  public void setIsCustActive(Boolean isCustActive) {
    this.isCustActive = isCustActive;
  }

  public InlineResponse20018Customers issuedDate(String issuedDate) {
    this.issuedDate = issuedDate;
    return this;
  }

  /**
   * Get issuedDate
   * @return issuedDate
   **/
  @Schema(description = "")
  public String getIssuedDate() {
    return issuedDate;
  }

  public void setIssuedDate(String issuedDate) {
    this.issuedDate = issuedDate;
  }

  public InlineResponse20018Customers mobileNo(String mobileNo) {
    this.mobileNo = mobileNo;
    return this;
  }

  /**
   * Get mobileNo
   * @return mobileNo
   **/
  @Schema(description = "")
  public String getMobileNo() {
    return mobileNo;
  }

  public void setMobileNo(String mobileNo) {
    this.mobileNo = mobileNo;
  }

  public InlineResponse20018Customers pointsBal(String pointsBal) {
    this.pointsBal = pointsBal;
    return this;
  }

  /**
   * Get pointsBal
   * @return pointsBal
   **/
  @Schema(description = "")
  public String getPointsBal() {
    return pointsBal;
  }

  public void setPointsBal(String pointsBal) {
    this.pointsBal = pointsBal;
  }

  public InlineResponse20018Customers pointsIssued(String pointsIssued) {
    this.pointsIssued = pointsIssued;
    return this;
  }

  /**
   * Get pointsIssued
   * @return pointsIssued
   **/
  @Schema(description = "")
  public String getPointsIssued() {
    return pointsIssued;
  }

  public void setPointsIssued(String pointsIssued) {
    this.pointsIssued = pointsIssued;
  }

  public InlineResponse20018Customers pointsRedeemed(String pointsRedeemed) {
    this.pointsRedeemed = pointsRedeemed;
    return this;
  }

  /**
   * Get pointsRedeemed
   * @return pointsRedeemed
   **/
  @Schema(description = "")
  public String getPointsRedeemed() {
    return pointsRedeemed;
  }

  public void setPointsRedeemed(String pointsRedeemed) {
    this.pointsRedeemed = pointsRedeemed;
  }

  public InlineResponse20018Customers redeemTitle(String redeemTitle) {
    this.redeemTitle = redeemTitle;
    return this;
  }

  /**
   * Get redeemTitle
   * @return redeemTitle
   **/
  @Schema(description = "")
  public String getRedeemTitle() {
    return redeemTitle;
  }

  public void setRedeemTitle(String redeemTitle) {
    this.redeemTitle = redeemTitle;
  }

  public InlineResponse20018Customers redeemType(String redeemType) {
    this.redeemType = redeemType;
    return this;
  }

  /**
   * Get redeemType
   * @return redeemType
   **/
  @Schema(description = "")
  public String getRedeemType() {
    return redeemType;
  }

  public void setRedeemType(String redeemType) {
    this.redeemType = redeemType;
  }

  public InlineResponse20018Customers txnType(String txnType) {
    this.txnType = txnType;
    return this;
  }

  /**
   * Get txnType
   * @return txnType
   **/
  @Schema(description = "")
  public String getTxnType() {
    return txnType;
  }

  public void setTxnType(String txnType) {
    this.txnType = txnType;
  }

  public InlineResponse20018Customers txnRefNo(String txnRefNo) {
    this.txnRefNo = txnRefNo;
    return this;
  }

  /**
   * tbl_cust_txn_ledger
   * @return txnRefNo
   **/
  @Schema(description = "")
  public String getTxnRefNo() {
    return txnRefNo;
  }

  public void setTxnRefNo(String txnRefNo) {
    this.txnRefNo = txnRefNo;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    InlineResponse20018Customers inlineResponse20018Customers = (InlineResponse20018Customers) o;
    return Objects.equals(this.custName, inlineResponse20018Customers.custName) &&
            Objects.equals(this.custId, inlineResponse20018Customers.custId) &&
            Objects.equals(this.expiryDate, inlineResponse20018Customers.expiryDate) &&
            Objects.equals(this.isCustActive, inlineResponse20018Customers.isCustActive) &&
            Objects.equals(this.issuedDate, inlineResponse20018Customers.issuedDate) &&
            Objects.equals(this.mobileNo, inlineResponse20018Customers.mobileNo) &&
            Objects.equals(this.pointsBal, inlineResponse20018Customers.pointsBal) &&
            Objects.equals(this.pointsIssued, inlineResponse20018Customers.pointsIssued) &&
            Objects.equals(this.pointsRedeemed, inlineResponse20018Customers.pointsRedeemed) &&
            Objects.equals(this.redeemTitle, inlineResponse20018Customers.redeemTitle) &&
            Objects.equals(this.redeemType, inlineResponse20018Customers.redeemType) &&
            Objects.equals(this.txnType, inlineResponse20018Customers.txnType) &&
            Objects.equals(this.txnRefNo, inlineResponse20018Customers.txnRefNo);
  }

  @Override
  public int hashCode() {
    return Objects.hash(custName, custId, expiryDate, isCustActive, issuedDate, mobileNo, pointsBal, pointsIssued, pointsRedeemed, redeemTitle, redeemType, txnType, txnRefNo);
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class InlineResponse20018Customers {\n");

    sb.append("    custName: ").append(toIndentedString(custName)).append("\n");
    sb.append("    custId: ").append(toIndentedString(custId)).append("\n");
    sb.append("    expiryDate: ").append(toIndentedString(expiryDate)).append("\n");
    sb.append("    isCustActive: ").append(toIndentedString(isCustActive)).append("\n");
    sb.append("    issuedDate: ").append(toIndentedString(issuedDate)).append("\n");
    sb.append("    mobileNo: ").append(toIndentedString(mobileNo)).append("\n");
    sb.append("    pointsBal: ").append(toIndentedString(pointsBal)).append("\n");
    sb.append("    pointsIssued: ").append(toIndentedString(pointsIssued)).append("\n");
    sb.append("    pointsRedeemed: ").append(toIndentedString(pointsRedeemed)).append("\n");
    sb.append("    redeemTitle: ").append(toIndentedString(redeemTitle)).append("\n");
    sb.append("    redeemType: ").append(toIndentedString(redeemType)).append("\n");
    sb.append("    txnType: ").append(toIndentedString(txnType)).append("\n");
    sb.append("    txnRefNo: ").append(toIndentedString(txnRefNo)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces
   * (except the first line).
   */
  private String toIndentedString(java.lang.Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }

}

