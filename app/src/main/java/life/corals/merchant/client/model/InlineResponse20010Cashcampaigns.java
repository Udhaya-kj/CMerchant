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
 * InlineResponse20010Cashcampaigns
 */

@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.JavaClientCodegen", date = "2019-09-19T07:17:58.745Z[GMT]")
public class InlineResponse20010Cashcampaigns {
  @SerializedName("campaignid")
  private String campaignid = null;

  @SerializedName("topupamt")
  private String topupamt = null;

  @SerializedName("topupbonus")
  private String topupbonus = null;

  @SerializedName("is_active")
  private Boolean isActive = null;

  public InlineResponse20010Cashcampaigns campaignid(String campaignid) {
    this.campaignid = campaignid;
    return this;
  }

   /**
   * Get campaignid
   * @return campaignid
  **/
  @Schema(description = "")
  public String getCampaignid() {
    return campaignid;
  }

  public void setCampaignid(String campaignid) {
    this.campaignid = campaignid;
  }

  public InlineResponse20010Cashcampaigns topupamt(String topupamt) {
    this.topupamt = topupamt;
    return this;
  }

   /**
   * Get topupamt
   * @return topupamt
  **/
  @Schema(description = "")
  public String getTopupamt() {
    return topupamt;
  }

  public void setTopupamt(String topupamt) {
    this.topupamt = topupamt;
  }

  public InlineResponse20010Cashcampaigns topupbonus(String topupbonus) {
    this.topupbonus = topupbonus;
    return this;
  }

   /**
   * Get topupbonus
   * @return topupbonus
  **/
  @Schema(description = "")
  public String getTopupbonus() {
    return topupbonus;
  }

  public void setTopupbonus(String topupbonus) {
    this.topupbonus = topupbonus;
  }

  public InlineResponse20010Cashcampaigns isActive(Boolean isActive) {
    this.isActive = isActive;
    return this;
  }

   /**
   * Get isActive
   * @return isActive
  **/
  @Schema(description = "")
  public Boolean isIsActive() {
    return isActive;
  }

  public void setIsActive(Boolean isActive) {
    this.isActive = isActive;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    InlineResponse20010Cashcampaigns inlineResponse20010Cashcampaigns = (InlineResponse20010Cashcampaigns) o;
    return Objects.equals(this.campaignid, inlineResponse20010Cashcampaigns.campaignid) &&
        Objects.equals(this.topupamt, inlineResponse20010Cashcampaigns.topupamt) &&
        Objects.equals(this.topupbonus, inlineResponse20010Cashcampaigns.topupbonus) &&
        Objects.equals(this.isActive, inlineResponse20010Cashcampaigns.isActive);
  }

  @Override
  public int hashCode() {
    return Objects.hash(campaignid, topupamt, topupbonus, isActive);
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class InlineResponse20010Cashcampaigns {\n");

    sb.append("    campaignid: ").append(toIndentedString(campaignid)).append("\n");
    sb.append("    topupamt: ").append(toIndentedString(topupamt)).append("\n");
    sb.append("    topupbonus: ").append(toIndentedString(topupbonus)).append("\n");
    sb.append("    isActive: ").append(toIndentedString(isActive)).append("\n");
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