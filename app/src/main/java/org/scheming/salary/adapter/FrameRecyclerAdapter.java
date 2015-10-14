package org.scheming.salary.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.scheming.salary.R;
import org.scheming.salary.entity.User;

import java.util.List;

/**
 * Created by Scheming on 2015/5/29.
 */
public class FrameRecyclerAdapter extends RecyclerView.Adapter<FrameRecyclerAdapter.ViewHolder> {

    private List<User> datas = null;
    private ItemClickListener clickListener = null;
    private ItemLongClickListener longClickListener = null;

    public FrameRecyclerAdapter(List<User> datas) {
        this.datas = datas;
    }

    public void setDatas(List<User> datas) {
        this.datas = datas;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_item, parent, false);
        return new ViewHolder(view, clickListener);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.nameTV.setText(datas.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    public void setOnItemClickListener(ItemClickListener listener) {
        this.clickListener = listener;
    }

    public void setOnItemLongClickListener(ItemLongClickListener longClickListener) {
        this.longClickListener = longClickListener;
    }

    public interface ItemClickListener {
        void OnItemClickListener(View view, int position);
    }

    public interface ItemLongClickListener {
        void OnItemLongClickListener(View view, int position);
    }

    static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView nameTV = null;
        private ItemClickListener clickListener = null;
//        private ItemLongClickListener longClickListener = null;

        public ViewHolder(View itemView, ItemClickListener clickListener) {
            super(itemView);
            nameTV = (TextView) itemView.findViewById(R.id.item_user_name);
            this.clickListener = clickListener;
//            this.longClickListener = longClickListener;
            itemView.setOnClickListener(this);
//            itemView.setOnLongClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (clickListener != null)
                clickListener.OnItemClickListener(v, getAdapterPosition());
        }

//        @Override
//        public boolean onLongClick(View v) {
//            if (longClickListener != null)
//                longClickListener.OnItemLongClickListener(v, getAdapterPosition());
//            return false;
//        }
    }
}
