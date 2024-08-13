package com.dain_review.domain.like.model.entity;


import com.dain_review.domain.campaign.model.entity.Campaign;
import com.dain_review.domain.user.model.entity.User;
import com.dain_review.global.model.entity.BaseEntity;
import jakarta.persistence.Entity;
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
public class like extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user; // 하나의 유저에 다수의 좋아요

    @ManyToOne
    @JoinColumn(name = "campaign_id")
    private Campaign campaign; // 하나의 캠페인에 다수의 좋아요
}
