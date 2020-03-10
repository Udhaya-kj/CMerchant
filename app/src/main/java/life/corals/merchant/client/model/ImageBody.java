/*
 * Corals app
 * Corals openAPI file
 *
 * OpenAPI spec version: 0.0.10
 * Contact: gp2wins-corals@yahoo.com
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
 * ImageBody
 */
@javax.annotation.Generated(value = "io.swagger.codegen.languages.JavaClientCodegen", date = "2020-02-13T14:44:27.493Z")
public class ImageBody {
  @SerializedName("big_image")
  private String bigImage = null;

  @SerializedName("cust_id")
  private String custId = null;

  @SerializedName("small_image")
  private String smallImage = null;

  public ImageBody bigImage(String bigImage) {
    this.bigImage = bigImage;
    return this;
  }

   /**
   * Get bigImage
   * @return bigImage
  **/
   @Schema(required = true, description = "")
  public String getBigImage() {
    return bigImage;
  }

  public void setBigImage(String bigImage) {
    this.bigImage = bigImage;
  }

  public ImageBody custId(String custId) {
    this.custId = custId;
    return this;
  }

   /**
   * Get custId
   * @return custId
  **/
   @Schema(required = true, description = "")
  public String getCustId() {
    return custId;
  }

  public void setCustId(String custId) {
    this.custId = custId;
  }

  public ImageBody smallImage(String smallImage) {
    this.smallImage = smallImage;
    return this;
  }

   /**
   * Get smallImage
   * @return smallImage
  **/
   @Schema(required = true, description = "")
  public String getSmallImage() {
    return smallImage;
  }

  public void setSmallImage(String smallImage) {
    this.smallImage = smallImage;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ImageBody imageBody = (ImageBody) o;
    return Objects.equals(this.bigImage, imageBody.bigImage) &&
        Objects.equals(this.custId, imageBody.custId) &&
        Objects.equals(this.smallImage, imageBody.smallImage);
  }

  @Override
  public int hashCode() {
    return Objects.hash(bigImage, custId, smallImage);
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ImageBody {\n");
    
    sb.append("    bigImage: ").append(toIndentedString(bigImage)).append("\n");
    sb.append("    custId: ").append(toIndentedString(custId)).append("\n");
    sb.append("    smallImage: ").append(toIndentedString(smallImage)).append("\n");
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

