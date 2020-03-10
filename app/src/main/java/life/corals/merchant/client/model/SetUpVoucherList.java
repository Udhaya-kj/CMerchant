package life.corals.merchant.client.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import io.swagger.v3.oas.annotations.media.Schema;

@javax.annotation.Generated(value = "io.swagger.codegen.languages.JavaClientCodegen", date = "2020-02-28T11:06:04.949Z")
public class SetUpVoucherList {
  @SerializedName("request_code")
  private String requestCode = null;

  @SerializedName("mer_id")
  private String merId = null;

  @SerializedName("mer_device_id")
  private String merDeviceId = null;

  @SerializedName("session_toke")
  private String sessionToke = null;

  @SerializedName("voucher_list")
  private List<SetUpRedemptionList> voucherList = null;

  public SetUpVoucherList requestCode(String requestCode) {
    this.requestCode = requestCode;
    return this;
  }

   /**
   * Get requestCode
   * @return requestCode
  **/
  @Schema(description = "")
  public String getRequestCode() {
    return requestCode;
  }

  public void setRequestCode(String requestCode) {
    this.requestCode = requestCode;
  }

  public SetUpVoucherList merId(String merId) {
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

  public SetUpVoucherList merDeviceId(String merDeviceId) {
    this.merDeviceId = merDeviceId;
    return this;
  }

   /**
   * Get merDeviceId
   * @return merDeviceId
  **/
  @Schema(description = "")
  public String getMerDeviceId() {
    return merDeviceId;
  }

  public void setMerDeviceId(String merDeviceId) {
    this.merDeviceId = merDeviceId;
  }

  public SetUpVoucherList sessionToke(String sessionToke) {
    this.sessionToke = sessionToke;
    return this;
  }

   /**
   * Get sessionToke
   * @return sessionToke
  **/
   @Schema(description = "")
  public String getSessionToke() {
    return sessionToke;
  }

  public void setSessionToke(String sessionToke) {
    this.sessionToke = sessionToke;
  }

  public SetUpVoucherList voucherList(List<SetUpRedemptionList> voucherList) {
    this.voucherList = voucherList;
    return this;
  }

  public SetUpVoucherList addVoucherListItem(SetUpRedemptionList voucherListItem) {
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
    SetUpVoucherList setUpVoucherList = (SetUpVoucherList) o;
    return Objects.equals(this.requestCode, setUpVoucherList.requestCode) &&
        Objects.equals(this.merId, setUpVoucherList.merId) &&
        Objects.equals(this.merDeviceId, setUpVoucherList.merDeviceId) &&
        Objects.equals(this.sessionToke, setUpVoucherList.sessionToke) &&
        Objects.equals(this.voucherList, setUpVoucherList.voucherList);
  }

  @Override
  public int hashCode() {
    return Objects.hash(requestCode, merId, merDeviceId, sessionToke, voucherList);
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class SetUpVoucherList {\n");
    
    sb.append("    requestCode: ").append(toIndentedString(requestCode)).append("\n");
    sb.append("    merId: ").append(toIndentedString(merId)).append("\n");
    sb.append("    merDeviceId: ").append(toIndentedString(merDeviceId)).append("\n");
    sb.append("    sessionToke: ").append(toIndentedString(sessionToke)).append("\n");
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

