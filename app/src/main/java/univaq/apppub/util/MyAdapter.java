package univaq.apppub.util;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;


import univaq.apppub.R;

/**
 * Created by Davide on 08/12/2017.
 */

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {

    private String[] mData;

    public MyAdapter(String[] data) {
        mData = data;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_customadapter, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.title.setText(mData[position]);
        holder.subtitle.setText(mData[position]);
    }

    @Override
    public int getItemCount() {
        return mData.length;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView title, subtitle;

        public ViewHolder(View view) {
            super(view);

            title = (TextView) view.findViewById(R.id.customadapter_text_title);
            subtitle = (TextView) view.findViewById(R.id.customadapter_text_subtitle);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {

            Toast.makeText(view.getContext(), title.getText() + " | " + subtitle.getText(), Toast.LENGTH_SHORT).show();
        }
    }
}