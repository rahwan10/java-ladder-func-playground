package ladder.domain;

/** 사다리의 세로선 위치를 0부터 시작하는 번호로 나타낸다. */
public record Position(int index) {

    public Position {
        if (index < 0) {
            throw new IllegalArgumentException("사다리 위치는 0 이상이어야 합니다.");
        }
    }
}
