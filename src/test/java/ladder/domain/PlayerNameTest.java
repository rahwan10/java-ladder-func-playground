package ladder.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.Test;

class PlayerNameTest {

    @Test
    void 이름은_앞뒤_공백을_제거하고_최대_다섯_글자까지_허용한다() {
        assertThat(new PlayerName(" tommy ").value()).isEqualTo("tommy");
    }

    @Test
    void 빈_이름이나_여섯_글자_이름은_허용하지_않는다() {
        assertThatThrownBy(() -> new PlayerName("  ")).isInstanceOf(IllegalArgumentException.class);
        assertThatThrownBy(() -> new PlayerName("longer")).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void 전체_조회에_쓰는_all은_참가자_이름으로_사용할_수_없다() {
        assertThatThrownBy(() -> new PlayerName("all")).isInstanceOf(IllegalArgumentException.class);
    }
}
