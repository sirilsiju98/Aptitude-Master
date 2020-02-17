package com.aptitudemaster;

public class Tutorial {
    String topic;
    String url;
    public Tutorial()
    {

    }

    public Tutorial(String topic, String url) {
        this.topic = topic;
        this.url = url;
    }

    public String getTopic() {
        return topic;
    }

    public String getUrl() {
        return url;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
