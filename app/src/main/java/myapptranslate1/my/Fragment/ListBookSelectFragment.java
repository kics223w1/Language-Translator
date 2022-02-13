package myapptranslate1.my.Fragment;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import myapptranslate1.my.Adapter.ListBookSelectAdapter;
import myapptranslate1.my.Class.BookSelect;
import myapptranslate1.my.MainActivity;
import myapptranslate1.my.R;
import myapptranslate1.my.database.UserDatabase;

import java.util.List;


public class ListBookSelectFragment extends Fragment {

    public static final String TAG = ListBookSelectFragment.class.getName();
    public View view;
    private MainActivity mainActivity;

    public ListBookSelectFragment() {

    }

    private void CheckListBookSelectExist() {
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
    }

    private List<BookSelect> getListBookSelect() {
        mainActivity = (MainActivity) getActivity();
        return UserDatabase.getInstance(mainActivity).userDao().getListBookSelect();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_list_book_select, container, false);
        RecyclerView rcv = view.findViewById(R.id.rcv_book_select);
        ImageButton btnback = view.findViewById(R.id.btn_back_action_bar);
        TextView txTitile = view.findViewById(R.id.tx_title_action_bar);
        mainActivity = (MainActivity) getActivity();

        txTitile.setText(R.string.title_list_book_select_action_bar);
        CheckListBookSelectExist();

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mainActivity);
        rcv.setLayoutManager(linearLayoutManager);

        ListBookSelectAdapter listBookSelectAdapter = new ListBookSelectAdapter(getListBookSelect(), this::gotoTranslateFrag);
        rcv.setAdapter(listBookSelectAdapter);

        LayoutAnimationController layoutAnimationController = AnimationUtils.loadLayoutAnimation(mainActivity , R.anim.layout_right_to_left);
        rcv.setLayoutAnimation(layoutAnimationController);

        RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(mainActivity, DividerItemDecoration.VERTICAL);
        rcv.addItemDecoration(itemDecoration);

        btnback.setOnClickListener(v -> {
            Intent intent = new Intent(mainActivity , MainActivity.class);
            Bundle bundle = getArguments();
            if (bundle != null){
                intent.putExtra("User input text" , bundle.getString("User input"));
            }
            startActivity(intent);
            requireActivity().overridePendingTransition(R.anim.anim_enter_activity , R.anim.anim_exit_activity);
        });

        return view;
    }

    private void gotoTranslateFrag(BookSelect bookSelect){
        if (!Check_Wifi()){
            show_dialogWifi();
        }
        mainActivity = (MainActivity) getActivity();
        UserDatabase.getInstance(mainActivity).userDao().updateBookstore(bookSelect.getName());
        Intent intent = new Intent(mainActivity, MainActivity.class);
        Bundle bundle = getArguments();
        if (bundle != null){
            intent.putExtra("User input text" , bundle.getString("User input"));
        }
        startActivity(intent);
        requireActivity().overridePendingTransition(R.anim.anim_enter_activity, R.anim.anim_exit_activity);
    }

    private void showWifi(){
        Toast.makeText(mainActivity , "Pls , connect Wifi for running app" , Toast.LENGTH_SHORT).show();
    }

    private Boolean Check_Wifi(){
        ConnectivityManager cm =
                (ConnectivityManager)mainActivity.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();
        return isConnected;
    }

    private void show_dialogWifi(){
        AlertDialog.Builder alert = new AlertDialog.Builder(mainActivity);
        View mview = getLayoutInflater().inflate(R.layout.layout_dialog_wifi, null);

        Button btn_wifi = mview.findViewById(R.id.btn_dialog_have_wifi);
        Button btn_stop_app = mview.findViewById(R.id.btn_dialog_stop_app);

        alert.setView(mview);
        AlertDialog alertDialog = alert.create();
        alertDialog.setCanceledOnTouchOutside(false);

        btn_wifi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!Check_Wifi()){
                    showWifi();
                }else{
                    alertDialog.dismiss();
                }
            }
        });

        btn_stop_app.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainActivity.moveTaskToBack(true);
                mainActivity.finish();
            }
        });
        alertDialog.show();
    }
}