package ladder.view;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;
import ladder.domain.Outcome;
import ladder.domain.PlayerName;

/** 콘솔에서 참가자, 결과, 사다리 높이와 조회 대상을 입력받는다. */
public final class InputView {

    private final Scanner scanner;

    public InputView(Scanner scanner) {
        this.scanner = scanner;
    }

    public List<PlayerName> readNames() {
        System.out.println("참여할 사람 이름을 입력하세요. (이름은 쉼표(,)로 구분하세요)");
        return Arrays.stream(scanner.nextLine().split(",", -1))
                .map(PlayerName::new)
                .toList();
    }

    public List<Outcome> readOutcomes() {
        System.out.println("실행 결과를 입력하세요. (결과는 쉼표(,)로 구분하세요)");
        return Arrays.stream(scanner.nextLine().split(",", -1))
                .map(Outcome::new)
                .toList();
    }

    public int readHeight() {
        System.out.println("최대 사다리 높이는 몇 개인가요?");
        return Integer.parseInt(scanner.nextLine().trim());
    }

    public Optional<String> readQuery() {
        System.out.println("결과를 보고 싶은 사람은?");
        if (!scanner.hasNextLine()) {
            return Optional.empty();
        }
        return Optional.of(scanner.nextLine().trim());
    }
}
