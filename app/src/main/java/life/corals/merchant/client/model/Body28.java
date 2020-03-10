package life.corals.merchant.client.model;

import java.util.Objects;
import com.google.gson.annotations.SerializedName;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * Body28
 */
@javax.annotation.Generated(value = "io.swagger.codegen.languages.JavaClientCodegen", date = "2019-12-31T10:20:07.583Z")
public class Body28 {
  @SerializedName("deviceid")
  private String deviceid = null;

  @SerializedName("mer_id")
  private String merId = null;

  @SerializedName("type")
  private String type = null;

  @SerializedName("sessiontoken")
  private String sessiontoken = null;

  public Body28 deviceid(String deviceid) {
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

  public Body28 merId(String merId) {
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

  public Body28 type(String type) {
    this.type = type;
    return this;
  }

   /**
   * Get type
   * @return type
  **/
   @Schema(required = true, description = "")
  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public Body28 sessiontoken(String sessiontoken) {
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
    Body28 body28 = (Body28) o;
    return Objects.equals(this.deviceid, body28.deviceid) &&
        Objects.equals(this.merId, body28.merId) &&
        Objects.equals(this.type, body28.type) &&
        Objects.equals(this.sessiontoken, body28.sessiontoken);
  }

  @Override
  public int hashCode() {
    return Objects.hash(deviceid, merId, type, sessiontoken);
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Body28 {\n");
    
    sb.append("    deviceid: ").append(toIndentedString(deviceid)).append("\n");
    sb.append("    merId: ").append(toIndentedString(merId)).append("\n");
    sb.append("    type: ").append(toIndentedString(type)).append("\n");
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

