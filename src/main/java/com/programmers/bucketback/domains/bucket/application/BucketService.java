package com.programmers.bucketback.domains.bucket.application;

import static java.util.stream.Collectors.*;

import org.springframework.stereotype.Service;

import com.programmers.bucketback.domains.bucket.api.dto.response.BucketGetByCursorResponse;
import com.programmers.bucketback.domains.bucket.api.dto.response.BucketGetMemberItemResponse;
import com.programmers.bucketback.domains.bucket.api.dto.response.BucketGetResponse;
import com.programmers.bucketback.domains.bucket.application.vo.BucketCursorSummary;
import com.programmers.bucketback.domains.bucket.application.vo.BucketMemberItemCursorSummary;
import com.programmers.bucketback.domains.bucket.application.vo.GetBucketServiceResponse;
import com.programmers.bucketback.domains.bucket.application.vo.ItemIdRegistry;
import com.programmers.bucketback.domains.bucket.domain.Bucket;
import com.programmers.bucketback.domains.bucket.domain.BucketInfo;
import com.programmers.bucketback.domains.common.Hobby;
import com.programmers.bucketback.domains.common.MemberUtils;
import com.programmers.bucketback.domains.common.vo.CursorPageParameters;
import com.programmers.bucketback.domains.item.application.ItemReader;
import com.programmers.bucketback.domains.member.application.MemberReader;
import com.programmers.bucketback.global.error.exception.BusinessException;
import com.programmers.bucketback.global.error.exception.ErrorCode;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BucketService {

	private final BucketAppender bucketAppender;
	private final BucketModifier bucketModifier;
	private final BucketRemover bucketRemover;
	private final BucketReader bucketReader;
	private final MemberReader memberReader;
	private final ItemReader itemReader;

	/** 버킷 생성 */
	public void createBucket(
		final BucketInfo bucketInfo,
		final ItemIdRegistry registry
	) {
		validateExceedBudget(bucketInfo, registry);

		bucketAppender.append(bucketInfo, registry);
	}

	/** 버킷 수정 */
	public void modifyBucket(
		final Long bucketId,
		final BucketInfo bucketInfo,
		final ItemIdRegistry registry
	) {
		validateExceedBudget(bucketInfo, registry);

		Long memberId = MemberUtils.getCurrentMemberId();
		Bucket bucket = bucketReader.read(bucketId, memberId);

		bucketModifier.modify(bucket, bucketInfo, registry);
	}

	/** 버킷 삭제 */
	public void deleteBucket(final Long bucketId) {
		Long memberId = MemberUtils.getCurrentMemberId();
		bucketRemover.remove(bucketId, memberId);
	}

	/** 버킷 수정을 위한 멤버 아이템 목록 조회 */
	public BucketGetMemberItemResponse getMemberItemsForModify(
		final Long bucketId,
		final CursorPageParameters parameters
	) {
		Long memberId = MemberUtils.getCurrentMemberId();

		BucketMemberItemCursorSummary bucketMemberItemCursorSummary =
			bucketReader.readByMemberItems(bucketId, memberId, parameters);

		return new BucketGetMemberItemResponse(bucketMemberItemCursorSummary);
	}

	/**
	 * 버킷 상세 조회
	 */
	public BucketGetResponse getBucket(final Long bucketId) {
		GetBucketServiceResponse response = bucketReader.readDetail(bucketId);
		return response.toBucketGetResponse();
	}

	/**
	 * 버킷 커서 조회
	 */
	public BucketGetByCursorResponse getBucketsByCursor(
		final String nickname,
		final Hobby hobby,
		final CursorPageParameters parameters
	) {
		Long memberId = memberReader.readByNickname(nickname).getId();
		BucketCursorSummary bucketCursorSummary = bucketReader.readByCursor(memberId, hobby, parameters);

		return BucketGetByCursorResponse.from(bucketCursorSummary);
	}

	private void validateExceedBudget(
		final BucketInfo bucketInfo,
		final ItemIdRegistry registry
	) {
		if (bucketInfo.getBudget() != null) {
			Integer totalPrice = registry.itemIds().stream()
				.map(itemId -> itemReader.read(itemId).getPrice())
				.collect(reducing(Integer::sum))
				.get();

			if (totalPrice > bucketInfo.getBudget()) {
				throw new BusinessException(ErrorCode.BUCKET_EXCEED_BUDGET);
			}
		}
	}

}
