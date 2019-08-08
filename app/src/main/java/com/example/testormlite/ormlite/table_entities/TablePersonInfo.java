package com.example.testormlite.ormlite.table_entities;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "table_person_info")
public class TablePersonInfo {

    @DatabaseField(generatedId = true)
    public int id;

    @DatabaseField(columnName = "name")
    public String name;

    @DatabaseField(columnName = "gender")
    public String gender;

    @DatabaseField(columnName = "age")
    public String age;

    @DatabaseField(columnName = "address")
    public String address;

    public TablePersonInfo () {}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return "name:" + name + ",gender:" + gender + ",age:" + age + ",address:" + address;
    }
}
