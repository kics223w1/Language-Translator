package myapptranslate1.my.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import myapptranslate1.my.Class.BookSelect;
import myapptranslate1.my.R;

import java.util.List;

import my_interface.IClickItemListBooksListener;
import my_interface.IClickRenameListBooksListener;

public class ListBookAdapter extends RecyclerView.Adapter<ListBookAdapter.BookSelectViewHolder> {

    private List<BookSelect> list;
    private IClickItemListBooksListener iClickItemListBooksListener;
    private IClickRenameListBooksListener iClickRenameListBooksListener;

    public ListBookAdapter( IClickItemListBooksListener iClickItemListBooksListener
                                , IClickRenameListBooksListener iClickRenameListBooksListener) {
        this.iClickItemListBooksListener = iClickItemListBooksListener;
        this.iClickRenameListBooksListener = iClickRenameListBooksListener;
    }

    public void setData(List<BookSelect> list){
        this.list = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public BookSelectViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_list_books , parent , false);
        return new BookSelectViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BookSelectViewHolder holder, int position) {
        BookSelect bookSelect = list.get(position);
        if (bookSelect == null){
            return;
        }
        holder.txname.setText(bookSelect.getName());
        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iClickItemListBooksListener.onClickItemListBooks(bookSelect);
            }
        });
        holder.btn_rename.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iClickRenameListBooksListener.onClickRenameListBooks(bookSelect);
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

        private  TextView txname;
        private  CardView layout;
        private Button btn_rename;

        public BookSelectViewHolder(@NonNull View itemView) {
            super(itemView);
            txname = itemView.findViewById(R.id.txt_list_of_books);
            layout = itemView.findViewById(R.id.layout_list_books);
            btn_rename = itemView.findViewById(R.id.btn_rename_book);
        }
    }

}
