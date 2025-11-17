package com.rummirimkub.game;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CreateRoomRequest {

    @NotBlank(message = "방 제목은 필수입니다.")
    @Size(min = 2, max = 30, message = "방 제목은 2자에서 30자 사이여야 합니다.")
    private String title;

    /**
     * - null이거나 빈 문자열이면 공방으로 취급
     */
    @Size(max = 20, message = "비밀번호는 20자 이하로 설정해주세요.")
    private String password;

    @Min(value = 2, message = "최소 2명 이상이어야 합니다.")
    @Max(value = 4, message = "최대 4명까지만 가능합니다.")
    private Integer maxPlayers;
}