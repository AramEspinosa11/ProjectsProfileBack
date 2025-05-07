package com.practica.demo.services;

import com.practica.demo.models.*;
import com.practica.demo.repositories.IUserRepository;
import com.practica.demo.repositories.IUserSessionRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Optional;
import java.util.Base64;

 @Slf4j
@Service
public class UserService {

    @Autowired
    IUserRepository userRepository;

    @Autowired
    IUserSessionRepository userSessionRepository;

    @Autowired
    private CryptoService cryptoService;

    public ArrayList<UserModel> getUsers(){
        return (ArrayList<UserModel>) userRepository.findAll();
    }

    public UserModel saveUser(UserModel user){
        return userRepository.save(user);
    }

    public Optional<UserModel> getById(Long id){
        return userRepository.findById(id);
    }

    public UserModel updateById(UserModel request, Long id){
        UserModel user = userRepository.findById(id).get();

        user.setName(request.getName());
        //user.setLastNames(request.getLastNames());
        user.setEmail(request.getEmail());
        user.setPhoneNumber(request.getPhoneNumber());

        return user;
    }

    public Boolean deleteUser(Long id){
        try {
            userRepository.deleteById(id);
            return true;
        }catch (Exception e){
            return false;
        }
    }

    public ResponseEntity<SessionModelResponse> startSession(DataModel session){
        try {

            SessionModelRequest dataDecripted = this.cryptoService.decryptData(session, SessionModelRequest.class);
            log.info("{}", "Email desencriptado: " + dataDecripted.getEmail());

            Optional<UserModel> userOpt = userRepository.findByEmailAndPassword(
                    dataDecripted.getEmail(),
                    Base64.getEncoder().encodeToString(dataDecripted.getPassword().getBytes(StandardCharsets.UTF_8))
            );

            if(userOpt.isEmpty()){
                SessionModelResponse error = new SessionModelResponse();
                error.setMessage("No existe un usuario registrado con esas credenciales.");
                error.setDataEncrypted("NULL");
                error.setErrorCode(1);
                error.setSessionToken("NULL");
                return new ResponseEntity<>(error, HttpStatus.OK);
            }

            UserModel user = userOpt.get();

            /* Crear token de sesion */
            String tokenRaw = user.getPassword() + "|" + user.getEmail() + "|" + LocalDateTime.now().toString();
            String userToken = Base64.getEncoder().encodeToString(tokenRaw.getBytes(StandardCharsets.UTF_8));

            UserDTO dto = new UserDTO();
            dto.setEmail(user.getEmail());
            dto.setName(user.getName());
            dto.setSurename(user.getSurename());
            dto.setLastname(user.getLastname());
            dto.setIcn(user.getIcn());
            dto.setId_hospital(user.getIdHospital());
            dto.setIs_Active(user.getIsActive());
            dto.setPhone(user.getPhoneNumber());
            dto.setShift_type(user.getShiftType());
            dto.setDate(user.getCreatedAt());
            dto.setType(user.getType());
            dto.setUsertoken(userToken);

            UserSession userSession = new UserSession();
            userSession.setUser_id(user.getUser_id());
            userSession.setUsertoken(userToken);
            userSession.setDate(LocalDateTime.now());
            userSession.setStatus(1);
            userSessionRepository.save(userSession);

            DataModel encryptedUserData = cryptoService.encryptData(dto);
            SessionModelResponse data = new SessionModelResponse();
            data.setMessage("Sesión iniciada.");
            data.setDataEncrypted(encryptedUserData.getData());
            data.setErrorCode(0);
            data.setSessionToken(userToken);
            return new ResponseEntity<>(data, HttpStatus.OK);

        }catch (Exception e){
            throw new RuntimeException("Error, wrong credentials", e);
        }
    }
}
