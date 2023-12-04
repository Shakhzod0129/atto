package uz.code.controller;

import uz.code.enums.ProfileStatus;
import uz.code.enums.UserRole;
import uz.code.model.Profile;

import static uz.code.controller.Controller.*;

public class AuthControlller {

    public void authenticationLogin() {
        login();
    }

    public void authenticationSignUp() {
        signUp();
    }

    private static void login() {
        String phone = scanner.nextLine("Enter your phone number : ");
        String password = scanner.nextLine("Enter your password : ");

        boolean login = profileService.login(phone, password);

        if (login) {
            for (Profile profile1 : profileService.getList()) {

                if (profile1.getPhone().equals(phone) && profile1.getPassword().equals(password)) {
                        profile.setPhone(phone);
                        if (profile1.getRole().equals(UserRole.USER)) {
                            UserController.userMenu();
                        } else {
                            AdminController.adminMenu();
                        }
                }
            }
        }
    }

    private static void signUp() {
        String name = scanner.nextLine("Enter your name : ");
        String surname = scanner.nextLine("Enter your surname : ");
        String phone = scanner.nextLine("Enter your phone : ");
        String password = scanner.nextLine("Enter your password : ");

        profile.setName(name);
        profile.setSurname(surname);
        profile.setPhone(phone);
        profile.setPassword(password);

        profileService.create(profile);


    }

}
