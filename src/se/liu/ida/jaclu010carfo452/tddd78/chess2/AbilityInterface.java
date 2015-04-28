package se.liu.ida.jaclu010carfo452.tddd78.chess2;

public interface AbilityInterface
{
    public AbilityCharacteristic getAC();

    public AbilityType getAT();

    public int getCost();

    public void use(int y, int x, ChessPiece[][] board);

    public void rule();
}
