package life.corals.merchant.client.model;

import com.google.gson.annotations.SerializedName;

import java.util.Objects;

import io.swagger.v3.oas.annotations.media.Schema;

@javax.annotation.Generated(value = "io.swagger.codegen.languages.JavaClientCodegen", date = "2020-01-24T12:29:05.600Z")
public class Body29 {
  @SerializedName("deviceid")
  private String deviceid = null;

  @SerializedName("mer_id")
  private String merId = null;

  @SerializedName("sessiontoken")
  private String sessiontoken = null;

  public Body29 deviceid(String deviceid) {
    this.deviceid = deviceid;
    return this;
  }

   /**
   * Get deviceid
   * @return deviceid
  **/
   @Schema(required = true, description = "")
  public String getDeviceid() {
    return deviceid;
  }

  public void setDeviceid(String deviceid) {
    this.deviceid = deviceid;
  }

  public Body29 merId(String merId) {
    this.merId = merId;
    return this;
  }

   /**
   * Get merId
   * @return merId
  **/
   @Schema(required = true, description = "")
  public String getMerId() {
    return merId;
  }

  public void setMerId(String merId) {
    this.merId = merId;
  }

  public Body29 sessiontoken(String sessiontoken) {
    this.sessiontoken = sessiontoken;
    return this;
  }

   /**
   * Get sessiontoken
   * @return sessiontoken
  **/
   @Schema(required = true, description = "")
  public String getSessiontoken() {
    return sessiontoken;
  }

  public void setSessiontoken(String sessiontoken) {
    this.sessiontoken = sessiontoken;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Body29 body29 = (Body29) o;
    return Objects.equals(this.deviceid, body29.deviceid) &&
        Objects.equals(this.merId, body29.merId) &&
        Objects.equals(this.sessiontoken, body29.sessiontoken);
  }

  @Override
  public int hashCode() {
    return Objects.hash(deviceid, merId, sessiontoken);
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Body29 {\n");
    
    sb.append("    deviceid: ").append(toIndentedString(deviceid)).append("\n");
    sb.append("    merId: ").append(toIndentedString(merId)).append("\n");
    sb.append("    sessiontoken: ").append(toIndentedString(sessiontoken)).append("\n");
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

