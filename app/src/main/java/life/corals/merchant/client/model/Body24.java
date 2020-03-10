package life.corals.merchant.client.model;
import com.google.gson.annotations.SerializedName;

import java.util.Objects;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * Body24
 */

@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.JavaClientCodegen", date = "2019-11-01T11:10:12.230Z[GMT]")
public class Body24 {
    @SerializedName("device_id")
    private String deviceId = null;

    @SerializedName("mer_id")
    private String merId = null;

    @SerializedName("cust_id")
    private String custId = null;

    @SerializedName("cust_mobile_no")
    private String custMobileNo = null;

    @SerializedName("from_date")
    private String fromDate = null;

    @SerializedName("to_date")
    private String toDate = null;

    @SerializedName("sessiontoken")
    private String sessiontoken = null;

    public Body24 deviceId(String deviceId) {
        this.deviceId = deviceId;
        return this;
    }

    /**
     * Get deviceId
     *
     * @return deviceId
     **/
    @Schema(required = true, description = "")
    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public Body24 merId(String merId) {
        this.merId = merId;
        return this;
    }

    /**
     * Get merId
     *
     * @return merId
     **/
    @Schema(required = true, description = "")
    public String getMerId() {
        return merId;
    }

    public void setMerId(String merId) {
        this.merId = merId;
    }

    public Body24 custId(String custId) {
        this.custId = custId;
        return this;
    }

    /**
     * Get custId
     *
     * @return custId
     **/
    @Schema(description = "")
    public String getCustId() {
        return custId;
    }

    public void setCustId(String custId) {
        this.custId = custId;
    }

    public Body24 custMobileNo(String custMobileNo) {
        this.custMobileNo = custMobileNo;
        return this;
    }

    /**
     * Get custMobileNo
     *
     * @return custMobileNo
     **/
    @Schema(description = "")
    public String getCustMobileNo() {
        return custMobileNo;
    }

    public void setCustMobileNo(String custMobileNo) {
        this.custMobileNo = custMobileNo;
    }

    public Body24 fromDate(String fromDate) {
        this.fromDate = fromDate;
        return this;
    }

    /**
     * Get fromDate
     *
     * @return fromDate
     **/
    @Schema(description = "")
    public String getFromDate() {
        return fromDate;
    }

    public void setFromDate(String fromDate) {
        this.fromDate = fromDate;
    }

    public Body24 toDate(String toDate) {
        this.toDate = toDate;
        return this;
    }

    /**
     * Get toDate
     *
     * @return toDate
     **/
    @Schema(description = "")
    public String getToDate() {
        return toDate;
    }

    public void setToDate(String toDate) {
        this.toDate = toDate;
    }

    public Body24 sessiontoken(String sessiontoken) {
        this.sessiontoken = sessiontoken;
        return this;
    }

    /**
     * Get sessiontoken
     *
     * @return sessiontoken
     **/
    @Schema(description = "")
    public String getSessiontoken() {
        return sessiontoken;
    }

    public void setSessiontoken(String sessiontoken) {
        this.sessiontoken = sessiontoken;
    }


    @Override
    public boolean equals(java.lang.Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Body24 body24 = (Body24) o;
        return Objects.equals(this.deviceId, body24.deviceId) &&
                Objects.equals(this.merId, body24.merId) &&
                Objects.equals(this.custId, body24.custId) &&
                Objects.equals(this.custMobileNo, body24.custMobileNo) &&
                Objects.equals(this.fromDate, body24.fromDate) &&
                Objects.equals(this.toDate, body24.toDate) &&
                Objects.equals(this.sessiontoken, body24.sessiontoken);
    }

    @Override
    public int hashCode() {
        return Objects.hash(deviceId, merId, custId, custMobileNo, fromDate, toDate, sessiontoken);
    }


    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class Body24 {\n");

        sb.append("    deviceId: ").append(toIndentedString(deviceId)).append("\n");
        sb.append("    merId: ").append(toIndentedString(merId)).append("\n");
        sb.append("    custId: ").append(toIndentedString(custId)).append("\n");
        sb.append("    custMobileNo: ").append(toIndentedString(custMobileNo)).append("\n");
        sb.append("    fromDate: ").append(toIndentedString(fromDate)).append("\n");
        sb.append("    toDate: ").append(toIndentedString(toDate)).append("\n");
        sb.append("    sessiontoken: ").append(toIndentedString(sessiontoken)).append("\n");
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
