package com.nls.web.rest.vm;

import com.nls.domain.Role;
import com.nls.service.dto.UserDTO;
import javax.validation.constraints.Size;

import java.time.ZonedDateTime;
import java.util.Set;

/**
 * View Model extending the UserDTO, which is meant to be used in the user
 * management UI.
 */
public class ManagedUserVM extends UserDTO {

    public static final int PASSWORD_MIN_LENGTH = 4;

    public static final int PASSWORD_MAX_LENGTH = 100;

    @Size(min = PASSWORD_MIN_LENGTH, max = PASSWORD_MAX_LENGTH)
    private String password;

    public ManagedUserVM() {
        // Empty constructor needed for Jackson.
    }

    public ManagedUserVM(Long id, String login, String password, String firstName, String lastName,
            String email, boolean activated, String imageUrl, String langKey,
            String createdBy, ZonedDateTime createdDate, String lastModifiedBy, ZonedDateTime lastModifiedDate,
            Set<String> authorities, Role role) {

        super(id, login, firstName, lastName, email, activated, imageUrl, langKey,
                createdBy, createdDate, lastModifiedBy, lastModifiedDate, authorities, role);

        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    @Override
    public String toString() {
        return "ManagedUserVM{"
                + "} " + super.toString();
    }
}
