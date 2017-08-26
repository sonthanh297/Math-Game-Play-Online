package pvsasoftware.mathgames.Multiplayer.FriendList;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import pvsasoftware.mathgames.Multiplayer.enties.Multiplayer;
import pvsasoftware.mathgames.R;

/**
 * Created by thien on 12/21/2016.
 */

public class MultiplayerAdapter extends RecyclerView.Adapter< MultiplayerAdapter.ViewHolder>{
        private List<Multiplayer> multiplayers;
        private OnItemClickListener onItemClickListener;


      public MultiplayerAdapter ( List<Multiplayer> multiplayers,OnItemClickListener onItemClickListener) {
           this.multiplayers = multiplayers;
           this.onItemClickListener = onItemClickListener;
      }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.multiplayer_online_view,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
            Multiplayer user = multiplayers.get(position);
            holder.setClickListener(user,onItemClickListener);
            String email = user.getMultiplayerEmail();
            boolean online = user.getMultiplayerstatus();
            String status = online ? "online" : "offline";
            int color = online ? Color.GREEN : Color.RED;

            holder.tv_multiplayerName.setText(email);
            holder.tv_multiplayerSatus.setText(status);
            holder.tv_multiplayerSatus.setTextColor(color);

    }

    @Override
    public int getItemCount() {
        return multiplayers.size();
    }

    private int getPositionByUserName(String username) {
        int position = 0;
        for (Multiplayer user : multiplayers) {
            if (user.getMultiplayerEmail().equals(username)){
                break;
            }
            position ++;
        }
        return position;
    }

    private boolean checkInAdapter(Multiplayer newUser) {
        boolean check = false ;

        for (Multiplayer user : multiplayers) {
            if (user.getMultiplayerEmail().equals(newUser.getMultiplayerEmail())) {
                check = true;
                break;
            }
        }
        return check;
    }
    public void add(Multiplayer user) {
        if (!checkInAdapter(user)) {
            this.multiplayers.add(user);
            this.notifyDataSetChanged();
        }
    }

    public void update(Multiplayer user) {
        int pos = getPositionByUserName(user.getMultiplayerEmail());
        this.multiplayers.set(pos,user);
        this.notifyDataSetChanged();

    }

    public  void remove(Multiplayer user) {
        int pos = getPositionByUserName(user.getMultiplayerEmail());
        multiplayers.remove(pos);
        this.notifyDataSetChanged();
    }


    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tv_multiplayerName, tv_multiplayerSatus;
        ImageView img_multiplayerAvatar;
        View view;
        public ViewHolder(View view){
            super(view);
            this.view = view;
            tv_multiplayerName = (TextView)view.findViewById(R.id.tv_multiplayer_name);
            tv_multiplayerSatus = (TextView)view.findViewById(R.id.txtStatus);
            img_multiplayerAvatar = (ImageView)view.findViewById(R.id.img_multiplayer_avatar);
        }
         public void setClickListener(final  Multiplayer user, final OnItemClickListener listener) {
             view.setOnClickListener(new View.OnClickListener() {
                 @Override
                 public void onClick(View view) {
                     listener.onItemClick(user);
                 }
             });

             view.setOnLongClickListener(new View.OnLongClickListener() {
                 @Override
                 public boolean onLongClick(View view) {
                     listener.onItemLongClick(user);
                     return false;
                 }
             });
         }
    }



}
