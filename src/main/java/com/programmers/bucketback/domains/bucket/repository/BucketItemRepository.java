package com.programmers.bucketback.domains.bucket.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.programmers.bucketback.domains.bucket.domain.BucketItem;

public interface BucketItemRepository extends JpaRepository<BucketItem,Long> {

	Optional<List<BucketItem>> findByBucketId(Long bucketId);
}
