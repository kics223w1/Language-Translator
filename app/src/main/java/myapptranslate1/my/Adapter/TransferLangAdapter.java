package myapptranslate1.my.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import myapptranslate1.my.Class.SelectTransferLang;
import myapptranslate1.my.R;

import java.util.List;

import my_interface.IClickItemSelectLangListener;

public class TransferLangAdapter extends RecyclerView.Adapter<TransferLangAdapter.LanguagesSelectViewHolder> {

    private final List<SelectTransferLang> mListSelect;
    private final IClickItemSelectLangListener iClickItemSelectLangListener;

    public TransferLangAdapter(List<SelectTransferLang> list , IClickItemSelectLangListener listener) {
        this.mListSelect = list;
        this.iClickItemSelectLangListener = listener;
    }


    @NonNull
    @Override
    public LanguagesSelectViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_select_languages_user , parent , false);
        return new LanguagesSelectViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LanguagesSelectViewHolder holder, int position) {
        SelectTransferLang selectTransferLang = mListSelect.get(position);
        if (selectTransferLang == null){
            return;
        }
        holder.tvSelectLanguages.setText(selectTransferLang.getName());
        holder.layoutselectlanguages.setOnClickListener(v ->
                iClickItemSelectLangListener.onClickSelectLanguages(selectTransferLang));
    }

    @Override
    public int getItemCount() {
        if (!mListSelect.isEmpty()){
            return mListSelect.size();
        }
        return 0;
    }

    public static class LanguagesSelectViewHolder extends RecyclerView.ViewHolder{

        private final TextView  tvSelectLanguages;
        private final CardView layoutselectlanguages;

        public LanguagesSelectViewHolder(@NonNull View itemView) {
            super(itemView);

            tvSelectLanguages = itemView.findViewById(R.id.select_languages_name);
            layoutselectlanguages = itemView.findViewById(R.id.layout_select_languages);
        }
    }
}
