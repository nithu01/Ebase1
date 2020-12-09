package ebase.hkgrox.com.ebase.bean;

import com.google.gson.annotations.SerializedName;

public class Registerdevice {

    @SerializedName("Message")
    String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
