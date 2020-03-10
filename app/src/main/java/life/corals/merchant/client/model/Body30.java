package life.corals.merchant.client.model;

import com.google.gson.annotations.SerializedName;

import java.util.Objects;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * Body30
 */
@javax.annotation.Generated(value = "io.swagger.codegen.languages.JavaClientCodegen", date = "2020-02-28T11:06:04.949Z")
public class Body30 {
    @SerializedName("deviceid")
    private String deviceid = null;

    @SerializedName("mer_id")
    private String merId = null;

    @SerializedName("plan_id")
    private String planId = null;

    @SerializedName("outletid")
    private String outletid = null;

    @SerializedName("sessiontoken")
    private String sessiontoken = null;

    public Body30 deviceid(String deviceid) {
        this.deviceid = deviceid;
        return this;
    }

    /**
     * Get deviceid
     *
     * @return deviceid
     **/
    @Schema(description = "")
    public String getDeviceid() {
        return deviceid;
    }

    public void setDeviceid(String deviceid) {
        this.deviceid = deviceid;
    }

    public Body30 merId(String merId) {
        this.merId = merId;
        return this;
    }

    /**
     * Get merId
     *
     * @return merId
     **/
    @Schema(description = "")
    public String getMerId() {
        return merId;
    }

    public void setMerId(String merId) {
        this.merId = merId;
    }

    public Body30 planId(String planId) {
        this.planId = planId;
        return this;
    }

    /**
     * Get planId
     *
     * @return planId
     **/
    @Schema(description = "")
    public String getPlanId() {
        return planId;
    }

    public void setPlanId(String planId) {
        this.planId = planId;
    }

    public Body30 outletid(String outletid) {
        this.outletid = outletid;
        return this;
    }

    /**
     * Get outletid
     *
     * @return outletid
     **/
    @Schema(description = "")
    public String getOutletid() {
        return outletid;
    }

    public void setOutletid(String outletid) {
        this.outletid = outletid;
    }

    public Body30 sessiontoken(String sessiontoken) {
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
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Body30 body30 = (Body30) o;
        return Objects.equals(this.deviceid, body30.deviceid) &&
                Objects.equals(this.merId, body30.merId) &&
                Objects.equals(this.planId, body30.planId) &&
                Objects.equals(this.outletid, body30.outletid) &&
                Objects.equals(this.sessiontoken, body30.sessiontoken);
    }

    @Override
    public int hashCode() {
        return Objects.hash(deviceid, merId, planId, outletid, sessiontoken);
    }


    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class Body30 {\n");

        sb.append("    deviceid: ").append(toIndentedString(deviceid)).append("\n");
        sb.append("    merId: ").append(toIndentedString(merId)).append("\n");
        sb.append("    planId: ").append(toIndentedString(planId)).append("\n");
        sb.append("    outletid: ").append(toIndentedString(outletid)).append("\n");
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

