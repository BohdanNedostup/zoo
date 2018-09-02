package zoowebapp.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import zoowebapp.dto.UserDtoAdmin;
import zoowebapp.dto.UserDtoProfile;
import zoowebapp.dto.UserDtoRegister;
import zoowebapp.entity.User;

public interface UserMapper {

    static User convertUserDtoRegisterToUser(UserDtoRegister userDtoRegister){
        User user = new ModelMapper().map(userDtoRegister, User.class);
        return user;
    }

    static UserDtoProfile convertUserToUserDtoProfile(User user){
        return new ModelMapper().map(user, UserDtoProfile.class);
    }

    static User convertUserDtoProfileToUser(UserDtoProfile userDtoProfile, User user){
        if (!user.getFirstName().equals(userDtoProfile.getFirstName())){
            user.setFirstName(userDtoProfile.getFirstName());
        }
        if (!user.getLastName().equals(userDtoProfile.getLastName())){
            user.setLastName(userDtoProfile.getLastName());
        }
        if (!user.getPassword().equals(userDtoProfile.getPassword())){
            user.setPassword((new BCryptPasswordEncoder()).encode(userDtoProfile.getPassword()));
        }
        if (user.getTelephone().equals(userDtoProfile.getTelephone())){
            user.setTelephone(userDtoProfile.getTelephone());
        }
        if (user.getCountry() != userDtoProfile.getCountry()){
            user.setCountry(userDtoProfile.getCountry());
        }
        if (user.getGender() != userDtoProfile.getUserGender()){
            user.setGender(userDtoProfile.getUserGender());
        }
        if (user.getBirthDate() != userDtoProfile.getBirthDate()){
            user.setBirthDate(userDtoProfile.getBirthDate());
        }
        return user;
    }

    static UserDtoAdmin convertUserToUserDtoAdmin(User user){
        UserDtoAdmin userDtoAdmin =  new ModelMapper().map(user, UserDtoAdmin.class);
        userDtoAdmin.setDepartment(user.getDepartment() == null ? "" : user.getDepartment().getName());
        return userDtoAdmin;
    }

    static User convertUserDtoAdminToUser(UserDtoAdmin userDtoAdmin){
        return new ModelMapper().map(userDtoAdmin, User.class);
    }
}