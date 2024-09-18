package com.dain_review.domain.user.model.response;


import com.dain_review.domain.user.model.entity.User;

public record UserHeaderResponse(String name, String profileImageUrl, Boolean isInfluencer) {

    public static UserHeaderResponse of(User user) {

        return new UserHeaderResponse(
                user.getName(), user.getProfileImageUrl(), user.isInfluencer());
    }
}