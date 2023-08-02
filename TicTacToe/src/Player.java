public class Player {

    private String name;

    private PieceType pieceType;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public PieceType getPieceType() {
        return pieceType;
    }

    public void setPieceType(PieceType pieceType) {
        this.pieceType = pieceType;
    }

    public Player(String name, PieceType pieceType) {
        this.name = name;
        this.pieceType = pieceType;
    }
}
