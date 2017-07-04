package com.example.heman.projectv02.Adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.heman.projectv02.SurveyComponents.Question;
import com.example.heman.projectv02.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;


public class QuestionAdapter extends ArrayAdapter<Question> {
    private static final String DOT=". ";
    private OnOptionSelect mOnOptionSelect;
    private ArrayList<Question> questionList;
    private Activity mActivity;

    public QuestionAdapter(@NonNull Activity mActivity, ArrayList<Question> questionList, OnOptionSelect mOnOptionSelect) {
        super(mActivity, R.layout.question_list_element, questionList);
        this.questionList = questionList;
        this.mOnOptionSelect=mOnOptionSelect;
        this.mActivity = mActivity;
    }

    private void logQuestionList() {
        for (Question q : questionList) {
            String s = "";
            Set<Integer> li = q.getSelectedOptions();
            for (int x : li) {
                s = s + String.valueOf(x) + ", ";
            }
            Log.d("Question " + String.valueOf(q.getqNo()), "Options Selected: \n" + s);
        }
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        logQuestionList();
        Question question = questionList.get(position);
        Context context=getContext();
        final ViewHolder viewHolder=new ViewHolder();
        convertView = LayoutInflater.from(context).inflate(R.layout.question_list_element, parent, false);
        final View finalView=convertView;
        viewHolder.qleLL=convertView.findViewById(R.id.qleLL);
        String[] optionArray = question.getOptionArray();
        int length = optionArray.length;
        viewHolder.qText = convertView.findViewById(R.id.qleQText);
        viewHolder.qText.setText(String.valueOf(question.getqNo())+DOT+question.getqText());
        int padding_in_dp = (int) context.getResources().getDimension(R.dimen.qleOTextPadding);
        float textSize = context.getResources().getDimensionPixelSize(R.dimen.qleOTextSize) / context.getResources().getDisplayMetrics().scaledDensity;
        if(question.isMultiSelect()){
            viewHolder.checkBoxes=new CheckBox[length];
            for(int i=0;i<length;i++){
                viewHolder.checkBoxes[i]=new CheckBox(context);
                viewHolder.checkBoxes[i].setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                viewHolder.checkBoxes[i].setText(optionArray[i]);
                viewHolder.checkBoxes[i].setPadding(padding_in_dp, padding_in_dp, padding_in_dp, padding_in_dp);
                viewHolder.checkBoxes[i].setTextSize(textSize);
                viewHolder.checkBoxes[i].setTag(i);
                final int checkboxPosition = i;
                Log.i("Question " + String.valueOf(question.getqNo()), "Option " + checkboxPosition + String.valueOf(questionList.get(position).isOptionSelected(i)));
                viewHolder.checkBoxes[i].setChecked(questionList.get(position).isOptionSelected(i));
                viewHolder.checkBoxes[i].setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                        if (b) {
                            questionList.get(position).addSelectedOption(checkboxPosition);
                        } else {
                            questionList.get(position).removeSelectedOption(checkboxPosition);
                        }
                    }
                });

                viewHolder.checkBoxes[i].setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if(((CheckBox)view).isChecked()){
                            questionList.get(position).addSelectedOption(checkboxPosition);
                            Log.d("Added Selected Position", "Question: " + String.valueOf(position) + "     Option" + String.valueOf(checkboxPosition));
                            Snackbar.make(finalView, "Checkbox Selected" + String.valueOf(view.getTag()), Snackbar.LENGTH_LONG).show();
                        }
                        else {
                            Log.d("Added Selected Position", "Question: " + String.valueOf(position) + "     Option" + String.valueOf(checkboxPosition));
                            Snackbar.make(finalView, "Checkbox UnSelected" + String.valueOf(view.getTag()), Snackbar.LENGTH_LONG).show();
                            questionList.get(position).removeSelectedOption(checkboxPosition);
                        }
                    }
                });
                viewHolder.qleLL.addView(viewHolder.checkBoxes[i],i+1);
            }
        }
        else {

            ArrayList<View> radioList = new ArrayList<>();

            Log.i("onView Question", String.valueOf(question.getqNo()));
            viewHolder.radioGroup=new RadioGroup(context);
            viewHolder.radioGroup.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            final int radioButtonsIndex[] = {1, 2, 3, 4, 5, 6, 7, 8, 9};
            viewHolder.radioGroup.setOrientation(LinearLayout.VERTICAL);
            viewHolder.radioButtons=new RadioButton[length];
            for(int i=0;i<length;i++){
                viewHolder.radioButtons[i]=new RadioButton(context);
                viewHolder.radioButtons[i].setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                viewHolder.radioButtons[i].setText(optionArray[i]);
                viewHolder.radioButtons[i].setPadding(padding_in_dp, padding_in_dp, padding_in_dp, padding_in_dp);
                viewHolder.radioButtons[i].setTextSize(textSize);
                viewHolder.radioButtons[i].setTag(i);
                viewHolder.radioButtons[i].setChecked(questionList.get(position).isOptionSelected(i));
                final int radioPosition = i;
                viewHolder.radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(RadioGroup radioGroup, @IdRes int i) {
                        if (i == -1) return;
                        if (((RadioButton) radioGroup.findViewById(i)).isChecked()) {
                            //radioGroup.clearCheck();
                        }
                    }
                });
                viewHolder.radioButtons[i].setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Log.i("Checked Question " + String.valueOf(position), "Option " + radioPosition);
                        //viewHolder.radioGroup.clearCheck();
                        questionList.get(position).addSelectedOption(radioPosition);
                        ((RadioButton) view).setChecked(true);
                    }
                });

                viewHolder.radioGroup.addView(viewHolder.radioButtons[i]);
            }


            viewHolder.qleLL.addView(viewHolder.radioGroup);

            //if(!questionList.get(position).getSelectedOptions().isEmpty()){
            //    ((RadioButton)viewHolder.radioGroup.getChildAt((Integer) questionList.get(position).getSelectedOptions().toArray()[0])).setChecked(true);
            //}
        }

        return convertView;
    }

    public interface OnOptionSelect {
        void onSingleChoiceOptionSelect(int position, int optionChoice);

        void onMultiChoiceOptionSelect(int position, int optionChoice);

        void onMultiChoiceOptionDeselect(int position, int optionChoice);
    }

    private static class ViewHolder {
        LinearLayout qleLL;
        TextView qText;
        CheckBox[] checkBoxes;
        RadioGroup radioGroup;
        RadioButton[] radioButtons;
    }
}
