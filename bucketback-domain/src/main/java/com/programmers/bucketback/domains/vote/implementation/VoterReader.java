package com.programmers.bucketback.domains.vote.implementation;

import java.util.Optional;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.programmers.bucketback.domains.vote.domain.Vote;
import com.programmers.bucketback.domains.vote.domain.Voter;
import com.programmers.bucketback.domains.vote.repository.VoterRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class VoterReader {

	private final VoterRepository voterRepository;

	@Transactional(readOnly = true)
	public Optional<Voter> read(
		final Vote vote,
		final Long memberId
	) {
		return voterRepository.findByVoteAndMemberId(vote, memberId);
	}

	@Transactional(readOnly = true)
	public Voter read(
		final Vote vote,
		final Long memberId,
		final Long itemId
	) {
		return voterRepository.findByVoteAndMemberId(vote, memberId)
			.orElseGet(() -> new Voter(vote, memberId, itemId));
	}
}
