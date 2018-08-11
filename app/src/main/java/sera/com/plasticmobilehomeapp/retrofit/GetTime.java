package sera.com.plasticmobilehomeapp.retrofit;

import retrofit2.Call;
import retrofit2.http.GET;
import sera.com.plasticmobilehomeapp.object.TimeObject;

public interface GetTime {

    @GET(".")
    Call<TimeObject> getTime();

}
