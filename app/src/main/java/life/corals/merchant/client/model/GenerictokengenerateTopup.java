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
 * GenerictokengenerateTopup
 */

@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.JavaClientCodegen", date = "2019-09-19T07:17:58.745Z[GMT]")
public class GenerictokengenerateTopup {
  @SerializedName("campaign_id")
  private String campaignId = null;

  @SerializedName("topupvalue")
  private String topupvalue = null;

  @SerializedName("bonus")
  private String bonus = null;

  public GenerictokengenerateTopup campaignId(String campaignId) {
    this.campaignId = campaignId;
    return this;
  }

   /**
   * Get campaignId
   * @return campaignId
  **/
  @Schema(description = "")
  public String getCampaignId() {
    return campaignId;
  }

  public void setCampaignId(String campaignId) {
    this.campaignId = campaignId;
  }

  public GenerictokengenerateTopup topupvalue(String topupvalue) {
    this.topupvalue = topupvalue;
    return this;
  }

   /**
   * Get topupvalue
   * @return topupvalue
  **/
  @Schema(description = "")
  public String getTopupvalue() {
    return topupvalue;
  }

  public void setTopupvalue(String topupvalue) {
    this.topupvalue = topupvalue;
  }

  public GenerictokengenerateTopup bonus(String bonus) {
    this.bonus = bonus;
    return this;
  }

   /**
   * Get bonus
   * @return bonus
  **/
  @Schema(description = "")
  public String getBonus() {
    return bonus;
  }

  public void setBonus(String bonus) {
    this.bonus = bonus;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    GenerictokengenerateTopup generictokengenerateTopup = (GenerictokengenerateTopup) o;
    return Objects.equals(this.campaignId, generictokengenerateTopup.campaignId) &&
        Objects.equals(this.topupvalue, generictokengenerateTopup.topupvalue) &&
        Objects.equals(this.bonus, generictokengenerateTopup.bonus);
  }

  @Override
  public int hashCode() {
    return Objects.hash(campaignId, topupvalue, bonus);
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class GenerictokengenerateTopup {\n");

    sb.append("    campaignId: ").append(toIndentedString(campaignId)).append("\n");
    sb.append("    topupvalue: ").append(toIndentedString(topupvalue)).append("\n");
    sb.append("    bonus: ").append(toIndentedString(bonus)).append("\n");
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
