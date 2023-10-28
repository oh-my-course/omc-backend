package com.programmers.bucketback.domains.member.domain;

import com.programmers.bucketback.domains.common.BaseEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "members")
public class Member extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@NotNull
	@Column(name = "email")
	private String email;

	@NotNull
	@Column(name = "password")
	private String password;

	@NotNull
	@Column(name = "nickname")
	private String nickname;

	@Column(name = "introduction")
	private String introduction;

	@NotNull
	@Column(name = "level_point")
	private Integer levelPoint;

	@NotNull
	@Enumerated(EnumType.STRING)
	@Column(name = "role")
	private Role role;

	@NotNull
	@Enumerated(EnumType.STRING)
	@Column(name = "status")
	private MemberStatus status;

	@Builder
	private Member(
		@NotNull final String email,
		@NotNull final String password,
		@NotNull final String nickname,
		@NotNull final Role role
	) {
		this.email = email;
		this.password = password;
		this.nickname = nickname;
		this.levelPoint = 0;
		this.role = role;
		this.status = MemberStatus.ACTIVE;
	}

	public void delete() {
		this.status = MemberStatus.DELETED;
	}

	public boolean isDeleted() {
		return this.getStatus() == MemberStatus.DELETED;
	}
}
