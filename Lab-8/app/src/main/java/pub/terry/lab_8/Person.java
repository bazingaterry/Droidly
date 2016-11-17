package pub.terry.lab_8;

/**
 * Created by terrychan on 17/11/2016.
 */

class Person {
    private String name, birthday, gift;

    Person() {

    }

    Person(String name, String birthday, String gift) {
        this.name = name;
        this.birthday = birthday;
        this.gift = gift;
    }

    public String getName() {
        return name;
    }

    public String getBirthday() {
        return birthday;
    }

    public String getGift() {
        return gift;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public void setGift(String gift) {
        this.gift = gift;
    }
}
