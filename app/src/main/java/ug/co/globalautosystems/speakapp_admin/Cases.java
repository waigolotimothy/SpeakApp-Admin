package ug.co.globalautosystems.speakapp_admin;

import java.io.Serializable;

class Cases implements Serializable {
    private String date,casedate,alleged,gender,stature,faculty,nature,location,details,phone;
    Cases(String phone,String date,String casedate, String alleged, String gender, String stature, String faculty, String nature, String location, String details) {
        this.date = date;
        this.gender = gender;
        this.stature = stature;
        this.faculty = faculty;
        this.phone=phone;
        this.alleged = alleged;
        this.nature = nature;
        this.casedate=casedate;
        this.location = location;
        this.details = details;
    }


    public String getAlleged() {
        return alleged;
    }

    public String getDate() {
        return date;
    }

    public String getDetails() {
        return details;
    }

    public String getFaculty() {
        return faculty;
    }

    public String getGender() {
        return gender;
    }

    public String getLocation() {
        return location;
    }

    public String getNature() {
        return nature;
    }

    public String getCasedate() {
        return casedate;
    }

    public String getPhone() {
        return phone;
    }

    public String getStature() {
        return stature;
    }
}
