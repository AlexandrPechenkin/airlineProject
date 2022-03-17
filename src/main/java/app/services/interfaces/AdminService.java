package app.services.interfaces;


import app.entities.users.admin.Admin;

import java.util.List;
import java.util.Optional;

public interface AdminService {
    Admin createOrUpdateAdmin(Admin admin);
    Optional<Admin> findById(Long id);
    List<Admin> findAll();
    void deleteAdmin(Admin admin);

}
