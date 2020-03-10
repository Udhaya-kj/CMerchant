package life.corals.merchant.client.model;
import java.util.Objects;
import com.google.gson.annotations.SerializedName;
import io.swagger.v3.oas.annotations.media.Schema;

/**
 * InlineResponse2001
 */
@javax.annotation.Generated(value = "io.swagger.codegen.languages.JavaClientCodegen", date = "2020-02-22T04:55:24.588Z")
public class InlineResponse2001 {
  @SerializedName("isadmin")
  private Boolean isadmin = null;

  @SerializedName("mer_id")
  private String merId = null;

  @SerializedName("success_code")
  private String successCode = null;

  @SerializedName("success_message")
  private String successMessage = null;

  @SerializedName("device_id")
  private String deviceId = null;

  public InlineResponse2001 isadmin(Boolean isadmin) {
    this.isadmin = isadmin;
    return this;
  }

  /**
   * Get isadmin
   * @return isadmin
   **/
  @Schema(description = "")
  public Boolean isIsadmin() {
    return isadmin;
  }

  public void setIsadmin(Boolean isadmin) {
    this.isadmin = isadmin;
  }

  public InlineResponse2001 merId(String merId) {
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

  public InlineResponse2001 successCode(String successCode) {
    this.successCode = successCode;
    return this;
  }

  /**
   * Get successCode
   * @return successCode
   **/
  @Schema(description = "")
  public String getSuccessCode() {
    return successCode;
  }

  public void setSuccessCode(String successCode) {
    this.successCode = successCode;
  }

  public InlineResponse2001 successMessage(String successMessage) {
    this.successMessage = successMessage;
    return this;
  }

  /**
   * Get successMessage
   * @return successMessage
   **/
  @Schema(description = "")
  public String getSuccessMessage() {
    return successMessage;
  }

  public void setSuccessMessage(String successMessage) {
    this.successMessage = successMessage;
  }

  public InlineResponse2001 deviceId(String deviceId) {
    this.deviceId = deviceId;
    return this;
  }

  /**
   * Get deviceId
   * @return deviceId
   **/
  @Schema(description = "")
  public String getDeviceId() {
    return deviceId;
  }

  public void setDeviceId(String deviceId) {
    this.deviceId = deviceId;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    InlineResponse2001 inlineResponse2001 = (InlineResponse2001) o;
    return Objects.equals(this.isadmin, inlineResponse2001.isadmin) &&
            Objects.equals(this.merId, inlineResponse2001.merId) &&
            Objects.equals(this.successCode, inlineResponse2001.successCode) &&
            Objects.equals(this.successMessage, inlineResponse2001.successMessage) &&
            Objects.equals(this.deviceId, inlineResponse2001.deviceId);
  }

  @Override
  public int hashCode() {
    return Objects.hash(isadmin, merId, successCode, successMessage, deviceId);
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class InlineResponse2001 {\n");

    sb.append("    isadmin: ").append(toIndentedString(isadmin)).append("\n");
    sb.append("    merId: ").append(toIndentedString(merId)).append("\n");
    sb.append("    successCode: ").append(toIndentedString(successCode)).append("\n");
    sb.append("    successMessage: ").append(toIndentedString(successMessage)).append("\n");
    sb.append("    deviceId: ").append(toIndentedString(deviceId)).append("\n");
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

