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
 * Body
 */

@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.JavaClientCodegen", date = "2019-09-19T07:17:58.745Z[GMT]")
public class Body {
  @SerializedName("deviceid")
  private String deviceid = null;

  @SerializedName("devicepin")
  private String devicepin = null;

  @SerializedName("isoutlets_reqd")
  private Boolean isoutletsReqd = null;

  @SerializedName("iscampaigns_reqd")
  private Boolean iscampaignsReqd = null;

  @SerializedName("notify_instance_id")
  private String notifyInstanceId = null;

  @SerializedName("max_record_count")
  private String maxRecordCount = "50";

  public Body deviceid(String deviceid) {
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

  public Body devicepin(String devicepin) {
    this.devicepin = devicepin;
    return this;
  }

   /**
   * Get devicepin
   * @return devicepin
  **/
  @Schema(description = "")
  public String getDevicepin() {
    return devicepin;
  }

  public void setDevicepin(String devicepin) {
    this.devicepin = devicepin;
  }

  public Body isoutletsReqd(Boolean isoutletsReqd) {
    this.isoutletsReqd = isoutletsReqd;
    return this;
  }

   /**
   * if the cache is expired, request for outlet data
   * @return isoutletsReqd
  **/
  @Schema(required = true, description = "if the cache is expired, request for outlet data")
  public Boolean isIsoutletsReqd() {
    return isoutletsReqd;
  }

  public void setIsoutletsReqd(Boolean isoutletsReqd) {
    this.isoutletsReqd = isoutletsReqd;
  }

  public Body iscampaignsReqd(Boolean iscampaignsReqd) {
    this.iscampaignsReqd = iscampaignsReqd;
    return this;
  }

   /**
   * if the cache is expired, request for cash campaing data
   * @return iscampaignsReqd
  **/
  @Schema(required = true, description = "if the cache is expired, request for cash campaing data")
  public Boolean isIscampaignsReqd() {
    return iscampaignsReqd;
  }

  public void setIscampaignsReqd(Boolean iscampaignsReqd) {
    this.iscampaignsReqd = iscampaignsReqd;
  }

  public Body notifyInstanceId(String notifyInstanceId) {
    this.notifyInstanceId = notifyInstanceId;
    return this;
  }

   /**
   * Get notifyInstanceId
   * @return notifyInstanceId
  **/
  @Schema(description = "")
  public String getNotifyInstanceId() {
    return notifyInstanceId;
  }

  public void setNotifyInstanceId(String notifyInstanceId) {
    this.notifyInstanceId = notifyInstanceId;
  }

  public Body maxRecordCount(String maxRecordCount) {
    this.maxRecordCount = maxRecordCount;
    return this;
  }

   /**
   * Get maxRecordCount
   * @return maxRecordCount
  **/
  @Schema(required = true, description = "")
  public String getMaxRecordCount() {
    return maxRecordCount;
  }

  public void setMaxRecordCount(String maxRecordCount) {
    this.maxRecordCount = maxRecordCount;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Body body = (Body) o;
    return Objects.equals(this.deviceid, body.deviceid) &&
        Objects.equals(this.devicepin, body.devicepin) &&
        Objects.equals(this.isoutletsReqd, body.isoutletsReqd) &&
        Objects.equals(this.iscampaignsReqd, body.iscampaignsReqd) &&
        Objects.equals(this.notifyInstanceId, body.notifyInstanceId) &&
        Objects.equals(this.maxRecordCount, body.maxRecordCount);
  }

  @Override
  public int hashCode() {
    return Objects.hash(deviceid, devicepin, isoutletsReqd, iscampaignsReqd, notifyInstanceId, maxRecordCount);
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Body {\n");

    sb.append("    deviceid: ").append(toIndentedString(deviceid)).append("\n");
    sb.append("    devicepin: ").append(toIndentedString(devicepin)).append("\n");
    sb.append("    isoutletsReqd: ").append(toIndentedString(isoutletsReqd)).append("\n");
    sb.append("    iscampaignsReqd: ").append(toIndentedString(iscampaignsReqd)).append("\n");
    sb.append("    notifyInstanceId: ").append(toIndentedString(notifyInstanceId)).append("\n");
    sb.append("    maxRecordCount: ").append(toIndentedString(maxRecordCount)).append("\n");
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