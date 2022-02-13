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
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import myapptranslate1.my.Adapter.ListPageAdapter;
import myapptranslate1.my.Class.Page;
import myapptranslate1.my.MainActivity;
import myapptranslate1.my.R;
import myapptranslate1.my.database.UserDatabase;

import java.util.ArrayList;
import java.util.List;


public class ListPageFragment extends Fragment {


    public static final String TAG = ListPageFragment.class.getName();
    private MainActivity mainActivity;
    public View view;
    private int idbook = 0;
    private int sizebook = 0;
    private int lastpage = 0;



    public ListPageFragment() {
        // Required empty public constructor
    }

    @Override
    public void onResume() {
        super.onResume();
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_list_page, container, false);
        ImageButton btnback = view.findViewById(R.id.btn_back_action_bar);
        TextView txTitile = view.findViewById(R.id.tx_title_action_bar);
        txTitile.setText(R.string.title_list_page_action_bar);
        mainActivity = (MainActivity) getActivity();
        RecyclerView rcv = view.findViewById(R.id.rcv_list_page);

        set_Size_and_Id_Book();

        ListPageAdapter listPageAdapter = new ListPageAdapter(getListPage(sizebook), this::gotoUserLang);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(mainActivity, 2);
        rcv.setLayoutManager(gridLayoutManager);
        rcv.setAdapter(listPageAdapter);


        btnback.setOnClickListener(v -> {
            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
            if (fragmentManager.getBackStackEntryCount() > 0){
                fragmentManager.popBackStack();
            }
        });

        return view;
    }

    private List<Page> getListPage(int size){
        int numberpage = 1;
        if (size > 21){
            numberpage += size / 21 - 1 ;
            if (size % 21 != 0){
                numberpage++;
            }
        }
        List<Page> list = new ArrayList<>();
        for(int i = 1 ; i <= numberpage ; i++){
            String namepage = "Page" + i;
            list.add(new Page(namepage , i));
        }
        lastpage = numberpage;
        return list;
    }

    private void set_Size_and_Id_Book(){
        Bundle bundle = getArguments();
        if (bundle != null){
            idbook = bundle.getInt("Id Book");
            sizebook =  getSizeBook(idbook);
        }
    }



    private void gotoUserLang(Page page){
        UserLangFragment userLangFragment = new UserLangFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("Id book" , idbook);
        bundle.putInt("Page number" , page.getNumber());
        bundle.putInt("Last page" , lastpage);
        userLangFragment.setArguments(bundle);
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction().setCustomAnimations(
                R.anim.slide_in_fragment,
                R.anim.fade_out_fragment,
                R.anim.fade_in_fragment,
                R.anim.slide_out_fragment
        );
        fragmentTransaction.replace(R.id.layout_all_main_activity , userLangFragment);
        fragmentTransaction.addToBackStack(ListPageFragment.TAG);
        fragmentTransaction.commit();
    }

    private int getSizeBook(int id){
        int size;
        switch (id){
            case 1:
                size = UserDatabase.getInstance(mainActivity).userDao().getSizeEnglishBook();
                return size;
            case 2:
                size = UserDatabase.getInstance(mainActivity).userDao().getSizeBelarusBook();
                return size;
            case 3:
                size = UserDatabase.getInstance(mainActivity).userDao().getSizeGermanyBook();
                return size;
            case 4:
                size = UserDatabase.getInstance(mainActivity).userDao().getSizeFranceBook();
                return size;
            case 5:
                size = UserDatabase.getInstance(mainActivity).userDao().getSizeRussianBook();
                return size;
            case 6:
                size = UserDatabase.getInstance(mainActivity).userDao().getSizePolandBook();
                return size;
        }
        return 0;
    }






}