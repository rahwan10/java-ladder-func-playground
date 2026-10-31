package ladder.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.List;
import org.junit.jupiter.api.Test;

class LineTest {

    @Test
    void 모든_연결을_선택해도_인접한_가로선은_겹치지_않는다() {
        Line line = Line.generate(3, () -> true);

        assertThat(line.connections()).containsExactly(
                Connection.CONNECTED, Connection.DISCONNECTED, Connection.CONNECTED);
    }

    @Test
    void 연결을_선택하지_않으면_모든_가로선이_끊어진다() {
        Line line = Line.generate(3, () -> false);

        assertThat(line.connections()).containsExactly(
                Connection.DISCONNECTED, Connection.DISCONNECTED, Connection.DISCONNECTED);
    }

    @Test
    void 겹치는_가로선을_가진_줄은_만들_수_없다() {
        List<Connection> connections = List.of(
                Connection.CONNECTED, Connection.CONNECTED, Connection.DISCONNECTED);

        assertThatThrownBy(() -> new Line(connections))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void 너비가_다른_줄도_각자_생성할_수_있다() {
        Line line = Line.generate(1, () -> true);

        assertThat(line.connections()).containsExactly(Connection.CONNECTED);
    }

    @Test
    void 연결된_가로선을_따라_좌우로_이동한다() {
        Line line = new Line(List.of(Connection.CONNECTED, Connection.DISCONNECTED, Connection.CONNECTED));

        assertThat(line.move(new Position(0))).isEqualTo(new Position(1));
        assertThat(line.move(new Position(1))).isEqualTo(new Position(0));
        assertThat(line.move(new Position(2))).isEqualTo(new Position(3));
        assertThat(line.move(new Position(3))).isEqualTo(new Position(2));
    }

    @Test
    void 가로선이_없으면_현재_위치를_유지한다() {
        Line line = Line.generate(3, () -> false);

        assertThat(line.move(new Position(1))).isEqualTo(new Position(1));
    }
}
