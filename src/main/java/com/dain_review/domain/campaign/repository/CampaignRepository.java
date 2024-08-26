package com.dain_review.domain.campaign.repository;


import com.dain_review.domain.campaign.model.entity.Campaign;
import com.dain_review.domain.campaign.model.entity.enums.Platform;
import com.dain_review.domain.campaign.model.entity.enums.State;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CampaignRepository extends JpaRepository<Campaign, Long> {

    @Query(
            "SELECT c FROM Campaign c "
                    + "WHERE c.state = :state "
                    + "AND c.platform = :platform "
                    + "AND c.businessName LIKE %:keyword% "
                    + "AND c.user.id = :userId "
                    + "AND c.isDeleted = false "
                    + "ORDER BY c.id DESC")
    Page<Campaign> findByStateAndPlatformAndNameContainingAndUserId(
            @Param("state") State state,
            @Param("platform") Platform platform,
            @Param("keyword") String keyword,
            @Param("userId") Long userId,
            Pageable pageable);
}