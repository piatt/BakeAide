package com.piatt.udacity.bakeaide.view;

import android.view.View;
import android.widget.TextView;

import com.jakewharton.rxbinding2.view.RxView;
import com.piatt.udacity.bakeaide.R;
import com.piatt.udacity.bakeaide.model.Step;
import com.piatt.udacity.bakeaide.view.StepsAdapter.StepViewHolder;

import java.util.List;

import butterknife.BindView;

public class StepsAdapter extends BaseAdapter<Step, StepViewHolder> {
    public StepsAdapter(List<Step> steps, OnItemClickListener<Step> listener) {
        super(R.layout.step_item_layout, steps, listener);
    }

    @Override
    protected StepViewHolder getViewHolder(View view) {
        return new StepViewHolder(view);
    }

    public class StepViewHolder extends BaseViewHolder<Step> {
        @BindView(R.id.step_view) TextView stepView;

        public StepViewHolder(View itemView) {
            super(itemView);

            RxView.clicks(itemView).subscribe(click -> {
                Step step = getItem(getAdapterPosition());
                getOnItemClickListener().onItemClick(step);
            });
        }

        @Override
        protected void onBind(Step step) {
            stepView.setText(step.getShortDescription());
        }
    }
}