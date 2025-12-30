package dev.mcger.lobby.world.validator;

import java.util.Map;
import dev.mcger.protocol.world.WorldConfigKey;
import dev.mcger.protocol.world.WorldConfigPolicy;
import dev.mcger.protocol.world.WorldType;

public final class WorldConfigUpdateValidator {

  private WorldConfigUpdateValidator() {
  }

  public static void validate(Map<WorldConfigKey, Object> updates) {
    // 1. Password-related settings
    validatePassword(updates);

    // 2. World name
    validateWorldName(updates);

    // 3. Player limit
    validateMaxPlayers(updates);

    // 4. World type
    validateWorldType(updates);
  }

  /**
   * 비밀번호 관련 설정 검증
   *
   * 검증 순서:
   * 1. PASSWORD 변경 요청 여부
   * 2. PASSWORD_ENABLED 토글 확인
   * 3. PASSWORD 값 검증
   */
  private static void validatePassword(Map<WorldConfigKey, Object> updates) {
    // PASSWORD 변경 요청이 없으면 검증하지 않음
    if (!updates.containsKey(WorldConfigKey.PASSWORD)) {
      return;
    }

    // 1️⃣ 토글 상태 확인
    if (!Boolean.TRUE.equals(updates.get(WorldConfigKey.PASSWORD_ENABLED))) {
      throw new IllegalArgumentException(
          "PASSWORD can be updated only when PASSWORD_ENABLED is true");
    }

    // 2️⃣ 값 검증
    Object value = updates.get(WorldConfigKey.PASSWORD);
    if (!(value instanceof String password)) {
      throw new IllegalArgumentException("PASSWORD must be a string");
    }

    if (password.isBlank()) {
      throw new IllegalArgumentException("PASSWORD cannot be blank");
    }

    if (password.length() > WorldConfigPolicy.PASSWORD_MAX_LENGTH) {
      throw new IllegalArgumentException(
          "PASSWORD max length is " + WorldConfigPolicy.PASSWORD_MAX_LENGTH);
    }
  }

  /**
   * 월드명 변경 검증
   */
  private static void validateWorldName(Map<WorldConfigKey, Object> updates) {

    if (!updates.containsKey(WorldConfigKey.WORLD_NAME)) {
      return;
    }

    Object value = updates.get(WorldConfigKey.WORLD_NAME);
    if (!(value instanceof String name)) {
      throw new IllegalArgumentException("WORLD_NAME must be a string");
    }

    if (name.isBlank()) {
      throw new IllegalArgumentException("WORLD_NAME cannot be blank");
    }

    if (name.length() > WorldConfigPolicy.WORLD_NAME_MAX_LENGTH) {
      throw new IllegalArgumentException(
          "WORLD_NAME max length is " + WorldConfigPolicy.WORLD_NAME_MAX_LENGTH);
    }
  }

  /**
   * 최대 접속 인원 제한 검증
   */
  private static void validateMaxPlayers(Map<WorldConfigKey, Object> updates) {

    if (!updates.containsKey(WorldConfigKey.MAX_PLAYERS)) {
      return;
    }

    Object value = updates.get(WorldConfigKey.MAX_PLAYERS);
    if (!(value instanceof Integer maxPlayers)) {
      throw new IllegalArgumentException("MAX_PLAYERS must be an integer");
    }

    if (maxPlayers < 1) {
      throw new IllegalArgumentException("MAX_PLAYERS must be >= 1");
    }
  }

  /**
   * 월드 타입 변경 검증
   *
   * ⚠️ 정책:
   * - 로비에서는 월드 타입 변경을 기본적으로 허용
   * - 서버에서는 실행 중인 월드에 대해 거부할 수 있음
   */
  private static void validateWorldType(Map<WorldConfigKey, Object> updates) {

    if (!updates.containsKey(WorldConfigKey.WORLD_TYPE)) {
      return;
    }

    // 타입 자체는 enum/문자열 여부만 확인
    Object value = updates.get(WorldConfigKey.WORLD_TYPE);
    if (!(value instanceof WorldType)) {
      throw new IllegalArgumentException("WORLD_TYPE must be WorldType enum");
    }
  }

  // public static void validate(Map<WorldConfigKey, Object> updates) {
  // // Password 변경 요청 확인
  // if (updates.containsKey(WorldConfigKey.PASSWORD)) {
  //
  // // 1. Password enabled 확인
  // Object enabledValue = updates.get(WorldConfigKey.PASSWORD_ENABLED);
  //
  // if (!Boolean.TRUE.equals(enabledValue)) {
  // throw new IllegalArgumentException("PASSWORD can be only when
  // PASSWORD_ENABLED is true");
  // }
  //
  // // 2. Password 값 검증 (글자수)
  // String password = (String) updates.get(WorldConfigKey.PASSWORD);
  //
  // if (password.isBlank()) {
  // throw new IllegalArgumentException("PASSWORD cannot be blank");
  // }
  //
  // if (password.length() > WorldConfigPolicy.PASSWORD_MAX_LENGTH) {
  // throw new IllegalArgumentException(" PASSWORD max length is " +
  // WorldConfigPolicy.PASSWORD_MAX_LENGTH);
  // }
  //
  // }
  //
  // if (updates.containsKey(WorldConfigKey.MAX_PLAYERS)) {
  // int max = (int) updates.get(WorldConfigKey.MAX_PLAYERS);
  // if (max < 1) {
  // throw new IllegalArgumentException("MAX_PLAYERS must be >= 1");
  // }
  // }
  //
  // if (updates.containsKey(WorldConfigKey.WORLD_NAME)) {
  // String name = (String) updates.get(WorldConfigKey.WORLD_NAME);
  // if (name.isBlank() || name.length() >
  // WorldConfigPolicy.WORLD_NAME_MAX_LENGTH) {
  // throw new IllegalArgumentException("WORLD_NAME invalid");
  // }
  // }
  // }
}
