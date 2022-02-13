package myapptranslate1.my.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import myapptranslate1.my.Adapter.TransferLangAdapter;
import myapptranslate1.my.Class.SelectTransferLang;
import myapptranslate1.my.Class.TransferLang;
import myapptranslate1.my.MainActivity;
import myapptranslate1.my.R;
import myapptranslate1.my.database.UserDatabase;


public class TransferLangFragment extends Fragment {

    public View view;
    private MainActivity mainActivity;


    public TransferLangFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_transfer_lang, container, false);
        RecyclerView rcv = view.findViewById(R.id.rcv_transferlang);
        TextView txTitile = view.findViewById(R.id.tx_title_action_bar);
        txTitile.setText(R.string.Select_language);
        ImageButton btnback = view.findViewById(R.id.btn_back_action_bar);
        mainActivity = (MainActivity) getActivity();


        TransferLangAdapter transferLangAdapter = new TransferLangAdapter(getListLanguagesSelect(), this::onClickputSelectlanguages);


        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mainActivity, RecyclerView.VERTICAL, false);
        rcv.setLayoutManager(linearLayoutManager);

        LayoutAnimationController layoutAnimationController = AnimationUtils.loadLayoutAnimation(mainActivity, R.anim.layout_right_to_left);
        rcv.setLayoutAnimation(layoutAnimationController);

        rcv.setAdapter(transferLangAdapter);


        btnback.setOnClickListener(v -> {
            Intent intent = new Intent(mainActivity, MainActivity.class);
            Bundle bundle = getArguments();
            if (bundle != null) {
                intent.putExtra("User input text", bundle.getString("User input"));
            }
            startActivity(intent);
            requireActivity().overridePendingTransition(R.anim.anim_enter_activity, R.anim.anim_exit_activity);
        });

        return view;
    }

    private List<SelectTransferLang> getListLanguagesSelect() {
        List<SelectTransferLang> list = new ArrayList<>();
        list.add(new SelectTransferLang("Vietnamese"));
        list.add(new SelectTransferLang("English"));
        list.add(new SelectTransferLang("Belarusian (Belarus)"));
        list.add(new SelectTransferLang("Chinese"));
        list.add(new SelectTransferLang("French"));
        list.add(new SelectTransferLang("German"));
        list.add(new SelectTransferLang("Russian"));
        list.add(new SelectTransferLang("Hindi (India)"));
        list.add(new SelectTransferLang("Portuguese"));
        list.add(new SelectTransferLang("Korean"));
        list.add(new SelectTransferLang("Japanese"));
        list.add(new SelectTransferLang("Welsh"));
        list.add(new SelectTransferLang("Czech"));
        list.add(new SelectTransferLang("Polish"));
        list.add(new SelectTransferLang("Swedish"));


        return list;
    }

    private void onClickputSelectlanguages(SelectTransferLang selectTransferLang) {
        Intent intent = new Intent(mainActivity, MainActivity.class);
        List<TransferLang> list = UserDatabase.getInstance(mainActivity).userDao().getListTransferlang();
        Bundle bundle = getArguments();
        if (bundle != null) {
            if (bundle.getString("From Left") != null) {
                list.get(0).setSelangnameL(selectTransferLang.getName());
            } else {
                list.get(0).setSelangnameR(selectTransferLang.getName());
            }
            intent.putExtra("User input text", bundle.getString("User input"));
            intent.putExtra("Reset Model", "Reset");
        }
        UserDatabase.getInstance(mainActivity).userDao().updateTransferLang(list.get(0));
        startActivity(intent);
        requireActivity().overridePendingTransition(R.anim.anim_enter_activity, R.anim.anim_exit_activity);
    }


}