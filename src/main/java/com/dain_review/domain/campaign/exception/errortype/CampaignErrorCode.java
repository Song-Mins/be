package com.dain_review.domain.campaign.exception.errortype;


import com.dain_review.global.exception.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum CampaignErrorCode implements ErrorCode {
    CAMPAIGN_NOT_FOUND(HttpStatus.BAD_REQUEST, "체험단을 찾을 수 없습니다."),
    UNAUTHORIZED_ACCESS(HttpStatus.FORBIDDEN, "권한이 없습니다.");

    private final HttpStatus status;
    private final String msg;
}