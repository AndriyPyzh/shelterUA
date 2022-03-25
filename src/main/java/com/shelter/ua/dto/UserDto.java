package com.shelter.ua.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDto {

    @NotBlank
    @Length(min = 4, max = 15)
    private String username;

    @NotBlank
    @Length(min = 8, max = 30)
    private String password;
}
