package com.example.heman.projectv02;

import android.content.Intent;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

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
    transient private List<String> optionArray;
    transient private List<Integer> selectedOptions=new ArrayList<Integer>();

    public Question() {
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
        optionArray=new ArrayList<String>();
        for(int i=0;i<length;i++){
            try {
                optionArray.add(options.getString(String.valueOf(i)));
            } catch (JSONException e) {
                e.printStackTrace();
                Log.d("ERROR PARSING OPTION",e.getMessage()+"\n"+options.toString());
            }
        }
    }


    public List<Integer> getSelectedOptions() {
        return selectedOptions;
    }

    public void setSelectedOptions(List<Integer> selectedOptions) {
        this.selectedOptions = selectedOptions;
    }

    public void addSelectedOption(Integer index){
        if(multiSelect)
        selectedOptions.add(index);
        else {
            selectedOptions=new ArrayList<Integer>();
            selectedOptions.add(index);
        }
    }

    public void removeSelectedOption(Integer index){
        if(multiSelect)
        selectedOptions.remove(index);
        else {
            selectedOptions=new ArrayList<Integer>();
        }
    }

    public List<String> getOptionArray(){
        if(optionArray==null)setOptionArray();
        return optionArray;
    }

    public void setOptionArray(List<String> optionArray){
        this.optionArray=optionArray;
    }
}
