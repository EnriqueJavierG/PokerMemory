/**
 * Class for setting number value from the ranks of each card
 * @version 1.0
 * @since 4/12/2017
 * 
 * @author Luis Rivera
 * @author Enrique Javier
 * 
 */

import java.util.Arrays;

import javax.swing.JFrame;

public class ValueGiver 
{
	long sumRank = 0;
	long highestRank = 0;
	long sumRank2 = 0;
	
	/**
	 * Gives each rank a unique value 
	 * 
	 * @param card
	 * @return
	 */
	public int rankValue(Card card)
	{
		int value = 0;
		
		if(card.getRank().equals("2"))
		{
			value = 2;
		}
		
		if(card.getRank().equals("3"))
		{
			value = 3;
		}
		
		if(card.getRank().equals("4"))
		{
			value = 4;
		}
		
		if(card.getRank().equals("5"))
		{
			value = 5;
		}
		
		if(card.getRank().equals("6"))
		{
			value = 6;
		}
		
		if(card.getRank().equals("7"))
		{
			value = 7;
		}
		
		if(card.getRank().equals("8"))
		{
			value = 8;
		}
		
		if(card.getRank().equals("9"))
		{
			value = 9;
		}
		
		if(card.getRank().equals("t"))
		{
			value = 10;
		}
		
		if(card.getRank().equals("j"))
		{
			value = 11;
		}
		
		if(card.getRank().equals("q"))
		{
			value = 12;
		}
		
		if(card.getRank().equals("k"))
		{
			value = 13;
		}
		
		if(card.getRank().equals("a"))
		{
			value = 20;
		}
		return value;
	} // ends rankValue method
	
	/**
	 * Method for summing 5 cards 
	 * 
	 * @param card1
	 * @param card2
	 * @param card3
	 * @param card4
	 * @param card5
	 * @return sum of the five cards
	 */
	public long rankSummation(Card card1, Card card2, Card card3, Card card4, Card card5)
	{
		long[] cards = new long[5];
		{
		cards[0] = this.rankValue(card1);
		cards[1] = this.rankValue(card2);
		cards[2] = this.rankValue(card3);
		cards[3] = this.rankValue(card4);
		cards[4] = this.rankValue(card5);
		}
		
		for(int i = 0; i< cards.length; i++)
		{
			sumRank += cards[i];
		}
		return sumRank;
	}// ends rankSummation method
	
	/**
	 * Method for summing rank values between 3 cards
	 * 
	 * @param card1
	 * @param card2
	 * @param card3
	 * @return sum of the three cards
	 */
	public long rankSummation2(Card card1, Card card2, Card card3)
	{
		long[] cards = new long[5];
		{
		cards[0] = this.rankValue(card1);
		cards[1] = this.rankValue(card2);
		cards[2] = this.rankValue(card3);
		}
		
		for(int i = 0; i< cards.length; i++)
		{
			sumRank2 += cards[i];
		}
		return sumRank2;
	}// ends rankSummation2 method
	
	/**
	 * Method for comparing five cards and obtain the highest value card 
	 * 
	 * @param card1
	 * @param card2
	 * @param card3
	 * @param card4
	 * @param card5
	 * @return highest rank card
	 */
	public long highestRank(Card card1, Card card2, Card card3, Card card4, Card card5)
	{
		long[] cards = new long[5];
		{
		cards[0] = this.rankValue(card1);
		cards[1] = this.rankValue(card2);
		cards[2] = this.rankValue(card3);
		cards[3] = this.rankValue(card4);
		cards[4] = this.rankValue(card5);
		}
		Arrays.sort(cards);
		
		highestRank = cards[4];
		return highestRank;
	}// ends highsetRank method 
	
	/**
	 * Method for resetting values
	 * 
	 */
	public void resetValues()
	{
		sumRank = 0;
		sumRank2 = 0;
		highestRank = 0;
	}// ends resetValue method
}