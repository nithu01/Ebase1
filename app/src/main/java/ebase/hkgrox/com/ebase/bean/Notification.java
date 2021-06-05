package ebase.hkgrox.com.ebase.bean;

import com.google.gson.annotations.SerializedName;

public class Notification {

    @SerializedName("Message")
    String message;
    @SerializedName("status")
    String status;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
