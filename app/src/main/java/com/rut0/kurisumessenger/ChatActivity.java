package com.rut0.kurisumessenger;

import android.os.Build;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ListView;

import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.rut0.kurisumessenger.chat.ChatCursorAdapter;
import com.rut0.kurisumessenger.chat.ChatDatabase;
import com.rut0.kurisumessenger.helper.Extensions;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ChatActivity extends SherlockActivity {
    @Bind(R.id.messageList)
    ListView messageList;

    @Bind(R.id.text)
    EditText textField;

    private ChatCursorAdapter adapter;
    private ChatDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        ButterKnife.bind(this);

        database = new ChatDatabase(this, "mTest");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            adapter = new ChatCursorAdapter(this, database.getMessages(), 0);
        } else {
            adapter = new ChatCursorAdapter(this, database.getMessages());
        }
        messageList.setAdapter(adapter);
    }

    @OnClick(R.id.sendButton)
    void sendMessage() {
        String text = textField.getText().toString().trim();
        if (text.length() > 0) {
            database.addMessage("ME", text);
            updateMessage();
        }
    }

    public void updateMessage() {
        Extensions.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
                    adapter.swapCursor(database.getMessages());
                } else {
                    adapter.changeCursor(database.getMessages());
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getSupportMenuInflater().inflate(R.menu.menu_chat, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
