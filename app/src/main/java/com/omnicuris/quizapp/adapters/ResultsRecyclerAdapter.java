package com.omnicuris.quizapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.omnicuris.quizapp.R;
import com.omnicuris.quizapp.models.ResultTO;

import java.util.List;

public class ResultsRecyclerAdapter extends RecyclerView.Adapter<ResultsRecyclerAdapter.ResultRowViewHolder> {
    private List<ResultTO> resultTOList;
    private Context context;
    private String resultStr;
    private static final String CORRECT = "correct";
    private static final String WRONG = "wrong";

    public ResultsRecyclerAdapter(List<ResultTO> resultTOList, Context context) {
        this.resultTOList = resultTOList;
        this.context = context;
    }

    @NonNull
    @Override
    public ResultRowViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.recycler_result_row, parent, false);
        return new ResultRowViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ResultRowViewHolder holder, int position) {
        resultStr = WRONG;
        if (resultTOList.get(position).getMark() == 1) {
            resultStr = CORRECT;
        }
        holder.tvQuestionResult.setText(resultTOList.get(position).getQuestionNumber() + ")    " + resultStr);
    }

    @Override
    public int getItemCount() {
        return resultTOList.size();
    }

    class ResultRowViewHolder extends RecyclerView.ViewHolder {
        private TextView tvQuestionResult;

        public ResultRowViewHolder(@NonNull View itemView) {
            super(itemView);
            tvQuestionResult = itemView.findViewById(R.id.tvQuestionResult);
        }
    }
}