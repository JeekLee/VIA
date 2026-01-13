package com.via.account.domain.utils;

import java.security.SecureRandom;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class NicknameGenerator {
    private static final Random random = new SecureRandom();

    private static final List<String> adjectives = Arrays.asList(
            "Cute", "Cool", "Smart", "Brave", "Kind", "Active", "Quiet", "Calm",
            "Bright", "Warm", "Fresh", "Fast", "Slow", "Big", "Small", "Tall",
            "Deep", "Wide", "Narrow", "Strong", "Gentle", "New", "Old", "Young",
            "Wise", "Pretty", "Handsome", "Healthy", "Happy", "Sad", "Joyful", "Angry",
            "Peaceful", "Free", "Mystical", "Magical", "Special", "Common", "Unique", "Simple",
            "Fancy", "Shy", "Bold", "Honest", "Diligent", "Lazy", "Hardworking", "Meticulous",
            "Casual", "Precise", "Agile", "Relaxed", "Hasty", "Patient", "Careful", "Daring"
    );

    private static final List<String> animals = Arrays.asList(
            "Cat", "Dog", "Rabbit", "Hamster", "GuineaPig", "Parrot", "Canary", "Goldfish",
            "Lion", "Tiger", "Leopard", "Cheetah", "Elephant", "Giraffe", "Hippo", "Rhino",
            "Monkey", "Gorilla", "Chimpanzee", "Orangutan", "Panda", "Koala", "Kangaroo", "Wallaby",
            "Wolf", "Fox", "Raccoon", "Badger", "Squirrel", "Chipmunk", "Beaver", "Otter",
            "Bear", "PolarBear", "Penguin", "Seal", "Whale", "Dolphin", "Shark", "Octopus",
            "Eagle", "Falcon", "Owl", "Crow", "Sparrow", "Pigeon", "Swallow", "Magpie",
            "Deer", "Boar", "Sheep", "Goat", "Horse", "Zebra", "Donkey", "Llama",
            "Alpaca", "Meerkat", "PrairieDog", "Chihuahua", "Hyena", "Maltese", "Poodle", "Retriever"
    );

    private static String generateRandomNumber() {
        return String.format("%04d", random.nextInt(10000));
    }

    public static String generate() {
        return adjectives.get(random.nextInt(adjectives.size()))
                + animals.get(random.nextInt(animals.size()))
                + "#"
                + generateRandomNumber();
    }
}
