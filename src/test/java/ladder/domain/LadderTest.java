package ladder.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.List;
import org.junit.jupiter.api.Test;

class LadderTest {

    @Test
    void 입력받은_크기로_사다리를_생성한다() {
        Ladder ladder = Ladder.generate(new LadderSize(4, 5), () -> true);

        assertThat(ladder.lines()).hasSize(5);
        assertThat(ladder.lines()).allSatisfy(line -> assertThat(line.connections()).hasSize(3));
    }

    @Test
    void 사다리에_줄이_없으면_만들_수_없다() {
        assertThatThrownBy(() -> new Ladder(List.of()))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void 사다리_줄의_너비가_다르면_만들_수_없다() {
        Line firstLine = Line.generate(2, () -> false);
        Line secondLine = Line.generate(3, () -> false);

        assertThatThrownBy(() -> new Ladder(List.of(firstLine, secondLine)))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void 사다리_크기는_양수여야_한다() {
        assertThatThrownBy(() -> new LadderSize(0, 5))
                .isInstanceOf(IllegalArgumentException.class);
        assertThatThrownBy(() -> new LadderSize(4, 0))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void 위에서_아래로_줄을_따라_최종_위치를_찾는다() {
        Line firstLine = new Line(List.of(Connection.CONNECTED, Connection.DISCONNECTED));
        Line secondLine = new Line(List.of(Connection.DISCONNECTED, Connection.CONNECTED));
        Ladder ladder = new Ladder(List.of(firstLine, secondLine));

        assertThat(ladder.trace(new Position(0))).isEqualTo(new Position(2));
        assertThat(ladder.trace(new Position(1))).isEqualTo(new Position(0));
        assertThat(ladder.trace(new Position(2))).isEqualTo(new Position(1));
    }

    @Test
    void 시작_위치는_사다리_넓이_안에_있어야_한다() {
        Ladder ladder = Ladder.generate(new LadderSize(3, 2), () -> false);

        assertThatThrownBy(() -> ladder.trace(new Position(3)))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
