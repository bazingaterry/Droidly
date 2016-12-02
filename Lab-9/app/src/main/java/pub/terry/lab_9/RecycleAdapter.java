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

class RecycleAdapter extends RecyclerView.Adapter {
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
        futureHolder.getFutureData().setText((contents[0].split(" "))[0]);
        futureHolder.getFutureWeather().setText((contents[0].split(" "))[1]);
        futureHolder.getFutureLowHigh().setText(contents[1]);
    }

    @Override
    public int getItemCount() {
        return futureWeathers.size();
    }

    private class FutureHolder extends RecyclerView.ViewHolder {
        private TextView futureData, futureWeather, futureLowHigh;

        FutureHolder(View view) {
            super(view);
            futureData = (TextView) view.findViewById(R.id.date);
            futureWeather = (TextView) view.findViewById(R.id.weather);
            futureLowHigh = (TextView) view.findViewById(R.id.item_low_high);
        }

        TextView getFutureData() {
            return futureData;
        }

        TextView getFutureWeather() {
            return futureWeather;
        }

        TextView getFutureLowHigh() {
            return futureLowHigh;
        }
    }
}
