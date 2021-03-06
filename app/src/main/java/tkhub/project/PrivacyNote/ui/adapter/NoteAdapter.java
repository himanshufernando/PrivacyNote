package tkhub.project.PrivacyNote.ui.adapter;

import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Random;


import tkhub.project.PrivacyNote.R;
import tkhub.project.PrivacyNote.data.model.NoteItem;
import tkhub.project.PrivacyNote.ui.actitvity.HomeActivity;
import tkhub.project.PrivacyNote.ui.font.TextViewFontAwesome;


/**
 * Created by Himanshu on 4/10/2015.
 */
public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.MyViewHolder> implements View.OnClickListener {

    Context mContext;
    ArrayList<NoteItem> item;


    public NoteAdapter(Context mContext, ArrayList<NoteItem> albumList) {
        this.mContext = mContext;
        this.item = albumList;
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_note_new, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {

        String titel = item.get(position).title;
        String userName = item.get(position).userName;

        if ((mContext.getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) >= Configuration.SCREENLAYOUT_SIZE_LARGE) {
            if (titel.length()>30) {
                holder.eTitel.setText(titel.substring(0,30));
            } if(userName.length()>30){
                holder.username.setText(userName.substring(0,30));
            }else {
                holder.eTitel.setText(titel);
                holder.username.setText(userName);
            }
        } else if ((mContext.getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) >= Configuration.SCREENLAYOUT_SIZE_NORMAL) {
            if (titel.length()>15) {
                holder.eTitel.setText(titel.substring(0, 15));
            }if(userName.length()>15){
                holder.username.setText(userName.substring(0, 15));
            }else {
                holder.eTitel.setText(titel);
                holder.username.setText(userName);
            }

        }



        holder.roundBack.setTextColor(setColor());
        holder.roundBackgrounTitel.setText(item.get(position).title.toUpperCase().substring(0, 1));

        holder.main.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((HomeActivity) mContext).lodeNote(item.get(position).id, item.get(position).title, item.get(position).userName, item.get(position).password, item.get(position).other);
            }
        });

        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((HomeActivity) mContext).accesPermitonforDelete(item.get(position).id);

            }
        });
    }

    @Override
    public int getItemCount() {
        return item.size();
    }

    @Override
    public void onClick(View v) {
    }

    class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {


        TextView eTitel, username, roundBackgrounTitel;
        RelativeLayout main, delete;
        TextViewFontAwesome roundBack;

        public MyViewHolder(View itemView) {
            super(itemView);
            eTitel = (TextView) itemView.findViewById(R.id.textView_title);
            username = (TextView) itemView.findViewById(R.id.textView_username);

            roundBackgrounTitel = (TextView) itemView.findViewById(R.id.textView17);

            roundBack = (TextViewFontAwesome) itemView.findViewById(R.id.textView16);

            main = (RelativeLayout) itemView.findViewById(R.id.relativeLayoutmain);
            delete = (RelativeLayout) itemView.findViewById(R.id.relativeLayoutdelete);


        }


        @Override
        public void onClick(View v) {

        }

    }

    public int setColor() {
        Random rnd = new Random();
        int colorcode = 0, r, g, b;

        r = rnd.nextInt(256);
        g = rnd.nextInt(256);
        b = rnd.nextInt(256);

        while ((r == 255 && g == 256 && b == 256)) {
            r = rnd.nextInt(256);
            g = rnd.nextInt(256);
            b = rnd.nextInt(256);
        }
        colorcode = Color.argb(255, r, g, b);
        return colorcode;
    }
}
