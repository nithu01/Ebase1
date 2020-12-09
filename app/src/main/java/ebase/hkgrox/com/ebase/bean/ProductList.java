package ebase.hkgrox.com.ebase.bean;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by rajeev on 1/2/17.
 */

public class ProductList implements Serializable {


    @SerializedName("image")
    @Expose
    private int image;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    @SerializedName("name")
    @Expose
    private String name;


}
