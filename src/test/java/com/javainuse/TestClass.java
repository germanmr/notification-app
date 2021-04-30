package com.javainuse;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static java.util.Arrays.asList;

public class TestClass {

    class Person {
        private String name;
        private String age;

        public Person(String name, String age) {
            this.name = name;
            this.age = age;
        }

        public String getName() {
            return name;
        }

        public String getAge() {
            return age;
        }
    }

    private List<Person> persons = asList(new Person("German", "39"), new Person("Ivan", "26"));

    //    Map<Age,Value>
//    public List<Person> sortMyListByAge() {
    public Map<String, Person> sortMyListByAge() {
        return this.persons.stream().collect(Collectors.toMap(Person::getAge, person -> person));
    }

}
