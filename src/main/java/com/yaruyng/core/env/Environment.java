package com.yaruyng.core.env;

public interface Environment {
    String[] getActiveProfiles();
    String[] getDefaultProfiles();
    boolean acceptProfiles(String... profiles);
}
