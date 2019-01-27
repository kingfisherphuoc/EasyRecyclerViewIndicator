package com.kingfisher.sample;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.kingfisher.easyviewindicator.AnyViewIndicator;
import com.kingfisher.easyviewindicator.GridLayoutSnapHelper;
import com.kingfisher.easyviewindicator.RecyclerViewIndicator;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.recyclerViewIndicator)
    RecyclerViewIndicator horizontalIndicator;

    @BindView(R.id.recyclerView2)
    RecyclerView recyclerView2;
    @BindView(R.id.anyViewIndicator)
    AnyViewIndicator verticalIndicator;

    @BindView(R.id.recyclerView3)
    RecyclerView recyclerView3;
    @BindView(R.id.anyViewIndicator2)
    AnyViewIndicator gridIndicator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        setupLinearRecyclerViewHorizontal();
        setupLinearRecyclerViewVertial();
        setupGridRecyclerView();
    }

    private void setupGridRecyclerView() {
        gridIndicator.setItemCount(2);
        gridIndicator.setCurrentPosition(0);
        recyclerView3.setLayoutManager(new GridLayoutManager(this, 2, RecyclerView.HORIZONTAL, false));
        recyclerView3.setAdapter(new TestAdapter());
        GridLayoutSnapHelper gridLayoutSnapHelper = new GridLayoutSnapHelper(6);
        gridLayoutSnapHelper.attachToRecyclerView(recyclerView3);


        recyclerView3.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                switch (newState) {
                    case RecyclerView.SCROLL_STATE_IDLE:
                        int position = ((LinearLayoutManager) recyclerView.getLayoutManager()).findLastVisibleItemPosition();
                        gridIndicator.setCurrentPosition((int) (Math.ceil(Double.valueOf(position)/ 6) - 1));
                        break;
                }
            }
        });
    }

    private void setupLinearRecyclerViewHorizontal() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(new TestAdapter());
        horizontalIndicator.setRecyclerView(recyclerView);
    }

    private void setupLinearRecyclerViewVertial() {
        recyclerView2.setLayoutManager(new LinearLayoutManager(this));
        recyclerView2.setAdapter(new TestAdapter());
        verticalIndicator.setItemCount(10);
        verticalIndicator.setCurrentPosition(0);

        recyclerView2.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                switch (newState) {
                    case RecyclerView.SCROLL_STATE_IDLE:
                        int position = ((LinearLayoutManager) recyclerView.getLayoutManager()).findFirstCompletelyVisibleItemPosition();
                        verticalIndicator.setCurrentPosition(position);
                        break;
                }
            }
        });
    }


    class TestAdapter extends RecyclerView.Adapter<TestAdapter.ViewHolder> {


        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item, null));
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            holder.setData();
        }

        @Override
        public int getItemCount() {
            return 10;
        }

        class ViewHolder extends RecyclerView.ViewHolder {
            @BindView(R.id.tvName)
            TextView textView;

            public ViewHolder(View itemView) {
                super(itemView);
                ButterKnife.bind(this, itemView);
            }

            public void setData() {
                textView.setText("Pos: " + (getAdapterPosition() + 1));
            }
        }
    }
}
