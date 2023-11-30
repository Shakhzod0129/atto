package uz.code.service;

import uz.code.enums.ProfileStatus;
import uz.code.enums.UserRole;
import uz.code.model.Profile;
import uz.code.repository.ProfileRepository;

import java.util.List;

public class ProfileService {
    static ProfileRepository profileRepository=new ProfileRepository();
    public void create(Profile profile) {

        profile.setStatus(ProfileStatus.ACTIVE);
        if(profile.getPassword().contains("admin")){
        profile.setRole(UserRole.ADMIN);
        }else {
        profile.setRole(UserRole.USER);
        }
        boolean result=profileRepository.create(profile);

        if(result){
            System.out.println("Profile has added successfully✅ : "+profile.getName());
        }else {
            System.out.println("Fail❌");
        }
    }

    public boolean login(String phone, String password) {

        List<Profile> all = profileRepository.getAll(ProfileStatus.ACTIVE);

        for (Profile profile : all) {
            if(profile.getPhone().equals(phone)&&profile.getPassword().equals(password)){
                System.out.println("Welcome to your account✅ : "+profile.getName()+" "+profile.getSurname());
                return true;
            }
        }
        System.out.println("This account has not found❌");
        return false;
    }


    public List<Profile> list() {
        List<Profile> profileList=profileRepository.getAll(ProfileStatus.ACTIVE);

       return profileList;
    }
}
