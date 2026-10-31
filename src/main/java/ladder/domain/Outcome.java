package ladder.domain;

/** 사다리 끝에 놓이는 결과다. */
public record Outcome(String value) {

    public Outcome {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException("사다리 결과는 비어 있을 수 없습니다.");
        }
        value = value.trim();
    }
}
