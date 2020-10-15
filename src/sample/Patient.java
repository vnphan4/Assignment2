package sample;

import java.util.UUID;

public class Patient {
    public String name;
    public String gender;
    public String bloodType;
    public int age;
    public int weight;
    public int height;
    private UUID id;

    public Patient(){}

    public Patient( UUID id, String name, String gender, String bloodType, int age, int weight, int height) {
        this.id = id;
        this.name = name;
        this.gender = gender;
        this.bloodType = bloodType;
        this.age = age;
        this.weight = weight;
        this.height = height;

    }
    @Override
    public String toString() {
        return  name + '\t' + gender + '\t' + bloodType + '\t' + age + '\t' +  weight +'\t' + height;

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

    public String getBloodType() {
        return bloodType;
    }

    public void setBloodType(String bloodType) {
        this.bloodType = bloodType;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }


}
