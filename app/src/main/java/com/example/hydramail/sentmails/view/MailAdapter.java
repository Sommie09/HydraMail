package com.example.hydramail.sentmails.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.hydramail.R;
import com.example.hydramail.sentmails.database.model.Mails;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MailAdapter extends RecyclerView.Adapter<MailAdapter.MyViewHolder> {

    private Context context;
    private ArrayList<String> mail_id, mail_recipient, mail_subject, mail_message, mail_timestamp;

    Animation translate_anim;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView id;
        public TextView recipient;
        public TextView subject;
        public TextView message;
        public TextView timestamp;
        public FrameLayout mail_layout;

        public MyViewHolder(View view) {
            super(view);

            recipient = view.findViewById(R.id.email_recipient);
            subject = view.findViewById(R.id.email_subject);
            message = view.findViewById(R.id.email_message);
            timestamp = view.findViewById(R.id.email_date);
            id = view.findViewById(R.id.mail_id);
            mail_layout = view.findViewById(R.id.item_view_layout);

            //Animate Recycler View
            translate_anim = AnimationUtils.loadAnimation(context, R.anim.translate_anim);
            mail_layout.setAnimation(translate_anim);
        }
    }

    public MailAdapter(Context context, ArrayList<String> mail_id, ArrayList<String> mail_recipient, ArrayList<String> mail_subject, ArrayList<String> mail_message, ArrayList<String> mail_timestamp) {
        this.context = context;
        this.mail_id = mail_id;
        this.mail_recipient = mail_recipient;
        this.mail_subject = mail_subject;
        this.mail_message = mail_message;
        this.mail_timestamp = mail_timestamp;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.mail_item_view, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.id.setText(String.valueOf(mail_id.get(position)));
        holder.recipient.setText(String.valueOf(mail_recipient.get(position)));
        holder.subject.setText(String.valueOf(mail_subject.get(position)));
        holder.message.setText(String.valueOf(mail_message.get(position)));
        holder.timestamp.setText(formatDate(mail_timestamp.get(position)));
    }

    @Override
    public int getItemCount() {
        return mail_id.size();
    }

    /**
     * Formatting timestamp to `MMM d` format
     * Input: 2018-02-21 00:15:42
     * Output: Feb 21
     */
    private String formatDate(String dateStr) {
        try {
            SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date date = fmt.parse(dateStr);
            SimpleDateFormat fmtOut = new SimpleDateFormat("MMM d");
            return fmtOut.format(date);
        } catch (ParseException e) {

        }
        return "";
    }
}
