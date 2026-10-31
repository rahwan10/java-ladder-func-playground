package ladder.view;

import ladder.domain.Connection;
import ladder.domain.Ladder;
import ladder.domain.Line;
import ladder.domain.Position;

/** 사다리의 연결 상태를 콘솔 문자로 표현한다. */
public final class OutputView {

    public void print(Ladder ladder) {
        System.out.println("실행결과\n");
        ladder.lines().forEach(line -> System.out.println(render(line)));
    }

    public void printResults(Ladder ladder) {
        System.out.println();
        for (int index = 0; index < ladder.width(); index++) {
            printResult(ladder, new Position(index));
        }
    }

    private void printResult(Ladder ladder, Position start) {
        Position destination = ladder.trace(start);
        System.out.printf("%d -> %d%n", start.index(), destination.index());
    }

    private String render(Line line) {
        StringBuilder result = new StringBuilder("    |");
        line.connections().forEach(connection -> result.append(symbol(connection)));
        return result.toString();
    }

    private String symbol(Connection connection) {
        if (connection == Connection.CONNECTED) {
            return "-----|";
        }
        return "     |";
    }
}
