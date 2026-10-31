package ladder.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BooleanSupplier;

/** 위에서 아래로 이어진 사다리의 줄을 관리한다. */
public final class Ladder {

    private final List<Line> lines;

    public Ladder(List<Line> lines) {
        validateLines(lines);
        this.lines = List.copyOf(lines);
    }

    /** 입력된 크기와 랜덤 선택으로 사다리를 만든다. */
    public static Ladder generate(LadderSize size, BooleanSupplier shouldConnect) {
        List<Line> lines = new ArrayList<>();
        for (int index = 0; index < size.height(); index++) {
            lines.add(Line.generate(size.connectionCount(), shouldConnect));
        }
        return new Ladder(lines);
    }

    public List<Line> lines() {
        return lines;
    }

    public int width() {
        return lines.get(0).connections().size() + 1;
    }

    /** 시작 위치에서 각 줄을 차례로 지나 최종 도착 위치를 찾는다. */
    public Position trace(Position start) {
        validateStart(start);
        Position current = start;
        for (Line line : lines) {
            current = line.move(current);
        }
        return current;
    }

    private void validateStart(Position start) {
        if (start.index() >= width()) {
            throw new IllegalArgumentException("사다리의 넓이를 벗어난 시작 위치입니다.");
        }
    }

    private void validateLines(List<Line> lines) {
        if (lines.isEmpty()) {
            throw new IllegalArgumentException("사다리는 한 줄 이상이어야 합니다.");
        }
        validateSameWidth(lines);
    }

    private void validateSameWidth(List<Line> lines) {
        int connectionCount = lines.get(0).connections().size();
        if (lines.stream().anyMatch(line -> line.connections().size() != connectionCount)) {
            throw new IllegalArgumentException("모든 사다리 줄의 너비가 같아야 합니다.");
        }
    }
}
