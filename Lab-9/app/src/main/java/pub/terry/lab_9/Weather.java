package pub.terry.lab_9;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by terrychan on 01/12/2016.
 */

class Weather {
    private List<String> tips, future;
    private String city, updateTime, temperature, lowHigh, humidity, airQuality, wind;

    public Weather(String[] weather) {
        tips = new ArrayList<>();
        tips.add(weather[8]);
        tips.add(weather[9]);
        tips.add(weather[10]);
        tips.add(weather[11]);
        tips.add(weather[12]);
        tips.add(weather[13]);
        future = new ArrayList<>();
        future.add(weather[15] + "\n" + weather[16]);
        future.add(weather[20] + "\n" + weather[21]);
        future.add(weather[25] + "\n" + weather[26]);
        future.add(weather[30] + "\n" + weather[31]);
        future.add(weather[35] + "\n" + weather[36]);
        city = weather[3];
        updateTime = weather[5].split(" ")[1];
        temperature = weather[6].substring(weather[6].indexOf("气温：") + 3, weather[6].indexOf("；风向"));
        lowHigh = weather[16];
        humidity = weather[6].substring(weather[6].indexOf("湿度："));
        airQuality = weather[7].substring(weather[7].indexOf("空气质量："));
        wind = weather[6].substring(weather[6].indexOf("风力：") + 3, weather[6].indexOf("；湿度"));
    }

    public List<String> getTips() {
        return tips;
    }

    public List<String> getFuture() {
        return future;
    }

    public String getCity() {
        return city;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public String getTemperature() {
        return temperature;
    }

    public String getLowHigh() {
        return lowHigh;
    }

    public String getHumidity() {
        return humidity;
    }

    public String getAirQuality() {
        return airQuality;
    }

    public String getWind() {
        return wind;
    }
}
