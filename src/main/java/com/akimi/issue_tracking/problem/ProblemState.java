package com.akimi.issue_tracking.problem;


public enum ProblemState {

    REPORTED("Prijavljen", "Reported"),
    ASSIGNED("Preuzet", "Assigned to Engineer"),
    SOLVING("Rješava se", "Being Resolved"),
    SOLVED("Riješen", "Solved");

    final String serbian;
    final String english;

    ProblemState(String serbian, String english) {
        this.serbian = serbian;
        this.english = english;
    }

//    private static final Map<String, String> SERBIAN_TO_ENGLISH =
//            Stream.of(values()).collect(Collectors.toMap(ProblemState::getSerbian, ProblemState::getEnglish));
//
//    private static final Map<String, String> ENGLISH_TO_SERBIAN =
//            Stream.of(values()).collect(Collectors.toMap(ProblemState::getEnglish, ProblemState::getSerbian));
//
//    public static String toEnglish(String serbian) {
//        return SERBIAN_TO_ENGLISH.getOrDefault(serbian, serbian);
//    }
//
//    public static String toSerbian(String english) {
//        return ENGLISH_TO_SERBIAN.getOrDefault(english, english);
//    }

    public static ProblemState valueOfIgnoreCase(String value) {
        for (ProblemState state : values()) {
            if (state.name().equalsIgnoreCase(value) ||
                    state.english.equalsIgnoreCase(value) ||
                    state.serbian.equalsIgnoreCase(value)) {
                return state;
            }
        }
        throw new IllegalArgumentException("Unknown state: " + value);
    }

    public String getEnglish() {
        return english;
    }
}
