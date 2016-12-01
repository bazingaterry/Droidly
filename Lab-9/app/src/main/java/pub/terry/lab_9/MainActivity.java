package pub.terry.lab_9;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private Button button;
    private EditText editText;
    private ListView tips;
    private TextView city, update_time, temperature, highLow, humidity, airQuality, wind;
    private RecyclerView recyclerView;
    private Handler handler;
    private Weather weather;
    private ListAdapter listAdapter;
    private RecycleAdapter recycleAdapter;

    void getView() {
        button = (Button) findViewById(R.id.btn_search);
        editText = (EditText) findViewById(R.id.input_city);
        tips = (ListView) findViewById(R.id.tips);
        city = (TextView) findViewById(R.id.city);
        update_time = (TextView) findViewById(R.id.update_time);
        temperature = (TextView) findViewById(R.id.tempture);
        highLow = (TextView) findViewById(R.id.low_high);
        humidity = (TextView) findViewById(R.id.humidity);
        airQuality = (TextView) findViewById(R.id.airQuality);
        wind = (TextView) findViewById(R.id.windSpeed);
        recyclerView = (RecyclerView) findViewById(R.id.future_weather);
    }

    void init() {
        listAdapter = new ListAdapter(new ArrayList<String>(), this);
        tips.setAdapter(listAdapter);
        recycleAdapter = new RecycleAdapter(new ArrayList<String>(), this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        recyclerView.setAdapter(recycleAdapter);
        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case 1:
                        city.setText(weather.getCity());
                        update_time.setText(weather.getUpdateTime());
                        temperature.setText(weather.getTempture());
                        highLow.setText(weather.getLowHigh());
                        humidity.setText(weather.getHumidity());
                        airQuality.setText(weather.getAirQuality());
                        wind.setText(weather.getWindSpeed());
                        listAdapter.setTips(weather.getTips());
                        listAdapter.notifyDataSetChanged();
                        recycleAdapter.setFuture(weather.getFuture());
                        recycleAdapter.notifyDataSetChanged();
                        break;
                    case 2:
                        showToast("免费用户不能使用高速访问");
                        break;
                    case 3:
                        showToast("查询结果为空");
                        break;
                    case 24:
                        showToast("免费用户24小时内访问超过规定数量");
                        break;
                }
            }
        };
    }

    String praseXML(String body) throws XmlPullParserException, IOException {
        XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
        factory.setNamespaceAware(true);
        XmlPullParser xpp = factory.newPullParser();

        StringBuilder builder = new StringBuilder();
        xpp.setInput(new StringReader(body));
        int eventType = xpp.getEventType();
        while (eventType != XmlPullParser.END_DOCUMENT) {
            if (eventType == XmlPullParser.START_TAG) {
                eventType = xpp.next();
                if (eventType == XmlPullParser.TEXT) {
                    builder.append(xpp.getText() + "\n");
                }
            }
            eventType = xpp.next();
        }
        return builder.toString();
    }

    void setResponse() {
        button.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                String str_city = editText.getText().toString();
                String http_request = "http://ws.webxml.com.cn/WebServices/WeatherWS.asmx/getWeather?theCityCode=" + str_city + "&theUserID=";
                try {
                    HttpURLConnection http = (HttpURLConnection) new URL(http_request).openConnection();
                    http.setRequestMethod("GET");
                    BufferedReader reader = new BufferedReader(new InputStreamReader(http.getInputStream()));
                    if (http.getResponseCode() == 200) {
                        StringBuilder builder = new StringBuilder();
                        String line;
                        while ((line = reader.readLine()) != null) {
                            builder.append(line + "\n");
                        }
                        String body = builder.toString();
                        if (body.contains("免费用户24小时内访问超过规定数量")) {
                            handler.sendEmptyMessage(24);
                            return;
                        } else if (body.contains("免费用户不能使用高速访问")) {
                            handler.sendEmptyMessage(2);
                        } else if (body.contains("查询结果为空")) {
                            handler.sendEmptyMessage(3);
                        } else {
                            weather = new Weather(praseXML(body).split("\n"));
                            handler.sendEmptyMessage(1);
                        }
                        reader.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (XmlPullParserException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    void showToast(String content) {
        Toast.makeText(this, content, Toast.LENGTH_SHORT).show();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}
