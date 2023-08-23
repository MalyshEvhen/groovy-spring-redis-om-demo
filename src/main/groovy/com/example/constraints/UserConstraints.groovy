package com.example.constraints

class UserConstraints {

    private UserConstraints() {
        throw new IllegalStateException('UserConstraints could not be instantiated!')
    }

    public static final String EMAIL_REGEXP = '^[A-Za-z0-9+_.-]+@([A-Za-z0-9-]+\\.)+[A-Za-z]{2,}'
    public static final int MIN_NAME_LENGTH = 3
    public static final int MAX_NAME_LENGTH = 30

}
