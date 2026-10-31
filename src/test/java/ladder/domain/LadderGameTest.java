package ladder.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.List;
import org.junit.jupiter.api.Test;

class LadderGameTest {

    @Test
    void 참가자의_출발점이_도착한_결과를_배정한다() {
        Ladder ladder = twoLineLadder();
        List<PlayerName> names = List.of(new PlayerName("neo"), new PlayerName("brown"), new PlayerName("brie"));
        List<Outcome> outcomes = List.of(new Outcome("꽝"), new Outcome("5000"), new Outcome("3000"));

        LadderGame game = LadderGame.play(ladder, names, outcomes);

        assertThat(game.resultFor(new PlayerName("neo"))).isEqualTo(new Outcome("3000"));
        assertThat(game.resultFor(new PlayerName("brown"))).isEqualTo(new Outcome("꽝"));
        assertThat(game.resultFor(new PlayerName("brie"))).isEqualTo(new Outcome("5000"));
    }

    @Test
    void 참가자와_결과의_개수는_사다리_넓이와_같아야_한다() {
        Ladder ladder = twoLineLadder();
        List<PlayerName> names = List.of(new PlayerName("neo"));
        List<Outcome> outcomes = List.of(new Outcome("꽝"));

        assertThatThrownBy(() -> LadderGame.play(ladder, names, outcomes))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void 중복된_참가자_이름은_허용하지_않는다() {
        Ladder ladder = twoLineLadder();
        List<PlayerName> names = List.of(new PlayerName("neo"), new PlayerName("neo"), new PlayerName("brie"));
        List<Outcome> outcomes = List.of(new Outcome("꽝"), new Outcome("5000"), new Outcome("3000"));

        assertThatThrownBy(() -> LadderGame.play(ladder, names, outcomes))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void 존재하지_않는_참가자의_결과는_찾을_수_없다() {
        Ladder ladder = twoLineLadder();
        List<PlayerName> names = List.of(new PlayerName("neo"), new PlayerName("brown"), new PlayerName("brie"));
        List<Outcome> outcomes = List.of(new Outcome("꽝"), new Outcome("5000"), new Outcome("3000"));
        LadderGame game = LadderGame.play(ladder, names, outcomes);

        assertThatThrownBy(() -> game.resultFor(new PlayerName("tommy")))
                .isInstanceOf(IllegalArgumentException.class);
    }

    private Ladder twoLineLadder() {
        Line firstLine = new Line(List.of(Connection.CONNECTED, Connection.DISCONNECTED));
        Line secondLine = new Line(List.of(Connection.DISCONNECTED, Connection.CONNECTED));
        return new Ladder(List.of(firstLine, secondLine));
    }
}
