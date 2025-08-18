package com.whispr.enums;

public enum VisibilityScope {
    PUBLIC, FRIENDS, SELF;

    public boolean isAtLeast(VisibilityScope other) {
        return this.ordinal() >= other.ordinal();
    }
}
