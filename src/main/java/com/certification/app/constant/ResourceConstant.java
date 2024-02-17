package com.certification.app.constant;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ResourceConstant {

    public static final String BASE_RESOURCE = "/reactive/api";
    public static final String STUDENTS_API = BASE_RESOURCE + "/students";
    public static final String BY_ID = "/{id}";

}
