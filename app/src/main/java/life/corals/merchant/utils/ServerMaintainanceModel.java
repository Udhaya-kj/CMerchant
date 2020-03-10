package life.corals.merchant.utils;

import com.google.gson.annotations.SerializedName;

import java.util.Objects;

public class ServerMaintainanceModel {

    @SerializedName("title")
    private String messageTitle = null;

    @SerializedName("Message")
    private String messageContent = null;

    @SerializedName("start-datetime")
    private String startDate = null;

    @SerializedName("end-datetime")
    private String endDate = null;

    @SerializedName("is-mapp-down")
    private boolean isAppDown = false;

    public ServerMaintainanceModel(String messageTitle, String messageContent, String startDate, String endDate, boolean isAppDown) {
        this.messageTitle = messageTitle;
        this.messageContent = messageContent;
        this.startDate = startDate;
        this.endDate = endDate;
        this.isAppDown = isAppDown;
    }

    @Override
    public String toString() {
        return "ServerMaintainanceModel{" +
                "messageTitle='" + messageTitle + '\'' +
                ", messageContent='" + messageContent + '\'' +
                ", startDate='" + startDate + '\'' +
                ", endDate='" + endDate + '\'' +
                ", isAppDown=" + isAppDown +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ServerMaintainanceModel)) return false;
        ServerMaintainanceModel that = (ServerMaintainanceModel) o;
        return isAppDown() == that.isAppDown() &&
                Objects.equals(getMessageTitle(), that.getMessageTitle()) &&
                Objects.equals(getMessageContent(), that.getMessageContent()) &&
                Objects.equals(getStartDate(), that.getStartDate()) &&
                Objects.equals(getEndDate(), that.getEndDate());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getMessageTitle(), getMessageContent(), getStartDate(), getEndDate(), isAppDown());
    }

    public void setMessageTitle(String messageTitle) {
        this.messageTitle = messageTitle;
    }

    public void setMessageContent(String messageContent) {
        this.messageContent = messageContent;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public void setAppDown(boolean appDown) {
        isAppDown = appDown;
    }

    public String getMessageTitle() {
        return messageTitle;
    }

    public String getMessageContent() {
        return messageContent;
    }

    public String getStartDate() {
        return startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public boolean isAppDown() {
        return isAppDown;
    }
}
