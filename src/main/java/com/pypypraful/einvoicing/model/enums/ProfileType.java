package com.pypypraful.einvoicing.model.enums;

public enum ProfileType {
    SELLER("SELLER"), BUYER("BUYER");

    private final String profileType;

    ProfileType(final String profileType) {
        this.profileType = profileType;
    }

    public String toString() {
        return this.profileType;
    }
}
