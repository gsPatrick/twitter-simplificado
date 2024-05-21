package br.com.patrick.config.users.admin;

import br.com.patrick.domain.service.user.admin.AdminService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AdminUserConfig implements CommandLineRunner {

    @Autowired
    private AdminService adminService;


    @Override
    @Transactional
    public void run(String... args) throws Exception {

        adminService.Admin();
    }
}
