package life.corals.merchant.client.model;

import com.google.gson.annotations.SerializedName;

import java.util.Objects;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * InlineResponseSummary
 */
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2020-01-24T12:19:51.186Z")

public class InlineResponseSummary   {
  @SerializedName("customers_old")
  private String customersOld = null;

  @SerializedName("customers_new")
  private String customersNew = null;

  @SerializedName("Ipoints_old")
  private String ipointsOld = null;

  @SerializedName("Ipoints_new")
  private String ipointsNew = null;

  @SerializedName("Rpoints_old")
  private String rpointsOld = null;

  @SerializedName("Rpoints_new")
  private String rpointsNew = null;

  @SerializedName("No.of_txn_old")
  private String noOfTxnOld = null;

  @SerializedName("No.of_txn_new")
  private String noOfTxnNew = null;

  @SerializedName("tot.amnt_txn_old")
  private String totAmntTxnOld = null;

  @SerializedName("tot.amnt_txn_new")
  private String totAmntTxnNew = null;

  @SerializedName("No.of_receives_old")
  private String noOfReceivesOld = null;

  @SerializedName("No.of_receives_new")
  private String noOfReceivesNew = null;

  @SerializedName("tot.amnt_receives_old")
  private String totAmntReceivesOld = null;

  @SerializedName("tot.amnt_Receives_new")
  private String totAmntReceivesNew = null;

  @SerializedName("customers_visit_old")
  private String customersVisitOld = null;

  @SerializedName("customers_visit_new")
  private String customersVisitNew = null;

  public InlineResponseSummary customersOld(String customersOld) {
    this.customersOld = customersOld;
    return this;
  }

  /**
   * Get customersOld
   * @return customersOld
  **/
  @Schema(description = "") public String getCustomersOld() {
    return customersOld;
  }

  public void setCustomersOld(String customersOld) {
    this.customersOld = customersOld;
  }

  public InlineResponseSummary customersNew(String customersNew) {
    this.customersNew = customersNew;
    return this;
  }

  /**
   * Get customersNew
   * @return customersNew
  **/
  @Schema(description = "")
  public String getCustomersNew() {
    return customersNew;
  }

  public void setCustomersNew(String customersNew) {
    this.customersNew = customersNew;
  }

  public InlineResponseSummary ipointsOld(String ipointsOld) {
    this.ipointsOld = ipointsOld;
    return this;
  }

  /**
   * Get ipointsOld
   * @return ipointsOld
  **/
  @Schema(description = "")
  public String getIpointsOld() {
    return ipointsOld;
  }

  public void setIpointsOld(String ipointsOld) {
    this.ipointsOld = ipointsOld;
  }

  public InlineResponseSummary ipointsNew(String ipointsNew) {
    this.ipointsNew = ipointsNew;
    return this;
  }

  /**
   * Get ipointsNew
   * @return ipointsNew
  **/
  @Schema(description = "")
  public String getIpointsNew() {
    return ipointsNew;
  }

  public void setIpointsNew(String ipointsNew) {
    this.ipointsNew = ipointsNew;
  }

  public InlineResponseSummary rpointsOld(String rpointsOld) {
    this.rpointsOld = rpointsOld;
    return this;
  }

  /**
   * Get rpointsOld
   * @return rpointsOld
  **/
  @Schema(description = "")
  public String getRpointsOld() {
    return rpointsOld;
  }

  public void setRpointsOld(String rpointsOld) {
    this.rpointsOld = rpointsOld;
  }

  public InlineResponseSummary rpointsNew(String rpointsNew) {
    this.rpointsNew = rpointsNew;
    return this;
  }

  /**
   * Get rpointsNew
   * @return rpointsNew
  **/
  @Schema(description = "")


  public String getRpointsNew() {
    return rpointsNew;
  }

  public void setRpointsNew(String rpointsNew) {
    this.rpointsNew = rpointsNew;
  }

  public InlineResponseSummary noOfTxnOld(String noOfTxnOld) {
    this.noOfTxnOld = noOfTxnOld;
    return this;
  }

  /**
   * Get noOfTxnOld
   * @return noOfTxnOld
  **/
  @Schema(description = "")

  public String getNoOfTxnOld() {
    return noOfTxnOld;
  }

  public void setNoOfTxnOld(String noOfTxnOld) {
    this.noOfTxnOld = noOfTxnOld;
  }

  public InlineResponseSummary noOfTxnNew(String noOfTxnNew) {
    this.noOfTxnNew = noOfTxnNew;
    return this;
  }

  /**
   * Get noOfTxnNew
   * @return noOfTxnNew
  **/
  @Schema(description = "")
  public String getNoOfTxnNew() {
    return noOfTxnNew;
  }

  public void setNoOfTxnNew(String noOfTxnNew) {
    this.noOfTxnNew = noOfTxnNew;
  }

  public InlineResponseSummary totAmntTxnOld(String totAmntTxnOld) {
    this.totAmntTxnOld = totAmntTxnOld;
    return this;
  }

  /**
   * Get totAmntTxnOld
   * @return totAmntTxnOld
  **/
  @Schema(description = "")
  public String getTotAmntTxnOld() {
    return totAmntTxnOld;
  }

  public void setTotAmntTxnOld(String totAmntTxnOld) {
    this.totAmntTxnOld = totAmntTxnOld;
  }

  public InlineResponseSummary totAmntTxnNew(String totAmntTxnNew) {
    this.totAmntTxnNew = totAmntTxnNew;
    return this;
  }

  /**
   * Get totAmntTxnNew
   * @return totAmntTxnNew
  **/
  @Schema(description = "")
  public String getTotAmntTxnNew() {
    return totAmntTxnNew;
  }

  public void setTotAmntTxnNew(String totAmntTxnNew) {
    this.totAmntTxnNew = totAmntTxnNew;
  }

  public InlineResponseSummary noOfReceivesOld(String noOfReceivesOld) {
    this.noOfReceivesOld = noOfReceivesOld;
    return this;
  }

  /**
   * Get noOfReceivesOld
   * @return noOfReceivesOld
  **/
  @Schema(description = "")
  public String getNoOfReceivesOld() {
    return noOfReceivesOld;
  }

  public void setNoOfReceivesOld(String noOfReceivesOld) {
    this.noOfReceivesOld = noOfReceivesOld;
  }

  public InlineResponseSummary noOfReceivesNew(String noOfReceivesNew) {
    this.noOfReceivesNew = noOfReceivesNew;
    return this;
  }

  /**
   * Get noOfReceivesNew
   * @return noOfReceivesNew
  **/
  @Schema(description = "")
  public String getNoOfReceivesNew() {
    return noOfReceivesNew;
  }

  public void setNoOfReceivesNew(String noOfReceivesNew) {
    this.noOfReceivesNew = noOfReceivesNew;
  }

  public InlineResponseSummary totAmntReceivesOld(String totAmntReceivesOld) {
    this.totAmntReceivesOld = totAmntReceivesOld;
    return this;
  }

  /**
   * Get totAmntReceivesOld
   * @return totAmntReceivesOld
  **/
  @Schema(description = "")
  public String getTotAmntReceivesOld() {
    return totAmntReceivesOld;
  }

  public void setTotAmntReceivesOld(String totAmntReceivesOld) {
    this.totAmntReceivesOld = totAmntReceivesOld;
  }

  public InlineResponseSummary totAmntReceivesNew(String totAmntReceivesNew) {
    this.totAmntReceivesNew = totAmntReceivesNew;
    return this;
  }

  /**
   * Get totAmntReceivesNew
   * @return totAmntReceivesNew
  **/
  @Schema(description = "")
  public String getTotAmntReceivesNew() {
    return totAmntReceivesNew;
  }

  public void setTotAmntReceivesNew(String totAmntReceivesNew) {
    this.totAmntReceivesNew = totAmntReceivesNew;
  }

  public InlineResponseSummary customersVisitOld(String customersVisitOld) {
    this.customersVisitOld = customersVisitOld;
    return this;
  }

  /**
   * Get customersVisitOld
   * @return customersVisitOld
  **/
  @Schema(description = "")
  public String getCustomersVisitOld() {
    return customersVisitOld;
  }

  public void setCustomersVisitOld(String customersVisitOld) {
    this.customersVisitOld = customersVisitOld;
  }

  public InlineResponseSummary customersVisitNew(String customersVisitNew) {
    this.customersVisitNew = customersVisitNew;
    return this;
  }

  /**
   * Get customersVisitNew
   * @return customersVisitNew
  **/
  @Schema(description = "")
  public String getCustomersVisitNew() {
    return customersVisitNew;
  }

  public void setCustomersVisitNew(String customersVisitNew) {
    this.customersVisitNew = customersVisitNew;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    InlineResponseSummary inlineResponseSummary = (InlineResponseSummary) o;
    return Objects.equals(this.customersOld, inlineResponseSummary.customersOld) &&
        Objects.equals(this.customersNew, inlineResponseSummary.customersNew) &&
        Objects.equals(this.ipointsOld, inlineResponseSummary.ipointsOld) &&
        Objects.equals(this.ipointsNew, inlineResponseSummary.ipointsNew) &&
        Objects.equals(this.rpointsOld, inlineResponseSummary.rpointsOld) &&
        Objects.equals(this.rpointsNew, inlineResponseSummary.rpointsNew) &&
        Objects.equals(this.noOfTxnOld, inlineResponseSummary.noOfTxnOld) &&
        Objects.equals(this.noOfTxnNew, inlineResponseSummary.noOfTxnNew) &&
        Objects.equals(this.totAmntTxnOld, inlineResponseSummary.totAmntTxnOld) &&
        Objects.equals(this.totAmntTxnNew, inlineResponseSummary.totAmntTxnNew) &&
        Objects.equals(this.noOfReceivesOld, inlineResponseSummary.noOfReceivesOld) &&
        Objects.equals(this.noOfReceivesNew, inlineResponseSummary.noOfReceivesNew) &&
        Objects.equals(this.totAmntReceivesOld, inlineResponseSummary.totAmntReceivesOld) &&
        Objects.equals(this.totAmntReceivesNew, inlineResponseSummary.totAmntReceivesNew) &&
        Objects.equals(this.customersVisitOld, inlineResponseSummary.customersVisitOld) &&
        Objects.equals(this.customersVisitNew, inlineResponseSummary.customersVisitNew);
  }

  @Override
  public int hashCode() {
    return Objects.hash(customersOld, customersNew, ipointsOld, ipointsNew, rpointsOld, rpointsNew, noOfTxnOld, noOfTxnNew, totAmntTxnOld, totAmntTxnNew, noOfReceivesOld, noOfReceivesNew, totAmntReceivesOld, totAmntReceivesNew, customersVisitOld, customersVisitNew);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class InlineResponseSummary {\n");
    
    sb.append("    customersOld: ").append(toIndentedString(customersOld)).append("\n");
    sb.append("    customersNew: ").append(toIndentedString(customersNew)).append("\n");
    sb.append("    ipointsOld: ").append(toIndentedString(ipointsOld)).append("\n");
    sb.append("    ipointsNew: ").append(toIndentedString(ipointsNew)).append("\n");
    sb.append("    rpointsOld: ").append(toIndentedString(rpointsOld)).append("\n");
    sb.append("    rpointsNew: ").append(toIndentedString(rpointsNew)).append("\n");
    sb.append("    noOfTxnOld: ").append(toIndentedString(noOfTxnOld)).append("\n");
    sb.append("    noOfTxnNew: ").append(toIndentedString(noOfTxnNew)).append("\n");
    sb.append("    totAmntTxnOld: ").append(toIndentedString(totAmntTxnOld)).append("\n");
    sb.append("    totAmntTxnNew: ").append(toIndentedString(totAmntTxnNew)).append("\n");
    sb.append("    noOfReceivesOld: ").append(toIndentedString(noOfReceivesOld)).append("\n");
    sb.append("    noOfReceivesNew: ").append(toIndentedString(noOfReceivesNew)).append("\n");
    sb.append("    totAmntReceivesOld: ").append(toIndentedString(totAmntReceivesOld)).append("\n");
    sb.append("    totAmntReceivesNew: ").append(toIndentedString(totAmntReceivesNew)).append("\n");
    sb.append("    customersVisitOld: ").append(toIndentedString(customersVisitOld)).append("\n");
    sb.append("    customersVisitNew: ").append(toIndentedString(customersVisitNew)).append("\n");
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

