package myapptranslate1.my.Fragment;

import android.app.AlertDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import myapptranslate1.my.Adapter.ListBookAdapter;
import myapptranslate1.my.Class.BookSelect;
import myapptranslate1.my.MainActivity;
import myapptranslate1.my.R;
import myapptranslate1.my.database.UserDatabase;

import java.util.List;

import my_interface.IClickItemListBooksListener;
import my_interface.IClickRenameListBooksListener;


public class ListBookUserFragment extends Fragment {

    public static final String TAG = ListBookUserFragment.class.getName();
    public View view;
    private MainActivity mainActivity;
    private ListBookAdapter listBookAdapter;
    private RecyclerView rcvListBooks;


    public ListBookUserFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_list_book, container, false);
        rcvListBooks = view.findViewById(R.id.rcv_fragment_list_book);
        TextView txTitile = view.findViewById(R.id.tx_title_action_bar);
        mainActivity = (MainActivity) getActivity();
        txTitile.setText(R.string.title_list_book_action_bar);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mainActivity);
        rcvListBooks.setLayoutManager(linearLayoutManager);

         listBookAdapter = new ListBookAdapter(
                new IClickItemListBooksListener() {
            @Override
            public void onClickItemListBooks(BookSelect bookSelect) {
                gotoListPage(bookSelect);
            }
        }, new IClickRenameListBooksListener() {
            @Override
            public void onClickRenameListBooks(BookSelect bookSelect) {
                show_dialog(bookSelect);
            }
        });
        listBookAdapter.setData(getListBook());
        rcvListBooks.setAdapter(listBookAdapter);

        RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(mainActivity , DividerItemDecoration.VERTICAL);
        rcvListBooks.addItemDecoration(itemDecoration);



        return view;
    }

    private void gotoListPage(BookSelect bookSelect){
        ListPageFragment listPageFragment = new ListPageFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("Id Book" , bookSelect.getId());
        listPageFragment.setArguments(bundle);
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction().setCustomAnimations(
                R.anim.slide_in_fragment,
                R.anim.fade_out_fragment,
                R.anim.fade_in_fragment,
                R.anim.slide_out_fragment
        );
        fragmentTransaction.replace(R.id.layout_all_main_activity , listPageFragment);
        fragmentTransaction.addToBackStack(ListPageFragment.TAG);
        fragmentTransaction.commit();
    }




    private List<BookSelect> getListBook(){
        mainActivity = (MainActivity) getActivity();
        List<BookSelect> list = UserDatabase.getInstance(mainActivity).userDao().getListBookSelect();
        if (list.size() == 0) {
            UserDatabase.getInstance(mainActivity).userDao().insertBookSelect(new BookSelect(1, "English Book"));
            UserDatabase.getInstance(mainActivity).userDao().insertBookSelect(new BookSelect(2, "Belarus Book"));
            UserDatabase.getInstance(mainActivity).userDao().insertBookSelect(new BookSelect(3, "Germany Book"));
            UserDatabase.getInstance(mainActivity).userDao().insertBookSelect(new BookSelect(4, "France Book"));
            UserDatabase.getInstance(mainActivity).userDao().insertBookSelect(new BookSelect(5, "Russian Book"));
            UserDatabase.getInstance(mainActivity).userDao().insertBookSelect(new BookSelect(6, "Poland Book"));
        }
        list = UserDatabase.getInstance(mainActivity).userDao().getListBookSelect();
        return list;
    }

    private void show(String s){
        Toast.makeText(mainActivity , s , Toast.LENGTH_SHORT).show();
    }

    private void show_dialog(BookSelect bookSelect){
        AlertDialog.Builder alert = new AlertDialog.Builder(mainActivity);
        View mview = getLayoutInflater().inflate(R.layout.layout_dialog_rename_book, null);

        EditText editText = mview.findViewById(R.id.edit_text_dialog);
        Button btn_ok = mview.findViewById(R.id.btn_dialog_rename);
        Button btn_cancel = mview.findViewById(R.id.btn_dialog_cancel);

        alert.setView(mview);
        AlertDialog alertDialog = alert.create();
        alertDialog.setCanceledOnTouchOutside(false);

        btn_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nameBook = editText.getText().toString().trim();
                if(!nameBook.equals("")){
                    List<BookSelect> list = UserDatabase.getInstance(mainActivity)
                            .userDao().isCheckNameBookEsixt(nameBook);
                    if (list.size() > 0){
                        show("Same name with other book");
                        return;
                    }
                    int idbook = bookSelect.getId();
                    UserDatabase.getInstance(mainActivity).userDao().updateNameBook(nameBook , idbook);
                    listBookAdapter.setData(getListBook());
                    rcvListBooks.setAdapter(listBookAdapter);
                    alertDialog.dismiss();
                }else{
                    show("Name book can not be empty");
                }
            }
        });

        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });
        alertDialog.show();
    }


}