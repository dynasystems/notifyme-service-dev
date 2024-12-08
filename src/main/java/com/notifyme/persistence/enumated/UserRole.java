package com.notifyme.persistence.enumated;

public enum UserRole {
    ADMINGERAL("adminGeral"),
    ADMINCONDOMINIO("adminCondominio"),
    USUARIOCONDOMINIO("usuarioCondominio");

    private String role;

    UserRole(String role) {
        this.role = role;
    }

    public String getRole(){
        return role;
    }

}
