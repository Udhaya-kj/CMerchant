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
 * Body22
 */

@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.JavaClientCodegen", date = "2019-09-19T07:17:58.745Z[GMT]")
public class Body22 {
  @SerializedName("mer_id")
  private String merId = null;

  @SerializedName("cust_id")
  private String custId = null;

  @SerializedName("mobile_no")
  private String mobileNo = null;

  @SerializedName("sessiontoken")
  private String sessiontoken = null;

  @SerializedName("device_id")
  private String deviceId = null;

  @SerializedName("mer_cbredeem_id")
  private String merCbredeemId = null;

  public Body22 merId(String merId) {
    this.merId = merId;
    return this;
  }

   /**
   * Get merId
   * @return merId
  **/
  @Schema(description = "")
  public String getMerId() {
    return merId;
  }

  public void setMerId(String merId) {
    this.merId = merId;
  }

  public Body22 custId(String custId) {
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

  public Body22 mobileNo(String mobileNo) {
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

  public Body22 sessiontoken(String sessiontoken) {
    this.sessiontoken = sessiontoken;
    return this;
  }

   /**
   * Get sessiontoken
   * @return sessiontoken
  **/
  @Schema(description = "")
  public String getSessiontoken() {
    return sessiontoken;
  }

  public void setSessiontoken(String sessiontoken) {
    this.sessiontoken = sessiontoken;
  }

  public Body22 deviceId(String deviceId) {
    this.deviceId = deviceId;
    return this;
  }

   /**
   * Get deviceId
   * @return deviceId
  **/
  @Schema(description = "")
  public String getDeviceId() {
    return deviceId;
  }

  public void setDeviceId(String deviceId) {
    this.deviceId = deviceId;
  }

  public Body22 merCbredeemId(String merCbredeemId) {
    this.merCbredeemId = merCbredeemId;
    return this;
  }

   /**
   * Get merCbredeemId
   * @return merCbredeemId
  **/
  @Schema(description = "")
  public String getMerCbredeemId() {
    return merCbredeemId;
  }

  public void setMerCbredeemId(String merCbredeemId) {
    this.merCbredeemId = merCbredeemId;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Body22 body22 = (Body22) o;
    return Objects.equals(this.merId, body22.merId) &&
        Objects.equals(this.custId, body22.custId) &&
        Objects.equals(this.mobileNo, body22.mobileNo) &&
        Objects.equals(this.sessiontoken, body22.sessiontoken) &&
        Objects.equals(this.deviceId, body22.deviceId) &&
        Objects.equals(this.merCbredeemId, body22.merCbredeemId);
  }

  @Override
  public int hashCode() {
    return Objects.hash(merId, custId, mobileNo, sessiontoken, deviceId, merCbredeemId);
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Body22 {\n");

    sb.append("    merId: ").append(toIndentedString(merId)).append("\n");
    sb.append("    custId: ").append(toIndentedString(custId)).append("\n");
    sb.append("    mobileNo: ").append(toIndentedString(mobileNo)).append("\n");
    sb.append("    sessiontoken: ").append(toIndentedString(sessiontoken)).append("\n");
    sb.append("    deviceId: ").append(toIndentedString(deviceId)).append("\n");
    sb.append("    merCbredeemId: ").append(toIndentedString(merCbredeemId)).append("\n");
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
