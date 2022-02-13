package myapptranslate1.my.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import myapptranslate1.my.Class.Page;
import myapptranslate1.my.R;

import java.util.List;

import my_interface.IClickItemListPageListener;

public class ListPageAdapter extends RecyclerView.Adapter<ListPageAdapter.ListPageViewHolder> {

    private List<Page> listpage;
    private IClickItemListPageListener iClickItemListPageListener;

    public ListPageAdapter(List<Page> listpage, IClickItemListPageListener iClickItemListPageListener) {
        this.listpage = listpage;
        this.iClickItemListPageListener = iClickItemListPageListener;
    }


    @NonNull
    @Override
    public ListPageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_item_list_page , parent , false);
        return new ListPageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ListPageViewHolder holder, int position) {
        Page page = listpage.get(position);
        if (page == null){
            return;
        }
        holder.btnlistpage.setText(page.getName());
        holder.btnlistpage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iClickItemListPageListener.onClickItemListPage(page);
            }
        });
    }

    @Override
    public int getItemCount() {
        if (listpage != null){
            return listpage.size();
        }
        return 0;
    }

    public class ListPageViewHolder extends RecyclerView.ViewHolder{

        private Button btnlistpage;


        public ListPageViewHolder(@NonNull View itemView) {
            super(itemView);
            btnlistpage = itemView.findViewById(R.id.btn_list_page);

        }
    }

}
