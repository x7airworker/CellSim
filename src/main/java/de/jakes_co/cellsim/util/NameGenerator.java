package de.jakes_co.cellsim.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class NameGenerator {
    public static final NameGenerator INSTANCE = new NameGenerator();

    private final List<String> names = new ArrayList<>();
    private final Random random = new Random();

    private NameGenerator() {
        Scanner scanner = new Scanner(getClass().getClassLoader().getResourceAsStream("names.txt"));
        while (scanner.hasNextLine())
            names.add(scanner.nextLine());
    }

    public String next() {
        return names.get(random.nextInt(names.size() - 1));
    }

}
