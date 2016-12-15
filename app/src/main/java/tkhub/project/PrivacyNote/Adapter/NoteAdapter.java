package tkhub.project.PrivacyNote.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

import tkhub.project.PrivacyNote.Layout.Home;
import tkhub.project.PrivacyNote.R;


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
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_note, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {

        holder.eTitel.setText(item.get(position).title);
        holder.username.setText(item.get(position).userName);

        holder.main.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((Home) mContext).lodeNote(item.get(position).id, item.get(position).title, item.get(position).userName, item.get(position).password, item.get(position).other);
            }
        });

        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((Home) mContext).accesPermitonforDelete(item.get(position).id);

            }
        });
    }

    @Override
    public int getItemCount() {
        return item.size();
    }

    @Override
    public void onClick(View v){
    }

    class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {


        TextView eTitel,username;
        RelativeLayout main,delete;


        public MyViewHolder(View itemView) {
            super(itemView);
            eTitel = (TextView) itemView.findViewById(R.id.textView_title);
            username = (TextView) itemView.findViewById(R.id.textView_username);

            main=(RelativeLayout)itemView.findViewById(R.id.relativeLayoutmain);
            delete=(RelativeLayout)itemView.findViewById(R.id.relativeLayoutdelete);


        }


        @Override
        public void onClick(View v) {

        }

    }

}
