package sera.com.plasticmobilehomeapp;


import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.Objects;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import sera.com.plasticmobilehomeapp.object.TimeObject;
import sera.com.plasticmobilehomeapp.retrofit.GetTime;
import sera.com.plasticmobilehomeapp.retrofit.RetrofitClientInstance;


/**
 * A simple {@link Fragment} subclass.
 */
public class RectangleFragment extends Fragment {
    @BindView(R.id.timeTV)
    TextView timeTV;
    private GetTime service;

    public RectangleFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_rectangle, container, false);
        ButterKnife.bind(this, v);

        service = RetrofitClientInstance.getRetrofitInstace().create(GetTime.class);
        execute();
        new Timer().scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                execute();
            }
        }, 0, 1);

        return v;
    }

    private void execute() {
        Call<TimeObject> call = service.getTime();
        call.enqueue(new Callback<TimeObject>() {
            @Override
            public void onResponse(@NonNull Call<TimeObject> call, @NonNull Response<TimeObject> response) {
                TimeObject obj = response.body();
                String[] arr = Objects.requireNonNull(obj).getTime().split(" ");
                timeTV.setText(arr[1]);
            }

            @Override
            public void onFailure(Call<TimeObject> call, Throwable t) {
                Log.d("error", t.getMessage());
            }
        });
    }

}
