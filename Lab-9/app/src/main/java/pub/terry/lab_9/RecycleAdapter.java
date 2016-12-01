package pub.terry.lab_9;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by terrychan on 01/12/2016.
 */

public class RecycleAdapter extends RecyclerView.Adapter {
    private List<String> futureWeathers;
    private Context context;

    void setFutureWeathers(List<String> futureWeathers) {
        this.futureWeathers = futureWeathers;
    }

    RecycleAdapter(List<String> futureWeathers, Context context) {
        this.futureWeathers = futureWeathers;
        this.context = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new FutureHolder(LayoutInflater.from(context).inflate(R.layout.weather_item, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        FutureHolder futureHolder = (FutureHolder) holder;
        String[] contents = futureWeathers.get(position).split("\n");
        futureHolder.getFuture_data().setText((contents[0].split(" "))[0]);
        futureHolder.getFuture_weather().setText((contents[0].split(" "))[1]);
        futureHolder.getFuture_low_high().setText(contents[1]);
    }

    @Override
    public int getItemCount() {
        return futureWeathers == null ? 0 : futureWeathers.size();
    }

    private class FutureHolder extends RecyclerView.ViewHolder {
        private TextView future_data, future_weather, future_low_high;

        FutureHolder(View view) {
            super(view);
            future_data = (TextView) view.findViewById(R.id.date);
            future_weather = (TextView) view.findViewById(R.id.weather);
            future_low_high = (TextView) view.findViewById(R.id.item_low_high);
        }

        TextView getFuture_data() {
            return future_data;
        }

        TextView getFuture_weather() {
            return future_weather;
        }

        TextView getFuture_low_high() {
            return future_low_high;
        }
    }
}
