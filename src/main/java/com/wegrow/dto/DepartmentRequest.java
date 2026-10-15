
package com.wegrow.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class DepartmentRequest {

    @NotBlank(message = "Department name is required")
    @Size(
            min = 2,
            max = 100,
            message = "Department name must be between 2 and 100 characters"
    )
    @Pattern(
            regexp = "^\\S(?:.*\\S)?$",
            message = "Department name must not start or end with spaces"
    )
    private String name;

    private String description;

    public DepartmentRequest() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}