package com.kingfisher.sample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.kingfisher.easyviewindicator.AnyViewIndicator;
import com.kingfisher.easyviewindicator.RecyclerViewIndicator;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @BindView (R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView (R.id.circleIndicator)
    RecyclerViewIndicator recyclerViewIndicator;

    @BindView (R.id.recyclerView2)
    RecyclerView recyclerView2;
    @BindView (R.id.anyViewIndicator)
    AnyViewIndicator verticalIndicator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(new TestAdapter());
        recyclerViewIndicator.setRecyclerView(recyclerView);

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
            @BindView (R.id.tvName)
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
