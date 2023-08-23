package com.example.constraints

class SharedConstraints {

    private SharedConstraints() {
        throw new IllegalStateException('Utility class!')
    }

    public static final int MIN_TITLE_LENGTH = 3
    public static final int MAX_TITLE_LENGTH = 300
    public static final int MAX_FIELD_LENGTH = 100
    public static final int MIN_CONTENT_LENGTH = 30
    public static final int MAX_CONTENT_LENGTH = 3000

}
