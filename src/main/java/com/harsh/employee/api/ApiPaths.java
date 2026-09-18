package com.harsh.employee.api;

public final class ApiPaths {

    public static final String API_VERSION = "/api/v1";
    public static final String EMPLOYEES = "/employees";
    public static final String EMPLOYEE_BY_ID = EMPLOYEES + "/{id}";

    private ApiPaths() {
    }
}