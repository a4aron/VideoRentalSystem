package controller;

import java.io.Serializable;

public enum Auth{
	ADMIN(0), EMPLOYEE(1), INVALID(2);

	private int roleValue;
	private long userId;

    Auth(int roleValue) {
        this.roleValue = roleValue;
    }

    public static Auth of(int roleValue) {
        for (Auth a: Auth.values()) {
            if (a.roleValue == roleValue) {
                return a;
            }
        }
        return INVALID;
    }

    public long getUserId() {
        return userId;
    }

    public Auth setUserId(long userId) {
        this.userId = userId;
        return this;
    }
}
