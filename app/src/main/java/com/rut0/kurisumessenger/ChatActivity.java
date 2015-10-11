package com.rut0.kurisumessenger;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.transition.Slide;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import com.rut0.kurisumessenger.chat.ChatDatabase;
import com.rut0.kurisumessenger.chat.ChatRecyclerAdapter;
import com.rut0.kurisumessenger.helper.Extensions;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ChatActivity extends AppCompatActivity {
    @Bind(R.id.messageList)
    //ListView messageList;
            RecyclerView messageList;

    @Bind(R.id.text)
    EditText textField;

    //private ChatCursorAdapter adapter;
    private ChatRecyclerAdapter adapter;
    private ChatDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        setUpAnimation();
        ButterKnife.bind(this);

        database = new ChatDatabase(this, "mTest");
        adapter = new ChatRecyclerAdapter(database.getMessagesL());

        /*if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            adapter = new ChatCursorAdapter(this, database.getMessages(), 0);
        } else {
            adapter = new ChatCursorAdapter(this, database.getMessages());
        }*/
        messageList.setLayoutManager(new LinearLayoutManager(this));
        messageList.setItemAnimator(new DefaultItemAnimator());
        messageList.setAdapter(adapter);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void setUpAnimation() {
        Slide slide = new Slide();
        slide.setDuration(500);
        getWindow().setReturnTransition(slide);
    }

    @OnClick(R.id.sendButton)
    void sendMessage() {
        String text = textField.getText().toString().trim();
        if (text.length() > 0) {
            database.addMessage("ME", text);
            updateMessage();
            textField.setText("");
        }
    }

    public void updateMessage() {
        Extensions.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                /*if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
                    adapter.swapCursor(database.getMessages());
                } else {
                    adapter.changeCursor(database.getMessages());
                }*/
                adapter.updateData(database.getMessagesL());
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_chat, menu);
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
