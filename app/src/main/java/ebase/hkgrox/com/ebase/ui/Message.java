package ebase.hkgrox.com.ebase.ui;

import com.google.gson.annotations.SerializedName;

public class Message {

    @SerializedName("Message")
    String message;

    @SerializedName("Smessage")
    String smessage;

    @SerializedName("Imagepath")
    String image;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @SerializedName("Date")
    String date;

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getSmessage() {
        return smessage;
    }

    public void setSmessage(String smessage) {
        this.smessage = smessage;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
