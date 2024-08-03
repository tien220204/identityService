package com.tien.identity.service.service;

import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import com.tien.identity.service.dto.request.AuthenticationRequest;
import com.tien.identity.service.dto.request.IntropectRequest;
import com.tien.identity.service.dto.response.AuthenticationResponse;
import com.tien.identity.service.dto.response.IntrospectResponse;
import com.tien.identity.service.entity.User;
import com.tien.identity.service.exception.AppException;
import com.tien.identity.service.exception.ErrorCode;
import com.tien.identity.service.repository.UserRepository;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.text.ParseException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.StringJoiner;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
public class AuthenticationService {
    private static final Logger log = LoggerFactory.getLogger(AuthenticationService.class);
    UserRepository userRepository;
    @NonFinal //de khong inject vao constructor
    @Value("${jwt.signerKey}")
    protected  String SIGNER_KEY ;

    public AuthenticationResponse authenticate(AuthenticationRequest request){
        var user = userRepository.findByUsername(request.getUsername()).orElseThrow(()-> new AppException(ErrorCode.USER_NOT_EXISTED));
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(10);
        var authenticated =  encoder.matches(request.getPassword(), user.getPassword());
        if(!authenticated)
            throw new AppException(ErrorCode.UNAUTHENTICATED);

        var token = generateToken(user);
        return AuthenticationResponse.builder()
                .authenticated(true)
                .token(token)
                .build();
    }
    public String generateToken(User user){
        //tao object(header,payload)=>sign


        var jwtHeader = new JWSHeader(JWSAlgorithm.HS512);

        var jwtClaimsSet = new JWTClaimsSet.Builder()
                .subject(user.getUsername()) //chu the
                .issuer("tien.com") //noi cap token
                .issueTime(new Date()) //thoi diem phat hanh token
                .expirationTime(new Date(
                        Instant.now().plus(1, ChronoUnit.HOURS).toEpochMilli()
                )) //thoi diem toke het han(mili giay)
                //them claim ten scope de xac dinh role user, cach nhau bang dau cach
                .claim("scope",scopeBuilder(user)) //them truong du lieu moi (neu that su can thiet)
                .build();
        Payload payload = new Payload(jwtClaimsSet.toJSONObject()); //chuyen sang dang json => payload mang dang json
        JWSObject jwsObject = new JWSObject(jwtHeader,payload); //ket hop thanh 1 object token
        //ky object token bang MACSigner (co the dung cac thuat toan khac)
        try{
            jwsObject.sign(new MACSigner(SIGNER_KEY.getBytes()));
            return jwsObject.serialize();//chuyen sang string
        }catch (JOSEException e){

            log.error("cant create token",e);
            throw new RuntimeException(e);
        }
    }
    public IntrospectResponse intropect (IntropectRequest request) throws ParseException, JOSEException {
        var token = request.getToken();
        JWSVerifier verifier = new MACVerifier(SIGNER_KEY.getBytes());
        SignedJWT signedJWT = SignedJWT.parse(token);
        var expirationTime = signedJWT.getJWTClaimsSet().getExpirationTime();
        var verified = signedJWT.verify(verifier);
        return IntrospectResponse.builder()
                .valid(expirationTime.after(new Date()) && verified)
                .build();
    }
    //tao StringJoiner de them gia tri role vao scope
    private String scopeBuilder(User user){
        //cach nhau bang dau cahc
        StringJoiner stringJoiner = new StringJoiner(" ");
        if(!CollectionUtils.isEmpty(user.getRoles()))
            user.getRoles().forEach(role -> {
                stringJoiner.add("ROLE_" + role.getName());
                if(!CollectionUtils.isEmpty(role.getPermissions()))
                    role.getPermissions().forEach(permission -> {
                        stringJoiner.add(permission.getName());
                    });
            });
        return stringJoiner.toString();
    }
}
