package com.company;

import java.util.*;
import java.util.stream.Stream;
import java.util.stream.Collectors;

import static java.util.Comparator.naturalOrder;

public class Main {

    public static void main(String[] args) {
        List<String> names = Arrays.asList("Jack", "Connor", "Harry", "George", "Samuel", "John");
        List<String> families = Arrays.asList("Evans", "Young", "Harris", "Wilson", "Davies", "Adamson", "Brown");
        Collection<Person> persons = new ArrayList<>();
        for (int i = 0; i < 10_000_000; i++) {
            persons.add(new Person(
                    names.get(new Random().nextInt(names.size())),
                    families.get(new Random().nextInt(families.size())),
                    new Random().nextInt(100),
                    Sex.values()[new Random().nextInt(Sex.values().length)],
                    Education.values()[new Random().nextInt(Education.values().length)])
            );
        }
        System.out.println("Несовершеннолетние (т.е. люди младше 18 лет)");
        long total = persons.stream()
                .filter(peopleMinors -> peopleMinors.getAge() < 18)
                .count();
        System.out.println(total);

        System.out.println();

        System.out.println("Фамилии призывников (т.е. мужчин от 18 и до 27 лет).");
        List<String> result = persons.stream()
                .filter(peopleConscripts -> peopleConscripts.getAge() > 18)
                .filter(peopleConscripts -> peopleConscripts.getAge() <= 27)
                .filter(peopleConscripts -> peopleConscripts.getSex().equals(Sex.MAN))
                .map(Person::getFamily)
                .collect(Collectors.toList());
        System.out.println(result);

        System.out.println();

        System.out.println("Отсортированный по фамилии список потенциально работоспособных людей с высшим образованием в выборке (т.е. людей с высшим образованием от 18 до 60 лет для женщин и до 65 лет для мужчин");
        List<String> result2 = persons.stream()
                .filter(people -> people.getEducation().equals(Education.HIGHER))
                .filter(people -> people.getAge() > 18)
                .filter(people -> (people.getSex().equals(Sex.WOMAN) && people.getAge() < 60)
                        || (people.getSex().equals(Sex.MAN) && people.getAge() < 65))
                .map(Person::getFamily)
                .sorted(naturalOrder())
                .collect(Collectors.toList());
        System.out.println(result2);
    }
}
