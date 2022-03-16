package app.services.interfaces;


import app.entities.users.admin.Admin;

import java.util.Optional;

public interface AdminService {
    Admin createOrUpdateAdmin(Admin admin);
    Optional<Admin> findById(Long id);
    void deleteAdmin(Admin admin);

}
