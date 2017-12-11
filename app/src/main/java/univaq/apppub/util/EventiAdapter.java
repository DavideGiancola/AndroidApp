package univaq.apppub.util;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import univaq.apppub.R;
import univaq.apppub.controller.EventiDettaglioActivity;
import univaq.apppub.model.Evento;

/**
 * Created by Davide on 08/12/2017.
 */

public class EventiAdapter extends RecyclerView.Adapter<EventiAdapter.ViewHolder> {

    private Context mContext;
    private List<Evento> mEventi;



    public EventiAdapter(Context mContext, List<Evento> eventi) {
        this.mContext = mContext;
        mEventi = eventi;
    }

    @Override
    public EventiAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_eventiadapter, parent, false);
        return new EventiAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(EventiAdapter.ViewHolder holder, int position) {
        holder.nome.setText(mEventi.get(position).getNome());
        holder.data.setText(mEventi.get(position).getData());
        holder.oraInizio.setText(mEventi.get(position).getOraInizio());
        //holder.oraFine.setText(mEventi.get(position).getOraFine());
        //holder.descrizione.setText(mEventi.get(position).getDescrizione());
        Glide.with(mContext).load(mEventi.get(position).getImg()).into(holder.img);
    }

    @Override
    public int getItemCount() {
        return mEventi.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView nome, data, oraInizio, oraFine, descrizione;
        ImageView img;

        public ViewHolder(View view) {
            super(view);

            nome = (TextView) view.findViewById(R.id.customadapter_text_nome);
            data = (TextView) view.findViewById(R.id.customadapter_text_data);
            oraInizio = (TextView) view.findViewById(R.id.customadapter_text_ora_inizio);
            //oraFine = (TextView) view.findViewById(R.id.customadapter_text_ora_fine);
            //descrizione = (TextView) view.findViewById(R.id.customadapter_text_descrizione);
            img = (ImageView) view.findViewById(R.id.imamagineEvento);

            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {

            //Toast.makeText(view.getContext(), title.getText() + " | " + subtitle.getText(), Toast.LENGTH_SHORT).show();
            //Toast.makeText(view.getContext()," Ciao ", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(view.getContext(),EventiDettaglioActivity.class);
            view.getContext().startActivity(intent);
        }



    }

}
