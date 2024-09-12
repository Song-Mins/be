package com.dain_review.domain.application.model.entity;


import com.dain_review.domain.application.exception.ApplicationException;
import com.dain_review.domain.application.exception.errortype.ApplicationErrorCode;
import com.dain_review.domain.application.model.entity.enums.ApplicationState;
import com.dain_review.domain.application.model.request.ApplicationRequest;
import com.dain_review.domain.campaign.model.entity.Campaign;
import com.dain_review.domain.review.exception.ReviewException;
import com.dain_review.domain.review.exception.errortype.ReviewErrorCode;
import com.dain_review.domain.user.model.entity.User;
import com.dain_review.global.model.entity.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class Application extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "campaign_id")
    private Campaign campaign;

    private String message;

    @Enumerated(EnumType.STRING)
    private ApplicationState applicationState;

    private Boolean isDeleted;

    public static Application from(
            ApplicationRequest applicationRequest, User user, Campaign campaign) {
        return Application.builder()
                .user(user)
                .campaign(campaign)
                .message(applicationRequest.message())
                .applicationState(ApplicationState.PENDING)
                .isDeleted(false)
                .build();
    }

    public void delete(Long userId) {
        if (!user.getId().equals(userId)) {
            throw new ApplicationException(ApplicationErrorCode.FAIL_CANCEL);
        }
        this.isDeleted = true;
    }

    public void checkReviewCreateAble(Long userId) {
        if (this.user.isNotSame(userId)) {
            throw new ReviewException(ReviewErrorCode.UNAUTHORIZED_ACCESS);
        }
        if (!applicationState.equals(ApplicationState.APPROVED)) {
            throw new ReviewException(ReviewErrorCode.APPLICATION_NOT_APPROVED);
        }
        if (this.campaign.isNotReviewPeriod()) {
            throw new ReviewException(ReviewErrorCode.INVALID_REVIEW_PERIOD);
        }
    }
}
