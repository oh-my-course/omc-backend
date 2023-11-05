package com.programmers.bucketback.domains.feed.api;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.programmers.bucketback.domains.feed.api.request.FeedCreateRequest;
import com.programmers.bucketback.domains.feed.api.request.FeedUpdateRequest;
import com.programmers.bucketback.domains.feed.application.FeedService;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/feeds")
public class FeedController {

	private final FeedService feedService;

	@Operation(summary = "피드 생성", description = "FeedCreateRequest 을 이용하여 피드를 생성힙니다.")
	@PostMapping("/")
	public ResponseEntity<Void> createFeed(@RequestBody @Valid final FeedCreateRequest request) {
		feedService.createFeed(request.toContent());

		return ResponseEntity.ok().build();
	}

	@Operation(summary = "피드 수정", description = "FeedUpdateRequest 을 이용하여 피드를 수정힙니다.")
	@PutMapping("/{feedId}")
	public ResponseEntity<Void> modifyFeed(
		@PathVariable final Long feedId,
		@RequestBody @Valid final FeedUpdateRequest request
	) {
		feedService.modifyFeed(feedId, request.toContent());

		return ResponseEntity.ok().build();
	}

	@Operation(summary = "피드 삭제", description = "FeedId를 이용하여 피드를 수정힙니다.")
	@DeleteMapping("/{feedId}")
	public ResponseEntity<Void> modifyFeed(@PathVariable final Long feedId) {
		feedService.removeFeed(feedId);

		return ResponseEntity.ok().build();
	}
}
