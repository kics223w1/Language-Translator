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
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import myapptranslate1.my.Class.BelarusBookLang;
import myapptranslate1.my.Class.BookSelect;
import myapptranslate1.my.Class.FranceBookLang;
import myapptranslate1.my.Class.GermanyBookLang;
import myapptranslate1.my.Class.PolandBookLang;
import myapptranslate1.my.Class.RussianBookLang;
import myapptranslate1.my.Class.TransferLang;
import myapptranslate1.my.Class.UserLang;
import myapptranslate1.my.MainActivity;
import myapptranslate1.my.R;
import myapptranslate1.my.database.UserDatabase;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.mlkit.nl.translate.TranslateLanguage;
import com.google.mlkit.nl.translate.Translation;
import com.google.mlkit.nl.translate.Translator;
import com.google.mlkit.nl.translate.TranslatorOptions;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class TranslateFragment extends Fragment {

    public static final String TAG = TranslateFragment.class.getName();
    private TextView btnsetBookstore;
    private TextView btnlanguages1;
    private TextView btnlanguages2 , btntranslateWord , btnInstruction;
    private TextView textShowTranslateWord;
    private TextView textSourceTag;
    private TextView textTargetTag;
    private LinearLayout layout_progress_model;
    public View view;
    private ProgressBar progressBar_translate;
    private MainActivity mainActivity;
    private TextInputEditText txuser_input;
    public int idBook = 0;
    private boolean process_Model = true;
    public  Translator translatorlang;
    private final List<String> textTranslateFull = new ArrayList<>();






    private void CheckTransferLang_and_Bookstore() {
        List<TransferLang> list22 = UserDatabase.getInstance(mainActivity).userDao().getListTransferlang();
        if (list22.size() == 0) {
            TransferLang transferLang = new TransferLang("English", "Vietnamese", "Chose book to store");
            UserDatabase.getInstance(mainActivity).userDao().insertTransferLang(transferLang);
        }
        list22 = UserDatabase.getInstance(mainActivity).userDao().getListTransferlang();
        String sourceTag = list22.get(0).getSelangnameL();
        String targetTag = list22.get(0).getSelangnameR();

        btnlanguages1.setText(sourceTag);
        btnlanguages2.setText(targetTag);
        btnsetBookstore.setText(list22.get(0).getBookstore());

        textSourceTag.setText(sourceTag);
        textTargetTag.setText(targetTag);
    }

    private void hideKeyboard() {
        InputMethodManager inputMethodManager = (InputMethodManager) requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(requireView().getWindowToken(), 0);
    }

    private void Set_all_things() {
        CheckTransferLang_and_Bookstore();
        String sourceTag = getTagLang(btnlanguages1.getText().toString());
        String targetTag = getTagLang(btnlanguages2.getText().toString());
        prepareModel(sourceTag, targetTag);
    }


    private void translateLanguage(String text, boolean ok) {
        translatorlang.translate(text).addOnSuccessListener(new OnSuccessListener<String>() {
            @Override
            public void onSuccess(@NonNull String s) {
                textTranslateFull.add(s);
                if (ok) {
                    showTranslate();
                } else {
                    insertLang(s);
                }
            }

            public void showTranslate() {
                textShowTranslateWord.setVisibility(View.VISIBLE);
                textTargetTag.setVisibility(View.VISIBLE);
                StringBuilder full = new StringBuilder();
                for (String s : textTranslateFull) {
                    full.append(s);
                    full.append("..");
                }
                textShowTranslateWord.setText(full.toString());
                progressBar_translate.setVisibility(View.INVISIBLE);
                btntranslateWord.setText(R.string.Translate);
            }

            public void insertLang(String textTranslate) {
                inserUserLangtoBook(idBook, text, textTranslate);
            }
        }).addOnFailureListener(e -> Toast.makeText(mainActivity, "Can't translate this word", Toast.LENGTH_SHORT).show());
    }



    private void prepareModel(String sourceTag, String targetTag) {
        layout_progress_model.setVisibility(View.VISIBLE);
        Boolean okWifi = true;
        if (!Check_Wifi()){
            show_dialogWifi();
            okWifi = false;
        }
        if (Check_Wifi() && !okWifi){
            show("Ok , go");
        }
        process_Model = false;
        TranslatorOptions options = new TranslatorOptions.Builder()
                .setSourceLanguage(TranslateLanguage.fromLanguageTag(sourceTag))
                .setTargetLanguage(TranslateLanguage.fromLanguageTag(targetTag))
                .build();
        translatorlang = Translation.getClient(options);

        translatorlang.downloadModelIfNeeded().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                process_Model = true;
                layout_progress_model.setVisibility(View.INVISIBLE);

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                show("Pls , reset app some thing went wrong");
            }
        });
    }

    public TranslateFragment() {

    }




    @Override
    public void onResume() {
        super.onResume();
        if(idBook != 0){
            String name = UserDatabase.getInstance(mainActivity)
                    .userDao().setNameBookSelect(idBook).getName();
            btnsetBookstore.setText(name);
        }else{
            String name = btnsetBookstore.getText().toString();
            if (!name.equals("Chose book to store")){
                setIdbook(name);
            }
        }
        Intent intent = requireActivity().getIntent();
        if (intent != null) {
            if (intent.getStringExtra("User input text") != null) {
                String text = intent.getStringExtra("User input text");
                txuser_input.setText(text);
            }
            if (intent.getStringExtra("Reset Model") != null) {
                String sourceTag = getTagLang(btnlanguages1.getText().toString());
                String targetTag = getTagLang(btnlanguages2.getText().toString());
                prepareModel(sourceTag , targetTag);
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_translate, container, true);
        btnlanguages1 = view.findViewById(R.id.languages1);
        btnlanguages2 = view.findViewById(R.id.languages2);
        TextView btnStoreLang = view.findViewById(R.id.btn_store_word);
        TextInputLayout textInputLayout = view.findViewById(R.id.layout_text_input);
        txuser_input = view.findViewById(R.id.text_user_input);
        btnsetBookstore = view.findViewById(R.id.btn_set_book_store);
        btntranslateWord = view.findViewById(R.id.btn_translate_word);
        textShowTranslateWord = view.findViewById(R.id.show_word_translate);
        textSourceTag = view.findViewById(R.id.tv_sourceTag);
        textTargetTag = view.findViewById(R.id.tv_targetTag);
        btnInstruction = view.findViewById(R.id.btn_instruction);
        layout_progress_model = view.findViewById(R.id.progress_model);
        ImageButton swap = view.findViewById(R.id.swap);
        progressBar_translate = view.findViewById(R.id.progress_translate);
        mainActivity = (MainActivity) getActivity();



        Set_all_things();

        btnInstruction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InstructionFragment frag = new InstructionFragment();
                FragmentManager fm = requireActivity().getSupportFragmentManager();
                FragmentTransaction fr = fm.beginTransaction().setCustomAnimations(
                        R.anim.slide_in_fragment,
                        R.anim.fade_out_fragment,
                        R.anim.fade_in_fragment,
                        R.anim.slide_out_fragment
                );
                fr.replace(R.id.layout_all_main_activity , frag);
                fr.addToBackStack(InstructionFragment.TAG);
                fr.commit();
            }
        });

        textInputLayout.setEndIconOnClickListener(v -> {
            txuser_input.setText("");
            textShowTranslateWord.setText("");
            textShowTranslateWord.setVisibility(View.INVISIBLE);
            textTargetTag.setVisibility(View.INVISIBLE);
        });

        btnsetBookstore.setOnClickListener(v -> {
            hideKeyboard();
            Bundle bd = new Bundle();
            if (!Objects.requireNonNull(txuser_input.getText()).toString().equals("")) {
                bd.putString("User input", txuser_input.getText().toString());
                // Keep user input
            }
            ListBookSelectFragment listBookSelectFragment = new ListBookSelectFragment();
            FragmentManager fm = requireActivity().getSupportFragmentManager();
            FragmentTransaction fr = fm.beginTransaction().setCustomAnimations(
                    R.anim.slide_in_fragment,
                    R.anim.fade_out_fragment,
                    R.anim.fade_in_fragment,
                    R.anim.slide_out_fragment
            );
            listBookSelectFragment.setArguments(bd);
            fr.replace(R.id.layout_all_main_activity, listBookSelectFragment);
            fr.addToBackStack(ListBookSelectFragment.TAG);
            fr.commit();
        });


        btntranslateWord.setOnClickListener(v -> {
            hideKeyboard();
            if (!Check_Wifi()){
                show_dialogWifi();
            }
            if (!process_Model){
                Toast.makeText(mainActivity , "Hmm, take a minute for setting language " +
                        "at the first time ", Toast.LENGTH_LONG).show();
                return;
            }
            if (btntranslateWord.getText().toString().equals("Translating")){
                show("Wait wait , we are translating");
                return;
            }
            String text = Objects.requireNonNull(txuser_input.getText()).toString().trim();
            if (!text.equals("")) {
                textTranslateFull.clear();
                progressBar_translate.setVisibility(View.VISIBLE);
                btntranslateWord.setText(R.string.Translating);
                List<String> textnotTrans = makeText(text);
                for (String s : textnotTrans) {
                    translateLanguage(s, true);
                }
            } else {
                Toast.makeText(mainActivity, "You didn't type (^^)....", Toast.LENGTH_SHORT).show();
            }
        });



        btnStoreLang.setOnClickListener(v -> {
            hideKeyboard();
            if (!Check_Wifi()){
                show_dialogWifi();
            }
            if (!process_Model){
                Toast.makeText(mainActivity , "Hmm, take a minute for setting language " +
                        "in the first time ", Toast.LENGTH_LONG).show();
                return;
            }
            String nameBookStore = btnsetBookstore.getText().toString();
            if (nameBookStore.equals("Chose book to store")) {
                show("Pls select book to add");
                return;
            }
            BookSelect bookSelect = UserDatabase.getInstance(mainActivity)
                    .userDao().setIdbookSelect(nameBookStore);
            if (bookSelect == null){
                show("Seem like that book doesn't exit , pls select book again");
                return;
            }

            String text = Objects.requireNonNull(txuser_input.getText()).toString().trim();
            txuser_input.setText("");
            textShowTranslateWord.setText("");
            textShowTranslateWord.setVisibility(View.INVISIBLE);
            textTargetTag.setVisibility(View.INVISIBLE);
            StoreWords(text);
        });

        btnlanguages1.setOnClickListener(v -> {
            hideKeyboard();
            Bundle bd = new Bundle();
            bd.putString("From Left", "1");
            if (!Objects.requireNonNull(txuser_input.getText()).toString().equals("")) {
                bd.putString("User input", txuser_input.getText().toString());
                // Keep user input
            }
            TransferLangFragment transferLangFragment = new TransferLangFragment();
            FragmentManager fm = requireActivity().getSupportFragmentManager();
            FragmentTransaction fr = fm.beginTransaction().setCustomAnimations(
                    R.anim.slide_in_fragment,
                    R.anim.fade_out_fragment,
                    R.anim.fade_in_fragment,
                    R.anim.slide_out_fragment
            );
            transferLangFragment.setArguments(bd);
            fr.replace(R.id.layout_all_main_activity, transferLangFragment);
            fr.addToBackStack(TranslateFragment.TAG);
            fr.commit();
        });

        btnlanguages2.setOnClickListener(v -> {
            hideKeyboard();
            Bundle bd = new Bundle();
            bd.putString("From Right", "2");
            if (!Objects.requireNonNull(txuser_input.getText()).toString().equals("")) {
                bd.putString("User input", txuser_input.getText().toString());
                // Keep user input
            }
            TransferLangFragment transferLangFragment = new TransferLangFragment();
            FragmentManager fm = requireActivity().getSupportFragmentManager();
            FragmentTransaction fr = fm.beginTransaction().setCustomAnimations(
                    R.anim.slide_in_fragment,
                    R.anim.fade_out_fragment,
                    R.anim.fade_in_fragment,
                    R.anim.slide_out_fragment
            );
            transferLangFragment.setArguments(bd);
            fr.replace(R.id.layout_all_main_activity, transferLangFragment);
            fr.addToBackStack(TranslateFragment.TAG);
            fr.commit();
        });

        textSourceTag.setOnClickListener(v -> {
            hideKeyboard();
            Bundle bd = new Bundle();
            bd.putString("From Left", "1");
            if (!Objects.requireNonNull(txuser_input.getText()).toString().equals("")) {
                bd.putString("User input", txuser_input.getText().toString());
                // Keep user input
            }
            TransferLangFragment transferLangFragment = new TransferLangFragment();
            FragmentManager fm = requireActivity().getSupportFragmentManager();
            FragmentTransaction fr = fm.beginTransaction().setCustomAnimations(
                    R.anim.slide_in_fragment,
                    R.anim.fade_out_fragment,
                    R.anim.fade_in_fragment,
                    R.anim.slide_out_fragment
            );
            transferLangFragment.setArguments(bd);
            fr.replace(R.id.layout_all_main_activity, transferLangFragment);
            fr.addToBackStack(TranslateFragment.TAG);
            fr.commit();
        });

        textTargetTag.setOnClickListener(v -> {
            hideKeyboard();
            Bundle bd = new Bundle();
            bd.putString("From Right", "2");
            if (!Objects.requireNonNull(txuser_input.getText()).toString().equals("")) {
                bd.putString("User input", txuser_input.getText().toString());
                // Keep user input
            }
            TransferLangFragment transferLangFragment = new TransferLangFragment();
            FragmentManager fm = requireActivity().getSupportFragmentManager();
            FragmentTransaction fr = fm.beginTransaction().setCustomAnimations(
                    R.anim.slide_in_fragment,
                    R.anim.fade_out_fragment,
                    R.anim.fade_in_fragment,
                    R.anim.slide_out_fragment
            );
            transferLangFragment.setArguments(bd);
            fr.replace(R.id.layout_all_main_activity, transferLangFragment);
            fr.addToBackStack(TranslateFragment.TAG);
            fr.commit();
        });

        swap.setOnClickListener(v -> {
            hideKeyboard();
            List<TransferLang> listswap = UserDatabase.getInstance(mainActivity).userDao().getListTransferlang();
            String nameL = listswap.get(0).getSelangnameL();
            String nameR = listswap.get(0).getSelangnameR();
            listswap.get(0).setSelangnameL(nameR);
            listswap.get(0).setSelangnameR(nameL);
            UserDatabase.getInstance(mainActivity).userDao().updateTransferLang(listswap.get(0));
            List<TransferLang> listswap2 = UserDatabase.getInstance(mainActivity).userDao().getListTransferlang();
            String name1 = listswap2.get(0).getSelangnameL();
            String name2 = listswap2.get(0).getSelangnameR();

            Animation animation = AnimationUtils.loadAnimation(mainActivity, R.anim.swap_roate);
            swap.startAnimation(animation);
            btnlanguages1.setText(name1);
            btnlanguages2.setText(name2);

            String sourceTag = getTagLang(name1);
            String targetTag = getTagLang(name2);

            prepareModel(sourceTag , targetTag);

            textSourceTag.setText(name1);
            textTargetTag.setText(name2);

            String textUp = Objects.requireNonNull(txuser_input.getText()).toString();
            String textDown = textShowTranslateWord.getText().toString();

            txuser_input.setText(textDown);
            textShowTranslateWord.setText(textUp);
        });

        return view;
    }


    private List<String> makeText(String text) {
        //This function will not work with case
        //text = "One.Number t^$^$%^&$wo."
        //But I'dont think user will wirte like this
        String[] word = text.split("[.]");
        List<String> ans = new ArrayList<>();
        for (String s : word) {
            String ss = s.replaceAll("[\\d.]", "");
            if (!ss.equals("")){
                ans.add(ss);
            }
        }
        return ans;
    }




    private String getTagLang(String s) {
        String ss = "";
        switch (s) {
            case "Vietnamese":
                ss = "vi";
                break;
            case "English":
                ss = "en";
                break;
            case "Belarusian (Belarus)":
                ss = "be";
                break;
            case "French":
                ss = "fr";
                break;
            case "German":
                ss = "de";
                break;
            case "Russian":
                ss = "ru";
                break;
            case "Hindi (India)":
                ss = "hi";
                break;
            case "Portuguese":
                ss = "pt";
                break;
            case "Spanish":
                ss = "es";
                break;
            case "Korean":
                ss = "ko";
                break;
            case "Thai (Thailand)":
                ss = "th";
                break;
            case "Chinese":
                ss = "zh";
                break;
            case "Japanese":
                ss = "ja";
                break;
            case "Welsh":
                ss = "cy";
                break;
            case "Czech":
                ss = "cs";
                break;
            case "Polish":
                ss = "pl";
                break;
            case "Swedish":
                ss = "sv";
                break;
        }
        return ss;
    }


    private void StoreWords(String text) {
        setIdbook(btnsetBookstore.getText().toString());
        textTranslateFull.clear();
        List<String> textx = makeText(text);
        List<String> toremove = new ArrayList<>();
        StringBuilder showEsixt = new StringBuilder();
        for (String s : textx) {
            if (isCheckUserLangEsixt(s, idBook)) {
                toremove.add(s);
                showEsixt.append(s);
                showEsixt.append("  ");
            }
        }
        if (toremove.size() > 0) {
            textx.removeAll(toremove);
        }
        for (String s : textx) {
            translateLanguage(s, false);
        }
    }

    private Boolean isCheckUserLangEsixt(String nameL, int idBook) {
        List<UserLang> list;
        switch (idBook) {
            case 1:
                list = UserDatabase.getInstance(mainActivity).userDao().checkEnglishLangExist(nameL);
                return list.size() > 0;
            case 2:
                list = UserDatabase.getInstance(mainActivity).userDao().checkBelarusLangExist(nameL);
                return list.size() > 0;
            case 3:
                list = UserDatabase.getInstance(mainActivity).userDao().checkGermanyLangExist(nameL);
                return list.size() > 0;
            case 4:
                list = UserDatabase.getInstance(mainActivity).userDao().checkFranceLangExist(nameL);
                return list.size() > 0;
            case 5:
                list = UserDatabase.getInstance(mainActivity).userDao().checkRussianLangExist(nameL);
                return list.size() > 0;
            case 6:
                list = UserDatabase.getInstance(mainActivity).userDao().checkPolandLangExist(nameL);
                return list.size() > 0;
        }
        return true;
    }

    private void inserUserLangtoBook(int id, String nameL, String nameR) {
        switch (id) {
            case 1:
                UserDatabase.getInstance(mainActivity).userDao().insertUserEnglish(new UserLang(nameL, nameR));
                break;
            case 2:
                UserDatabase.getInstance(mainActivity).userDao().insertUserBelarus(new BelarusBookLang(nameL, nameR));
                break;
            case 3:
                UserDatabase.getInstance(mainActivity).userDao().insertUserGermanty(new GermanyBookLang(nameL, nameR));
                break;
            case 4:
                UserDatabase.getInstance(mainActivity).userDao().insertUserFrance(new FranceBookLang(nameL, nameR));
                break;
            case 5:
                UserDatabase.getInstance(mainActivity).userDao().insertUserRussian(new RussianBookLang(nameL, nameR));
                break;
            case 6:
                UserDatabase.getInstance(mainActivity).userDao().insertUserPoland(new PolandBookLang(nameL, nameR));
                break;
        }
    }
    private void show(String s) {
        Toast.makeText(mainActivity, s, Toast.LENGTH_SHORT).show();
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

    private void setIdbook(String namebook) {
        if (UserDatabase.getInstance(mainActivity).userDao().setIdbookSelect(namebook) == null){
            idBook = 0;
            return;
        }
        idBook = UserDatabase.getInstance(mainActivity).userDao().setIdbookSelect(namebook).getId();
    }



}