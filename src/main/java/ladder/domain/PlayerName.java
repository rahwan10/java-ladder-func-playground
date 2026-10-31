package ladder.domain;

/** 사다리 게임 참가자의 이름이다. */
public record PlayerName(String value) {

    private static final int MAX_LENGTH = 5;

    public PlayerName {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException("참가자 이름은 비어 있을 수 없습니다.");
        }
        value = value.trim();
        validate(value);
    }

    private static void validate(String value) {
        if (value.codePointCount(0, value.length()) > MAX_LENGTH || value.contains(",")) {
            throw new IllegalArgumentException("참가자 이름은 쉼표 없이 5글자 이하여야 합니다.");
        }
        if (value.equalsIgnoreCase("all")) {
            throw new IllegalArgumentException("all은 전체 결과 조회에 사용하므로 이름으로 쓸 수 없습니다.");
        }
    }
}
