package com.example.heman.projectv02.SurveyComponents;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.annotation.IntegerRes;
import android.util.ArraySet;
import android.util.Log;

import com.google.gson.annotations.Expose;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by yashjain on 6/26/17.
 */

public class Question {

    private String sId;
    private int qNo;
    private String qText;
    private JSONObject options;
    private String language;
    private boolean multiSelect;
    @Expose
    transient private String[] optionArray;
    @Expose
    transient private Set<Integer> selectedOptions;

    public Question() {
        selectedOptions = new HashSet<Integer>();
    }

    public String getqText() {
        return qText;
    }

    public void setqText(String qText) {
        this.qText = qText;
    }

    public JSONObject getOptions() {
        return options;
    }

    public void setOptions(JSONObject options) {
        this.options = options;
        setOptionArray();
    }

    public boolean isMultiSelect() {
        return multiSelect;
    }

    public void setMultiSelect(boolean multiSelect) {
        this.multiSelect = multiSelect;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getsId() {
        return sId;
    }

    public void setsId(String sId) {
        this.sId = sId;
    }

    public int getqNo() {
        return qNo;
    }

    public void setqNo(int qNo) {
        this.qNo = qNo;
    }

    public void setOptionArray(){
        int length=options.length();
        optionArray = new String[length];
        for(int i=0;i<length;i++){
            try {
                optionArray[i] = options.getString(String.valueOf(i));
            } catch (JSONException e) {
                e.printStackTrace();
                Log.d("ERROR PARSING OPTION",e.getMessage()+"\n"+options.toString());
            }
        }
    }


    public Set<Integer> getSelectedOptions() {
        return selectedOptions;
    }

    public void setSelectedOptions(Set<Integer> selectedOptions) {
        this.selectedOptions = selectedOptions;
    }

    public void addSelectedOption(Integer index){
        if(multiSelect)
        selectedOptions.add(index);
        else {
            selectedOptions.clear();
            selectedOptions.add(index);
        }
    }

    public void removeSelectedOption(Integer index){
        selectedOptions.remove(index);
    }

    public String[] getOptionArray() {
        if(optionArray==null)setOptionArray();
        return optionArray;
    }

    public void setOptionArray(String[] optionArray) {
        this.optionArray=optionArray;
    }

    public boolean isOptionSelected(int i) {
        getOptionArray();
        return selectedOptions.contains(i);
    }
}
