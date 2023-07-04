/**
 * Class is the same as RankTrioLevel but with a score metric
 * Stores currently turned cards, allows only three cards to be uncovered on each turn
 * Also handles turning cards back down after a delay if cards are different
 *
 * @author Michael Leonhard (Original Author)
 * @author Modified by Bienvenido Vélez (UPRM)
 * @version Sept 2017
 * 
 * @author Modified by Luis Rivera and Enrique Javier
 * added score metrics
 */

import java.util.Arrays;

import javax.swing.ImageIcon;
import javax.swing.JFrame;

public class NewRankTrioLevel extends RankTrioLevel
{
	ValueGiver giver = new ValueGiver();
	long score = 0;		// initialize score
	final long x = 5;	// deduct points to score

	protected NewRankTrioLevel(TurnsTakenCounterLabel validTurnTime, JFrame mainFrame) 
	{
		super(validTurnTime, mainFrame);
		this.getTurnsTakenCounter().setDifficultyModeLabel("New Trio Level");
		this.setCardsToTurnUp(3);
		this.setCardsPerRow(10);
		this.setRowsPerGrid(5);
		this.scoreReset();
	}

	@Override
	protected void makeDeck() {
		// In Trio level the grid consists of distinct cards, no repetitions

		//back card
		ImageIcon backIcon = this.getCardIcons()[this.getTotalCardsPerDeck()];

		int cardsToAdd[] = new int[getRowsPerGrid() * getCardsPerRow()];
		for(int i = 0; i < (getRowsPerGrid() * getCardsPerRow()); i++)
		{
			cardsToAdd[i] = i;
		}

		// randomize the order of the deck
		this.randomizeIntArray(cardsToAdd);

		// make each card object
		for(int i = 0; i < cardsToAdd.length; i++)
		{
			// number of the card, randomized
			int num = cardsToAdd[i];
			// make the card object and add it to the panel
			String rank = cardNames[num].substring(0, 1);
			String suit = cardNames[num].substring(1, 2);
			this.getGrid().add( new Card(this, this.getCardIcons()[num], backIcon, num, rank, suit));
		}
	}

	@Override
	protected boolean turnUp(Card card) {
		// the card may be turned
		if(this.getTurnedCardsBuffer().size() < getCardsToTurnUp()) 
		{
			// add the card to the list
			this.getTurnedCardsBuffer().add(card);
			if(this.getTurnedCardsBuffer().size() == getCardsToTurnUp())
			{
				// We are uncovering the last card in this turn
				// Record the player's turn
				this.getTurnsTakenCounter().increment();
				// get the other card (which was already turned up)
				if(this.isTrio(card)) 
				{
					// Three cards match, so remove them from the list (they will remain face up)
					this.getTurnedCardsBuffer().clear();
				}
				else
				{
					if(score != 0) 
					{
						score -= x;
						this.getMainFrame().setScore(score);
						giver.resetValues();
					}
					this.getTurnDownTimer().start();
				}
			}
			return true;
		}
		return false;
	}
	
	
	/**
	 * Method that checks if the three cards are of the same rank
	 *	 
	 * @param card 
	 * @return true if all three cards are of the same rank
	 */
	public boolean isTrio(Card card)
	{
		Card otherCard1 = (Card) this.getTurnedCardsBuffer().get(0);
		Card otherCard2 = (Card) this.getTurnedCardsBuffer().get(1);

		int[] cards = new int[3];
		{
			cards[0] = giver.rankValue(otherCard1);
			cards[1] = giver.rankValue(otherCard2);
			cards[2] = giver.rankValue(card);
		}
		Arrays.sort(cards);

		if(cards[0] == cards[1] && cards[1] == cards[2])
		{
			score = 100 + giver.rankSummation2(otherCard1, otherCard2, card);
			this.getMainFrame().setScore(score);
			giver.resetValues();
			return true;
		}
		return false;
	}

	public void scoreReset()
	{
		this.getMainFrame().setScore(0);
	}

	/**
	 *  Method for checking if there are no more hands to find
	 * 
	 */
	public boolean isGameOver()
	{

		int counter = 0;

		for(int i = 0; i < this.getGrid().size(); i++)
		{
			if(!this.getGrid().get(i).isFaceUp())
			{
				counter++;
			}
		}	

		if (counter == 13)
		{
			return true;
		}
		else
		{
			return false;
		}
	}
}
