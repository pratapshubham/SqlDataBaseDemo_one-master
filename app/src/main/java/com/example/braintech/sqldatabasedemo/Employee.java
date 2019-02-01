package com.example.braintech.sqldatabasedemo;

public class Employee {
    public  String User;
    public String Phone;
    public String Address;
    public int id;
    public Employee(int id,String User,String Phone,String Address)
    {
        this.id = id;
        this.User = User;
        this.Phone = Phone;
        this.Address = Address;
    }
    public Employee(String User,String Phone,String Address)
    {
        this.User = User;
        this.Phone = Phone;
        this.Address = Address;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUser() {
        return User;
    }

    public void setUser(String user) {
        User = user;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }
}
