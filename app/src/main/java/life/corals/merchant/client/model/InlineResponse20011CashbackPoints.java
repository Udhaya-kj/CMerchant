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
 * InlineResponse20011CashbackPoints
 */

@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.JavaClientCodegen", date = "2019-09-19T07:17:58.745Z[GMT]")
public class InlineResponse20011CashbackPoints {
  @SerializedName("ttl_outstanding_points")
  private String ttlOutstandingPoints = null;

  @SerializedName("redemption_rate")
  private String redemptionRate = null;

  @SerializedName("avg_redeem_cost_percent")
  private String avgRedeemCostPercent = null;

  public InlineResponse20011CashbackPoints ttlOutstandingPoints(String ttlOutstandingPoints) {
    this.ttlOutstandingPoints = ttlOutstandingPoints;
    return this;
  }

   /**
   * Get ttlOutstandingPoints
   * @return ttlOutstandingPoints
  **/
  @Schema(description = "")
  public String getTtlOutstandingPoints() {
    return ttlOutstandingPoints;
  }

  public void setTtlOutstandingPoints(String ttlOutstandingPoints) {
    this.ttlOutstandingPoints = ttlOutstandingPoints;
  }

  public InlineResponse20011CashbackPoints redemptionRate(String redemptionRate) {
    this.redemptionRate = redemptionRate;
    return this;
  }

   /**
   * Get redemptionRate
   * @return redemptionRate
  **/
  @Schema(description = "")
  public String getRedemptionRate() {
    return redemptionRate;
  }

  public void setRedemptionRate(String redemptionRate) {
    this.redemptionRate = redemptionRate;
  }

  public InlineResponse20011CashbackPoints avgRedeemCostPercent(String avgRedeemCostPercent) {
    this.avgRedeemCostPercent = avgRedeemCostPercent;
    return this;
  }

   /**
   * Get avgRedeemCostPercent
   * @return avgRedeemCostPercent
  **/
  @Schema(description = "")
  public String getAvgRedeemCostPercent() {
    return avgRedeemCostPercent;
  }

  public void setAvgRedeemCostPercent(String avgRedeemCostPercent) {
    this.avgRedeemCostPercent = avgRedeemCostPercent;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    InlineResponse20011CashbackPoints inlineResponse20011CashbackPoints = (InlineResponse20011CashbackPoints) o;
    return Objects.equals(this.ttlOutstandingPoints, inlineResponse20011CashbackPoints.ttlOutstandingPoints) &&
        Objects.equals(this.redemptionRate, inlineResponse20011CashbackPoints.redemptionRate) &&
        Objects.equals(this.avgRedeemCostPercent, inlineResponse20011CashbackPoints.avgRedeemCostPercent);
  }

  @Override
  public int hashCode() {
    return Objects.hash(ttlOutstandingPoints, redemptionRate, avgRedeemCostPercent);
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class InlineResponse20011CashbackPoints {\n");

    sb.append("    ttlOutstandingPoints: ").append(toIndentedString(ttlOutstandingPoints)).append("\n");
    sb.append("    redemptionRate: ").append(toIndentedString(redemptionRate)).append("\n");
    sb.append("    avgRedeemCostPercent: ").append(toIndentedString(avgRedeemCostPercent)).append("\n");
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
