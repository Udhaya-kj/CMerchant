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

import java.util.Objects;
import java.util.Arrays;
import com.google.gson.TypeAdapter;
import com.google.gson.annotations.JsonAdapter;
import com.google.gson.annotations.SerializedName;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import io.swagger.v3.oas.annotations.media.Schema;
import java.io.IOException;
/**
 * MerchantoutletaddOutletparams
 */

@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.JavaClientCodegen", date = "2019-11-12T10:27:28.001Z[GMT]")
public class MerchantoutletaddOutletparams {
  @SerializedName("outletname")
  private String outletname = null;

  @SerializedName("addline1")
  private String addline1 = null;

  @SerializedName("addline2")
  private String addline2 = null;

  @SerializedName("postcode")
  private String postcode = null;

  @SerializedName("phonenumber")
  private String phonenumber = null;

  @SerializedName("gpslat")
  private String gpslat = null;

  @SerializedName("gpslon")
  private String gpslon = null;

  public MerchantoutletaddOutletparams outletname(String outletname) {
    this.outletname = outletname;
    return this;
  }

   /**
   * Get outletname
   * @return outletname
  **/
  @Schema(description = "")
  public String getOutletname() {
    return outletname;
  }

  public void setOutletname(String outletname) {
    this.outletname = outletname;
  }

  public MerchantoutletaddOutletparams addline1(String addline1) {
    this.addline1 = addline1;
    return this;
  }

   /**
   * Get addline1
   * @return addline1
  **/
  @Schema(description = "")
  public String getAddline1() {
    return addline1;
  }

  public void setAddline1(String addline1) {
    this.addline1 = addline1;
  }

  public MerchantoutletaddOutletparams addline2(String addline2) {
    this.addline2 = addline2;
    return this;
  }

   /**
   * Get addline2
   * @return addline2
  **/
  @Schema(description = "")
  public String getAddline2() {
    return addline2;
  }

  public void setAddline2(String addline2) {
    this.addline2 = addline2;
  }

  public MerchantoutletaddOutletparams postcode(String postcode) {
    this.postcode = postcode;
    return this;
  }

   /**
   * Get postcode
   * @return postcode
  **/
  @Schema(description = "")
  public String getPostcode() {
    return postcode;
  }

  public void setPostcode(String postcode) {
    this.postcode = postcode;
  }

  public MerchantoutletaddOutletparams phonenumber(String phonenumber) {
    this.phonenumber = phonenumber;
    return this;
  }

   /**
   * Get phonenumber
   * @return phonenumber
  **/
  @Schema(description = "")
  public String getPhonenumber() {
    return phonenumber;
  }

  public void setPhonenumber(String phonenumber) {
    this.phonenumber = phonenumber;
  }

  public MerchantoutletaddOutletparams gpslat(String gpslat) {
    this.gpslat = gpslat;
    return this;
  }

   /**
   * Get gpslat
   * @return gpslat
  **/
  @Schema(description = "")
  public String getGpslat() {
    return gpslat;
  }

  public void setGpslat(String gpslat) {
    this.gpslat = gpslat;
  }

  public MerchantoutletaddOutletparams gpslon(String gpslon) {
    this.gpslon = gpslon;
    return this;
  }

   /**
   * Get gpslon
   * @return gpslon
  **/
  @Schema(description = "")
  public String getGpslon() {
    return gpslon;
  }

  public void setGpslon(String gpslon) {
    this.gpslon = gpslon;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    MerchantoutletaddOutletparams merchantoutletaddOutletparams = (MerchantoutletaddOutletparams) o;
    return Objects.equals(this.outletname, merchantoutletaddOutletparams.outletname) &&
        Objects.equals(this.addline1, merchantoutletaddOutletparams.addline1) &&
        Objects.equals(this.addline2, merchantoutletaddOutletparams.addline2) &&
        Objects.equals(this.postcode, merchantoutletaddOutletparams.postcode) &&
        Objects.equals(this.phonenumber, merchantoutletaddOutletparams.phonenumber) &&
        Objects.equals(this.gpslat, merchantoutletaddOutletparams.gpslat) &&
        Objects.equals(this.gpslon, merchantoutletaddOutletparams.gpslon);
  }

  @Override
  public int hashCode() {
    return Objects.hash(outletname, addline1, addline2, postcode, phonenumber, gpslat, gpslon);
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class MerchantoutletaddOutletparams {\n");
    
    sb.append("    outletname: ").append(toIndentedString(outletname)).append("\n");
    sb.append("    addline1: ").append(toIndentedString(addline1)).append("\n");
    sb.append("    addline2: ").append(toIndentedString(addline2)).append("\n");
    sb.append("    postcode: ").append(toIndentedString(postcode)).append("\n");
    sb.append("    phonenumber: ").append(toIndentedString(phonenumber)).append("\n");
    sb.append("    gpslat: ").append(toIndentedString(gpslat)).append("\n");
    sb.append("    gpslon: ").append(toIndentedString(gpslon)).append("\n");
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