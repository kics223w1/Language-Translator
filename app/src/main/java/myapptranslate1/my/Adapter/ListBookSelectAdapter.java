package myapptranslate1.my.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import myapptranslate1.my.Class.BookSelect;
import myapptranslate1.my.R;

import java.util.List;

import my_interface.IClickItemListBookSelectListener;

public class ListBookSelectAdapter extends RecyclerView.Adapter<ListBookSelectAdapter.BookSelectViewHolder> {


    private List<BookSelect> list;
    private IClickItemListBookSelectListener iClickItemListBookSelectListener;

    public ListBookSelectAdapter(List<BookSelect> list , IClickItemListBookSelectListener iClickItemListBookSelectListener){
        this.list = list;
        this.iClickItemListBookSelectListener = iClickItemListBookSelectListener;
    }

    @NonNull
    @Override
    public BookSelectViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_list_book_select , parent , false);
        return new BookSelectViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BookSelectViewHolder holder, int position) {
        BookSelect bookSelect = list.get(position);
        if (bookSelect == null){
            return;
        }
        holder.namebook.setText(bookSelect.getName());
        holder.btnchosebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iClickItemListBookSelectListener.onClickItemListBookSelect(bookSelect);
            }
        });
    }

    @Override
    public int getItemCount() {
        if (list == null){
            return 0;
        }
        return list.size();
    }

    public static class BookSelectViewHolder extends RecyclerView.ViewHolder{

        private TextView namebook ;
        private Button btnchosebook;

        public BookSelectViewHolder(@NonNull View itemView) {
            super(itemView);
            namebook = itemView.findViewById(R.id.txt_list_of_book_select);
            btnchosebook = itemView.findViewById(R.id.btn_chose_select_book);
        }
    }

}
