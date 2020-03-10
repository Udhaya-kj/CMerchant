/*
 * Corals app
 * This version includes cashback module
 *
 * OpenAPI spec version: 1.13.2
 * Contact: contact@corals.life
 *
 * NOTE: This class is auto generated by the swagger code generator program.
 * https://github.com/swagger-api/swagger-codegen.git
 * Do not edit the class manually.
 */

package life.corals.merchant.client.model;

import com.google.gson.annotations.SerializedName;

import java.util.Objects;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * Body20
 */

@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.JavaClientCodegen", date = "2019-09-19T07:17:58.745Z[GMT]")
public class Body20 {
  @SerializedName("deviceid")
  private String deviceid = null;

  @SerializedName("count")
  private String count = null;

  @SerializedName("callertype")
  private String callertype = null;

  @SerializedName("mer_id")
  private String merId = null;

  @SerializedName("customerid")
  private String customerid = null;

  @SerializedName("sessiontoken")
  private String sessiontoken = null;

  @SerializedName("transaction_type")
  private String transactionType = null;

  @SerializedName("transactionamount")
  private String transactionamount = null;

  @SerializedName("txnrefno")
  private String txnrefno = null;

  @SerializedName("outletid")
  private String outletid = null;

  public Body20 deviceid(String deviceid) {
    this.deviceid = deviceid;
    return this;
  }

   /**
   * Get deviceid
   * @return deviceid
  **/
  @Schema(required = true, description = "")
  public String getDeviceid() {
    return deviceid;
  }

  public void setDeviceid(String deviceid) {
    this.deviceid = deviceid;
  }

  public Body20 count(String count) {
    this.count = count;
    return this;
  }

   /**
   * Get count
   * @return count
  **/
  @Schema(description = "")
  public String getCount() {
    return count;
  }

  public void setCount(String count) {
    this.count = count;
  }

  public Body20 callertype(String callertype) {
    this.callertype = callertype;
    return this;
  }

   /**
   * Get callertype
   * @return callertype
  **/
  @Schema(description = "")
  public String getCallertype() {
    return callertype;
  }

  public void setCallertype(String callertype) {
    this.callertype = callertype;
  }

  public Body20 merId(String merId) {
    this.merId = merId;
    return this;
  }

   /**
   * Get merId
   * @return merId
  **/
  @Schema(required = true, description = "")
  public String getMerId() {
    return merId;
  }

  public void setMerId(String merId) {
    this.merId = merId;
  }

  public Body20 customerid(String customerid) {
    this.customerid = customerid;
    return this;
  }

   /**
   * Get customerid
   * @return customerid
  **/
  @Schema(description = "")
  public String getCustomerid() {
    return customerid;
  }

  public void setCustomerid(String customerid) {
    this.customerid = customerid;
  }

  public Body20 sessiontoken(String sessiontoken) {
    this.sessiontoken = sessiontoken;
    return this;
  }

   /**
   * Get sessiontoken
   * @return sessiontoken
  **/
  @Schema(required = true, description = "")
  public String getSessiontoken() {
    return sessiontoken;
  }

  public void setSessiontoken(String sessiontoken) {
    this.sessiontoken = sessiontoken;
  }

  public Body20 transactionType(String transactionType) {
    this.transactionType = transactionType;
    return this;
  }

   /**
   * Get transactionType
   * @return transactionType
  **/
  @Schema(description = "")
  public String getTransactionType() {
    return transactionType;
  }

  public void setTransactionType(String transactionType) {
    this.transactionType = transactionType;
  }

  public Body20 transactionamount(String transactionamount) {
    this.transactionamount = transactionamount;
    return this;
  }

   /**
   * Get transactionamount
   * @return transactionamount
  **/
  @Schema(description = "")
  public String getTransactionamount() {
    return transactionamount;
  }

  public void setTransactionamount(String transactionamount) {
    this.transactionamount = transactionamount;
  }

  public Body20 txnrefno(String txnrefno) {
    this.txnrefno = txnrefno;
    return this;
  }

   /**
   * Get txnrefno
   * @return txnrefno
  **/
  @Schema(description = "")
  public String getTxnrefno() {
    return txnrefno;
  }

  public void setTxnrefno(String txnrefno) {
    this.txnrefno = txnrefno;
  }

  public Body20 outletid(String outletid) {
    this.outletid = outletid;
    return this;
  }

   /**
   * Get outletid
   * @return outletid
  **/
  @Schema(description = "")
  public String getOutletid() {
    return outletid;
  }

  public void setOutletid(String outletid) {
    this.outletid = outletid;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Body20 body20 = (Body20) o;
    return Objects.equals(this.deviceid, body20.deviceid) &&
        Objects.equals(this.count, body20.count) &&
        Objects.equals(this.callertype, body20.callertype) &&
        Objects.equals(this.merId, body20.merId) &&
        Objects.equals(this.customerid, body20.customerid) &&
        Objects.equals(this.sessiontoken, body20.sessiontoken) &&
        Objects.equals(this.transactionType, body20.transactionType) &&
        Objects.equals(this.transactionamount, body20.transactionamount) &&
        Objects.equals(this.txnrefno, body20.txnrefno) &&
        Objects.equals(this.outletid, body20.outletid);
  }

  @Override
  public int hashCode() {
    return Objects.hash(deviceid, count, callertype, merId, customerid, sessiontoken, transactionType, transactionamount, txnrefno, outletid);
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Body20 {\n");

    sb.append("    deviceid: ").append(toIndentedString(deviceid)).append("\n");
    sb.append("    count: ").append(toIndentedString(count)).append("\n");
    sb.append("    callertype: ").append(toIndentedString(callertype)).append("\n");
    sb.append("    merId: ").append(toIndentedString(merId)).append("\n");
    sb.append("    customerid: ").append(toIndentedString(customerid)).append("\n");
    sb.append("    sessiontoken: ").append(toIndentedString(sessiontoken)).append("\n");
    sb.append("    transactionType: ").append(toIndentedString(transactionType)).append("\n");
    sb.append("    transactionamount: ").append(toIndentedString(transactionamount)).append("\n");
    sb.append("    txnrefno: ").append(toIndentedString(txnrefno)).append("\n");
    sb.append("    outletid: ").append(toIndentedString(outletid)).append("\n");
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
