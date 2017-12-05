package com.tony.bday.greeting.model;

import org.apache.camel.dataformat.bindy.annotation.CsvRecord;
import org.apache.camel.dataformat.bindy.annotation.DataField;

/**
 * 
 * 
 * @author lei.du
 *
 */
@CsvRecord(separator = ",")
public class Employee {
    @DataField(pos = 1)
    private String Name;
    @DataField(pos = 2)
    private String SurName;
    @DataField(pos = 3)
    private String Birthday;
    @DataField(pos = 4)
    private String Email;

    public String getName() {
        return Name;
    }
    public void setName(String name) {
        Name = name;
    }
    public String getSurName() {
        return SurName;
    }
    public void setSurName(String surName) {
        SurName = surName;
    }
    public String getBirthday() {
        return Birthday;
    }
    public void setBirthday(String birthday) {
        Birthday = birthday;
    }
    public String getEmail() {
        return Email;
    }
    public void setEmail(String email) {
        Email = email;
    }
    
    @Override
    public String toString() {
        return "Employee [Name=" + Name + ", SurName=" + SurName + ", Birthday="+ Birthday + ", Email="+ Email + "]";
    }
}
