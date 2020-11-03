package battleship;

public enum Shot {
    Hit,
    Miss,
    Sank;

    public char toChar() {
        if (this == Hit) {
            return 'X';
        } else if (this == Miss) {
            return 'M';
        }

        return '?';
    }

    public static Shot fromChar(char boardMark) {
        switch (Character.toUpperCase(boardMark)) {
            case 'X':
                return Shot.Hit;
            case 'M':
                return Shot.Miss;
            default:
                return null;
        }
    }
}
