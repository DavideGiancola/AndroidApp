package univaq.apppub.util;

import android.content.Context;
import android.media.Image;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import univaq.apppub.R;
import univaq.apppub.model.Categoria;
import univaq.apppub.model.Piatto;

/**
 * Created by Gioele on 09/12/2017.
 */

public class CategorieAdapter extends RecyclerView.Adapter<CategorieAdapter.ViewHolder> {
    private Context mContext;
    private List<Categoria> CategoriaList;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView mNome;
        public ImageView mThumbnail;
        public ViewHolder(View v) {
            super(v);
            mNome = (TextView) v.findViewById(R.id.nome);
            mThumbnail = (ImageView) v.findViewById(R.id.thumbnail);

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
    public void onBindViewHolder(CategorieAdapter.ViewHolder holder, int position) {
        Categoria categoria = CategoriaList.get(position);
        holder.mNome.setText(categoria.getNome());

        Glide.with(mContext).load(categoria.getImg()).into(holder.mThumbnail);

    }

    @Override
    public int getItemCount() {
        return CategoriaList.size();
    }


}
