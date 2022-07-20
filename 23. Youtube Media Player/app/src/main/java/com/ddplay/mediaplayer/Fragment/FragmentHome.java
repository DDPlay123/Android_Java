package com.ddplay.mediaplayer.Fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import com.ddplay.mediaplayer.API.Video;
import com.ddplay.mediaplayer.Activity.VideoActivity;
import com.ddplay.mediaplayer.Adapter.VideoListAdapter;
import com.ddplay.mediaplayer.Data.ObjectData;
import com.ddplay.mediaplayer.Data.Sentence;
import com.ddplay.mediaplayer.Data.VideoDetail;
import com.ddplay.mediaplayer.Function.ConvertTime;
import com.ddplay.mediaplayer.R;
import com.google.gson.Gson;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class FragmentHome extends Fragment {
    private RecyclerView recyclerView;
    private SwipeRefreshLayout swipeRefreshLayout;
    private VideoListAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // define view
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @SuppressLint("NotifyDataSetChanged")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // main function
        recyclerView = view.findViewById(R.id.recyclerview);
        swipeRefreshLayout = view.findViewById(R.id.swipe_refresh);
        // Get Data
        getData();
        // Define
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        swipeRefreshLayout.setColorSchemeResources(R.color.blue);
        swipeRefreshLayout.setOnRefreshListener(() -> {
            getData();
            adapter.notifyDataSetChanged();
            swipeRefreshLayout.setRefreshing(false);
        });
    }

    private void getData() {
        Request request = new Video().postData();
        new OkHttpClient().newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                e.printStackTrace();
            }

            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                String json = Objects.requireNonNull(response.body()).string();
                ObjectData objectData = new Gson().fromJson(json, ObjectData.class);
                List<VideoDetail> data = new ArrayList<>();
                List<Sentence> sentence = new ArrayList<>();
                // Sentence、VideoURL、MainEditor
                for (ObjectData.Result.VideoInfo.CaptionResult.Results result : objectData.result.videoInfo.captionResult.results) {
                    int index = 0;
                    for (ObjectData.Result.VideoInfo.CaptionResult.Results.Captions caption : result.captions) {
                        index ++;
                        sentence.add(new Sentence(
                                caption.getContent(),
                                caption.getMiniSecond(),
                                index,
                                objectData.result.videoInfo.getVideourl(),
                                objectData.result.getMainEditor()
                        ));
                    }
                }

                requireActivity().runOnUiThread(() -> {
                    data.add(new VideoDetail(
                            objectData.result.videoInfo.getThumbnails(),
                            new ConvertTime().convertTime(objectData.result.videoInfo.getDuration()),
                            objectData.result.videoInfo.getTitle(),
                            objectData.result.getViewer()
                    ));
                    adapter = new VideoListAdapter(data);
                    recyclerView.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                    adapter.setOnItemClickListener(position -> {
                        Intent intent = new Intent(getActivity(), VideoActivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("sentence", (Serializable) sentence);
                        intent.putExtras(bundle);
                        startActivity(intent);
                    });
                });
            }
        });
    }
}
