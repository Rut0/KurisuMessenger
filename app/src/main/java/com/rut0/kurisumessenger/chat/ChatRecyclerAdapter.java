package com.rut0.kurisumessenger.chat;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.rut0.kurisumessenger.R;

import java.util.List;

/**
 * Created by Winston on 9/22/2015.
 */
public class ChatRecyclerAdapter extends RecyclerView.Adapter<ChatRecyclerAdapter.ViewHolder> {
    List<MessageData> messages;

    public ChatRecyclerAdapter(List<MessageData> data) {
        messages = data;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_message, viewGroup, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        MessageData msg = messages.get(i);
        viewHolder.name.setText(msg.name);
        viewHolder.message.setText(msg.message);
    }

    @Override
    public int getItemCount() {
        return messages.size();
    }

    public void updateData(List<MessageData> data) {
        messages = data;
        notifyDataSetChanged();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView name;
        public TextView message;

        ViewHolder(View v) {
            super(v);
            name = (TextView) v.findViewById(R.id.name);
            message = (TextView) v.findViewById(R.id.message);
        }
    }
}
