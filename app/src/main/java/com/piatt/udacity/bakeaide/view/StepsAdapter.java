package com.piatt.udacity.bakeaide.view;

import android.view.View;
import android.widget.TextView;

import com.piatt.udacity.bakeaide.R;
import com.piatt.udacity.bakeaide.model.Step;
import com.piatt.udacity.bakeaide.view.StepsAdapter.StepViewHolder;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class StepsAdapter extends BaseAdapter<Step, StepViewHolder> {
    private OnStepClickListener listener;

    public StepsAdapter(List<Step> items, OnStepClickListener listener) {
        super(R.layout.step_item_layout, items);
        this.listener = listener;
    }

    @Override
    protected StepViewHolder getViewHolder(View view) {
        return new StepViewHolder(view);
    }

    public interface OnStepClickListener {
        void onStepClick(Step step);
    }

    public class StepViewHolder extends BaseViewHolder<Step> {
        @BindView(R.id.step_name_view) TextView stepNameView;

        public StepViewHolder(View itemView) {
            super(itemView);
        }

        @Override
        public void onBind(Step item) {
            stepNameView.setText(item.getShortDescription());
        }

        @OnClick(R.id.step_name_view)
        public void onStepNameViewClick() {
            Step step = getItem(getAdapterPosition());
            listener.onStepClick(step);
        }
    }
}