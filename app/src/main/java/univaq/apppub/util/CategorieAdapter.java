package univaq.apppub.util;

import android.content.Context;
import android.media.Image;
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
import univaq.apppub.controller.CategorieActivity;
import univaq.apppub.model.Categoria;
import univaq.apppub.model.Piatto;

/**
 * Created by Gioele on 09/12/2017.
 */

public class CategorieAdapter extends RecyclerView.Adapter<CategorieAdapter.ViewHolder> {
    private Context mContext;
    private List<Categoria> CategoriaList;
    private ItemClickListener mClickListener;

    public class ViewHolder extends RecyclerView.ViewHolder{
        // each data item is just a string in this case
        public TextView mNome;
        public ImageView mThumbnail;
        public CardView cardView;

        public ViewHolder(View v) {
            super(v);
            mNome = (TextView) v.findViewById(R.id.nome);
            mThumbnail = (ImageView) v.findViewById(R.id.thumbnail_categoria);
            cardView = (CardView) v.findViewById(R.id.card_view);
        }

    }

    public CategorieAdapter(Context mContext, List<Categoria> CategoriaList) {
        this.mContext = mContext;
        this.CategoriaList = CategoriaList;
    }


    @Override
    public CategorieAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.categoria_view, parent, false);
        return new CategorieAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final CategorieAdapter.ViewHolder holder, final int position) {
        final Categoria categoria = CategoriaList.get(position);
        holder.mNome.setText(categoria.getNome());
        System.out.println(categoria.getImg());
        Glide.with(mContext).load(categoria.getImg()).into(holder.mThumbnail);

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            // rilevo il click su una card specifica
            public void onClick(View view) {
                //passo l'evento click all'activity che gestisce l'adapter
                if (mClickListener != null) mClickListener.onItemClick(view, categoria);
            }
        });

    }


    // parent activity will implement this method to respond to click events
    public interface ItemClickListener {
        void onItemClick(View view, Categoria categoria);
    }

    @Override
    public int getItemCount() {
        return CategoriaList.size();
    }

    // setto il listner all click
    public void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }


}
