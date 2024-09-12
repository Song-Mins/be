package com.dain_review.domain.post.repository;


import com.dain_review.domain.post.model.entity.Post;
import com.dain_review.domain.post.model.entity.enums.CategoryType;
import com.dain_review.domain.post.model.entity.enums.CommunityType;
import com.dain_review.domain.post.model.entity.enums.FollowType;
import com.dain_review.domain.user.model.entity.enums.Role;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PostRepositoryCustom {
    Long findPreviousPost(
            Long postId,
            Role role,
            CategoryType categoryType,
            CommunityType communityType,
            FollowType followType,
            String keyword);

    Long findNextPost(
            Long postId,
            Role role,
            CategoryType categoryType,
            CommunityType communityType,
            FollowType followType,
            String keyword);

    Page<Post> searchCommunityPost(
            Role role, String keyword, CommunityType communityType, Pageable pageable);

    Page<Post> searchFollowPost(String keyword, FollowType followType, Pageable pageable);
}