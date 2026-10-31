package ladder.domain;

import java.util.HashSet;
import java.util.List;
import java.util.stream.IntStream;

/** 사다리를 통과한 참가자별 최종 결과를 관리한다. */
public final class LadderGame {

    private final List<PlayerResult> playerResults;

    private LadderGame(List<PlayerResult> playerResults) {
        this.playerResults = List.copyOf(playerResults);
    }

    /** 각 참가자의 시작 위치를 따라가 도착 지점의 결과를 배정한다. */
    public static LadderGame play(Ladder ladder, List<PlayerName> names, List<Outcome> outcomes) {
        validateInput(ladder, names, outcomes);
        List<PlayerResult> playerResults = IntStream.range(0, names.size())
                .mapToObj(start -> resultForStart(ladder, names, outcomes, start))
                .toList();
        return new LadderGame(playerResults);
    }

    private static PlayerResult resultForStart(Ladder ladder, List<PlayerName> names,
                                               List<Outcome> outcomes, int start) {
        int destination = ladder.trace(new Position(start)).index();
        return new PlayerResult(names.get(start), outcomes.get(destination));
    }

    /** 이름에 대응하는 도착 결과를 찾는다. */
    public Outcome resultFor(PlayerName name) {
        return playerResults.stream()
                .filter(playerResult -> playerResult.playerName().equals(name))
                .map(PlayerResult::outcome)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("참가자를 찾을 수 없습니다: " + name.value()));
    }

    /** 전체 참가자의 결과를 입력한 이름 순서대로 반환한다. */
    public List<PlayerResult> results() {
        return playerResults;
    }

    private static void validateInput(Ladder ladder, List<PlayerName> names, List<Outcome> outcomes) {
        if (names.size() != ladder.width() || outcomes.size() != ladder.width()) {
            throw new IllegalArgumentException("참가자와 결과의 개수는 사다리 넓이와 같아야 합니다.");
        }
        if (new HashSet<>(names).size() != names.size()) {
            throw new IllegalArgumentException("참가자 이름은 중복될 수 없습니다.");
        }
    }
}
