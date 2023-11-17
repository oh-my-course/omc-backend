package com.programmers.bucketback.domains.item.domain;

import java.util.Objects;

import com.programmers.bucketback.Hobby;
import com.programmers.bucketback.domains.BaseEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "items")
public class Item extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@Column(name = "hobby")
	@Enumerated(EnumType.STRING)
	private Hobby hobby;

	@Column(name = "name")
	private String name;

	@Column(name = "price")
	private Integer price;

	@Column(name = "url")
	private String url;

	@Column(name = "image")
	private String image;

	@Builder
	public Item(
		final Hobby hobby,
		final String name,
		final Integer price,
		final String url,
		final String image
	) {
		this.hobby = Objects.requireNonNull(hobby);
		this.name = Objects.requireNonNull(name);
		this.price = Objects.requireNonNull(price);
		this.url = Objects.requireNonNull(url);
		this.image = Objects.requireNonNull(image);
	}
}
