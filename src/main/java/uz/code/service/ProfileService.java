package uz.code.service;

import uz.code.enums.ProfileStatus;
import uz.code.enums.UserRole;
import uz.code.model.Profile;
import uz.code.repository.ProfileRepository;

import java.util.List;

public class ProfileService {
    static ProfileRepository profileRepository=new ProfileRepository();
    static boolean bool=false;
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

        List<Profile> all = profileRepository.getAll();

        for (Profile profile : all) {
            if(profile.getPhone().equals(phone)&&profile.getPassword().equals(password)){
                if(profile.getStatus().equals(ProfileStatus.ACTIVE)){
                    System.out.println("Welcome to your account✅ : "+profile.getName()+" "+profile.getSurname());
                    return true;
                }else if(profile.getStatus().equals(ProfileStatus.INACTIVE)) {
                    System.out.println("Your account is INACTIVE❌");
                    return false;
                }else {
                    System.out.println("Your account is BLOCKED❌");
                    return false;

                }

            }
        }
        System.out.println("This account has not found❌");
        return false;
    }


    public List<Profile> getList() {
        List<Profile> profileList=profileRepository.getAll();

       return profileList;
    }

    public void listOfUser(){
        List<Profile> profileList=profileRepository.getAll();
        for (Profile profile : profileList) {
            System.out.println(profile);
        }
    }

    public void changeStatus(String number, ProfileStatus status) {
        List<Profile> profileList=profileRepository.getAll();

        for (Profile profile : profileList) {
            if(profile.getPhone().equals(number)){
              bool=  profileRepository.changeStatus(profile.getPhone(),status);
            }
        }
        if(bool){
            System.out.println("Success✅");
        }else {
            System.out.println("Error❌");
        }
    }
}
