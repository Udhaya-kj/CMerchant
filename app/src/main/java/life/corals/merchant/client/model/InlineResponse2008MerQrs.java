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
 * InlineResponse2008MerQrs
 */

@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.JavaClientCodegen", date = "2019-09-19T07:17:58.745Z[GMT]")
public class InlineResponse2008MerQrs {
  @SerializedName("mer_id")
  private String merId = null;

  @SerializedName("outlet_id")
  private String outletId = null;

  @SerializedName("mer_qr_id")
  private String merQrId = null;

  public InlineResponse2008MerQrs merId(String merId) {
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

  public InlineResponse2008MerQrs outletId(String outletId) {
    this.outletId = outletId;
    return this;
  }

   /**
   * Get outletId
   * @return outletId
  **/
  @Schema(description = "")
  public String getOutletId() {
    return outletId;
  }

  public void setOutletId(String outletId) {
    this.outletId = outletId;
  }

  public InlineResponse2008MerQrs merQrId(String merQrId) {
    this.merQrId = merQrId;
    return this;
  }

   /**
   * Get merQrId
   * @return merQrId
  **/
  @Schema(description = "")
  public String getMerQrId() {
    return merQrId;
  }

  public void setMerQrId(String merQrId) {
    this.merQrId = merQrId;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    InlineResponse2008MerQrs inlineResponse2008MerQrs = (InlineResponse2008MerQrs) o;
    return Objects.equals(this.merId, inlineResponse2008MerQrs.merId) &&
        Objects.equals(this.outletId, inlineResponse2008MerQrs.outletId) &&
        Objects.equals(this.merQrId, inlineResponse2008MerQrs.merQrId);
  }

  @Override
  public int hashCode() {
    return Objects.hash(merId, outletId, merQrId);
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class InlineResponse2008MerQrs {\n");

    sb.append("    merId: ").append(toIndentedString(merId)).append("\n");
    sb.append("    outletId: ").append(toIndentedString(outletId)).append("\n");
    sb.append("    merQrId: ").append(toIndentedString(merQrId)).append("\n");
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