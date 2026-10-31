package ladder.domain;

/** 사다리의 세로선 개수와 높이를 나타낸다. */
public record LadderSize(int width, int height) {

    public LadderSize {
        if (width < 1 || height < 1) {
            throw new IllegalArgumentException("사다리의 넓이와 높이는 1 이상이어야 합니다.");
        }
    }

    public int connectionCount() {
        return width - 1;
    }
}
