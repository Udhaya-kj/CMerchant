package life.corals.merchant.client.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * SetUpVoucherResponse
 */
@javax.annotation.Generated(value = "io.swagger.codegen.languages.JavaClientCodegen", date = "2020-02-28T11:06:04.949Z")
public class SetUpVoucherResponse {
  @SerializedName("status_code")
  private String statusCode = null;

  @SerializedName("status_msg")
  private String statusMsg = null;

  @SerializedName("session_token")
  private String sessionToken = null;

  @SerializedName("voucher_list")
  private List<SetUpRedemptionList> voucherList = null;

  public SetUpVoucherResponse statusCode(String statusCode) {
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

  public SetUpVoucherResponse statusMsg(String statusMsg) {
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

  public SetUpVoucherResponse sessionToken(String sessionToken) {
    this.sessionToken = sessionToken;
    return this;
  }

   /**
   * Get sessionToken
   * @return sessionToken
  **/
  @Schema(description = "")
  public String getSessionToken() {
    return sessionToken;
  }

  public void setSessionToken(String sessionToken) {
    this.sessionToken = sessionToken;
  }

  public SetUpVoucherResponse voucherList(List<SetUpRedemptionList> voucherList) {
    this.voucherList = voucherList;
    return this;
  }

  public SetUpVoucherResponse addVoucherListItem(SetUpRedemptionList voucherListItem) {
    if (this.voucherList == null) {
      this.voucherList = new ArrayList<SetUpRedemptionList>();
    }
    this.voucherList.add(voucherListItem);
    return this;
  }

   /**
   * Get voucherList
   * @return voucherList
  **/
  @Schema(description = "")
  public List<SetUpRedemptionList> getVoucherList() {
    return voucherList;
  }

  public void setVoucherList(List<SetUpRedemptionList> voucherList) {
    this.voucherList = voucherList;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    SetUpVoucherResponse setUpVoucherResponse = (SetUpVoucherResponse) o;
    return Objects.equals(this.statusCode, setUpVoucherResponse.statusCode) &&
        Objects.equals(this.statusMsg, setUpVoucherResponse.statusMsg) &&
        Objects.equals(this.sessionToken, setUpVoucherResponse.sessionToken) &&
        Objects.equals(this.voucherList, setUpVoucherResponse.voucherList);
  }

  @Override
  public int hashCode() {
    return Objects.hash(statusCode, statusMsg, sessionToken, voucherList);
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class SetUpVoucherResponse {\n");
    
    sb.append("    statusCode: ").append(toIndentedString(statusCode)).append("\n");
    sb.append("    statusMsg: ").append(toIndentedString(statusMsg)).append("\n");
    sb.append("    sessionToken: ").append(toIndentedString(sessionToken)).append("\n");
    sb.append("    voucherList: ").append(toIndentedString(voucherList)).append("\n");
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

