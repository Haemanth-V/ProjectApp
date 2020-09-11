package com.example.projectapp.menu;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.projectapp.R;
import com.example.projectapp.adapter.ChatListAdapter;
import com.example.projectapp.model.ChatItem;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ChatsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ChatsFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ChatsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ChatsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ChatsFragment newInstance(String param1, String param2) {
        ChatsFragment fragment = new ChatsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    private List<ChatItem> chat = new ArrayList<>();
    private RecyclerView recyclerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_chats, container, false);
        recyclerView = view.findViewById(R.id.chatRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        getChat();
        return view;
    }

    private void getChat(){
        chat.add(new ChatItem("1","Haemanth", "Hello!",
                "10/09/2020", "https://m.hindustantimes.com/rf/image_size_1200x900/HT/p2/2019/12/03/Pictures/_2953cc14-15b0-11ea-ba57-c3a9d68be36c.PNG"));
        chat.add(new ChatItem("3","Pragati", "Hey there!",
                "10/10/2020", "https://www.gstatic.com/tv/thumb/persons/169721/169721_v9_bb.jpg"));
        chat.add(new ChatItem("2","Sai", "Thanks!",
                "02/09/2020", "https://static.wikia.nocookie.net/tvshadowhunters/images/d/d1/TMI204promo_Alec01.jpg/revision/latest/top-crop/width/360/height/450?cb=20171009125719"));
        recyclerView.setAdapter(new ChatListAdapter(chat,getContext()));

    }
}