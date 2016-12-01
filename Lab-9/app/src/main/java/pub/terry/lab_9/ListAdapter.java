package pub.terry.lab_9;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by terrychan on 01/12/2016.
 */

class ListAdapter extends BaseAdapter {
    private List<String> tips;
    private Context context;

    public ListAdapter(List<String> tips, Context context) {
        this.tips = tips;
        this.context = context;
    }

    public void setTips(List<String> tips) {
        this.tips = tips;
    }

    @Override
    public int getCount() {
        return tips == null ? 0 : tips.size();
    }

    @Override
    public Object getItem(int i) {
        return tips.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.tip_item, viewGroup, false);
        }
        if (view.getTag() == null) {
            view.setTag(new TipHolder(view));
        }
        String[] contents = tips.get(i).split("：");
        TipHolder tipHolder = (TipHolder) view.getTag();
        tipHolder.getTitle().setText(contents[0]);
        tipHolder.getDetail().setText(contents[1]);
        return view;
    }

    class TipHolder {
        private TextView title, detail;

        TipHolder(View view) {
            title = (TextView) view.findViewById(R.id.textView1);
            detail = (TextView) view.findViewById(R.id.textView2);
        }

        TextView getTitle() {
            return title;
        }

        TextView getDetail() {
            return detail;
        }
    }

}