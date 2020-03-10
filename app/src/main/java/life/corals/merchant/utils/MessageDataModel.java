package life.corals.merchant.utils;

public class MessageDataModel {

    String title;
    String content;

    public MessageDataModel(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }
}