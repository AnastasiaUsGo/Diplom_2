package user;

import com.github.javafaker.Faker;
import org.apache.commons.lang3.RandomStringUtils;

public class UserGenerator {

    public User generic(){
        return new User("user28@mail.ru", "user28", "Anastasia");
    }

    Faker faker = new Faker();
    public User random(){
        return new User(faker.internet().emailAddress(), faker.internet().password(), faker.name().firstName());
    }

    public User loginCredentials(){
        return new User("user28@mail.ru","user28");
    }
}
