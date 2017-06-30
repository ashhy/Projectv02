package com.example.heman.projectv02.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.heman.projectv02.SurveyComponents.Question;
import com.example.heman.projectv02.R;

import java.util.ArrayList;
import java.util.List;


public class QuestionAdapter extends ArrayAdapter<Question> {
    private static final String DOT=". ";
    private OnOptionSelect mOnOptionSelect;

    public QuestionAdapter(@NonNull Context context, ArrayList<Question> questionList, OnOptionSelect mOnOptionSelect) {
        super(context, R.layout.question_list_element,questionList);
        this.mOnOptionSelect=mOnOptionSelect;
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        final Question question=getItem(position);
        Context context=getContext();
        final ViewHolder viewHolder=new ViewHolder();
        convertView = LayoutInflater.from(context).inflate(R.layout.question_list_element, parent, false);
        final View finalView=convertView;
        viewHolder.qleLL=convertView.findViewById(R.id.qleLL);
        List<String> optionArray=question.getOptionArray();
        int length=optionArray.size();
        viewHolder.qText = convertView.findViewById(R.id.qleQText);
        viewHolder.qText.setText(String.valueOf(question.getqNo())+DOT+question.getqText());
        int padding_in_dp = (int) context.getResources().getDimension(R.dimen.qleOTextPadding);
        float textSize = context.getResources().getDimensionPixelSize(R.dimen.qleOTextSize) / context.getResources().getDisplayMetrics().scaledDensity;
        if(question.isMultiSelect()){
            viewHolder.checkBoxes=new CheckBox[length];
            for(int i=0;i<length;i++){
                viewHolder.checkBoxes[i]=new CheckBox(context);
                viewHolder.checkBoxes[i].setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                viewHolder.checkBoxes[i].setText(optionArray.get(i));
                viewHolder.checkBoxes[i].setPadding(padding_in_dp, padding_in_dp, padding_in_dp, padding_in_dp);
                viewHolder.checkBoxes[i].setTextSize(textSize);
                viewHolder.checkBoxes[i].setTag(i);
                viewHolder.checkBoxes[i].setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if(((CheckBox)view).isChecked()){
                            Snackbar.make(finalView, "Checkbox Selected" + String.valueOf(view.getTag()), Snackbar.LENGTH_LONG).show();
                        }
                        else {
                            Snackbar.make(finalView, "Checkbox UnSelected" + String.valueOf(view.getTag()), Snackbar.LENGTH_LONG).show();

                        }
                        question.addSelectedOption((Integer) view.getTag());
                    }
                });
                viewHolder.qleLL.addView(viewHolder.checkBoxes[i],i+1);
            }
        }
        else {
            viewHolder.radioGroup=new RadioGroup(context);
            viewHolder.radioGroup.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            viewHolder.radioButtons=new RadioButton[length];
            for(int i=0;i<length;i++){
                viewHolder.radioButtons[i]=new RadioButton(context);
                viewHolder.radioButtons[i].setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                viewHolder.radioButtons[i].setText(optionArray.get(i));
                viewHolder.radioButtons[i].setPadding(padding_in_dp, padding_in_dp, padding_in_dp, padding_in_dp);
                viewHolder.radioButtons[i].setTextSize(textSize);
                viewHolder.radioButtons[i].setTag(i);
                viewHolder.radioButtons[i].setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if(((RadioButton)view).isChecked())
                            Snackbar.make(finalView, "New Radio Selected" + String.valueOf(view.getTag()), Snackbar.LENGTH_LONG).show();
                    }
                });
                viewHolder.radioGroup.addView(viewHolder.radioButtons[i],i);
            }
            viewHolder.qleLL.addView(viewHolder.radioGroup);
        }

        return convertView;
    }

    public interface OnOptionSelect {
        void onSingleChoiceOptionSelect(int position, int optionChoice);

        void onMultiChoiceOptionSelect(int position, int optionChoice);

        void onMultiChoiceOptionDeselect(int position, int optionChoice);
    }

    private class ViewHolder {
        LinearLayout qleLL;
        TextView qText;
        CheckBox[] checkBoxes;
        RadioGroup radioGroup;
        RadioButton[] radioButtons;
    }
}
