/**
 * This class makes a a unique level where you can find straights to win the game.
 * 
 * @author Enrique Javier
 * @author Luis Rivera 
 * 
 */
import javax.swing.JFrame;

import java.lang.reflect.Array;
import java.util.Arrays;

import javax.swing.ImageIcon;
import javax.swing.JFrame;

public class StraightLevel extends RankTrioLevel
{
	ValueGiver giver = new ValueGiver(); 
	long score = 0;		// initialize score
	final long x = 5;	// points to deduct

	protected StraightLevel(TurnsTakenCounterLabel validTurnTime, JFrame mainFrame) {
		super(validTurnTime, mainFrame);
		this.getTurnsTakenCounter().setDifficultyModeLabel("Straight Level");
		this.setCardsToTurnUp(5);
		this.setCardsPerRow(10);
		this.setRowsPerGrid(5);
		this.scoreReset();
	}

	@Override
	protected void makeDeck() {
		// In Straight level the grid consists of distinct cards, no repetitions

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
	protected boolean turnUp(Card card) 
	{
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
				
				// get the other cards (which were already turned up
				// checks if the user found a straight
				if (this.isStraight(card))
				{
					//				Five cards have at least two different suits and have ascending ranks, 
					//				so they are removed from the list (they will remain face up).
					this.getMainFrame().setScore(score);
					this.getTurnedCardsBuffer().clear();
					//					counter--;
				}

				else
					if(score != 0) 
					{
						score -= x;
						this.getMainFrame().setScore(score);
						giver.resetValues();
					}
				// The cards have the same matching suits, so start the timer to turn them down
					this.getTurnDownTimer().start();
			}
			return true;
		}
		return false;
	}

	/**
	 * Method that checks if the user has a straight
	 * 
	 * @param card card clicked by user 
	 * @return true if user finds a straight
	 */
	public boolean isStraight(Card card)
	{
		Card otherCard1 = (Card) this.getTurnedCardsBuffer().get(0);
		Card otherCard2 = (Card) this.getTurnedCardsBuffer().get(1);
		Card otherCard3 = (Card) this.getTurnedCardsBuffer().get(2);
		Card otherCard4 = (Card) this.getTurnedCardsBuffer().get(3);

		int[] cards = new int[5];
		{
			cards[0] = giver.rankValue(otherCard1);
			cards[1] = giver.rankValue(otherCard2);
			cards[2] = giver.rankValue(otherCard3);
			cards[3] = giver.rankValue(otherCard4);
			cards[4] = giver.rankValue(card);
		}
		Arrays.sort(cards);

		if (card.getSuit() != otherCard1.getSuit() ||card.getSuit() != otherCard2.getSuit() || 
				card.getSuit() != otherCard3.getSuit() || card.getSuit() != otherCard4.getSuit()) 
		{
			// Special case
			// if one of the cards is an A
			if(cards[4] == 20) 
			{
				boolean a = cards[0] == 2 && cards[1] == 3 &&
						cards[2] == 4 && cards[3] == 5 ;
				boolean b = cards[0] == 10 && cards[1] == 11 &&        
						cards[2] == 12 && cards[3] == 13 ;
				if (a == true)
				{
					// adds score 
					score += 1000+100*giver.highestRank(otherCard1, otherCard2, otherCard3, otherCard4, card);
					this.getMainFrame().setScore(score);
					giver.resetValues();
				}
				else if( b == true)
				{
					// adds score
					score += 1000 + 100*giver.highestRank(otherCard1, otherCard2, otherCard3, otherCard4, card);
					this.getMainFrame().setScore(score);
					giver.resetValues();
				}

				return  a || b ;
			}

			// General case
			else 
				if(cards[0]+1 == cards[1] && cards[1]+1 == cards[2] && cards[2]+1 == cards[3] && cards[3]+1 == cards[4])
				{
					// adds score
					score += 1000 + 100*giver.highestRank(otherCard1, otherCard2, otherCard3, otherCard4, card);
					this.getMainFrame().setScore(score);
					giver.resetValues();

					return true;
				}
		}
		return false;
	}// ends isStraight method
	
	/**
	 * Method for resetting score to 0
	 * 
	 */
	public void scoreReset()
	{
		this.getMainFrame().setScore(0);
	}// ends scoreReset method
	
	/**
	 *  Method for checking if there are no more hands to find
	 * 
	 */
	public boolean isGameOver()
	{

		int counter = 0;

		for(int i = 0; i< this.getGrid().size(); i++)
		{
			if(!this.getGrid().get(i).isFaceUp())
			{
				counter++;
			}
		}	

		if (counter == 9)
		{
			return true;
		}
		else
		{
			return false;
		}
	}// ends isGameOver Method
} // ends class 