package univaq.apppub.util;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import univaq.apppub.R;
import univaq.apppub.model.Piatto;

/**
 * Created by Gioele on 10/12/2017.
 */

public class PiattiAdapter extends RecyclerView.Adapter<PiattiAdapter.ViewHolder> {
    private Context mContext;
    private List<Piatto> PiattiList;
    private ItemClickListener mClickListener;

    public class ViewHolder extends RecyclerView.ViewHolder{
        // each data item is just a string in this case
        public TextView mNome;
        public ImageView mThumbnail;
        public CardView cardView;

        public ViewHolder(View v) {
            super(v);
            mNome = (TextView) v.findViewById(R.id.nome);
            mThumbnail = (ImageView) v.findViewById(R.id.thumbnail);
            cardView = (CardView) v.findViewById(R.id.card_view);
        }

    }

    public PiattiAdapter(Context mContext, List<Piatto> piattoList) {
        this.mContext = mContext;
        this.PiattiList = piattoList;

    }

    @Override
    public PiattiAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.piatti_view, parent, false);
        return new PiattiAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(PiattiAdapter.ViewHolder holder, int position) {
        final Piatto piatto = PiattiList.get(position);
        holder.mNome.setText(piatto.getNome());
        Glide.with(mContext).load(piatto.getImg()).into(holder.mThumbnail);

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            // rilevo il click su una card specifica
            public void onClick(View view) {
                //passo l'evento click all'activity che gestisce l'adapter
                if (mClickListener != null) mClickListener.onItemClick(view, piatto);
            }
        });

    }
    // parent activity will implement this method to respond to click events
    public interface ItemClickListener {
        void onItemClick(View view, Piatto piatto);
    }

    @Override
    public int getItemCount() {
            return PiattiList.size();
    }

    public void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }




}
