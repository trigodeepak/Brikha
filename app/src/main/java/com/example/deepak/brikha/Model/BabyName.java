package com.example.deepak.brikha.Model;

import java.io.Serializable;

public class BabyName implements Serializable{

    public BabyName(String Name, String pronunciation, String meaning, String arabicMeaning, String arabic, String syriac, Boolean is_boy) {
        this.Name = Name;
        Pronunciation = pronunciation;
        Meaning = meaning;
        ArabicMeaning = arabicMeaning;
        Arabic = arabic;
        Syriac = syriac;
        this.is_boy = is_boy;
    }
    public BabyName(){
    }

    private String Name,Pronunciation,Meaning,ArabicMeaning,Arabic,Syriac;
    private Boolean is_boy;

    public String getName() {
        return Name;
    }

    public void setName(String Name) {
        this.Name = Name;
    }

    public String getPronunciation() {
        return Pronunciation;
    }

    public void setPronunciation(String pronunciation) {
        Pronunciation = pronunciation;
    }

    public String getMeaning() {
        return Meaning;
    }

    public void setMeaning(String meaning) {
        Meaning = meaning;
    }

    public String getArabicMeaning() {
        return ArabicMeaning;
    }

    public void setArabicMeaning(String arabicMeaning) {
        ArabicMeaning = arabicMeaning;
    }

    public String getArabic() {
        return Arabic;
    }

    public void setArabic(String arabic) {
        Arabic = arabic;
    }

    public String getSyriac() {
        return Syriac;
    }

    public void setSyriac(String syriac) {
        Syriac = syriac;
    }

    public Boolean getIs_boy() {
        return is_boy;
    }

    public void setIs_boy(Boolean is_boy) {
        this.is_boy = is_boy;
    }
}
