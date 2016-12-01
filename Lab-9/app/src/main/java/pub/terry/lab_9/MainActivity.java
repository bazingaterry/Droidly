package pub.terry.lab_9;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
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
    private TextView city, updateTime, temperature, highLow, humidity, airQuality, wind;
    private RecyclerView recyclerView;
    private Handler handler;
    private Weather weather;
    private ListAdapter listAdapter;
    private RecycleAdapter recycleAdapter;

    final private int OK = 0, ERR_TOO_FAST = -1, ERR_NULL = -2, ERR_LIMIT = -3;

    void getView() {
        button = (Button) findViewById(R.id.btn_search);
        editText = (EditText) findViewById(R.id.input_city);
        tips = (ListView) findViewById(R.id.tips);
        city = (TextView) findViewById(R.id.city);
        updateTime = (TextView) findViewById(R.id.updateTime);
        temperature = (TextView) findViewById(R.id.temperature);
        highLow = (TextView) findViewById(R.id.low_high);
        humidity = (TextView) findViewById(R.id.humidity);
        airQuality = (TextView) findViewById(R.id.airQuality);
        wind = (TextView) findViewById(R.id.wind);
        recyclerView = (RecyclerView) findViewById(R.id.futureWeather);
    }

    void init() {
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        String cityCode = editText.getText().toString();
                        String url = String.format("http://ws.webxml.com.cn/WebServices/WeatherWS.asmx/getWeather?theCityCode=%d&theUserID=", cityCode);
                        try {
                            HttpURLConnection httpURLConnection = (HttpURLConnection) new URL(url).openConnection();
                            httpURLConnection.setRequestMethod("GET");
                            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
                            if (httpURLConnection.getResponseCode() == 200) {
                                StringBuilder builder = new StringBuilder();
                                String string;
                                while ((string = bufferedReader.readLine()) != null) {
                                    builder.append(string + "\n");
                                }
                                String response = builder.toString();
                                if (response.contains("免费用户24小时内访问超过规定数量")) {
                                    handler.sendEmptyMessage(ERR_LIMIT);
                                } else if (response.contains("免费用户不能使用高速访问")) {
                                    handler.sendEmptyMessage(ERR_TOO_FAST);
                                } else if (response.contains("查询结果为空")) {
                                    handler.sendEmptyMessage(ERR_NULL);
                                } else {
                                    weather = new Weather(parseXML(response).split("\n"));
                                    handler.sendEmptyMessage(OK);
                                }
                                bufferedReader.close();
                            } else {
                                Log.e("Network", "HTTP Error");
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }).start();
            }
        });
        listAdapter = new ListAdapter(new ArrayList<String>(), this);
        tips.setAdapter(listAdapter);
        recycleAdapter = new RecycleAdapter(new ArrayList<String>(), this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        recyclerView.setAdapter(recycleAdapter);
        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case OK:
                        city.setText(weather.getCity());
                        updateTime.setText(weather.getUpdateTime());
                        temperature.setText(weather.getTemperature());
                        highLow.setText(weather.getLowHigh());
                        humidity.setText(weather.getHumidity());
                        airQuality.setText(weather.getAirQuality());
                        wind.setText(weather.getWind());
                        listAdapter.setTips(weather.getTips());
                        listAdapter.notifyDataSetChanged();
                        recycleAdapter.setFutureWeathers(weather.getFuture());
                        recycleAdapter.notifyDataSetChanged();
                        break;
                    case ERR_TOO_FAST:
                        showToast("免费用户不能使用高速访问");
                        break;
                    case ERR_NULL:
                        showToast("查询结果为空");
                        break;
                    case ERR_LIMIT:
                        showToast("免费用户24小时内访问超过规定数量");
                        break;
                }
            }
        };
    }

    String parseXML(String body) throws XmlPullParserException, IOException {
        XmlPullParserFactory xmlPullParserFactory = XmlPullParserFactory.newInstance();
        xmlPullParserFactory.setNamespaceAware(true);
        XmlPullParser xmlPullParser = xmlPullParserFactory.newPullParser();

        StringBuilder builder = new StringBuilder();
        xmlPullParser.setInput(new StringReader(body));
        int eventType = xmlPullParser.getEventType();
        while (eventType != XmlPullParser.END_DOCUMENT) {
            if (eventType == XmlPullParser.START_TAG) {
                eventType = xmlPullParser.next();
                if (eventType == XmlPullParser.TEXT) {
                    builder.append(xmlPullParser.getText() + "\n");
                }
            }
            eventType = xmlPullParser.next();
        }
        return builder.toString();
    }

    void showToast(String content) {
        Toast.makeText(this, content, Toast.LENGTH_SHORT).show();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getView();
        init();
    }
}
