package ladder.view;

import java.util.List;
import java.util.stream.Collectors;
import ladder.domain.Connection;
import ladder.domain.Ladder;
import ladder.domain.Line;
import ladder.domain.Outcome;
import ladder.domain.PlayerName;
import ladder.domain.PlayerResult;

/** 사다리의 연결 상태를 콘솔 문자로 표현한다. */
public final class OutputView {

    public void printBoard(Ladder ladder, List<PlayerName> names, List<Outcome> outcomes) {
        System.out.println("사다리 결과\n");
        System.out.println(renderValues(names.stream().map(PlayerName::value).toList()));
        ladder.lines().forEach(line -> System.out.println(render(line)));
        System.out.println(renderValues(outcomes.stream().map(Outcome::value).toList()));
    }

    public void printOne(Outcome outcome) {
        System.out.println("실행 결과");
        System.out.println(outcome.value());
    }

    public void printAll(List<PlayerResult> playerResults) {
        System.out.println("실행 결과");
        playerResults.forEach(this::printPlayerResult);
    }

    public void printError(String message) {
        System.out.println("[ERROR] " + message);
    }

    private void printPlayerResult(PlayerResult playerResult) {
        System.out.printf("%s : %s%n", playerResult.playerName().value(), playerResult.outcome().value());
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

    private String renderValues(List<String> values) {
        return "    " + values.stream()
                .map(value -> String.format("%-6s", value))
                .collect(Collectors.joining());
    }
}
