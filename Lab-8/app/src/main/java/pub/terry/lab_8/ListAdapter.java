package pub.terry.lab_8;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by terrychan on 17/11/2016.
 */

class ListAdapter extends BaseAdapter {
    private List<Person> persons;
    private Context context;

    ListAdapter(List<Person> persons, Context context) {
        this.persons = persons;
        this.context = context;
    }

    @Override
    public int getCount() {
        return persons.size();
    }

    @Override
    public Object getItem(int i) {
        return persons.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view = View.inflate(this.context, R.layout.list_element, null);
        TextView name = (TextView) view.findViewById(R.id.name);
        TextView birthday = (TextView) view.findViewById(R.id.birthday);
        TextView gift = (TextView) view.findViewById(R.id.gift);
        Person person = persons.get(i);
        name.setText(person.getName());
        birthday.setText(person.getBirthday());
        gift.setText(person.getGift());
        return view;
    }

    void notifyDataSetChanged(List<Person> persons) {
        this.persons = persons;
        notifyDataSetChanged();
    }
}
