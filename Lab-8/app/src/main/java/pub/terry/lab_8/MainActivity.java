package pub.terry.lab_8;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.provider.ContactsContract;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private Button addBtn;
    private ListView listView;
    private Database database;
    private ListAdapter listAdapter;
    List<Person> persons;
    final int REQUEST_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findView();
        setOnClickListener();
        init();
    }

    String getPhoneByName(String name) {
        Cursor cursor = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, new String[]{ContactsContract.CommonDataKinds.Phone.NUMBER}, ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME + "=?", new String[]{name}, null);
        if (cursor.moveToNext()) {
            return cursor.getString(0);
        } else {
            return "";
        }
    }

    void findView() {
        addBtn = (Button) findViewById(R.id.add);
        listView = (ListView) findViewById(R.id.list);
    }

    void setOnClickListener() {
        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AddActivity.class);
                startActivityForResult(intent, REQUEST_CODE);
            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                final Person person = persons.get(i);
                LinearLayout linearLayout = (LinearLayout) getLayoutInflater().inflate(R.layout.list_detail, null);
                EditText nameEditText = (EditText) linearLayout.findViewById(R.id.name);
                final EditText birthdayEditText = (EditText) linearLayout.findViewById(R.id.birthday);
                final EditText giftEditText = (EditText) linearLayout.findViewById(R.id.gift);
                EditText phoneEditText = (EditText) linearLayout.findViewById(R.id.phone);
                nameEditText.setText(person.getName());
                birthdayEditText.setText(person.getBirthday());
                giftEditText.setText(person.getGift());
                phoneEditText.setText(getPhoneByName(person.getName()));
                AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                builder.setPositiveButton("Save", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        database.update(person.getName(), birthdayEditText.getText().toString(), giftEditText.getText().toString());
                        persons = database.queryAll();
                        listAdapter.notifyDataSetChanged(persons);
                    }
                });
                builder.setNegativeButton("Discard", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                builder.setView(linearLayout);
                builder.create().show();
            }
        });
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                final Person person = persons.get(i);
                builder.setTitle("Delete?");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        database.delete(person.getName());
                        persons = database.queryAll();
                        listAdapter.notifyDataSetChanged(persons);
                    }
                });
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                builder.create().show();
                return true;
            }
        });
    }

    void init() {
        this.database = Database.getDatabase(this);
        this.persons = database.queryAll();
        this.listAdapter = new ListAdapter(persons, this);
        this.listView.setAdapter(listAdapter);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE) {
            this.persons = database.queryAll();
            this.listAdapter.notifyDataSetChanged(persons);
        }
    }
}
