package cn.wingene.mallxm.display.home.firstMenu.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.limecn.ghmall.R;

import java.util.Iterator;
import java.util.List;

/**
 * Created by wangcq on 2017/9/2.
 * 搜索item适配器
 */

public class SearchItemAdapter extends RecyclerView.Adapter {
    public static final int LOCAL_SEARCH_TYPE = 1;
    public static final int HOT_SEARCH_TYPE = 2;

    private HistorySearchItemChoiceListener mHistorySearchItemChoiceListener;
    private int type = LOCAL_SEARCH_TYPE;
    private List<String> mStringList;

    public SearchItemAdapter(List<String> stringList, int type) {
        this.type = type;
        this.mStringList = stringList;
    }

    public void setHistorySearchItemChoiceListener(HistorySearchItemChoiceListener historySearchItemChoiceListener) {
        mHistorySearchItemChoiceListener = historySearchItemChoiceListener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.search_item_layout, parent, false);

        return new ItemHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        ItemHolder itemHolder = (ItemHolder) holder;
        itemHolder.mTextView.setText(mStringList.get(position));
        if (type == LOCAL_SEARCH_TYPE) {
            itemHolder.mTextView.setBackgroundResource(R.drawable.line_gray_shape);
        } else if (type == HOT_SEARCH_TYPE) {
            itemHolder.mTextView.setBackgroundResource(R.drawable.yellow_linear);
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mHistorySearchItemChoiceListener != null) {
                    mHistorySearchItemChoiceListener.historySearchItemChoice(position);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mStringList.size();
    }

    class ItemHolder extends RecyclerView.ViewHolder {
        private TextView mTextView;

        public ItemHolder(View itemView) {
            super(itemView);
            mTextView = (TextView) itemView.findViewById(R.id.searchItemV);
        }
    }

    public interface HistorySearchItemChoiceListener {
        void historySearchItemChoice(int position);
    }
}
