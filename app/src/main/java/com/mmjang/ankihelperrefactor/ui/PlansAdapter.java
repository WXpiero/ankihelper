package com.mmjang.ankihelperrefactor.ui;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.test.ActivityTestCase;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.mmjang.ankihelperrefactor.R;
import com.mmjang.ankihelperrefactor.app.MyApplication;
import com.mmjang.ankihelperrefactor.app.OutputPlan;
import java.util.List;

/**
 * Created by liao on 2017/4/27.
 */

public class PlansAdapter extends RecyclerView.Adapter<PlansAdapter.ViewHolder> {
    private List<OutputPlan> mPlansList;
    private Activity mActivity;

    static class ViewHolder extends RecyclerView.ViewHolder{
        TextView planName;
        Button btnDelete;
        Button btnEdit;

        public ViewHolder(View view){
            super(view);
            planName = (TextView) view.findViewById(R.id.plans_name);
            btnDelete = (Button) view.findViewById(R.id.btn_delete);
            btnEdit = (Button) view.findViewById(R.id.btn_edit);
        }
    }

    public PlansAdapter(
            Activity activity,
            List<OutputPlan> planList){
        mPlansList = planList;
        mActivity = activity;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.plans_item, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position){
        OutputPlan plan = mPlansList.get(position);
        holder.planName.setText(plan.getPlanName());
        holder.btnDelete.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int pos = holder.getAdapterPosition();
                        mPlansList.get(pos).delete();
                        mPlansList.remove(pos);
                        notifyItemRemoved(pos);
                    }
                }
        );
        holder.btnEdit.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int pos = holder.getAdapterPosition();
                        String planName = mPlansList.get(pos).getPlanName();
                        Intent intent = new Intent(MyApplication.getContext(), PlanEditorActivity.class);
                        intent.setAction(Intent.ACTION_SEND);
                        intent.putExtra(Intent.EXTRA_TEXT, planName);
                        MyApplication.getContext().startActivity(intent);
                    }
                }
        );

    }

    @Override
    public int getItemCount(){
        return mPlansList.size();
    }
}
