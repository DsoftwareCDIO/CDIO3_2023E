package dtu.game;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import dtu.UIController;

public final class MonopolyJunior {
    protected static Player[] players;
    protected static Board board;
    protected static Die die;
    protected static Player currentPlayer;

    public static void main(String[] args) {
        String[] playerNames = UIController.drawMenu();
        play(playerNames);
    }

    private static void play(String[] playerNames) {
        int startcapital = 24 - (2 * playerNames.length);
        // Recieves a list of player names and creats a new of players with the names
        players = new Player[playerNames.length];
        for (int i = 0; i < playerNames.length; i++) {
            players[i] = new Player(playerNames[i], startcapital);
        }
        UIController.drawBoard(players, startcapital);

        // Creates board with chosen amount of players (2/4)
        board = new Board(players);
        die = new Die();

        boolean endGame = false;
        int turn = 0;
        Player loser = null;
        while (!endGame) {  
            try {
                currentPlayer = players[turn % players.length];

                // Checks if a player is in jail, if player is in jail eihter remove 1 money or
                // use a goojfc
                if (currentPlayer.isInJail()) {
                    if (!currentPlayer.getOutOfJail()) {
                        transaction(currentPlayer, -1);
                    }
                }

                // If special card(move to x location) is recieved from chancepile, moves the
                // player to chosen target
                if (currentPlayer.useUniqueCardIfPossible()) {
                    
                    moveOnBoard(movementWithUniqueCard(), true, false);
                } else {
                    UIController.waitForRoll(currentPlayer);
                    moveOnBoard(die.roll(), false, false);
                }
                turn++;
            } catch (TransactionImpossibleException e) {
                loser = e.loser;
                endGame = true;
            }
        }
        endGame(loser);
    }

    private static int movementWithUniqueCard() {
        // Choice is only available properties, unless they are all bought
        // In which case, player can choose any property
        List<Integer> freeFieldsTemp = new ArrayList<>();
        List<Integer> ownedFieldsTemp = new ArrayList<>();
        for (Field field : board.getFields()) {
            try {
                Property p = (Property)field;
                if (p.getOwner() == null) {
                    freeFieldsTemp.add(field.getPosition());
                    continue;
                }
                ownedFieldsTemp.add(field.getPosition());
            } catch (ClassCastException e) {

            }
        }
        int[] freeFields = new int[freeFieldsTemp.size()];
        for (int j = 0; j < freeFieldsTemp.size(); j++) {
            freeFields[j] = freeFieldsTemp.get(j);
        }
        int[] ownedFields = new int[ownedFieldsTemp.size()];
        for (int j = 0; j < ownedFieldsTemp.size(); j++) {
            ownedFields[j] = ownedFieldsTemp.get(j);
        }
        int targetField = UIController.chooseFieldOnBoard(freeFields.length > 0 ? freeFields : ownedFields);
        int movement = targetField - currentPlayer.piece.getPosition();
        return movement < 0 ? movement + 24 : movement;
    }

    // Moves player on the board, switch case for different field types
    public static void moveOnBoard(int movement, boolean forceBuy, boolean getForFree) throws TransactionImpossibleException {
        Field[] fields = board.move(currentPlayer.piece.getPosition(), movement);
        currentPlayer.piece.setPosition(fields[fields.length - 1].position);
        UIController.movePlayer(fields[fields.length - 1].position, currentPlayer);

        for (Field field : fields) {
            switch (field.getType()) {
                case PROPERTY:
                    landOnProperty((Property) field, forceBuy, getForFree); 
                    break;
                case JAIL:
                    currentPlayer.goToJail();
                    break;
                case EMPTY:
                    break;
                case CHANCE:
                    board.cardDeck.draw().activate();
                    break;
                case START:
                    transaction(currentPlayer, 2);
                    break;
                default:
                    break;
            }
        }
    }

    private static void landOnProperty(Property property, boolean forceBuy, boolean getForFree)
            throws TransactionImpossibleException {
        Player owner = property.getOwner();
        if (owner == currentPlayer) {
            return;
        }

        if (owner == null) {
            if (!getForFree) {
                transaction(currentPlayer, -property.getPrice());
            }
            property.setOwner(currentPlayer);
        } else {
            if (forceBuy) {
                transaction(currentPlayer, -property.getPrice());
                property.setOwner(currentPlayer);
                transaction(owner, property.getPrice());
            } else {
                int rent = (owner == property.getSameColor().getOwner() ? 2 : 1);
                transaction(currentPlayer, -property.getPrice() * rent);
                transaction(owner, property.getPrice() * rent);
            }
        }
    }

    // Checks if player has enough money, if false is returned the game ends
    public static void transaction(Player player, int money) throws TransactionImpossibleException {
        if (!player.account.changeMoney(money)) {
            throw new TransactionImpossibleException(player);
        }
        UIController.updateMoneyInAccount(money, player);
    }

    // Creates a list of the player account holdings, sorts them by value
    private static void endGame(Player loser) {
        Player[] leaderBoard = new Player[players.length - 1];
        int place = 0;
        for (int i = 0; i < players.length; i++) {
            if (players[i] != currentPlayer) {
                leaderBoard[place] = players[i];
                place++;
            }
        }

        Arrays.sort(leaderBoard, Collections.reverseOrder(Comparator.comparing(player -> getNetWorth(player, board))));
        Arrays.sort(leaderBoard, Collections.reverseOrder(Comparator.comparing(player -> player.account.getMoney())));

        UIController.endGamePodium(leaderBoard, loser);
    }

    public static int getNetWorth(Player player, Board b) {
        int worth = 0;
        //Checks if player owns a property on the board and adds it to his networth
        for (Field field : b.getFields()) {
            try {
                Property p = (Property)field;
                if (p.getOwner() == player) {
                    worth += p.getPrice();
                }
                
            } catch (ClassCastException e) {
                
            }
        }
        return worth;
    }
}
