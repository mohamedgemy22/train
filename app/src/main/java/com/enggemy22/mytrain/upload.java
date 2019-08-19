package com.enggemy22.mytrain;

public class upload {
    private String image;
    private String title;
    private String content;

    public upload() {
    }

    public upload(String image, String title, String content) {
       if (title.isEmpty())
       {
           title="no title";
       }

        this.image = image;
        this.title = title;
        this.content = content;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
