package ladder.view;

import java.util.Scanner;
import ladder.domain.LadderSize;

/** 콘솔에서 사다리 크기를 입력받는다. */
public final class InputView {

    private final Scanner scanner = new Scanner(System.in);

    public LadderSize readSize() {
        System.out.println("사다리의 넓이는 몇 개인가요?");
        int width = readNumber();
        System.out.println("사다리의 높이는 몇 개인가요?");
        int height = readNumber();
        return new LadderSize(width, height);
    }

    private int readNumber() {
        return Integer.parseInt(scanner.nextLine().trim());
    }
}
