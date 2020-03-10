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

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * InlineResponse20012Merredeemlist
 */

@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.JavaClientCodegen", date = "2019-09-19T07:17:58.745Z[GMT]")
public class InlineResponse20012Merredeemlist {
  @SerializedName("custpointsbal")
  private String custpointsbal = null;

  @SerializedName("cbpoints_expdt")
  private String cbpointsExpdt = null;

  @SerializedName("redemption_list")
  private List<InlineResponse20012MerredeemlistRedemptionList> redemptionList = null;

  public InlineResponse20012Merredeemlist custpointsbal(String custpointsbal) {
    this.custpointsbal = custpointsbal;
    return this;
  }

   /**
   * Get custpointsbal
   * @return custpointsbal
  **/
  @Schema(description = "")
  public String getCustpointsbal() {
    return custpointsbal;
  }

  public void setCustpointsbal(String custpointsbal) {
    this.custpointsbal = custpointsbal;
  }

  public InlineResponse20012Merredeemlist cbpointsExpdt(String cbpointsExpdt) {
    this.cbpointsExpdt = cbpointsExpdt;
    return this;
  }

   /**
   * Get cbpointsExpdt
   * @return cbpointsExpdt
  **/
  @Schema(description = "")
  public String getCbpointsExpdt() {
    return cbpointsExpdt;
  }

  public void setCbpointsExpdt(String cbpointsExpdt) {
    this.cbpointsExpdt = cbpointsExpdt;
  }

  public InlineResponse20012Merredeemlist redemptionList(List<InlineResponse20012MerredeemlistRedemptionList> redemptionList) {
    this.redemptionList = redemptionList;
    return this;
  }

  public InlineResponse20012Merredeemlist addRedemptionListItem(InlineResponse20012MerredeemlistRedemptionList redemptionListItem) {
    if (this.redemptionList == null) {
      this.redemptionList = new ArrayList<InlineResponse20012MerredeemlistRedemptionList>();
    }
    this.redemptionList.add(redemptionListItem);
    return this;
  }

   /**
   * Get redemptionList
   * @return redemptionList
  **/
  @Schema(description = "")
  public List<InlineResponse20012MerredeemlistRedemptionList> getRedemptionList() {
    return redemptionList;
  }

  public void setRedemptionList(List<InlineResponse20012MerredeemlistRedemptionList> redemptionList) {
    this.redemptionList = redemptionList;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    InlineResponse20012Merredeemlist inlineResponse20012Merredeemlist = (InlineResponse20012Merredeemlist) o;
    return Objects.equals(this.custpointsbal, inlineResponse20012Merredeemlist.custpointsbal) &&
        Objects.equals(this.cbpointsExpdt, inlineResponse20012Merredeemlist.cbpointsExpdt) &&
        Objects.equals(this.redemptionList, inlineResponse20012Merredeemlist.redemptionList);
  }

  @Override
  public int hashCode() {
    return Objects.hash(custpointsbal, cbpointsExpdt, redemptionList);
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class InlineResponse20012Merredeemlist {\n");

    sb.append("    custpointsbal: ").append(toIndentedString(custpointsbal)).append("\n");
    sb.append("    cbpointsExpdt: ").append(toIndentedString(cbpointsExpdt)).append("\n");
    sb.append("    redemptionList: ").append(toIndentedString(redemptionList)).append("\n");
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