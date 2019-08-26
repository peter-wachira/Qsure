package com.wazinsure.qsure.Models;

public class CustomerModel {

    String first_name;
    String last_name;
    String dob;
    String kra_pin;
    String occupation;
    String mobile_no;
    String email;
    String location;
    String postal_address;
    String postal_code;
    String town;
    String country;
    String photo_url;
    String nok_fullname;
    String nok_mobileno;
    String nok_relation;
    String agent_code;


    public CustomerModel(String first_name, String last_name, String dob, String kra_pin, String occupation, String mobile_no, String email, String location, String postal_address, String postal_code, String town, String country, String photo_url, String nok_fullname, String nok_mobileno, String nok_relation, String agent_code) {
        this.first_name = first_name;
        this.last_name = last_name;
        this.dob = dob;
        this.kra_pin = kra_pin;
        this.occupation = occupation;
        this.mobile_no = mobile_no;
        this.email = email;
        this.location = location;
        this.postal_address = postal_address;
        this.postal_code = postal_code;
        this.town = town;
        this.country = country;
        this.photo_url = photo_url;
        this.nok_fullname = nok_fullname;
        this.nok_mobileno = nok_mobileno;
        this.nok_relation = nok_relation;
        this.agent_code = agent_code;
    }


    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getKra_pin() {
        return kra_pin;
    }

    public void setKra_pin(String kra_pin) {
        this.kra_pin = kra_pin;
    }

    public String getOccupation() {
        return occupation;
    }

    public void setOccupation(String occupation) {
        this.occupation = occupation;
    }

    public String getMobile_no() {
        return mobile_no;
    }

    public void setMobile_no(String mobile_no) {
        this.mobile_no = mobile_no;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getPostal_address() {
        return postal_address;
    }

    public void setPostal_address(String postal_address) {
        this.postal_address = postal_address;
    }

    public String getPostal_code() {
        return postal_code;
    }

    public void setPostal_code(String postal_code) {
        this.postal_code = postal_code;
    }

    public String getTown() {
        return town;
    }

    public void setTown(String town) {
        this.town = town;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getPhoto_url() {
        return photo_url;
    }

    public void setPhoto_url(String photo_url) {
        this.photo_url = photo_url;
    }

    public String getNok_fullname() {
        return nok_fullname;
    }

    public void setNok_fullname(String nok_fullname) {
        this.nok_fullname = nok_fullname;
    }

    public String getNok_mobileno() {
        return nok_mobileno;
    }

    public void setNok_mobileno(String nok_mobileno) {
        this.nok_mobileno = nok_mobileno;
    }

    public String getNok_relation() {
        return nok_relation;
    }

    public void setNok_relation(String nok_relation) {
        this.nok_relation = nok_relation;
    }

    public String getAgent_code() {
        return agent_code;
    }

    public void setAgent_code(String agent_code) {
        this.agent_code = agent_code;
    }
}
