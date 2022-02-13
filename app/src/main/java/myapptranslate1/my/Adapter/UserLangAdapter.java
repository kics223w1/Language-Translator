package myapptranslate1.my.Adapter;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.chauthai.swipereveallayout.SwipeRevealLayout;
import com.chauthai.swipereveallayout.ViewBinderHelper;

import myapptranslate1.my.Class.UserLang;
import myapptranslate1.my.R;

import java.util.List;

import my_interface.IClickDeleteUserLangListener;
import my_interface.IClickEditUserLangListener;

public class UserLangAdapter extends RecyclerView.Adapter<UserLangAdapter.UserViewHolder> {

    private List<UserLang> listuser;
    private ViewBinderHelper viewBinderHelper = new ViewBinderHelper();
    private IClickEditUserLangListener iClickEditUserLangListener;
    private IClickDeleteUserLangListener iClickDeleteUserLangListener;


    public UserLangAdapter(IClickEditUserLangListener listen1,
                           IClickDeleteUserLangListener listen2) {
        this.iClickEditUserLangListener = listen1;
        this.iClickDeleteUserLangListener = listen2;
    }

    public void setData(List<UserLang> list) {
        this.listuser = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_user_language, parent, false);
        return new UserViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {
        UserLang userLang = listuser.get(position);
        if (userLang == null) {
            return;
        }
        viewBinderHelper.bind(holder.swipeRevealLayout, String.valueOf(userLang.getId()));
        viewBinderHelper.setOpenOnlyOne(true);
        holder.tvUserR.setText(userLang.getUsernameR());
        holder.tvUserL.setText(userLang.getUsernameL());


        if (holder.swipeRevealLayout.isOpened()){
            holder.swipeRevealLayout.close(true);
        }

        holder.tvEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iClickEditUserLangListener.onClickEditUserLang(userLang);
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        holder.swipeRevealLayout.close(true);
                    }
                }, 2000);
            }
        });
        holder.tvDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listuser.remove(holder.getBindingAdapterPosition());
                notifyItemRemoved(holder.getBindingAdapterPosition());
                iClickDeleteUserLangListener.onClickDeleteUserLang(userLang);
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        holder.swipeRevealLayout.close(true);
                    }
                }, 2000);
            }
        });
    }

    @Override
    public int getItemCount() {
        if (listuser != null) {
            return listuser.size();
        }
        return 0;
    }

    public static class UserViewHolder extends RecyclerView.ViewHolder {


        private SwipeRevealLayout swipeRevealLayout;
        private final TextView tvUserL;
        private final TextView tvUserR;
        private TextView tvEdit;
        private TextView tvDelete;

        public UserViewHolder(@NonNull View itemView) {
            super(itemView);
            tvUserL = itemView.findViewById(R.id.left);
            tvUserR = itemView.findViewById(R.id.right);
            swipeRevealLayout = itemView.findViewById(R.id.layout_swipe);
            tvEdit = itemView.findViewById(R.id.tv_edit_user_lang);
            tvDelete = itemView.findViewById(R.id.tv_delete_user_lang);
        }
    }

}
