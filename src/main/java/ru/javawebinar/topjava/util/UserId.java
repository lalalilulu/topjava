package ru.javawebinar.topjava.util;

public enum UserId {
    USER(1),
    ADMIN(2);

    private int userId;

    public int getUserId() {
        return userId;
    }

    UserId(int userId) {
        this.userId = userId;
    }

}
