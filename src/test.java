import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;


public class test{
	public static void main(String[] args) {
		Function<String, Integer> length = s->s.length();
		System.out.println(length.apply("Hello"));
	}

}

class SimplePair<T>{
	T data, data2;
	
	public SimplePair(T s, T s2){
		data = s;
		data2 = s2;
	}
	public T getFirst() {
		return data;
	}
	public T getSecond() {
		return data2;
	}
}

class Card{
	String suit;
	String number;

	public Card(String suit, String number) {
		this.suit = suit;
		this.number = number;
	}
	@Override
	public String toString() {
		return "Card [suit=" + suit + ", number=" + number + "]";
	}
}

class Deck{
	ArrayList<Card> deck = new ArrayList<>();
	String[] suit, number;
	
	public Deck() {};
	void shuffle() {
		Collections.shuffle(deck);
	}
}