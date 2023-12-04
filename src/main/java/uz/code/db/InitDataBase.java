package uz.code.db;

import uz.code.enums.CardStatus;
import uz.code.enums.ProfileStatus;
import uz.code.enums.UserRole;
import uz.code.model.Card;
import uz.code.model.Profile;
import uz.code.repository.CardRepository;
import uz.code.repository.ProfileRepository;
import uz.code.utils.MD5Util;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class InitDataBase {

    public static void adminInit(){

        Profile profile = new Profile();
        profile.setName("Admin");
        profile.setSurname("Adminjon");
        profile.setPhone("123");
        profile.setPassword(MD5Util.encode("123"));
        profile.setCreatedDate(LocalDateTime.now());
        profile.setStatus(ProfileStatus.ACTIVE);
        profile.setRole(UserRole.ADMIN);

        ProfileRepository profileRepository=new ProfileRepository();

        Profile profileByPhone = profileRepository.getProfileByPhone(profile.getPhone());

        if(profileByPhone!=null){
            return;
        }

        profileRepository.saveProfiile(profile);

    }
    public static void addCompanyCard() {
        Card card = new Card();
        card.setNumber("5555");
        card.setExpDate(LocalDate.of(2025, 12, 01));

        card.setPhone("123");
        card.setBalance(0d);
        card.setCreatedDate(LocalDateTime.now());
        card.setStatus(CardStatus.ACTIVE);

        CardRepository cardRepository = new CardRepository();
        Card exists = cardRepository.getCardByNumber(card.getNumber());

        if (exists != null) {
            return;
        }
        cardRepository.save(card);
    }
}
