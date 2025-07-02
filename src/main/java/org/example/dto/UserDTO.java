package org.example.dto;

import java.util.Set;

public class UserDTO {
    private Long id;
    private String username;
    private String fullName;
    private Set<RoleDTO> roles;

    // Getters and setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }
    public Set<RoleDTO> getRoles() { return roles; }
    public void setRoles(Set<RoleDTO> roles) { this.roles = roles; }
}

