package dtu.game;

import dtu.game.Field.FieldType;
import dtu.game.Property.Color;

public class Board {
    private Field[] fields;
    protected ChanceCardDeck cardDeck;
    
    //Board object, creates all the fields when initialised 
    public Board(Player[] players) {
        fields = new Field[24];
        fields[0] = new Field(FieldType.START, 0);
        fields[1] = new Property(1, Color.BROWN, 1);
        fields[2] = new Property(1, Color.BROWN,2);
        fields[3] = new Field(FieldType.CHANCE,3);
        fields[4] = new Property(1, Color.CYAN,4);
        fields[5] = new Property(1, Color.CYAN,5);
        fields[6] = new Field(FieldType.EMPTY,6);
        fields[7] = new Property(2, Color.PURPLE,7);
        fields[8] = new Property(2, Color.PURPLE,8);
        fields[9] = new Field(FieldType.CHANCE,9);
        fields[10] = new Property(2, Color.ORANGE,10);
        fields[11] = new Property(2, Color.ORANGE,11);
        fields[12] = new Field(FieldType.EMPTY,12);
        fields[13] = new Property(3, Color.RED,13);
        fields[14] = new Property(3, Color.RED,14);
        fields[15] = new Field(FieldType.CHANCE,15);
        fields[16] = new Property(3, Color.YELLOW,16);
        fields[17] = new Property(3, Color.YELLOW,17);
        fields[18] = new Field(FieldType.JAIL,18);
        fields[19] = new Property(4, Color.GREEN,19);
        fields[20] = new Property(4, Color.GREEN,20);
        fields[21] = new Field(FieldType.CHANCE,21);
        fields[22] = new Property(5, Color.BLUE,22);
        fields[23] = new Property(5, Color.BLUE,23);
        //I'm having a field day, ha ha

        // We need to find a better way of doing this
        ((Property)fields[1]).setSameColor((Property)fields[2]);
        ((Property)fields[2]).setSameColor((Property)fields[1]);
        ((Property)fields[4]).setSameColor((Property)fields[5]);
        ((Property)fields[5]).setSameColor((Property)fields[4]);
        ((Property)fields[7]).setSameColor((Property)fields[8]);
        ((Property)fields[8]).setSameColor((Property)fields[7]);
        ((Property)fields[10]).setSameColor((Property)fields[11]);
        ((Property)fields[11]).setSameColor((Property)fields[10]);
        ((Property)fields[13]).setSameColor((Property)fields[14]);
        ((Property)fields[14]).setSameColor((Property)fields[13]);
        ((Property)fields[16]).setSameColor((Property)fields[17]);
        ((Property)fields[17]).setSameColor((Property)fields[16]);
        ((Property)fields[19]).setSameColor((Property)fields[20]);
        ((Property)fields[20]).setSameColor((Property)fields[19]);
        ((Property)fields[22]).setSameColor((Property)fields[23]);
        ((Property)fields[23]).setSameColor((Property)fields[22]);

        cardDeck = new ChanceCardDeck(players);
    }

    //Function to move position on the board
    public Field[] move(int startPosition, int movement) {
        int endPosition = startPosition + movement;
        if (endPosition >= 24) {
            endPosition %= 24;
            if (endPosition > 0) {         
                return new Field[]{fields[0], fields[endPosition]};
            }
        }
        return new Field[]{fields[endPosition]};
    }

    public Field[] getFields() {
        return fields;
    }
}
