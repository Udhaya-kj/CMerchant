package life.corals.merchant.utils;

import com.google.gson.annotations.SerializedName;

import java.util.Objects;

public class MerchantUpdateModel {

    @SerializedName("version")
    private String updateVersion = null;

    @SerializedName("force-update")
    private boolean isforceUpdate = true;

    @SerializedName("min-support-version")
    private String minimumSupportVersion = null;

    @SerializedName("msg")
    private String updateMessage = null;

    public MerchantUpdateModel() {
    }

    public MerchantUpdateModel(String updateVersion, boolean isforceUpdate, String minimumSupportVersion, String updateMessage) {
        this.updateVersion = updateVersion;
        this.isforceUpdate = isforceUpdate;
        this.minimumSupportVersion = minimumSupportVersion;
        this.updateMessage = updateMessage;
    }

    @Override
    public String toString() {
        return "CoralsUpdateModel{" +
                "updateVersion='" + updateVersion + '\'' +
                ", isforceUpdate=" + isforceUpdate +
                ", minimumSupportVersion='" + minimumSupportVersion + '\'' +
                ", updateMessage='" + updateMessage + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MerchantUpdateModel)) return false;
        MerchantUpdateModel that = (MerchantUpdateModel) o;
        return isIsforceUpdate() == that.isIsforceUpdate() &&
                Objects.equals(getUpdateVersion(), that.getUpdateVersion()) &&
                Objects.equals(getMinimumSupportVersion(), that.getMinimumSupportVersion()) &&
                Objects.equals(getUpdateMessage(), that.getUpdateMessage());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getUpdateVersion(), isIsforceUpdate(), getMinimumSupportVersion(), getUpdateMessage());
    }

    public void setUpdateVersion(String updateVersion) {
        this.updateVersion = updateVersion;
    }

    public void setIsforceUpdate(boolean isforceUpdate) {
        this.isforceUpdate = isforceUpdate;
    }

    public void setMinimumSupportVersion(String minimumSupportVersion) {
        this.minimumSupportVersion = minimumSupportVersion;
    }

    public void setUpdateMessage(String updateMessage) {
        this.updateMessage = updateMessage;
    }

    public String getUpdateVersion() {
        return updateVersion;
    }

    public boolean isIsforceUpdate() {
        return isforceUpdate;
    }

    public String getMinimumSupportVersion() {
        return minimumSupportVersion;
    }

    public String getUpdateMessage() {
        return updateMessage;
    }
}
