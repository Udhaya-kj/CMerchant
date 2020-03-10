package life.corals.merchant.client.model;
import com.google.gson.annotations.SerializedName;

import java.util.Objects;
import io.swagger.v3.oas.annotations.media.Schema;
import life.corals.merchant.client.model.MerchantoutletaddOutletparams;

/**
 * Body8
 */

@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.JavaClientCodegen", date = "2019-11-12T10:27:28.001Z[GMT]")
public class Body8 {
    @SerializedName("deviceid")
    private String deviceid = null;

    @SerializedName("mer_id")
    private String merId = null;

    @SerializedName("sessiontoken")
    private String sessiontoken = null;

    @SerializedName("outletparams")
    private MerchantoutletaddOutletparams outletparams = null;

    public Body8 deviceid(String deviceid) {
        this.deviceid = deviceid;
        return this;
    }

    /**
     * Get deviceid
     *
     * @return deviceid
     **/
    @Schema(required = true, description = "")
    public String getDeviceid() {
        return deviceid;
    }

    public void setDeviceid(String deviceid) {
        this.deviceid = deviceid;
    }

    public Body8 merId(String merId) {
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

    public Body8 sessiontoken(String sessiontoken) {
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

    public Body8 outletparams(MerchantoutletaddOutletparams outletparams) {
        this.outletparams = outletparams;
        return this;
    }

    /**
     * Get outletparams
     *
     * @return outletparams
     **/
    @Schema(description = "")
    public MerchantoutletaddOutletparams getOutletparams() {
        return outletparams;
    }

    public void setOutletparams(MerchantoutletaddOutletparams outletparams) {
        this.outletparams = outletparams;
    }


    @Override
    public boolean equals(java.lang.Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Body8 body8 = (Body8) o;
        return Objects.equals(this.deviceid, body8.deviceid) &&
                Objects.equals(this.merId, body8.merId) &&
                Objects.equals(this.sessiontoken, body8.sessiontoken) &&
                Objects.equals(this.outletparams, body8.outletparams);
    }

    @Override
    public int hashCode() {
        return Objects.hash(deviceid, merId, sessiontoken, outletparams);
    }


    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class Body8 {\n");

        sb.append("    deviceid: ").append(toIndentedString(deviceid)).append("\n");
        sb.append("    merId: ").append(toIndentedString(merId)).append("\n");
        sb.append("    sessiontoken: ").append(toIndentedString(sessiontoken)).append("\n");
        sb.append("    outletparams: ").append(toIndentedString(outletparams)).append("\n");
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
