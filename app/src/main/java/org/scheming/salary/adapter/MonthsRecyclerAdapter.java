package org.scheming.salary.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.scheming.salary.R;
import org.scheming.salary.entity.Salary;

import java.util.List;

/**
 * Created by Scheming on 2015/10/15.
 */
public class MonthsRecyclerAdapter extends RecyclerView.Adapter<MonthsRecyclerAdapter.ViewHolder> {
    private List<Salary> datas = null;
    private ItemClickListener clickListener = null;
    private FrameRecyclerAdapter.ItemLongClickListener longClickListener = null;

    public MonthsRecyclerAdapter(List<Salary> datas) {
        this.datas = datas;
    }

    public void setDatas(List<Salary> datas) {
        this.datas = datas;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.month_recycler_item, parent, false);
        return new ViewHolder(view, clickListener);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.nameTV.setText(String.format("%d", datas.get(position).getCurrent_month()));
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    public void setOnItemClickListener(ItemClickListener listener) {
        this.clickListener = listener;
    }

    public interface ItemClickListener {
        void onItemClickListener(View view, int position);
    }

    static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView nameTV = null;
        private ItemClickListener clickListener = null;

        public ViewHolder(View itemView, ItemClickListener clickListener) {
            super(itemView);
            nameTV = (TextView) itemView.findViewById(R.id.months_recycler_item_name);
            this.clickListener = clickListener;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (clickListener != null)
                clickListener.onItemClickListener(v, getAdapterPosition());
        }

    }
}
