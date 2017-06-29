package com.example.heman.projectv02;

import android.content.Context;
import android.content.res.ColorStateList;
import android.os.Build;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.CompoundButtonCompat;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yashjain on 6/29/17.
 */

public class QuestionAdapter extends ArrayAdapter<Question> {
    private static final String DOT=". ";
    private OnOptionSelect mOnOptionSelect;

    public QuestionAdapter(@NonNull Context context, ArrayList<Question> questionList, OnOptionSelect mOnOptionSelect) {
        super(context, R.layout.question_list_element,questionList);
        this.mOnOptionSelect=mOnOptionSelect;
    }


    private class ViewHolder{
        LinearLayout qleLL;
        TextView qText;
        CheckBox[] checkBoxes;
        RadioGroup radioGroup;
        RadioButton[] radioButtons;
    }

    public interface OnOptionSelect{
        void onSingleChoiceOptionSelect(int position,int optionChoice);
        void onMultiChoiceOptionSelect(int position,int optionChoice);
        void onMultiChoiceOptionDeselect(int position,int optionChoice);
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        final Question question=getItem(position);
        Context context=getContext();
        final ViewHolder viewHolder=new ViewHolder();
        LayoutInflater inflater = LayoutInflater.from(context);
        convertView = inflater.inflate(R.layout.question_list_element, parent, false);
        final View finalView=convertView;
        viewHolder.qleLL=convertView.findViewById(R.id.qleLL);
        List<String> optionArray=question.getOptionArray();
        int length=optionArray.size();
        viewHolder.qText=(TextView)convertView.findViewById(R.id.qleQText);
        viewHolder.qText.setText(String.valueOf(question.getqNo())+DOT+question.getqText());
        int states[][] = {{android.R.attr.state_checked}, {}};
        int colors[] = {R.color.qleOptionChecked, R.color.qleOptionUnchecked};
        float padding_in_dp = context.getResources().getDimension(R.dimen.qleOTextPadding);
        final float scale = context.getResources().getDisplayMetrics().density;
        int padding_in_px = (int) (padding_in_dp * scale + 0.5f);
        if(question.isMultiSelect()){
            viewHolder.checkBoxes=new CheckBox[length];
            for(int i=0;i<length;i++){
                viewHolder.checkBoxes[i]=new CheckBox(context);
                viewHolder.checkBoxes[i].setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                viewHolder.checkBoxes[i].setText(optionArray.get(i));
                viewHolder.checkBoxes[i].setPadding(padding_in_px,padding_in_px,padding_in_px,padding_in_px);
                viewHolder.checkBoxes[i].setTextColor(new ColorStateList(states,colors));
                viewHolder.checkBoxes[i].setTextSize(TypedValue.COMPLEX_UNIT_DIP,context.getResources().getDimensionPixelSize(R.dimen.qleOTextSize));
                viewHolder.checkBoxes[i].setTag(i);
                viewHolder.checkBoxes[i].setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if(((CheckBox)view).isChecked()){
                            Snackbar.make(finalView,"Checkbox Selected"+String.valueOf((Integer)view.getTag()),Snackbar.LENGTH_LONG).show();
                        }
                        else {
                            Snackbar.make(finalView,"Checkbox UnSelected"+String.valueOf((Integer)view.getTag()),Snackbar.LENGTH_LONG).show();

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
            viewHolder.radioGroup.setPadding(padding_in_px,padding_in_px,padding_in_px,padding_in_px);
            viewHolder.radioButtons=new RadioButton[length];
            for(int i=0;i<length;i++){
                viewHolder.radioButtons[i]=new RadioButton(context);
                viewHolder.radioButtons[i].setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                viewHolder.radioButtons[i].setText(optionArray.get(i));
                viewHolder.radioButtons[i].setPadding(padding_in_px,padding_in_px,padding_in_px,padding_in_px);
                viewHolder.radioButtons[i].setTextColor(new ColorStateList(states,colors));
                viewHolder.radioButtons[i].setTextSize(TypedValue.COMPLEX_UNIT_DIP,context.getResources().getDimensionPixelSize(R.dimen.qleOTextSize));
                viewHolder.radioButtons[i].setTag(i);
                viewHolder.radioButtons[i].setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if(((RadioButton)view).isChecked())
                            Snackbar.make(finalView,"New Radio Selected"+String.valueOf((Integer)view.getTag()),Snackbar.LENGTH_LONG).show();
                    }
                });
                viewHolder.radioGroup.addView(viewHolder.radioButtons[i],i);
            }
            viewHolder.qleLL.addView(viewHolder.radioGroup);
        }

        return convertView;
    }
}
