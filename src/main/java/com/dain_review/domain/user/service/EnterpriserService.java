package com.dain_review.domain.user.service;


import com.dain_review.domain.user.exception.RegisterException;
import com.dain_review.domain.user.exception.errortype.RegisterErrorCode;
import com.dain_review.domain.user.model.entity.Enterpriser;
import com.dain_review.domain.user.model.entity.User;
import com.dain_review.domain.user.model.entity.enums.Role;
import com.dain_review.domain.user.model.request.EnterpriserChangeRequest;
import com.dain_review.domain.user.model.request.EnterpriserExtraRegisterRequest;
import com.dain_review.domain.user.model.request.EnterpriserSingUpRequest;
import com.dain_review.domain.user.model.response.EnterpriserChangeResponse;
import com.dain_review.domain.user.model.response.EnterpriserResponse;
import com.dain_review.domain.user.repository.EnterpriserRepository;
import com.dain_review.domain.user.repository.UserRepository;
import com.dain_review.global.api.API;
import com.siot.IamportRestClient.IamportClient;
import com.siot.IamportRestClient.exception.IamportResponseException;
import com.siot.IamportRestClient.response.Certification;
import com.siot.IamportRestClient.response.IamportResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class EnterpriserService {

    private final UserRepository userRepository;
    private final EnterpriserRepository enterpriserRepository;
    private final IamportClient iamportClient;
    private final PasswordEncoder pe;

    @Transactional
    public void signUpExtra(
            Long id, EnterpriserExtraRegisterRequest enterpriserExtraRegisterRequest) {

        User user = userRepository.getUserById(id);
        user.change(enterpriserExtraRegisterRequest);
    }

    public EnterpriserResponse getMyPage(Long id) {

        User user = userRepository.getUserById(id);
        return EnterpriserResponse.from(user);
    }

    public EnterpriserChangeResponse getChangePage(Long id) {

        User user = userRepository.getUserById(id);
        return EnterpriserChangeResponse.from(user);
    }

    @Transactional
    public EnterpriserChangeResponse changeInformation(
            EnterpriserChangeRequest enterpriserChangeRequest, Long id) {

        User user = userRepository.getUserById(id);
        user.change(enterpriserChangeRequest);

        return EnterpriserChangeResponse.from(user);
    }

    @Transactional
    public ResponseEntity singUpEnterpriser(EnterpriserSingUpRequest request) {
        try {
            IamportResponse<Certification> certification = iamportClient.certificationByImpUid(request.impId());

            if (certification.getResponse().getName().equals(request.name()))
                throw new RegisterException(RegisterErrorCode.FAIL_IMP_NAME_NOT_SAME);

            if (userRepository.findByEmail(request.email()).isPresent())
                throw new RegisterException(RegisterErrorCode.EMAIL_SAME);

            User user = userRepository.save(User.builder()
                    .email(request.email())
                    .role(Role.ROLE_INFLUENCER)
                    .marketing(request.marketing())
                    .isDeleted(false)
                    .phone(certification.getResponse().getPhone())
                    .point(0L)
                    .nickname(request.nickname())
                    .name(request.name())
                    .password(pe.encode(request.password()))
                    .joinPath(request.joinPath())
                    .build());

            enterpriserRepository.save(Enterpriser.builder()
                    .company(request.company())
                    .user(user)
                    .build());

        } catch (IamportResponseException e) {
            throw new RegisterException(RegisterErrorCode.FAIL_IMP_ID);
        } catch (IOException e) {
            throw new RegisterException(RegisterErrorCode.FAIL_IMP_ID);
        }

        return API.OK();
    }

}
