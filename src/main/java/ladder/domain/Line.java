package ladder.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BooleanSupplier;

/** 사다리의 한 줄에 있는 가로선 연결을 관리한다. */
public final class Line {

    private final List<Connection> connections;

    public Line(List<Connection> connections) {
        validateNoOverlapping(connections);
        this.connections = List.copyOf(connections);
    }

    /** 랜덤 선택을 받아 겹치지 않는 가로선 한 줄을 만든다. */
    public static Line generate(int connectionCount, BooleanSupplier shouldConnect) {
        validateConnectionCount(connectionCount);
        List<Connection> connections = new ArrayList<>();
        for (int index = 0; index < connectionCount; index++) {
            connections.add(nextConnection(connections, shouldConnect));
        }
        return new Line(connections);
    }

    public List<Connection> connections() {
        return connections;
    }

    /** 현재 위치에서 이 줄의 연결을 따라 이동한 위치를 반환한다. */
    public Position move(Position position) {
        validatePosition(position);
        if (hasRightConnection(position)) {
            return new Position(position.index() + 1);
        }
        if (hasLeftConnection(position)) {
            return new Position(position.index() - 1);
        }
        return position;
    }

    private boolean hasRightConnection(Position position) {
        return position.index() < connections.size()
                && connections.get(position.index()) == Connection.CONNECTED;
    }

    private boolean hasLeftConnection(Position position) {
        return position.index() > 0
                && connections.get(position.index() - 1) == Connection.CONNECTED;
    }

    private void validatePosition(Position position) {
        if (position.index() > connections.size()) {
            throw new IllegalArgumentException("사다리의 넓이를 벗어난 위치입니다.");
        }
    }

    private static void validateConnectionCount(int connectionCount) {
        if (connectionCount < 0) {
            throw new IllegalArgumentException("가로선 연결 개수는 0 이상이어야 합니다.");
        }
    }

    private static Connection nextConnection(List<Connection> connections, BooleanSupplier shouldConnect) {
        if (!connections.isEmpty() && connections.get(connections.size() - 1) == Connection.CONNECTED) {
            return Connection.DISCONNECTED;
        }
        if (shouldConnect.getAsBoolean()) {
            return Connection.CONNECTED;
        }
        return Connection.DISCONNECTED;
    }

    private void validateNoOverlapping(List<Connection> connections) {
        for (int index = 1; index < connections.size(); index++) {
            validatePair(connections, index);
        }
    }

    private void validatePair(List<Connection> connections, int index) {
        if (connections.get(index - 1) == Connection.CONNECTED
                && connections.get(index) == Connection.CONNECTED) {
            throw new IllegalArgumentException("가로선은 서로 겹칠 수 없습니다.");
        }
    }
}
