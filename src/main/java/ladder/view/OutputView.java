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

    /** 같은 위치의 이름과 결과를 사다리 위아래에 출력한다. */
    public void printBoard(Ladder ladder, List<PlayerName> names, List<Outcome> outcomes) {
        System.out.println("사다리 결과\n");
        System.out.println(renderValues(names.stream().map(PlayerName::value).toList()));
        System.out.println(renderLines(ladder));
        System.out.println(renderValues(outcomes.stream().map(Outcome::value).toList()));
        System.out.println();
    }

    /** 한 참가자가 도착한 결과를 출력한다. */
    public void printOne(Outcome outcome) {
        System.out.println("실행 결과");
        System.out.println(outcome.value());
    }

    /** 전체 참가자 결과를 입력한 이름 순서대로 출력한다. */
    public void printAll(List<PlayerResult> playerResults) {
        System.out.println("실행 결과");
        String results = playerResults.stream()
                .map(this::renderPlayerResult)
                .collect(Collectors.joining(System.lineSeparator()));
        System.out.println(results);
    }

    /** 조회할 수 없는 이름의 오류를 출력한다. */
    public void printError(String message) {
        System.out.println("[ERROR] " + message);
    }

    private String renderPlayerResult(PlayerResult playerResult) {
        return playerResult.playerName().value() + " : " + playerResult.outcome().value();
    }

    private String renderLines(Ladder ladder) {
        return ladder.lines().stream()
                .map(this::render)
                .collect(Collectors.joining(System.lineSeparator()));
    }

    private String render(Line line) {
        return "    |" + line.connections().stream()
                .map(this::symbol)
                .collect(Collectors.joining());
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
