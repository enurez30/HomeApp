package sera.com.plasticmobilehomeapp.object;

import com.google.gson.annotations.SerializedName;

public class TimeObject {

    @SerializedName("datetime")
    private String time;

    public TimeObject(String time) {
        this.time = time;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
