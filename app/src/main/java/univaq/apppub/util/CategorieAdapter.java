package univaq.apppub.util;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import univaq.apppub.R;
import univaq.apppub.model.Piatto;

/**
 * Created by Gioele on 09/12/2017.
 */

public class CategorieAdapter extends RecyclerView.Adapter<CategorieAdapter.ViewHolder> {
    private String[] mDataset;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView mTextView;
        public ViewHolder(View v) {
            super(v);
            mTextView = (TextView) v.findViewById(R.id.info_text);

        }
    }

    public CategorieAdapter(String[] myDataset) {
        mDataset = myDataset;
    }


    @Override
    public CategorieAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.categoria_view, parent, false);
        return new CategorieAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(CategorieAdapter.ViewHolder holder, int position) {
        holder.mTextView.setText(mDataset[position]);
    }

    @Override
    public int getItemCount() {
        return mDataset.length;
    }


}
