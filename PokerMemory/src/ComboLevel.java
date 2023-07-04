/**
 * This class makes a a unique level where you can find flushes, 
 * straight and royal flushes to win.
 * 
 * @version 1.0
 * @since 4/12/2017
 * 
 * @author Enrique Javier
 * @author Luis Rivera 
 * 
 * 
 */
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import java.util.Arrays;

import javax.swing.ImageIcon;
import javax.swing.JFrame;

public class ComboLevel extends RankTrioLevel
{
	
	ValueGiver giver = new ValueGiver();
	long score = 0;		// initialize score
	final long x = 5;	// points to deduct from score when a user does not press pass and does not find a hand
	int iChoose;		// chosen hand by user


	protected ComboLevel(TurnsTakenCounterLabel validTurnTime, JFrame mainFrame) {
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
				// enters to the isCombo method 
				if (this.isCombo(card))
				{
					// the five cards are a permitted hand (Straight, Flush or Royal Flush)
					this.getTurnedCardsBuffer().clear();
				}

				else {
					// will deduct 5 points for every turn that the user did not found a hand
					// get the user's chosen hand 
					iChoose = this.getChosenOption(iChoose);
					if(score != 0) 
					{
						if(iChoose == 3) {

						}else
							score -= x;
						this.getMainFrame().setScore(score);
						giver.resetValues();
					}
					// The cards do not have the same matching suits, so start the timer to turn them down
					this.getTurnDownTimer().start();
				}
			}
			return true;
		}

		return false;
	}

/**
 * Method that checks if the five cards selected make a possible hand.
 * 
 * @param card cards that are click on
 * @return true if cards make a hand
 */
	public boolean isCombo(Card card)
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
		iChoose = this.setChosenOption();

		/**
		 * Will check first what is the option selected by the user
		 * If the option chosen by the user is Flush it will enter the block
		 * It checks if the hand is a Flush 
		 * 
		 */

		if(this.getChosenOption(iChoose) == 0) 

		{
			if((card.getSuit().equals(otherCard1.getSuit())) && (card.getSuit().equals(otherCard2.getSuit())) && 
					(card.getSuit().equals(otherCard3.getSuit())) && (card.getSuit().equals(otherCard4.getSuit()))) 


			{
				score += 700 + giver.rankSummation(otherCard1, otherCard2, otherCard3, otherCard4, card);
				giver.resetValues();
				this.getMainFrame().setScore(score);

				return true;
			}

		}

		/**
		 * will check first what is the option selected by the user
		 * if the option chosen by the user is a straight it will enter the block
		 * It checks if the hand is a straight 
		 * 
		 */
		else 
			if(this.getChosenOption(iChoose) == 1) {
				if (card.getSuit() != otherCard1.getSuit() ||card.getSuit() != otherCard2.getSuit() || 
						card.getSuit() != otherCard3.getSuit() || card.getSuit() != otherCard4.getSuit()) 
				{
					if(cards[4] == 20) {
						boolean a = cards[0] == 2 && cards[1] == 3 &&
								cards[2] == 4 && cards[3] == 5 ;
						boolean b = cards[0] == 10 && cards[1] == 11 &&        
								cards[2] == 12 && cards[3] == 13 ;

						if (a||b == true)
						{
							//adds score
							score = 1000 + 100*giver.highestRank(otherCard1, otherCard2, otherCard3, otherCard4, card);
							this.getMainFrame().setScore(score);
							giver.resetValues();
						}

						return  a || b ;

					}
				}
			}


		/**
		 * will check first what is the option selected by the user
		 * If the option chosen by the user is Royal Flush it will enter the block
		 * It checks if the hand is a Royal Flush 
		 * 
		 */
			else 
				if(this.getChosenOption(iChoose) == 2){
					if((card.getSuit().equals(otherCard1.getSuit())) && (card.getSuit().equals(otherCard2.getSuit())) && 
							(card.getSuit().equals(otherCard3.getSuit())) && (card.getSuit().equals(otherCard4.getSuit()))) 
					{
						if (cards[0] == 10 && cards[1] == 11 && cards[2] == 12 && cards[3] == 13 && cards[4] == 20 )
						{	
							// adds score 
							score += 1000+75*giver.rankSummation(otherCard1, otherCard2, otherCard3, otherCard4, card);
							giver.resetValues();
							this.getMainFrame().setScore(score);

							return true;
						}
					}
					/**
					 * If the user chooses to pass 
					 * it enters this block
					 * 
					 */
					else 
						if(this.getChosenOption(iChoose) == 3) {
							// do nothing
						}
						else {

						}
				}
		return false;
	}

	/**
	 * Method for allowing the user to pick a hand 
	 * 
	 * @return option selected by the user
	 */
	public int setChosenOption() {

		String[] options = { "Flush", "Straight", "Royal Flush", "Pass" };

		int option = JOptionPane.showOptionDialog(null, "What do you want to do? :", "Your options",
				JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE,
				null, options, options[0]);

		// if option = 0 is Flush, if option = 1 is straight, if option = 2 is Royal flush and if option = 3 is pass

		return option;
	}

	/**
	 * A getter method for getting the option chosen by the user
	 * @param option option chosen by user in the JOptionPane
	 * @return option 
	 */
	public int getChosenOption(int option) {

		return option;
	}

/**
 * Method for resetting score to 0
 * 
 */
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
	}
}