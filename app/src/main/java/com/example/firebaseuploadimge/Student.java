package com.example.firebaseuploadimge;

public class Student {

    String name;
    String age;
    String descrip;
    String imageUri;

    public Student(String name, String age, String descrip, String imageUri) {
        this.name = name;
        this.age = age;
        this.descrip = descrip;
        this.imageUri = imageUri;
    }

    public Student() {

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getDescrip() {
        return descrip;
    }

    public void setDescrip(String descrip) {
        this.descrip = descrip;
    }

    public String getImageUri() {
        return imageUri;
    }

    public void setImageUri(String imageUri) {
        this.imageUri = imageUri;
    }
}
