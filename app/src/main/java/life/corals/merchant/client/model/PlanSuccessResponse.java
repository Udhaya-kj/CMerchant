package life.corals.merchant.client.model;

import com.google.gson.annotations.SerializedName;

import java.util.Objects;

import io.swagger.v3.oas.annotations.media.Schema;


/**
 * PlanSuccessResponse
 */
@javax.annotation.Generated(value = "io.swagger.codegen.languages.JavaClientCodegen", date = "2020-02-28T11:06:04.949Z")
public class PlanSuccessResponse {
  @SerializedName("status_code")
  private String statusCode = null;

  @SerializedName("status_msg")
  private String statusMsg = null;

  @SerializedName("mer_id")
  private String merId = null;

  public PlanSuccessResponse statusCode(String statusCode) {
    this.statusCode = statusCode;
    return this;
  }

   /**
   * Get statusCode
   * @return statusCode
  **/
  @Schema(description = "")
  public String getStatusCode() {
    return statusCode;
  }

  public void setStatusCode(String statusCode) {
    this.statusCode = statusCode;
  }

  public PlanSuccessResponse statusMsg(String statusMsg) {
    this.statusMsg = statusMsg;
    return this;
  }

   /**
   * Get statusMsg
   * @return statusMsg
  **/
   @Schema(description = "")
  public String getStatusMsg() {
    return statusMsg;
  }

  public void setStatusMsg(String statusMsg) {
    this.statusMsg = statusMsg;
  }

  public PlanSuccessResponse merId(String merId) {
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


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    PlanSuccessResponse planSuccessResponse = (PlanSuccessResponse) o;
    return Objects.equals(this.statusCode, planSuccessResponse.statusCode) &&
        Objects.equals(this.statusMsg, planSuccessResponse.statusMsg) &&
        Objects.equals(this.merId, planSuccessResponse.merId);
  }

  @Override
  public int hashCode() {
    return Objects.hash(statusCode, statusMsg, merId);
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class PlanSuccessResponse {\n");
    
    sb.append("    statusCode: ").append(toIndentedString(statusCode)).append("\n");
    sb.append("    statusMsg: ").append(toIndentedString(statusMsg)).append("\n");
    sb.append("    merId: ").append(toIndentedString(merId)).append("\n");
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

